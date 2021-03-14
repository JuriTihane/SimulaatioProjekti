package simu.model;

import simu.framework.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)


/**
 * Asiakas luokka luo jokaiselle oliolle satunnaisen numeron, joka määrittää mihin bussiin asiakas
 * pyrkii. Id:tä käytetään kun halutaan halutaan tietää asiakkaiden kokonaismäärä.
 * Jokaisella asiakas oliolla on saapumisaika, poistumisaika, bussinumero ja id.
 */
public class Asiakas {

	private double saapumisaika;
	private double poistumisaika;
	private int bussiNumero;
	private static int id = 0;

	/**
	 * Konstruktorissa kasvatetaan id:n arvoa. Generoidaan bussinumero muuttujalle satunnainen arvo. Laitetaan saapumisajan arvoksi kellosta
	 * saatu aika.
	 */

	public Asiakas(){
		id++;
		bussiNumero = new Random().nextInt(OmaMoottori.bussienMaara);
		saapumisaika = Kello.getInstance().getAika();
	}

	/**
	 * Asetetaan uusi arvo Asiakas olion poistumisajalle
	 * @param poistumisaika
	 */
	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika + ThreadLocalRandom.current().nextDouble(1, 10);
	}

	/**
	 * Palauttaa Asiakas olion poistumisajan
	 * @return
	 */
	public double getPoistumisaika() {
		return poistumisaika;
	}

	/**
	 * Palauttaa Asiakas olion saapumisajan
	 * @return
	 */

	public double getSaapumisaika() {
		return saapumisaika;
	}

	/**
	 * Palauttaa Asiakas olion bussinumeron
	 * @return
	 */
	public int getBussiNumero(){
		return bussiNumero;
	}

	/**
	 * Palauttaa id:n eli == kokonaisasiakasmäärän
	 * @return
	 */
	public static int getId(){
		return id;
	}

	/**
	 * Kutsutaan kun Asiakas olio on poistunut listalta. Ilmoittaa konsoliin kokonaisasiakasmäärän ja odotusajan
	 */
	public void raportti() {
		System.out.println("Asiakas "+id+ " läpimenoaika:" + (poistumisaika - saapumisaika));
	}
}