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
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import utils.Constants;

/**
 * @author grolfsen
 *
 */
public class Board extends JPanel {

	private final String owner = "N/A";
	private int type = -1;
	private int buildingsCounter = 0;

	private Image BackgroundImage = null;
	private ImageIcon icon = null;

	private final ArrayList<Tile> productionArea = new ArrayList<Tile>();
	private final ArrayList<Tile> cityArea = new ArrayList<Tile>();

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
		BackgroundImage = icon.getImage();
		final int w = BackgroundImage.getWidth(this);
		final int h = BackgroundImage.getHeight(this);

		setPreferredSize(new Dimension(w, h));
	}

	@Override
	public void paintComponent(final Graphics g) {
		g.drawImage(BackgroundImage, 0, 0, null);
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
		icon = new ImageIcon("/res/board_norse.png");
	}

	/**
	 * Fills the production area of the Board with the Egypt Board configuration
	 * of tiles.
	 */
	public void createEgyptBoard() {
		productionArea.add(new Tile(Constants.TYPE_DESERT, 0, 0));
		productionArea.add(new Tile(Constants.TYPE_HILLS, 1, 0));
		productionArea.add(new Tile(Constants.TYPE_FERTILE, 2, 0));
		productionArea.add(new Tile(Constants.TYPE_HILLS, 3, 0));
		productionArea.add(new Tile(Constants.TYPE_DESERT, 0, 1));
		productionArea.add(new Tile(Constants.TYPE_DESERT, 1, 1));
		productionArea.add(new Tile(Constants.TYPE_FERTILE, 2, 1));
		productionArea.add(new Tile(Constants.TYPE_FERTILE, 3, 1));
		productionArea.add(new Tile(Constants.TYPE_FOREST, 0, 2));
		productionArea.add(new Tile(Constants.TYPE_DESERT, 1, 2));
		productionArea.add(new Tile(Constants.TYPE_FERTILE, 2, 2));
		productionArea.add(new Tile(Constants.TYPE_FERTILE, 3, 2));
		productionArea.add(new Tile(Constants.TYPE_DESERT, 0, 3));
		productionArea.add(new Tile(Constants.TYPE_DESERT, 1, 3));
		productionArea.add(new Tile(Constants.TYPE_SWAMP, 2, 3));
		productionArea.add(new Tile(Constants.TYPE_SWAMP, 3, 3));
		icon = new ImageIcon("res/board_egypt.png");
	}

	/**
	 * Fills the production area of the Board with the Greek Board configuration
	 * of tiles.
	 */
	public void createGreekBoard() {
		productionArea.add(new Tile(Constants.TYPE_DESERT, 0, 0));
		productionArea.add(new Tile(Constants.TYPE_HILLS, 1, 0));
		productionArea.add(new Tile(Constants.TYPE_HILLS, 2, 0));
		productionArea.add(new Tile(Constants.TYPE_HILLS, 3, 0));
		productionArea.add(new Tile(Constants.TYPE_HILLS, 0, 1));
		productionArea.add(new Tile(Constants.TYPE_HILLS, 1, 1));
		productionArea.add(new Tile(Constants.TYPE_HILLS, 2, 1));
		productionArea.add(new Tile(Constants.TYPE_HILLS, 3, 1));
		productionArea.add(new Tile(Constants.TYPE_HILLS, 0, 2));
		productionArea.add(new Tile(Constants.TYPE_MOUNTAINS, 1, 2));
		productionArea.add(new Tile(Constants.TYPE_FERTILE, 2, 2));
		productionArea.add(new Tile(Constants.TYPE_FOREST, 3, 2));
		productionArea.add(new Tile(Constants.TYPE_FERTILE, 0, 3));
		productionArea.add(new Tile(Constants.TYPE_FERTILE, 1, 3));
		productionArea.add(new Tile(Constants.TYPE_FOREST, 2, 3));
		productionArea.add(new Tile(Constants.TYPE_SWAMP, 3, 3));
		icon = new ImageIcon("res/board_greek.png");
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

		int type = -1;
		int x = -1;
		int y = -1;
		boolean isFilled = false;

		/**
		 * 
		 * @param type
		 *            Tile type, defined in /utils/Constants.java
		 * @param x
		 *            [0-3] Horizontal position of the tile on a 4x4 area
		 * @param y
		 *            [0-3] Vertical position of the tile on a 4x4 area
		 */
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
