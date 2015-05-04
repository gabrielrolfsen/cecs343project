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
import models.Card;
import models.Player;
import models.Tile;
import models.Unit;
import utils.Constants;
import utils.Types.CardType;
import utils.Types.CultureType;
import utils.Types.UnitType;

/**
 * @author grolfsen
 *
 */
public class MainFrameView extends JFrame {

	private final static MainFrameView mInstance = new MainFrameView();

	// BoardView where the board is shown
	private final BoardView currentBoard = new BoardView();

	// Button to switch between boards
	final JButton btnSwitchBoard = new JButton("Switch Board");
	final GridBagConstraints switchBtnConstants = new GridBagConstraints(0, 0, 0, 0, 0.5, 0.5,
			GridBagConstraints.FIRST_LINE_START, 0, this.getInsets(), 0, 0);

	// Button to add victory cubes to cards
	final JButton btnVictoryCard = new JButton("Place Victory Cubes");
	// TODO: Fix position
	final GridBagConstraints victoryBtnConstants = new GridBagConstraints(4, 0, 0, 0, 0.5, 0.5,
			GridBagConstraints.FIRST_LINE_START, 0, this.getInsets(), 0, 0);

	private MainFrameView() {
		// XXX: Remove that
		setLocation(300, 300);
		// Sets the size of the 'switch board' Button
		btnSwitchBoard.setPreferredSize(new Dimension(100, 50));
		btnVictoryCard.setPreferredSize(new Dimension(150, 50));

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
	}

	/**
	 * Simple Procedure that show a Dialog displaying that the game is ready to
	 * play.
	 */
	public void showGameReadyDialog() {
		JOptionPane.showMessageDialog(this, "Game is ready to Play.", "All Set!", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Sets the board to be displayed on the BoardView JPanel
	 * 
	 * @param board
	 *            Board type, the board to be displayed.
	 */
	public void setDisplayedBoard(final Player player) {
		this.updatePlayerResources(player.getResourceCounter());
		this.updatePlayerArmy(player.getArmy());

		final Board board = player.getBoard();
		currentBoard.setBoard(board);
		showBoard(currentBoard, board.getTypeName());
	}

	/**
	 * 
	 * @return
	 */
	public CultureType getDisplayedBoardType() {
		return this.currentBoard.getBoardType();
	}

	/**
	 * 
	 * @param board
	 * @param title
	 */
	private void showBoard(final BoardView board, final String title) {

		// Places the BoardView on the JFrame
		setContentPane(board);

		// Sets the correspondent layout and configurations
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT));
		setResizable(false);
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// TODO: don't know why but was changing the window's position
		// setLocationRelativeTo(null);

		// Adds the switch board button
		add(btnSwitchBoard, switchBtnConstants);
		add(btnVictoryCard, victoryBtnConstants);
		pack();
		// Makes this JFrame visible
		this.setVisible(true);
	}

	/**
	 * 
	 * @param army
	 */
	public void updatePlayerArmy(final ArrayList<Unit> army) {
		currentBoard.updateArmy(army);
	}

	/**
	 * 
	 * @param resources
	 */
	public void updatePlayerResources(final int[] resources) {
		currentBoard.updateResources(resources);
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
	public void addSwitchBoardsButtonActionListener(final ActionListener listener) {
		btnSwitchBoard.addActionListener(listener);
	}

	public void addVictoryCardButtonActionListener(final ActionListener listener) {
		btnVictoryCard.addActionListener(listener);
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
	public int[] openTradeDialog(final int[] bankResources, final int[] playerResources,
			final boolean allowVictoryCubes, final CardType type) {
		final JFrame frame = new JFrame();
		final TradeDialog c = new TradeDialog(frame, type, bankResources, playerResources, allowVictoryCubes);
		// Display the Dialog
		c.setVisible(true);
		return c.getUpdatedResources();
	}

	/**
	 * 
	 * @param playerResources
	 * @param qty
	 * @param availableUnits
	 * @return
	 */
	public ArrayList<Unit> openRecruitDialog(final int[] playerResources, final int qty,
			final ArrayList<BattleCard> availableUnits) {
		final JFrame frame = new JFrame();
		final RecruitDialog d = new RecruitDialog(frame, playerResources, qty, availableUnits);
		// Display the Dialog
		d.setVisible(true);
		return d.getSelectedUnits();
	}

	/**
	 * 
	 * @param bankResources
	 * @param playerResources
	 * @param qty
	 * @return
	 */
	public int[] showPaymentDialog(final int[] bankResources, final int[] playerResources, final int qty) {
		final JFrame frame = new JFrame();
		final PaymentDialog dialog = new PaymentDialog(frame, bankResources, playerResources, qty);
		// Display the Dialog
		dialog.setVisible(true);
		return dialog.getUpdatedResources();
	}

	/**
	 * 
	 * @param hand
	 * @return
	 */
	public Card showHandDialog(final ArrayList<Card> hand) {
		final JFrame frame = new JFrame();
		final HandDialog dialog = new HandDialog(frame, hand);
		// Display the Dialog
		dialog.setVisible(true);

		return dialog.getSelectedCard();
	}

	/**
	 * Show the dialog for the Norse Special Recruit Card
	 * 
	 * @param units
	 */
	public ArrayList<Unit> showNorseRecruitSpecialDialog(final ArrayList<Unit> units) {
		final String[] unitsNames = new String[units.size()];

		for (int i = 0; i < units.size(); i++) {
			unitsNames[i] = units.get(i).getType().getName();
		}

		final String[] selected = new String[2];
		final JFrame frame = new JFrame();
		selected[0] = (String) JOptionPane
				.showInputDialog(frame, "Norse God Power - Hel", "Select The First Mortal Unit",
						JOptionPane.QUESTION_MESSAGE, null, unitsNames, unitsNames[0]);

		selected[1] = (String) JOptionPane.showInputDialog(frame, "Norse God Power - Hel",
				"Select The Second Mortal Unit", JOptionPane.QUESTION_MESSAGE, null, unitsNames,
				unitsNames[0]);
		final ArrayList<Unit> result = new ArrayList<Unit>();
		result.add(UnitType.getUnit(selected[0]));
		result.add(UnitType.getUnit(selected[1]));
		return result;
	}

	/**
	 * 
	 * @return
	 */
	public int showCardDialog(final CultureType culture, final ArrayList<Card> hand, final int qty) {
		final JFrame frame = new JFrame();
		final CardDialog dialog = new CardDialog(frame, culture, hand, qty);
		// Display the Dialog
		dialog.setVisible(true);

		return dialog.getQtyRandomCardsSelected();
	}

	public static synchronized MainFrameView getInstance() {
		return mInstance;
	}

}
