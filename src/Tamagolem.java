import it.kibo.fp.lib.InputData;

import java.util.ArrayList;

public class Tamagolem {
    public static final int VITA = 10;
    private int vita_golem = VITA;
    private Sacchetto sacchetto;
    public Tamagolem(){
        this.vita_golem = VITA;
        this.sacchetto = null;
    }
    public boolean isDead()
    {
        return vita_golem<=0;
    }

    public void setSacchetto(Sacchetto sacchetto) {
        this.sacchetto = sacchetto;
    }

    public Sacchetto getSacchetto(){
        return this.sacchetto;
    }

    public void danno(int danno){
        vita_golem-=danno;
    }
}
