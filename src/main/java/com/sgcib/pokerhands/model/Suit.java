package com.sgcib.pokerhands.model;

public enum Suit {
	CLUB("C"),
	DIAMOND("D"),
	HEART("H"),
	SPADE("S");
	
	private final String name;
	
	private Suit(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
