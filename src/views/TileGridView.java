package views;

import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JPanel;

import models.ResourceTile;
import models.TilePlaceHolder;
import utils.Constants;
import utils.Types.GridType;

public class TileGridView {

	private final ResourceTileView[] tileArray = new ResourceTileView[16];

	public TileGridView(final JPanel jfrm, final GridType newType,
			final ArrayList<TilePlaceHolder> tileList) {

		// TODO: Just work for RESOURCE TILES UNTIL NOW!!
		for (int i = 0; i < 16; i++) {
			tileArray[i] = new ResourceTileView(tileList.get(i).getType());
		}

		final Insets insFrame = jfrm.getInsets();

		// TODO: create Constants and use it ?
		// Calculates the top of the grid based on constant (window's size)
		int posYTile = Constants.GRID_TOP - insFrame.top;
		final int tileHeight = (Constants.BOARD_HEIGHT - insFrame.top) / 8 + 2;
		final int tileWidth = (Constants.BOARD_WIDTH - insFrame.left * 2) / 8;

		// X position for the grid and X position for each tile
		int posXGrid, posXTile;

		// Determines position of the grid based on the type
		if (newType == GridType.BUILDING) {
			// TODO: this part is not being used right now
			posXGrid = Constants.GRID_LEFT - insFrame.left;
		} else {
			posXGrid = (3 * tileWidth);
		}

		// First position starts at the right-most and top most position grid
		// location
		posXTile = posXGrid;
		System.out.println(posXGrid);

		// Iterator to add each tile on the grid in the right location
		for (int k = 15; k >= 0; k--) {
			// Set size and location based on the position
			tileArray[k].setSize(tileWidth, tileHeight);
			tileArray[k].setLocation(posXTile, posYTile);
			jfrm.add(tileArray[k]);
			// Decrements tile's X position based on tile Width, jumps to the
			// next column on the left
			posXTile -= tileWidth;

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
	public void addTile(final ResourceTile t, final int posX, final int posY) {
		final int pos = posX + posY * 4;
		this.tileArray[pos].setType(t.getType(), t.getResource());
		this.tileArray[pos].setVisible(true);
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
