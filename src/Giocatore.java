import java.util.ArrayList;

public class Giocatore {

    //conterra un arraylist di golem e il relativo sacchetto
    private ArrayList<Tamagolem> team = new ArrayList<>();
    private String nome;

    public Giocatore(){
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
}
