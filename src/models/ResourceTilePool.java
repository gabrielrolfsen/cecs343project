package models;

import java.util.Random;

import utils.Constants;
import utils.Types.ResourceTileType;
import utils.Types.ResourceType;

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
			tilePool[curTile] = new ResourceTile(ResourceTileType.FERTILE,
					ResourceType.TWO_FOODS);
			curTile++;
		}

		for (i = 0; i < 3; i++) {
			tilePool[curTile] = new ResourceTile(ResourceTileType.FERTILE,
					ResourceType.ONE_WOOD);
			curTile++;
		}

		for (i = 0; i < 3; i++) {
			tilePool[curTile] = new ResourceTile(ResourceTileType.FERTILE,
					ResourceType.ONE_FAVOR);
			curTile++;
		}

		for (i = 0; i < 3; i++) {
			tilePool[curTile] = new ResourceTile(ResourceTileType.FERTILE,
					ResourceType.ONE_GOLD);
			curTile++;
		}

		for (i = 0; i < 9; i++) {
			tilePool[curTile] = new ResourceTile(ResourceTileType.FOREST,
					ResourceType.TWO_WOODS);
			curTile++;
		}

		for (i = 0; i < 2; i++) {
			tilePool[curTile] = new ResourceTile(ResourceTileType.FOREST,
					ResourceType.ONE_FOOD);
			curTile++;
		}

		for (i = 0; i < 2; i++) {
			tilePool[curTile] = new ResourceTile(ResourceTileType.FOREST,
					ResourceType.ONE_GOLD);
			curTile++;
		}

		for (i = 0; i < 2; i++) {
			tilePool[curTile] = new ResourceTile(ResourceTileType.FOREST,
					ResourceType.ONE_FAVOR);
			curTile++;
		}

		for (i = 0; i < 4; i++) {
			tilePool[curTile] = new ResourceTile(ResourceTileType.HILLS,
					ResourceType.TWO_GOLDS);
			curTile++;
		}

		for (i = 0; i < 4; i++) {
			tilePool[curTile] = new ResourceTile(ResourceTileType.HILLS,
					ResourceType.ONE_WOOD);
			curTile++;
		}
		for (i = 0; i < 4; i++) {
			tilePool[curTile] = new ResourceTile(ResourceTileType.HILLS,
					ResourceType.ONE_FOOD);
			curTile++;
		}
		for (i = 0; i < 4; i++) {
			tilePool[curTile] = new ResourceTile(ResourceTileType.HILLS,
					ResourceType.ONE_FAVOR);
			curTile++;
		}

		for (i = 0; i < 6; i++) {
			tilePool[curTile] = new ResourceTile(ResourceTileType.MOUNTAINS,
					ResourceType.TWO_GOLDS);
			curTile++;
		}

		for (i = 0; i < 3; i++) {
			tilePool[curTile] = new ResourceTile(ResourceTileType.MOUNTAINS,
					ResourceType.ONE_WOOD);
			curTile++;
		}

		for (i = 0; i < 3; i++) {
			tilePool[curTile] = new ResourceTile(ResourceTileType.MOUNTAINS,
					ResourceType.ONE_FAVOR);
			curTile++;
		}

		for (i = 0; i < 7; i++) {
			tilePool[curTile] = new ResourceTile(ResourceTileType.DESERT,
					ResourceType.TWO_FAVORS);
			curTile++;
		}

		for (i = 0; i < 7; i++) {
			tilePool[curTile] = new ResourceTile(ResourceTileType.DESERT,
					ResourceType.ONE_GOLD);
			curTile++;
		}

		for (i = 0; i < 4; i++) {
			tilePool[curTile] = new ResourceTile(ResourceTileType.SWAMP,
					ResourceType.ONE_FAVOR);
			curTile++;
		}

		for (i = 0; i < 4; i++) {
			tilePool[curTile] = new ResourceTile(ResourceTileType.SWAMP,
					ResourceType.ONE_FOOD);
			curTile++;
		}

		for (i = 0; i < 4; i++) {
			tilePool[curTile] = new ResourceTile(ResourceTileType.SWAMP,
					ResourceType.ONE_WOOD);
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
