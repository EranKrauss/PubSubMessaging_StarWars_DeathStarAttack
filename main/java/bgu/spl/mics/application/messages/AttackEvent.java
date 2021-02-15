package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Attack;

public class AttackEvent implements Event<Boolean> {

    private long duration;
    private int[] serials;

    public AttackEvent(Attack attack){
        this.duration = attack.getDuration();
        this.serials = new int[attack.getSerials().size()];
        for (int i = 0 ; i < attack.getSerials().size() ; i++){
            this.serials[i] = attack.getSerials().get(i);
        }
    }

    public long getDuration(){return duration;}
    public int[] getSerials(){return serials;}

}
