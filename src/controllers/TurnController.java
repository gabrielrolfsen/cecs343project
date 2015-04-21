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
import models.ResourcesBank;
import utils.Types.ResourceCubeType;
import views.MainFrameView;
import views.VictoryCardsDialog;

/**
 * @author grolfsen
 *
 */
public class TurnController {

	private final MainFrameView mainFrame = MainFrameView.getInstance();
	private final CardController cardController = CardController.getInstance();
	private final ResourcesBank resourceBank = ResourcesBank.getInstance();

	public void playTurn(final Player[] players) {

		final int victoryCubesOnCards[] = ResourcesBank.getInstance()
				.getVictoryCubesOnCards();

		final VictoryCardsDialog b = new VictoryCardsDialog(mainFrame,
				players[0].getResourceCounter(), victoryCubesOnCards);
		b.setVisible(true);

		// Show player's hand after he selects the cards
		final ArrayList<Card> hand = mainFrame.showCardDialog(players[0]
				.getAge().getValue() + 4);

		for (int i = 0; i < 3; i++) {
			if (!hand.isEmpty()) {
				final Card selectedCard = mainFrame.showHandDialog(hand);
				// If player didn't pass the turn and still has cards to play
				if (selectedCard != null) {
					// Play the selected Card
					cardController.play(players, selectedCard);
					// Updates Player's hand
					hand.remove(selectedCard);
				}
				// TODO: Add AIs turns
			}
		}
		// Clear player's cards if there's any
		hand.clear();

		// Conduct Spoilage
		for (int i = 0; i < players.length; i++) {
			final int[] res = players[i].getResourceCounter();
			int maxQty = 5;

			// TODO: Check if player has a Storehouse
			if (1 > 2) {
				maxQty = 8;
			}
			for (final int ii = 0; i < res.length; i++) {
				if (res[ii] > maxQty) {
					resourceBank.replenishResource(
							ResourceCubeType.getType(ii), res[ii] - maxQty);
					res[ii] = maxQty;
				}
			}

		}

		// Rotate Starting Player

	}

}
