import it.kibo.fp.lib.AnsiColors;
import it.kibo.fp.lib.InputData;
import it.kibo.fp.lib.RandomDraws;

import java.util.ArrayList;

public class Partita {

    private Giocatore g1;
    private Giocatore g2;
    
    private ArrayList<Pietra> scorta = new ArrayList<>(); //pietre della scorta comune
    
    private int n_ele;
    private int dim_sacch;
    private int[][] equilibrio; //matrice dell'equilibrio
    
    /**
     *  Prepara il necessario per svolgere la partita:
     *   - definizione del numero di elementi da usare, della quantità di pietre usate dai tamagolem, del numero di tamagolem
     *   - creazione dell'equilibrio e della scorta   
     */
    public Partita(){
        this.g1 = new Giocatore(AnsiColors.RED_BOLD_BRIGHT);
        this.g2 = new Giocatore(AnsiColors.BLUE_BOLD_BRIGHT);

        this.n_ele = InputData.readIntegerBetween(AnsiColors.PURPLE_BRIGHT + "Inserire numero elementi da 4 a 10: " +AnsiColors.RESET, 4, 10);
        this.equilibrio = new int[n_ele][n_ele];
        this.dim_sacch = (int) Math.ceil((double)(n_ele+1)/3)+1;

        Elementi.creaEquilibrio(this);
        this.numeroTama();
        this.creaScorta();
    }

    public int[][] getEquilibrio() {
        return equilibrio;
    }

    public void setEquiilbrio(int[][] equiilbrio) {
        this.equilibrio = equiilbrio;
    }
    /**
     * Esegue la partita
     */
    public void creaPartita(){

        System.out.println(AnsiColors.PURPLE_BRIGHT + "Inizia la partita".toUpperCase() + AnsiColors.RESET);
        g1.setNome(InputData.readNonEmptyString(AnsiColors.PURPLE_BRIGHT + "Inserisci il nome del giocatore 1: " + AnsiColors.RESET, true));
        g2.setNome(InputData.readNonEmptyString(AnsiColors.PURPLE_BRIGHT + "Inserisci il nome del giocatore 2: " + AnsiColors.RESET, true));

        //inizializza i primi set di pietre per i tamagolem
        creaSet(g1);
        creaSet(g2);

        do{
            scontro(g1.getTeam().get(0), g2.getTeam().get(0));
            if(g1.getTeam().get(0).isDead()) { //controlla di chi è il tamagolem morto
                morteTamagolem(g1);
            }
            else{
                morteTamagolem(g2);
            }

        }while(g1.getTeam().size()>0 && g2.getTeam().size()>0);
        if(g1.getTeam().size()>0){
            System.out.println("Il giocatore " + g1 + " ha vinto!");
        }
        else {
            System.out.println("Il giocatore " + g2 + " ha vinto!");
        }
        System.out.println();
        tempo();
        System.out.println(Elementi.getStringEquilibrio(getEquilibrio())); //stampa l'equilibrio
    }
    
    /**
     *  Crea la scorta di pietre condivisa tra i due giocatori
     */
    public void creaScorta(){
        double s = Math.ceil(((double)2*g1.getTeam().size()*dim_sacch/n_ele))*n_ele;
        for(int i=0;i<n_ele;i++){
            for(int j=0;j<(s/n_ele);j++){
                scorta.add(new Pietra(Elementi.getElemento(i)));
            }
        }
    }
    
    /**
     * Stampa l'elenco delle pietre dentro la scorta
     */
    public void stampaScorta(){
        for(Pietra p : scorta){
            //Thread.sleep(MILLIS2);
            System.out.println(scorta.indexOf(p)+1  + "\t" + p.getElemento().toString());
        }
    }
    
    /**
     * Costruisce un set di pietre per il tamagolem corrente
     * @param giocatore il giocatore che deve scegliere le pietre
     */
    public void creaSet(Giocatore giocatore) {
        ArrayList<Pietra> pietre = new ArrayList<>();
        //Thread.sleep(MILLIS);
        System.out.println(AnsiColors.GREEN + "Selezione " + dim_sacch + " Pietre dalla scorta per creare il set" + AnsiColors.RESET);
        for(int i = 0; i<dim_sacch;i++){
            stampaScorta();
            int n = InputData.readInteger( giocatore.toString() + " che pietra vuoi: ");
            while(n<1 || n> scorta.size()){
                n=InputData.readInteger("Inserisci una pietra valida della scorta");
            }
            pietre.add(scorta.get(n-1));
            scorta.remove(n-1);
        }
        giocatore.getTeam().get(0).setSacchetto(new Sacchetto(pietre, this));
    }
    
    /*
     * Inizializza i tamagolem dei due giocatori
     */
    public void numeroTama(){
        int num_golem_giocatore = (int)Math.ceil(((double)n_ele-1)*(n_ele-2)/(2*dim_sacch));
        //int num_golem_giocatore = InputData.readIntegerBetween(AnsiColors.PURPLE + "Inserire numero di tamagolem per giocatore: " + AnsiColors.RESET, 1, 20);
        for(int i=0;i<num_golem_giocatore;i++) {
            g1.getTeam().add(new Tamagolem());
            g2.getTeam().add(new Tamagolem());
        }
    }

    /**
     * Gestisce lo scontro tra due tamagolem
     * @param t1 il tamagolem corrente del giocatore 1
     * @param t2 il tamagolem corrente del giocatore 2
     */
    public void scontro(Tamagolem t1, Tamagolem t2){
        int turni = 0;
        do{
            int potenza = Elementi.interazione(t1.getSacchetto().usaPietra().getElemento(), t2.getSacchetto().usaPietra().getElemento(),this);
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
            else { //potenza==0
                tempo();
                //Thread.sleep(MILLIS);
                System.out.println(".\tNessun golem(" + g1.getTeam().get(0).getSacchetto().getPietre().getLast().getElemento().toString() + ") ha preso danno!\n.");
            }
            turni ++;
            /*
             * Per evitare che i tamagolem combattano all'infinito, dopo molti turni in pareggio,
             * si smette di usare il combattimento con le pietre.
             * I tamagolem infliggono danni limitati (anche 0) fino a quando uno dei due non viene sconfitto
             */
            if(turni==15) {
                System.out.println(AnsiColors.PURPLE_BRIGHT + "I due golem hanno finito le forze per usare le pietre e stanno cominciando a fare a pugni" + AnsiColors.RESET + "\n.");
                do{
                    tempo();
                    int danno1= RandomDraws.drawInteger(0, Tamagolem.VITA/5);
                    t1.danno(danno1);
                    System.out.println(".\tIl golem di " + g1 + " prende " + danno1 + " di danno!\n.");
                    if(!t1.isDead())
                    {
                        tempo();
                        int danno2= RandomDraws.drawInteger(0, Tamagolem.VITA/5);
                        t2.danno(danno2);
                    System.out.println(".\tIl golem di " + g2.toString() + " prende " + danno2 + " di danno!\n.");
                    }
                }while(!(t1.isDead() || t2.isDead()));
                if (t1.isDead()){
                    System.out.println("Il tamagolem di "+ g1 + " é andato KO");
                    System.out.println("Tamagolem rimasti:\n" + g1 + ": " + (g1.getTeam().size()-1) + " ;\n" + g2.toString() + ": " + g2.getTeam().size() + " ;");
                } else if (t2.isDead()) {
                    System.out.println("Il tamagolem di "+ g2.toString() + " é andato KO");
                    System.out.println("Tamagolem rimasti:\n" + g2 + ": " + (g2.getTeam().size()-1) + " ;\n" + g1 + ": " + g1.getTeam().size() + " ;");
                }

            }
        }while(!(t1.isDead() || t2.isDead()));
    }

    /**
     * Esegue alcune azioni da fare dopo la morte di un tamagolem
     * @param giocatore il giocatore a cui è morto il tamagolem
     */
    public void morteTamagolem(Giocatore giocatore){
        giocatore.getTeam().remove(0);
        if(giocatore.getTeam().size()>0){
            creaSet(giocatore);
        }
        else {
            System.out.println("Il giocatore " + giocatore + " ha finito i Tamagolem");
        }
    }

    /**
     * Visualizza gli elementi delle pietre usate dai tamagolem e 
     * @param g1 il giocatore con il tamagolem che ha subito danno
     * @param g2 l'altro giocatore
     * @param danno danno inflitto nell'ultimo turno
     */
    public void esito(Giocatore g1, Giocatore g2, int danno){
        tempo();
        System.out.println(".\tIl golem(" + g1.getTeam().get(0).getSacchetto().getPietre().getLast().getElemento().toString() + ") di " + g1 +  " ha preso " + danno + " di danno(" + g2.getTeam().get(0).getSacchetto().getPietre().getLast().getElemento().toString() + ")\n.");

        if(g1.getTeam().get(0).isDead()){
            tempo();
            System.out.println("Il Tamagolem di " + g1 + " è morto!");
            System.out.println("Tamagolem rimasti:\n" + g1 + ": " + (g1.getTeam().size()-1) + " ;\n" + g2 + ": " + g2.getTeam().size() + " ;");
        }

    }
    
    //ferma l'esecuzione per 1s
    public void tempo(){
        try {
            Thread.sleep(1000);  // 1000 milliseconds = 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getN_ele() {
        return n_ele;
    }

    public int getDim_sacch() {
        return dim_sacch;
    }
}
