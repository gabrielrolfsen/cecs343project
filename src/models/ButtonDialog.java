/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Feb 23, 2015
 */
package models;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author grolfsen
 *
 */
class ButtonDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final String selected = null;
	final Icon icon = new ImageIcon("res/ResourceFood.jpg");

	public ButtonDialog(final JFrame frame, final String title,
			final ArrayList<ResourceTile> tiles) {
		super(frame, title, true);
		int numRows = tiles.size() / 3;
		if (tiles.size() % 3 != 0) {
			numRows++;
		}
		final GridLayout layout = new GridLayout(numRows, 3);
		final JPanel panel = new JPanel(layout);
		final ActionListener confirmListener = new ConfirmListener();
		int i = 0;
		for (i = 0; i < tiles.size(); i++) {
			// TODO: If necessary, store the position to be able to delete
			// afterwards
			// TODO: Add the correct icons.
			final JButton button = new JButton(Integer.toString(tiles.get(i)
					.getType()), icon);
			panel.add(button);
			button.addActionListener(confirmListener);
		}

		// TODO: Add the correct Icon
		// TODO: Make the layout fit this last button
		final JButton passBtn = new JButton("Pass", icon);
		panel.add(passBtn);

		add(panel);
		pack();
		setLocationRelativeTo(frame);

	}

	public String getSelected() {
		return this.selected;
	}

	private class ConfirmListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			// TODO: Verify if the player can actually pick the Tile and throw
			// the specific error.
			// TODO: If the player can pick it, remove from the tiles Array.
			System.out.println("Button Clicked: " + e.getActionCommand());

			ButtonDialog.this.setVisible(false);
		}
	}

}
