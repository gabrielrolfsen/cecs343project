/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Feb 12, 2015
 */
package models;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import utils.CulEnum;
import utils.ResEnum;
import utils.TileGridEnum;
import utils.Types.BoardType;
import utils.Types.ResourceTileType;

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

	private final ArrayList<Tile> productionArea = new ArrayList<Tile>();
	private final ArrayList<Tile> cityArea = new ArrayList<Tile>();

	public ResStats boardResStats;
	public TileGrid boardBuildGrid;
	public ResGrid boardResGrid;
	
	
	public Board(final BoardType type) {
		boardBuildGrid = new TileGrid(this, TileGridEnum.BUILDING);
		boardResGrid = new ResGrid(this, TileGridEnum.RESOURCE);
		
		this.type = type;
		final int n = BoardType.NORSE.getValue();
		switch (type) {
		case NORSE:
			createNorseBoard();
			boardResGrid.setCulture(CulEnum.NORSE);
			break;
		case EGYPTIAN:
			createEgyptBoard();
			boardResGrid.setCulture(CulEnum.EGYPTIAN);
			break;
		case GREEK:
			createGreekBoard();
			boardResGrid.setCulture(CulEnum.GREEK);
			break;
		default:
			System.err.println("ERROR: " + type
					+ " is not a valid code for board type.");
			System.exit(1);
		}
		BackgroundImage = icon.getImage();
		//final int w = BackgroundImage.getWidth(this);
		//final int h = BackgroundImage.getHeight(this);

		//setPreferredSize(new Dimension(w, h));
		setPreferredSize(new Dimension(800, 600));
		//setSize(800, 600);
		setLayout(null);
		setVisible(true);

		boardResStats = new ResStats(this);
		boardResStats.setValue(ResEnum.FOOD, 5);
		boardResStats.setValue(ResEnum.FAVOR, 5);
		boardResStats.setValue(ResEnum.GOLD, 5);
		boardResStats.setValue(ResEnum.WOOD, 5);
		boardResStats.setValue(ResEnum.VICTORY, 0);
		
		
//		int i;
//		for (i = 0; i < 16; i++) {
//			boardResGrid.TileArray[i].setVisible(true);
//			((TileRes) boardResGrid.TileArray[i]).setRes(ResEnum.FOOD, true);
//		}

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(BackgroundImage, 0, 0, null);
		
		boardResStats.setTop(10);
		boardResStats.setLeft(700);

		boardResGrid.Refresh();
		boardBuildGrid.Refresh();
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
		for (final Tile t : productionArea) {
			if (t.getType() == tile.getType() && !t.isFilled()) {
				t.setFilled();
				System.out.println("BEFORE "
						+ freeTerrainCounter[tile.getType().getValue()]);
				freeTerrainCounter[tile.getType().getValue()]--;
				System.out.println("AFTER:"
						+ freeTerrainCounter[tile.getType().getValue()]);
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
			for (final Tile t : cityArea) {
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
		productionArea.add(new Tile(ResourceTileType.DESERT, 0, 0));
		productionArea.add(new Tile(ResourceTileType.FERTILE, 1, 0));
		productionArea.add(new Tile(ResourceTileType.FOREST, 2, 0));
		productionArea.add(new Tile(ResourceTileType.FERTILE, 3, 0));
		productionArea.add(new Tile(ResourceTileType.HILLS, 0, 1));
		productionArea.add(new Tile(ResourceTileType.SWAMP, 1, 1));
		productionArea.add(new Tile(ResourceTileType.FOREST, 2, 1));
		productionArea.add(new Tile(ResourceTileType.HILLS, 3, 1));
		productionArea.add(new Tile(ResourceTileType.FERTILE, 0, 2));
		productionArea.add(new Tile(ResourceTileType.FOREST, 1, 2));
		productionArea.add(new Tile(ResourceTileType.HILLS, 2, 2));
		productionArea.add(new Tile(ResourceTileType.MOUNTAINS, 3, 2));
		productionArea.add(new Tile(ResourceTileType.FERTILE, 0, 3));
		productionArea.add(new Tile(ResourceTileType.MOUNTAINS, 1, 3));
		productionArea.add(new Tile(ResourceTileType.MOUNTAINS, 2, 3));
		productionArea.add(new Tile(ResourceTileType.MOUNTAINS, 3, 3));
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
		productionArea.add(new Tile(ResourceTileType.DESERT, 0, 0));
		productionArea.add(new Tile(ResourceTileType.HILLS, 1, 0));
		productionArea.add(new Tile(ResourceTileType.FERTILE, 2, 0));
		productionArea.add(new Tile(ResourceTileType.HILLS, 3, 0));
		productionArea.add(new Tile(ResourceTileType.DESERT, 0, 1));
		productionArea.add(new Tile(ResourceTileType.DESERT, 1, 1));
		productionArea.add(new Tile(ResourceTileType.FERTILE, 2, 1));
		productionArea.add(new Tile(ResourceTileType.FERTILE, 3, 1));
		productionArea.add(new Tile(ResourceTileType.FOREST, 0, 2));
		productionArea.add(new Tile(ResourceTileType.DESERT, 1, 2));
		productionArea.add(new Tile(ResourceTileType.FERTILE, 2, 2));
		productionArea.add(new Tile(ResourceTileType.FERTILE, 3, 2));
		productionArea.add(new Tile(ResourceTileType.DESERT, 0, 3));
		productionArea.add(new Tile(ResourceTileType.DESERT, 1, 3));
		productionArea.add(new Tile(ResourceTileType.SWAMP, 2, 3));
		productionArea.add(new Tile(ResourceTileType.SWAMP, 3, 3));
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
		productionArea.add(new Tile(ResourceTileType.DESERT, 0, 0));
		productionArea.add(new Tile(ResourceTileType.HILLS, 1, 0));
		productionArea.add(new Tile(ResourceTileType.HILLS, 2, 0));
		productionArea.add(new Tile(ResourceTileType.HILLS, 3, 0));
		productionArea.add(new Tile(ResourceTileType.HILLS, 0, 1));
		productionArea.add(new Tile(ResourceTileType.HILLS, 1, 1));
		productionArea.add(new Tile(ResourceTileType.HILLS, 2, 1));
		productionArea.add(new Tile(ResourceTileType.HILLS, 3, 1));
		productionArea.add(new Tile(ResourceTileType.HILLS, 0, 2));
		productionArea.add(new Tile(ResourceTileType.MOUNTAINS, 1, 2));
		productionArea.add(new Tile(ResourceTileType.FERTILE, 2, 2));
		productionArea.add(new Tile(ResourceTileType.FOREST, 3, 2));
		productionArea.add(new Tile(ResourceTileType.FERTILE, 0, 3));
		productionArea.add(new Tile(ResourceTileType.FERTILE, 1, 3));
		productionArea.add(new Tile(ResourceTileType.FOREST, 2, 3));
		productionArea.add(new Tile(ResourceTileType.SWAMP, 3, 3));
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

	// TODO: implement a camelCase Method
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

	/**
	 * Class used to represent the tiles on the board. The board has two 4x4
	 * tile areas, the city area (for BuildingTiles) and the production area
	 * (for ResourceTiles).
	 * 
	 * @author grolfsen
	 *
	 */
	class Tile {

		ResourceTileType type;
		int x = -1;
		int y = -1;
		boolean isFilled = false;

		/**
		 * 
		 * @param type
		 *            Tile type, defined in /utils/Types.java
		 * @param x
		 *            [0-3] Horizontal position of the tile on a 4x4 area
		 * @param y
		 *            [0-3] Vertical position of the tile on a 4x4 area
		 */
		public Tile(final ResourceTileType type, final int x, final int y) {
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

		public ResourceTileType getType() {
			return this.type;
		}

	}

}
