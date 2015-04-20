/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 15, 2015
 */
package controllers;

import java.util.ArrayList;

import models.Card;
import models.Player;
import views.MainFrameView;

/**
 * @author grolfsen
 *
 */
public class TurnController {

	private final MainFrameView mainFrame = MainFrameView.getInstance();
	private final CardController cardController = CardController.getInstance();

	/**
	 * 
	 */
	public TurnController() {
		// TODO Auto-generated constructor stub
	}

	public void playTurn(final Player[] players) {
		// Show player's hand after he selects the cards
		final ArrayList<Card> hand = mainFrame.showCardDialog();

		for (int i = 0; i < 3; i++) {
			final Card selectedCard = mainFrame.showHandDialog(hand);
			if (selectedCard != null) {
				// Play the selected Card
				cardController.play(players, selectedCard);
				// Updates Player's hand
				hand.remove(selectedCard);
			}
		}
		// Clear player's cards if there's any
		hand.clear();

		// Rotate Starting Player

	}

}
