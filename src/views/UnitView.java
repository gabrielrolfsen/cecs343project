/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 13, 2015
 */
package views;

import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.Types.UnitType;

/**
 * @author grolfsen
 *
 */
public class UnitView extends ImageIcon {

	final private JPanel mParentPanel;
	UnitType mType;
	final JLabel mLabel = new JLabel(this);
	int mPos;

	public UnitView(final JPanel parentPanel, final UnitType type, final int pos) {
		this.mParentPanel = parentPanel;
		this.mType = type;
		this.mPos = pos;
		final ImageIcon icon = new ImageIcon("res/units/"
				+ type.toString().toLowerCase() + ".png");
		setImage(icon.getImage());
		draw();

	}

	public JLabel getLabel() {
		return this.mLabel;
	}

	public void draw() {
		final Insets insFrame = mParentPanel.getInsets();
		mLabel.setText("AFFERSON");
		mLabel.setSize(30, 30);
		mLabel.setLocation(200 + (mPos * 30), insFrame.top + 20);
		mParentPanel.add(mLabel);
		mLabel.setVisible(true);
		mLabel.setBounds(150, 50, 30, 30);
		// TODO: Debug
		System.out.println("Drawing at x: " + mLabel.getX() + " y: "
				+ mLabel.getY());
	}
}
