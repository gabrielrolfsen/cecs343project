/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Feb 12, 2015
 */
package models;

import java.util.ArrayList;

import utils.Constants;

/**
 * @author grolfsen
 *
 */
public class Board {

	private final String owner = "N/A";
	private int type = -1;
	private int buildingsCounter = 0;
	// TODO: Change Type "Object" to correct type
	private final Tile[] desertTiles = new Tile[10];
	private final ArrayList<Tile> productionArea = new ArrayList<Tile>();
	private final ArrayList<Tile> cityArea = new ArrayList<Tile>();

	// private final Object[][] productionArea = new Object[3][3];
	// private final Object[][] cityArea = new BuildingTile[3][3];

	public Board(final int type) {
		this.type = type;
		switch (type) {
		case Constants.TYPE_NORSE:
			createNorseBoard();
			break;
		case Constants.TYPE_EGYPTIAN:
			createEgyptBoard();
			break;
		case Constants.TYPE_GREEK:
			createGreekBoard();
			break;
		default:
			System.err.println("ERROR: " + type
					+ " is not a valid code for board type.");
			System.exit(1);
		}
	}

	/**
	 * Adds a Resource Tile to the production area of the Board.
	 * 
	 * @param tile
	 *            ResourceTile to be added to the board
	 * @return true if the tile was successful added, false if there's no
	 *         matching tile on the board or if all spaces are being used.
	 */
	public boolean addResourceTile(final ResourceTile tile) {
		for (final Tile t : cityArea) {
			if (t.getType() == tile.getType() && !t.isFilled()) {
				t.setFilled();
				return true;
			}
		}
		return false;
	}

	/**
	 * Adds a Building Tile to the city area of the Board.
	 * 
	 * @param tile
	 *            BuildingTile to be added to the board
	 * @return true if the tile was successful added, false if there's no
	 *         matching tile on the board or if all the 16 spaces are being
	 *         used.
	 */
	public boolean addBuildingTile(final BuildingTile tile) {
		if (this.buildingsCounter < 16) {
			for (final Tile t : productionArea) {
				if (!t.isFilled()) {
					t.setFilled();
					this.buildingsCounter++;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Fills the production area of the Board with the Norse Board configuration
	 * of tiles.
	 */
	public void createNorseBoard() {
		productionArea.add(new Tile(Constants.TYPE_DESERT, 0, 0));
		productionArea.add(new Tile(Constants.TYPE_FERTILE, 1, 0));
		productionArea.add(new Tile(Constants.TYPE_FOREST, 2, 0));
		productionArea.add(new Tile(Constants.TYPE_FERTILE, 3, 0));
		productionArea.add(new Tile(Constants.TYPE_HILLS, 0, 1));
		productionArea.add(new Tile(Constants.TYPE_SWAMP, 1, 1));
		productionArea.add(new Tile(Constants.TYPE_FOREST, 2, 1));
		productionArea.add(new Tile(Constants.TYPE_HILLS, 3, 1));
		productionArea.add(new Tile(Constants.TYPE_FERTILE, 0, 2));
		productionArea.add(new Tile(Constants.TYPE_FOREST, 1, 2));
		productionArea.add(new Tile(Constants.TYPE_HILLS, 2, 2));
		productionArea.add(new Tile(Constants.TYPE_MOUNTAINS, 3, 2));
		productionArea.add(new Tile(Constants.TYPE_FERTILE, 0, 3));
		productionArea.add(new Tile(Constants.TYPE_MOUNTAINS, 1, 3));
		productionArea.add(new Tile(Constants.TYPE_MOUNTAINS, 2, 3));
		productionArea.add(new Tile(Constants.TYPE_MOUNTAINS, 3, 3));
	}

	// TODO: implement this method - DO NOT remove the commented code
	public boolean createEgyptBoard() {
		// addDesertTile(0, 0);
		// addHillsTile(1, 0);
		// addFertileTile(2, 0);
		// addHillsTile(3, 0);
		// addDesertTile(0, 1);
		// addDesertTile(1, 1);
		// addFertileTile(2, 1);
		// addFertileTile(3, 1);
		// addForestTile(0, 2);
		// addDesertTile(1, 2);
		// addFertileTile(2, 2);
		// addFertileTile(3, 2);
		// addDesertTile(0, 3);
		// addDesertTile(1, 3);
		// addSwampTile(2, 3);
		// addSwampTile(3, 3);
		return false;
	}

	// TODO: implement this method - DO NOT remove the commented code
	public boolean createGreekBoard() {
		// addDesertTile(0, 0);
		// addHillsTile(1, 0);
		// addHillsTile(2, 0);
		// addHillsTile(3, 0);
		// addHillsTile(0, 1);
		// addHillsTile(1, 1);
		// addHillsTile(2, 1);
		// addHillsTile(3, 1);
		// addHillsTile(0, 2);
		// addMountainsTile(1, 2);
		// addFertileTile(2, 2);
		// addForestTile(3, 2);
		// addFertileTile(0, 3);
		// addFertileTile(1, 3);
		// addForestTile(2, 3);
		// addSwampTile(3, 3);
		return false;
	}

	// private void addDesertTile(final int i, final int j) {
	// // TODO: Change Type "Object" to Desert Tile type
	// productionArea[i][j] = new Object();
	// }
	//
	// private void addSwampTile(final int i, final int j) {
	// // TODO: Change Type "Object" to Swamp Tile type
	// productionArea[i][j] = new Object();
	// }
	//
	// private void addHillsTile(final int i, final int j) {
	// // TODO: Change Type "Object" to Hills Tile type
	// productionArea[i][j] = new Object();
	// }
	//
	// private void addFertileTile(final int i, final int j) {
	// // TODO: Change Type "Object" to Fertile Tile type
	// productionArea[i][j] = new Object();
	// }
	//
	// private void addForestTile(final int i, final int j) {
	// // TODO: Change Type "Object" to Forest Tile type
	// productionArea[i][j] = new Object();
	// }
	//
	// private void addMountainsTile(final int i, final int j) {
	// // TODO: Change Type "Object" to Mountains Tile type
	// productionArea[i][j] = new Object();
	// }

	class Tile {

		int type = -1;
		int x = -1;
		int y = -1;
		boolean isFilled = false;

		public Tile(final int type, final int x, final int y) {
			this.type = type;
			this.x = x;
			this.y = y;
		}

		public void setTile() {
			this.isFilled = true;
		}

		public void setFilled() {
			this.isFilled = true;
		}

		public boolean isFilled() {
			return this.isFilled;
		}

		public int getType() {
			return this.type;
		}

	}

}
