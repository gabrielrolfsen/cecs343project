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

import javax.swing.JPanel;

import models.Board;
import models.Tile;
import models.TilePlaceHolder;
import models.Unit;
import utils.Constants;
import utils.Types.BoardType;
import utils.Types.GridType;

/**
 * @author grolfsen
 *
 */
public class BoardView extends JPanel {

	private BoardType boardType;
	private final ResourceStatusView mBoardResStats = new ResourceStatusView(
			this);
	private TileGridView mProductionAreaGrid;
	private TileGridView mCityAreaGrid;
	private final ArrayList<UnitView> mArmy = new ArrayList<UnitView>();
	private Image BackgroundImage = null;

	public BoardView() {
		super();
	}

	public void updateArmy(final ArrayList<Unit> army) {
		int i = 0;

		mArmy.clear();
		for (final Unit u : army) {
			final UnitView uView = new UnitView(this, u.getType(), i);
			mArmy.add(uView);
			revalidate();
			repaint();
			i++;
		}

	}

	public void updateResources(final int[] resources) {
		mBoardResStats.updateResources(resources);
	}

	private void setCityAreaGrid(final ArrayList<TilePlaceHolder> cityArea) {
		mCityAreaGrid = new TileGridView(this, GridType.BUILDING, cityArea);
	}

	private void setProductionAreaGrid(
			final ArrayList<TilePlaceHolder> productionArea) {
		mProductionAreaGrid = new TileGridView(this, GridType.RESOURCE,
				productionArea);
	}

	public void addResourceTile(final Tile t, final int posX, final int posY) {
		mProductionAreaGrid.addTile(GridType.RESOURCE, t, posX, posY);
	}

	public void addBuildingTile(final Tile t, final int posX, final int posY) {
		mProductionAreaGrid.addTile(GridType.BUILDING, t, posX, posY);
	}

	public void setBoard(final Board board) {
		boardType = board.getType();
		setProductionAreaGrid(board.getProductionArea());
		setCityAreaGrid(board.getCityArea());
		BackgroundImage = board.getIcon().getImage();
	}

	public BoardType getBoardType() {
		return this.boardType;
	}

	public void refreshBoard() {
		mProductionAreaGrid.refresh();
		mCityAreaGrid.refresh();
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);

		g.drawImage(BackgroundImage, 0, 0, null);

		// Set Resource Bar location
		mBoardResStats.setTop(Constants.CUBES_BAR_Y_LOCATION);
		mBoardResStats.setLeft(Constants.CUBES_BAR_X_LOCATION);

		// TODO: Why is it necessary?
		this.refreshBoard();
	}
}
