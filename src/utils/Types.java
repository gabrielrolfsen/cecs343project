/**
 *  
 *
 * @author Gabriel Franzoni
 * @version %I%, %G%
 * @since Mar 3, 2015
 */
package utils;

import java.util.ArrayList;

import models.Unit;

/**
 * @author grolfsen
 *
 */
public class Types {

	public enum CultureType {
		NORSE(0),
		EGYPTIAN(1),
		GREEK(2);
		private final int n;

		CultureType(final int n) {
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
		BUILD_EGYPTIAN(RANDOM, CultureType.EGYPTIAN, 3, "Nephthys", 2),
		EXPLORE_EGYPTIAN(RANDOM, CultureType.EGYPTIAN, 0, "Ptah", 1),
		GATHER_EGYPTIAN(RANDOM, CultureType.EGYPTIAN, 0, "Ra", 3),
		NEXT_AGE_EGYPTIAN(RANDOM, CultureType.EGYPTIAN, 0, "Hathor", 1),
		RECRUIT_EGYPTIAN(RANDOM, CultureType.EGYPTIAN, 6, "Bast", 1),
		TRADE_EGYPTIAN(RANDOM, CultureType.EGYPTIAN, 0, "N/A", 1),
		BUILD_GREEK(RANDOM, CultureType.GREEK, 3, "Hera", 1),
		EXPLORE_GREEK(RANDOM, CultureType.GREEK, 0, "Arthmis", 1),
		GATHER_GREEK(RANDOM, CultureType.GREEK, 0, "Poseidon", 1),
		NEXT_AGE_GREEK(RANDOM, CultureType.GREEK, 0, "Hephaestus", 2),
		RECRUIT_GREEK(RANDOM, CultureType.GREEK, 4, "Apollo", 1),
		TRADE_GREEK(RANDOM, CultureType.GREEK, 0, "Hermes", 1),
		BUILD_NORSE(RANDOM, CultureType.NORSE, 4, "Njord", 1),
		EXPLORE_NORSE(RANDOM, CultureType.NORSE, 0, "Baldr", 1),
		GATHER_NORSE(RANDOM, CultureType.NORSE, 0, "Freyja", 1),
		NEXT_AGE_NORSE(RANDOM, CultureType.NORSE, 0, "Odin", 1),
		RECRUIT_NORSE(RANDOM, CultureType.NORSE, 3, "Hel", 1),
		TRADE_NORSE(RANDOM, CultureType.NORSE, 0, "Forseti", 1);

		private int id = 0;
		private CardType type = null;
		private CultureType culture = null;
		private String godName = "";
		private int num = 0;
		private int cost = 0;

		CardType() {
		}

		CardType(final CardType type, final int id, final int cost) {
			this.id = id;
			this.type = type;
			this.cost = cost;
		}

		CardType(final CardType type, final CultureType culture, final int num, final String godName,
				final int price) {
			this.id = 0;
			this.type = type;
			this.culture = culture;
			this.num = num;
			this.godName = godName;
			this.cost = price;
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

		public CultureType getCulture() {
			return this.culture;
		}

		public String getGodName() {
			return this.godName;
		}

		public int getNum() {
			return this.num;
		}

		public int getCost() {
			return this.cost;
		}

	}

	public enum UnitType {
		MORTAL(),
		MYTH(),
		HERO(),
		ANUBITE(CultureType.EGYPTIAN, "Anubite", MYTH, new int[] { 1, 0, 3, 0 }),
		PRIEST(CultureType.EGYPTIAN, "Priest", HERO, new int[] { 0, 2, 4, 0 }),
		CHARIOT_ARCHER(CultureType.EGYPTIAN, "Chariot Archer", MORTAL, new int[] { 0, 0, 1, 1 }),
		ELEPHANT(CultureType.EGYPTIAN, "Elephant", MORTAL, new int[] { 0, 2, 1, 0 }),
		SPHINX(CultureType.EGYPTIAN, "Sphinx", MYTH, new int[] { 2, 0, 2, 0 }),
		SCORPION_MAN(CultureType.EGYPTIAN, "Scorpion-Man", MYTH, new int[] { 0, 4, 2, 0 }),
		SON_OF_OSIRIS(CultureType.EGYPTIAN, "Son of Osiris", MYTH, new int[] { 4, 0, 4, 0 }),
		PHARAOH(CultureType.EGYPTIAN, "Pharaoh", HERO, new int[] { 0, 3, 3, 0 }),
		WADJET(CultureType.EGYPTIAN, "Wadjet", MYTH, new int[] { 2, 2, 0, 0 }),
		PHOENIX(CultureType.EGYPTIAN, "Phoenix", MYTH, new int[] { 3, 0, 0, 2 }),
		SPEARMAN(CultureType.EGYPTIAN, "Spearman", MORTAL, new int[] { 0, 1, 0, 1 }),
		MUMMY(CultureType.EGYPTIAN, "Mummy", MYTH, new int[] { 2, 0, 3, 0 }),
		CENTAUR(CultureType.GREEK, "Centaur", MYTH, new int[] { 1, 0, 3, 0 }),
		CYCLOP(CultureType.GREEK, "Cyclop", MYTH, new int[] { 3, 3, 0, 0 }),
		MANTICORE(CultureType.GREEK, "Manticore", MYTH, new int[] { 2, 2, 0, 0 }),
		CLASSICAL_GREEK_HERO(CultureType.GREEK, "Classical Greek Hero", HERO, new int[] { 0, 3, 3, 0 }),
		TOXOTES(CultureType.GREEK, "Toxotes", MORTAL, new int[] { 0, 0, 0, 1 }),
		HEROIC_GREEK_HERO(CultureType.GREEK, "Heroic Greek Hero", HERO, new int[] { 0, 3, 4, 0 }),
		HYDRA(CultureType.GREEK, "Hydra", MYTH, new int[] { 2, 0, 2, 0 }),
		MINOTAUR(CultureType.GREEK, "Centaur", MYTH, new int[] { 1, 0, 3, 0 }),
		MYTHICAL_GREEK_HERO(CultureType.GREEK, "Mythical Greek Hero", HERO, new int[] { 4, 0, 4, 0 }),
		HIPPOKON(CultureType.GREEK, "Hippokon", MORTAL, new int[] { 0, 1, 1, 0 }),
		HOPLITE(CultureType.GREEK, "Hoplite", MORTAL, new int[] { 0, 1, 0, 1 }),
		MEDUSA(CultureType.GREEK, "Medusa", MYTH, new int[] { 3, 1, 0, 0 }),
		JARL(CultureType.NORSE, "Jarl", MORTAL, new int[] { 0, 1, 1, 0 }),
		NIDHOGG(CultureType.NORSE, "Nidhogg", MYTH, new int[] { 1, 0, 4, 0 }),
		THROWING_AXEMAN(CultureType.NORSE, "Throwing Axeman", MORTAL, new int[] { 0, 1, 0, 1 }),
		TROLL(CultureType.NORSE, "Troll", MYTH, new int[] { 0, 3, 0, 2 }),
		DWARF(CultureType.NORSE, "Dwarf", MYTH, new int[] { 0, 2, 2, 0 }),
		HUSKARL(CultureType.NORSE, "Huskarl", MORTAL, new int[] { 0, 1, 2, 0 }),
		CLASSICAL_NORSE_HERO(CultureType.NORSE, "Classical Norse Hero", HERO, new int[] { 0, 3, 3, 0 }),
		HEROIC_NORSE_HERO(CultureType.NORSE, "Heroic Norse Hero", HERO, new int[] { 0, 3, 3, 0 }),
		MYTHIC_NORSE_HERO(CultureType.NORSE, "Mythic Norse Hero", HERO, new int[] { 0, 3, 3, 0 }),
		VALKYRIE(CultureType.NORSE, "Valkyrie", MYTH, new int[] { 3, 0, 1, 0 }),
		FROST_GIANT(CultureType.NORSE, "Frost Giant", MYTH, new int[] { 2, 4, 0, 0 });

		private CultureType type = null;
		private UnitType subType = null;
		private String name = "";
		private int[] cost = null;

		UnitType() {
		}

		UnitType(final CultureType b, final String name, final UnitType subType, final int[] cost) {
			this.cost = cost;
			this.type = b;
			this.subType = subType;
			this.name = name;
		}

		/**
		 * Given the Culture and SubType, return an array with all units
		 * matching these criteria.
		 * 
		 * @param culture
		 * @param subType
		 * @return
		 */
		public static ArrayList<Unit> getUnits(final CultureType culture, final UnitType subType) {
			final ArrayList<Unit> result = new ArrayList<Unit>();
			for (final UnitType u : UnitType.values()) {
				if (u.getCulture() == culture && u.getSubType() == subType) {
					result.add(new Unit(u));
				}
			}

			return result;
		}

		public static ArrayList<Unit> getUnits(final CultureType culture) {
			final ArrayList<Unit> result = new ArrayList<Unit>();
			for (final UnitType u : UnitType.values()) {
				if (u.getCulture() == culture) {
					result.add(new Unit(u));
				}
			}

			return result;
		}

		public static ArrayList<Unit> getUnits(final UnitType subType) {
			final ArrayList<Unit> result = new ArrayList<Unit>();
			for (final UnitType u : UnitType.values()) {
				if (u.getSubType() == subType) {
					result.add(new Unit(u));
				}
			}

			return result;
		}

		public static Unit getUnit(final String unitName) {
			Unit result = null;
			for (final UnitType u : UnitType.values()) {
				if (u.getName().equals(unitName)) {
					result = new Unit(u);
				}
			}
			return result;
		}

		public UnitType getSubType() {
			return this.subType;
		}

		public String getName() {
			return this.name;
		}

		public int[] getCost() {
			return this.cost;
		}

		public CultureType getCulture() {
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
