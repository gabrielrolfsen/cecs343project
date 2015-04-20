package controllers;

import javax.swing.JOptionPane;

import models.Board;
import models.BuildModel;
import models.BuildingTile;
import models.Player;
import utils.Types.BuildingTileType;
import views.BuildView;

public class BuildController {

	private BuildView curBuildView;
	private BuildModel curBuildModel;
	private Player curPlayer;
	
	public BuildController() {
		curBuildView = new BuildView(this);
		curBuildModel = new BuildModel();
		curBuildView.setVisible(false);
	}
	
	public void play(Player cardPlayer) {
		curPlayer = cardPlayer;
		
		// Display dialog showing all building types
		curBuildView.setVisible(true);
	}
	
	public void playerChose(int playerChoice) {
		try {
			// Check if player has enough resources
			if (!curBuildModel.enoughFunds(curPlayer, playerChoice)) {
				throw new IndexOutOfBoundsException();
			}
			
			// Check if player already owns building
			if (curPlayer.buildingOwned[playerChoice]) {
				
				if (BuildingTileType.getTypeForInt(playerChoice) != 
					BuildingTileType.HOUSE) {
					
					throw new ArrayStoreException();
					
				} else if (curPlayer.housesOwned == 10) {
					
					throw new IllegalArgumentException();
					
				}
			}
			
			// Subtract appropriate amount of resources from the player
			curBuildModel.purchaseBuilding(curPlayer, playerChoice);
			
			// Designate player owning building
			curPlayer.buildingOwned[playerChoice] = true;
		
			// display purchased building on player board
			Board curBoard;
			BuildingTile curBuildingTile;
			curBoard = curPlayer.getBoard();
			curBuildingTile = new BuildingTile(BuildingTileType.getTypeForInt(playerChoice));
			curBoard.addBuildingTile(curBuildingTile);
			
			// Close dialog
			curBuildView.setVisible(false);
			
		}
		catch (ArrayStoreException e) {
			JOptionPane.showMessageDialog(null, "May only own 1 of selected building type.", "No Space", JOptionPane.PLAIN_MESSAGE);
		}
		catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, "May only own 10 houses.", "No Space", JOptionPane.PLAIN_MESSAGE);
		}
		catch (IndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, "Not enough resources.", "No Space", JOptionPane.PLAIN_MESSAGE);
		}
	}
}
