/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 14, 2015
 */
package controllers;

/**
 * @author grolfsen
 *
 */
public class Main {

	public static void main(final String[] args) {

		MainController game;
		// Start the game
		try {
			game = MainController.getInstance();
		} catch (final Exception e) {
			e.printStackTrace(System.err);
			System.exit(0);

		}

	}

}
