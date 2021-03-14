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
	 * @return Palauttaa ensimmäisen Tapahtuman listalta, jonka poistetaan
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
	 * @return Palauttaa seuraavan tapahtumalistan tapahtuman ajan
	 */
	public double getSeuraavanAika(){
		return lista.peek().getAika();
	}
}