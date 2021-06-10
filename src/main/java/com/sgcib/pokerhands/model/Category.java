package com.sgcib.pokerhands.model;

public enum Category {
	HIGH_CARD("high card"),
	PAIR("pair"),
	TWO_PAIRS("two pairs"),
	THREE_OF_A_KIND("three of a kind"),
	STRAIGHT("straight"),
	FLUSH("flush"),
	FULL_HOUSE("full house"),
	FOUR_OF_A_KIND("four of a kind"),
	STRAIGHT_FLUSH("straight flush");

	private final String name;

	private Category(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
