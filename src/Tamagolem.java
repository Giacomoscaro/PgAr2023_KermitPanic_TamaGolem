public class Tamagolem {
    public static final int VITA = 10; //vita massima di un tamagolem
    private int vita_golem;
    private Sacchetto sacchetto; //il set di pietre del tamagolem

    public Tamagolem(){
        this.vita_golem = VITA;
        this.sacchetto = null;
    }
    
    public boolean isDead()
    {
        return vita_golem<=0;
    }

    public void setSacchetto(Sacchetto sacchetto) {
        this.sacchetto = sacchetto;
    }

    public Sacchetto getSacchetto(){
        return this.sacchetto;
    }

    /**
     * Sottrae dalla vita del golem la quantità specificata
     */
    public void danno(int danno){
        vita_golem-=danno;
    }
}
