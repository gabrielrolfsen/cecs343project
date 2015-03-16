package models;

import java.util.ArrayList;
import java.util.Random;

import utils.Types.ResourceTileType;
import utils.Types.ResourceType;

public class ResourceTilePool {
	private final ArrayList<ResourceTile> tilePool = new ArrayList<ResourceTile>();

	public ResourceTilePool() {
		InitPool();
	}

	private void InitPool() {
		int i = 0; // Counter

		for (i = 0; i < 12; i++) {
			tilePool.add(new ResourceTile(ResourceTileType.FERTILE,
					ResourceType.TWO_FOODS));

		}

		for (i = 0; i < 3; i++) {
			tilePool.add(new ResourceTile(ResourceTileType.FERTILE,
					ResourceType.ONE_WOOD));

		}

		for (i = 0; i < 3; i++) {
			tilePool.add(new ResourceTile(ResourceTileType.FERTILE,
					ResourceType.ONE_FAVOR));

		}

		for (i = 0; i < 3; i++) {
			tilePool.add(new ResourceTile(ResourceTileType.FERTILE,
					ResourceType.ONE_GOLD));

		}

		for (i = 0; i < 9; i++) {
			tilePool.add(new ResourceTile(ResourceTileType.FOREST,
					ResourceType.TWO_WOODS));

		}

		for (i = 0; i < 2; i++) {
			tilePool.add(new ResourceTile(ResourceTileType.FOREST,
					ResourceType.ONE_FOOD));

		}

		for (i = 0; i < 2; i++) {
			tilePool.add(new ResourceTile(ResourceTileType.FOREST,
					ResourceType.ONE_GOLD));

		}

		for (i = 0; i < 2; i++) {
			tilePool.add(new ResourceTile(ResourceTileType.FOREST,
					ResourceType.ONE_FAVOR));

		}

		for (i = 0; i < 4; i++) {
			tilePool.add(new ResourceTile(ResourceTileType.HILLS,
					ResourceType.TWO_GOLDS));

		}

		for (i = 0; i < 4; i++) {
			tilePool.add(new ResourceTile(ResourceTileType.HILLS,
					ResourceType.ONE_WOOD));

		}
		for (i = 0; i < 4; i++) {
			tilePool.add(new ResourceTile(ResourceTileType.HILLS,
					ResourceType.ONE_FOOD));

		}
		for (i = 0; i < 4; i++) {
			tilePool.add(new ResourceTile(ResourceTileType.HILLS,
					ResourceType.ONE_FAVOR));

		}

		for (i = 0; i < 6; i++) {
			tilePool.add(new ResourceTile(ResourceTileType.MOUNTAINS,
					ResourceType.TWO_GOLDS));

		}

		for (i = 0; i < 3; i++) {
			tilePool.add(new ResourceTile(ResourceTileType.MOUNTAINS,
					ResourceType.ONE_WOOD));

		}

		for (i = 0; i < 3; i++) {
			tilePool.add(new ResourceTile(ResourceTileType.MOUNTAINS,
					ResourceType.ONE_FAVOR));

		}

		for (i = 0; i < 7; i++) {
			tilePool.add(new ResourceTile(ResourceTileType.DESERT,
					ResourceType.TWO_FAVORS));

		}

		for (i = 0; i < 7; i++) {
			tilePool.add(new ResourceTile(ResourceTileType.DESERT,
					ResourceType.ONE_GOLD));

		}

		for (i = 0; i < 4; i++) {
			tilePool.add(new ResourceTile(ResourceTileType.SWAMP,
					ResourceType.ONE_FAVOR));

		}

		for (i = 0; i < 4; i++) {
			tilePool.add(new ResourceTile(ResourceTileType.SWAMP,
					ResourceType.ONE_FOOD));

		}

		for (i = 0; i < 4; i++) {
			tilePool.add(new ResourceTile(ResourceTileType.SWAMP,
					ResourceType.ONE_WOOD));

		}

	}

	/**
	 * Method that takes a given quantity of Resource Tiles wanted, return
	 * random Resource Tiles in an arrayList.
	 * 
	 * @param qty
	 *            int, quantity of ResourceTiles needed. Beware that this number
	 *            must be no higher than the current pool size
	 * @return ArrayList, return 'qty' random Resource Tiles in ArrayList
	 */
	public ArrayList<ResourceTile> getRandomTiles(final int qty) {
		int i, j;
		final ArrayList<ResourceTile> randomTiles = new ArrayList<ResourceTile>();
		final Random randomGenerator = new Random();

		for (i = 0; i < qty; i++) {
			j = randomGenerator.nextInt(tilePool.size());
			// Put the tile selected on the return array
			randomTiles.add(tilePool.get(j));
			// Remove from the pool
			tilePool.remove(j);
		}
		return randomTiles;
	}

	/**
	 * Method to add back tiles to the pool.
	 * 
	 * @param returningTiles
	 *            ArrayList with the ResourceTiles to be added back to the pool.
	 */
	public void returnTilesToPool(final ArrayList<ResourceTile> returningTiles) {
		for (final ResourceTile t : returningTiles) {
			tilePool.add(t);
		}
	}

}
