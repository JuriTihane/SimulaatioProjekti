package testi;
import simu.framework.*;

import simu.model.*;

public class Simulaattori {

	public static void main(String[] args) {
		System.out.println("Toimii");
		Trace.setTraceLevel(Trace.Level.INFO);
		Moottori m = new OmaMoottori();
		m.setSimulointiaika(1000);
		m.aja();
		System.out.println("Toimii");
		
		

	}

}
