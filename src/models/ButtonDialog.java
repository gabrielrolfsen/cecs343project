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
public class ButtonDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private ResourceTile tileSelected = null;
	private final int pickCounter = 0;
	private final JFrame frame;
	ArrayList<ResourceTile> tiles = new ArrayList<ResourceTile>();
	final ActionListener confirmListener = new PickTileListener();
	final ActionListener passTurnListener = new PassTurnListener();

	public ButtonDialog(final JFrame frame, final ArrayList<ResourceTile> tiles) {
		super(frame, "Pick a terrain", true);
		this.frame = frame;
		this.tiles = tiles;

		// Creates a layout for 18 tiles
		final GridLayout layout = new GridLayout(6, 3);
		final JPanel panel = new JPanel(layout);

		// Set-up a JButton listener
		int i = 0;
		for (i = 0; i < tiles.size(); i++) {
			final JButton button = new JButton("", tiles.get(i).getIcon());
			panel.add(button);
			button.setActionCommand(Integer.toString(i));
			button.addActionListener(confirmListener);
		}

		// TODO: Make the layout fit this last button.
		final JButton passBtn = new JButton("Pass");
		passBtn.addActionListener(passTurnListener);
		panel.add(passBtn);

		add(panel);
		pack();
		setLocationRelativeTo(frame);

	}

	public ResourceTile getSelected() {
		return this.tileSelected;
	}

	private class PickTileListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			// DEBUG
			System.out.println("Button Clicked: " + e.getActionCommand());

			// Add the tile to the return object
			tileSelected = tiles.get(Integer.valueOf(e.getActionCommand()));

			// Remove the button from the array.
			tiles.remove(Integer.valueOf(e.getActionCommand()));

			// TODO: Verify if the player can actually pick the Tile and throw
			// the specific error.
			dispose();
		}
	}

	private class PassTurnListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			tileSelected = null;
			dispose();
		}
	}

}
