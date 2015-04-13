/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 10, 2015
 */
package views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import utils.Types.ResourceCubeType;

/**
 * @author grolfsen
 *
 */
public class TradeDialog extends JDialog {

	final ActionListener confirmListener = new ConfirmTradeListener();
	final JSpinner[] spinners = new JSpinner[10];
	int updatedResources[] = new int[4];

	public TradeDialog(final JFrame parentFrame, final int[] bankResources,
			final int[] playerResources) {
		super(parentFrame, "Select Resources to Trade", true);

		final GridBagLayout b = new GridBagLayout();
		setResizable(false);
		// Creates the layout for the Dialog
		final JPanel panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(300, 210));
		// TODO: Use constraints on dimensions
		// TODO: Remove "X" close button from dialog
		final JLabel lblPlayer = new JLabel("Player");
		final GridBagConstraints c = new GridBagConstraints();

		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;

		panel.add(lblPlayer, c);

		final JLabel lblBank = new JLabel("Bank");
		c.gridx = 2;
		c.gridy = 0;

		panel.add(lblBank, c);

		for (final ResourceCubeType type : ResourceCubeType.values()) {
			final SpinnerModel model = new SpinnerNumberModel(0, 0,
					playerResources[type.getValue()], 1);
			final int index = type.getValue();
			spinners[index] = addSpinner(panel, type.getName(), model, index,
					true);
		}

		// Disable the Victory Point Trading
		spinners[4].setEnabled(false);

		for (final ResourceCubeType type : ResourceCubeType.values()) {
			final SpinnerModel model = new SpinnerNumberModel(0, 0,
					bankResources[type.getValue()], 1);
			final int index = type.getValue() + 5;
			spinners[index] = addSpinner(panel, type.getName(), model, index,
					false);
		}

		// Disable the Victory Point Trading - Only available if player has
		// wonder
		spinners[9].setEnabled(false);

		final JButton btnConfirm = new JButton("Trade");
		btnConfirm.addActionListener(confirmListener);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_END; // bottom of space
		c.insets = new Insets(10, 0, 0, 0); // top padding
		c.ipady = 10; // padding
		c.gridwidth = 3; // occupies 3 rows
		c.gridx = 0;
		c.gridy = 6;
		panel.add(btnConfirm, c);

		add(panel);
		pack();
		setLocationRelativeTo(parentFrame);

	}

	private JSpinner addSpinner(final JPanel panel, final String label,
			final SpinnerModel model, final int index, final boolean isLabeled) {
		final JSpinner spinner = new JSpinner(model);
		final GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.5;

		if (isLabeled) {
			final JLabel l = new JLabel(label, SwingConstants.RIGHT);
			c.gridx = (index / 5);
			c.gridy = (index % 5) + 1;
			panel.add(l, c);
			l.setLabelFor(spinner);
		}

		// Settings to the spinner textfield
		final JFormattedTextField tf = ((JSpinner.DefaultEditor) spinner
				.getEditor()).getTextField();
		final JComponent field = (spinner.getEditor());
		Dimension prefSize = field.getPreferredSize();
		prefSize = new Dimension(50, prefSize.height);
		field.setPreferredSize(prefSize);
		tf.setEditable(false);
		tf.setFocusable(true);

		// * Button Layout Constraints * //
		c.gridx = (index / 5) + 1;

		// Add it to the Panel
		panel.add(spinner, c);

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

			final int qtySelected[] = new int[10];
			for (int i = 0; i < 4; i++) {
				qtySelected[i] = (Integer) spinners[i].getValue();
				qty1 += qtySelected[i];
			}

			for (int i = 5; i < spinners.length; i++) {
				qtySelected[i] = (Integer) spinners[i].getValue();
				qty2 += qtySelected[i];
			}
			if (qty1 != qty2) {
				// TODO: Error message.
				System.out.println("ERROR!");
			} else {
				for (int i = 0; i < 4; i++) {
					updatedResources[i] = qtySelected[i] - qtySelected[i + 5];
				}

				dispose();
			}
		}
	}

}