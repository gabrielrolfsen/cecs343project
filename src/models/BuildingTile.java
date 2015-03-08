package models;

import javax.swing.Icon;

import utils.Types.BuildingTileType;

public class BuildingTile extends Tile {

	private final BuildingTileType type;
	private Icon icon;

	public BuildingTile(final BuildingTileType type) {
		this.type = type;
	}

	private void setIcon() {
		switch (this.type) {
		case HOUSE:

			break;
		case WALL:

			break;
		case TOWER:

			break;
		case STOREHOUSE:

			break;
		case MARKET:

			break;
		case ARMORY:

			break;
		case QUARRY:

			break;
		case MONUMENT:

			break;
		case GRANARY:

			break;
		case MINT:

			break;
		case WORKSHOP_WOOD:

			break;
		case WORKSHOP_SIEGE:

			break;
		case TEMPLE:

			break;
		case WONDER:

			break;
		}
	}

	public BuildingTileType getType() {
		return this.type;
	}

	public Icon getIcon() {
		return this.icon;
	}

}
