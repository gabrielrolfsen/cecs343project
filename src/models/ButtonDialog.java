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
	private int[] freeTerrainCounter = new int[6];
	ArrayList<ResourceTile> tiles = new ArrayList<ResourceTile>();
	final ActionListener confirmListener = new PickTileListener();
	final ActionListener passTurnListener = new PassTurnListener();

	public ButtonDialog(final JFrame frame,
			final ArrayList<ResourceTile> tiles, final int[] terrainCounter) {
		super(frame, "Pick a terrain", true);
		this.tiles = tiles;
		this.freeTerrainCounter = terrainCounter;
		int i = 0;

		// Creates a layout for 18 tiles
		final GridLayout layout = new GridLayout(6, 3);
		final JPanel panel = new JPanel(layout);

		// Set-up a JButton listener

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

	public ArrayList<ResourceTile> getList() {
		return this.tiles;
	}

	public int[] getTerrainCounter() {
		return this.freeTerrainCounter;
	}

	private class PickTileListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			tileSelected = tiles.get(Integer.valueOf(e.getActionCommand()));

			// DEBUG
			System.out.println("Button Clicked: " + e.getActionCommand());
			System.out.println("Terrains left:"
					+ freeTerrainCounter[tileSelected.getType()]);

			// TODO: possible disable the button
			if (freeTerrainCounter[tileSelected.getType()] == 0) {
				// User cannot pick, because it doesn't fit. He has to pick
				// another one
			} else {
				// Add the tile to the return object

				// Remove the button from the array.
				tiles.remove(Integer.valueOf(e.getActionCommand()));

				dispose();
			}
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
