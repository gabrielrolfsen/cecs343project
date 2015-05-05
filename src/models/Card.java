/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 29, 2015
 */
package models;

import utils.Types.CardType;
import utils.Types.CultureType;

/**
 * @author grolfsen
 *
 */
public class Card {

	private final CultureType mCulture;
	private final CardType mType;
	private final int mNum;
	private final int mCost;
	private final String mGodName;

	/**
	 * 
	 */
	public Card(final CardType type) {
		this.mType = type;
		this.mNum = type.getNum();
		this.mCost = type.getCost();
		this.mCulture = type.getCulture();
		this.mGodName = type.getGodName();
	}

	public String getGodName() {
		return this.mGodName;
	}

	public CultureType getCulture() {
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
