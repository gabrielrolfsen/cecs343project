/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Mar 6, 2015
 */
package views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import models.Board;
import utils.Constants;
import utils.Types.BoardType;

/**
 * @author grolfsen
 *
 */
public class MainFrameView extends JFrame {

	private Board currentBoard = null;
	private final BoardView displayedBoard = null;
	final JButton btnSwitchBoard = new JButton("Switch Board");

	public MainFrameView() {

		// Sets the size of the 'switch board' Button
		btnSwitchBoard.setPreferredSize(new Dimension(100, 50));

	}

	/**
	 * Opens a Dialog to user pick among the three cultures available.
	 * 
	 * @return the selected culture, all uppercase.
	 */
	public String culturePickDialog() {
		final String[] boardNames = new String[] { BoardType.GREEK.toString(),
				BoardType.NORSE.toString(), BoardType.EGYPTIAN.toString() };
		final JFrame frame = new JFrame("Board Selection");
		final String input = (String) JOptionPane.showInputDialog(frame,
				"Please choose a board:", "Board Selection",
				JOptionPane.QUESTION_MESSAGE, null, boardNames, boardNames[0]);
		return input;
	}

	public void showGameReadyDialog() {
		JOptionPane.showMessageDialog(this, "Game is ready to Play.",
				"All Set!", JOptionPane.PLAIN_MESSAGE);
	}

	public Board getDisplayedBoard() {
		return this.currentBoard;
	}

	// Show the desired board on the JFrame
	private void showBoard(final Board board, final String title) {

		setContentPane(board);
		setLayout(new GridBagLayout());
		final GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		setPreferredSize(new Dimension(Constants.BOARD_WIDTH,
				Constants.BOARD_HEIGHT));
		setResizable(false);

		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		add(btnSwitchBoard, c);

		pack();
		currentBoard.refresh();
	}

	public void setDisplayedBoard(final Board displayedBoard) {
		this.currentBoard = displayedBoard;
		showBoard(displayedBoard, displayedBoard.getTypeName());
	}

	public void addButtonActionListener(final ActionListener listener) {
		btnSwitchBoard.addActionListener(listener);
	}

}
