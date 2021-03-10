package simu.framework;
import eduni.distributions.*;
import simu.model.TapahtumanTyyppi;

public class Saapumisprosessi {
	
	private ContinuousGenerator generaattori;
	private Tapahtumalista tapahtumalista;
	private TapahtumanTyyppi tyyppi;
	private ContinuousGenerator lahdonViivastys = new Normal(3,2);

	public Saapumisprosessi(ContinuousGenerator g, Tapahtumalista tl, TapahtumanTyyppi tyyppi){
		this.generaattori = g;
		this.tapahtumalista = tl;
		this.tyyppi = tyyppi;
	}

	public void generoiSeuraava(){
		Tapahtuma t = new Tapahtuma(tyyppi, Kello.getInstance().getAika()+generaattori.sample());
		tapahtumalista.lisaa(t);
	}

	public void generoiSeuraavaBussi(){
		Tapahtuma t = new Tapahtuma(tyyppi, Kello.getInstance().getAika() + generaattori.sample());
		tapahtumalista.lisaa(t);
		Tapahtuma lahto = new Tapahtuma(TapahtumanTyyppi.BUSDEP, Kello.getInstance().getAika() + generaattori.sample() + lahdonViivastys.sample());
		tapahtumalista.lisaa(lahto);
	}
}