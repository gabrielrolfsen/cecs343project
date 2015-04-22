package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

import utils.Types.BuildingTileType;
import controllers.BuildController;

public class BuildView extends JDialog implements ActionListener {
	private final BuildController curBuildControl;

	private final int MAX_BUTTONS = 14;
	private final JRadioButton[] btnBuilding;
	private final ButtonGroup bgBuilding;
	private final JButton btnPurchase;

	public BuildView(final JFrame parentFrame,
			final BuildController newBuildControl) {
		super(parentFrame, "Select Building", true);

		curBuildControl = newBuildControl;

		btnBuilding = new JRadioButton[MAX_BUTTONS];
		bgBuilding = new ButtonGroup();

		int i;
		for (i = 0; i < MAX_BUTTONS; i++) {
			btnBuilding[i] = new JRadioButton(
					BuildingTileType.getStringForInt(i));
			btnBuilding[i].addActionListener(this);
			this.add(btnBuilding[i]);
			bgBuilding.add(btnBuilding[i]);
		}

		btnPurchase = new JButton("Purchase");
		btnPurchase.setActionCommand(Integer.toString(-1));
		btnPurchase.addActionListener(this);
		this.add(btnPurchase);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		int i;
		for (i = 0; i < MAX_BUTTONS; i++) {
			if (btnBuilding[i].isSelected()) {
				break;
			}
		}
		curBuildControl.playerChose(i);
	}
}
