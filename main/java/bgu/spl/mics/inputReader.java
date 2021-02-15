package bgu.spl.mics;

import java.util.List;

import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.services.LandoMicroservice;
import bgu.spl.mics.application.services.R2D2Microservice;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class inputReader {

    @SerializedName("attacks")
    @Expose
    private Attack[] attacks;

    @SerializedName("R2D2")
    @Expose
    private long R2D2;

    @SerializedName("Lando")
    @Expose
    private long lando;

    @SerializedName("Ewoks")
    @Expose
    private int ewoks;


    public Attack[] getAttacks(){return this.attacks;}
    public long getR2D2(){return this.R2D2;}
    public long getLando(){return this.lando;}
    public int getEwoks(){return this.ewoks;}

    public void setAttacks(Attack[] attacks){this.attacks = attacks;}
    public void setR2D2s(long R2D2){this.R2D2 = R2D2;}
    public void setLando(long lando){this.lando = lando;}
    public void setEwoks(int ewoks){this.ewoks = ewoks;}




}
