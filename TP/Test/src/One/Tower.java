package One;

import java.util.concurrent.Semaphore;

public class Tower {
    private int bahnenNR;
    private Semaphore bahnen;
    private int[] free;
    public Tower(int bahnenNR) {
        this.bahnenNR = bahnenNR;
        this.bahnen = new Semaphore(bahnenNR);
        this.free = new int[bahnenNR];
    }

    public Semaphore getBahnen() {
        return bahnen;
    }
}
