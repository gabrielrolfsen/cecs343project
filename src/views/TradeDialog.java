/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 10, 2015
 */
package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;

import utils.SpringUtilities;
import utils.Types.ResourceCubeType;

/**
 * @author grolfsen
 *
 */
public class TradeDialog extends JDialog {

	final ActionListener confirmListener = new ConfirmTradeListener();
	final JSpinner[] spinners = new JSpinner[8];
	int updatedResources[] = new int[4];

	public TradeDialog(final JFrame parentFrame, final int[] bankResources,
			final int[] playerResources) {
		super(parentFrame, "Select Resources to Trade", true);

		final JPanel panel = new JPanel(new SpringLayout());
		// TODO: Fix the layout!

		final JLabel lblPlayer = new JLabel("Player");
		panel.add(lblPlayer);

		final SpinnerModel modelFavorPlayer = new SpinnerNumberModel(0, 0,
				playerResources[ResourceCubeType.FAVOR.getValue()], 1);
		spinners[0] = addLabeledSpinner(panel, "Favor", modelFavorPlayer);

		final SpinnerModel modelFoodPlayer = new SpinnerNumberModel(0, 0,
				playerResources[ResourceCubeType.FOOD.getValue()], 1);
		spinners[1] = addLabeledSpinner(panel, "Food", modelFoodPlayer);

		final SpinnerModel modelGoldPlayer = new SpinnerNumberModel(0, 0,
				playerResources[ResourceCubeType.GOLD.getValue()], 1);
		spinners[2] = addLabeledSpinner(panel, "Gold", modelGoldPlayer);

		final SpinnerModel modelWoodPlayer = new SpinnerNumberModel(0, 0,
				playerResources[ResourceCubeType.WOOD.getValue()], 1);
		spinners[3] = addLabeledSpinner(panel, "Wood", modelWoodPlayer);

		final JLabel lblBank = new JLabel("Bank");
		panel.add(lblBank);

		final SpinnerModel modelFavorBank = new SpinnerNumberModel(0, 0,
				bankResources[ResourceCubeType.FAVOR.getValue()], 1);
		spinners[4] = addLabeledSpinner(panel, "Favor", modelFavorBank);

		final SpinnerModel modelFoodBank = new SpinnerNumberModel(0, 0,
				bankResources[ResourceCubeType.FOOD.getValue()], 1);
		spinners[5] = addLabeledSpinner(panel, "Food", modelFoodBank);

		final SpinnerModel modelGoldBank = new SpinnerNumberModel(0, 0,
				bankResources[ResourceCubeType.GOLD.getValue()], 1);
		spinners[6] = addLabeledSpinner(panel, "Gold", modelGoldBank);

		final SpinnerModel modelWoodBank = new SpinnerNumberModel(0, 0,
				bankResources[ResourceCubeType.WOOD.getValue()], 1);
		spinners[7] = addLabeledSpinner(panel, "Wood", modelWoodBank);

		final JButton btnConfirm = new JButton("Trade");
		btnConfirm.addActionListener(confirmListener);
		panel.add(btnConfirm);

		SpringUtilities.makeCompactGrid(panel, 5, 3, 10, 10, 6, 10);

		add(panel);
		pack();
		setLocationRelativeTo(parentFrame);

	}

	private JSpinner addLabeledSpinner(final JPanel c, final String label,
			final SpinnerModel model) {
		final JLabel l = new JLabel(label);
		c.add(l);

		final JSpinner spinner = new JSpinner(model);
		l.setLabelFor(spinner);

		final JFormattedTextField tf = ((JSpinner.DefaultEditor) spinner
				.getEditor()).getTextField();
		tf.setEditable(false);
		tf.setFocusable(true);

		c.add(spinner);

		return spinner;
	}

	/**
	 * 
	 * @return an array with the resources that need to be added to the player
	 *         and subtracted from the bank
	 */
	public int[] getUpdatedResources() {
		return this.updatedResources;
	}

	/**
	 * Listener to "Trade" JButton. When the user clicks the button it will
	 * trigger this listener, that will verify and trade the selected resources.
	 * 
	 * @author grolfsen
	 *
	 */
	private class ConfirmTradeListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			int qty1 = 0;
			int qty2 = 0;

			final int qtySelected[] = new int[8];
			for (int i = 0; i < spinners.length; i++) {
				qtySelected[i] = (Integer) spinners[i].getValue();
				if (i < 4) {
					qty1 += qtySelected[i];
				}
				if (i >= 4) {
					qty2 += qtySelected[i];
				}
			}
			if (qty1 != qty2) {
				// TODO: Error message.
				System.out.println("ERROR!");
			} else {
				for (int i = 0; i < 4; i++) {
					updatedResources[i] = qtySelected[i] - qtySelected[i + 4];
				}

				dispose();
			}
		}
	}

}
