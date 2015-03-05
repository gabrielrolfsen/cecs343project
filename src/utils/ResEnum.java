package utils;

public enum ResEnum {
	FOOD(0), FAVOR(1), WOOD(2), GOLD(3), VICTORY(4);
	
	private int myValue;
	
	ResEnum(int v) { myValue = v; }
	
	public int getValue() { return myValue; }
}
