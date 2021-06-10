package com.sgcib.pokerhands.model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Player {
	private String name;
	private Set<Card> hand;
	private List<Value> allValues;
	private List<Suit> allSuits;
	
	public Player(String name, Set<Card> hand) {
		this.name = name;
		this.hand = hand;
		this.allValues = splitValues(hand);
		this.allSuits = splitSuits(hand);
	}
	
	public Set<Card> getHand() {
		return hand;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Value> getAllValues() {
		return allValues;
	}

	public List<Suit> getAllSuits() {
		return allSuits;
	}
	
	/**
	 * Split values from cards. eg. 2H 3D 5S 9C KD ==> 2 3 5 9 13
	 * 
	 * @param cards
	 *            input cards
	 * @return a list of values
	 */
	private List<Value> splitValues(Set<Card> hand) {
		return hand.stream().map(card -> card.getValue()).sorted().collect(Collectors.toList());
	}

	/**
	 * Split suits from cards. eg. 2H 3D 5S 9C KD ==> H, D, S, C, D
	 * 
	 * @param cards
	 *            input cards
	 * @return a list of suits
	 */
	private List<Suit> splitSuits(Set<Card> hand) {
		return hand.stream().map(card -> card.getSuit()).collect(Collectors.toList());
	}
}
