package controllers;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import utils.Types.ResourceCubeType;
import utils.Types.ResourceTileType;
import utils.Types.ResourceType;
import models.Board;
import models.Player;
import models.ResourceTile;
import models.ResourcesBank;
import models.TilePlaceHolder;

public class GatherControl {
	private final String TYPE_TERRAIN = "Terrain Type";
	private final String TYPE_RESOURCE = "Resource Type";
	
	private ResourcesBank bank;
	
	public GatherControl(ResourcesBank newBank) {
		bank = newBank;
	}
	
	public void play(Player player0, Player player1, Player player2) {
		gather(player0);
		gather(player1);
		gather(player2);
	}
	
	private void gather(Player curPlayer) {
		String resourceType;
		int[] requestedResources;
		
		// Prompt player for Resource Type to gather from
		resourceType = humanTypeChoice();

		if (resourceType == TYPE_TERRAIN) {
			requestedResources = gatherByTerrain(curPlayer);
		} else {
			requestedResources = gatherByResource(curPlayer);
		}
		
		// Get Resources from Bank
		getResourcesFromBank(requestedResources, curPlayer);
	}
	
	private String humanTypeChoice() {
		String[] playerOptions = new String[2];
		
		playerOptions[0] = TYPE_TERRAIN;
		playerOptions[1] = TYPE_RESOURCE;
		
		JFrame frame = new JFrame();
		return (String) JOptionPane.showInputDialog(frame, 
													"Please select which type of terrain to gather from.", 
													"Select Gather Type", 
													JOptionPane.QUESTION_MESSAGE, 
													null, 
													playerOptions, 
													playerOptions[0]);
	}
	
	private int[] gatherByTerrain(Player curPlayer) {
		ResourceTileType playerChoice;
		ResourceTile[] resourceTile;
		int[] requested = new int[4];
				
		// Get the player's resource Tiles
		resourceTile = getTileArray(curPlayer);
		
		if (curPlayer.human) {		
			// Prompt human for Terrain choice
			playerChoice = humanTerrainChoice();
		} else {
			// Choose for AI
			// TODO Choose better for AI
			playerChoice = ResourceTileType.FERTILE;
		}
		
		// Count Resources gathered from players resource tiles
		int i;
		for (i = 0; i < resourceTile.length; i++) {
			if (resourceTile[i] != null) {
				if (resourceTile[i].getType() == playerChoice) {
					switch(resourceTile[i].getResource()) {
					case ONE_FAVOR:
						requested[ResourceCubeType.FAVOR.getValue()] += 1;
						break;
					case ONE_FOOD:
						requested[ResourceCubeType.FOOD.getValue()] += 1;
						break;
					case ONE_GOLD:
						requested[ResourceCubeType.GOLD.getValue()] += 1;
						break;
					case ONE_WOOD:
						requested[ResourceCubeType.WOOD.getValue()] += 1;
						break;
					case TWO_FAVORS:
						requested[ResourceCubeType.FAVOR.getValue()] += 2;
						break;
					case TWO_FOODS:
						requested[ResourceCubeType.FOOD.getValue()] += 2;
						break;
					case TWO_GOLDS:
						requested[ResourceCubeType.GOLD.getValue()] += 2;
						break;
					case TWO_WOODS:
						requested[ResourceCubeType.WOOD.getValue()] += 2;
					}
				}
			}
		}
		
		return requested;
	}
	
	private int[] gatherByResource(Player curPlayer) {
		ResourceCubeType playerChoice;
		ResourceTile[] resourceTile;
		int[] requested = new int[4];
		ResourceType resourceOption1, resourceOption2;
		
		// Get the player's resource Tiles
		resourceTile = getTileArray(curPlayer);
		
		if (curPlayer.human) {
			// Prompt human for Terrain choice
			playerChoice = humanResourceChoice();
		} else {
			// Choose for AI
			// TODO Choose better for AI
			playerChoice = ResourceCubeType.FOOD;
		}

		// Set search criteria
		switch (playerChoice) {
		case FAVOR:
			resourceOption1 = ResourceType.ONE_FAVOR;
			resourceOption2 = ResourceType.TWO_FAVORS;
			break;
		case FOOD:
			resourceOption1 = ResourceType.ONE_FOOD;
			resourceOption2 = ResourceType.TWO_FOODS;
			break;
		case GOLD:
			resourceOption1 = ResourceType.ONE_GOLD;
			resourceOption2 = ResourceType.TWO_GOLDS;
			break;
		default:
			resourceOption1 = ResourceType.ONE_WOOD;
			resourceOption2 = ResourceType.TWO_WOODS;
			break;			
		}
		
		// Count Resources gathered from players resource tiles
		int i;
		for (i = 0; i < resourceTile.length; i++) {
			if (resourceTile[i] != null) {
				if (resourceTile[i].getResource() == resourceOption1) {
					requested[playerChoice.getValue()] += 1;
				} else if (resourceTile[i].getResource() == resourceOption2) {
					requested[playerChoice.getValue()] += 2;
				}
			}
		}
		
		return requested;
	}
	
	private void getResourcesFromBank(int[] requested, Player curPlayer) {
		ResourceCubeType curType;
		int i;
		for (i = 0; i < 4; i++) {
			curType = ResourceCubeType.getTypeForInt(i);
			curPlayer.updateResource(curType, bank.getRequestedResource(curType, requested[i]));
		}
	}
	
	private ResourceTileType humanTerrainChoice() {
		String[] playerOptions = new String[ResourceTileType.values().length];
		String humanResponse;
		
		int i = 0;
		for (ResourceTileType r: ResourceTileType.values()) {
			playerOptions[i] = r.getString();
			i++;
		}
		
		JFrame frame = new JFrame();
		humanResponse = (String) JOptionPane.showInputDialog(frame, 
															 "Please select which type of terrain to gather from.", 
															 "Select Gather Type", 
															 JOptionPane.QUESTION_MESSAGE, 
															 null, 
															 playerOptions, 
															 playerOptions[0]);

		return ResourceTileType.getTypeForString(humanResponse);
	}
	
	private ResourceCubeType humanResourceChoice() {
		String[] playerOptions = new String[4];
		String humanResponse;
		
		playerOptions[ResourceCubeType.FAVOR.getValue()] = ResourceCubeType.FAVOR.getName();
		playerOptions[ResourceCubeType.FOOD.getValue()] = ResourceCubeType.FOOD.getName();
		playerOptions[ResourceCubeType.GOLD.getValue()] = ResourceCubeType.GOLD.getName();
		playerOptions[ResourceCubeType.WOOD.getValue()] = ResourceCubeType.WOOD.getName();
		
		JFrame frame = new JFrame();
		humanResponse = (String) JOptionPane.showInputDialog(frame, 
															 "Please select which type of terrain to gather from.", 
															 "Select Gather Type", 
															 JOptionPane.QUESTION_MESSAGE, 
															 null, 
															 playerOptions, 
															 playerOptions[0]);

		return ResourceCubeType.getTypeForString(humanResponse);
	}
	
	private ResourceTile[] getTileArray(Player curPlayer) {
		Board tempBoard = curPlayer.getBoard();
		ArrayList<TilePlaceHolder> tempProd = tempBoard.getProductionArea();
		
		TilePlaceHolder[] tempPH = new TilePlaceHolder[tempProd.size()];
		tempPH = tempProd.toArray(tempPH);
		
		ResourceTile[] resourceTile = new ResourceTile[tempProd.size()];
		int i;
		for(i = 0; i < tempProd.size(); i++) {
			resourceTile[i] = (ResourceTile) tempPH[i].getTile();
		}

		return resourceTile;
	}
}
