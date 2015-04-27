/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Mar 12, 2015
 */
package models;

import java.util.ArrayList;
import java.util.Random;

import utils.Constants;
import utils.Types.AgeType;
import utils.Types.BoardType;
import utils.Types.ResourceCubeType;

/**
 * @author grolfsen
 *
 */
public class Player {

	private Board mBoard = null;
	private final AgeType mAge = AgeType.ARCHAIC;
	private ArrayList<Card> mHand = new ArrayList<Card>();
	private final int mResources[] = new int[5];
	private final ArrayList<Unit> army = new ArrayList<Unit>();

	public boolean human;
	public boolean turnTaken;
	public AgeType currentAge;

	public boolean[] buildingOwned = new boolean[Constants.MAX_BUILDINGS];
	public int housesOwned = 0;
	private final int MAX_BUILDINGS = 14;

	public Player() {
		for (int i = 0; i < mResources.length - 1; i++) {
			mResources[i] = 5;
		}

		// Place 1 Victory Cube for each player
		mResources[ResourceCubeType.VICTORY.getValue()] = 1;

		currentAge = AgeType.ARCHAIC;

	}

	/**
	 * Returns a Random Perm. Action Card from players hand
	 * 
	 * @return
	 */
	public Card getRandomCardFromHand() {
		final Random r = new Random();
		return mHand.get(r.nextInt(mHand.size()));
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
