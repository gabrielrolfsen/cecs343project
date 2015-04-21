/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Mar 12, 2015
 */
package models;

import utils.Types.CardType;

/**
 * @author grolfsen
 *
 */
public class Card {

	CardType mType;
	int mCost;
	int mNum;

	public Card(final CardType type) {
		init(type, 0);
	}

	public Card(final CardType type, final int cost) {
		init(type, cost);
	}

	public Card(final CardType type, final int cost, final int num) {
		this.mNum = num;
		init(type, cost);
	}

	private void init(final CardType type, final int cost) {
		this.mType = type;
		this.mCost = cost;
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
