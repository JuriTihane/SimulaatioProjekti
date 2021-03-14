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
	 * @param generator
	 * @param tapahtumalista
	 * @param tyyppi
	 * @param kapasiteetti
	 */
	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi, int kapasiteetti){
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
		this.kapasiteetti = kapasiteetti;
	}

	/**
	 * Palauttaa matkustajat muuttujan arvon
	 * @return
	 */
	public int getMatkustajat(){
		return this.matkustajat;
	}

	/**
	 * Asettaa uuden arvon matkustajat muuttujalle
	 * @param uusiArvo
	 */
	public void setMatkustajat(int uusiArvo){
		this.matkustajat = uusiArvo;
	}

	/**
	 * Palauttaa valmisLahtoon muuttujan arvon
	 * @return
	 */
	public boolean getValmisLahtoon(){
		return valmisLahtoon;
	}

	/**
	 * Asettaa uuden arvon valmisLahtoon muuttujalle
	 * @param valmisLahtoon
	 */
	public void setValmisLahtoon(boolean valmisLahtoon){
		this.valmisLahtoon = valmisLahtoon;
	}

	/**
	 * Asettaa uuden arvon onPysakilla muuttujalle
	 * @param onPysakilla
	 */
	public void setOnPysakilla(boolean onPysakilla){
		this.onPysakilla = onPysakilla;
	}

	/**
	 * Palauttaa onPysakilla arvon
	 * @return
	 */
	public boolean getOnPysakilla(){
		return onPysakilla;
	}

	/**
	 * Asiakas olio lisätään jonoon mikäli kapasiteetti ei ole täynnä
	 * @param a
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
	 * Linkedlist jonosta otetaan ensimmäinen jäsen ja se poistetaan listalta, jos varattu muuttujan arvo
	 * on false
	 * @return
	 */
	public Asiakas otaJonosta(){  // Poistetaan palvelussa ollut
		varattu = false;
		return jono.poll();
	}

	/**
	 * varattu muuttujan ollessa arvon ollessa true generoidaan palveluaika ja lisätään uusi tapahtuma.
	 */
	public void aloitaPalvelu(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		varattu = true;
		double palveluaika = generator.sample();
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi,Kello.getInstance().getAika()+palveluaika));
	}

	/**
	 * Olion kapasiteetille laitetaan uusi arvo
	 * @param uusikapasiteetti
	 */

	public void setKapasiteettia (int uusikapasiteetti){
		this.kapasiteetti = uusikapasiteetti;
	}

	/**
	 * Palauttaa varattu muuttjan arvon
	 * @return
	 */
	public boolean onVarattu(){
		return varattu;
	}

	/**
	 * Palauttaa true arvon, jos jonon koko on enemmän kuin 0
	 * @return
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