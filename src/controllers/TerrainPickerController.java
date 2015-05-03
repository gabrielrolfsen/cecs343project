/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 14, 2015
 */
package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import models.Player;
import models.ResourceTile;
import models.ResourcesBank;
import utils.Constants;
import utils.Coordinates;
import utils.Types.CardType;
import views.ButtonDialog;
import views.MainFrameView;

/**
 * @author grolfsen
 *
 */
public class TerrainPickerController {

	private static TerrainPickerController mInstance = new TerrainPickerController();
	private final MainFrameView mainFrame = MainFrameView.getInstance();
	private final ResourcesBank resBank = ResourcesBank.getInstance();

	private ArrayList<ResourceTile> randomTiles = new ArrayList<ResourceTile>();
	/*
	 * Counter for each type of terrain inside the randomTiles array, each index
	 * represent a terrainType and contains the number of tiles of this type in
	 * the random array
	 */
	int[] terrainTypeCounter = new int[6];

	public Player[] players = new Player[Constants.MAX_PLAYERS];

	public void exploreCardRoutine(final Player[] players, final int startingPlayer, final CardType type) {

		final int qtyTiles = players.length + 1;
		int tilesToPick = qtyTiles;

		// If it's the "Ptah" god power add two more tiles
		if (type == CardType.EXPLORE_EGYPTIAN || type == CardType.EXPLORE_GREEK) {
			tilesToPick++;
		}

		// Picks Random Tiles from the Tile Pool based on # of players
		randomTiles = resBank.getRandomTiles(tilesToPick);

		// Count the Types on the Random Tiles
		countResourceTypes();

		if (type == CardType.EXPLORE_NORSE) {
			if (startingPlayer == 0) {
				userPick();
			} else {
				aiPick(startingPlayer);
			}
		} else // The for loops assure that the god power works
			if (startingPlayer == 0) {
				for (int i = qtyTiles; i < tilesToPick + 1; i++) {
					userPick();
				}
				aiPick(1);
				aiPick(2);
			} else if (startingPlayer == 1) {
				for (int i = qtyTiles; i < tilesToPick + 1; i++) {
					aiPick(1);
				}
				aiPick(2);
				userPick();
			} else {
				for (int i = qtyTiles; i < tilesToPick + 1; i++) {
					aiPick(2);
				}
				userPick();
				aiPick(1);
			}

		// Return any tiles that weren't selected to the Tile Pool
		returnTilesToPool();
	}

	public void terrainPickRoutine(final Player[] players) {
		this.players = players;
		// Picks MAX_TILES_PICKING_PHASE Random Tiles from the Tile Pool
		randomTiles = resBank.getRandomTiles(Constants.MAX_TILES_PICKING_PHASE);

		countResourceTypes();

		for (int i = 0; i < 3; i++) {
			userPick();
			aiPick(1);
			aiPick(2);
			aiPick(2);
			aiPick(1);
			userPick();
		}
	}

	private void countResourceTypes() {
		// Counts the Resource Types on the Random Tiles array
		for (final ResourceTile t : randomTiles) {
			terrainTypeCounter[t.getType().getValue()]++;
		}
	}

	private void returnTilesToPool() {
		if (randomTiles.size() != 0) {
			resBank.returnTilesToPool(randomTiles);
		}
		// Clear the random Tile Array and Terrain Counter
		Arrays.fill(terrainTypeCounter, 0);
		randomTiles.clear();
	}

	public static TerrainPickerController getInstance() {
		return mInstance;
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
		final int[] boardFreeTerrains = players[0].getBoard().getFreeTerrainCounter();
		boolean canPick = false;
		int i = 0;

		// Checks if there's still any available selection
		for (i = 0; i < 6; i++) {
			if (boardFreeTerrains[i] != 0 && terrainTypeCounter[i] > 0) {
				canPick = true;
				break;
			}
		}

		/*
		 * If there's any available choice, a dialog will pop so the user can
		 * pick one Tile
		 */
		if (canPick) {

			// Pops the ButtonDialog for the Tile Selection
			final ButtonDialog bDialog = new ButtonDialog(mainFrame, randomTiles, players[0].getBoard()
					.getFreeTerrainCounter());
			bDialog.setVisible(true);

			// Checks if the user has passed his turn or picked a tile
			if (bDialog.getIndexSelected() != -1) {
				// Get the ResourceTile selected
				final ResourceTile selectedTile = randomTiles.get(bDialog.getIndexSelected());

				/*
				 * Add tile to player's board and get the coordinates where it
				 * was added
				 */
				final Coordinates c = players[0].getBoard().addResourceTile(selectedTile);

				// Removes the tile picked from the RandomTiles Array
				randomTiles.remove(selectedTile);

				/*
				 * If the tile was successfully added to the board, show it on
				 * the BoardView
				 */
				if (c != null) {
					mainFrame.addResourceTileToBoard(selectedTile, c.getX(), c.getY());
				} else {
					System.err.println("There was a problem adding the tile to the board.");
				}

				// Decrease the TerrainCounter for RandomTiles Array
				terrainTypeCounter[selectedTile.getType().getValue()]--;

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
		// Get the FreeTerrains Type counter
		final int[] boardFreeTerrains = players[aiNum].getBoard().getFreeTerrainCounter();
		boolean canPick = false;
		int i = 0;

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
			final Random r = new Random();
			int pickedTileIndex = -1;
			// Variable that says if the tile can fit in the board
			boolean tileCanFit = false;
			// Keeps picking tile until find one that fits in the board
			while (!tileCanFit) {
				pickedTileIndex = r.nextInt(randomTiles.size());
				if (boardFreeTerrains[randomTiles.get(pickedTileIndex).getType().getValue()] != 0) {
					tileCanFit = true;
				}
			}

			// Get the selectedTile
			final ResourceTile selectedTile = randomTiles.get(pickedTileIndex);

			// TODO: Not needed. Test
			// Add the Tile to AI's board
			final Coordinates c = players[aiNum].getBoard().addResourceTile(selectedTile);

			// Removes the tile picked from the RandomTiles Array
			randomTiles.remove(pickedTileIndex);
			// Decrease the TerrainCounter for RandomTiles Array
			terrainTypeCounter[selectedTile.getType().getValue()]--;
		}
	}

}
