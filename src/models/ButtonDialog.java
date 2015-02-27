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

		// TODO: Get a better layout, fixed size
		final GridLayout layout = new GridLayout(6, 3);
		final JPanel panel = new JPanel(layout);

		for (i = 0; i < tiles.size(); i++) {
			final JButton button = new JButton("", tiles.get(i).getIcon());
			// If the user cannot pick the tile, the button is disabled
			if (freeTerrainCounter[tiles.get(i).getType()] == 0) {
				button.setEnabled(false);
			}
			panel.add(button);
			// Put the button index as Action Command
			button.setActionCommand(Integer.toString(i));
			// Set-up a JButton listener
			button.addActionListener(confirmListener);
		}

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
			System.out.println("USER has selected Index: "
					+ e.getActionCommand());

			// Remove the button from the array.
			tiles.remove(Integer.parseInt((e.getActionCommand())));

			setVisible(false);
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
