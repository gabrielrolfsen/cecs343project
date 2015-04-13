/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 11, 2015
 */
package views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import models.BattleCard;
import models.Unit;

/**
 * @author grolfsen
 *
 */
public class RecruitDialog extends JDialog {

	final ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
	ConfirmListener confirmListener = new ConfirmListener();
	SelectListener selectListener = new SelectListener();
	ArrayList<Unit> mSelectedUnits = new ArrayList<Unit>();
	ArrayList<BattleCard> availableUnits = new ArrayList<BattleCard>();
	int maxSelection = 0;
	int boxesSelected = 0;
	int[] mPlayerResources;

	public RecruitDialog(final JFrame parentFrame, final int[] playerResources,
			final int qty, final ArrayList<BattleCard> availableUnits) {
		super(parentFrame, "Select up to " + qty + " unit"
				+ (qty > 1 ? "s" : "") + " to recruit.", true);
		this.availableUnits = availableUnits;
		this.maxSelection = qty;
		this.mPlayerResources = playerResources;

		// Creates the layout for the Dialog
		final JPanel panel = new JPanel(new GridBagLayout());
		// TODO: Use constraints on dimensions
		panel.setPreferredSize(new Dimension(350, 300));
		final GridBagConstraints c = new GridBagConstraints();

		for (int i = 0; i < availableUnits.size(); i++) {

			final JCheckBox box = new JCheckBox(availableUnits.get(i).getName());
			box.addItemListener(selectListener);
			box.setActionCommand(Integer.toString(i));
			checkBoxes.add(box);

			// If the user cannot afford the unit, the checkbox is disabled
			final int[] cardCost = availableUnits.get(i).getCost();
			for (int j = 0; j < 4; j++) {
				if (mPlayerResources[j] < cardCost[j]) {
					box.setEnabled(false);
					break;
				}
			}

			// * Button Layout Constraints * //
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.5;
			c.gridx = (i % 3);
			c.gridy = (i / 3);
			// Add it to the Panel
			panel.add(box, c);
		}

		final JButton passBtn = new JButton("Recruit");
		passBtn.addActionListener(confirmListener);
		// * Button Layout Constraints * //
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0; // reset to default
		c.weighty = 1.0; // request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; // bottom of space
		c.insets = new Insets(10, 0, 0, 0); // top padding
		c.gridx = 0;
		c.gridwidth = 3;
		c.gridy = (availableUnits.size() / 3) + 1; // set y based on array size
		panel.add(passBtn, c);

		add(panel);
		pack();
		setLocationRelativeTo(parentFrame);

	}

	public ArrayList<Unit> getSelectedUnits() {
		return this.mSelectedUnits;
	}

	/**
	 * Listener that limit the amount of checkboxes to be selected.
	 * 
	 * @author grolfsen
	 *
	 */
	private class SelectListener implements ItemListener {

		// TODO: improve logic
		@Override
		public void itemStateChanged(final ItemEvent e) {
			final int index = Integer.parseInt(((JCheckBox) e.getSource())
					.getActionCommand());
			final int unitCost[] = availableUnits.get(index).getCost();

			if (((JCheckBox) e.getSource()).isSelected()) {
				boxesSelected++;

				// Pre-calculate the player resources if he recruits the
				// selected unit
				for (int i = 0; i < 4; i++) {
					mPlayerResources[i] -= unitCost[i];
				}

				toggleCheckBoxes(false);
			} else {
				for (int i = 0; i < 4; i++) {
					mPlayerResources[i] += unitCost[i];
				}
				toggleCheckBoxes(true);

				boxesSelected--;
			}

			if (boxesSelected != maxSelection) {
				// Disable units that the player cannot afford
				for (int i = 0; i < availableUnits.size(); i++) {
					final int[] cardCost = availableUnits.get(i).getCost();
					for (int j = 0; j < 4; j++) {
						if (mPlayerResources[j] < cardCost[j]
								&& !checkBoxes.get(i).isSelected()) {
							checkBoxes.get(i).setEnabled(false);
							break;
						} else if (checkBoxes.get(i).isEnabled() == false) {
							checkBoxes.get(i).setEnabled(true);
						}
					}
				}
			}
		}

	}

	private void toggleCheckBoxes(final boolean s) {
		if (boxesSelected == maxSelection) {
			for (final JCheckBox c : checkBoxes) {
				if (!c.isSelected()) {
					c.setEnabled(s);
				}
			}
		}
	}

	private class ConfirmListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {

			// Get all selected checkboxes and store the names in a string
			for (final JCheckBox j : checkBoxes) {
				if (j.isSelected()) {
					mSelectedUnits.add(availableUnits.get(
							Integer.parseInt(j.getActionCommand())).getUnit());
				}
			}
			// TODO: Check if player can afford it
			// Closes the dialog
			setVisible(false);
			dispose();
		}
	}
}
