package controllers;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import models.Board;
import models.BuildModel;
import models.BuildingTile;
import models.Player;
import utils.Coordinates;
import utils.Types.BuildingTileType;
import views.BuildView;
import views.MainFrameView;

public class BuildController {

	//private final BuildView curBuildView;
	private final BuildModel curBuildModel = new BuildModel();
	private Player curPlayer;
	
	public BuildController() {
		JFrame frame = new JFrame();
		//curBuildView = new BuildView(frame, this);
		//curBuildView.setVisible(false);
	}

	public void play(final Player cardPlayer) {
		BuildingTileType playerChoice;
		
		curPlayer = cardPlayer;

		// Display dialog showing all building types
		//curBuildView.setVisible(true);
		playerChoice = promptHuman();
		
		// Process Player's Request
		playerChose(playerChoice.getValue());
	}

	private BuildingTileType promptHuman() {
		final int MAX_BUILDINGS = 14;
		
		String[] playerOptions = new String[MAX_BUILDINGS];
		String playerChoice;
		
		int i;
		for (i = 0; i < MAX_BUILDINGS; i++) {
			playerOptions[i] = BuildingTileType.getStringForInt(i);
		}
		
		JFrame frame = new JFrame();
		playerChoice = (String) JOptionPane.showInputDialog(frame, 
													"Please select which building to purchase.", 
													"Purchase Building", 
													JOptionPane.QUESTION_MESSAGE, 
													null, 
													playerOptions, 
													playerOptions[0]);

		return BuildingTileType.getTypeForString(playerChoice);
	}
	
	public void playerChose(final int playerChoice) {
		try {
			// Check if player has enough resources
			if (!curBuildModel.enoughFunds(curPlayer, playerChoice)) {
				throw new IndexOutOfBoundsException();
			}

			// Check if player already owns building
			if (curPlayer.buildingOwned[playerChoice]) {

				if (BuildingTileType.getTypeForInt(playerChoice) != BuildingTileType.HOUSE) {

					throw new ArrayStoreException();

				} else if (curPlayer.housesOwned == 10) {

					throw new IllegalArgumentException();

				}
			}

			// Subtract appropriate amount of resources from the player
			curBuildModel.purchaseBuilding(curPlayer, playerChoice);

			// Designate player owning building
			curPlayer.buildingOwned[playerChoice] = true;

			// Add purchased building on player board
			Board curBoard;
			BuildingTile curBuildingTile;
			curBoard = curPlayer.getBoard();
			curBuildingTile = new BuildingTile(
					BuildingTileType.getTypeForInt(playerChoice));
			curBoard.addBuildingTile(curBuildingTile);
			//final Coordinates c = curBoard.addBuildingTile(curBuildingTile);
			//MainFrameView.getInstance().addBuildingTileToBoard(curBuildingTile, c.getX(), c.getY());

			// Close dialog
			//curBuildView.setVisible(false);

		} catch (final ArrayStoreException e) {
			JOptionPane.showMessageDialog(null,
					"May only own 1 of selected building type.", "No Space",
					JOptionPane.PLAIN_MESSAGE);
		} catch (final IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, "May only own 10 houses.",
					"No Space", JOptionPane.PLAIN_MESSAGE);
		} catch (final IndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, "Not enough resources.",
					"No Space", JOptionPane.PLAIN_MESSAGE);
		}
	}
}
