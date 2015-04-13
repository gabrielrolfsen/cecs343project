/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 13, 2015
 */
package views;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.Types.UnitType;

/**
 * @author grolfsen
 *
 */
public class UnitView extends ImageIcon {

	public UnitView(final UnitType type) {

		final ImageIcon icon = new ImageIcon("res/units/"
				+ type.toString().toLowerCase() + ".png");
		System.out.println("res/units/" + type.toString().toLowerCase()
				+ ".png");
		setImage(icon.getImage());
	}

	public void draw(final JPanel parentPanel, final int pos) {
		final JLabel graphic = new JLabel(this);
		graphic.setSize(30, 30);
		graphic.setLocation(200 + (pos * 30), 150);
		parentPanel.add(graphic);
		graphic.setVisible(true);
		// TODO: Debug
		System.out.println("Drawing at x: " + graphic.getX() + " y: 50");
	}
}
