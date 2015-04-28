package views;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import models.Board;
import models.ResourceTile;
import models.TilePlaceHolder;

public class TilePanelView extends JPanel {

	private Board myBoard;
	private TileType myType;
	private JLabel[] tileArray;
	
	public static final int MAX_TILES = 16;
	public static final int RESOURCE = 1;
	public static final int BUILDING = 2;
	
	public static enum TileType {PRODUCTION, BUILDING};
	
	public TilePanelView(TileType newType) {
		super();
		
		this.setLayout(new GridLayout(4,4)); 
		
		tileArray = new JLabel[MAX_TILES];
		int i;
		for (i = 0; i < MAX_TILES; i++) {
			add(tileArray[i]);
		}
		
		myType = newType;
	}
	
	public void setBoard(Board newBoard) {
		myBoard = newBoard;
	}
	
	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		
		ResourceTile[] tempArray = new ResourceTile[MAX_TILES];
		tempArray = getTileArray();
		
		int i;
		for (i = 0; i < MAX_TILES; i++) {
			this.tileArray[i].setIcon(tempArray[i].getIcon());
		}
	}
	
	private ResourceTile[] getTileArray() {
		final Board tempBoard = this.myBoard;
		ArrayList<TilePlaceHolder> tempProd;
				
		if (myType == TileType.PRODUCTION) {
			tempProd = tempBoard.getProductionArea();
		} else {
			tempProd = tempBoard.getCityArea();
		}
		
		TilePlaceHolder[] tempPH = new TilePlaceHolder[tempProd.size()];
		tempPH = tempProd.toArray(tempPH);

		final ResourceTile[] resourceTile = new ResourceTile[tempProd.size()];
		int i;
		for (i = 0; i < tempProd.size(); i++) {
			resourceTile[i] = (ResourceTile) tempPH[i].getTile();
		}

		return resourceTile;
	}
}
