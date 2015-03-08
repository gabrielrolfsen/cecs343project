/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Mar 6, 2015
 */
package views;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import models.Board;
import models.ResStats;
import models.Tile;
import models.TilePlaceHolder;
import utils.Constants;
import utils.Types.GridType;

/**
 * @author grolfsen
 *
 */
public class BoardView extends JPanel {

	// Current board held in the view
	private Board board;

	private final ResStats boardResStats;
	private TileGridView productionAreaGrid;
	private TileGridView cityAreaGrid;
	private Image BackgroundImage = null;
	private final ImageIcon icon = null;

	public BoardView() {
		super();

		boardResStats = new ResStats(this);
	}

	private void setCityAreaGrid(final ArrayList<TilePlaceHolder> cityArea) {
		cityAreaGrid = new TileGridView(this, GridType.BUILDING, cityArea);
	}

	private void setProductionAreaGrid(
			final ArrayList<TilePlaceHolder> productionArea) {
		productionAreaGrid = new TileGridView(this, GridType.RESOURCE,
				productionArea);
	}

	public void addResourceTile(final Tile t, final int posX, final int posY) {
		productionAreaGrid.addTile(t, posX, posY);
	}

	public Board getBoard() {
		return this.board;
	}

	public void setBoard(final Board board) {
		setProductionAreaGrid(board.getProductionArea());
		BackgroundImage = board.getIcon().getImage();
		this.board = board;
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
}
