package views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import models.ResourceTile;
import models.Tile;
import utils.Constants;
import utils.Types.ResourceTileType;

public class ResourceTileView extends TileView {
	// private final JLabel lblRes1;
	private Image BackgroundImage = null;
	private ImageIcon icon = null;
	private final JLabel lblTitle;

	public ResourceTileView(final ResourceTileType tileType) {
		super();

		lblTitle = new JLabel();
		add(lblTitle);
		lblTitle.setLocation(0, 0);
		lblTitle.setSize(Constants.TILE_WIDTH, Constants.TILE_HEIGHT / 2);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setVisible(true);

		// TODO: find a way to not use public/protected variables?
		lblTitle.setForeground(Color.BLACK);
		switch (tileType) {
		case FERTILE:
			lblTitle.setText("Fertile");
			break;
		case FOREST:
			lblTitle.setText("Forest");
			break;
		case HILLS:
			lblTitle.setText("Hills");
			break;
		case MOUNTAINS:
			lblTitle.setText("Mountains");
			break;
		case DESERT:
			lblTitle.setText("Desert");
			break;
		case SWAMP:
			lblTitle.setText("Swamp");
		}
	}

	public void setTile(final Tile t) {
		final ResourceTile tile = (ResourceTile) t;
		// Remove the title from the tile placeholder
		lblTitle.setText("");

		// Both switches will make the correct words and then they will be
		// Concatenated to create the correct icon path
		String typeName = "";
		String resourceName = "";
		String iconPath = "";

		typeName = tile.getType().toString().toLowerCase();

		switch (tile.getResource()) {
		case ONE_GOLD:
			resourceName = "1_gold";
			break;
		case ONE_FAVOR:
			resourceName = "1_favor";
			break;
		case ONE_FOOD:
			resourceName = "1_food";
			break;
		case ONE_WOOD:
			resourceName = "1_wood";
			break;
		case TWO_GOLDS:
			resourceName = "2_golds";
			break;
		case TWO_FAVORS:
			resourceName = "2_favors";
			break;
		case TWO_FOODS:
			resourceName = "2_foods";
			break;
		case TWO_WOODS:
			resourceName = "2_woods";
			break;
		}
		// Build the iconPath
		iconPath = "res/production_tiles/tile_" + typeName + "_" + resourceName
				+ ".png";
		this.icon = new ImageIcon(iconPath, String.valueOf(tile.getType())
				+ "-" + String.valueOf(tile.getResource()));

		// Assign the background image the proper background
		BackgroundImage = icon.getImage();
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		int x, y;

		/*
		 * If the Tile has a image (a ResourceTile is placed on this tile
		 * placeholder, center the image. If not, just use (0,0) as coordinates
		 * to paint.
		 */
		if (BackgroundImage != null) {
			x = (this.getWidth() - BackgroundImage.getWidth(null)) / 2;
			y = (this.getHeight() - BackgroundImage.getHeight(null)) / 2;
		} else {
			x = 0;
			y = 0;
		}
		g.drawImage(BackgroundImage, x, y, null);
	}
}
