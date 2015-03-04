package models;

import java.util.Random;

import utils.Constants;

public class ResourceTilePool {
	private final ResourceTile[] AllTiles;
	public ResourceTile[] SelectedTiles;

	private static final int MAX_TILES = 90;
	private static final int MAX_TILES_SELECTED = 18;

	public ResourceTilePool() {
		AllTiles = new ResourceTile[MAX_TILES];
		SelectedTiles = new ResourceTile[MAX_TILES_SELECTED];

		InitPool();
		SelectRandomTiles();
	}

	private void RefillPool() {
		int i;
		for (i = 0; i < MAX_TILES; i++) {
			AllTiles[i].setSelected(false);
		}
	}

	private void InitPool() {
		int i;
		int curTile = 0;

		for (i = 0; i < 12; i++) {
			AllTiles[curTile] = new ResourceTile(Constants.TYPE_FERTILE,
					Constants.TWO_FOODS);
			curTile++;
		}

		for (i = 0; i < 3; i++) {
			AllTiles[curTile] = new ResourceTile(Constants.TYPE_FERTILE,
					Constants.ONE_WOOD);
			curTile++;
		}

		for (i = 0; i < 3; i++) {
			AllTiles[curTile] = new ResourceTile(Constants.TYPE_FERTILE,
					Constants.ONE_FAVOR);
			curTile++;
		}

		for (i = 0; i < 3; i++) {
			AllTiles[curTile] = new ResourceTile(Constants.TYPE_FERTILE,
					Constants.ONE_GOLD);
			curTile++;
		}

		for (i = 0; i < 9; i++) {
			AllTiles[curTile] = new ResourceTile(Constants.TYPE_FOREST,
					Constants.TWO_WOODS);
			curTile++;
		}

		for (i = 0; i < 2; i++) {
			AllTiles[curTile] = new ResourceTile(Constants.TYPE_FOREST,
					Constants.ONE_FOOD);
			curTile++;
		}

		for (i = 0; i < 2; i++) {
			AllTiles[curTile] = new ResourceTile(Constants.TYPE_FOREST,
					Constants.ONE_GOLD);
			curTile++;
		}

		for (i = 0; i < 2; i++) {
			AllTiles[curTile] = new ResourceTile(Constants.TYPE_FOREST,
					Constants.ONE_FAVOR);
			curTile++;
		}

		for (i = 0; i < 4; i++) {
			AllTiles[curTile] = new ResourceTile(Constants.TYPE_HILLS,
					Constants.TWO_GOLDS);
			curTile++;
		}

		for (i = 0; i < 4; i++) {
			AllTiles[curTile] = new ResourceTile(Constants.TYPE_HILLS,
					Constants.ONE_WOOD);
			curTile++;
		}
		for (i = 0; i < 4; i++) {
			AllTiles[curTile] = new ResourceTile(Constants.TYPE_HILLS,
					Constants.ONE_FOOD);
			curTile++;
		}
		for (i = 0; i < 4; i++) {
			AllTiles[curTile] = new ResourceTile(Constants.TYPE_HILLS,
					Constants.ONE_FAVOR);
			curTile++;
		}

		for (i = 0; i < 6; i++) {
			AllTiles[curTile] = new ResourceTile(Constants.TYPE_MOUNTAINS,
					Constants.TWO_GOLDS);
			curTile++;
		}

		for (i = 0; i < 3; i++) {
			AllTiles[curTile] = new ResourceTile(Constants.TYPE_MOUNTAINS,
					Constants.ONE_WOOD);
			curTile++;
		}

		for (i = 0; i < 3; i++) {
			AllTiles[curTile] = new ResourceTile(Constants.TYPE_MOUNTAINS,
					Constants.ONE_FAVOR);
			curTile++;
		}

		for (i = 0; i < 7; i++) {
			AllTiles[curTile] = new ResourceTile(Constants.TYPE_DESERT,
					Constants.TWO_FAVORS);
			curTile++;
		}

		for (i = 0; i < 7; i++) {
			AllTiles[curTile] = new ResourceTile(Constants.TYPE_DESERT,
					Constants.ONE_GOLD);
			curTile++;
		}

		for (i = 0; i < 4; i++) {
			AllTiles[curTile] = new ResourceTile(Constants.TYPE_SWAMP,
					Constants.ONE_FAVOR);
			curTile++;
		}

		for (i = 0; i < 4; i++) {
			AllTiles[curTile] = new ResourceTile(Constants.TYPE_SWAMP,
					Constants.ONE_FOOD);
			curTile++;
		}

		for (i = 0; i < 4; i++) {
			AllTiles[curTile] = new ResourceTile(Constants.TYPE_SWAMP,
					Constants.ONE_WOOD);
			curTile++;
		}

		RefillPool();
	}

	private void SelectRandomTiles() {
		RefillPool();

		int i, j;
		final Random randomGenerator = new Random();

		for (i = 0; i < MAX_TILES_SELECTED; i++) {
			do {
				j = randomGenerator.nextInt(MAX_TILES);
				SelectedTiles[i] = AllTiles[j];
			} while (SelectedTiles[i].isSelected() == true);

			AllTiles[j].setSelected(true);
		}
	}
}
