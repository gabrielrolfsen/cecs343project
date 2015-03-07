/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Feb 23, 2015
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import models.Board;
import models.ResourceTile;
import models.ResourceTilePool;
import utils.Constants;
import utils.Types.BoardType;
import views.ButtonDialog;
import views.MainFrameView;

/**
 * Main Controller class, that control all the game flow
 * 
 * @author grolfsen
 *
 */
public class MainController {
	private static final long serialVersionUID = 1L;

	private final MainFrameView mainFrame = new MainFrameView();
	ButtonDialog tilePickerDialog;

	// Array to place 18 random tiles picked from the tile pool
	ArrayList<ResourceTile> randomTiles = new ArrayList<ResourceTile>();

	// Counter for each type of terrain inside the randomTiles array, each index
	// represent a terrainType and contains the number of tiles of this type in
	// the random array
	int[] terrainTypeCounter = new int[6];

	// Player Board
	public Board playerBoard = null;

	// Array that hold the AI players' boards.
	public Board[] aiPlayersBoards = new Board[Constants.NUM_AI_PLAYERS];

	public MainController() throws Exception {

		// Pops a Dialog so user can pick a culture
		userCulturePick();

		// Creates the ResourceTile Pool
		final ResourceTilePool resPool = new ResourceTilePool();

		// Picks 18 random Tiles from the Resource Tile Pool
		for (int i = 0; i < 18; i++) {
			randomTiles.add(resPool.getSelectedTile(i));
			// Increment the terrainTypeCounter for the specific tile type
			terrainTypeCounter[resPool.getSelectedTile(i).getType().getValue()]++;
		}

		// ResourceTile picking begins - 6 Turns each player
		for (int i = 0; i < 3; i++) {
			userPick();
			aiPick(0);
			aiPick(1);
			aiPick(1);
			aiPick(0);
			userPick();
		}

		// TODO: Improve this dialog
		mainFrame.showGameReadyDialog();

		// Add Switch Button functionality
		mainFrame.addButtonActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				Board boardToDisplay = null;

				final String[] boardNames = new String[] {
						playerBoard.getTypeName(),
						aiPlayersBoards[0].getTypeName(),
						aiPlayersBoards[1].getTypeName() };
				final JFrame frame = new JFrame("Board Selection");
				final String boardType = (String) JOptionPane.showInputDialog(
						frame, "Please choose a board:", "Board Selection",
						JOptionPane.QUESTION_MESSAGE, null, boardNames,
						boardNames[0]);

				// Switch between the option selected
				if (boardType
						.equalsIgnoreCase(playerBoard.getType().toString())) {
					boardToDisplay = playerBoard;
				} else if (boardType.equalsIgnoreCase(aiPlayersBoards[0]
						.getType().toString())) {
					boardToDisplay = aiPlayersBoards[0];
				} else if (boardType.equalsIgnoreCase(aiPlayersBoards[1]
						.getType().toString())) {
					boardToDisplay = aiPlayersBoards[1];
				}

				// If the option was one different from the already
				// displayed, chagne it
				if (boardToDisplay != mainFrame.getDisplayedBoard()) {
					mainFrame.setDisplayedBoard(boardToDisplay);
					boardToDisplay.refresh();
				}
			}
		});

	}

	/**
	 * Class that calls the mainFrame to pops the Dialog for culture pick, then
	 * initialize the boards.
	 */
	private void userCulturePick() {
		// Pops the DialogView on the mainFrame so user can pick a culture
		final String input = mainFrame.culturePickDialog();

		// Gets the input and initialize the specific board for user + AI's
		if (input.equalsIgnoreCase("Greek")) {
			initBoard(BoardType.GREEK);
		} else if (input.equalsIgnoreCase("Norse")) {
			initBoard(BoardType.NORSE);
		} else if (input.equalsIgnoreCase("Egyptian")) {
			initBoard(BoardType.EGYPTIAN);
		}

	}

	/**
	 * Method that handles the user picking a tile in the Terrain Tile
	 * selection. First compare the tiles in the randomTiles Array with the free
	 * tiles in the user board and see if it's possible to pick any of them. If
	 * it;s possible, show a JDialog and checks if the user picked a tile or
	 * passed the turn. Add the tile to the board and update the lists and
	 * counters. If it's not possible, don't show the JDialog and automatically
	 * pass the turn.
	 */
	private void userPick() {
		final int[] boardFreeTerrains = playerBoard.getFreeTerrainCounter();
		boolean canPick = false;
		int i = 0;

		// Checks if there's still any available selection
		for (i = 0; i < 6; i++) {
			if (boardFreeTerrains[i] != 0 && terrainTypeCounter[i] > 0) {
				canPick = true;
				break;
			}
		}

		// If there's any available choice, a dialog will pop so the user can
		// pick one Tile
		if (canPick) {

			// Pops the ButtonDialog for the Tile Selection
			final ButtonDialog bDialog = new ButtonDialog(mainFrame,
					randomTiles, playerBoard.getFreeTerrainCounter());
			bDialog.setVisible(true);

			// Checks if the user has passed his turn or picked a tile
			if (bDialog.getSelected() != null) {
				// Update free board tiles counter
				playerBoard.setFreeTerrainCounter(bDialog.getTerrainCounter());

				// Add tile to player's board
				final boolean s = playerBoard.addResourceTile(bDialog
						.getSelected());

				// DEBUG
				System.out.println("Tile Successfuly added:" + s);

				// Update RandomTiles Array
				this.randomTiles = bDialog.getList();

				// Decrease the TerrainCounter for RandomTiles Array
				terrainTypeCounter[bDialog.getSelected().getType().getValue()]--;

			}
		}
	}

	/**
	 * Method that handles the AI #aiNum picking a tile in the Terrain Tile
	 * selection. First creates a random array with the tile Types corresponding
	 * integers, Then compare the tiles in the randomTiles Array with the free
	 * tiles in the AI board and see if it's possible to pick any of them. If
	 * it's possible, randomize a number and try to picks. Add the tile to the
	 * board and update the lists and counters. If it's not, pass the turn.
	 * 
	 * @param aiNum
	 *            AI number that will pick, this method is made so it can
	 *            'plays' for any AI player.
	 */
	private void aiPick(final int aiNum) {
		final int[] boardFreeTerrains = aiPlayersBoards[aiNum]
				.getFreeTerrainCounter();
		boolean canPick = false;
		int i = 0;
		Random r = new Random();

		// DEBUG
		System.out.println();
		System.out.println("--AI" + aiNum + " Pick--");

		// Create a list with numbers 0 to 5 (types of terrains)
		final ArrayList<Integer> nums = new ArrayList<Integer>();
		for (i = 0; i < 6; i++) {
			nums.add(i);
		}
		// Shuffle it
		Collections.shuffle(nums);

		// Iterate through a random array of numbers from 0..5 and see if the AI
		// can pick this type of Tile
		for (final Integer n : nums) {
			if (boardFreeTerrains[n] != 0 && terrainTypeCounter[n] > 0) {
				canPick = true;
				break;
			}
		}

		// If there's any available choice, the AI will try to pick one tile
		if (canPick) {
			r = new Random();
			int pickedTileIndex = -1;
			// TODO: change variable name (it's horrible)
			boolean rightTile = false;
			// Keeps picking tile until find one that fits in the board
			while (!rightTile) {
				pickedTileIndex = r.nextInt(randomTiles.size());
				if (boardFreeTerrains[randomTiles.get(pickedTileIndex)
				                      .getType().getValue()] != 0) {
					rightTile = true;
				}
			}

			// Get the selectedTile
			final ResourceTile selectedTile = randomTiles.get(pickedTileIndex);

			// Add the Tile to AI's board
			final boolean s = aiPlayersBoards[aiNum]
					.addResourceTile(selectedTile);

			// DEBUG
			System.out.println("Tile Successfuly added: " + s);

			// Removes the tile picked from the RandomTiles Array
			randomTiles.remove(pickedTileIndex);
			// Decrease the TerrainCounter for RandomTiles Array
			terrainTypeCounter[selectedTile.getType().getValue()]--;
		} else {
			// DEBUG
			System.out.println("Passed");
		}
	}

	/**
	 * Initialize all the Boards depending on what culture the user has chosen.
	 * Then call the mainFrame to display the userBoard first.
	 * 
	 * @param type
	 *            BoardType, the BoardType that the user has chosen.
	 * @param title
	 *            String, the title to be used in the JFrame
	 */
	private void initBoard(final BoardType type) {
		playerBoard = new Board(type);

		// If Player = Greek, Ai0 = Egyptian, Ai1 = Norse
		// If Player = Egyptian, Ai0 = Norse, Ai1 = Greek
		// If Player = Norse, Ai0 = Greek, Ai1 = Egyptian
		aiPlayersBoards[0] = new Board(
				type == BoardType.GREEK ? BoardType.EGYPTIAN
						: (type == BoardType.EGYPTIAN ? BoardType.NORSE
								: BoardType.GREEK));
		aiPlayersBoards[1] = new Board(
				type == BoardType.GREEK ? BoardType.NORSE
						: (type == BoardType.EGYPTIAN ? BoardType.GREEK
								: BoardType.EGYPTIAN));
		// Set the Boards on the main JFrame
		mainFrame.setDisplayedBoard(playerBoard);
	}

	public static void main(final String[] args) {

		MainController game;
		try {
			game = new MainController();
			game.mainFrame.setVisible(true);
		} catch (final Exception e) {
			e.printStackTrace(System.err);
			System.exit(0);

		}

	}

}
