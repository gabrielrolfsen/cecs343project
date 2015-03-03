/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Mar 3, 2015
 */
package models;

/**
 * @author grolfsen
 *
 */
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingUtilities;

public class ButtonDialogLayout extends JFrame {

	public ButtonDialogLayout() {

		initUI();
	}

	public final void initUI() {

		final JPanel panel = new JPanel();

		final JButton button = new JButton("button");
		panel.add(button);

		final JTree tree = new JTree();
		panel.add(tree);

		add(panel);

		pack();

		setTitle("FlowLayout Example");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void main(final String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				final ButtonDialogLayout ex = new ButtonDialogLayout();
				ex.setVisible(true);
			}
		});
	}
}