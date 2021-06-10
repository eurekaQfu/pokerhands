package com.sgcib.pokerhands;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.sgcib.pokerhands.demo.PokerHandRank;
import com.sgcib.pokerhands.model.Card;
import com.sgcib.pokerhands.model.Player;
import com.sgcib.pokerhands.model.Suit;
import com.sgcib.pokerhands.model.Value;

/**
 * Main class
 * @author Qiang FU
 */
public class App {
	
	private static final String WHITE = "White";
	private static final String BLACK = "Black";
	private static final String SPACE = " ";
	private static Scanner sc;

	public static void main(String[] args) {
		System.out.println("Poker Hands");
		sc = new Scanner(System.in);
		System.out.println("Please input 5 poker hand for the player black like 2H 3D 5S 9C KD :");
		String black = sc.nextLine();
		System.out.println("Please input 5 poker hand for the player white like 2C 3H 4S 8C AH :");
		String white = sc.nextLine();

		Map<String, Value> valueMap = new HashMap<String, Value>();
		valueMap.put("2", Value.TWO);
		valueMap.put("3", Value.THREE);
		valueMap.put("4", Value.FOUR);
		valueMap.put("5", Value.FIVE);
		valueMap.put("6", Value.SIX);
		valueMap.put("7", Value.SEVEN);
		valueMap.put("8", Value.EIGHT);
		valueMap.put("9", Value.NINE);
		valueMap.put("T", Value.TEN);
		valueMap.put("J", Value.JACK);
		valueMap.put("K", Value.KING);
		valueMap.put("A", Value.ACE);
		Map<String, Suit> suitMap = new HashMap<String, Suit>();
		suitMap.put("C", Suit.CLUB);
		suitMap.put("D", Suit.DIAMOND);
		suitMap.put("H", Suit.HEART);
		suitMap.put("S", Suit.SPADE);

		Set<Card> blackHand = new HashSet<Card>();
		String[] blackCards = black.split(SPACE);
		for (String card : blackCards) {
			blackHand.add(new Card(valueMap.get(card.substring(0, card.length() - 1)),
					suitMap.get(card.substring(card.length() - 1, card.length()))));
		}

		Set<Card> whiteHand = new HashSet<Card>();
		String[] whiteCards = white.split(SPACE);
		for (String card : whiteCards) {
			whiteHand.add(new Card(valueMap.get(card.substring(0, card.length() - 1)),
					suitMap.get(card.substring(card.length() - 1, card.length()))));
		}

		Player blackPlayer = new Player(BLACK, blackHand);
		Player whitePlayer = new Player(WHITE, whiteHand);
		PokerHandRank rank = new PokerHandRank();
		String result = rank.compare(blackPlayer, whitePlayer);
		System.out.println(result);
	}

}
