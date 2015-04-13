package models;

import java.util.ArrayList;
import java.util.Arrays;


public class ExploreModel {

	private ResourceTileBank resourcePool = null;
	
	public ResourceTile[] tileArray;
	
	public ExploreModel(ResourceTileBank curResourcePool) {
		resourcePool = curResourcePool;
		tileArray = new ResourceTile[18];
	}
	
	public void getTiles(int numTiles) {
		ArrayList<ResourceTile> tileList; 

		tileList = resourcePool.getRandomTiles(numTiles);
		tileArray = Arrays.copyOf(tileList.toArray(), 18, ResourceTile[].class);
	}
	
	public void placeTile(int tileNumber, Player curPlayer) {
		Board curBoard;
		
		curBoard = curPlayer.getBoard();
		
		curBoard.addResourceTile(tileArray[tileNumber]);
		
		tileArray[tileNumber] = null;
	}
	
	public void returnTiles() {
		ArrayList<ResourceTile> tileList;
		
		tileList = new ArrayList<ResourceTile>(Arrays.asList(tileArray));
		
		resourcePool.returnTilesToPool(tileList);
	}
	
	public boolean spotAvailable(int tileNumber, Player curPlayer) {
		Board curBoard;
		int[] freeTerrain;
		
		curBoard = curPlayer.getBoard();
		freeTerrain = curBoard.getFreeTerrainCounter();
		
		if (freeTerrain[tileArray[tileNumber].getType().getValue()] > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public int chooseForPlayer(Player curPlayer) {
		boolean choiceMade = false;
		int curTile;
		
		// AI picks the first tile that fits on its board
		for (curTile = 0; curTile < tileArray.length; curTile++) {
			if (tileArray[curTile] != null) {
				if (spotAvailable(curTile, curPlayer)) {
					choiceMade = true;
					break;
				}
			}
		}
		
		if (choiceMade) {
			return curTile;
		} else {
			return -1;
		}
	}
}
