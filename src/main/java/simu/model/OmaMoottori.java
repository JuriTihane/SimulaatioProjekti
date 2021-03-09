package simu.model;

import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.*;

import java.util.LinkedList;
import java.util.Random;

public class OmaMoottori extends Moottori{

	public static int matkustajienMaara;
	public static int bussienMaara = 8;
	private Saapumisprosessi bussiSaapumisprosessi;
	private Saapumisprosessi saapumisprosessi;
	private Saapumisprosessi bussiLahtoprosessi;
	private ContinuousGenerator bussiGeneraattori = new Normal(20,1);
	private ContinuousGenerator lahdonViivastys = new Normal(3,1);
	private LinkedList<Palvelupiste> prioriteettiJonoPalvelupisteille = new LinkedList<Palvelupiste>();

	public OmaMoottori(){
		// Annetaan arraylle bussienMaara koon
		palvelupisteet = new Palvelupiste[bussienMaara - 1];

		// Luodaan tapahtumatyyppiä DEP1 bussienMaara verran palvelupisteitä eli busseja terminaalia varten
		for (int i = 0 ; i < palvelupisteet.length; i++){
			palvelupisteet[i] = new Palvelupiste(new Normal(10,1),tapahtumalista,TapahtumanTyyppi.DEP1);
		}


		saapumisprosessi = new Saapumisprosessi(new Negexp(1,5), tapahtumalista, TapahtumanTyyppi.ARR1);
		bussiSaapumisprosessi = new Saapumisprosessi(new Normal(20,1.5), tapahtumalista, TapahtumanTyyppi.BUSARR);
		bussiLahtoprosessi = new Saapumisprosessi(new Normal(3,1),tapahtumalista, TapahtumanTyyppi.BUSDEP);
	}


	@Override
	protected void alustukset() {
		// Ensimmäinen saapuminen järjestelmään
		saapumisprosessi.generoiSeuraava();
		bussiSaapumisprosessi.generoiSeuraavaBussi();
	}

	// Suoritetaan tapahtuma t, määitellään Moottori.java
	@Override
	protected void suoritaTapahtuma(Tapahtuma t) {
		// Luodaan uusi asiakas
		Asiakas a = new Asiakas();

		// Switch eri tyyppien käsittelyä varten
		switch (t.getTyyppi()) {
			// Jos ARR1, ottaa palvelupisteen arraysta asiakkaan "haluaman" bussinumeron ja lisää asiakkaan jonoon
			case ARR1: palvelupisteet[a.getBussiNumero()].lisaaJonoon(a);
				// Generoi uuden tapahtuman ja lisää sitä tapahtumalistan "lista" priorityqueuen arraylist
				saapumisprosessi.generoiSeuraava();
				break;

			// Jos DEP1 ja jos asiakkaan "haluama" bussinumero on valmis lähtöön
			case DEP1:
				if (palvelupisteet[a.getBussiNumero()].getValmisLahtoon()) {
					// Ottaa asiakkaan "haluama" bussinumero palvelupisteet indexista ja ottaa jonosta sen asiakkaan
					palvelupisteet[a.getBussiNumero()].otaJonosta();
					// Asettaa asiakkaalle poistumis ajan
					a.setPoistumisaika(Kello.getInstance().getAika());
					// Asiakkaan raportti
					a.raportti();
				}

			// Jos BUSARR niin luo uuden tapahtuman tyyppiä BUSARR
			case BUSARR: Tapahtuma i = new Tapahtuma(TapahtumanTyyppi.BUSARR, Kello.getInstance().getAika() + bussiGeneraattori.sample());
				// Asettaa asiakkaan "haluaman" palvelupisteelle valmi lahtoon false
				palvelupisteet[a.getBussiNumero()].setValmisLahtoon(false);
				System.out.println("Bussi on pysäkillä");
				// Lisää arrayyn asiakkaan "haluaman" palvelupisteen
				prioriteettiJonoPalvelupisteille.add(palvelupisteet[a.getBussiNumero()]);
				// Generoi uuden tapahtuman saapumisprosessille bussilahtoprosessi ja lisää sitä tapahtumalistan "lista" priorityqueuen arraylist
				bussiLahtoprosessi.generoiSeuraava();

			// Jos BUSDEP
			case BUSDEP:
				// Kokeile asettaa ensimmäiselle arraylle valmis lahtoon true
				try {
					prioriteettiJonoPalvelupisteille.getFirst().setValmisLahtoon(true);
				// Busseja ei ole listalla
				} catch (Exception NoSuchElementException) {
					System.out.println("Ei busseja listassa");
				}
				// Joka tapauksessa asettaa asiakkaan "haluaman" palvelupisteelle valmis lahtoon true
				palvelupisteet[a.getBussiNumero()].setValmisLahtoon(true);
				// Ottaa ensimmäisen indexin ja poistaa sen
				prioriteettiJonoPalvelupisteille.poll();
				System.out.println("Bussi lähtee");
		}
	}

	@Override
	protected void tulokset() {
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		System.out.println("Tulokset ... puuttuvat vielä");
		// TODO: Palveltujen asiakkaiden määrä palvelupisteessä
		// TODO: Terminaalin kokonaisasiakasmäärä
//		for (int i = 0; i < palvelupisteet.length; i++) {
//			System.out.println(palvelupisteet[i].);
//		}
	}
}