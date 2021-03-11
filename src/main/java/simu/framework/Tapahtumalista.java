package simu.framework;

import java.util.PriorityQueue;
import simu.framework.*;
import simu.model.*;


public class Tapahtumalista {

	private PriorityQueue<Tapahtuma> lista = new PriorityQueue<Tapahtuma>();

	public Tapahtumalista(){
	}
	
	public Tapahtuma poista(){
		//Trace.out(Trace.Level.INFO,"Tapahtumalistasta poisto " + lista.peek());
		return lista.remove();
	}

	public void lisaaBussiTapahtumia (Tapahtuma t){
		lisaa(t);
	}
	
	public void lisaa(Tapahtuma t){
		lista.add(t);
	}
	
	public double getSeuraavanAika(){
		return lista.peek().getAika();
	}
}