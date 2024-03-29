/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Feb 23, 2015
 */
package views;

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

import models.ResourceTile;

/**
 * @author grolfsen
 *
 */
public class ButtonDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private int indexSelected = -1;
	final ActionListener confirmListener = new PickTileListener();
	final ActionListener passTurnListener = new PassTurnListener();

	public ButtonDialog(final JFrame parentFrame,
			final ArrayList<ResourceTile> tiles, final int[] terrainCounter) {
		// TODO: Try to implement a way it doesn't block the main Frame (modal:
		// false)
		super(parentFrame, "Pick a terrain", true);
		int i = 0;

		// Creates the layout for the Dialog
		final JPanel panel = new JPanel(new GridBagLayout());
		// TODO: Use constraints on dimensions
		panel.setPreferredSize(new Dimension(350, 700));
		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		for (i = 0; i < tiles.size(); i++) {
			final JButton button = new JButton("", tiles.get(i).getIcon());
			// If the user cannot pick the tile, the button is disabled
			if (terrainCounter[tiles.get(i).getType().getValue()] == 0) {
				button.setEnabled(false);
			}
			// Put the button index as Action Command
			button.setActionCommand(Integer.toString(i));
			// Set-up a JButton listener
			button.addActionListener(confirmListener);
			// * Button Layout Constraints * //
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
		c.gridy = (tiles.size() / 3) + 1; // set y based on array size
		panel.add(passBtn, c);

		add(panel);
		pack();
		setLocationRelativeTo(parentFrame);

	}

	public int getIndexSelected() {
		return this.indexSelected;
	}

	/**
	 * Listener to JButtons. When the user clicks one of the Buttons with a
	 * ResourceTile on it, it will trigger this listener
	 * 
	 * @author grolfsen
	 *
	 */
	private class PickTileListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			/*
			 * Get the tileSelected by using the array index number on the
			 * button
			 */
			indexSelected = Integer.parseInt((e.getActionCommand()));

			// Dispose the JDialog
			setVisible(false);
			dispose();

		}
	}

	/**
	 * Listener to " pass"JButton. When the user clicks the button it will
	 * trigger this listener, that just make the tileSelected null and dispose
	 * the JDialog
	 * 
	 * @author grolfsen
	 *
	 */
	private class PassTurnListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			// Set the index as -1, so the controller knows that turn was passed
			indexSelected = -1;
			dispose();
		}
	}

}
