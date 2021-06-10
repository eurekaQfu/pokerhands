package com.sgcib.pokerhands.model;

public enum Value {
	TWO("2", "2", 2),
	THREE("3", "3", 3),
	FOUR("4", "4", 4),
	FIVE("5", "5", 5),
	SIX("6", "6", 6),
	SEVEN("7", "7", 7),
	EIGHT("8", "8", 8),
	NINE("9", "9", 9),
	TEN("T", "10", 10),
	JACK("J", "Jack", 11),
	QUEEN("Q", "Queen", 12),
	KING("K", "King", 13),
	ACE("A", "Ace", 14);

	private final String shortName;
	private final String longName;
	private final Integer value;
	
	private Value(String shortName, String longName, Integer value) {
		this.shortName = shortName;
		this.longName = longName;
		this.value = value;
	}
	
	public String getShortName() {
		return shortName;
	}
	public String getLongName() {
		return longName;
	}
	public Integer getValue() {
		return value;
	}
}
