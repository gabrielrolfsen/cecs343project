public class Resource_tile {

	private int terrain;

	private int resource;

	public Resource_tile(int aTerrain, int aResource) {
		if (aTerrain >= 1 && aTerrain <= 4) // 4 terrain types
		{
			this.terrain = aTerrain;
		} else {
			System.err.println(aTerrain + "is not a vaild terrain type");
			System.exit(1);
		}

		if (aResource >= 1 && aResource <= 7)// 7 different resource producing
												// types
		{
			this.resource = aResource;

		} else {
			System.err.println(aResource + " is not a valid resource type.");
			System.exit(1);
		}
	}// end Resource_tile

	public int getTerrain() {
		return terrain;
	}

	public int getResource() {
		return resource;
	}

	@Override
	public String toString() {
		String terrainName = "Error";

		switch (this.terrain) {
		case 1:
			terrainName = "Fertile";
			break;

		case 2:
			terrainName = "Forest";
			break;

		case 3:
			terrainName = "Hill";
			break;

		case 4:
			terrainName = "Mountain";
			break;

		}

		String resourceName = "Error";

		switch (this.resource) {
		case 1:
			resourceName = "Food x2";
			break;

		case 2:
			resourceName = "Food x1";
			break;

		case 3:
			resourceName = "Wood x2";
			break;

		case 4:
			resourceName = "Wood x1";
			break;

		case 5:
			resourceName = "Gold x2";
			break;

		case 6:
			resourceName = "Gold x1";
			break;

		case 7:
			resourceName = "Favor x1";
		}

		return terrainName + " produces " + resourceName;
	} // end toString
} // end class Resource_tile
