/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 13, 2015
 */
package views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import models.Card;

/**
 * @author grolfsen
 *
 */
public class HandDialog extends JDialog {

	private final JLayeredPane layeredPane = new JLayeredPane();
	final JPanel panel = new JPanel(new GridBagLayout());

	private final SelectListener selectListener = new SelectListener();
	private Card selectedCard = null;

	private final ArrayList<Card> playerHand = new ArrayList<Card>();

	public HandDialog(final JFrame parentFrame, final ArrayList<Card> playerHand) {
		super(parentFrame, "Select a Card to Play", true);
		layeredPane.setPreferredSize(new Dimension(1300, 260));

		// Calculate Panel Width based on the hand size
		final int panelWidth = playerHand.size() * 300;
		panel.setPreferredSize(new Dimension(1300, 260));
		setResizable(false);
		final GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.5;
		c.gridy = 0;

		int i = 0;
		for (final Card card : playerHand) {
			// final ImageIcon ii = new ImageIcon("res/action_cards/"
			// + c.getType().toString().toLowerCase() + ".png");
			final ImageIcon ii = new ImageIcon(
					"res/victory_cards/the_wonder.png");
			final int scale = 3; // 3 times smaller
			final int width = ii.getIconWidth();
			final int newWidth = width / scale;
			final ImageIcon ic = new ImageIcon(ii.getImage().getScaledInstance(
					newWidth, -1, java.awt.Image.SCALE_SMOOTH));
			final JButton btn = new JButton(card.getType().toString());
			// Set the button properties
			btn.setBounds(i * ic.getIconWidth(), 0, ic.getIconWidth(),
					ic.getIconHeight());
			btn.setActionCommand(String.valueOf(i));
			btn.addActionListener(selectListener);
			c.gridx = i;
			// Add the button to panel
			layeredPane.add(btn, c, new Integer(0));
			i++;
		}
	}

	/**
	 * 
	 * @return Selected card by user when pressed the button
	 */
	public Card getSelectedCard() {
		return this.selectedCard;
	}

	private class SelectListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			// Get the position of the selected Card
			final int pos = Integer.parseInt(e.getActionCommand());

			// Get the selected card from player's hand
			selectedCard = playerHand.get(pos);

			// Dismiss Dialog
			setVisible(false);
		}
	}
}
