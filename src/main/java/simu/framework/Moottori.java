package simu.framework;

import simu.model.Palvelupiste;

/**
 * Moottori luokka ohjelman pyörimisen varten
 */
public abstract class Moottori {
	private double simulointiaika = 0;
	private Kello kello;
	protected Tapahtumalista tapahtumalista;
	protected Palvelupiste[] palvelupisteet;

	/**
	 * Moottori konstruktori, jossa otetaan kello muuttujaan ja luodaan uusi tapahtumalista.
	 */
	public Moottori(){
		kello = Kello.getInstance(); // Otetaan kello muuttujaan yksinkertaistamaan koodia
		tapahtumalista = new Tapahtumalista();
		// Palvelupisteet luodaan simu.model-pakkauksessa Moottorin aliluokassa
	}

	/**
	 * @param aika Asettaa simulointiajan pituuden
	 */
	public void setSimulointiaika(double aika) {
		simulointiaika = aika;
	}

	/**
	 * Aja metodi, joka koordinoi eri metodien laukeamisen, jotka ovar keskeiset osat ohjelmaa
	 */
	public void aja(){
		alustukset(); // luodaan mm. ensimmäinen tapahtuma
		while (simuloidaan()){
			kello.setAika(nykyaika());
			suoritaBTapahtumat();
			yritaCTapahtumat();
		}
		tulokset();
	}

	/**
	 * Suorittaa seuraavan tapahtuman tapahtuman listalta jos seuraavan tapahtuman aika on sama kuin kellon nykyinen aika
	 */
	private void suoritaBTapahtumat() {
		while (tapahtumalista.getSeuraavanAika() == kello.getAika()){
			suoritaTapahtuma(tapahtumalista.poista());
		}
	}

	/**
	 * Suorittaa aloitaPalvelu jos palvelupisteet listasta löytyy palvelupiste joka ei ole varattu ja on jonossa
	 */
	private void yritaCTapahtumat(){
		for (Palvelupiste p: palvelupisteet){
			if (!p.onVarattu() && p.onJonossa()){
				p.aloitaPalvelu();
			}
		}
	}

	/**
	 * @return Palauttaa seuraavan tapahtuman ajan
	 */
	private double nykyaika(){
		return tapahtumalista.getSeuraavanAika();
	}

	/**
	 * @return Palauttaa true jos kellon aika  ei ole isompi kuin simulointiaika, false jos on
	 */
	private boolean simuloidaan(){
		return kello.getAika() < simulointiaika;
	}

	/**
	 * Määritellään simu.model-pakkauksessa Moottorin aliluokassa
	 */
	protected abstract void alustukset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	/**
	 * @param t Määritellään simu.model-pakkauksessa Moottorin aliluokassa
	 */
	protected abstract void suoritaTapahtuma(Tapahtuma t);  // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	/**
	 * Määritellään simu.model-pakkauksessa Moottorin aliluokassa
	 */
	protected abstract void tulokset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa
}