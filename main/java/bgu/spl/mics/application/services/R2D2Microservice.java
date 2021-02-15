package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Message;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.messages.GeneralBroadcast;
import bgu.spl.mics.application.passiveObjects.Diary;

/**
 * R2D2Microservices is in charge of the handling {@link DeactivationEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link DeactivationEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class R2D2Microservice extends MicroService {

    private long duration;

    public R2D2Microservice(long duration) {

        super("R2D2");
        this.duration = duration;
    }

    @Override
    protected void initialize() {

        MessageBusImpl messageBus = MessageBusImpl.getInstance();
        Diary diary = Diary.getDiaryInstance();

        //register
        messageBus.register(this);
        //subscribe to deactivation event
        this.subscribeEvent(DeactivationEvent.class, new Callback<DeactivationEvent>() {
            @Override
            public void call(DeactivationEvent c) {
                try {
                    Thread.sleep(duration);                 //deactivated shield generator
                    diary.setR2D2Deactivate();              //write in the diary
                    messageBus.complete(c , true);    // let Leia knows that it finish
                    messageBus.sendEvent(new BombDestroyerEvent()); //TODO - call lando
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //subscribeBroadcast
        this.subscribeBroadcast(GeneralBroadcast.class, new Callback<GeneralBroadcast>() {
            @Override
            public void call(GeneralBroadcast c) {
                diary.setR2D2Terminate();
                terminate();
            }
        });
    }
}
