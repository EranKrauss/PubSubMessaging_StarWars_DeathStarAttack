package bgu.spl.mics.application.services;

import java.util.ArrayList;
import java.util.List;

import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;

/**
 * LeiaMicroservices Initialized with Attack objects, and sends them as  {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LeiaMicroservice extends MicroService  {
	private Attack[] attacks;
	private Future[] futures;
	
    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
		this.attacks = attacks;
		this.futures = new Future[attacks.length];
    }
    @Override
    protected void initialize() {

        MessageBusImpl messageBus = MessageBusImpl.getInstance();
        Diary diary = Diary.getDiaryInstance();

        //subscribeBroadcast
        this.subscribeBroadcast(GeneralBroadcast.class, new Callback<GeneralBroadcast>() {
            @Override
            public void call(GeneralBroadcast c) {
                diary.setLeiaTerminate();
                terminate();
            }
        });

        sendAttacks();                                               //Leia send attacks to the messageBus
        messageBus.sendBroadcast(new attacksDoneBroadcast());       //TODO
        waitForAttacks();                                           //leia waits for c3po & han solo
        messageBus.sendEvent(new DeactivationEvent());              //call R2D2

    }

    private boolean attacksDone(){
        boolean isDone = true;
        for (int i = 0 ; i < this.futures.length /*& isDone*/; i++){
            if (!futures[i].isDone()){
                isDone = false;
            }
        }
        return isDone;
    }

    private void sendAttacks (){
        MessageBusImpl messageBus = MessageBusImpl.getInstance();
        for (int i = 0 ; i < attacks.length ; i++){
            Attack curr = this.attacks[i];
            AttackEvent attackEvent = new AttackEvent(curr);
            futures[i] = messageBus.sendEvent(attackEvent);
        }
    }

    private void waitForAttacks(){
        while (!attacksDone()){
            try {
                Thread.sleep(200);                                  //wait untill all atacks are done
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
