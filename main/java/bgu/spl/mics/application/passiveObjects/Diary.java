package bgu.spl.mics.application.passiveObjects;


/**
 * Passive data-object representing a Diary - in which the flow of the battle is recorded.
 * We are going to compare your recordings with the expected recordings, and make sure that your output makes sense.
 * <p>
 * Do not add to this class nothing but a single constructor, getters and setters.
 */
public class Diary {
    private int totalAttacks;
    private long HanSoloFinish;
    private long C3POFinish;
    private long R2D2Deactivate;
    private long LeiaTerminate;
    private long HanSoloTerminate;
    private long C3POTerminate;
    private long R2D2Terminate;
    private long LandoTerminate;

    private static class DiaryHolder{
        private static Diary instance = new Diary();
    }

    public static Diary getDiaryInstance() {
        return DiaryHolder.instance;
    }

    private Diary(){
        this.totalAttacks = 0;
        this.HanSoloFinish = 0;
        this.C3POFinish = 0;
        this.R2D2Deactivate = 0;
        this.LeiaTerminate = 0;
        this.HanSoloTerminate = 0;
        this.C3POTerminate = 0;
        this.R2D2Terminate = 0;
        this.LandoTerminate = 0;
    }

    public int getTotalAttacks(){return totalAttacks;}
    public long getHanSoloFinish(){return HanSoloFinish;}
    public long getC3POFinish(){return C3POFinish;}
    public long getR2D2Deactivate(){return R2D2Deactivate;}
    public long getLeiaTerminate(){return LeiaTerminate;}
    public long getHanSoloTerminate(){return HanSoloTerminate;}
    public long getC3POTerminate(){return C3POTerminate;}
    public long getR2D2Terminate(){return R2D2Terminate;}
    public long getLandoTerminate(){return LandoTerminate;}

    public synchronized void increaseTotallAttack(){
        this.totalAttacks++;
    }
    public void setHanSoloFinish(){this.HanSoloFinish = System.currentTimeMillis();}
    public void setC3POFinish(){this.C3POFinish = System.currentTimeMillis();}
    public void setR2D2Deactivate(){this.R2D2Deactivate = System.currentTimeMillis();}
    public void setLeiaTerminate(){this.LeiaTerminate = System.currentTimeMillis();}
    public void setHanSoloTerminate(){this.HanSoloTerminate = System.currentTimeMillis();}
    public void setC3POTerminate(){this.C3POTerminate = System.currentTimeMillis();}
    public void setR2D2Terminate(){this.R2D2Terminate = System.currentTimeMillis();}
    public void setLandoTerminate(){this.LandoTerminate = System.currentTimeMillis();}


    //for test
    public void printForTest() {
        System.out.println("**********output in the diary will be:************");
        System.out.println("totall attacks: " + totalAttacks);
        System.out.println("han solo finish: " + HanSoloFinish);
        System.out.println("c3po finish: " + C3POFinish);
        System.out.println("terminate c3po: " + C3POTerminate);
        System.out.println("terminate han solo: " + HanSoloTerminate);
        System.out.println("terminate r2d2: " + R2D2Terminate);
        System.out.println("terminate leia: " + LeiaTerminate);
        System.out.println("terminate lando: " + LandoTerminate);
    }

}
