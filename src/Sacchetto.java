import java.util.ArrayList;
import java.util.ArrayDeque;

public class Sacchetto {

	private ArrayDeque<Pietra> pietre= new ArrayDeque<>();
	
	public Sacchetto(ArrayList<Pietra> pietre, Partita p1) {
			if(pietre.size()<=p1.getDim_sacch()){
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

	public ArrayDeque<Pietra> getPietre() {
		return pietre;
	}
}
