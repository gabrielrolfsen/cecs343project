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
import utils.Types.CultureType;

/**
 * @author grolfsen
 *
 */
public class HandDialog extends JDialog {

	private final JLayeredPane layeredPane = new JLayeredPane();
	final JPanel panel = new JPanel(new GridBagLayout());

	private final SelectListener selectListener = new SelectListener();
	private Card selectedCard = null;

	private ArrayList<Card> mPlayerHand = new ArrayList<Card>();

	public HandDialog(final JFrame parentFrame, final CultureType culture, final ArrayList<Card> playerHand) {
		super(parentFrame, "Select a Card to Play", true);
		this.mPlayerHand = playerHand;

		// Calculate Panel Width based on the hand size
		final int panelWidth = playerHand.size() * 300;

		layeredPane.setPreferredSize(new Dimension(1300, 280));
		panel.setPreferredSize(new Dimension(1300, 280));
		setResizable(false);
		final GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.5;
		c.gridy = 0;

		int i = 0;
		for (final Card card : playerHand) {
			final ImageIcon ii = new ImageIcon("res/action_cards/" + culture.toString().toLowerCase() + "/"
					+ card.getType().toString().toLowerCase() + ".png");

			// Add the button with the image on it
			final JButton btn = new JButton("", ii);
			// Set the button properties
			btn.setBounds(i * ii.getIconWidth(), 0, ii.getIconWidth(), ii.getIconHeight());
			btn.setActionCommand(String.valueOf(i));
			btn.addActionListener(selectListener);
			c.gridx = i;
			// Add the button to panel
			layeredPane.add(btn, c, new Integer(0));
			i++;
		}
		final JButton passBtn = new JButton("Pass Turn");
		passBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				dispose();
			}
		});
		passBtn.setBounds(0, 257, 1260, 20);
		layeredPane.add(passBtn, new Integer(0));

		panel.add(layeredPane);
		add(panel);
		pack();
		setLocationRelativeTo(parentFrame);
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
			selectedCard = mPlayerHand.get(pos);

			// Dismiss Dialog
			setVisible(false);
		}
	}
}
