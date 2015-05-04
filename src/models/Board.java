package models;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import utils.Coordinates;
import utils.Types.CultureType;
import utils.Types.BuildingTileType;
import utils.Types.ResourceTileType;

/**
 * @author grolfsen
 *
 */
public class Board {

	private final CultureType type;
	private int buildingsCounter = 0;
	int freeTerrainCounter[] = new int[6];
	// TODO: put icon on view only
	private ImageIcon icon = null;

	private boolean hasMarket = false;
	private boolean hasGreatTemple = false;
	private boolean hasStoreHouse = false;

	private final ArrayList<TilePlaceHolder> productionArea = new ArrayList<TilePlaceHolder>();
	private final ArrayList<TilePlaceHolder> cityArea = new ArrayList<TilePlaceHolder>();

	public Board(final CultureType type) {
		this.type = type;

		switch (type) {
		case NORSE:
			createNorseBoard();
			break;
		case EGYPTIAN:
			createEgyptBoard();
			break;
		case GREEK:
			createGreekBoard();
			break;
		}

		initCityArea();

	}

	private void initCityArea() {
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				cityArea.add(new TilePlaceHolder(x, y));
			}
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
	public Coordinates addResourceTile(final ResourceTile tile) {
		for (final TilePlaceHolder t : productionArea) {
			if (t.getType() == tile.getType() && !t.isFilled()) {
				t.setTile(tile);
				// Decrease the Specific Free Terrain Counter
				freeTerrainCounter[tile.getType().getValue()]--;
				return t.getCoordinates();
			}
		}
		return null;
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
			for (final TilePlaceHolder t : cityArea) {
				if (!t.isFilled()) {
					t.setTile(tile);
					this.buildingsCounter++;
					if (tile.getType() == BuildingTileType.MARKET) {
						hasMarket = true;
					} else if (tile.getType() == BuildingTileType.TEMPLE) {
						hasGreatTemple = true;
					} else if (tile.getType() == BuildingTileType.STOREHOUSE) {
						hasStoreHouse = true;
					}
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
		productionArea.add(new TilePlaceHolder(ResourceTileType.DESERT, 0, 0));
		productionArea.add(new TilePlaceHolder(ResourceTileType.FERTILE, 1, 0));
		productionArea.add(new TilePlaceHolder(ResourceTileType.FOREST, 2, 0));
		productionArea.add(new TilePlaceHolder(ResourceTileType.FERTILE, 3, 0));
		productionArea.add(new TilePlaceHolder(ResourceTileType.HILLS, 0, 1));
		productionArea.add(new TilePlaceHolder(ResourceTileType.SWAMP, 1, 1));
		productionArea.add(new TilePlaceHolder(ResourceTileType.FOREST, 2, 1));
		productionArea.add(new TilePlaceHolder(ResourceTileType.HILLS, 3, 1));
		productionArea.add(new TilePlaceHolder(ResourceTileType.FERTILE, 0, 2));
		productionArea.add(new TilePlaceHolder(ResourceTileType.FOREST, 1, 2));
		productionArea.add(new TilePlaceHolder(ResourceTileType.HILLS, 2, 2));
		productionArea
				.add(new TilePlaceHolder(ResourceTileType.MOUNTAINS, 3, 2));
		productionArea.add(new TilePlaceHolder(ResourceTileType.FERTILE, 0, 3));
		productionArea
				.add(new TilePlaceHolder(ResourceTileType.MOUNTAINS, 1, 3));
		productionArea
				.add(new TilePlaceHolder(ResourceTileType.MOUNTAINS, 2, 3));
		productionArea
				.add(new TilePlaceHolder(ResourceTileType.MOUNTAINS, 3, 3));
		icon = new ImageIcon("res/board_norse.png");
		freeTerrainCounter[ResourceTileType.DESERT.getValue()] = 1;
		freeTerrainCounter[ResourceTileType.FERTILE.getValue()] = 4;
		freeTerrainCounter[ResourceTileType.FOREST.getValue()] = 3;
		freeTerrainCounter[ResourceTileType.HILLS.getValue()] = 3;
		freeTerrainCounter[ResourceTileType.MOUNTAINS.getValue()] = 4;
		freeTerrainCounter[ResourceTileType.SWAMP.getValue()] = 1;
	}

	/**
	 * Fills the production area of the Board with the Egypt Board configuration
	 * of tiles.
	 */
	public void createEgyptBoard() {
		productionArea.add(new TilePlaceHolder(ResourceTileType.DESERT, 0, 0));
		productionArea.add(new TilePlaceHolder(ResourceTileType.HILLS, 1, 0));
		productionArea.add(new TilePlaceHolder(ResourceTileType.FERTILE, 2, 0));
		productionArea.add(new TilePlaceHolder(ResourceTileType.HILLS, 3, 0));
		productionArea.add(new TilePlaceHolder(ResourceTileType.DESERT, 0, 1));
		productionArea.add(new TilePlaceHolder(ResourceTileType.DESERT, 1, 1));
		productionArea.add(new TilePlaceHolder(ResourceTileType.FERTILE, 2, 1));
		productionArea.add(new TilePlaceHolder(ResourceTileType.FERTILE, 3, 1));
		productionArea.add(new TilePlaceHolder(ResourceTileType.FOREST, 0, 2));
		productionArea.add(new TilePlaceHolder(ResourceTileType.DESERT, 1, 2));
		productionArea.add(new TilePlaceHolder(ResourceTileType.FERTILE, 2, 2));
		productionArea.add(new TilePlaceHolder(ResourceTileType.FERTILE, 3, 2));
		productionArea.add(new TilePlaceHolder(ResourceTileType.DESERT, 0, 3));
		productionArea.add(new TilePlaceHolder(ResourceTileType.DESERT, 1, 3));
		productionArea.add(new TilePlaceHolder(ResourceTileType.SWAMP, 2, 3));
		productionArea.add(new TilePlaceHolder(ResourceTileType.SWAMP, 3, 3));
		icon = new ImageIcon("res/board_egypt.png");
		freeTerrainCounter[ResourceTileType.DESERT.getValue()] = 6;
		freeTerrainCounter[ResourceTileType.FERTILE.getValue()] = 5;
		freeTerrainCounter[ResourceTileType.FOREST.getValue()] = 1;
		freeTerrainCounter[ResourceTileType.HILLS.getValue()] = 2;
		freeTerrainCounter[ResourceTileType.MOUNTAINS.getValue()] = 0;
		freeTerrainCounter[ResourceTileType.SWAMP.getValue()] = 2;
	}

	/**
	 * Fills the production area of the Board with the Greek Board configuration
	 * of tiles.
	 */
	public void createGreekBoard() {
		productionArea.add(new TilePlaceHolder(ResourceTileType.DESERT, 0, 0));
		productionArea.add(new TilePlaceHolder(ResourceTileType.HILLS, 1, 0));
		productionArea.add(new TilePlaceHolder(ResourceTileType.HILLS, 2, 0));
		productionArea.add(new TilePlaceHolder(ResourceTileType.HILLS, 3, 0));
		productionArea.add(new TilePlaceHolder(ResourceTileType.HILLS, 0, 1));
		productionArea.add(new TilePlaceHolder(ResourceTileType.HILLS, 1, 1));
		productionArea.add(new TilePlaceHolder(ResourceTileType.HILLS, 2, 1));
		productionArea.add(new TilePlaceHolder(ResourceTileType.HILLS, 3, 1));
		productionArea.add(new TilePlaceHolder(ResourceTileType.HILLS, 0, 2));
		productionArea
				.add(new TilePlaceHolder(ResourceTileType.MOUNTAINS, 1, 2));
		productionArea.add(new TilePlaceHolder(ResourceTileType.FERTILE, 2, 2));
		productionArea.add(new TilePlaceHolder(ResourceTileType.FOREST, 3, 2));
		productionArea.add(new TilePlaceHolder(ResourceTileType.FERTILE, 0, 3));
		productionArea.add(new TilePlaceHolder(ResourceTileType.FERTILE, 1, 3));
		productionArea.add(new TilePlaceHolder(ResourceTileType.FOREST, 2, 3));
		productionArea.add(new TilePlaceHolder(ResourceTileType.SWAMP, 3, 3));
		icon = new ImageIcon("res/board_greek.png");
		freeTerrainCounter[ResourceTileType.DESERT.getValue()] = 1;
		freeTerrainCounter[ResourceTileType.FERTILE.getValue()] = 3;
		freeTerrainCounter[ResourceTileType.FOREST.getValue()] = 2;
		freeTerrainCounter[ResourceTileType.HILLS.getValue()] = 8;
		freeTerrainCounter[ResourceTileType.MOUNTAINS.getValue()] = 1;
		freeTerrainCounter[ResourceTileType.SWAMP.getValue()] = 1;
	}

	public int[] getFreeTerrainCounter() {
		return this.freeTerrainCounter;
	}

	public void setFreeTerrainCounter(final int[] counter) {
		this.freeTerrainCounter = counter;
	}

	public CultureType getType() {
		return this.type;
	}

	/**
	 * Returns the type on a string
	 * 
	 * @return
	 */
	public String getTypeName() {
		String typeName = "";
		switch (this.type) {
		case EGYPTIAN:
			typeName = "Egyptian";
			break;
		case NORSE:
			typeName = "Norse";
			break;
		case GREEK:
			typeName = "Greek";
			break;
		}
		return typeName;

	}

	public boolean hasMarket() {
		return this.hasMarket;
	}

	public boolean hasGreatTemple() {
		return this.hasGreatTemple;
	}

	public boolean hasStoreHouse() {
		return this.hasStoreHouse;
	}

	public ArrayList<TilePlaceHolder> getCityArea() {
		return this.cityArea;
	}

	public ArrayList<TilePlaceHolder> getProductionArea() {
		return this.productionArea;
	}

	public ImageIcon getIcon() {
		return this.icon;
	}

}
