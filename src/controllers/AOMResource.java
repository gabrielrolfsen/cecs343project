package controllers;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class AOMResource {
	private static final int FOOD = 0;
	private static final int FAVOR = 1;
	private static final int WOOD = 2;
	private static final int GOLD = 3;
	private static final int VICTORY = 4;

	private final JLabel[] lblGraphic;
	private final JLabel[] lblValue;
	private final int[] intValue;
	private int myLeft, myTop;

	public AOMResource(final JFrame jfrm) {
		int n;
		lblGraphic = new JLabel[5];
		lblValue = new JLabel[5];
		intValue = new int[5];
		myLeft = 0;
		myTop = 0;

		lblGraphic[FOOD] = new JLabel(new ImageIcon("res/ResourceFood.jpg"));
		lblGraphic[FAVOR] = new JLabel(new ImageIcon("res/ResourceFavor.jpg"));
		lblGraphic[WOOD] = new JLabel(new ImageIcon("res/ResourceWood.jpg"));
		lblGraphic[GOLD] = new JLabel(new ImageIcon("res/ResourceGold.jpg"));
		lblGraphic[VICTORY] = new JLabel(new ImageIcon(
				"res/ResourceVictory.jpg"));

		for (n = 0; n < 5; n++) {
			jfrm.add(lblGraphic[n]);
			lblGraphic[n].setSize(30, 30);
			lblGraphic[n].setVisible(true);

			lblValue[n] = new JLabel("X ");
			jfrm.add(lblValue[n]);
			lblValue[n].setSize(50, 30);
			lblValue[n].setHorizontalTextPosition(SwingConstants.LEFT);
			lblValue[n].setVisible(true);

			intValue[n] = 0;
		}

		updateValues();
		moveObjects();
	}

	private void updateValues() {
		int i;
		for (i = 0; i < 5; i++) {
			lblValue[i].setText("X " + intValue[i]);
		}
	}

	private void moveObjects() {
		int n;
		int tempTop = myTop;

		for (n = 0; n < 5; n++) {
			lblGraphic[n].setLocation(myLeft, tempTop);
			lblValue[n].setLocation(myLeft + 40, tempTop);
			tempTop += 50;
		}
	}

	public void setTop(final int newTop) {
		myTop = newTop;
		moveObjects();
	}

	public void setLeft(final int newLeft) {
		myLeft = newLeft;
		moveObjects();
	}

	public void setValue(final AOMResourceEnum resType, final int newValue) {
		switch (resType) {
		case FOOD:
			intValue[FOOD] = newValue;
			break;
		case FAVOR:
			intValue[FAVOR] = newValue;
			break;
		case WOOD:
			intValue[WOOD] = newValue;
			break;
		case GOLD:
			intValue[GOLD] = newValue;
			break;
		default:
			intValue[VICTORY] = newValue;
		}
		updateValues();
	}
}
