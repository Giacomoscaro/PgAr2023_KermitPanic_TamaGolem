import it.kibo.fp.lib.InputData;
import it.kibo.fp.lib.AnsiColors;
import it.kibo.fp.lib.RandomDraws;

import java.util.ArrayList;
import java.util.HashMap;

public class Partita {


    public static final int MILLIS = 1000;
    public static final int MILLIS2 = 50;
    private Giocatore g1;
    private Giocatore g2;
    //private HashMap<Elementi, Integer> scorta = new HashMap<>(){}; //S = ⎡(2 * G * P) / N⎤ * N
    private ArrayList<Pietra> scorta = new ArrayList<>();
    public Partita(){
        this.g1 = new Giocatore(AnsiColors.RED_BOLD_BRIGHT);
        this.g2 = new Giocatore(AnsiColors.BLUE_BOLD_BRIGHT);

        Elementi.creaEquilibrio();
        this.numeroTama();
        this.creaScorta();
    }
    public void creaPartita(){
        System.out.println(AnsiColors.PURPLE_BRIGHT + "Inizia la partita".toUpperCase() + AnsiColors.RESET);

        g1.setNome(InputData.readNonEmptyString(AnsiColors.PURPLE_BRIGHT + "Inserisci il nome del giocatore 1: " + AnsiColors.RESET, true));
        g2.setNome(InputData.readNonEmptyString(AnsiColors.PURPLE_BRIGHT + "Inserisci il nome del giocatore 2: " + AnsiColors.RESET, true));

        creaSet(g1);
        creaSet(g2);

        do{
            scontro(g1.getTeam().get(0), g2.getTeam().get(0));
            if(g1.getTeam().get(0).isDead()) {
                morteTamagolem(g1);
            }
            else{
                morteTamagolem(g2);
            }

        }while(g1.getTeam().size()>0 && g2.getTeam().size()>0);
        if(g1.getTeam().size()>0){
            System.out.println("Il giocatore " + g1.toString() + " ha vinto!");
        }
        else {
            System.out.println("Il giocatore " +g2.toString() + " ha vinto!");
        }
        System.out.println();
        System.out.println(Elementi.getStringEquilibrio());
    }
    public void creaScorta(){
        double s = (Math.ceil(((2*g1.getTeam().size()*Sacchetto.DIM_SACCHETTO)/Elementi.N_ELEMENTI)))*Elementi.N_ELEMENTI;
        for(int i=0;i<Elementi.N_ELEMENTI;i++){
            for(int j=0;j<(s/Elementi.N_ELEMENTI);j++){
                scorta.add(new Pietra(Elementi.getElemento(i)));
            }
        }
    }
    public void stampaScorta(){
        for(Pietra p : scorta){
            //Thread.sleep(MILLIS2);
            System.out.println(scorta.indexOf(p)+1  + "\t" + p.getElemento().toString());
        }
    }
    public void creaSet(Giocatore giocatore) {
        ArrayList<Pietra> pietre = new ArrayList<>();
        //Thread.sleep(MILLIS);
        System.out.println(AnsiColors.GREEN + "Selezione " + (int)Sacchetto.DIM_SACCHETTO + " Pietre dalla scorta per creare il set" + AnsiColors.RESET);
        for(int i = 0; i<Sacchetto.DIM_SACCHETTO;i++){
            stampaScorta();
            int n = InputData.readInteger( giocatore.toString() + " che pietra vuoi: ");
            while(n<1 || n> scorta.size()){
                n=InputData.readInteger("Inserisci una pietra valida della scorta");
            }
            pietre.add(scorta.get(n-1));
            scorta.remove(n-1);
        }
        giocatore.getTeam().get(0).setSacchetto(new Sacchetto(pietre));
    }
    public void numeroTama(){
        double num_golem_giocatore = Math.ceil((Elementi.N_ELEMENTI-1)*(Elementi.N_ELEMENTI-2)/(2*Sacchetto.DIM_SACCHETTO));
        //int num_golem_giocatore = InputData.readIntegerBetween(AnsiColors.PURPLE + "Inserire numero di tamagolem per giocatore: " + AnsiColors.RESET, 1, 20);
        for(int i=0;i<num_golem_giocatore;i++) {
            g1.getTeam().add(new Tamagolem());
            g2.getTeam().add(new Tamagolem());
        }
    }

    public void scontro(Tamagolem t1, Tamagolem t2){
        int turni = 0;
        do{
            int potenza = Elementi.interazione(t1.getSacchetto().usaPietra().getElemento(), t2.getSacchetto().usaPietra().getElemento());
            if(potenza<0){
                t1.danno(Math.abs(potenza));
                tempo();
                //Thread.sleep(MILLIS);
                esito(g1, g2, Math.abs(potenza));
            }
            else if(potenza>0){
                    t2.danno(Math.abs(potenza));
                    tempo();
                    //Thread.sleep(MILLIS);
                    esito(g2, g1, Math.abs(potenza));
            }
            else {
                tempo();
                //Thread.sleep(MILLIS);
                System.out.println(".\tNessun golem(" + g1.getTeam().get(0).getSacchetto().getPietre().getLast().getElemento().toString() + ") ha preso danno!\n.");
            }
            //potenza==0
            turni ++;
            if(turni==20) {
                System.out.println(AnsiColors.PURPLE_BRIGHT + "I due golem hanno finito le forze per usare le pietre e stanno cominciando a fare a pugni" + AnsiColors.RESET + "\n.");
                do{
                    tempo();
                    t1.danno(Tamagolem.VITA / 5);
                    System.out.println(".\tIl golem di " + g1.toString() + " prende " + Tamagolem.VITA/5 + " di danno!\n.");
                    if(!t1.isDead())
                    {
                        tempo();
                        t2.danno(Tamagolem.VITA / 5);
                    System.out.println(".\tIl golem di " + g2.toString() + " prende " + Tamagolem.VITA/5 + " di danno!\n.");
                    }
                }while(!(t1.isDead() || t2.isDead()));
            }
        }while(!(t1.isDead() || t2.isDead()));
    }

    public void morteTamagolem(Giocatore giocatore){
        giocatore.getTeam().remove(0);
        if(giocatore.getTeam().size()>0){
            creaSet(giocatore);
        }
        else {
            System.out.println("Il giocatore " +giocatore.toString() + " ha finito i Tamagolem");
        }
    }

    public void esito(Giocatore g1, Giocatore g2, int danno){
        tempo();
        System.out.println(".\tIl golem(" + g1.getTeam().get(0).getSacchetto().getPietre().getLast().getElemento().toString() + ") di " + g1.toString() +  " ha preso " + danno + " di danno(" + g2.getTeam().get(0).getSacchetto().getPietre().getLast().getElemento().toString() + ")\n.");

        if(g1.getTeam().get(0).isDead()){
            tempo();
            System.out.println("Il Tamagolem di " +  g1.toString() + " è morto!");
            System.out.println("Tamagolem rimasti:\n" + g1.toString() + ": " + (g1.getTeam().size()-1) + " ;\n" + g2.toString() + ": " + g2.getTeam().size() + " ;");
        }

    }
    public void tempo(){
        try {
            Thread.sleep(1000);  // 1000 milliseconds = 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
