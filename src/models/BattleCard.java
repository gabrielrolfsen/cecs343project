/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 11, 2015
 */
package models;

import utils.Types.UnitType;

/**
 * @author grolfsen
 *
 */
public class BattleCard {

	private final String mName;
	private final Unit mUnit;
	private int[] mCost = new int[4];

	public BattleCard(final UnitType type) {
		this.mUnit = new Unit(type);
		this.mName = type.getName();

		this.mCost = type.getCost();
	}

	public Unit getUnit() {
		return this.mUnit;
	}

	public String getName() {
		return this.mName;
	}

	public int[] getCost() {
		return this.mCost;
	}

}
