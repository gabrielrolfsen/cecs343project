package models;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import utils.Types.BuildingTileType;

public class BuildingTile extends Tile {

	private final BuildingTileType type;
	private Icon icon;

	public BuildingTile(final BuildingTileType type) {
		this.type = type;
		setIcon();
	}

	private void setIcon() {
		String iconPath;
		
		iconPath = "res/building_tiles/" + type.getString() + ".png";
		this.icon = new ImageIcon(iconPath, type.getString());
	}

	public BuildingTileType getType() {
		return this.type;
	}

	public Icon getIcon() {
		return this.icon;
	}

}
