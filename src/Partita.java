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
    }
    public void creaPartita(){

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
            System.out.println(p.getElemento().toString());
        }
    }
    public void numeroTama(){
        int num_golem_giocatore = InputData.readIntegerBetween("Inserire numero di tamagolem per giocatore: ", 1, 20);
        for(int i=0;i<num_golem_giocatore;i++) {
            g1.getTeam().add(new Tamagolem(Tamagolem.VITA, null));
            g2.getTeam().add(new Tamagolem(Tamagolem.VITA, null));
        }
    }


}
