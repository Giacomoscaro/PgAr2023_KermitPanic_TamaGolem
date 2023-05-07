import java.util.ArrayList;
import java.util.ArrayDeque;

public class Sacchetto {

	private static double div = (Elementi.n_elementi+1);
	public static final double DIM_SACCHETTO = Math.ceil(div/3)+1;
	private ArrayDeque<Pietra> pietre= new ArrayDeque<>();
	
	public Sacchetto(ArrayList<Pietra> pietre) {
			if(pietre.size()<=DIM_SACCHETTO){
				for (Pietra p : pietre) {
					this.pietre.add(p);
				}//aggiunge p alla fine della coda
			}
	}
	
	/**
	 * Ritorna la pietra corrente e scorre tutte
	 * le pietre del sacchetto
	 * @return la pietra corrente
	 */
	public Pietra usaPietra() {
		Pietra corrente = pietre.getFirst();
		pietre.pollFirst(); //rimuove la pietra corrente dalla coda
		pietre.add(corrente); //aggiunge la pietra corrente alla fine della coda
		return corrente;
	}
	
	/**
	 * Ritorna una descrizione delle pietre del
	 * sacchetto nell'ordine corrente
	 */
	public String toString() {
		StringBuffer output=new StringBuffer();
		for(Pietra p : pietre)
			output.append(p.getElemento().toString() + "\t");
		
		output.append("\n");
		return output.toString();
	}
}
