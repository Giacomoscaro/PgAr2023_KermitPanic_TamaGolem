import it.kibo.fp.lib.InputData;
import it.kibo.fp.lib.RandomDraws;
import it.kibo.fp.lib.AnsiColors;

    
public enum Elementi{
    	
	//lista degli elementi
	TERRA("Terra",0, AnsiColors.GREEN_BRIGHT),ACQUA("Acqua", 1, AnsiColors.BLUE_BRIGHT),FUOCO("Fuoco",2, AnsiColors.RED_BRIGHT),ARIA("Aria", 3, AnsiColors.CYAN_BRIGHT),LUCE("Luce",4,AnsiColors.YELLOW_BRIGHT)
	,ROMANO("Romanaccio",5,AnsiColors.BLACK), HIGHGROUND("Highground",6,AnsiColors.WHITE_BRIGHT),PLASMA("Plasma",7,AnsiColors.PURPLE_BRIGHT),SABBIA("Sabbia",8,AnsiColors.YELLOW),FUMO("FUMO",9,AnsiColors.GREEN);

	public static final int N_ELEMENTI = InputData.readIntegerBetween(AnsiColors.PURPLE_BRIGHT + "Inserire il numero di elementi da 3 a 10: " + AnsiColors.RESET, 3, 10);//numero di elementi da usare durante il gioco
	private final int indice;
	private String nome;
	private AnsiColors colore;

	Elementi(String nome, int indice, AnsiColors colore){
		this.nome = nome;
		this.indice = indice;
		this.colore = colore;
	}
	public static Elementi getElemento(int i){
		switch (i){
			case 0:{ return TERRA;
			}
			case 1:{ return ACQUA;
			}
			case 2:{ return FUOCO;
			}
			case 3:{ return ARIA;
			}
			case 4:{ return LUCE;
			}
			case 5:{ return ROMANO;
			}
			case 6:{ return HIGHGROUND;
			}
			case 7:{ return PLASMA;
			}
			case 8:{ return SABBIA;
			}
			case 9:{ return FUMO;
			}
			default:{ return null;
			}
		}

	}

	public int getIndice(){
		return this.indice;
	}

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
    public static int interazione(Elementi e1, Elementi e2) {
    	return matrix[e1.indice][e2.indice];
    }

	//EQUILIBRIO ----------------------------------------

	//creazione della matrice per la creazione dell'equilibrio
	private static int matrix[][] = new int[N_ELEMENTI][N_ELEMENTI];

	//inserire nella matrice matrix[i][i] = 0;

	/**
	 * Crea l'equilibrio dell'Universo
	 * L'equilibrio ottenuto viene utilizzato per
	 * i confronti tra Elementi durante tutta la pratita
	 */
	public static void creaEquilibrio() {
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
			for(int j=0; j<N_ELEMENTI; j++)

				if(j==N_ELEMENTI-1) {
					if(Math.abs(somma)>=Tamagolem.VITA){
						i=-1;
						break;
					}
					matrix[i][j]=-somma;
					matrix[j][i]=somma; //elemento simmetrico
				}else if(j>i) {
					int valore=RandomDraws.drawInteger(1,Tamagolem.VITA);
					if(RandomDraws.estraiBoolean())
						valore= - valore;

					somma+=valore;

					matrix[i][j]=valore;
					matrix[j][i]=-valore; //elemento simmetrico
				}else somma+=matrix[i][j];
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