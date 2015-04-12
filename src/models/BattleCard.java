/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 11, 2015
 */
package models;

/**
 * @author grolfsen
 *
 */
public class BattleCard {

	private final String mName;
	private final String mType;
	private final int mDice;
	private int[] mCost = new int[4];
	private final String mSpecialPower = "N/A";

	// TODO: Bonus dice for special battle

	public BattleCard(final String name, final String type, final int dice,
			final int[] cost) {
		this.mName = name;
		this.mType = type;
		this.mDice = dice;
		this.mCost = cost;
	}

	public String getName() {
		return this.mName;
	}

	public int[] getCost() {
		return this.mCost;
	}

}
