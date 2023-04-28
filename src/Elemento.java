import it.kibo.fp.lib.RandomDraws;

public class Elemento {
    private static final int ELEMENTO = 5;
    private int potenza;
    public enum Elementi{TERRA("Terra",1),ACQUA("Acqua", 2),FUOCO("Fuoco",3),ARIA("Aria", 4),LUCE("Luce",5);
        private final int indice;
        private String nome;
        Elementi(String nome, int indice){
            this.nome = nome;
            this.indice = indice;
        }

        public int getIndice(){
            return this.indice;
        }
    }
    //creazione della matrice per la creazione dell'equilibrio
    static int matrix[][] = new int[ELEMENTO][ELEMENTO];

    //inserire nella matrice matrix[i][i] = 0;

    public void creaEquilibrio(int matrix[][]){
        for(int i = 0;i<ELEMENTO;i++){
            int ris = 0;
            for(int j=0;j<ELEMENTO;j++){
                if(i==j){
                    matrix[i][j] = 0;
                }else if(j!=ELEMENTO-1){
                    int valore = RandomDraws.drawInteger(1,10);
                    if(RandomDraws.estraiBoolean()) valore=-valore;
                    matrix[i][0] = valore;
                    ris = ris + matrix[i][0];
                }else{
                    matrix[i][j] = ris;
                }
            }
        }

    }

    public int generaPotenza(){
       return potenza = RandomDraws.drawInteger(1,10);
    }
    public int generaRiga(int matrix[]){
        int i;
        for(i=1;i<ELEMENTO-1;i++){
            int valore = RandomDraws.drawInteger(1,10);
            if(RandomDraws.estraiBoolean()) valore=-valore;
            matrix[i] = valore;
        }
        int ris=0;
        for(i=0;i<ELEMENTO-1;i++){
            ris = matrix[i];
        }
        matrix[ELEMENTO] = matrix[ris];

        return matrix[i];
    }
    public int ultimoNumero(int matrix[][]){
        int i;
        int ris=0;
        for(i=0;i<ELEMENTO-1;i++){
            ris = matrix[i][0];
        }
        return ris;
    }

    public Elemento(){

    }

    public String toString(){
        StringBuffer tabella= new StringBuffer();
        for(int i=0; i<ELEMENTO; i++){
            for(int j=0; j<ELEMENTO; j++)
                tabella.append(matrix[i][j] + "\t");

            tabella.append("\n");
            }
        return tabella.toString();
    }

}
