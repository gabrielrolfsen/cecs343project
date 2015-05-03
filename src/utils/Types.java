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
		NORSE(0),
		EGYPTIAN(1),
		GREEK(2);
		private final int n;

		BoardType(final int n) {
			this.n = n;
		}

		public int getValue() {
			return this.n;
		}

	}

	public enum ResourceTileType {
		DESERT(0, "Desert"),
		FERTILE(1, "Fertile"),
		FOREST(2, "Forest"),
		HILLS(3, "Hills"),
		MOUNTAINS(4, "Mountains"),
		SWAMP(5, "Swamp");

		private final int value;
		private final String s;

		ResourceTileType(final int n, final String s) {
			this.value = n;
			this.s = s;
		}

		public int getValue() {
			return this.value;
		}

		public String getString() {
			return this.s;
		}

		public static ResourceTileType getTypeForString(final String s) {
			for (final ResourceTileType r : ResourceTileType.values()) {
				if (r.getString() == s) {
					return r;
				}
			}
			throw new IllegalArgumentException("Invalid id");
		}
	}

	public enum BuildingTileType {
		HOUSE(0, "House"),
		WALL(1, "Wall"),
		TOWER(2, "Tower"),
		STOREHOUSE(3, "Storehouse"),
		MARKET(4, "Market"),
		ARMORY(5, "Armory"),
		QUARRY(6, "Quarry"),
		MONUMENT(7, "Monument"),
		GRANARY(8, "Granary"),
		MINT(9, "Mint"),
		WORKSHOP_WOOD(10, "Wood Workshop"),
		WORKSHOP_SIEGE(11, "Siege Workshop"),
		TEMPLE(12, "Temple"),
		WONDER(13, "Wonder");

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
		ONE_FAVOR(0),
		ONE_FOOD(1),
		ONE_GOLD(2),
		ONE_WOOD(3),
		TWO_FAVORS(4),
		TWO_FOODS(5),
		TWO_GOLDS(6),
		TWO_WOODS(7);
		private final int value;

		ResourceType(final int n) {
			this.value = n;
		}

		public int getValue() {
			return this.value;
		}
	}

	public enum ResourceCubeType {
		FAVOR(0, "Favor"),
		FOOD(1, "Food"),
		GOLD(2, "Gold"),
		WOOD(3, "Wood"),
		VICTORY(4, "Victory");

		private final int n;
		private final String name;

		ResourceCubeType(final int n, final String name) {
			this.name = name;
			this.n = n;
		}

		public String getName() {
			return this.name;
		}

		public static ResourceCubeType getType(final int n) {
			for (final ResourceCubeType cube : ResourceCubeType.values()) {
				if (cube.n == n) {
					return cube;
				}
			}
			return null;
		}

		public int getValue() {
			return this.n;
		}

		public static ResourceCubeType getTypeForString(final String s) {
			for (final ResourceCubeType r : ResourceCubeType.values()) {
				if (r.getName() == s) {
					return r;
				}
			}
			throw new IllegalArgumentException("Invalid id");
		}
	}

	public enum GridType {
		RESOURCE(0),
		BUILDING(1);
		private final int n;

		GridType(final int n) {
			this.n = n;
		}

		public int getValue() {
			return this.n;
		}
	}

	public enum CardType {
		PERMANENT(),
		ATTACK(PERMANENT, 0, 0),
		BUILD(PERMANENT, 1, 0),
		EXPLORE(PERMANENT, 2, 0),
		GATHER(PERMANENT, 3, 0),
		NEXTAGE(PERMANENT, 4, 0),
		RECRUIT(PERMANENT, 5, 0),
		TRADE(PERMANENT, 6, 2),
		RANDOM(),
		BUILD_EGYPTIAN(RANDOM, BoardType.EGYPTIAN, 3, "Nephthys", 2),
		EXPLORE_EGYPTIAN(RANDOM, BoardType.EGYPTIAN, 0, "Ptah", 1),
		GATHER_EGYPTIAN(RANDOM, BoardType.EGYPTIAN, 0, "Ra", 3),
		NEXT_AGE_EGYPTIAN(RANDOM, BoardType.EGYPTIAN, 0, "Hathor", 1),
		RECRUIT_EGYPTIAN(RANDOM, BoardType.EGYPTIAN, 6, "Bast", 1),
		TRADE_EGYPTIAN(RANDOM, BoardType.EGYPTIAN, 0, "N/A", 1),
		BUILD_GREEK(RANDOM, BoardType.GREEK, 3, "Hera", 1),
		EXPLORE_GREEK(RANDOM, BoardType.GREEK, 0, "Arthmis", 1),
		GATHER_GREEK(RANDOM, BoardType.GREEK, 0, "Poseidon", 1),
		NEXT_AGE_GREEK(RANDOM, BoardType.GREEK, 0, "Hephaestus", 2),
		RECRUIT_GREEK(RANDOM, BoardType.GREEK, 4, "Apollo", 1),
		TRADE_GREEK(RANDOM, BoardType.GREEK, 0, "Hermes", 1),
		BUILD_NORSE(RANDOM, BoardType.NORSE, 4, "Njord", 1),
		EXPLORE_NORSE(RANDOM, BoardType.NORSE, 0, "Baldr", 1),
		GATHER_NORSE(RANDOM, BoardType.NORSE, 0, "Freyja", 1),
		NEXT_AGE_NORSE(RANDOM, BoardType.NORSE, 0, "Odin", 1),
		RECRUIT_NORSE(RANDOM, BoardType.NORSE, 3, "Hel", 1),
		TRADE_NORSE(RANDOM, BoardType.NORSE, 0, "Forseti", 1);

		private int id = 0;
		private CardType type = null;
		private BoardType culture = null;
		private String godName = "";
		private int num = 0;
		private int price = 0;

		CardType() {
		}

		CardType(final CardType type, final int id, final int num) {
			this.id = id;
			this.type = type;
			this.num = num;
		}

		CardType(final CardType type, final BoardType culture, final int num, final String godName,
				final int price) {
			this.id = 0;
			this.type = type;
			this.culture = culture;
			this.godName = godName;
			this.price = price;
			this.num = num;
		}

		public static CardType getType(final int id) {
			for (final CardType card : CardType.values()) {
				if (card.id == id) {
					return card;
				}
			}
			return null;
		}

		public int getId() {
			return this.id;
		}

		public CardType getType() {
			return this.type;
		}

		public BoardType getCulture() {
			return this.culture;
		}

		public String getGodName() {
			return this.godName;
		}

		public int getNum() {
			return this.num;
		}

		public int getPrice() {
			return this.price;
		}

	}

	public enum UnitType {
		ANUBITE(BoardType.EGYPTIAN, "Anubite", new int[] { 1, 0, 3, 0 }),
		PRIEST(BoardType.EGYPTIAN, "Priest", new int[] { 0, 2, 4, 0 }),
		CHARIOT_ARCHER(BoardType.EGYPTIAN, "Chariot Archer", new int[] { 0, 0, 1, 1 }),
		ELEPHANT(BoardType.EGYPTIAN, "Elephant", new int[] { 0, 2, 1, 0 }),
		SPHINX(BoardType.EGYPTIAN, "Sphinx", new int[] { 2, 0, 2, 0 }),
		SCORPION_MAN(BoardType.EGYPTIAN, "Scorpion-Man", new int[] { 0, 4, 2, 0 }),
		SON_OF_OSIRIS(BoardType.EGYPTIAN, "Son of Osiris", new int[] { 4, 0, 4, 0 }),
		PHARAOH(BoardType.EGYPTIAN, "Pharaoh", new int[] { 0, 3, 3, 0 }),
		WADJET(BoardType.EGYPTIAN, "Wadjet", new int[] { 2, 2, 0, 0 }),
		PHOENIX(BoardType.EGYPTIAN, "Phoenix", new int[] { 3, 0, 0, 2 }),
		SPEARMAN(BoardType.EGYPTIAN, "Spearman", new int[] { 0, 1, 0, 1 }),
		MUMMY(BoardType.EGYPTIAN, "Mummy", new int[] { 2, 0, 3, 0 }),
		CENTAUR(BoardType.GREEK, "Centaur", new int[] { 1, 0, 3, 0 }),
		CYCLOP(BoardType.GREEK, "Cyclop", new int[] { 3, 3, 0, 0 }),
		MANTICORE(BoardType.GREEK, "Manticore", new int[] { 2, 2, 0, 0 }),
		CLASSICAL_GREEK_HERO(BoardType.GREEK, "Classical Greek Hero", new int[] { 0, 3, 3, 0 }),
		TOXOTES(BoardType.GREEK, "Toxotes", new int[] { 0, 0, 0, 1 }),
		HEROIC_GREEK_HERO(BoardType.GREEK, "Heroic Greek Hero", new int[] { 0, 3, 4, 0 }),
		HYDRA(BoardType.GREEK, "Hydra", new int[] { 2, 0, 2, 0 }),
		MINOTAUR(BoardType.GREEK, "Centaur", new int[] { 1, 0, 3, 0 }),
		MYTHICAL_GREEK_HERO(BoardType.GREEK, "Mythical Greek Hero", new int[] { 4, 0, 4, 0 }),
		HIPPOKON(BoardType.GREEK, "Hippokon", new int[] { 0, 1, 1, 0 }),
		HOPLITE(BoardType.GREEK, "Hoplite", new int[] { 0, 1, 0, 1 }),
		MEDUSA(BoardType.GREEK, "Medusa", new int[] { 3, 1, 0, 0 }),
		NIDHOGG(BoardType.NORSE, "Nidhogg", new int[] { 1, 0, 4, 0 }),
		THROWING_AXEMAN(BoardType.NORSE, "Throwing Axeman", new int[] { 0, 1, 0, 1 }),
		TROLL(BoardType.NORSE, "Troll", new int[] { 0, 3, 0, 2 }),
		DWARF(BoardType.NORSE, "Dwarf", new int[] { 0, 2, 2, 0 }),
		HUSKARL(BoardType.NORSE, "Huskarl", new int[] { 0, 1, 2, 0 }),
		CLASSICAL_NORSE_HERO(BoardType.NORSE, "Classical Norse Hero", new int[] { 0, 3, 3, 0 }),
		HEROIC_NORSE_HERO(BoardType.NORSE, "Heroic Norse Hero", new int[] { 0, 3, 3, 0 }),
		MYTHIC_NORSE_HERO(BoardType.NORSE, "Mythic Norse Hero", new int[] { 0, 3, 3, 0 }),
		VALKYRIE(BoardType.NORSE, "Valkyrie", new int[] { 3, 0, 1, 0 }),
		FROST_GIANT(BoardType.NORSE, "Frost Giant", new int[] { 2, 4, 0, 0 });

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
		THE_LARGEST_ARMY(0, "The Largest Army"),
		THE_MOST_BUILDINGS(1, "The Most Buildings"),
		THE_WONDER(2, "The Wonder"),
		WON_THE_LAST_BATTLE(3, "Won the Last Battle");
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

	public enum AgeType {
		ARCHAIC(0, "Archaic"),
		CLASSICAL(1, "Classical"),
		HEROIC(2, "Heroic"),
		MYTHIC(3, "Mythic");

		int n;
		String s;

		AgeType(final int n, final String s) {
			this.n = n;
			this.s = s;
		}

		public int getValue() {
			return this.n;
		}

		public String getString() {
			return this.s;
		}

	}
}
