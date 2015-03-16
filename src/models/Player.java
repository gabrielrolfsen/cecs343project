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

/**
 * @author grolfsen
 *
 */
public class Player {

	Board mBoard = null;
	ArrayList<Card> mHand = new ArrayList<Card>();

	public void setBoard(final BoardType boardType) {
		this.mBoard = new Board(boardType);
	}

	public Board getBoard() {
		return this.mBoard;
	}
}
