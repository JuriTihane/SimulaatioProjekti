package simu.model;

import simu.framework.*;
import java.util.Random;


// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Asiakas {

	private double saapumisaika;
	private double poistumisaika;
	private int bussiNumero;
	private int id = 0;

	public Asiakas(){
		id++;
		bussiNumero = new Random().nextInt(OmaMoottori.bussienMaara -1);
		saapumisaika = Kello.getInstance().getAika();
	}

	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	public int getBussiNumero(){
		return bussiNumero;
	}

	public void raportti(){
		Trace.out(Trace.Level.INFO, "Asiakas "+id+ " läpimenoaika:" + (poistumisaika - saapumisaika));
	}
}