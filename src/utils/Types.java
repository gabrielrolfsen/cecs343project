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

	public enum ResourceType {
		ONE_FOOD(0), ONE_FAVOR(1), ONE_GOLD(2), ONE_WOOD(3), TWO_FOODS(4), TWO_FAVORS(
				5), TWO_GOLDS(6), TWO_WOODS(7);
		private final int n;

		ResourceType(final int n) {
			this.n = n;
		}
	}

	public enum ResourceCubeType {
		FOOD(0), FAVOR(1), WOOD(2), GOLD(3), VICTORY(4);

		private final int myValue;

		ResourceCubeType(final int v) {
			myValue = v;
		}

		public int getValue() {
			return myValue;
		}
	}

	public enum GridType {
		RESOURCE(0), BUILDING(1);
		private final int n;

		GridType(final int n) {
			this.n = n;
		}
	}

}
