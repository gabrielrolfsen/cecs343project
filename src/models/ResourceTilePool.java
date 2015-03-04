package models;

import java.util.Random;

import utils.Constants;

public class ResourceTilePool {
	private final ResourceTile[] tilePool;
	public ResourceTile[] selectedTiles;

	public ResourceTilePool() {
		tilePool = new ResourceTile[Constants.MAX_TILES];
		selectedTiles = new ResourceTile[Constants.MAX_TILES_SELECTED];

		InitPool();
		SelectRandomTiles();
	}

	public ResourceTile[] getSelectedTiles() {
		return this.selectedTiles;
	}

	public ResourceTile getSelectedTile(final int index) {
		return this.selectedTiles[index];
	}

	private void RefillPool() {
		int i;
		for (i = 0; i < Constants.MAX_TILES; i++) {
			tilePool[i].setSelected(false);
		}
	}

	private void InitPool() {
		int i;
		int curTile = 0;

		for (i = 0; i < 12; i++) {
			tilePool[curTile] = new ResourceTile(Constants.TYPE_FERTILE,
					Constants.TWO_FOODS);
			curTile++;
		}

		for (i = 0; i < 3; i++) {
			tilePool[curTile] = new ResourceTile(Constants.TYPE_FERTILE,
					Constants.ONE_WOOD);
			curTile++;
		}

		for (i = 0; i < 3; i++) {
			tilePool[curTile] = new ResourceTile(Constants.TYPE_FERTILE,
					Constants.ONE_FAVOR);
			curTile++;
		}

		for (i = 0; i < 3; i++) {
			tilePool[curTile] = new ResourceTile(Constants.TYPE_FERTILE,
					Constants.ONE_GOLD);
			curTile++;
		}

		for (i = 0; i < 9; i++) {
			tilePool[curTile] = new ResourceTile(Constants.TYPE_FOREST,
					Constants.TWO_WOODS);
			curTile++;
		}

		for (i = 0; i < 2; i++) {
			tilePool[curTile] = new ResourceTile(Constants.TYPE_FOREST,
					Constants.ONE_FOOD);
			curTile++;
		}

		for (i = 0; i < 2; i++) {
			tilePool[curTile] = new ResourceTile(Constants.TYPE_FOREST,
					Constants.ONE_GOLD);
			curTile++;
		}

		for (i = 0; i < 2; i++) {
			tilePool[curTile] = new ResourceTile(Constants.TYPE_FOREST,
					Constants.ONE_FAVOR);
			curTile++;
		}

		for (i = 0; i < 4; i++) {
			tilePool[curTile] = new ResourceTile(Constants.TYPE_HILLS,
					Constants.TWO_GOLDS);
			curTile++;
		}

		for (i = 0; i < 4; i++) {
			tilePool[curTile] = new ResourceTile(Constants.TYPE_HILLS,
					Constants.ONE_WOOD);
			curTile++;
		}
		for (i = 0; i < 4; i++) {
			tilePool[curTile] = new ResourceTile(Constants.TYPE_HILLS,
					Constants.ONE_FOOD);
			curTile++;
		}
		for (i = 0; i < 4; i++) {
			tilePool[curTile] = new ResourceTile(Constants.TYPE_HILLS,
					Constants.ONE_FAVOR);
			curTile++;
		}

		for (i = 0; i < 6; i++) {
			tilePool[curTile] = new ResourceTile(Constants.TYPE_MOUNTAINS,
					Constants.TWO_GOLDS);
			curTile++;
		}

		for (i = 0; i < 3; i++) {
			tilePool[curTile] = new ResourceTile(Constants.TYPE_MOUNTAINS,
					Constants.ONE_WOOD);
			curTile++;
		}

		for (i = 0; i < 3; i++) {
			tilePool[curTile] = new ResourceTile(Constants.TYPE_MOUNTAINS,
					Constants.ONE_FAVOR);
			curTile++;
		}

		for (i = 0; i < 7; i++) {
			tilePool[curTile] = new ResourceTile(Constants.TYPE_DESERT,
					Constants.TWO_FAVORS);
			curTile++;
		}

		for (i = 0; i < 7; i++) {
			tilePool[curTile] = new ResourceTile(Constants.TYPE_DESERT,
					Constants.ONE_GOLD);
			curTile++;
		}

		for (i = 0; i < 4; i++) {
			tilePool[curTile] = new ResourceTile(Constants.TYPE_SWAMP,
					Constants.ONE_FAVOR);
			curTile++;
		}

		for (i = 0; i < 4; i++) {
			tilePool[curTile] = new ResourceTile(Constants.TYPE_SWAMP,
					Constants.ONE_FOOD);
			curTile++;
		}

		for (i = 0; i < 4; i++) {
			tilePool[curTile] = new ResourceTile(Constants.TYPE_SWAMP,
					Constants.ONE_WOOD);
			curTile++;
		}

		RefillPool();
	}

	private void SelectRandomTiles() {
		RefillPool();

		int i, j;
		final Random randomGenerator = new Random();

		for (i = 0; i < Constants.MAX_TILES_SELECTED; i++) {
			do {
				j = randomGenerator.nextInt(Constants.MAX_TILES);
				selectedTiles[i] = tilePool[j];
			} while (selectedTiles[i].isSelected() == true);

			tilePool[j].setSelected(true);
		}
	}
}
