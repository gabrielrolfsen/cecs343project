package models;

import utils.Coordinates;
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

	Tile tile;
	ResourceTileType type;
	int x = -1;
	int y = -1;

	/**
	 * 
	 * Constructor for ResourceTiles
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
		this.tile = null;
	}

	/**
	 * 
	 * Constructor for BuildingTiles
	 * 
	 * @param x
	 *            [0-3] Horizontal position of the tile on a 4x4 area
	 * @param y
	 *            [0-3] Vertical position of the tile on a 4x4 area
	 */
	public TilePlaceHolder(final int x, final int y) {
		this.x = x;
		this.y = y;
		this.tile = null;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setTile(final Tile tile) {
		this.tile = tile;
	}

	public Tile getTile() {
		return this.tile;
	}

	// Returns true if there's a Tile assigned to the placeholder
	public boolean isFilled() {
		return tile != null ? true : false;
	}

	public ResourceTileType getType() {
		return this.type;
	}

	public Coordinates getCoordinates() {
		return new Coordinates(this.x, this.y);
	}

}
