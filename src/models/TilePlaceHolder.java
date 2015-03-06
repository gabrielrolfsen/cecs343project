package models;

import utils.Types.ResourceTileType;

/**
 * Class used to represent the tiles on the board. The board has two 4x4 tile
 * areas, the city area (for BuildingTiles) and the production area (for
 * ResourceTiles).
 * 
 * @author grolfsen
 *
 */
public class TilePlaceHolder {

	ResourceTileType type;
	int x = -1;
	int y = -1;
	boolean isFilled = false;

	/**
	 * 
	 * @param type
	 *            Tile type, defined in /utils/Types.java
	 * @param x
	 *            [0-3] Horizontal position of the tile on a 4x4 area
	 * @param y
	 *            [0-3] Vertical position of the tile on a 4x4 area
	 */
	public TilePlaceHolder(final ResourceTileType type, final int x, final int y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setTile() {
		this.isFilled = true;
	}

	public void setFilled() {
		this.isFilled = true;
	}

	public boolean isFilled() {
		return this.isFilled;
	}

	public ResourceTileType getType() {
		return this.type;
	}

}
