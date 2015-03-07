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

import javax.swing.JFrame;
import javax.swing.JPanel;

import models.ResStats;
import models.TilePlaceHolder;
import utils.Constants;
import utils.Types.GridType;
import utils.Types.ResourceCubeType;

/**
 * @author grolfsen
 *
 */
public class BoardView extends JPanel {

	private final ResStats boardResStats;
	private TileGridView productionAreaGrid;
	private final Image BackgroundImage = null;
	JFrame parent = null;

	public BoardView(final JFrame parent) {
		super();
		this.parent = parent;

		boardResStats = new ResStats(this);
		boardResStats.setValue(ResourceCubeType.FOOD, 5);
		boardResStats.setValue(ResourceCubeType.FAVOR, 5);
		boardResStats.setValue(ResourceCubeType.GOLD, 5);
		boardResStats.setValue(ResourceCubeType.WOOD, 5);
		boardResStats.setValue(ResourceCubeType.VICTORY, 0);
	}

	public void setProductionAreaGrid(
			final ArrayList<TilePlaceHolder> productionArea) {
		productionAreaGrid = new TileGridView(this, GridType.RESOURCE,
				productionArea);
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

	private void refresh() {
		productionAreaGrid.refresh();
	}
}
