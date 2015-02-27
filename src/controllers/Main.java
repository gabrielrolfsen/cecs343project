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
	public Board ai1Board;
	public Board ai2Board;

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
		for (i = 0; i < 18; i++) {
			randomTiles.add(new ResourceTile(Constants.TYPE_DESERT,
					Constants.ONE_GOLD,
					"res/production_tiles/tile_desert_1_gold.png"));

		}

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
			System.out.println("i: " + i);
			userPick();
			aiPick(1);
			aiPick(2);
			aiPick(2);
			aiPick(1);
			userPick();
			System.out.println("i: " + i);
		}

		// TODO: Improve this dialog
		JOptionPane.showMessageDialog(this, "Game is ready to Play.",
				"All Set!", JOptionPane.PLAIN_MESSAGE);
		System.exit(0);
	}

	// TODO: Make for AI1 and AI2
	private void userPick() {
		final int[] boardFreeTerrains = playerBoard.getFreeTerrainCounter();
		boolean canPick = false;
		int i = 0;
		for (i = 0; i < 6; i++) {
			if (boardFreeTerrains[i] != 0 && terrainCounter[i] > 0) {
				canPick = true;
			}
		}
		if (canPick) {
			final ButtonDialog bDialog = new ButtonDialog(this, randomTiles,
					playerBoard.getFreeTerrainCounter());
			// User picks
			bDialog.setVisible(true);
			// DEBUG
			System.out.println("USER SELECTED TYPE: "
					+ bDialog.getSelected().getType());
			// Adds the Tile to the player's board and decrement tileCounter if
			// he
			// didn't skipped the
			// selection
			if (bDialog.getSelected() != null) {
				// Update the list and counter
				playerBoard.setFreeTerrainCounter(bDialog.getTerrainCounter());
				this.randomTiles = bDialog.getList();
				playerBoard.addResourceTile(bDialog.getSelected());
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
		final int pickedTileIndex = new Random().nextInt(randomTiles.size());
		System.out.println("AI #" + aiNum + " has selected index: "
				+ pickedTileIndex);
		final ResourceTile selectedTile = randomTiles.get(pickedTileIndex);

		// Add the Tile to AI's board
		if (aiNum == 1) {
			ai1Board.addResourceTile(selectedTile);
		} else {
			ai2Board.addResourceTile(selectedTile);
		}
		int i = 0;

		for (i = 0; i < 6; i++) {
			if (selectedTile.getType() == i) {
				if (aiNum == 1) {
					ai1Board.decreaseFreeTerrainCounter(i);
				} else {
					ai2Board.decreaseFreeTerrainCounter(i);
				}
				terrainCounter[i]--;
				break;
			}
		}

		// Removes the tile picked from the Array
		randomTiles.remove(pickedTileIndex);
	}

	private void initBoard(final int Type, final String Title) {
		playerBoard = new Board(Type);
		// If user = 2, ai2 = 1, if User = 1, ai2 = 0, user = 0, ai2 = 2
		ai1Board = new Board(Type == 2 ? 1 : (Type == 1 ? 0 : 2));
		// If user = 2, ai3 = 0, if User = 1, ai3 = 2, user = 0, ai3 = 1
		ai2Board = new Board(Type == 2 ? 0 : (Type == 1 ? 2 : 1));
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
