/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Mar 12, 2015
 */
package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

import utils.Constants;
import utils.Types.AgeType;
import utils.Types.BoardType;
import utils.Types.CardType;
import utils.Types.ResourceCubeType;

/**
 * @author grolfsen
 *
 */
public class Player {

	private Board mBoard = null;
	private final AgeType mAge = AgeType.ARCHAIC;
	private ArrayList<Card> mHand = new ArrayList<Card>();
	private final Stack<Card> mDeckRandomCards = new Stack<Card>();
	private final int mResources[] = { 5, 5, 5, 5, 1 };
	private final ArrayList<Unit> army = new ArrayList<Unit>();

	public boolean human;
	public boolean turnTaken;
	public AgeType currentAge = AgeType.ARCHAIC;

	public boolean[] buildingOwned = new boolean[Constants.MAX_BUILDINGS];
	public int housesOwned = 0;
	private final int MAX_BUILDINGS = 14;

	/**
	 * Returns a Random Permanent/Random Action Card from players hand. Used for
	 * AI
	 * 
	 * @return
	 */
	public Card getRandomCardFromHand() {
		final Random r = new Random();
		return mHand.get(r.nextInt(mHand.size()));
	}

	public Card getRandomActionCard() {
		return mDeckRandomCards.pop();
	}

	public ArrayList<Card> getHand() {
		return this.mHand;
	}

	public void removeCardFromHand(final Card card) {
		mHand.remove(card);
	}

	public void addHand(final ArrayList<Card> hand) {
		this.mHand = hand;
	}

	/**
	 * Add units to player's army and decrement the respectively player
	 * resources
	 * 
	 * @param unitCards
	 */
	public void addUnits(final ArrayList<Unit> units) {
		for (final Unit unit : units) {
			army.add(unit);
		}
	}

	public AgeType getAge() {
		return this.mAge;
	}

	public ArrayList<Unit> getArmy() {
		return this.army;
	}

	public void incrementResources(final int[] updatedResources) {
		for (int i = 0; i < updatedResources.length; i++) {
			mResources[i] += updatedResources[i];
		}
	}

	public void decrementResources(final int[] updatedResources) {
		for (int i = 0; i < updatedResources.length; i++) {
			mResources[i] -= updatedResources[i];
		}
	}

	public void incrementResource(final ResourceCubeType type, final int qty) {
		this.mResources[type.getValue()] += qty;
	}

	public void decrementResource(final ResourceCubeType type, final int qty) {
		this.mResources[type.getValue()] -= qty;
	}

	public int[] getResourceCounter() {
		return this.mResources;
	}

	public void setBoard(final BoardType boardType) {
		this.mBoard = new Board(boardType);

		// Initialize Deck of Random Action Cards
		for (final CardType c : CardType.values()) {
			if (c.getType() == CardType.RANDOM) {
				// If the card is from the player's culture
				if (c.getCulture() == mBoard.getType()) {
					mDeckRandomCards.add(new Card(c));
				}
			}
		}

		// Shuffle Deck of Random Action Cards
		Collections.shuffle(mDeckRandomCards);

	}

	public boolean hasMarket() {
		return this.mBoard.hasMarket();
	}

	public boolean hasStoreHouse() {
		return this.mBoard.hasStoreHouse();
	}

	public boolean hasGreatTemple() {
		return this.mBoard.hasGreatTemple();
	}

	public Board getBoard() {
		return this.mBoard;
	}
}
