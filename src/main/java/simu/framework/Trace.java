package simu.framework;

/**
 * Trace luokka
 */
public class Trace {

	public enum Level{INFO, WAR, ERR}

	private static Level traceLevel;

	/**
	 * Asettaa trace levelin
	 * @param lvl e.g. INFO vai WAR vai ERR trace level
	 */
	public static void setTraceLevel(Level lvl){
		traceLevel = lvl;
	}

	/**
	 * Ei vielä käytössä
	 * @param lvl
	 * @param txt
	 */
	public static void out(Level lvl, String txt) {
		if (lvl.ordinal() >= traceLevel.ordinal()) {
			//System.out.println(txt);
		}
	}
}