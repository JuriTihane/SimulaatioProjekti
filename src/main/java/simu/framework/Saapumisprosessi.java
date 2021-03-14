package simu.framework;
import eduni.distributions.*;
import simu.model.TapahtumanTyyppi;

/**
 * Luokka saapumisprosessien käsittelemisen varten
 */
public class Saapumisprosessi {
	
	private ContinuousGenerator generaattori;
	private Tapahtumalista tapahtumalista;
	private TapahtumanTyyppi tyyppi;
	private ContinuousGenerator lahdonViivastys = new Normal(3,2);

	/**
	 * @param g Asettaa jakauman
	 * @param tl Mihin tapahtumanlistaan saapumisprosessi sijoitetaan
	 * @param tyyppi Minkä tyyppinen saapumisprosessi on, e.g. ARR1, DEP1, BUSARR, BUSDEP
	 */
	public Saapumisprosessi(ContinuousGenerator g, Tapahtumalista tl, TapahtumanTyyppi tyyppi){
		this.generaattori = g;
		this.tapahtumalista = tl;
		this.tyyppi = tyyppi;
	}

	/**
	 * Generoi uuden tapahtuman ja lisää sen tapahtumalistaan
	 */
	public void generoiSeuraava(){
		Tapahtuma t = new Tapahtuma(tyyppi, (Kello.getInstance().getAika() + generaattori.sample()));
		tapahtumalista.lisaa(t);
	}

	/**
	 * Generoi uuden tapahtuman tyyppiä BUSDEP ja lisää sen tapahtumanlistaan
	 */
	public void generoiSeuraavaBussi() {
		Tapahtuma lahto = new Tapahtuma(TapahtumanTyyppi.BUSDEP, (Kello.getInstance().getAika() + generaattori.sample() + lahdonViivastys.sample()));
		tapahtumalista.lisaa(lahto);
	}
}