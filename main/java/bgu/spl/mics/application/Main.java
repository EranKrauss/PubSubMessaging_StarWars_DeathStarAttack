package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.services.*;
import bgu.spl.mics.inputReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.*;


/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args)  {

		String path = args[0];
		try {
			inputReader input = getInputFromFile(path);		//read from json
			createEwoks(input);								//create and initialize Ewoks
			Thread[] threadArr = new Thread[5];
			createAndActivateThreads(threadArr , input);	//create microServices and Threads
			waitForThreads(threadArr);						//wait for all threads
			createOutputFile(args[1]);						// create the output file and write to it
			//Diary.getDiaryInstance().printForTest();

			System.out.println("finish");					//print finish


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (InterruptedException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void createOutputFile(String name) throws IOException {
		Diary diary = Diary.getDiaryInstance();
		// create a map
		Map<String, Object> map = new HashMap<>();
		map.put("totalAttacks", diary.getTotalAttacks() );
		map.put("HanSoloFinish", diary.getHanSoloFinish());
		map.put("C3POFinish", diary.getC3POFinish());
		map.put("R2D2Deactivate", diary.getR2D2Deactivate());
		map.put("LeiaTerminate", diary.getLeiaTerminate());
		map.put("HanSoloTerminate", diary.getHanSoloTerminate());
		map.put("C3POTerminate", diary.getC3POTerminate());
		map.put("R2D2Terminate", diary.getR2D2Terminate());
		map.put("LandoTerminate", diary.getLandoTerminate());

		Writer writer = new FileWriter(name);
		Gson out = new GsonBuilder().setPrettyPrinting().create();
		out.toJson(map , writer);
		writer.close();
	}

	public static inputReader getInputFromFile(String path) throws FileNotFoundException {
		Gson gson = new Gson();
		FileReader file= new FileReader(path);
		BufferedReader reader = new BufferedReader(file);
		inputReader input = gson.fromJson(reader , inputReader.class);
		return input;
	}

	public static void createAndActivateThreads(Thread[] arr ,inputReader input ) throws InterruptedException{

		//create MicroService
		LeiaMicroservice leia = new LeiaMicroservice(input.getAttacks());		//create Leia
		C3POMicroservice C3PO = new C3POMicroservice();							//create c3po
		HanSoloMicroservice hanSolo = new HanSoloMicroservice();				//create hansolo
		LandoMicroservice lando = new LandoMicroservice(input.getLando());		//create land
		R2D2Microservice r2d2 = new R2D2Microservice(input.getR2D2());			//create r2d2

		//created threads
		arr[0] = new Thread(hanSolo);
		arr[1] = new Thread(C3PO);
		arr[2] = new Thread(leia);
		arr[3] = new Thread(r2d2);
		arr[4] = new Thread(lando);

		//activate Threads
		//activate C3PO & HAN SOLO
		arr[0].start();
		arr[1].start();
		Thread.sleep(100);	//sleep for a while - that leia can start at time
		arr[2].start();				//activate leia
		arr[3].start();				//activate R2D2
		arr[4].start();				//activate Lando
	}


	public static void waitForThreads(Thread[] arr) throws InterruptedException{
		for (Thread t : arr){
			t.join();
		}
	}

	public static void createEwoks(inputReader input){
		//create Ewoks
		Ewoks ewoks = Ewoks.getInstance(input.getEwoks());
		for (int i = 0 ; i < input.getEwoks() ; i++){
			Ewok ewok = new Ewok(i + 1);
			ewoks.addEwok(ewok);
		}
	}

}
