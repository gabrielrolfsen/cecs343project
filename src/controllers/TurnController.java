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
import utils.Constants;
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

	private final int victoryCubesOnCards[] = resourceBank.getVictoryCubesOnCards();
	final private ArrayList<Integer> rotatePlayerControl = new ArrayList<Integer>();
	private ArrayList<Card> humanPlayerHand;
	private Player[] mPlayers;
	private int startingPlayer;
	private int counter = 0;

	/**
	 * Points the variable to players that are playing. Probably can be
	 * refactored.
	 * 
	 * @param players
	 */
	public void setPlayers(final Player[] players) {
		this.mPlayers = players;
	}

	public void playTurn(final int rounds) {
		// Initialize Array for control the rotating players
		for (int i = 0; i < rounds + 1; i++) {
			rotatePlayerControl.add(0);
			rotatePlayerControl.add(1);
			rotatePlayerControl.add(2);
		}

		// Do as many rounds as specified by rounds
		for (int i = 0; i < rounds; i++) {
			// Get the current starting player
			startingPlayer = rotatePlayerControl.get(i);
			// Place Victory Cubes
			placeVictoryCubes(startingPlayer);
			// Pick Permanent and Random Cards
			pickCards(startingPlayer);
			// Play Cards
			playCards(startingPlayer);
			// Conduct Spoilage
			spoilage();
		}

	}

	/**
	 * Method that conducts spoilage for all players
	 */
	private void spoilage() {
		// Conduct Spoilage
		for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
			// Clear players' cards if there's any
			mPlayers[i].getHand().clear();
			final int[] res = mPlayers[i].getResourceCounter();
			int maxQty = 5;

			// If player Has a StoreHouse, the maximum Quantity Increases
			if (mPlayers[i].hasStoreHouse()) {
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
	}

	private void placeVictoryCubes(final int i) {

		if (i == startingPlayer) {
			counter++;
		}

		if (counter > 1) {
			counter = 0;
			return;
		} else {
			if (i == Constants.HUMAN_PLAYER) {
				humanPlaceVictoryCubes();
			} else {
				System.out.println("AI " + i + " tried to place a Victory Cube.");
				aiPlaceVictoryCubes(i);
			}
		}
		// Recursive call
		placeVictoryCubes(rotatePlayerControl.get(i + 1));
	}

	private void pickCards(final int i) {
		if (i == startingPlayer) {
			counter++;
		}

		if (counter > 1) {
			counter = 0;
			return;
		} else {
			if (i == Constants.HUMAN_PLAYER) {
				humanPickCards();
			} else {
				aiPickCards(i);
			}
		}
		// Recursive call
		pickCards(rotatePlayerControl.get(i + 1));
	}

	private void playCards(final int i) {

		if (i == startingPlayer) {
			counter++;
		}

		if (counter > Constants.TURNS_PLAYING_CARDS) {
			counter = 0;
			return;
		} else {
			if (i == Constants.HUMAN_PLAYER) {
				humanPlayCards();
			} else {
				aiPlayCards(i);

			}
		}
		// Recursive call
		playCards(rotatePlayerControl.get(i + 1));

	}

	private void aiPlayCards(final int i) {
		final Card card = mPlayers[i].getRandomCardFromHand();
		// Play the Card Picked
		cardController.play(card, i);
		System.out.println("===============");
		System.out.println("Player " + i + " played card: " + card.getType().toString());
		System.out.println("===============");
		// Remove it from player's hand
		mPlayers[i].removeCardFromHand(card);
	}

	private void humanPlayCards() {
		if (!humanPlayerHand.isEmpty()) {
			final Card selectedCard = mainFrame.showHandDialog(mPlayers[0].getBoard().getType(),
					humanPlayerHand);
			// If player didn't pass the turn and still has cards to play
			if (selectedCard != null) {
				// Play the selected Card
				cardController.play(selectedCard, 0);
				// Updates Player's hand
				humanPlayerHand.remove(selectedCard);
			}
		}
	}

	private void humanPickCards() {
		// Gets PlayerHand Pointer
		humanPlayerHand = mPlayers[0].getHand();

		// Shows Dialog to playe choose the cards he wants
		final int qtyRandomCards = mainFrame.showCardDialog(mPlayers[0].getBoard().getType(),
				humanPlayerHand, mPlayers[0].getAge().getId() + 4);

		/*
		 * Place Random Action Cards on player's hand according to the quantity
		 * he selected
		 */
		for (int i = 0; i < qtyRandomCards; i++) {
			humanPlayerHand.add(mPlayers[0].getRandomActionCard());
		}
	}

	private void aiPickCards(final int i) {

		final ArrayList<Card> hand = new ArrayList<Card>();

		// Create a list with numbers 0 to 6 (types of cards)
		final ArrayList<Integer> nums = new ArrayList<Integer>();
		for (int x = 0; x < 7; x++) {
			nums.add(x);
		}
		// Shuffle it
		Collections.shuffle(nums);
		final Random r = new Random();
		final int maxCardsAllowed = (mPlayers[i].getAge().getId() + 4);
		final int maxPermActionCards = r.nextInt(maxCardsAllowed - 1) + 2;
		System.out.println("=== Player " + i + " ===");
		System.out.println("# Of Perm. Actions Card that will be picked: " + maxPermActionCards);

		// Select the max amount of Perm. Action Card the player's age
		// allows
		for (int ii = 0; ii < maxPermActionCards; ii++) {
			// Get Random Type
			final CardType type = CardType.getType(nums.get(ii));
			// Add to temporary hand
			hand.add(new Card(type));
			System.out.println("Player " + ii + " picked Perm. Action Card: " + type.toString());
		}

		// Fill the rest of the hand with Random Action Cards.
		for (int z = maxPermActionCards; z < maxCardsAllowed; z++) {
			// Add a Random Action to Player's hand
			hand.add(mPlayers[i].getRandomActionCard());

		}

		// Add the Hand to player's hand
		mPlayers[i].addHand(hand);

	}

	private void humanPlaceVictoryCubes() {
		// Human Player Turn to Place Victory Cubes
		final VictoryCardsDialog victoryDialog = new VictoryCardsDialog(mainFrame,
				mPlayers[0].getResourceCounter(), victoryCubesOnCards);
		victoryDialog.setVisible(true);
		// Update the Main Frame to show player's Resources
		mainFrame.updatePlayerResources(mPlayers[0].getResourceCounter());
	}

	private void aiPlaceVictoryCubes(final int i) {
		// If player has at least one Victory Cube
		if (mPlayers[i].getResourceCounter()[ResourceCubeType.VICTORY.getValue()] > 0) {
			// AIs Players Turn to Place Victory Cubes
			final Random r = new Random();
			// Add a Victory Cube to a Random Card
			victoryCubesOnCards[r.nextInt(4)]++;
			// Decrement the Victory Cube placed from the Player
			mPlayers[i].decrementResource(ResourceCubeType.VICTORY, 1);
		}
	}

}
