package views;

import java.awt.Color;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import utils.Constants;

public class ArmyView {
	private final JLabel[] lblGraphic = new JLabel[5];
	private final JLabel[] lblValue = new JLabel[5];
	private String[] intValue = new String[5];
	private final int myLeft;
	int tempLeft[] = new int[5];
	int mTop[] = new int[5];
	int tempTop = 100;
	boolean crazyVariable = false;

	public ArmyView(final JPanel parentPanel) {

		// Create a label for each Resource Cube Type
		for (int i = 0; i < lblValue.length; i++) {
			final JLabel lbl = new JLabel(new UnitView());
			lblGraphic[i] = lbl;
		}

		for (int i = 0; i < 5; i++) {
			parentPanel.add(lblGraphic[i]);
			lblGraphic[i].setSize(70, 70);
			lblGraphic[i].setVisible(false);
			lblValue[i] = new JLabel("NO UNITS");
			parentPanel.add(lblValue[i]);
			lblValue[i].setSize(50, 30);
			// TODO: Center the view
			lblValue[i].setHorizontalTextPosition(SwingConstants.CENTER);
			lblValue[i].setVisible(true);
			lblValue[i].setForeground(Color.WHITE);
			intValue[i] = "";
		}
		myLeft = Constants.UNITS_X_LOCATION;
		moveObjects();
	}

	private void refreshValues() {
		for (int i = 0; i < 5; i++) {
			lblValue[i].setText(intValue[i]);
			// If there's a unit, enable the graphics
			if (intValue[i].length() > 0) {
				lblGraphic[i].setVisible(true);
			} else {
				lblGraphic[i].setVisible(false);
			}
		}
	}

	private void moveObjects() {
		final Random r = new Random();

		for (int i = 0; i < 5; i++) {
			if (crazyVariable == false) {
				final int rndm = r.nextInt(10);
				tempLeft[i] = myLeft;
				if (rndm > 4) {
					tempTop += 72;
					tempLeft[i] += r.nextInt(6);
				} else {
					tempLeft[i] = r.nextInt((675 - myLeft) + 1) + myLeft;
				}
				mTop[i] = tempTop;
			}

			lblGraphic[i].setLocation(tempLeft[i], mTop[i]);
			lblValue[i].setLocation(tempLeft[i], mTop[i] + 65);

		}
		crazyVariable = true;
	}

	public void refresh() {
		moveObjects();
	}

	public void updateResources(final String[] resources) {
		this.intValue = resources;
		refreshValues();
	}

}
