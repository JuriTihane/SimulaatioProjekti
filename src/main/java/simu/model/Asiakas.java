package simu.model;

import simu.framework.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Asiakas {

	private double saapumisaika;
	private double poistumisaika;
	private int bussiNumero;
	private static int id = 0;

	public Asiakas(){
		id++;
		bussiNumero = new Random().nextInt(OmaMoottori.bussienMaara);
		saapumisaika = Kello.getInstance().getAika();
	}

	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika + ThreadLocalRandom.current().nextDouble(1, 10);
	}

	public double getPoistumisaika() {
		return poistumisaika;
	}

	public double getSaapumisaika() {
		return saapumisaika;
	}

	public int getBussiNumero(){
		return bussiNumero;
	}
	public static int getId(){
		return id;
	}

	public void raportti() {
		System.out.println("Asiakas "+id+ " läpimenoaika:" + (poistumisaika - saapumisaika));
	}
}