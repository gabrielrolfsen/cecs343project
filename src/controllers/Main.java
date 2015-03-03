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
import java.util.Random;

import javax.swing.JButton;
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
public class Main extends JFrame implements ActionListener {

	ArrayList<ResourceTile> randomTiles = new ArrayList<ResourceTile>();
	int[] terrainCounter = new int[6];

	public Board playerBoard = null;
	public Board[] aiPlayersBoards = new Board[2];

	public Main() {

		final String[] boardNames = new String[] { "Greek", "Norse", "Egyptian" };
		final JFrame frame = new JFrame("Board Selection");
		final String input = (String) JOptionPane.showInputDialog(frame,
				"Please choose a board:", "Board Selection",
				JOptionPane.QUESTION_MESSAGE, null, boardNames, boardNames[0]);
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
		System.out.println("--AI Pick--");
		for (i = 0; i < 6; i++) {
			if (boardFreeTerrains[i] != 0 && terrainCounter[i] > 0) {
				System.out.println("freeTerrains i:" + i + " - "
						+ boardFreeTerrains[i]);
				System.out.println("terrainCounter i:" + i + " - "
						+ terrainCounter[i]);
				canPick = true;
				break;
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

	private void initBoard(final int type, final String title) {
		playerBoard = new Board(type);
		System.out.println(playerBoard.getTypeName());

		// If Player = Greek, Ai0 = Egyptian, Ai1 = Norse
		// If Player = Egyptian, Ai0 = Norse, Ai1 = Greek
		// If Player = Norse, Ai0 = Greek, Ai1 = Egyptian
		aiPlayersBoards[0] = new Board(type == 2 ? 1 : (type == 1 ? 0 : 2));
		aiPlayersBoards[1] = new Board(type == 2 ? 0 : (type == 1 ? 2 : 1));

		final JButton switchBoard = new JButton("Switch Board");
		switchBoard.addActionListener(this);
		switchBoard.setPreferredSize(new Dimension(100, 50));

		setContentPane(playerBoard);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		final String[] boardNames = new String[] { playerBoard.getTypeName(),
				aiPlayersBoards[0].getTypeName(),
				aiPlayersBoards[1].getTypeName() };
		final JFrame frame = new JFrame("Board Selection");
		final String input = (String) JOptionPane.showInputDialog(frame,
				"Please choose a board:", "Board Selection",
				JOptionPane.QUESTION_MESSAGE, null, boardNames, boardNames[0]);
		if (input.equalsIgnoreCase(playerBoard.getTypeName())) {
			// TODO: Show playerBoard
		}

		if (input.equalsIgnoreCase(aiPlayersBoards[0].getTypeName())) {
			// TODO: Show aiPlayersBoards[0]
		}

		if (input.equalsIgnoreCase(aiPlayersBoards[1].getTypeName())) {
			// TODO: Show aiPlayersBoards[1]
		}

	}

}
