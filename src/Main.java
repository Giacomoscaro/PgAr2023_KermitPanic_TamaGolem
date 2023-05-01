import java.util.ArrayList;

import it.kibo.fp.lib.RandomDraws;

public class Main {


    public static void main(String[] args) {
        Elementi.creaEquilibrio2();
        System.out.println(Elementi.getStringEquilibrio());

        Partita p3 = new Partita();
        p3.numeroTama();
        p3.creaScorta();
        p3.stampaScorta();
    }
}