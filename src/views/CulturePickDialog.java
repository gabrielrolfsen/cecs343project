/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Mar 9, 2015
 */
package views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author grolfsen
 *
 */
public class CulturePickDialog extends JDialog {

	PickCultureListener pickListener = new PickCultureListener();
	String culturePicked;

	public CulturePickDialog(final JFrame parentFrame) {
		super(parentFrame, "Pick a culture", true);

		// Creates the layout for the Dialog
		final JPanel panel = new JPanel(new GridBagLayout());
		// TODO: Use constraints on dimensions
		panel.setPreferredSize(new Dimension(500, 165));
		final GridBagConstraints c = new GridBagConstraints();

		final JButton btnEgypt = new JButton("", new ImageIcon(
				"res/ic_egyptian.png"));
		btnEgypt.setActionCommand("Egyptian");
		final JButton btnGreek = new JButton("", new ImageIcon(
				"res/ic_greek.png"));
		btnGreek.setActionCommand("GREEK");
		final JButton btnNorse = new JButton("", new ImageIcon(
				"res/ic_norse.png"));
		btnNorse.setActionCommand("Norse");

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		// Add it to the Panel
		panel.add(btnEgypt, c);
		c.gridx = 1;
		panel.add(btnGreek, c);
		c.gridx = 2;
		panel.add(btnNorse, c);

		btnGreek.addActionListener(pickListener);
		btnEgypt.addActionListener(pickListener);
		btnNorse.addActionListener(pickListener);

		add(panel);
		pack();
		setLocationRelativeTo(parentFrame);
	}

	public String getCulturePicked() {
		return this.culturePicked;
	}

	private class PickCultureListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			culturePicked = e.getActionCommand();
			dispose();
			setVisible(false);
		}
	}

}
