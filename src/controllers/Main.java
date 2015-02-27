/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Feb 23, 2015
 */
package controllers;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import models.Board;
import models.ButtonDialog;
import models.ResourceTile;
import utils.Constants;

/**
 * @author grolfsen
 *
 */
public class Main extends JFrame {

	ArrayList<ResourceTile> randomTiles = new ArrayList<ResourceTile>();
	int[] terrainCounter = new int[6];

	public Board playerBoard;
	Board[] aiPlayersBoards = new Board[2];

	public Main() {

		final String input = JOptionPane
				.showInputDialog("Please choose your board: Greek, Norse or Egyptian:");
		if (input.equalsIgnoreCase("Greek")) {
			initBoard(Constants.TYPE_GREEK, "Greek Board");
		}

		if (input.equalsIgnoreCase("Norse")) {
			initBoard(Constants.TYPE_NORSE, "Norse Board");
		}

		if (input.equalsIgnoreCase("Egyptian")) {
			initBoard(Constants.TYPE_EGYPTIAN, "Egyptian Board");
		}

		// TODO: Implement the code to get 18 random tiles from the bank and add
		// to an ArrayList called randomTiles
		int i = 0;
		for (i = 0; i < 17; i++) {
			randomTiles.add(new ResourceTile(Constants.TYPE_DESERT,
					Constants.ONE_GOLD,
					"res/production_tiles/tile_desert_1_gold.png"));

		}
		randomTiles.add(new ResourceTile(Constants.TYPE_MOUNTAINS,
				Constants.ONE_GOLD,
				"res/production_tiles/tile_forest_1_gold.png"));

		// Counts terrains on the random selection
		for (final ResourceTile t : randomTiles) {
			for (i = 0; i < 6; i++) {
				if (t.getType() == i) {
					terrainCounter[i]++;
					break;
				}
			}
		}

		// 6 Rounds to pick the Terrains
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
		System.exit(0);
	}

	private void userPick() {
		final int[] boardFreeTerrains = playerBoard.getFreeTerrainCounter();
		boolean canPick = false;
		int i = 0;
		// Checks if there's still any available selection
		for (i = 0; i < 6; i++) {
			if (boardFreeTerrains[i] != 0 && terrainCounter[i] > 0) {
				canPick = true;
			}
		}

		if (canPick) {
			final ButtonDialog bDialog = new ButtonDialog(this, randomTiles,
					playerBoard.getFreeTerrainCounter());
			bDialog.setVisible(true);

			if (bDialog.getSelected() != null) {
				// Update tile counter
				playerBoard.setFreeTerrainCounter(bDialog.getTerrainCounter());
				// Update tile list
				this.randomTiles = bDialog.getList();
				// Add tile to player's board
				final boolean s = playerBoard.addResourceTile(bDialog
						.getSelected());
				// DEBUG
				System.out.println(s);
				for (i = 0; i < 6; i++) {
					if (bDialog.getSelected().getType() == i) {
						playerBoard.decreaseFreeTerrainCounter(i);
						terrainCounter[i]--;
						break;
					}
				}
			}
		}

	}

	private void aiPick(final int aiNum) {
		final int[] boardFreeTerrains = aiPlayersBoards[aiNum]
				.getFreeTerrainCounter();
		boolean canPick = false;
		int i = 0;
		for (i = 0; i < 6; i++) {
			if (boardFreeTerrains[i] != 0 && terrainCounter[i] > 0) {
				canPick = true;
			}
		}
		if (canPick) {
			final Random r = new Random();
			int pickedTileIndex = -1;
			// TODO: change variable name (it's horrible)
			boolean rightTile = false;
			// Keeps picking tile until find one that fits in the board
			while (!rightTile) {
				pickedTileIndex = r.nextInt(randomTiles.size());
				if (boardFreeTerrains[randomTiles.get(pickedTileIndex)
						.getType()] != 0) {
					rightTile = true;
				}
			}
			// DEBUG
			System.out.println("AI #" + aiNum + " has selected index: "
					+ pickedTileIndex);

			final ResourceTile selectedTile = randomTiles.get(pickedTileIndex);

			// Add the Tile to AI's board
			final boolean s = aiPlayersBoards[aiNum]
					.addResourceTile(selectedTile);
			// DEBUG
			System.out.println(s);

			for (i = 0; i < 6; i++) {
				if (selectedTile.getType() == i) {
					aiPlayersBoards[aiNum].addResourceTile(selectedTile);
					aiPlayersBoards[aiNum].decreaseFreeTerrainCounter(i);
					terrainCounter[i]--;
					break;
				}
			}

			// Removes the tile picked from the Array
			randomTiles.remove(pickedTileIndex);
		}
	}

	private void initBoard(final int Type, final String Title) {
		playerBoard = new Board(Type);

		aiPlayersBoards[0] = new Board(Type == 2 ? 1 : (Type == 1 ? 0 : 2));
		aiPlayersBoards[1] = new Board(Type == 2 ? 0 : (Type == 1 ? 2 : 1));

		add(playerBoard);
		pack();
		setTitle(Title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(this);
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

}
