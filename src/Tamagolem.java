import it.kibo.fp.lib.InputData;

import java.util.ArrayList;

public class Tamagolem {
    public static final int VITA = 10;
    private int vita_golem = VITA;
    private Sacchetto sacchetto;
    public Tamagolem(int vita_golem, Sacchetto sacchetto){
        this.vita_golem = VITA;
        this.sacchetto = null;
    }
    public void isDead(){
        System.out.println("Il Tamagolem è morto");
    }

}
