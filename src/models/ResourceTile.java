package models;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import utils.Types.ResourceTileType;
import utils.Types.ResourceType;

import utils.*;


public class ResourceTile {

	private boolean isSelected;
	private final ResourceTileType type;
	private final ResourceType resource;
	private Icon icon;
	
	public TileEnum myType;
	public ResEnum myRes;
	public boolean myTwoRes;

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
			myType = TileEnum.DESERT;
			break;
		case FOREST:
			typeName = "forest";
			myType = TileEnum.FOREST;
			break;
		case FERTILE:
			typeName = "fertile";
			myType = TileEnum.FERTILE;
			break;
		case HILLS:
			typeName = "hills";
			myType = TileEnum.HILL;
			break;
		case MOUNTAINS:
			typeName = "mountains";
			myType = TileEnum.MOUNTAIN;
			break;
		case SWAMP:
			typeName = "swamp";
			myType = TileEnum.SWAMP;
			break;
		}

		
		switch (this.resource) {
		case ONE_GOLD:
			resourceName = "1_gold";
			myRes = ResEnum.GOLD;
			myTwoRes = false;
			break;
		case ONE_FAVOR:
			resourceName = "1_favor";
			myRes = ResEnum.FAVOR;
			myTwoRes = false;
			break;
		case ONE_FOOD:
			resourceName = "1_food";
			myRes = ResEnum.FOOD;
			myTwoRes = false;
			break;
		case ONE_WOOD:
			resourceName = "1_wood";
			myRes = ResEnum.WOOD;
			myTwoRes = false;
			break;
		case TWO_GOLDS:
			resourceName = "2_golds";
			myRes = ResEnum.GOLD;
			myTwoRes = true;
			break;
		case TWO_FAVORS:
			resourceName = "2_favors";
			myRes = ResEnum.FAVOR;
			myTwoRes = true;
			break;
		case TWO_FOODS:
			resourceName = "2_foods";
			myRes = ResEnum.FOOD;
			myTwoRes = true;
			break;
		case TWO_WOODS:
			resourceName = "2_woods";
			myRes = ResEnum.WOOD;
			myTwoRes = true;
			break;
		}
		//iconPath = "res/production_tiles/tile_" + typeName + "_" + resourceName
		//		+ ".png";
		//this.icon = new ImageIcon(iconPath, String.valueOf(type) + "-"
		//		+ String.valueOf(resource));
		
		TileRes tempResTile = new TileRes();
		tempResTile.setType(myType);
		tempResTile.setRes(myRes, myTwoRes);
		this.icon = tempResTile.getIcon();
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
