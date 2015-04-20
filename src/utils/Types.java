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
		private final int value;

		ResourceTileType(final int n) {
			this.value = n;
		}

		public int getValue() {
			return this.value;
		}
	}

	public enum BuildingTileType {
		HOUSE(0, "House"), WALL(1, "Wall"), TOWER(2, "Tower"), STOREHOUSE(3,
				"Storehouse"), MARKET(4, "Market"), ARMORY(5, "Armory"), QUARRY(
				6, "Quarry"), MONUMENT(7, "Monument"), GRANARY(8, "Granary"), MINT(
				9, "Mint"), WORKSHOP_WOOD(10, "Wood Workshop"), WORKSHOP_SIEGE(
				11, "Siege Workshop"), TEMPLE(12, "Temple"), WONDER(13,
				"Wonder");

		private final int n;
		private final String s;

		BuildingTileType(final int n, final String s) {
			this.n = n;
			this.s = s;
		}

		public int getValue() {
			return this.n;
		}

		public String getString() {
			return this.s;
		}

		public static String getStringForInt(final int n) {
			for (final BuildingTileType a : BuildingTileType.values()) {
				if (a.n == n) {
					return a.s;
				}
			}
			throw new IllegalArgumentException("Invalid id");
		}

		public static BuildingTileType getTypeForInt(final int n) {
			for (final BuildingTileType a : BuildingTileType.values()) {
				if (a.n == n) {
					return a;
				}
			}
			throw new IllegalArgumentException("Invalid id");
		}
	}

	public enum ResourceType {
		ONE_FAVOR(0), ONE_FOOD(1), ONE_GOLD(2), ONE_WOOD(3), TWO_FAVORS(4), TWO_FOODS(
				5), TWO_GOLDS(6), TWO_WOODS(7);
		private final int value;

		ResourceType(final int n) {
			this.value = n;
		}

		public int getValue() {
			return this.value;
		}
	}

	public enum ResourceCubeType {
		FAVOR(0, "Favor"), FOOD(1, "Food"), GOLD(2, "Gold"), WOOD(3, "Wood"), VICTORY(
				4, "Victory");

		private final int n;
		private final String name;

		ResourceCubeType(final int n, final String name) {
			this.name = name;
			this.n = n;
		}

		public String getName() {
			return this.name;
		}

		public int getValue() {
			return this.n;
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

	public enum UnitType {
		ANUBITE(BoardType.EGYPTIAN, "Anubite", new int[] { 1, 0, 3, 0 }), PRIEST(
				BoardType.EGYPTIAN, "Priest", new int[] { 0, 2, 4, 0 }), CHARIOT_ARCHER(
				BoardType.EGYPTIAN, "Chariot Archer", new int[] { 0, 0, 1, 1 }), ELEPHANT(
				BoardType.EGYPTIAN, "Elephant", new int[] { 0, 2, 1, 0 }), SPHINX(
				BoardType.EGYPTIAN, "Sphinx", new int[] { 2, 0, 2, 0 }), SCORPION_MAN(
				BoardType.EGYPTIAN, "Scorpion-Man", new int[] { 0, 4, 2, 0 }), SON_OF_OASIS(
				BoardType.EGYPTIAN, "Son of Oasis", new int[] { 4, 0, 4, 0 }), PHARAOH(
				BoardType.EGYPTIAN, "Pharaoh", new int[] { 0, 3, 3, 0 }), WADJET(
				BoardType.EGYPTIAN, "Wadjet", new int[] { 2, 2, 0, 0 }), PHOENIX(
				BoardType.EGYPTIAN, "Phoenix", new int[] { 3, 0, 0, 2 }), SPEARMAN(
				BoardType.EGYPTIAN, "Spearman", new int[] { 0, 1, 0, 1 }), MUMMY(
				BoardType.EGYPTIAN, "Mummy", new int[] { 2, 0, 3, 0 }), TEST(
				BoardType.NORSE, "Test", new int[] { 0, 3, 4, 5 });

		private final BoardType type;
		private final String name;
		private final int[] cost;

		UnitType(final BoardType b, final String name, final int[] cost) {
			this.cost = cost;
			this.type = b;
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public int[] getCost() {
			return this.cost;
		}

		public BoardType getCulture() {
			return this.type;
		}

	}

	public enum VictoryCardType {
		THE_LARGEST_ARMY(0, "The Largest Army"), THE_MOST_BUILDINGS(1,
				"The Most Buildings"), THE_WONDER(2, "The Wonder"), WON_THE_LAST_BATTLE(
				3, "Won the Last Battle");
		private final String name;
		private final int value;

		VictoryCardType(final int n, final String s) {
			this.value = n;
			this.name = s;
		}

		public int getValue() {
			return this.value;
		}

		public String getName() {
			return this.name;
		}

	}
}
