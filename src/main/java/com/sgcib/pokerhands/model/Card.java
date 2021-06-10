package com.sgcib.pokerhands.model;

import java.util.Objects;

public class Card {

    private final Value value;
    private final Suit suit;

    public Card(Value value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public Value getValue() {
        return value;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public boolean equals(Object o) {
    	if(o == null) return false;
    	else {
    		if (o instanceof Card) {
    			Card c = (Card) o;
    			if (c.value == this.value && c.suit == this.suit) return true;
    		}
    	}
    	return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, suit);
    }

    @Override
    public String toString() {
        return value.getShortName() + suit.getName();
    }
}
