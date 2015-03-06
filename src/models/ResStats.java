package models;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import utils.Types.ResourceCubeType;
import views.ResourceCube;

public class ResStats {
	private final JLabel[] lblGraphic;
	private final JLabel[] lblValue;
	private final int[] intValue;
	private int myLeft, myTop;

	public ResStats(final JPanel jfrm) {
		int n;
		lblGraphic = new JLabel[5];
		lblValue = new JLabel[5];
		intValue = new int[5];
		myLeft = 0;
		myTop = 0;

		lblGraphic[ResourceCubeType.FOOD.getValue()] = new JLabel(
				new ResourceCube(ResourceCubeType.FOOD));
		lblGraphic[ResourceCubeType.FAVOR.getValue()] = new JLabel(
				new ResourceCube(ResourceCubeType.FAVOR));
		lblGraphic[ResourceCubeType.WOOD.getValue()] = new JLabel(
				new ResourceCube(ResourceCubeType.WOOD));
		lblGraphic[ResourceCubeType.GOLD.getValue()] = new JLabel(
				new ResourceCube(ResourceCubeType.GOLD));
		lblGraphic[ResourceCubeType.VICTORY.getValue()] = new JLabel(
				new ResourceCube(ResourceCubeType.VICTORY));

		for (n = 0; n < 5; n++) {
			jfrm.add(lblGraphic[n]);
			lblGraphic[n].setSize(30, 30);
			lblGraphic[n].setVisible(true);

			lblValue[n] = new JLabel("X ");
			jfrm.add(lblValue[n]);
			lblValue[n].setSize(50, 30);
			lblValue[n].setHorizontalTextPosition(SwingConstants.LEFT);
			lblValue[n].setVisible(true);
			lblValue[n].setForeground(Color.WHITE);

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

	public void setValue(final ResourceCubeType resType, final int newValue) {
		intValue[resType.getValue()] = newValue;
		updateValues();
	}
}
