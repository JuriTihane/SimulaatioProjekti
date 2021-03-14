package simu.framework;

/**
 * Kello luokka, käytetään yhtenä instanssina kellon ylläpitämiseksi ohjelman ajamisen aikana
 */
public class Kello {

	private double aika;
	private static Kello instanssi;

	/**
	 * Kello konstruktori, joka asettaa kellon 0:ksi
	 */
	private Kello(){
		aika = 0;
	}

	/**
	 * Palauttaa kellon instanssin, jos instanssia ei ole olemassa niin luo uuden instanssin kellosta
	 * @return instanssi
	 */
	public static Kello getInstance(){
		if (instanssi == null){
			instanssi = new Kello();	
		}
		return instanssi;
	}

	/**
	 * Asettaa uuden ajan jos sitä haluaa muuttaa
	 * @param aika aika
	 */
	public void setAika(double aika){
		this.aika = aika;
	}

	/**
	 * Palauttaa ajan
	 * @return aika
	 */
	public double getAika(){
		return aika;
	}
}
