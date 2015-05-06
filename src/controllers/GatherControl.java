package controllers;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import models.Board;
import models.Player;
import models.ResourceTile;
import models.ResourcesBank;
import models.TilePlaceHolder;
import utils.Constants;
import utils.Types.CardType;
import utils.Types.ResourceCubeType;
import utils.Types.ResourceTileType;
import utils.Types.ResourceType;

public class GatherControl {
	private final String TYPE_TERRAIN = "Terrain Type";
	private final String TYPE_RESOURCE = "Resource Type";

	private final ResourcesBank mResourceBank;

	private Player[] mPlayers;
	private boolean mAllowGodPower = false;

	public GatherControl(final ResourcesBank resourceBank) {
		mResourceBank = resourceBank;
	}

	public void play(final Player[] players, final int i, final CardType type, final boolean allowGodPower) {
		this.mAllowGodPower = allowGodPower;
		this.mPlayers = players;

		// XXX: HARDCODED
		if (i == 0) {
			gather(0, type);
			gather(1, type);
			gather(2, type);
		} else if (i == 1) {
			gather(1, type);
			gather(2, type);
			gather(0, type);
		} else {
			gather(2, type);
			gather(0, type);
			gather(1, type);
		}
	}

	private void gather(final int i, final CardType type) {
		int[] requestedResources;
		String resourceType = "";

		// Check if it is a player or AI playing
		if (i == Constants.HUMAN_PLAYER) {
			// Prompt player for Resource Type to gather from
			resourceType = humanTypeChoice();
		} else {
			// Randomize AI Selection
			if (new Random().nextInt(1) == 0) {
				resourceType = TYPE_TERRAIN;
			}
		}

		// Checks response and do the proper procedure
		if (resourceType == TYPE_TERRAIN) {
			requestedResources = gatherByTerrain(i, type);
		} else {
			requestedResources = gatherByResource(i);
		}

		// Get Resources from Bank
		getResourcesFromBank(requestedResources, mPlayers[i]);
	}

	private String humanTypeChoice() {
		final String[] playerOptions = new String[2];

		// Options For the JOption Pane
		playerOptions[0] = TYPE_TERRAIN;
		playerOptions[1] = TYPE_RESOURCE;

		final JFrame frame = new JFrame();
		// Pops up a JOptionPane
		return (String) JOptionPane.showInputDialog(frame,
				"Please select which type of terrain to gather from.", "Select Gather Type",
				JOptionPane.QUESTION_MESSAGE, null, playerOptions, playerOptions[0]);
	}

	private int[] gatherByTerrain(final int i, final CardType type) {
		ResourceTileType playerChoice;
		ResourceTile[] resourceTile;
		final int[] requested = new int[4];

		// Get the player's resource Tiles
		resourceTile = getTileArray(mPlayers[i]);

		// Checks if it is a player or AI playing
		if (i == Constants.HUMAN_PLAYER) {
			// Prompt human for Terrain choice
			playerChoice = humanTerrainChoice();
			System.out.println(playerChoice);
		} else {
			// Get Player's Current Resource Tile Types
			final String[] playerOptions = mPlayers[i].getResourceTilesTypes();
			final Random r = new Random();
			// Choose a Random between possibilities
			final String selected = playerOptions[r.nextInt(playerOptions.length)];

			playerChoice = ResourceTileType.getType(selected);
		}

		// Count Resources gathered from players resource tiles
		for (int ii = 0; ii < resourceTile.length; ii++) {
			if (resourceTile[ii] != null) {
				if (resourceTile[ii].getType() == playerChoice) {
					switch (resourceTile[ii].getResource()) {
					case ONE_FAVOR:
						requested[ResourceCubeType.FAVOR.getValue()] += 1;
						break;
					case ONE_FOOD:
						requested[ResourceCubeType.FOOD.getValue()] += 1;
						// Egyptian Special Gather Card
						if (type == CardType.RA && mAllowGodPower == true) {
							requested[ResourceCubeType.FOOD.getValue()] += 1;
						}
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

	private int[] gatherByResource(final int i) {
		ResourceCubeType playerChoice;
		ResourceTile[] resourceTile;
		final int[] requested = new int[4];
		ResourceType resourceOption1, resourceOption2;

		// Get the player's resource Tiles
		resourceTile = getTileArray(mPlayers[i]);

		if (i == Constants.HUMAN_PLAYER) {
			// Prompt human for Terrain choice
			playerChoice = humanResourceChoice();
		} else {
			// Chooses a random tile for AI
			final Random r = new Random();
			playerChoice = ResourceCubeType.getType(r.nextInt(ResourceCubeType.values().length));
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
		for (int ii = 0; ii < resourceTile.length; ii++) {
			if (resourceTile[ii] != null) {
				if (resourceTile[ii].getResource() == resourceOption1) {
					requested[playerChoice.getValue()] += 1;
				} else if (resourceTile[ii].getResource() == resourceOption2) {
					requested[playerChoice.getValue()] += 2;
				}
			}
		}

		return requested;
	}

	private void getResourcesFromBank(final int[] requested, final Player curPlayer) {
		ResourceCubeType curType;
		int i;
		for (i = 0; i < 4; i++) {
			curType = ResourceCubeType.getType(i);
			curPlayer.incrementResource(curType, mResourceBank.getRequestedResource(curType, requested[i]));
		}
	}

	private ResourceTileType humanTerrainChoice() {

		final String[] playerOptions = mPlayers[0].getResourceTilesTypes();

		final JFrame frame = new JFrame();
		final String humanResponse = (String) JOptionPane.showInputDialog(frame,
				"Please select which type of terrain to gather from.", "Select Terrain Type",
				JOptionPane.QUESTION_MESSAGE, null, playerOptions, playerOptions[0]);

		System.out.println("Humand Res: " + humanResponse);
		return ResourceTileType.getType(humanResponse);
	}

	private ResourceCubeType humanResourceChoice() {
		final String[] playerOptions = new String[4];

		// Options for the JOption Pane
		playerOptions[ResourceCubeType.FAVOR.getValue()] = ResourceCubeType.FAVOR.getName();
		playerOptions[ResourceCubeType.FOOD.getValue()] = ResourceCubeType.FOOD.getName();
		playerOptions[ResourceCubeType.GOLD.getValue()] = ResourceCubeType.GOLD.getName();
		playerOptions[ResourceCubeType.WOOD.getValue()] = ResourceCubeType.WOOD.getName();

		final JFrame frame = new JFrame();
		final String humanResponse = (String) JOptionPane.showInputDialog(frame,
				"Please select which type of Resource to gather from.", "Select Resource Type",
				JOptionPane.QUESTION_MESSAGE, null, playerOptions, playerOptions[0]);

		return ResourceCubeType.getTypeForString(humanResponse);
	}

	private ResourceTile[] getTileArray(final Player curPlayer) {
		final Board tempBoard = curPlayer.getBoard();
		final ArrayList<TilePlaceHolder> tempProd = tempBoard.getProductionArea();

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
