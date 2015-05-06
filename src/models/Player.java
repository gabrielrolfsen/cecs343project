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
import utils.Types.CardType;
import utils.Types.CultureType;
import utils.Types.ResourceCubeType;
import utils.Types.ResourceTileType;

/**
 * @author grolfsen
 *
 */
public class Player {

	private Board mBoard = null;
	private AgeType mAge = AgeType.ARCHAIC;
	private ArrayList<Card> mHand = new ArrayList<Card>();
	private final Stack<Card> mDeckRandomCards = new Stack<Card>();
	private final int mResources[] = { 5, 5, 5, 5, 1 };
	private final ArrayList<Unit> army = new ArrayList<Unit>();

	// XXX: what is that?!
	public boolean turnTaken;
	public int housesOwned = 0;
	public boolean[] buildingOwned = new boolean[Constants.MAX_BUILDINGS];

	/**
	 * Returns a Random Permanent/Random Action Card from players hand. Used for
	 * AI
	 * 
	 * @return
	 */
	public Card getRandomCardFromHand() {
		final Random r = new Random();
		// Returns a Random Card, but in case the hand is empty, returns null
		return mHand.size() > 0 ? mHand.get(r.nextInt(mHand.size())) : null;
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

	/**
	 * 
	 * @return
	 */
	public AgeType getAge() {
		return this.mAge;
	}

	/**
	 * Advance player's age
	 * 
	 * @return true if the player isn't on the last age possible, false if he
	 *         is.
	 */
	public boolean advanceAge() {
		// Get Current Age's id
		int id = mAge.getId();
		// Increment it.
		id++;
		// Get the correspondent Age for the id
		final AgeType nextAge = AgeType.getType(id);
		if (nextAge != null) {
			mAge = AgeType.getType(id);
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<Unit> getArmy() {
		return this.army;
	}

	public void incrementResources(final int[] resourcesToAdd) {
		for (int i = 0; i < resourcesToAdd.length; i++) {
			mResources[i] += resourcesToAdd[i];
		}
	}

	public void decrementResources(final int[] resourcesToSubtract) {
		for (int i = 0; i < resourcesToSubtract.length; i++) {
			mResources[i] -= resourcesToSubtract[i];
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

	public void setBoard(final CultureType boardType) {
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

	/**
	 * Return player's current Types of ResourceTiles on the production area.
	 * 
	 * @return
	 */
	public String[] getResourceTilesTypes() {
		// Get all Resource Tiles
		final ArrayList<String> result = new ArrayList<String>();
		final int i = 0;

		// Iterate trough types and array
		final ArrayList<ResourceTile> resTiles = mBoard.getResourceTiles();
		for (final ResourceTileType type : ResourceTileType.values()) {
			for (final ResourceTile tile : resTiles) {
				if (tile.getType() == type) {
					// Add to result array
					result.add(type.toString());
					// Break to assure just 1 of each type
					break;
				}
			}
		}
		// Returns a String[] instead of ArrayList
		return result.toArray(new String[result.size()]);
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
