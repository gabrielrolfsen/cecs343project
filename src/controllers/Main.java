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
	Board playerBoard;
	Board ai1Board;
	Board ai2Board;

	public Main() {

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

		// TODO: Implement the code to get 18 random tiles from the bank and add
		// to an ArrayList called randomTiles
		int i = 0;
		for (i = 0; i < 18; i++) {
			randomTiles.add(new ResourceTile(Constants.TYPE_FOREST,
					Constants.ONE_GOLD,
					"res/production_tiles/tile_desert_1_gold.png"));

		}
		// 6 Rounds to pick the Terrains
		for (i = 0; i < 3; i++) {
			userPick();
			aiPick(1);
			aiPick(2);
			aiPick(2);
			aiPick(1);
			userPick();
		}

		System.exit(0);
	}

	// TODO: If no remaining selection for the human player can be made, they
	// must “pass” (not get a selection) on this and all subsequent rounds
	private void userPick() {
		final ButtonDialog bDialog = new ButtonDialog(this, randomTiles);
		// User picks
		bDialog.setVisible(true);
		// DEBUG
		System.out.println("USER SELECTED TYPE: "
				+ bDialog.getSelected().getType());
		// Adds the Tile to the player's board if he didn't pass the selection
		if (bDialog.getSelected() != null) {
			playerBoard.addResourceTile(bDialog.getSelected());
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

		// Removes the tile picked from the Array
		randomTiles.remove(pickedTileIndex);
	}

	private void init(final int Type, final String Title) {
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
