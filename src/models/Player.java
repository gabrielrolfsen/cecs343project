/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Mar 12, 2015
 */
package models;

import java.util.ArrayList;

import utils.Types.BoardType;
import utils.Types.ResourceCubeType;

/**
 * @author grolfsen
 *
 */
public class Player {

	private Board mBoard = null;
	private final ArrayList<Card> mHand = new ArrayList<Card>();
	private final int mResources[] = new int[5];
	private final ArrayList<Unit> army = new ArrayList<Unit>();

	public boolean human;
	public boolean turnTaken;

	public Player() {
		for (int i = 0; i < mResources.length - 1; i++) {
			mResources[i] = 5;
		}
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

	public void updateResource(final ResourceCubeType type, final int qty) {
		mResources[type.getValue()] += qty;
	}

	public int[] getResourceCounter() {
		return this.mResources;
	}

	public void setBoard(final BoardType boardType) {
		this.mBoard = new Board(boardType);
	}

	public Board getBoard() {
		return this.mBoard;
	}
}
