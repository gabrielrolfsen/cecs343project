/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Feb 12, 2015
 */
package models;

import java.util.Arrays;

/**
 * @author grolfsen
 *
 */
public class Board {

	static int TYPE_NORSE = 0;
	static int TYPE_EGYPTIAN = 1;
	static int TYPE_GREEK = 2;

	private final String owner = "N/A";
	private final int type = -1;
	// TODO: Change Type "Object" to correct type
	private final Object[][] productionArea = new Object[4][4];
	private final Object[][] cityArea = new Object[4][4];

	public Board(final int type) {
		// TODO: Change Type "Object" to correct type
		Arrays.fill(cityArea, new Object());
	}

	public boolean createNorseBoard() {
		return false;
	}

}
