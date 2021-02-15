package bgu.spl.mics.application.services;
import bgu.spl.mics.Callback;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.GeneralBroadcast;
import bgu.spl.mics.application.messages.attacksDoneBroadcast;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewoks;


/**
 * C3POMicroservices is in charge of the handling {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class C3POMicroservice extends MicroService {

    public C3POMicroservice() {
        super("C3PO");
    }

    @Override
    protected void initialize() {

        MessageBusImpl messageBus = MessageBusImpl.getInstance();
        Diary diary = Diary.getDiaryInstance();

        //subscribe to event
        this.subscribeEvent(AttackEvent.class, new Callback<AttackEvent>() {
            @Override
            public void call(AttackEvent c) {

                //acuire resources
                boolean finish = false;
                while (! finish ){

                      finish = Ewoks.isAvailable(c);
                    try {
                        if (finish) {
                                Thread.sleep(c.getDuration());              //sleep -- fight
                                Ewoks.makeAvailable(c);                       //release resources/ewoks
                                messageBus.complete(c, true);         //let leia know that this specific mission isDone
                            diary.increaseTotallAttack();               //update diary
                        }
                        else {
                            Thread.sleep(200);
                        }
                    }catch(InterruptedException e){e.printStackTrace();}

                }
            }
        });

        //subscribe Broadcast
        this.subscribeBroadcast(GeneralBroadcast.class, new Callback<GeneralBroadcast>() {
            @Override
            public void call(GeneralBroadcast c) {
                diary.setC3POTerminate();
                terminate();
            }
        });

        //register attacksDoneBroadcast
        this.subscribeBroadcast(attacksDoneBroadcast.class, new Callback<attacksDoneBroadcast>() {
            @Override
            public void call(attacksDoneBroadcast c) {
                diary.setC3POFinish();
            }
        });
    }

}

