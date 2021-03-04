package simu.model;

import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;

import java.util.Random;

public class OmaMoottori extends Moottori{

	public static int matkustajienMaara;
	public static int bussienMaara = 8;
	private Saapumisprosessi saapumisprosessi;
	Random random;


	public OmaMoottori(){

		palvelupisteet = new Palvelupiste[bussienMaara -1];


		for (int i = 0 ; i < palvelupisteet.length;){

			System.out.println(palvelupisteet.length);
			int average;
			int variance;

			palvelupisteet[i] = new Palvelupiste(new Normal(10,1),tapahtumalista,TapahtumanTyyppi.DEP1);
			i++;
		}

		//palvelupisteet[0]=new Palvelupiste(new Normal(10,6), tapahtumalista, TapahtumanTyyppi.DEP1);
		//palvelupisteet[1]=new Palvelupiste(new Normal(10,10), tapahtumalista, TapahtumanTyyppi.DEP1);
		//palvelupisteet[2]=new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP1);

		saapumisprosessi = new Saapumisprosessi(new Negexp(15,5), tapahtumalista, TapahtumanTyyppi.ARR1);

	}


	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t){  // B-vaiheen tapahtumat

		Asiakas a = new Asiakas();
		switch (t.getTyyppi()){

			case ARR1: palvelupisteet[a.getBussiNumero()].lisaaJonoon(a);
				saapumisprosessi.generoiSeuraava();
				break;
			case DEP1: palvelupisteet[a.getBussiNumero()].otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				a.raportti();


		}
	}


	@Override
	protected void tulokset() {
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		System.out.println("Tulokset ... puuttuvat vielä");
	}


}
