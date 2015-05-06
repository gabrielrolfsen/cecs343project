package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import models.BuildingTile;
import models.Tile;

public class BuildingTileView extends TileView {

	private ImageIcon icon = null;
	private Image BackgroundImage = null;
	
	//private final JLabel lblDesc;


	public BuildingTileView() {
		super();

		setBackground(Color.LIGHT_GRAY);

		/*
		lblDesc = new JLabel();
		add(lblDesc);
		final Dimension myDimension = getSize();
		lblDesc.setLocation(0, myDimension.height / 2);
		lblDesc.setSize(myDimension.width, myDimension.height / 2);
		lblDesc.setHorizontalAlignment(SwingConstants.CENTER);
		lblDesc.setFont(new Font("Serif", Font.PLAIN, 9));
		lblDesc.setVisible(false);
		*/
	}

	public void setTile(final Tile t) {
		final BuildingTile tile = (BuildingTile) t;

		/*
		switch (tile.getType()) {
		case HOUSE:
			// lblTitle.setText("House");
			lblDesc.setText("Villager");
			break;
		case WALL:
			// lblTitle.setText("Wall");
			lblDesc.setText("Defend City");
			break;
		case TOWER:
			// lblTitle.setText("Tower");
			lblDesc.setText("<html><center>Defend Production</center></html>");
			break;
		case STOREHOUSE:
			// lblTitle.setText("Storehouse");
			lblDesc.setText("<html><center>Store Resource Cubes</center></html>");
			break;
		case MARKET:
			// lblTitle.setText("Market");
			lblDesc.setText("Free Trade");
			break;
		case ARMORY:
			// lblTitle.setText("Armory");
			lblDesc.setText("+1 Unit in Battle");
			break;
		case QUARRY:
			// lblTitle.setText("Quarry");
			lblDesc.setText("<html><center>-1 Cost per Building</center></html>");
			break;
		case MONUMENT:
			// lblTitle.setText("Monument");
			lblDesc.setText("+2 Favor / Gather");
			break;
		case GRANARY:
			// lblTitle.setText("Granary");
			lblDesc.setText("+2 Food / Gather");
			break;
		case MINT:
			// lblTitle.setText("Gold Mint");
			lblDesc.setText("+2 Gold / Gather");
			break;
		case WORKSHOP_WOOD:
			// lblTitle.setText("<html><center>Wood Workshop</center><html>");
			lblDesc.setText("+2 Wood / Gather");
			break;
		case WORKSHOP_SIEGE:
			// lblTitle.setText("<html><center>Siege Engine Workshop</center><html>");
			lblDesc.setText("<html><center>Negates Walls and Towers +1 Building Destroyed</center><html>");
			break;
		case TEMPLE:
			// lblTitle.setText("Great Temple");
			lblDesc.setText("<html><center>Trade 8 favor for 1 Victory Point</center></html>");
			break;
		case WONDER:
			// lblTitle.setText("The Wonder");
			lblDesc.setText("End Game");
			break;
		}
		*/
		
		// Build the iconPath
		String iconPath = "res/building_tiles/" + tile.getType().getString() + ".png";
		this.icon = new ImageIcon(iconPath);

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
