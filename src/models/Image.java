import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Image extends JFrame {

	public Image() {

		String input = JOptionPane
				.showInputDialog("Please choose your board: Greek, Norse or Egyptian:");
		if (input.equalsIgnoreCase("Greek")) {
			initGreek();
		}

		if (input.equalsIgnoreCase("Norse")) {
			initNorse();
		}

		if (input.equalsIgnoreCase("Egyptian")) {
			initEgypt();
		}
	}

	public void initGreek() {

		add(new GreekBoard());

		pack();

		setTitle("Greek Board");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public void initNorse() {

		add(new NorseBoard());

		pack();

		setTitle("Norse Board");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public void initEgypt() {

		add(new EgyptBoard());

		pack();

		setTitle("Egypt Board");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
	

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Image board = new Image();
				board.setVisible(true);
			}
		});

	}

}
