/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 29, 2015
 */
package models;

import utils.Types.BoardType;
import utils.Types.CardType;

/**
 * @author grolfsen
 *
 */
public class Card {

	private final BoardType mCulture;
	private final CardType mType;
	private final int mNum;
	private final int mCost;

	/**
	 * 
	 */
	public Card(final CardType type) {
		this.mType = type;
		this.mNum = type.getNum();
		this.mCost = type.getPrice();
		this.mCulture = type.getCulture();
		// TODO Auto-generated constructor stub
	}

	public BoardType getCulture() {
		return this.mCulture;
	}

	public int getCost() {
		return this.mCost;
	}

	public int getNum() {
		return this.mNum;
	}

	public CardType getType() {
		return this.mType;
	}

}
