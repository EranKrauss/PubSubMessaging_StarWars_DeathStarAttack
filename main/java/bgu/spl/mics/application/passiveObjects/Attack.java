package bgu.spl.mics.application.passiveObjects;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Passive data-object representing an attack object.
 * You must not alter any of the given public methods of this class.
 * <p>
 * YDo not add any additional members/method to this class (except for getters).
 */
public class Attack {

    @SerializedName("serials")
    @Expose
    final List<Integer> serials;

    @SerializedName("duration")
    @Expose
    final int duration;

    /**
     * Constructor.
     */
    public Attack(List<Integer> serialNumbers, int duration) {
        this.serials = serialNumbers;
        this.duration = duration;
    }

    public int getDuration(){return this.duration;}
    public List<Integer> getSerials(){return this.serials;}
}
