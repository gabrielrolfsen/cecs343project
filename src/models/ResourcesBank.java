/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 10, 2015
 */
package models;

import java.util.ArrayList;
import java.util.Random;

import utils.Types.ResourceCubeType;
import utils.Types.ResourceTileType;
import utils.Types.ResourceType;
import utils.Types.UnitType;

/**
 * @author grolfsen
 *
 */
public class ResourcesBank {

	private static ResourcesBank mInstance = null;

	private final int mResources[] = new int[5];

	private final ArrayList<ResourceTile> mTilePool = new ArrayList<ResourceTile>();

	private final ArrayList<BattleCard> mBattleCardsDeck = new ArrayList<BattleCard>();

	// Counter of how many Victor Cubes each Victory Card has
	private int mVictoryCubesOnCards[] = { 0, 0, 0, 0 };

	private ResourcesBank(final int numPlayers) {
		initPool();

		/*
		 * Fill the bank based on the number of players. This takes in account
		 * the resources giving to each player (4) at the beginning of the game.
		 */
		for (int i = 0; i < mResources.length - 1; i++) {
			mResources[i] = (12 + (numPlayers - 2));
		}

		/*
		 * 30 Victory points are placed in the bank regardless of how many
		 * players are playing. But each player gets 1, so discount numPlayers
		 */
		mResources[ResourceCubeType.VICTORY.getValue()] = 30 - numPlayers;

		for (final UnitType type : UnitType.values()) {
			mBattleCardsDeck.add(new BattleCard(type));
		}
	}

	public int[] getVictoryCubesOnCards() {
		return this.mVictoryCubesOnCards;
	}

	public void setVictoryCubesOnCards(final int[] victoryCubesOnCards) {
		this.mVictoryCubesOnCards = victoryCubesOnCards;
	}

	public ArrayList<BattleCard> getBattleCardsDeck() {
		return this.mBattleCardsDeck;
	}

	public void decrementResources(final int[] updatedResources) {
		for (int i = 0; i < updatedResources.length; i++) {
			mResources[i] -= updatedResources[i];
		}
	}

	public void incrementResources(final int[] updatedResources) {
		for (int i = 0; i < updatedResources.length; i++) {
			mResources[i] += updatedResources[i];
		}
	}

	public void gatherResource(final ResourceCubeType type, final int qty) {
		mResources[type.getValue()] -= qty;
	}

	public void replenishResource(final ResourceCubeType type, final int qty) {
		mResources[type.getValue()] += qty;
	}

	public int[] getResourceCounter() {
		return this.mResources;
	}

	public int getRequestedResource(ResourceCubeType type, int quantityRequested) {
		int returnValue;
		if (mResources[type.getValue()] < quantityRequested) {
			returnValue = mResources[type.getValue()];
			mResources[type.getValue()] = 0;
		} else {
			returnValue = quantityRequested;
			mResources[type.getValue()] -= quantityRequested;
		}
		return returnValue;
	}
	
	public static synchronized ResourcesBank getInstance() {
		if (mInstance == null) {
			mInstance = new ResourcesBank(3);
		}
		return mInstance;
	}

	private void initPool() {
		/*
		 * Each row represent a Resource Tile Type (Desert, Fertile, etc.) and
		 * each column a Resource Type (Favor, Food, etc.) the number in each
		 * position represents how many tiles are to be added in the bank.
		 */
		final int qty[][] = { { 0, 0, 7, 0, 7, 0, 0, 0 },
				{ 3, 0, 3, 3, 0, 12, 0, 0 }, { 2, 2, 2, 0, 0, 0, 0, 9 },
				{ 4, 4, 0, 4, 0, 0, 4, 0 }, { 3, 0, 0, 3, 0, 0, 6, 0 },
				{ 4, 4, 0, 4, 0, 0, 0, 0 } };

		for (final ResourceTileType tileType : ResourceTileType.values()) {
			for (final ResourceType resType : ResourceType.values()) {
				for (int i = 0; i < qty[tileType.getValue()][resType.getValue()]; i++) {
					mTilePool.add(new ResourceTile(tileType, resType));
				}
			}
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
			j = randomGenerator.nextInt(mTilePool.size());
			// Put the tile selected on the return array
			randomTiles.add(mTilePool.get(j));
			// Remove from the pool
			mTilePool.remove(j);
		}
		return randomTiles;
	}

	/**
	 * Method to add back tiles to the Resource Tile Pool.
	 * 
	 * @param returningTiles
	 *            ArrayList with the ResourceTiles to be added back to the pool.
	 */
	public void returnTilesToPool(final ArrayList<ResourceTile> returningTiles) {
		for (final ResourceTile t : returningTiles) {
			mTilePool.add(t);
		}
	}

}
