/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 14, 2015
 */
package views;

import java.awt.Color;
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
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import models.Card;
import utils.Types.CardType;

/**
 * @author grolfsen
 *
 */
public class CardDialog extends JDialog {

	private final JLayeredPane layeredPane = new JLayeredPane();
	final JPanel panel = new JPanel(new GridBagLayout());

	private final AddSelectListener addSelectListener = new AddSelectListener();
	private final ConfirmListener confirmListener = new ConfirmListener();
	private final ArrayList<Card> selectedCards = new ArrayList<Card>();

	private final ArrayList<Card> playerHand = new ArrayList<Card>();

	public CardDialog(final JFrame parentFrame) {
		super(parentFrame, "Select Cards to your hand", true);
		// Add a Layered Pane for Cards
		layeredPane.setPreferredSize(new Dimension(1265, 280));

		panel.setPreferredSize(new Dimension(1265, 280));
		setResizable(false);
		final GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.5;
		c.gridy = 0;

		// Add the Buttons
		for (final CardType v : CardType.values()) {
			final int i = v.getValue();
			// final ImageIcon ii = new ImageIcon("res/action_cards/"
			// + v.toString().toLowerCase() + ".png");
			final ImageIcon ii = new ImageIcon(
					"res/victory_cards/the_wonder.png");
			final int scale = 3; // 3 times smaller
			final int width = ii.getIconWidth();
			final int newWidth = width / scale;
			final ImageIcon ic = new ImageIcon(ii.getImage().getScaledInstance(
					newWidth, -1, java.awt.Image.SCALE_SMOOTH));
			final JButton btn = new JButton(v.toString());

			// Set the button properties
			btn.setBounds(i * ic.getIconWidth(), 0, ic.getIconWidth(),
					ic.getIconHeight());
			btn.setActionCommand(String.valueOf(i));
			btn.addActionListener(addSelectListener);
			c.gridx = i;
			// Add the button to panel
			layeredPane.add(btn, c, new Integer(0));
		}
		final JButton confirmBtn = new JButton("Add to hand");
		// TODO: Confirm if it's necessary to add listener
		confirmBtn.addActionListener(confirmListener);
		c.gridy = 1;
		c.gridx = 0;
		confirmBtn.setBounds(0, 257, 1260, 20);
		layeredPane.add(confirmBtn, c, new Integer(0));
		panel.add(layeredPane);

		add(panel);
		pack();
		setLocationRelativeTo(parentFrame);
	}

	public ArrayList<Card> getSelectedCards() {
		return this.selectedCards;
	}

	private class ConfirmListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			setVisible(false);
		}
	}

	private class AddSelectListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			// Add selected card to array
			// Draw an "O" on the button

			final JLabel xLabel = new JLabel("X");
			xLabel.setForeground(Color.red);
			final int pos = Integer.parseInt(e.getActionCommand());
			// Add card to Selected Cards Array
			for (final CardType v : CardType.values()) {
				if (v.getValue() == pos) {
					selectedCards.add(new Card(v));
				}
			}

			xLabel.setBounds(90 + pos * 180, 125, 30, 40);
			layeredPane.add(xLabel, new Integer(100));

			// Refresh the layout
			layeredPane.revalidate();
			layeredPane.repaint();
		}
	}

}
