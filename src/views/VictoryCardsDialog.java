/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 14, 2015
 */
package views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.Types.ResourceCubeType;
import utils.Types.VictoryCardType;

/**
 * @author grolfsen
 *
 */
public class VictoryCardsDialog extends JDialog {

	final ActionListener addCubeListener = new AddCubeListener();

	private final JLabel[] lblCubesCounter = new JLabel[4];
	private final JButton[] victoryCards = new JButton[4];
	private int[] cubesCounter = new int[4];
	private final int[] mPlayerResources;

	public VictoryCardsDialog(final JFrame parentFrame,
			final int[] playerResources, final int[] cubesCounter) {
		super(parentFrame, "Place Victory Cubes", true);
		this.mPlayerResources = playerResources;
		this.cubesCounter = cubesCounter;

		final JPanel panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(800, 300));
		setResizable(false);
		final GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.5;

		// Add the Buttons
		for (final VictoryCardType v : VictoryCardType.values()) {
			final int i = v.getValue();
			final ImageIcon ii = new ImageIcon("res/victory_cards/"
					+ v.toString().toLowerCase() + ".png");
			final int scale = 3; // 3 times smaller
			final int width = ii.getIconWidth();
			final int newWidth = width / scale;
			final ImageIcon ic = new ImageIcon(ii.getImage().getScaledInstance(
					newWidth, -1, java.awt.Image.SCALE_SMOOTH));

			final JButton btn = new JButton("", ic);

			// btn.setPreferredSize(new Dimension(150, 400));
			btn.setActionCommand(String.valueOf(i));
			btn.addActionListener(addCubeListener);

			c.gridy = 0;
			c.gridx = i;
			// Add the button to panel
			panel.add(btn, c);
			lblCubesCounter[i] = new JLabel();
			victoryCards[i] = btn;
			c.gridy = 1;
			panel.add(lblCubesCounter[i], c);
		}

		for (int i = 0; i < cubesCounter.length; i++) {
			lblCubesCounter[i].setText("x " + String.valueOf(cubesCounter[i]));
		}

		checkPlayerResources();

		add(panel);
		pack();
		setLocationRelativeTo(parentFrame);
	}

	/**
	 * Checks if player has at least one Victory Cube to add to a Victory Card,
	 * if not, disable all buttons
	 */
	private void checkPlayerResources() {
		if (mPlayerResources[ResourceCubeType.VICTORY.getValue()] == 0) {
			for (final JButton btn : victoryCards) {
				btn.setEnabled(false);
			}
		}
	}

	private class AddCubeListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			if (mPlayerResources[ResourceCubeType.VICTORY.getValue()] > 0) {
				final int i = Integer.parseInt(e.getActionCommand());
				// Get the current number on the label
				int value = Integer.parseInt(lblCubesCounter[i].getText()
						.substring(2, 3));
				value++; // Increment it
				lblCubesCounter[i].setText("x " + value); // Set new value
				cubesCounter[i]++; // Increase the cubeCounter to the card
				// Decrease player victory cubes
				mPlayerResources[ResourceCubeType.VICTORY.getValue()]--;
			}

			// If player doesn't have more victory cubes, disable buttons
			checkPlayerResources();

		}
	}
}
