import it.kibo.fp.lib.RandomDraws;

public class Main {
    public static void main(String[] args) {
        Elementi.creaEquilibrio2();
        System.out.println(Elementi.getStringEquilibrio());
        
        Pietra p1= new Pietra(Elementi.ACQUA);
        Pietra p2= new Pietra(Elementi.FUOCO);
        System.out.println("Elemento 1: " + p1.getElemento().toString() + "\tElemento 2: "+ p2.getElemento().toString() );
        
    }
}