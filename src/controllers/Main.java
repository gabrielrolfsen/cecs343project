/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Feb 23, 2015
 */
package controllers;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import models.Board;
import models.ButtonDialog;
import models.ResourceTile;
import models.ResourceTilePool;
import models.TileRes;
import utils.Constants;
import utils.ResEnum;
import utils.TileEnum;
import utils.Types.BoardType;

/**
 * @author grolfsen
 *
 */
public class Main extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	// Array to place 18 random tiles picked from the tile pool
	ArrayList<ResourceTile> randomTiles = new ArrayList<ResourceTile>();
	// Counter for each type of terrain inside the randomTiles array
	int[] terrainCounter = new int[6];

	public Board playerBoard = null;
	// Array that hold the AI players boards.
	public Board[] aiPlayersBoards = new Board[Constants.NUM_AI_PLAYERS];

	public Main() {
		// Creates a Dialog to user choose their culture
		final String[] boardNames = new String[] { BoardType.GREEK.toString(),
				BoardType.NORSE.toString(), BoardType.EGYPTIAN.toString() };
		final JFrame frame = new JFrame("Board Selection");
		final String input = (String) JOptionPane.showInputDialog(frame,
				"Please choose a board:", "Board Selection",
				JOptionPane.QUESTION_MESSAGE, null, boardNames, boardNames[0]);
		// Gets input and initialize the specific board for user + ai's
		if (input.equalsIgnoreCase("Greek")) {
			initBoard(BoardType.GREEK, "Greek Board");
		} else if (input.equalsIgnoreCase("Norse")) {
			initBoard(BoardType.NORSE, "Norse Board");
		} else if (input.equalsIgnoreCase("Egyptian")) {
			initBoard(BoardType.EGYPTIAN, "Egyptian Board");
		}

		// Creates the ResourceTile Pool
		final ResourceTilePool resPool = new ResourceTilePool();

		int i = 0;
		// Picks 18 random Tiles from the Resource Tile Pool
		for (i = 0; i < 18; i++) {
			randomTiles.add(resPool.getSelectedTile(i));
		}

		// Counts terrains type on the random selection
		for (final ResourceTile t : randomTiles) {
			for (i = 0; i < 6; i++) {
				if (t.getType().getValue() == i) {
					terrainCounter[i]++;
					break;
				}
			}
		}

		// 6 Rounds to pick the Terrains
		// TODO: Show the tiles on the boards.
		for (i = 0; i < 3; i++) {
			userPick();
			aiPick(0);
			aiPick(1);
			aiPick(1);
			aiPick(0);
			userPick();
		}

		// TODO: Improve this dialog
		JOptionPane.showMessageDialog(this, "Game is ready to Play.",
				"All Set!", JOptionPane.PLAIN_MESSAGE);

	}

	/**
	 * Method that handles the user picking a tile in the Terrain Tile selection
	 */
	private void userPick() {
		final int[] boardFreeTerrains = playerBoard.getFreeTerrainCounter();
		boolean canPick = false;
		int i = 0;

		// Checks if there's still any available selection
		for (i = 0; i < 6; i++) {
			if (boardFreeTerrains[i] != 0 && terrainCounter[i] > 0) {
				canPick = true;
				break;
			}
		}

		// If there's any available choice, a dialog will pop so the user can
		// pick one Tile
		if (canPick) {
			
			// Pops the ButtonDialog for the Tile Selection
			final ButtonDialog bDialog = new ButtonDialog(this, randomTiles,
					playerBoard.getFreeTerrainCounter());
			bDialog.setVisible(true);

			// Checks if the user has passed his turn or picked a tile
			if (bDialog.getSelected() != null) {
				// Update tile counter
				playerBoard.setFreeTerrainCounter(bDialog.getTerrainCounter());
				// Update tile list
				this.randomTiles = bDialog.getList();
				// Add tile to player's board
				final boolean s = playerBoard.addResourceTile(bDialog
						.getSelected());

				
				TileRes curTileRes = (TileRes) playerBoard.boardResGrid.TileArray[i];
				ResourceTile curResourceTile = bDialog.getSelected();
				for (i = 0; i < 16; i++) {
					if ((curTileRes.getType() == curResourceTile.myType) &&
						!curTileRes.isVisible()) {
						curTileRes.setType(curResourceTile.myType);
						curTileRes.setRes(curResourceTile.myRes,
										  curResourceTile.myTwoRes);
						curTileRes.setVisible(true);
					}
				}
				
				// DEBUG
				System.out.println("Tile Successfuly added:" + s);

				// Decrease the TerrainCounter (for RandomTiles array)
				terrainCounter[bDialog.getSelected().getType().getValue()]--;

			}
		}
	}

	private void aiPick(final int aiNum) {
		final int[] boardFreeTerrains = aiPlayersBoards[aiNum]
				.getFreeTerrainCounter();
		boolean canPick = false;
		int i = 0;
		Random r = new Random();

		// DEBUG
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
			if (boardFreeTerrains[n] != 0 && terrainCounter[n] > 0) {
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

			final ResourceTile selectedTile = randomTiles.get(pickedTileIndex);

			// Add the Tile to AI's board
			final boolean s = aiPlayersBoards[aiNum]
					.addResourceTile(selectedTile);
			terrainCounter[selectedTile.getType().getValue()]--;

			
			
			TileRes curTileRes = (TileRes) aiPlayersBoards[aiNum].boardResGrid.TileArray[i];
			ResourceTile curResourceTile = selectedTile;
			for (i = 0; i < 16; i++) {
				if ((curTileRes.getType() == curResourceTile.myType) &&
					!curTileRes.isVisible()) {
					curTileRes.setType(curResourceTile.myType);
					curTileRes.setRes(curResourceTile.myRes,
									  curResourceTile.myTwoRes);
					curTileRes.setVisible(true);
				}
			}
			
			
			
			// DEBUG
			System.out.println("Tile Successfuly added:" + s);

			// Removes the tile picked from the Array
			randomTiles.remove(pickedTileIndex);
		} else {
			// DEBUG
			System.out.println("Passed");
		}
	}

	private void initBoard(final BoardType type, final String title) {
		playerBoard = new Board(type);
		System.out.println(playerBoard.getType());

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

		showBoard(playerBoard, title);
	}

	private void showBoard(final Board board, final String title) {
		final JButton switchBoard = new JButton("Switch Board");
		switchBoard.addActionListener(this);
		switchBoard.setPreferredSize(new Dimension(100, 50));

		final JButton showAll = new JButton("Show All Tiles");
		showAll.addActionListener(this);
		showAll.setPreferredSize(new Dimension(150, 50));
		showAll.setLocation(20, 20);
		showAll.setActionCommand("Show All Tiles");
		showAll.setVisible(true);
		
		

		
		setContentPane(board);
		setLayout(new GridBagLayout());
		final GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		setSize(800, 600);

		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		add(switchBoard, c);

		add(showAll);
		
		pack();
	}

	public static void main(final String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				final Main main = new Main();
				main.setVisible(true);
			}
		});

	}

	/**
	 * Method used to Switch Boards Button
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		Board board = null;
		String title = "";
		final String[] boardNames = new String[] {
				playerBoard.getType().toString(),
				aiPlayersBoards[0].getType().toString(),
				aiPlayersBoards[1].getType().toString() };
		final JFrame frame = new JFrame("Board Selection");
		final String input = (String) JOptionPane.showInputDialog(frame,
				"Please choose a board:", "Board Selection",
				JOptionPane.QUESTION_MESSAGE, null, boardNames, boardNames[0]);

		if (input.equalsIgnoreCase(playerBoard.getType().toString())) {
			if (getContentPane() != playerBoard) {
				board = playerBoard;
				title = playerBoard.getType().toString();
			}
		} else if (input.equalsIgnoreCase(aiPlayersBoards[0].getType()
				.toString())) {
			if (getContentPane() != aiPlayersBoards[0]) {
				board = aiPlayersBoards[0];
				title = aiPlayersBoards[0].getType().toString();
			}
		}

		if (input.equalsIgnoreCase(aiPlayersBoards[1].getType().toString())) {
			if (getContentPane() != aiPlayersBoards[1]) {
				board = aiPlayersBoards[1];
				title = aiPlayersBoards[1].getType().toString();
			}
		}
		if (board != null) {
			showBoard(board, title);
		}
		
		if (e.getActionCommand() == "Show All Tiles") {
			playerBoard.boardBuildGrid.TileArray[0].setType(TileEnum.WALL);
			playerBoard.boardBuildGrid.TileArray[1].setType(TileEnum.TOWER);
			playerBoard.boardBuildGrid.TileArray[2].setType(TileEnum.STOREHOUSE);
			playerBoard.boardBuildGrid.TileArray[3].setType(TileEnum.MARKET);
			playerBoard.boardBuildGrid.TileArray[4].setType(TileEnum.ARMORY);
			playerBoard.boardBuildGrid.TileArray[5].setType(TileEnum.QUARRY);
			playerBoard.boardBuildGrid.TileArray[6].setType(TileEnum.MONUMENT);
			playerBoard.boardBuildGrid.TileArray[7].setType(TileEnum.GRANARY);
			playerBoard.boardBuildGrid.TileArray[8].setType(TileEnum.MINT);
			playerBoard.boardBuildGrid.TileArray[9].setType(TileEnum.WORKSHOP_WOOD);
			playerBoard.boardBuildGrid.TileArray[10].setType(TileEnum.TEMPLE);
			playerBoard.boardBuildGrid.TileArray[11].setType(TileEnum.WONDER);
			playerBoard.boardBuildGrid.TileArray[12].setType(TileEnum.HOUSE);
			playerBoard.boardBuildGrid.TileArray[13].setType(TileEnum.HOUSE);
			playerBoard.boardBuildGrid.TileArray[14].setType(TileEnum.HOUSE);
			playerBoard.boardBuildGrid.TileArray[15].setType(TileEnum.WORKSHOP_SIEGE);
			
			int i;
			for(i = 0; i < 16; i++) {
				playerBoard.boardBuildGrid.TileArray[i].setVisible(true);
			}
			
			
			for (i = 0; i < 16; i++) {
				playerBoard.boardResGrid.TileArray[i].setVisible(true);
				((TileRes) playerBoard.boardResGrid.TileArray[i]).setRes(ResEnum.FOOD, true);
			}

		}

	}

}
