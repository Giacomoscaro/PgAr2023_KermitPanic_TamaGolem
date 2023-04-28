import it.kibo.fp.lib.RandomDraws;

public class Elemento {
    private static final int ELEMENTO = 5;
    private int potenza;
    
    public Elemento(){
    	
    }
    
    /**
     * Elenco elementi presenti nel gioco
     * Non è detto che vengano tutti utilizzati nel gioco
     *
     */
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

    public void creaEquilibrio(){
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
    
    /**
     * Crea l'equilibrio dell'Universo
     * L'equilibrio ottenuto viene utilizzato per
     * i confronti tra Elementi durante tutta la pratita
     */
    public static void creaEquilibrio2() {
    	/*
    	 * Genera una matrice adiacenza che rappresenta il grafo delle potenze
    	 * I valori delle potenze sono rappresentati dai valori positivi, i valori
    	 * negativi indicano la potenza inversa
    	 * Es Acqua ---- > Fuoco	5
    	 *    Fuoco -----> Acqua    -5
    	 *    
    	 * Per ottenere un grafo corretto, la matrice di adiacenza ha:
    	 * 	sulla diagonale tutti zeri
    	 *  è simmetrica
    	 *  negli elementi dell'ultima colonna/riga la somma degli elementi precedenti nella riga/colonna
    	 *  nelle altre caselle, dei valori casuali	
    	 */
    	
    	//zeri sulla diagonale
    	for(int i=0; i<ELEMENTO; i++)
    		for(int j=0; j<ELEMENTO; j++)
    			if(i==j)
    				matrix[i][j]=0;
    	
    	for(int i=0; i<ELEMENTO; i++) {
    		int somma=0; //somma da usare per l'ultimo elemento della riga
    		for(int j=i+1; j<ELEMENTO; j++)
    			if(j==ELEMENTO-1) {
    				matrix[i][j]=-somma;
    				matrix[j][i]=somma; //elemento simmetrico
    			}else {
    				int valore=RandomDraws.drawInteger(1,10);
    				if(RandomDraws.estraiBoolean())
    					valore= - valore;
    				
    				somma+=valore;
    				
    				matrix[i][j]=valore;
    				matrix[j][i]=-valore; //elemento simmetrico
    			}
    	}
    }

    /*
     * Ritorna una stringa che rappresenta l'equilibrio
     */
    /*
     * Nota: toString non si poteva utilizzare perchè
     * definita come funzione non static
     */
    public static String getStringEquilibrio(){
        StringBuffer tabella= new StringBuffer();
        for(int i=0; i<ELEMENTO; i++){
            for(int j=0; j<ELEMENTO; j++)
                tabella.append(matrix[i][j] + "\t");

            tabella.append("\n");
            }
        return tabella.toString();
    }

}
