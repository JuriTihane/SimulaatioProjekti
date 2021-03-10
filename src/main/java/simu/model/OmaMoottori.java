package simu.model;

import eduni.distributions.ContinuousGenerator;
import eduni.distributions.LogNormal;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class OmaMoottori extends Moottori{

	public static int matkustajienMaara;
	public static int bussienMaara = 4;
	private Saapumisprosessi bussiSaapumisprosessi;
	private Saapumisprosessi saapumisprosessi;
	private Saapumisprosessi bussiLahtoprosessi;
	private ContinuousGenerator bussiGeneraattori = new Normal(5,1);
	private ContinuousGenerator lahdonViivastys = new Normal(3,1);
	private LinkedList<Palvelupiste> prioriteettiJonoPalvelupisteille = new LinkedList<Palvelupiste>();



	public OmaMoottori(){
		// Annetaan arraylle bussienMaara koon
		palvelupisteet = new Palvelupiste[bussienMaara - 1];
		// Luodaan tapahtumatyyppiä DEP1 bussienMaara verran palvelupisteitä eli busseja terminaalia varten
		for (int i = 0 ; i < palvelupisteet.length; i++){
			palvelupisteet[i] = new Palvelupiste(new Normal(10,1),tapahtumalista,TapahtumanTyyppi.DEP1);
		}


		saapumisprosessi = new Saapumisprosessi(new Negexp(0.5,5), tapahtumalista, TapahtumanTyyppi.ARR1);
		bussiSaapumisprosessi = new Saapumisprosessi(new Normal(3,0.5), tapahtumalista, TapahtumanTyyppi.BUSARR);
		bussiLahtoprosessi = new Saapumisprosessi(new Normal(3,0.5),tapahtumalista, TapahtumanTyyppi.BUSDEP);
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
			case BUSARR: Tapahtuma i = new Tapahtuma(TapahtumanTyyppi.BUSDEP, Kello.getInstance().getAika() + bussiGeneraattori.sample());
				// Asettaa asiakkaan "haluaman" palvelupisteelle valmi lahtoon false
				palvelupisteet[a.getBussiNumero()].setValmisLahtoon(false);
				System.out.println("Bussi " + a.getBussiNumero() + " on pysäkillä" + Kello.getInstance().getAika());
				// Lisää arrayyn asiakkaan "haluaman" palvelupisteen
				prioriteettiJonoPalvelupisteille.add(palvelupisteet[a.getBussiNumero()]);
				prioriteettiJonoPalvelupisteille.get(0).setOnPysakilla(true);
				System.out.println(prioriteettiJonoPalvelupisteille.add(palvelupisteet[a.getBussiNumero()]));
				System.out.println("Lisätty");
				for (Palvelupiste ab : prioriteettiJonoPalvelupisteille){
					System.out.println(ab.getMatkustajat());
				}
				// Generoi uuden tapahtuman saapumisprosessille bussilahtoprosessi ja lisää sitä tapahtumalistan "lista" priorityqueuen arraylist
				bussiLahtoprosessi.generoiSeuraava();

			// Jos BUSDEP
			case BUSDEP:
				//
				if (prioriteettiJonoPalvelupisteille.get(0).getOnPysakilla()){
					try {
						prioriteettiJonoPalvelupisteille.get(0).setValmisLahtoon(true);
						// Busseja ei ole listalla
					} catch (Exception NoSuchElementException) {
						System.out.println("Ei busseja listassa");
					}
					// Joka tapauksessa asettaa asiakkaan "haluaman" palvelupisteelle valmis lahtoon true
					palvelupisteet[a.getBussiNumero()].setValmisLahtoon(true);
					System.out.println("Bussi " + a.getBussiNumero() +" lähtee " + palvelupisteet[a.getBussiNumero()].getMatkustajat() + " " + Kello.getInstance().getAika());
					prioriteettiJonoPalvelupisteille.get(0).setOnPysakilla(false);
					palvelupisteet[a.getBussiNumero()].setMatkustajat(0);
					// Ottaa ensimmäisen indexin ja poistaa sen
					prioriteettiJonoPalvelupisteille.poll();
				}

		}
	}

	@Override
	protected void tulokset() {
		System.out.println("Kaikki asiakkaat yhteensä " + Asiakas.getId());
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		System.out.println("Tulokset ... puuttuvat vielä");
		// TODO: Palveltujen asiakkaiden määrä palvelupisteessä
		// TODO: Terminaalin kokonaisasiakasmäärä
//		for (int i = 0; i < palvelupisteet.length; i++) {
//			System.out.println(palvelupisteet[i].);
//		}
	}
}