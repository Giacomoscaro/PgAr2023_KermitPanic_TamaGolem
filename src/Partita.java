import it.kibo.fp.lib.InputData;

import java.util.ArrayList;
import java.util.HashMap;

public class Partita {

    private Giocatore g1 = new Giocatore();
    private Giocatore g2 = new Giocatore();
    //private HashMap<Elementi, Integer> scorta = new HashMap<>(){}; //S = ⎡(2 * G * P) / N⎤ * N
    private ArrayList<Pietra> scorta = new ArrayList<>();
    public Partita(){
        this.g1 = new Giocatore();
        this.g2 = new Giocatore();

        Elementi.creaEquilibrio();
        this.numeroTama();
        this.creaScorta();
    }
    public void creaPartita(){
        System.out.println("Inizia la partita");

        g1.setNome(InputData.readNonEmptyString("Inserisci il nome del giocatore 1: ", true));
        g2.setNome(InputData.readNonEmptyString("Inserisci il nome del giocatore 2: ", true));

        creaSet(g1);
        creaSet(g2);

        do{


            scontro(g1.getTeam().get(0), g2.getTeam().get(0));
            if(g1.getTeam().get(0).isDead()) {
                morteTamagolem(g1);
            }
            else {
                morteTamagolem(g2);
            }

        }while(g1.getTeam().size()>0 && g2.getTeam().size()>0);
        if(g1.getTeam().size()>0){
            System.out.println("Il giocatore " + g1.getNome() + " ha vinto!");
        }
        else {
            System.out.println("Il giocatore " + g2.getNome() + " ha vinto");
        }
    }
    public void creaScorta(){
        int s = (((2*g1.getTeam().size()*Sacchetto.DIM_SACCHETTO)/Elementi.N_ELEMENTI)+1)*Elementi.N_ELEMENTI;
        for(int i=0;i<Elementi.N_ELEMENTI;i++){
            for(int j=0;j<(s/Elementi.N_ELEMENTI);j++){
                scorta.add(new Pietra(Elementi.getElemento(i)));
            }
        }
    }
    public void stampaScorta(){
        for(Pietra p : scorta){
            System.out.println(scorta.indexOf(p)+1  + "\t" + p.getElemento().toString());
        }
    }
    public void creaSet(Giocatore giocatore){
        ArrayList<Pietra> pietre = new ArrayList<>();
        System.out.println("Selezione" + Sacchetto.DIM_SACCHETTO + "Pietre dalla scorta per creare il set");
        for(int i = 0; i<Sacchetto.DIM_SACCHETTO;i++){
            stampaScorta();
            int n = InputData.readInteger(giocatore.getNome() + " che pietra vuoi: ");
            while(n<1 || n> scorta.size()){
                n=InputData.readInteger("Inserisci una pietra della scorta");
            }
            pietre.add(scorta.get(n-1));
            scorta.remove(n-1);
        }
        giocatore.getTeam().get(0).setSacchetto(new Sacchetto(pietre));
    }
    public void numeroTama(){
        int num_golem_giocatore = InputData.readIntegerBetween("Inserire numero di tamagolem per giocatore: ", 1, 20);
        for(int i=0;i<num_golem_giocatore;i++) {
            g1.getTeam().add(new Tamagolem());
            g2.getTeam().add(new Tamagolem());
        }
    }

    public void scontro(Tamagolem t1, Tamagolem t2){
        do{
            int potenza = Elementi.interazione(t1.getSacchetto().usaPietra().getElemento(), t2.getSacchetto().usaPietra().getElemento());
            if(potenza<0){
                t1.danno(Math.abs(potenza));
                esito(g1,Math.abs(potenza));
            }
            else if(potenza>0){
                    t2.danno(Math.abs(potenza));
                    esito(g2,Math.abs(potenza));
            }
            else {System.out.println("Nessun golem ha preso danno!");} //potenza==0

        }while(!(t1.isDead() || t2.isDead()));
    }

    public void morteTamagolem(Giocatore giocatore){
        giocatore.getTeam().remove(0);
        if(giocatore.getTeam().size()>0){
            creaSet(giocatore);
        }
        else {
            System.out.println("Il giocatore " + giocatore.getNome() + " ha finito i Tamagolem");
        }
    }

    public void esito(Giocatore giocatore, int danno){
        System.out.println("Il golem di " + giocatore.getNome() + " ha preso " + danno + " di danno");

        if(giocatore.getTeam().get(0).isDead()){
            System.out.println("Il Tamagolem di " + giocatore.getNome() + " è morto!");
        }

    }
}
