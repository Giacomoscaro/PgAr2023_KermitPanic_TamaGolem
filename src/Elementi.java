import it.kibo.fp.lib.RandomDraws;

    
public enum Elementi{
    	
	//lista degli elementi
	TERRA("Terra",0),ACQUA("Acqua", 1),FUOCO("Fuoco",2),ARIA("Aria", 3),LUCE("Luce",4);

	private static final int N_ELEMENTI = 5; //numero di elementi da usare durante il gioco

	private final int indice;
	private String nome;

	Elementi(String nome, int indice){
		this.nome = nome;
		this.indice = indice;
	}

	public int getIndice(){
		return this.indice;
	}

	public String toString() {
		return this.nome;
	}
	
	/**
     * Restituisce il valore dell'interazione di
     * 		e1 ---> e2
     * @param e1 primo elemento
     * @param e2 secondo elemento
     * @return intero >0 se il secondo elemento è quello debole;
     * intero<0 se il primo elemento è quello debole
     */
    public static int interazione(Elementi e1, Elementi e2) {
    	return matrix[e1.indice][e2.indice];
    }

	//EQUILIBRIO ----------------------------------------

	//creazione della matrice per la creazione dell'equilibrio
	private static int matrix[][] = new int[N_ELEMENTI][N_ELEMENTI];

	//inserire nella matrice matrix[i][i] = 0;

	public void creaEquilibrio(){
		for(int i = 0;i<N_ELEMENTI;i++){
			int ris = 0;
			for(int j=0;j<N_ELEMENTI;j++){
				if(i==j){
					matrix[i][j] = 0;
				}else if(j!=N_ELEMENTI-1){
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
		for(i=1;i<N_ELEMENTI-1;i++){
			int valore = RandomDraws.drawInteger(1,10);
			if(RandomDraws.estraiBoolean()) valore=-valore;
			matrix[i] = valore;
		}
		int ris=0;
		for(i=0;i<N_ELEMENTI-1;i++){
			ris = matrix[i];
		}
		matrix[N_ELEMENTI] = matrix[ris];

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
		for(int i=0; i<N_ELEMENTI; i++)
			for(int j=0; j<N_ELEMENTI; j++)
				if(i==j)
					matrix[i][j]=0;

		for(int i=0; i<N_ELEMENTI; i++) {
			int somma=0; //somma da usare per l'ultimo elemento della riga
			for(int j=i+1; j<N_ELEMENTI; j++)
				if(j==N_ELEMENTI-1) {
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
		for(int i=0; i<N_ELEMENTI; i++){
			for(int j=0; j<N_ELEMENTI; j++)
				tabella.append(matrix[i][j] + "\t");

			tabella.append("\n");
		}
		return tabella.toString();
	}
} 