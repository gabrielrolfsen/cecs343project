package views;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ArmyView {
	private final JLabel[] lblGraphic = new JLabel[5];
	private final JLabel[] lblValue = new JLabel[5];
	private String[] intValue = new String[5];
	private int myLeft, myTop = 0;

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
			System.out.println("INT VAL:" + intValue[i]);
		}

		moveObjects();
	}

	private void refreshValues() {
		for (int i = 0; i < 5; i++) {
			lblValue[i].setText(intValue[i]);
			System.out.println("BLa: " + intValue[i]);
			if (intValue[i].length() > 0) {
				lblGraphic[i].setVisible(true);
			} else {
				lblGraphic[i].setVisible(false);
			}
		}
	}

	private void moveObjects() {
		int tempTop = myTop;

		for (int i = 0; i < 5; i++) {
			lblGraphic[i].setLocation(myLeft, tempTop);
			lblValue[i].setLocation(myLeft, tempTop + 70);
			tempTop += 80;
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

	public void updateResources(final String[] resources) {
		this.intValue = resources;
		refreshValues();
	}

}
