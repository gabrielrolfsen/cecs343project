/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 12, 2015
 */
package models;

import javax.swing.ImageIcon;

import utils.Types.UnitType;

/**
 * @author grolfsen
 *
 */
public class Unit {

	private final String mName;
	private final UnitType mType;
	private final int[] mCost = new int[4];

	private final ImageIcon icon = null;

	public Unit(final UnitType type) {
		this.mName = type.getName();
		this.mType = type;

		setIcon();
	}

	public UnitType getType() {
		return this.mType;
	}

	private void setIcon() {

	}

}
