package models;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import utils.Constants;

public class ResourceTile {

	private boolean isSelected;
	private int type;
	private int resource;
	private Icon icon;

	public ResourceTile(final int type, final int resource) {

		if (type < 0 || type > 5) // 6 terrain types
		{
			System.err.println(type + " is not a vaild code for terrain type.");
		} else {
			this.type = type;
		}

		if (resource < 0 || resource > 7) {

			System.err.println(resource
					+ " is not a valid code for resource type.");

		} else {
			this.resource = resource;
		}
		setIcon();

	}

	private void setIcon() {
		String typeName = "";
		String resourceName = "";
		String iconPath = "";

		switch (this.type) {
		case Constants.TYPE_DESERT:
			typeName = "desert";
			break;
		case Constants.TYPE_FOREST:
			typeName = "forest";
			break;
		case Constants.TYPE_FERTILE:
			typeName = "fertile";
			break;
		case Constants.TYPE_HILLS:
			typeName = "hills";
			break;
		case Constants.TYPE_MOUNTAINS:
			typeName = "mountains";
			break;
		case Constants.TYPE_SWAMP:
			typeName = "swamp";
			break;
		}

		switch (this.resource) {
		case Constants.ONE_GOLD:
			resourceName = "1_gold";
			break;
		case Constants.ONE_FAVOR:
			resourceName = "1_favor";
			break;
		case Constants.ONE_FOOD:
			resourceName = "1_food";
			break;
		case Constants.ONE_WOOD:
			resourceName = "1_wood";
			break;
		case Constants.TWO_GOLDS:
			resourceName = "2_golds";
			break;
		case Constants.TWO_FAVORS:
			resourceName = "2_favors";
			break;
		case Constants.TWO_FOODS:
			resourceName = "2_foods";
			break;
		case Constants.TWO_WOODS:
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

	public int getType() {
		return this.type;
	}

	public int getResource() {
		return this.resource;
	}

}
