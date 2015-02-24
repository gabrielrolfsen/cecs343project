package models;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import utils.Constants;

public class Image extends JFrame {

	public Image() {

		final String input = JOptionPane
				.showInputDialog("Please choose your board: Greek, Norse or Egyptian:");
		if (input.equalsIgnoreCase("Greek")) {
			init(Constants.TYPE_GREEK, "Greek Board");
		}

		if (input.equalsIgnoreCase("Norse")) {
			init(Constants.TYPE_NORSE, "Norse Board");
		}

		if (input.equalsIgnoreCase("Egyptian")) {
			init(Constants.TYPE_EGYPTIAN, "Egyptian Board");
		}
	}

	public void init(final int Type, final String Title) {
		add(new Board(Type));
		pack();
		setTitle(Title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

	}

	public static void main(final String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				final Image board = new Image();
				board.setVisible(true);
			}
		});

	}

}
