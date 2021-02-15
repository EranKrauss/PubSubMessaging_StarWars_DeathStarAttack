package bgu.spl.mics.application.services;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Callback;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.GeneralBroadcast;
import bgu.spl.mics.application.passiveObjects.Diary;

/**
 * LandoMicroservice
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LandoMicroservice  extends MicroService {
    private long duration;

    public LandoMicroservice(long duration) {


        super("Lando");
        this.duration = duration;
    }

    @Override
    protected void initialize() {

        MessageBusImpl messageBus = MessageBusImpl.getInstance();
        Diary diary = Diary.getDiaryInstance();

        //register
        messageBus.register(this);
        //subscribe Event
        this.subscribeEvent(BombDestroyerEvent.class, new Callback<BombDestroyerEvent>() {
            @Override
            public void call(BombDestroyerEvent c) {
                try {
                    Thread.sleep(duration);                                 //exploed
                    messageBus.complete(c , true);                    //let his friends know that it exploed
                    messageBus.sendBroadcast(new GeneralBroadcast());     //terminateAll
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        //subscribe broadcst
        this.subscribeBroadcast(GeneralBroadcast.class, new Callback<GeneralBroadcast>() {
            @Override
            public void call(GeneralBroadcast c) {
                diary.setLandoTerminate();
                terminate();
            }
        });
    }
}
