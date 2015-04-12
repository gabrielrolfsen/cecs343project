/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Mar 3, 2015
 */
package utils;

/**
 * @author grolfsen
 *
 */
public class Types {

	public enum BoardType {
		NORSE(0), EGYPTIAN(1), GREEK(2);
		private final int n;

		BoardType(final int n) {
			this.n = n;
		}

		public int getValue() {
			return this.n;
		}

	}

	public enum ResourceTileType {
		DESERT(0), FERTILE(1), FOREST(2), HILLS(3), MOUNTAINS(4), SWAMP(5);
		private final int n;

		ResourceTileType(final int n) {
			this.n = n;
		}

		public int getValue() {
			return this.n;
		}
	}

	public enum BuildingTileType {
		HOUSE, WALL, TOWER, STOREHOUSE, MARKET, ARMORY, QUARRY, MONUMENT, GRANARY, MINT, WORKSHOP_WOOD, WORKSHOP_SIEGE, TEMPLE, WONDER;

	}

	public enum ResourceType {
		ONE_FOOD(0), ONE_FAVOR(1), ONE_GOLD(2), ONE_WOOD(3), TWO_FOODS(4), TWO_FAVORS(
				5), TWO_GOLDS(6), TWO_WOODS(7);
		private final int n;

		ResourceType(final int n) {
			this.n = n;
		}
	}

	public enum ResourceCubeType {
		FAVOR(0), FOOD(1), GOLD(2), WOOD(3), VICTORY(4);

		private final int n;

		ResourceCubeType(final int n) {
			this.n = n;
		}

		public int getValue() {
			return n;
		}
	}

	public enum GridType {
		RESOURCE(0), BUILDING(1);
		private final int n;

		GridType(final int n) {
			this.n = n;
		}

		public int getValue() {
			return this.n;
		}
	}

	// TODO: Possibly turn TRADE into TRADE_1 and TRADE_2
	public enum CardType {
		ATTACK(0), BUILD(1), EXPLORE(2), GATHER(3), NEXTAGE(4), RECRUIT(5), TRADE(
				6);
		private final int n;

		CardType(final int n) {
			this.n = n;
		}

		public int getValue() {
			return this.n;
		}
	}

}
