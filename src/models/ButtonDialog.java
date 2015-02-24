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
	ArrayList<ResourceTile> tiles = new ArrayList<ResourceTile>();

	public ButtonDialog(final JFrame frame, final String title,
			final ArrayList<ResourceTile> tiles) {
		super(frame, title, true);
		this.tiles = tiles;
		int numRows = tiles.size() / 3;
		if (tiles.size() % 3 != 0) {
			numRows++;
		}
		final GridLayout layout = new GridLayout(numRows, 3);
		final JPanel panel = new JPanel(layout);
		final ActionListener confirmListener = new ConfirmListener();
		int i = 0;
		for (i = 0; i < tiles.size(); i++) {
			final JButton button = new JButton(Integer.toString(tiles.get(i)
					.getType()), tiles.get(i).getIcon());
			panel.add(button);
			button.setActionCommand(Integer.toString(i));
			button.addActionListener(confirmListener);
		}

		// TODO: Make the layout fit this last button
		final JButton passBtn = new JButton("Pass");
		panel.add(passBtn);

		add(panel);
		pack();
		setLocationRelativeTo(frame);

	}

	public void start() {

	}

	public String getSelected() {
		return this.selected;
	}

	private class ConfirmListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			final JButton button = (JButton) e.getSource();
			tiles.remove(String.valueOf((button.getActionCommand())));
			// TODO: Verify if the player can actually pick the Tile and throw
			// the specific error.
			// TODO: If the player can pick it, remove from the tiles Array.
			System.out.println("Button Clicked: " + e.getActionCommand());

			// TODO: Do the Routine
		}
	}

}
