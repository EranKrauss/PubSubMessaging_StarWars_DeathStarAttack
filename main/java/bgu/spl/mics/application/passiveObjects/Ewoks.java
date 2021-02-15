package bgu.spl.mics.application.passiveObjects;


import bgu.spl.mics.application.messages.AttackEvent;

import java.util.HashMap;

/**
 * Passive object representing the resource manager.
 * <p>
 * This class must be implemented as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private methods and fields to this class.
 */
public class Ewoks {

    private static Ewoks ewoksInstance = null;
    private static Ewok[] ewoksArr;

    public static synchronized Ewoks getInstance(int num){
        if (ewoksInstance == null){
            ewoksInstance = new Ewoks(num + 1);
        }
        return ewoksInstance;
    }

    private Ewoks(int num){
      this.ewoksArr = new Ewok[num];
    }

    public static void addEwok(Ewok ewok){
        ewoksArr[ewok.getSerialNumber()] = ewok;
    }

    public static synchronized boolean isAvailable(AttackEvent attackEvent)   {
            boolean isAvailable = true;
            for (int i = 0; i < attackEvent.getSerials().length; i++) {
                Ewok currEwok = ewoksArr[attackEvent.getSerials()[i]];
                if (!currEwok.isAvailable()) {
                    isAvailable = false;
                }
            }

            if (isAvailable) {
                for (int i = 0; i < attackEvent.getSerials().length; i++) {
                    Ewok currEwok = ewoksArr[attackEvent.getSerials()[i]];
                    currEwok.acquire();
                }
            }
            return isAvailable;

    }

    public synchronized static void makeAvailable(AttackEvent attackEvent){
            for (int i = 0; i < attackEvent.getSerials().length; i++) {
                ewoksArr[attackEvent.getSerials()[i]].release();
            }
    }



}
