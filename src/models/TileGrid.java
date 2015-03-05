package models;

import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;

import utils.TileGridEnum;


public class TileGrid {
	private static final int BOARD_HEIGHT = 600;
	private static final int BOARD_WIDTH = 800;
	private static final int GRID_TOP = BOARD_HEIGHT / 2;
	private static final int GRID_LEFT = BOARD_WIDTH / 2;
	
	public Tile[] TileArray;
	
	public TileGrid(JPanel jfrm, TileGridEnum newType) {
		TileArray = new Tile[16];
		
		Insets insFrame = jfrm.getInsets();
		
		int i, j, k;
		int intTop = GRID_TOP - insFrame.top;
		int intLeft;
		int intLeftTemp;
		int intHeightInc = (BOARD_HEIGHT - insFrame.top) / 2 / 4 + 2;
		int intWidthInc = (BOARD_WIDTH - insFrame.left * 2) / 2 / 4;
		
		if (newType == TileGridEnum.BUILDING) {
			intLeft = GRID_LEFT - insFrame.left;
		} else {
			intLeft = 0;
		}
		
		intLeftTemp = intLeft;
		
		k = 0;
		for(i = 0; i < 4; i++) {
			
			for(j = 0; j < 4; j++) {
				
				if (newType == TileGridEnum.BUILDING) {
					TileArray[k] = new TileBuild();
				} else {
					TileArray[k] = new TileRes();
				}
				
				jfrm.add(TileArray[k]);
				TileArray[k].setSize(intWidthInc, intHeightInc);
				TileArray[k].setLocation(intLeftTemp, intTop);
				
				intLeftTemp += intWidthInc;
				k++;
			}
			
			intTop += intHeightInc;
			intLeftTemp = intLeft;
		}
	}
	
	public void Refresh() {
		int i;
		for (i = 0; i < 16; i++) {
			TileArray[i].Refresh();
		}
	}
}
