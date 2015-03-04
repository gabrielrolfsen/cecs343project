/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Feb 23, 2015
 */
package models;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

		final JPanel panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(350, 700));
		final GridBagConstraints c = new GridBagConstraints();

		for (i = 0; i < tiles.size(); i++) {
			final JButton button = new JButton("", tiles.get(i).getIcon());
			// If the user cannot pick the tile, the button is disabled
			if (freeTerrainCounter[tiles.get(i).getType().getValue()] == 0) {
				button.setEnabled(false);
			}
			// Put the button index as Action Command
			button.setActionCommand(Integer.toString(i));
			// Set-up a JButton listener
			button.addActionListener(confirmListener);
			// * Button Layout Constraints * //
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.5;
			c.gridx = (i % 3);
			c.gridy = (i / 3);
			// Add it to the Panel
			panel.add(button, c);
		}

		final JButton passBtn = new JButton("Pass");
		passBtn.addActionListener(passTurnListener);
		// * Button Layout Constraints * //
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0; // reset to default
		c.weighty = 1.0; // request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; // bottom of space
		c.insets = new Insets(10, 0, 0, 0); // top padding
		c.gridx = 0;
		c.gridwidth = 3;
		c.gridy = (tiles.size() / 3) + 1;
		panel.add(passBtn, c);

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
