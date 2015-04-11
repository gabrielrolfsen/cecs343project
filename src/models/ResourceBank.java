/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Apr 10, 2015
 */
package models;

import utils.Types.ResourceCubeType;

/**
 * @author grolfsen
 *
 */
public class ResourceBank {

	private static ResourceBank mInstance = null;

	private final int mResources[] = new int[5];

	private ResourceBank(final int numPlayers) {
		/*
		 * Fill the bank based on the number of players. This takes in account
		 * the resources giving to each player (4) at the beginning of the game.
		 */
		for (int i = 0; i < mResources.length - 1; i++) {
			mResources[i] = (12 + (numPlayers - 2));
		}

		/*
		 * 30 Victory points are placed in the bank regardless of how many /*
		 * players are playing.
		 */
		mResources[ResourceCubeType.VICTORY.getValue()] = 30;
	}

	public void updateResources(final int[] updatedResources) {
		for (int i = 0; i < updatedResources.length; i++) {
			mResources[i] -= updatedResources[i];
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

	public static synchronized ResourceBank getInstance() {
		if (mInstance == null) {
			mInstance = new ResourceBank(3);
		}
		return mInstance;
	}

}
