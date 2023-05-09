import it.kibo.fp.lib.AnsiColors;
import it.kibo.fp.lib.InputData;

public class Main {

    public static void main(String[] args){
        boolean scelta = true;

        while(scelta) {
            Partita p3 = new Partita();
            p3.creaPartita();
            scelta = InputData.readYesOrNo(AnsiColors.PURPLE_BRIGHT + "Vuoi giocare ancora");
            System.out.println(AnsiColors.RESET);
        }
    }
}