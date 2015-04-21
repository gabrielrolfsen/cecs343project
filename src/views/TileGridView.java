package views;

import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JPanel;

import models.Tile;
import models.TilePlaceHolder;
import utils.Constants;
import utils.Types.GridType;

public class TileGridView {

	private final TileView[] tileArray = new TileView[16];

	public TileGridView(final JPanel parentPanel, final GridType type,
			final ArrayList<TilePlaceHolder> tileList) {
		int posXGrid = -1;
		final Insets insFrame = parentPanel.getInsets();

		// TODO: Change to constants?
		// Calculates the top of the grid based on constant (window's size)
		int posYTile = Constants.GRID_TOP - insFrame.top - 15;
		final int tileHeight = (Constants.BOARD_HEIGHT - insFrame.top) / 8 - 2;
		final int tileWidth = (Constants.BOARD_WIDTH - insFrame.left * 2) / 8;

		switch (type) {
		case BUILDING:
			posXGrid = (7 * tileWidth);
			for (int i = 0; i < 16; i++) {
				tileArray[i] = new BuildingTileView();
				if (tileList.get(i).isFilled()) {
					((BuildingTileView) this.tileArray[i]).setTile(tileList
							.get(i).getTile());
				}
			}
			break;
		case RESOURCE:
			posXGrid = (3 * tileWidth);
			for (int i = 0; i < 16; i++) {
				tileArray[i] = new ResourceTileView(tileList.get(i).getType());
				if (tileList.get(i).isFilled()) {
					((ResourceTileView) this.tileArray[i]).setTile(tileList
							.get(i).getTile());
				}
			}
			break;
		}

		/*
		 * First position starts at the right-most and top most position grid
		 * location
		 */
		int posXTile = posXGrid;

		// Iterator to add each tile on the grid in the right location
		// In the matrix is this one:
		// 12 13 14 15
		// 8 9 10 11
		// 4 5 6 7
		// 0 1 2 3
		for (int k = 15; k >= 0; k--) {
			// Set size and location based on the position
			// tileArray[k].setSize(tileWidth, tileHeight);
			tileArray[k].setLocation(posXTile, posYTile);
			parentPanel.add(tileArray[k]);

			/*
			 * Decrements tile's X position based on tile Width, jumps to the
			 * next column on the left
			 */
			posXTile -= tileWidth;

			// If the row ended
			if (k % 4 == 0) {
				// Increments tile's Y position based on tile Height, jumps to
				// next row
				posYTile += tileHeight;

				// Restarts X position
				posXTile = posXGrid;
			}
		}
	}

	/**
	 * Adds the Resource tile to the graphical grid, in the corresponding
	 * location.
	 * 
	 * @param t
	 *            Tile to be added to the Grid
	 */
	public void addTile(final GridType type, final Tile t, final int posX,
			final int posY) {
		final int pos = posX + posY * 4;
		switch (type) {
		case BUILDING:
			((BuildingTileView) this.tileArray[pos]).setTile(t);
			break;
		case RESOURCE:
			((ResourceTileView) this.tileArray[pos]).setTile(t);
			break;
		}

	}

	/**
	 * Call the refresh() method on each TileView Object
	 */
	public void refresh() {
		for (int i = 0; i < 16; i++) {
			tileArray[i].refresh();
		}
	}
}
