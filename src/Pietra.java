public class Pietra {
	private Elementi elemento;
	
	public Pietra(Elementi elemento) {
		this.elemento = elemento;
	}
	
	public Elementi getElemento() {
		return elemento;
	}
	
	/**
	 * Indica quale pietra è quella debole
	 * @param p1
	 * @param p2
	 * @return 1 se il primo elemento è quello debole
	 * 2 se il secondo elemento è quello debole
	 * 0 se gli elementi sono le stesse
	 */
	public static int debole(Pietra p1, Pietra p2) {
		int interazione=Elementi.interazione(p1.elemento, p2.elemento);
		
		if(interazione==0)
			return 0;
		else if(interazione<0)
				return 1;
		else return 2;
	}
}
