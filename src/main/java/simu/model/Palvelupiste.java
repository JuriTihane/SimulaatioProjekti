package simu.model;

import java.util.LinkedList;


import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava

/**
 * Palveupisteellä on oma jono johon asiakkaat sijoitetaan. Kolme boolean muuttujaa joilla tarkistetaan
 * voiko bussiin astua (palvelupiste). Jokainen palvelupiste pitää kirjaa matkustajien määrästä. Jokaisella
 * palvelupisteellä on kapasiteetti.
 */
public class Palvelupiste {

	private LinkedList<Asiakas> jono = new LinkedList<Asiakas>(); // Tietorakennetoteutus
	private int kapasiteetti;
	private int matkustajat = 0;
	private double aikavali = 15;
	//Tsekkaa onko bussi edes pysäkille ja estää bussin lähteminen ennen kuin se on tullut laiturille
	private boolean onPysakilla = false;
	private boolean valmisLahtoon = false;
	private boolean varattu = false;
	private ContinuousGenerator generator;
	private Tapahtumalista tapahtumalista;
	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;

	//JonoStartegia strategia; //optio: asiakkaiden järjestys


	/**
	 * Konstruktori
	 * @param generator Palvelupisteen jakauma
	 * @param tapahtumalista Mihin tapahtumalistaan
	 * @param tyyppi Mikä tyyppi, e.g. ARR1, DEP1, BUSARR vai BUSDEP
	 * @param kapasiteetti Bussin kapasiteetti
	 */
	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi, int kapasiteetti){
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
		this.kapasiteetti = kapasiteetti;
	}

	/**
	 * @return Palauttaa matkustajat muuttujan arvon
	 */
	public int getMatkustajat(){
		return this.matkustajat;
	}

	/**
	 * Asettaa uuden arvon matkustajat muuttujalle
	 * @param uusiArvo Uusi arvo matkustajien määrälle
	 */
	public void setMatkustajat(int uusiArvo){
		this.matkustajat = uusiArvo;
	}

	/**
	 * @return Palauttaa valmisLahtoon muuttujan arvon
	 */
	public boolean getValmisLahtoon(){
		return valmisLahtoon;
	}

	/**
	 * Asettaa uuden arvon valmisLahtoon muuttujalle
	 * @param valmisLahtoon true on valmis lähtöön, false ei ole valmis lähtöön
	 */
	public void setValmisLahtoon(boolean valmisLahtoon){
		this.valmisLahtoon = valmisLahtoon;
	}

	/**
	 * Asettaa uuden arvon onPysakilla muuttujalle
	 * @param onPysakilla True jos on pysäkillä, false jos ei ole pysäkillä
	 */
	public void setOnPysakilla(boolean onPysakilla){
		this.onPysakilla = onPysakilla;
	}

	/**
	 * @return Palauttaa onPysakilla arvon
	 */
	public boolean getOnPysakilla(){
		return onPysakilla;
	}

	/**
	 * Asiakas olio lisätään jonoon mikäli kapasiteetti ei ole täynnä
	 * @param a Asiakas olio
	 */
	public void lisaaJonoon(Asiakas a){ // Jonon 1. asiakas aina palvelussa
		jono.add(a);
		if (matkustajat == kapasiteetti) {
			setValmisLahtoon(true);
		} else {
			matkustajat++;
		}
	}

	/**
	 * Asetetaan varattu booleani false:ksi, Linkedlist jonosta otetaan ensimmäinen jäsen ja se poistetaan listalta
	 * @return Linkedlist jonosta otettu ensimmäinen jäsen joka poistetaan
	 */
	public Asiakas otaJonosta() {  // Poistetaan palvelussa ollut
		varattu = false;
		return jono.poll();
	}

	/**
	 * Varattu muuttuja asetetaan arvoksi true, generoidaan palveluaika ja lisätään uusi tapahtuma.
	 */
	public void aloitaPalvelu(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		varattu = true;
		double palveluaika = generator.sample();
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi,Kello.getInstance().getAika()+palveluaika));
	}

	/**
	 * Olion kapasiteetille laitetaan uusi arvo, ei vielä käytössä
	 * @param uusikapasiteetti Uusi bussin kapasiteetti
	 */
	public void setKapasiteettia (int uusikapasiteetti){
		this.kapasiteetti = uusikapasiteetti;
	}

	/**
	 * @return Palauttaa varattu muuttjan arvon
	 */
	public boolean onVarattu(){
		return varattu;
	}

	/**
	 * @return Palauttaa true arvon, jos jonon koko ei ole 0
	 */
	public boolean onJonossa(){
		return jono.size() != 0;
	}

	/**
	 * Käytetään bussien testaukseen
	 */
	public void bussinLahtoRaportti(){
		System.out.println("Bussi lähti laiturilta kello " + Kello.getInstance().getAika());
	}
}