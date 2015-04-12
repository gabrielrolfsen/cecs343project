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
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import models.BattleCard;
import models.Board;
import models.Tile;
import utils.Constants;
import utils.Types.BoardType;

/**
 * @author grolfsen
 *
 */
public class MainFrameView extends JFrame {

	private final static MainFrameView mInstance = new MainFrameView();

	// BoardView where the boad is shown
	private final BoardView currentBoard = new BoardView();

	// Button to switch between boards
	final JButton btnSwitchBoard = new JButton("Switch Board");
	final GridBagConstraints switchBtnConstants = new GridBagConstraints(0, 0,
			0, 0, 0.5, 0.5, GridBagConstraints.FIRST_LINE_START, 0,
			this.getInsets(), 0, 0);

	private MainFrameView() {
		// TODO: Remove that
		setLocation(300, 300);
		// Sets the size of the 'switch board' Button
		btnSwitchBoard.setPreferredSize(new Dimension(100, 50));

	}

	/**
	 * Opens a Dialog to user pick one of the three cultures available.
	 * 
	 * @return the selected culture, all uppercase.
	 */
	public String culturePickDialog() {
		final JFrame frame = new JFrame("Board Selection");
		final CulturePickDialog c = new CulturePickDialog(frame);
		c.setVisible(true);
		System.out.println(c.getCulturePicked());
		return c.getCulturePicked();
		// final String[] boardNames = new String[] {
		// BoardType.GREEK.toString(),
		// BoardType.NORSE.toString(), BoardType.EGYPTIAN.toString() };
		//
		// final String input = (String) JOptionPane.showInputDialog(frame,
		// "Please choose a board:", "Board Selection",
		// JOptionPane.QUESTION_MESSAGE, null, boardNames, boardNames[0]);
		// return input;
	}

	public void showGameReadyDialog() {
		JOptionPane.showMessageDialog(this, "Game is ready to Play.",
				"All Set!", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Sets the board to be displayed on the BoardView JPanel
	 * 
	 * @param board
	 *            Board type, the board to be displayed.
	 */
	public void setDisplayedBoard(final Board board) {
		currentBoard.setBoard(board);
		showBoard(currentBoard, board.getTypeName());
	}

	public BoardType getDisplayedBoardType() {
		return this.currentBoard.getBoardType();
	}

	// Show the desired board on the JFrame
	private void showBoard(final BoardView board, final String title) {

		// Places the BoardView on the JFrame
		setContentPane(board);

		// Sets the correspondent layout and configurations
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(Constants.BOARD_WIDTH,
				Constants.BOARD_HEIGHT));
		setResizable(false);
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// TODO: don't know why but was changing the window's position
		// setLocationRelativeTo(null);

		// Adds the switch board button
		add(btnSwitchBoard, switchBtnConstants);
		pack();
		// Makes this JFrame visible
		this.setVisible(true);
	}

	/**
	 * Add a ResourceTile to the gridView of the BoardView.
	 * 
	 * @param tile
	 *            Tile, Tile to be added.
	 * @param x
	 *            int, X position.
	 * @param y
	 *            int, Y position.
	 */
	public void addResourceTileToBoard(final Tile tile, final int x, final int y) {
		currentBoard.addResourceTile(tile, x, y);
	}

	/**
	 * Add a BuildingTile to the gridView of the BoardView.
	 * 
	 * @param tile
	 *            Tile, Tile to be added.
	 * @param x
	 *            int, X position.
	 * @param y
	 *            int, Y position.
	 */
	public void addBuildingTileToBoard(final Tile tile, final int x, final int y) {
		currentBoard.addBuildingTile(tile, x, y);
	}

	/**
	 * Adds a listener to the switch board button listener.
	 * 
	 * @param listener
	 *            ActionListener, listener to be added.
	 */
	public void addSwitchBoardsButtonActionListener(
			final ActionListener listener) {
		btnSwitchBoard.addActionListener(listener);
	}

	/**
	 * Triggered when a Trade Card is played. Pops up the trade dialog with the
	 * player current resources and the bank resources.
	 * 
	 * @param bankResources
	 *            Array containing the current bank resources
	 * @param playerResources
	 *            Array containing the current player resources
	 * @return array with the quantities of resources that have to be added to
	 *         the player resources and subtracted from the bank
	 */
	public int[] openTradeDialog(final int[] bankResources,
			final int[] playerResources) {
		final JFrame frame = new JFrame();
		final TradeDialog c = new TradeDialog(frame, bankResources,
				playerResources);
		// Display the Dialog
		c.setVisible(true);
		return c.getUpdatedResources();
	}

	public ArrayList<String> openRecruitDialog(final int[] playerResources,
			final int qty, final ArrayList<BattleCard> availableUnits) {
		final JFrame frame = new JFrame();
		final RecruitDialog d = new RecruitDialog(frame, playerResources, qty,
				availableUnits);
		// Display the Dialog
		d.setVisible(true);
		return d.getSelectedUnits();

	}

	public static synchronized MainFrameView getInstance() {
		return mInstance;
	}

}
