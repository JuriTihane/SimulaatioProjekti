package simu.framework;

import java.util.PriorityQueue;

/**
 * Tapahtumalista luokka ylläpitää tapahtumalistan parametrit, muuttujat ja tietorakennen
 */
public class Tapahtumalista {

	private PriorityQueue<Tapahtuma> lista = new PriorityQueue<Tapahtuma>();

	/**
	 * Tapahtumalista konstruktori
	 */
	public Tapahtumalista(){
	}

	/**
	 * Palauttaa ensimmäisen Tapahtuman listalta, jonka poistetaan
	 * @return Tapahtuma
	 */
	public Tapahtuma poista(){
		//Trace.out(Trace.Level.INFO,"Tapahtumalistasta poisto " + lista.peek());
		return lista.remove();
	}

	/**
	 * Lisää tapahtumalistaan uuden Tapahtuman t
	 * @param t Tapahtuma olio
	 */
	public void lisaa(Tapahtuma t){
		lista.add(t);
	}

	/**
	 * Palauttaa seuraavan tapahtumalistan tapahtuman ajan
	 * @return double
	 */
	public double getSeuraavanAika(){
		return lista.peek().getAika();
	}
}