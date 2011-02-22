/*
 * Copyright (c) 2010 Martin Ueding <dev@martin-ueding.de>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

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
