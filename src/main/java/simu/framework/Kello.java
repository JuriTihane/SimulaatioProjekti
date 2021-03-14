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
	 * @return Palauttaa kellon instanssin, jos instanssia ei ole olemassa niin luo uuden instanssin kellosta
	 */
	public static Kello getInstance(){
		if (instanssi == null){
			instanssi = new Kello();	
		}
		return instanssi;
	}

	/**
	 * @param aika asettaa uuden ajan jos sitä haluaa muuttaa
	 */
	public void setAika(double aika){
		this.aika = aika;
	}

	/**
	 * @return Palauttaa ajan
	 */
	public double getAika(){
		return aika;
	}
}
