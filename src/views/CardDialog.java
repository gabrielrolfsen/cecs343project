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
import javax.swing.JPanel;

import utils.Types.CardType;

/**
 * @author grolfsen
 *
 */
public class CardDialog extends JDialog {

	private final AddSelectListener addSelectListener = new AddSelectListener();

	public CardDialog(final JFrame parentFrame) {
		super(parentFrame, "Place Victory Cubes", true);
		final JPanel panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(800, 300));
		setResizable(false);
		final GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.5;
		c.gridy = 0;

		// Add the Buttons
		for (final CardType v : CardType.values()) {
			final int i = v.getValue();
			final ImageIcon ii = new ImageIcon("res/action_cards/"
					+ v.toString().toLowerCase() + ".png");
			final int scale = 3; // 3 times smaller
			final int width = ii.getIconWidth();
			final int newWidth = width / scale;
			final ImageIcon ic = new ImageIcon(ii.getImage().getScaledInstance(
					newWidth, -1, java.awt.Image.SCALE_SMOOTH));

			final JButton btn = new JButton("", ic);

			btn.setPreferredSize(new Dimension(150, 400));
			btn.setActionCommand(String.valueOf(i));
			btn.addActionListener(addSelectListener);
			c.gridx = i;
			// Add the button to panel
			panel.add(btn, c);
		}

		add(panel);
		pack();
		setLocationRelativeTo(parentFrame);
	}

	private class AddSelectListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {

		}
	}

}
