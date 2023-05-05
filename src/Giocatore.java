import java.util.ArrayList;
import it.kibo.fp.lib.AnsiColors;

public class Giocatore {

    //conterra un arraylist di golem e il relativo sacchetto
    private ArrayList<Tamagolem> team = new ArrayList<>();
    private String nome;
    private AnsiColors colore;

    public Giocatore(AnsiColors colore){
        this.colore=colore;
    }
    public ArrayList<Tamagolem> getTeam() {
        return team;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
    public AnsiColors getColore() {
        return colore;
    }
    public String toString(){
        return colore +  this.nome.toUpperCase() + AnsiColors.RESET;
    }
    
    /* valutare se fare una funzione toString come per Elementi
     * per semplificare il codice in Partita
     * 
     * public String toString(){
     * 	return colore + nome + AnsiColors.RESET;
     * }
     */
}
