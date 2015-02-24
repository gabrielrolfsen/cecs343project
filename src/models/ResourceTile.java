package models;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import utils.Constants;

public class ResourceTile {

	private int type;
	private int resource;
	private final Icon icon;

	public ResourceTile(final int aTerrain, final int aResource,
			final String iconPath) {
		this.icon = new ImageIcon(iconPath);

		if (aTerrain < 0 && aTerrain > 5) // 6 terrain types
		{
			System.err.println(aTerrain
					+ " is not a vaild code for terrain type.");
		} else {
			this.type = aTerrain;
		}

		if (aResource < 0 && aResource > 7) {

			System.err.println(aResource
					+ " is not a valid code for resource type.");

		} else {
			this.resource = aResource;
		}

		setIcon();
	}

	private void setIcon() {
		switch (type) {
		case Constants.TYPE_DESERT:
			break;
		case Constants.TYPE_FOREST:
			break;
		case Constants.TYPE_FERTILE:
			break;
		case Constants.TYPE_HILLS:
			break;
		case Constants.TYPE_MOUNTAINS:
			break;
		case Constants.TYPE_SWAMP:
			break;
		}
	}

	public Icon getIcon() {
		return this.icon;
	}

	public int getType() {
		return type;
	}

	public int getResource() {
		return resource;
	}

}
