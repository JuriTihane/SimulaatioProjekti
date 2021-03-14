package simu.framework;

import simu.model.TapahtumanTyyppi;

/**
 * Tapahtuma luokka ylläpitää tapahtumien parametrit / muuttujat
 */
public class Tapahtuma implements Comparable<Tapahtuma> {
	private TapahtumanTyyppi tyyppi;
	private double aika;

	/**
	 * @param tyyppi Minkä tyyppinen tapahtuma luodaan
	 * @param aika Tapahtuman aika
	 */
	public Tapahtuma(TapahtumanTyyppi tyyppi, double aika){
		this.tyyppi = tyyppi;
		this.aika = aika;
	}

	/**
	 * @return Palauttaa tapahtuman tyypin
	 */
	public TapahtumanTyyppi getTyyppi() {
		return tyyppi;
	}

	/**
	 * @return Palauttaa tapahtuman ajan
	 */
	public double getAika() {
		return aika;
	}

	/**
	 * @param arg Tapahtuma mihin verrataan
	 * @return Palauttaa -1 jos aika on pienempi kuin arg tapahtuman aika, palauttaa 1 jos toistenpäin ja 0 jos tarkistukset ei mene läpi.
	 */
	@Override
	public int compareTo(Tapahtuma arg) {
		if (this.aika < arg.aika) return -1;
		else if (this.aika > arg.aika) return 1;
		return 0;
	}
}