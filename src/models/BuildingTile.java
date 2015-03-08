package models;

public class BuildingTile extends Tile {

	private final int type;

	public BuildingTile(final int aType) {
		this.type = aType;
	}

	public int getType() {
		return type;
	}

	@Override
	public String toString() {
		String buildingName = "Error";

		switch (this.type) {
		case 1:
			buildingName = "House";
			break;

		case 2:
			buildingName = "Wall";
			break;

		case 3:
			buildingName = "Tower";
			break;

		case 4:
			buildingName = "Storehouse";
			break;

		case 5:
			buildingName = "Market";
			break;

		case 6:
			buildingName = "Armory";
			break;

		case 7:
			buildingName = "Quarry";
			break;

		case 8:
			buildingName = "Monument";
			break;

		case 9:
			buildingName = "Granary";
			break;

		case 10:
			buildingName = "Gold Mint";
			break;

		case 11:
			buildingName = "Wood Workshop";
			break;

		case 12:
			buildingName = "Siege Engine Workshop";
			break;

		case 13:
			buildingName = "Great Temple";
			break;

		case 14:
			buildingName = "The Wonder";
			break;
		}

		return buildingName;
	}

}
