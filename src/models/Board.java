/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Feb 12, 2015
 */
package models;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import utils.Constants;
import utils.ResEnum;
import utils.Types.BoardType;
import utils.Types.GridType;
import utils.Types.ResourceTileType;
import views.TileGridView;

/**
 * @author grolfsen
 *
 */
public class Board extends JPanel {

	// TODO: Separate JPanel from model
	private final BoardType type;
	private int buildingsCounter = 0;
	int freeTerrainCounter[] = new int[6];

	private Image BackgroundImage = null;
	private ImageIcon icon = null;

	private final ArrayList<TilePlaceHolder> productionArea = new ArrayList<TilePlaceHolder>();
	private final ArrayList<TilePlaceHolder> cityArea = new ArrayList<TilePlaceHolder>();

	private final ResStats boardResStats;

	private final TileGridView productionAreaGrid;

	public Board(final BoardType type) {
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

		// Creates a Graphical Grid to place tiles on it
		productionAreaGrid = new TileGridView(this, GridType.RESOURCE,
				productionArea);

		// Set the BoardImage as background image
		BackgroundImage = icon.getImage();

		// Creates a Panel final to show the final resource status of
		// the player
		// TODO: Probably update this class, so its constructor do the setValue
		// lines?
		boardResStats = new ResStats(this);
		boardResStats.setValue(ResEnum.FOOD, 5);
		boardResStats.setValue(ResEnum.FAVOR, 5);
		boardResStats.setValue(ResEnum.GOLD, 5);
		boardResStats.setValue(ResEnum.WOOD, 5);
		boardResStats.setValue(ResEnum.VICTORY, 0);

	}

	public void refresh() {
		productionAreaGrid.refresh();
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		g.drawImage(BackgroundImage, 0, 0, null);

		// Set Resource Bar location
		boardResStats.setTop(Constants.CUBES_BAR_Y_LOCATION);
		boardResStats.setLeft(Constants.CUBES_BAR_X_LOCATION);

		// TODO: Why is it necessary?
		this.refresh();
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
		for (final TilePlaceHolder t : productionArea) {
			if (t.getType() == tile.getType() && !t.isFilled()) {
				t.setFilled();
				// Add the tile to the graphical grid
				productionAreaGrid.addTile(tile, t.getX(), t.getY());
				// Decrease the Specific Free Terrain Counter
				freeTerrainCounter[tile.getType().getValue()]--;
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
			for (final TilePlaceHolder t : cityArea) {
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

	public BoardType getType() {
		return this.type;
	}

	// TODO: implement a camelCase Method (this method is not used yet)
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

	public ArrayList<TilePlaceHolder> getProductionArea() {
		return this.productionArea;
	}

}
