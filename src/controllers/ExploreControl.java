package controllers;

import javax.swing.JOptionPane;

import utils.Constants;
import views.ExploreView;
import models.Board;
import models.ExploreModel;
import models.Player;
import models.ResourceTileBank;

public class ExploreControl {

	private ExploreModel exploreModel;
	private ExploreView exploreView;
	private Player[] playerArray;
	private int curPlayer;
	
	public ExploreControl(ResourceTileBank curResourcePool, Player[] curPlayerArray) {
		exploreModel = new ExploreModel(curResourcePool);
		exploreView = new ExploreView(exploreModel, this);
		exploreView.setLocation(100, 100);
		
		playerArray = curPlayerArray;
	}
	
	public void play(int startingPlayer, int numTiles) {
		// Draw amount of random resource tiles shown on card
		exploreModel.getTiles(numTiles);
		
		// Player who played card is designated current player
		curPlayer = startingPlayer;
		
		
		// Designate all players as turn not taken
		int i;
		for (i = 0; i < Constants.MAX_PLAYERS; i++) {
			playerArray[i].turnTaken = false;
		}
		
		
		// Display dialog box showing drawn resource tiles
		exploreView.setVisible(true);
		exploreView.repaint();

		// Current player chooses tile
		takeTurn();
		
	}

	
	private void takeTurn() {
		int tileNumber;
		
		if (playerArray[curPlayer].turnTaken) {
			endExplore();
		}
		else if (!playerArray[curPlayer].human) {
			// Choose tile for AI
			tileNumber = exploreModel.chooseForPlayer(playerArray[curPlayer]);
			
			playerChose(tileNumber);
		}
		// else wait for human player response from exploreView
	}
	
	private void advancePlayer() {
		curPlayer++;
			
		if (curPlayer >= Constants.MAX_PLAYERS) {
			curPlayer = 0;
		}
	}
	
	public void playerChose(int tileNumber) {
		try {
			if ((tileNumber >= 0) && !exploreModel.spotAvailable(tileNumber, playerArray[curPlayer])) {
				throw new ArrayStoreException();
			}
			
			// If tile number is a valid number, place the tile
			// otherwise, a negative number means the player passed
			if (tileNumber >= 0) {
			
				exploreModel.placeTile(tileNumber, playerArray[curPlayer]);
				exploreView.repaint();
			
			}
		
			playerArray[curPlayer].turnTaken = true;
			advancePlayer();
			takeTurn();
		} 
		catch (ArrayStoreException e){
			JOptionPane.showMessageDialog(null, "Space not available on board.", "No Space", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	private void endExplore() {
		// Return unchosen tiles to the bank
		exploreModel.returnTiles();
	}
}
