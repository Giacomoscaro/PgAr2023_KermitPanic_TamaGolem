import java.util.ArrayList;

import it.kibo.fp.lib.RandomDraws;

public class Main {
    public static void main(String[] args) {
        Elementi.creaEquilibrio2();
        System.out.println(Elementi.getStringEquilibrio());
        
        Pietra p1= new Pietra(Elementi.ACQUA);
        Pietra p2= new Pietra(Elementi.FUOCO);
        System.out.println("Pietra 1: " + p1.getElemento().toString() + "\tPietra 2: "+ p2.getElemento().toString() );
        
        ArrayList<Pietra>set1=new ArrayList<Pietra>();
        set1.add(p1);
        set1.add(p2);
        set1.add(new Pietra(Elementi.LUCE));
        Sacchetto sacchetto=new Sacchetto(set1);
        System.out.println(sacchetto);
        sacchetto.usaPietra();
        System.out.println(sacchetto);
        sacchetto.usaPietra();
        System.out.println(sacchetto);
    }
}