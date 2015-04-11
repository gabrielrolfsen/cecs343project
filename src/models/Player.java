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

	public Player() {
		for (int i = 0; i < mResources.length - 1; i++) {
			mResources[i] = 4;
		}
	}

	public void updateResources(final int[] updatedResources) {
		for (int i = 0; i < updatedResources.length; i++) {
			mResources[i] += updatedResources[i];
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
