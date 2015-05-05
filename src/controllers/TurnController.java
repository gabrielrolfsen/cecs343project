/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 15, 2015
 */
package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import models.Card;
import models.Player;
import models.ResourcesBank;
import utils.Types.CardType;
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

		final int victoryCubesOnCards[] = resourceBank.getVictoryCubesOnCards();

		// Human Player Turn to Place Victory Cubes
		final VictoryCardsDialog victoryDialog = new VictoryCardsDialog(mainFrame,
				players[0].getResourceCounter(), victoryCubesOnCards);
		victoryDialog.setVisible(true);

		// Update the Main Frame to show player's Resources
		mainFrame.updatePlayerResources(players[0].getResourceCounter());

		// AIs Players Turn to Place Victory Cubes
		final Random r = new Random();
		for (int i = 1; i < 3; i++) {
			// Add a Victory Cube to a Random Card
			victoryCubesOnCards[r.nextInt(4)]++;
			// Decrement the Victory Cube placed from the Player
			players[i].decrementResource(ResourceCubeType.VICTORY, 1);
		}

		// Gets PlayerHand Pointer
		final ArrayList<Card> humanPlayerHand = players[0].getHand();

		// Shows Dialog to playe choose the cards he wants
		final int qtyRandomCards = mainFrame.showCardDialog(players[0].getBoard().getType(), humanPlayerHand,
				players[0].getAge().getValue() + 4);

		/*
		 * Place Random Action Cards on player's hand according to the quantity
		 * he selected
		 */
		for (int i = 0; i < qtyRandomCards; i++) {
			humanPlayerHand.add(players[0].getRandomActionCard());
		}

		// AI Players Turn to Place Cards into their hands
		for (int i = 1; i < 3; i++) {
			final ArrayList<Card> hand = new ArrayList<Card>();

			// Create a list with numbers 0 to 7 (types of cards)
			final ArrayList<Integer> nums = new ArrayList<Integer>();
			for (int x = 0; x < 7; x++) {
				nums.add(x);
			}
			// Shuffle it
			Collections.shuffle(nums);

			final int maxCardsAllowed = (players[i].getAge().getValue() + 4);
			final int maxPermActionCards = r.nextInt(maxCardsAllowed - 1) + 2;
			System.out.println("# Of Perm. Actions Card that will be picked: " + maxPermActionCards);

			// Select the max amount of Perm. Action Card the player's age
			// allows
			for (int ii = 0; ii < maxPermActionCards; ii++) {
				// Get Random Type
				final CardType type = CardType.getType(nums.get(ii));
				System.out.println(nums.get(ii));
				// Add to temporary hand
				hand.add(new Card(type));
			}
			// Fill the rest of the hand with Random Action Cards.
			for (int z = maxPermActionCards; z < maxCardsAllowed; z++) {
				// Add a Random Action to Player's hand
				hand.add(players[i].getRandomActionCard());
			}
			System.out.println("# Of cards in player's " + i + " hand: " + hand.size());
			// Add the Hand to player's hand
			players[i].addHand(hand);
		}

		// 3 Turns of Playing Cards
		for (int i = 0; i < 3; i++) {
			// If Human has at least one card in his hand
			if (!humanPlayerHand.isEmpty()) {
				final Card selectedCard = mainFrame.showHandDialog(players[0].getBoard().getType(),
						humanPlayerHand);
				// If player didn't pass the turn and still has cards to play
				if (selectedCard != null) {
					// Play the selected Card
					cardController.play(selectedCard, 0);
					// Updates Player's hand
					humanPlayerHand.remove(selectedCard);
				}
			}

			// AI Players turn
			// for (int ii = 1; ii < 3; ii++) {
			// final Card card = players[ii].getRandomCardFromHand();
			// for (final Card c : players[ii].getHand()) {
			// System.out.println("Player " + ii + " picked card: " +
			// c.getType().toString());
			// }
			// cardController.play(card, ii);
			// System.out.println("===============");
			// System.out.println("Player " + ii + " played card: " +
			// card.getType().toString());
			// System.out.println("===============");
			// players[ii].removeCardFromHand(card);
			// }
		}
		// Clear player's cards if there's any
		humanPlayerHand.clear();

		// Conduct Spoilage
		for (int i = 0; i < players.length; i++) {
			final int[] res = players[i].getResourceCounter();
			int maxQty = 5;

			// If player Has a StoreHouse, the maximum Quantity Increases
			if (players[i].hasStoreHouse()) {
				maxQty = 8;
			}
			for (int ii = 0; ii < res.length; ii++) {
				if (res[ii] > maxQty) {
					// Put the Extra Resources on Bank
					resourceBank.replenishResource(ResourceCubeType.getType(ii), res[ii] - maxQty);
					// Remove Extra Resources from Player
					res[ii] = maxQty;
				}
			}

		}

		// Rotate Starting Player

	}
}
