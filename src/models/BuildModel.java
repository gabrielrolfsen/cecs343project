package models;

import utils.Types.BuildingTileType;
import utils.Types.ResourceCubeType;

public class BuildModel {
	private final int MAX_BUILDINGS = 14;
	private final int MAX_RESOURCE_TYPES = 4;

	private final int priceTable[][];

	public BuildModel() {
		// Create Building Price Table
		priceTable = new int[MAX_BUILDINGS][MAX_RESOURCE_TYPES];

		priceTable[BuildingTileType.HOUSE.getValue()][ResourceCubeType.WOOD
				.getValue()] = 2;
		priceTable[BuildingTileType.HOUSE.getValue()][ResourceCubeType.FOOD
				.getValue()] = 2;

		priceTable[BuildingTileType.WALL.getValue()][ResourceCubeType.WOOD
				.getValue()] = 3;
		priceTable[BuildingTileType.WALL.getValue()][ResourceCubeType.GOLD
				.getValue()] = 3;

		priceTable[BuildingTileType.TOWER.getValue()][ResourceCubeType.WOOD
				.getValue()] = 3;
		priceTable[BuildingTileType.TOWER.getValue()][ResourceCubeType.GOLD
				.getValue()] = 3;

		priceTable[BuildingTileType.MARKET.getValue()][ResourceCubeType.GOLD
				.getValue()] = 3;
		priceTable[BuildingTileType.MARKET.getValue()][ResourceCubeType.FAVOR
				.getValue()] = 2;

		priceTable[BuildingTileType.STOREHOUSE.getValue()][ResourceCubeType.WOOD
				.getValue()] = 2;
		priceTable[BuildingTileType.STOREHOUSE.getValue()][ResourceCubeType.GOLD
				.getValue()] = 2;
		priceTable[BuildingTileType.STOREHOUSE.getValue()][ResourceCubeType.FOOD
				.getValue()] = 2;
		priceTable[BuildingTileType.STOREHOUSE.getValue()][ResourceCubeType.FAVOR
				.getValue()] = 2;

		priceTable[BuildingTileType.ARMORY.getValue()][ResourceCubeType.WOOD
				.getValue()] = 3;
		priceTable[BuildingTileType.ARMORY.getValue()][ResourceCubeType.GOLD
				.getValue()] = 2;

		priceTable[BuildingTileType.QUARRY.getValue()][ResourceCubeType.GOLD
				.getValue()] = 1;
		priceTable[BuildingTileType.QUARRY.getValue()][ResourceCubeType.FOOD
				.getValue()] = 4;

		priceTable[BuildingTileType.MONUMENT.getValue()][ResourceCubeType.GOLD
				.getValue()] = 2;
		priceTable[BuildingTileType.MONUMENT.getValue()][ResourceCubeType.FOOD
				.getValue()] = 3;

		priceTable[BuildingTileType.GRANARY.getValue()][ResourceCubeType.GOLD
				.getValue()] = 2;
		priceTable[BuildingTileType.GRANARY.getValue()][ResourceCubeType.FOOD
				.getValue()] = 3;

		priceTable[BuildingTileType.WORKSHOP_WOOD.getValue()][ResourceCubeType.GOLD
				.getValue()] = 3;
		priceTable[BuildingTileType.WORKSHOP_WOOD.getValue()][ResourceCubeType.FOOD
				.getValue()] = 2;

		priceTable[BuildingTileType.MINT.getValue()][ResourceCubeType.WOOD
				.getValue()] = 2;
		priceTable[BuildingTileType.MINT.getValue()][ResourceCubeType.FOOD
				.getValue()] = 3;

		priceTable[BuildingTileType.WORKSHOP_SIEGE.getValue()][ResourceCubeType.WOOD
				.getValue()] = 3;
		priceTable[BuildingTileType.WORKSHOP_SIEGE.getValue()][ResourceCubeType.GOLD
				.getValue()] = 2;

		priceTable[BuildingTileType.TEMPLE.getValue()][ResourceCubeType.WOOD
				.getValue()] = 4;
		priceTable[BuildingTileType.TEMPLE.getValue()][ResourceCubeType.GOLD
				.getValue()] = 4;
		priceTable[BuildingTileType.TEMPLE.getValue()][ResourceCubeType.FOOD
				.getValue()] = 4;
		priceTable[BuildingTileType.TEMPLE.getValue()][ResourceCubeType.FAVOR
				.getValue()] = 4;

		priceTable[BuildingTileType.WONDER.getValue()][ResourceCubeType.WOOD
				.getValue()] = 10;
		priceTable[BuildingTileType.WONDER.getValue()][ResourceCubeType.GOLD
				.getValue()] = 10;
		priceTable[BuildingTileType.WONDER.getValue()][ResourceCubeType.FOOD
				.getValue()] = 10;
		priceTable[BuildingTileType.WONDER.getValue()][ResourceCubeType.FAVOR
				.getValue()] = 10;

	}

	public void purchaseBuilding(final Player curPlayer, final int playerChoice) {
		curPlayer.incrementResource(ResourceCubeType.WOOD,
				-priceTable[playerChoice][ResourceCubeType.WOOD.getValue()]);
		curPlayer.incrementResource(ResourceCubeType.GOLD,
				-priceTable[playerChoice][ResourceCubeType.GOLD.getValue()]);
		curPlayer.incrementResource(ResourceCubeType.FOOD,
				-priceTable[playerChoice][ResourceCubeType.FOOD.getValue()]);
		curPlayer.incrementResource(ResourceCubeType.FAVOR,
				-priceTable[playerChoice][ResourceCubeType.FAVOR.getValue()]);
	}

	public boolean enoughFunds(final Player curPlayer, final int playerChoice) {
		int[] curResources;
		curResources = curPlayer.getResourceCounter();

		if (curResources[ResourceCubeType.WOOD.getValue()] < priceTable[playerChoice][ResourceCubeType.WOOD
				.getValue()]) {
			return false;
		} else if (curResources[ResourceCubeType.GOLD.getValue()] < priceTable[playerChoice][ResourceCubeType.GOLD
				.getValue()]) {
			return false;
		} else if (curResources[ResourceCubeType.FOOD.getValue()] < priceTable[playerChoice][ResourceCubeType.FOOD
				.getValue()]) {
			return false;
		} else if (curResources[ResourceCubeType.FAVOR.getValue()] < priceTable[playerChoice][ResourceCubeType.FAVOR
				.getValue()]) {
			return false;
		} else {
			return true;
		}
	}
}
