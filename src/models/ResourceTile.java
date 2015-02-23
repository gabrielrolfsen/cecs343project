package models;

public class ResourceTile {

	private int type;
	private int resource;

	public ResourceTile(final int aTerrain, final int aResource) {
		if (aTerrain < 0 && aTerrain > 5) // 6 terrain types
		{
			System.err.println(aTerrain
					+ " is not a vaild code for terrain type.");
		} else {
			this.type = aTerrain;
		}

		if (aResource >= 1 && aResource <= 7) {
			this.resource = aResource;

		} else {
			System.err.println(aResource + " is not a valid resource type.");
		}
	}

	public int getType() {
		return type;
	}

	public int getResource() {
		return resource;
	}

}
