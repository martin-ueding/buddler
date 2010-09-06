import java.util.ResourceBundle;

/**
 * Das hier ist die Klasse, die sich um die Lokalisation der Programme kümmert.
 * Dafür muss eine Datei wie "sprache_de.properties" vorhanden sein und so aussehen:
 * <intern>=<extern>
 */

public class Spr {
	static ResourceBundle B = ResourceBundle.getBundle("sprache");
	
	public static String get(String ident) {
		return B.getString(ident);
	}
}
