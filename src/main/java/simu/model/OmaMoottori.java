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
	Random random;


	public OmaMoottori(){

		palvelupisteet = new Palvelupiste[bussienMaara -1];


		for (int i = 0 ; i < palvelupisteet.length;){

			System.out.println(palvelupisteet.length);
			int average;
			int variance;
			random = new Random();
			palvelupisteet[i] = new Palvelupiste(new Normal(10,1),tapahtumalista,TapahtumanTyyppi.DEP1);
			i++;
		}

		//palvelupisteet[0]=new Palvelupiste(new Normal(10,6), tapahtumalista, TapahtumanTyyppi.DEP1);
		//palvelupisteet[1]=new Palvelupiste(new Normal(10,10), tapahtumalista, TapahtumanTyyppi.DEP1);
		//palvelupisteet[2]=new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP1);

		saapumisprosessi = new Saapumisprosessi(new Negexp(1,5), tapahtumalista, TapahtumanTyyppi.ARR1);
		bussiSaapumisprosessi = new Saapumisprosessi(new Normal(20,1.5), tapahtumalista, TapahtumanTyyppi.BUSARR);

		bussiLahtoprosessi = new Saapumisprosessi(new Normal(3,1),tapahtumalista, TapahtumanTyyppi.BUSDEP);
	}


	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
		bussiSaapumisprosessi.generoiSeuraavaBussi();
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t){  // B-vaiheen tapahtumat

		Asiakas a = new Asiakas();
		switch (t.getTyyppi()){

			case ARR1: palvelupisteet[a.getBussiNumero()].lisaaJonoon(a);
				saapumisprosessi.generoiSeuraava();
				break;
			case DEP1:
				if (palvelupisteet[a.getBussiNumero()].getValmisLahtoon() == false){
					palvelupisteet[a.getBussiNumero()].otaJonosta();
					a.setPoistumisaika(Kello.getInstance().getAika());
					a.raportti();
				}

			case BUSARR: Tapahtuma i = new Tapahtuma(TapahtumanTyyppi.BUSARR, Kello.getInstance().getAika() + bussiGeneraattori.sample());
				palvelupisteet[a.getBussiNumero()].setValmisLahtoon(false);
				System.out.println("Bussi on pysäkillä");
				prioriteettiJonoPalvelupisteille.add(palvelupisteet[a.getBussiNumero()]);
				bussiLahtoprosessi.generoiSeuraava();
			case BUSDEP:
				try {
					prioriteettiJonoPalvelupisteille.getFirst().setValmisLahtoon(true);
				} catch (Exception NoSuchElementException){
					System.out.println("Ei busseja listassa");
				}

				//palvelupisteet[a.getBussiNumero()].setValmisLahtoon(true);
				prioriteettiJonoPalvelupisteille.poll();
				System.out.println("Bussi lähtee");
		}
	}
	@Override
	protected void tulokset() {
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		System.out.println("Tulokset ... puuttuvat vielä");
	}
}
