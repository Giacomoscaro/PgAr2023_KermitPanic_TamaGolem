import it.kibo.fp.lib.AnsiColors;
import it.kibo.fp.lib.RandomDraws;

    
public enum Elementi{
    	
	//lista degli elementi
	TERRA("Terra",0, AnsiColors.GREEN_BRIGHT),ACQUA("Acqua", 1, AnsiColors.BLUE_BRIGHT),FUOCO("Fuoco",2, AnsiColors.RED_BRIGHT),ARIA("Aria", 3, AnsiColors.CYAN_BRIGHT),LUCE("Luce",4,AnsiColors.YELLOW_BRIGHT)
	,ROMANO("Romanaccio",5,AnsiColors.BLACK), HIGHGROUND("Highground",6,AnsiColors.WHITE_BRIGHT),PLASMA("Plasma",7,AnsiColors.PURPLE_BRIGHT),SABBIA("Sabbia",8,AnsiColors.YELLOW),FUMO("FUMO",9,AnsiColors.GREEN);


	private final int indice;
	private final String nome;
	private final AnsiColors colore;

	/*
	 * Costruttore di Elementi che specifica nome, indice
	 */
	Elementi(String nome, int indice, AnsiColors colore){
		this.nome = nome;
		this.indice = indice;
		this.colore = colore;
	}
	public static Elementi getElemento(int i){
		switch (i) {
			case 0 -> {
				return TERRA;
			}
			case 1 -> {
				return ACQUA;
			}
			case 2 -> {
				return FUOCO;
			}
			case 3 -> {
				return ARIA;
			}
			case 4 -> {
				return LUCE;
			}
			case 5 -> {
				return ROMANO;
			}
			case 6 -> {
				return HIGHGROUND;
			}
			case 7 -> {
				return PLASMA;
			}
			case 8 -> {
				return SABBIA;
			}
			case 9 -> {
				return FUMO;
			}
			default -> {
				return null;
			}
		}

	}

	/**
	 * Restituisce una stringa formattata con il colore dell'elemento
	 */
	public String toString() {
		return colore + this.nome + AnsiColors.RESET;
	}
	
	/**
     * Restituisce il valore dell'interazione di
     * 		e1 ---> e2
     * @param e1 primo elemento
     * @param e2 secondo elemento
     * @return intero >0 se il secondo elemento è quello debole;
     * intero<0 se il primo elemento è quello debole
     */
    public static int interazione(Elementi e1, Elementi e2, Partita p) {
    	return p.getEquiilbrio()[e1.indice][e2.indice];
    }

	//EQUILIBRIO ----------------------------------------

	//creazione della matrice dell'equilibrio degli elementi

	/**
	 * Crea l'equilibrio dell'Universo
	 * L'equilibrio ottenuto viene utilizzato per
	 * i confronti tra Elementi durante tutta la pratita
	 */
	public static void creaEquilibrio(Partita p) {
		/*
		 * Genera una matrice adiacenza che rappresenta il grafo delle potenze
		 * I valori delle potenze sono rappresentati dai valori positivi, i valori
		 * negativi indicano la potenza inversa
		 * Es Acqua ---- > Fuoco	5
		 *    Fuoco -----> Acqua    -5
		 *    
		 * Per ottenere un grafo corretto, la matrice di adiacenza ha:
		 *  - sulla diagonale tutti zeri
		 *  - è simmetrica
		 *  - il valore assoluto dei valori (non sulla diagonale) è 1 < x < Tamagolem.VITA
		 *  - negli elementi dell'ultima colonna/riga la somma degli elementi precedenti nella riga/colonna
		 *    nelle altre caselle, dei valori casuali
		 */
		int[][] matrix = new int[p.getN_ele()][p.getN_ele()];
		//zeri sulla diagonale
		for(int i=0; i<p.getN_ele(); i++)
			for(int j=0; j<p.getN_ele(); j++)
				if(i==j)
					matrix[i][j]=0;

		for(int i=0; i<p.getN_ele(); i++) {
			int somma=0; //somma da calcolare per l'ultimo elemento della riga
			for(int j=0; j<p.getN_ele(); j++)

				//ultimi elementi delle righe (escluso quello dell'ultima riga)
				if(j==p.getN_ele()-1 && i!=p.getN_ele()-1) {
					/* se il valore da inserire supera la vita del tamagolem oppure è 0
					*  ripeti la generazione della riga corrente
					*/
					if(Math.abs(somma)>=Tamagolem.VITA || Math.abs(somma)==0){
						i=-1; //riporta l'indice alla riga precedente (il ciclo la riporta dopo a quella attuale)
						break; //esci dal ciclo della riga
					}
					matrix[i][j]=-somma;
					matrix[j][i]=somma; //elemento simmetrico
				}else if(j>i) {// elementi sopra la diagonale
					
					// Genera un valore tra -Tamagolem.VITA e Tamagolem.VITA (0 escluso)
					int valore=RandomDraws.drawInteger(1,Tamagolem.VITA);
					if(RandomDraws.estraiBoolean())
						valore= - valore;

					somma+=valore; //aggiorna il valore della somma degli elementi della riga

					matrix[i][j]=valore;
					matrix[j][i]=-valore; //elemento simmetrico
				}else somma+=matrix[i][j]; //elementi sotto la diagonale (si aggiorna solo la somma della riga)
			/*
			 * controlla se la tabella ha almeno una volta un valore massimo (valore=Tamagolem.vita)
			 * Se non lo trova ripete la generazione della tabella
			*/
			if(i==p.getN_ele()-1){
				boolean max=false;
				for(int i1=0; i1<p.getN_ele();i1++)
					for(int j1=0; j1<p.getN_ele(); j1++)
						if(matrix[i1][j1]==Tamagolem.VITA){
							max=true;
							break;
						}
				if(!max){
					i=-1;
				}
			}
		}
		p.setEquiilbrio(matrix); //imposta la matrice equilibrio per la partita indicata in input
	}

	/*
	 * Ritorna una stringa formattata che rappresenta l'equilibrio
	 * Per ogni elemento sono visualizzati i valori delle iterazioni
	 * con gli altri elementi
	 */
	/*
	 * Nota: toString non si poteva utilizzare perchè
	 * definita come funzione non static
	 */
	public static String getStringEquilibrio(int[][] matrix){
		StringBuffer output= new StringBuffer();
		for(int i=0; i<matrix.length; i++){
			output.append(getElemento(i).toString() + ":\n");
			for(int j=0; j<matrix.length; j++){

				//recupera dall'equilibrio solo i valori positivi e negativi delle interazioni tra elementi
				if(matrix[i][j]>0){
					output.append(AnsiColors.GREEN_BOLD_BRIGHT + "+" + matrix[i][j] + "\t" +  getElemento(j).toString() + "\n");
				}else if(matrix[i][j]<0){
					output.append(AnsiColors.RED_BOLD_BRIGHT  +""+ matrix[i][j] + "\t" +  getElemento(j).toString() + "\n");
				}
			}

			output.append("\n");
		}
		return output.toString();
	}
} 