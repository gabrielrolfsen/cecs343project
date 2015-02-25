package models;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import utils.Constants;

public class ResourceTile {

	private int type;
	private int resource;
	private final Icon icon;

	public ResourceTile(final int type, final int resource,
			final String iconPath) {
		this.icon = new ImageIcon(iconPath, String.valueOf(type) + "-"
				+ String.valueOf(resource));

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
		return this.type;
	}

	public int getResource() {
		return this.resource;
	}

}
