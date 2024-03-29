package views;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import utils.Types.ResourceCubeType;

public class ResourceStatusView {
	private final JLabel[] lblGraphic = new JLabel[5];
	private final JLabel[] lblValue = new JLabel[5];
	private int[] intValue = new int[5];
	private int myLeft, myTop = 0;

	public ResourceStatusView(final JPanel parentPanel) {

		// Create a label for each Resource Cube Type
		for (final ResourceCubeType type : ResourceCubeType.values()) {
			lblGraphic[type.getValue()] = new JLabel(new ResourceCubeView(type));
		}

		for (int i = 0; i < 5; i++) {
			parentPanel.add(lblGraphic[i]);
			lblGraphic[i].setSize(30, 30);
			lblGraphic[i].setVisible(true);

			lblValue[i] = new JLabel("X ");
			parentPanel.add(lblValue[i]);
			lblValue[i].setSize(50, 30);
			lblValue[i].setHorizontalTextPosition(SwingConstants.LEFT);
			lblValue[i].setVisible(true);
			lblValue[i].setForeground(Color.WHITE);
			intValue[i] = 0;
		}

		moveObjects();
	}

	private void refreshValues() {
		for (int i = 0; i < 5; i++) {
			lblValue[i].setText("X " + intValue[i]);
		}
	}

	private void moveObjects() {
		int tempTop = myTop;

		for (int i = 0; i < 5; i++) {
			lblGraphic[i].setLocation(myLeft, tempTop);
			lblValue[i].setLocation(myLeft + 40, tempTop);
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

	public void updateResources(final int[] resources) {
		this.intValue = resources;
		refreshValues();
	}

	public void setValue(final ResourceCubeType resType, final int newValue) {
		intValue[resType.getValue()] = newValue;
		refreshValues();
	}
}
