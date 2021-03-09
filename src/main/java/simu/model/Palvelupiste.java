package simu.model;

import java.util.LinkedList;


import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava
public class Palvelupiste {

	private LinkedList<Asiakas> jono = new LinkedList<Asiakas>(); // Tietorakennetoteutus
	private int kapasiteetti = 1;
	private int matkustajat = 0;
	private double aikavali = 15;

	private boolean valmisLahtoon = false;

	private ContinuousGenerator generator;
	private Tapahtumalista tapahtumalista;
	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;

	//JonoStartegia strategia; //optio: asiakkaiden järjestys


	/*
	miten bussit saadaan liikkumaan edes takaisin(boolean1 ja aikavali)
	miten kapasiteetti saadaan toimimaan(boolean2 ja kapasiteetti)
	miten kello saadaan toimimaan(kello.getAika() + aikavali)
	miten odotus jonossa tapahtuu(otaJonosta meneee paussille)

	Bussin saapuminen voi koittaa hoitaa tapahtuman kautta
	Esim. BUSDEP ja bussi lähtee laiturilta



	 */
	private boolean varattu = false;


	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi){
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;

	}

	// getterit ja setterit
	public boolean getValmisLahtoon(){
		return valmisLahtoon;
	}
	public void setValmisLahtoon(boolean valmisLahtoon){
		this.valmisLahtoon = valmisLahtoon;
	}

	public Palvelupiste() {
	}



	public void lisaaJonoon(Asiakas a){ // Jonon 1. asiakas aina palvelussa

		jono.add(a);

	}

	public Asiakas otaJonosta(){  // Poistetaan palvelussa ollut



		varattu = false;
		return jono.poll();
	}


	public void aloitaPalvelu(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		varattu = true;

		double palveluaika = generator.sample();
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi,Kello.getInstance().getAika()+palveluaika));
	}

	public void muutaKapasiteettia (int uusikapasiteetti){
		this.kapasiteetti = uusikapasiteetti;
	}


	public boolean onVarattu(){
		return varattu;
	}


	public boolean onJonossa(){
		return jono.size() != 0;
	}

	public void bussinLahtoRaportti(){
		System.out.println("Bussi lähti laiturilta kello " + Kello.getInstance().getAika());
	}

}
