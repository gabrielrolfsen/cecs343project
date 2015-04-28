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
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import models.Card;
import utils.Types.CardType;

/**
 * @author grolfsen
 *
 */
public class CardDialog extends JDialog {

	private final JLayeredPane layeredPane = new JLayeredPane();
	final JPanel panel = new JPanel(new GridBagLayout());
	private int mMaxQtyToSelect = 0;

	private final AddSelectListener addSelectListener = new AddSelectListener();
	private final ArrayList<Card> selectedCards = new ArrayList<Card>();
	private final JButton[] mBtns = new JButton[7];
	private final JLabel[] mSelectionLabels = new JLabel[7];
	private final JSpinner mSpinnerRandomCard = new JSpinner();
	private int lastSpinnerValue = 0;

	public CardDialog(final JFrame parentFrame, final int qty) {
		super(parentFrame, "Select Cards to your hand", true);
		this.mMaxQtyToSelect = qty;
		// Add a Layered Pane for Cards
		layeredPane.setPreferredSize(new Dimension(1265, 340));

		// Set the Random Card Spinner with a maximum of "qty"
		final SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, qty, 1);
		mSpinnerRandomCard.setModel(spinnerModel);

		mSpinnerRandomCard.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(final ChangeEvent arg0) {
				final int value = ((Integer) ((JSpinner) arg0.getSource())
						.getValue());
				System.out.println("VALUE: " + value);
				System.out.println("mMaxQty: " + mMaxQtyToSelect);
				System.out.println("lastSpinner: " + lastSpinnerValue);
				if (value > lastSpinnerValue) {
					mMaxQtyToSelect--;
				} else {
					mMaxQtyToSelect++;
				}

				lastSpinnerValue = value;

				if (mMaxQtyToSelect == 0) {
					for (int i = 0; i < 7; i++) {
						if (mSelectionLabels[i] == null) {
							mBtns[i].setEnabled(false);
						}
					}
				} else {
					for (int i = 0; i < 7; i++) {
						if (mSelectionLabels[i] == null) {
							mBtns[i].setEnabled(true);
						}
					}

				}
			}
		});

		panel.setPreferredSize(new Dimension(1265, 340));
		setResizable(false);

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
			// Add button to array of buttons
			mBtns[i] = btn;
			// Add button to panel
			layeredPane.add(btn, new Integer(0));
		}
		final JButton confirmBtn = new JButton("Add to hand");

		confirmBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				dispose();
			}
		});
		confirmBtn.setBounds(0, 257, 1260, 20);
		layeredPane.add(confirmBtn, new Integer(0));
		mSpinnerRandomCard.setBounds(0, 300, 40, 40);
		layeredPane.add(mSpinnerRandomCard, new Integer(0));

		panel.add(layeredPane);

		add(panel);
		pack();
		setLocationRelativeTo(parentFrame);
	}

	public ArrayList<Card> getSelectedCards() {
		return this.selectedCards;
	}

	private class AddSelectListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			final int pos = Integer.parseInt(e.getActionCommand());

			// If the selected Card was NOT previously selected
			if (mSelectionLabels[pos] == null) {
				final JLabel xLabel = new JLabel("X");
				xLabel.setFont(new Font("Serif", Font.PLAIN, 120));
				xLabel.setForeground(Color.red);
				mSelectionLabels[pos] = xLabel;

				// Add card to Selected Cards Array
				selectedCards.add(new Card(CardType.getType(pos), CardType
						.getType(pos).getCost()));
				xLabel.setBounds((pos * 180) + 40, 85, 110, 110);
				layeredPane.add(xLabel, new Integer(100));

				// Decrease Quantity of cards that player can select
				mMaxQtyToSelect--;

				/*
				 * If There's no more selection Avaliable, disable all buttons
				 * that were NOT selected.
				 */
				if (mMaxQtyToSelect == 0) {
					for (int i = 0; i < 7; i++) {
						if (mSelectionLabels[i] == null) {
							mBtns[i].setEnabled(false);
						}
					}
				}
				// If card Selected WAS previously selected
			} else {

				// TODO: Possibly refactoring is needed
				final Iterator<Card> iter = selectedCards.iterator();

				// Remove Card from SelectedCards Array
				while (iter.hasNext()) {
					final Card card = iter.next();

					if (card.getType() == CardType.getType(pos)) {
						iter.remove();
					}
				}

				// Remove the Label from Panel and from array
				layeredPane.remove(mSelectionLabels[pos]);
				mSelectionLabels[pos] = null;

				// Reactive Disabled Buttons
				if (mMaxQtyToSelect == 0) {
					for (int i = 0; i < 7; i++) {
						if (mSelectionLabels[i] == null) {
							mBtns[i].setEnabled(true);
						}
					}
				}

				// Increment Max Qty To Select
				mMaxQtyToSelect++;
			}

			System.out.println("MAX QTY: " + mMaxQtyToSelect);
			/*
			 * If RandomCards JSpinner has a number greater than the max Qty
			 * allowed to select, set it to maximum instead.
			 */
			if ((Integer) mSpinnerRandomCard.getValue() > mMaxQtyToSelect) {
				mSpinnerRandomCard.setValue(mMaxQtyToSelect);
			}

			// Change Maximum Value allowed on JSpinner
			final SpinnerNumberModel s = (SpinnerNumberModel) mSpinnerRandomCard
					.getModel();
			s.setMaximum(mMaxQtyToSelect);

			// Refresh the layout
			layeredPane.revalidate();
			layeredPane.repaint();
		}

	}
}
