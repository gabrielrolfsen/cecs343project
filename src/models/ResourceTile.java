package models;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import utils.Types.ResourceTileType;
import utils.Types.ResourceType;

public class ResourceTile extends Tile {

	private boolean isSelected;
	private final ResourceTileType type;
	private final ResourceType resource;
	private Icon icon;

	public ResourceTile(final ResourceTileType type, final ResourceType resource) {
		this.type = type;
		this.resource = resource;
		setIcon();
	}

	private void setIcon() {
		String typeName = "";
		String resourceName = "";
		String iconPath = "";

		switch (this.type) {
		case DESERT:
			typeName = "desert";
			break;
		case FOREST:
			typeName = "forest";
			break;
		case FERTILE:
			typeName = "fertile";
			break;
		case HILLS:
			typeName = "hills";
			break;
		case MOUNTAINS:
			typeName = "mountains";
			break;
		case SWAMP:
			typeName = "swamp";
			break;
		}

		switch (this.resource) {
		case ONE_GOLD:
			resourceName = "1_gold";
			break;
		case ONE_FAVOR:
			resourceName = "1_favor";
			break;
		case ONE_FOOD:
			resourceName = "1_food";
			break;
		case ONE_WOOD:
			resourceName = "1_wood";
			break;
		case TWO_GOLDS:
			resourceName = "2_golds";
			break;
		case TWO_FAVORS:
			resourceName = "2_favors";
			break;
		case TWO_FOODS:
			resourceName = "2_foods";
			break;
		case TWO_WOODS:
			resourceName = "2_woods";
			break;
		}
		iconPath = "res/production_tiles/tile_" + typeName + "_" + resourceName
				+ ".png";
		this.icon = new ImageIcon(iconPath, String.valueOf(type) + "-"
				+ String.valueOf(resource));
	}

	public void setSelected(final boolean state) {
		this.isSelected = state;
	}

	public boolean isSelected() {
		return this.isSelected;
	}

	public Icon getIcon() {
		return this.icon;
	}

	public ResourceTileType getType() {
		return this.type;
	}

	public ResourceType getResource() {
		return this.resource;
	}

}
