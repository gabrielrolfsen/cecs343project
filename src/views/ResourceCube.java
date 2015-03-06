package views;

import javax.swing.ImageIcon;

import utils.Types.ResourceCubeType;

public class ResourceCube extends ImageIcon {

	public ResourceCube(final ResourceCubeType newType) {
		super();

		ImageIcon tmpImageIcon;

		switch (newType) {
		case FOOD:
			tmpImageIcon = new ImageIcon("res/ResourceFood.jpg");
			break;
		case FAVOR:
			tmpImageIcon = new ImageIcon("res/ResourceFavor.jpg");
			break;
		case WOOD:
			tmpImageIcon = new ImageIcon("res/ResourceWood.jpg");
			break;
		case GOLD:
			tmpImageIcon = new ImageIcon("res/ResourceGold.jpg");
			break;
		default:
			tmpImageIcon = new ImageIcon("res/ResourceVictory.jpg");
		}

		setImage(tmpImageIcon.getImage());

	}
}
