package com.sgcib.pokerhands.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Poker hands.
 * @author FU
 */
public class PokerHands {
	private static final String OVER = " over ";
	private static final String EMPTY = "";
	private static final String ACE = "Ace";
	private static final String K = "K";
	private static final String Q = "Q";
	private static final String J = "J";
	private static final String T = "T";
	private static final String STRAIGHT_FLUSH = "straight flush";
	private static final String FOUR_OF_A_KIND = "four of a kind";
	private static final String FULL_HOUSE = "full house";
	private static final String FLUSH = "flush";
	private static final String STRAIGHT = "straight";
	private static final String THREE_OF_A_KIND = "three of a kind";
	private static final String COLON = ":";
	private static final String HIGH_CARD = "high card";
	private static final String PAIR = "pair";
	private static final String TWO_PAIRS = "two pairs";
	private static final String SPACE = " ";
	private static final String WHITE_WINS_WITH = "White wins. - with";
	private static final String BLACK_WINS_WITH = "Black wins. - with";
	private static final String TIE = "Tie.";
	public static Map<String, String> categoryMap = new HashMap<String, String>();

	private <T> List<T> repeatValue(int nbRepeat, List<T> values) {
		Map<T, Long> valueCount = repeatGroup(values);
		return valueCount.entrySet().stream().filter(e -> e.getValue().intValue() == nbRepeat).map(Map.Entry::getKey)
				.collect(Collectors.toList());
	}

	private <T> Map<T, Long> repeatGroup(List<T> values) {
		return values.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}

	private List<Integer> getRemainingCardValuesExcludingPair(List<Integer> values, List<Integer> pairValues) {
		return values.stream().filter(e -> !pairValues.contains(e)).collect(Collectors.toList());
	}

	private boolean isFourOfAKind(List<Integer> values) {
		return repeatValue(4, values).size() == 1 && repeatValue(1, values).size() == 1;
	}

	private boolean isFullHouse(List<Integer> values) {
		return repeatValue(3, values).size() == 1 && repeatValue(2, values).size() == 1;
	}

	private boolean isFlush(List<String> suits) {
		return repeatValue(5, suits).size() == 1;
	}

	private boolean isThreeOfAKind(List<Integer> values) {
		return repeatValue(3, values).size() == 1 && repeatValue(1, values).size() == 2;
	}

	private boolean isPair(List<Integer> values) {
		return repeatValue(2, values).size() == 1;
	}

	private boolean isTwoPairs(List<Integer> values) {
		return repeatValue(2, values).size() == 2;
	}
	
	private int getStraightFlush(List<Integer> values, List<String> suits) {
		if (getStraight(values) == 5 && getFlush(suits) == 6) return 9;
		return 0;
	}

	private int getFourOfAKind(List<Integer> values) {
		if (isFourOfAKind(values)) return 8;
		return 0;
	}

	private int getFullHouse(List<Integer> values) {
		if (isFullHouse(values)) return 7;
		return 0;
	}

	private int getFlush(List<String> suits) {
		if (isFlush(suits)) return 6;
		return 0;
	}

	private int getStraight(List<Integer> values) {
		for (int i = 1; i < values.size(); i++) {
			if (values.get(i) - values.get(i - 1) != 1) return 0;
		}
		return 5;
	}

	private int getThreeOfAKind(List<Integer> values) {
		if (isThreeOfAKind(values)) return 4;
		return 0;
	}

	private int getTwoPairs(List<Integer> values) {
		if (isTwoPairs(values)) return 3;
		return 0;
	}

	private int getPair(List<Integer> values) {
		if (isPair(values)) return 2;
		return 0;
	}

	private int checkCategory(String cards) {
		List<Integer> list = new ArrayList<Integer>();
		List<Integer> cardValues = splitValues(cards);
		Collections.sort(cardValues);
		List<String> cardSuits = splitSuits(cards);
		list.add(getStraightFlush(cardValues, cardSuits));
		list.add(getFourOfAKind(cardValues));
		list.add(getFullHouse(cardValues));
		list.add(getFlush(cardSuits));
		list.add(getStraight(cardValues));
		list.add(getThreeOfAKind(cardValues));
		list.add(getTwoPairs(cardValues));
		list.add(getPair(cardValues));
		list.add(1);
		Collections.sort(list);
		return list.get(list.size() - 1);
	}

	/**
	 * Split values from cards. eg. 2H 3D 5S 9C KD ==> 2 3 5 9 13
	 * 
	 * @param cards
	 *            input cards
	 * @return a list of values
	 */
	private static List<Integer> splitValues(String cards) {
		List<Integer> result = new ArrayList<Integer>();
		String[] splitedCards = cards.split(SPACE);
		for (String card : splitedCards) {
			result.add(Integer.valueOf(card.substring(0, card.length() - 1)));
		}
		return result;
	}

	/**
	 * Split suits from cards. eg. 2H 3D 5S 9C KD ==> H, D, S, C, D
	 * 
	 * @param cards
	 *            input cards
	 * @return a list of suits
	 */
	private static List<String> splitSuits(String cards) {
		List<String> result = new ArrayList<String>();
		String[] splitedCards = cards.split(SPACE);
		for (String card : splitedCards) {
			result.add(card.substring(card.length() - 1, card.length()));
		}
		return result;
	}

	public String compare(String black, String white) {
		black = black.replaceAll(T, "10");
		black = black.replaceAll(J, "11");
		black = black.replaceAll(Q, "12");
		black = black.replaceAll(K, "13");
		black = black.replaceAll("A", "14");
		white = white.replaceAll(T, "10");
		white = white.replaceAll(J, "11");
		white = white.replaceAll(Q, "12");
		white = white.replaceAll(K, "13");
		white = white.replaceAll("A", "14");
		int b = checkCategory(black);
		int w = checkCategory(white);
		categoryMap.put("1", HIGH_CARD);
		categoryMap.put("2", PAIR);
		categoryMap.put("3", TWO_PAIRS);
		categoryMap.put("4", THREE_OF_A_KIND);
		categoryMap.put("5", STRAIGHT);
		categoryMap.put("6", FLUSH);
		categoryMap.put("7", FULL_HOUSE);
		categoryMap.put("8", FOUR_OF_A_KIND);
		categoryMap.put("9", STRAIGHT_FLUSH);
		categoryMap.put("10", T);
		categoryMap.put("11", J);
		categoryMap.put("12", Q);
		categoryMap.put("13", K);
		categoryMap.put("14", ACE);
		List<Integer> blackValues = splitValues(black);
		List<Integer> whiteValues = splitValues(white);
		Collections.sort(blackValues);
		Collections.sort(whiteValues);
		StringBuilder winner = new StringBuilder();
		// different category - black wins
		if (b > w) {
			winner.append(BLACK_WINS_WITH);
			winner.append(SPACE);
			winner.append(categoryMap.get(String.valueOf(b)));
			if (b == 7) {
				buildFullHouseMsgWithOver(blackValues, winner);
			}
			return winner.toString();
		} else if (b < w) {
			// different category - white wins
			winner.append(WHITE_WINS_WITH);
			winner.append(SPACE);
			winner.append(categoryMap.get(String.valueOf(b)));
			if (w == 7) {
				buildFullHouseMsgWithOver(whiteValues, winner);
			}
			return winner.toString();
		} else {
			// same category
			// Straight flush or Straight ranked by their highest card
			if (b == 9 || b == 5) {
				return compareValues(blackValues.get(blackValues.size() - 1), whiteValues.get(whiteValues.size() - 1), b, blackValues, whiteValues);
			}
			// Four of a kind, Full House or Three of a Kind
			// Ranked by the sum value of the x cards
			if (b == 8 || b == 7 || b == 4) {
				return compareValues(sumValues(blackValues), sumValues(whiteValues), b, blackValues, whiteValues);
			}
			// Two Pairs
			// ranked by the value of their highest pair.
			// Hands with the same highest pair are ranked by the value of their other pair.
			// If these values are the same the hands are ranked by the value of the remaining card
			if (b == 3) {
				int blackTwoPair1 = repeatValue(2, blackValues).get(1);
				int whiteTwoPair1 = repeatValue(2, whiteValues).get(1);
				int blackTwoPair2 = repeatValue(2, blackValues).get(0);
				int whiteTwoPair2 = repeatValue(2, whiteValues).get(0);
				int blackTwoPairRemaining = getRemainingCardValuesExcludingPair(blackValues,
						Arrays.asList(blackTwoPair1, blackTwoPair2)).get(0);
				int whiteTwoPairRemaining = getRemainingCardValuesExcludingPair(whiteValues,
						Arrays.asList(whiteTwoPair1, whiteTwoPair2)).get(0);
				boolean isBlackWinWithTwoPairs = false;
				if (blackTwoPair1 > whiteTwoPair1) {
					isBlackWinWithTwoPairs = true;
				} else if (blackTwoPair1 < whiteTwoPair1) {
					isBlackWinWithTwoPairs = false;
				} else {
					if (blackTwoPair2 > whiteTwoPair2) {
						isBlackWinWithTwoPairs = true;
					} else if (blackTwoPair2 < whiteTwoPair2) {
						isBlackWinWithTwoPairs = false;
					} else {
						if (blackTwoPairRemaining > whiteTwoPairRemaining) {
							isBlackWinWithTwoPairs = true;
						} else if (blackTwoPairRemaining < whiteTwoPairRemaining) {
							isBlackWinWithTwoPairs = false;
						} else {
							return TIE;
						}
					}
				}
				return isBlackWinWithTwoPairs ? BLACK_WINS_WITH + SPACE + TWO_PAIRS : WHITE_WINS_WITH + SPACE + TWO_PAIRS;
			}
			// Pair
			// a pair are ranked by the value of the cards forming the pair.
			// If same, ranked by the values of the cards not forming the pair, decreasing order
			if (b == 2) {
				int blackOnePair = repeatValue(2, blackValues).get(0);
				int whiteOnePair = repeatValue(2, whiteValues).get(0);
				if (blackOnePair > whiteOnePair) {
					return BLACK_WINS_WITH + SPACE + PAIR;
				} else if (blackOnePair < whiteOnePair) {
					return WHITE_WINS_WITH + SPACE + PAIR;
				} else {
					blackValues = getRemainingCardValuesExcludingPair(blackValues, Arrays.asList(blackOnePair));
					whiteValues = getRemainingCardValuesExcludingPair(whiteValues, Arrays.asList(whiteOnePair));
					for (int i = blackValues.size() - 1; i > 0; i--) {
						if (blackValues.get(i) > whiteValues.get(i)) {
							return BLACK_WINS_WITH + SPACE + PAIR;
						} else if (blackValues.get(i) < whiteValues.get(i)) {
							return WHITE_WINS_WITH + SPACE + PAIR;
						}
					}
					return TIE;
				}
			}

			// High Card or Flush
			if (b == 1 || b == 6) {
				for (int i = blackValues.size() - 1; i > 0; i--) {
					if (blackValues.get(i) > whiteValues.get(i)) {
						String card = blackValues.get(i) >= 10 ? categoryMap.get(String.valueOf(blackValues.get(i)))
								: String.valueOf(blackValues.get(i));
						if (b == 6) {
							return BLACK_WINS_WITH + SPACE + categoryMap.get(String.valueOf(b));
						}
						return BLACK_WINS_WITH + SPACE + HIGH_CARD + COLON + SPACE + card;
					} else if (blackValues.get(i) < whiteValues.get(i)) {
						String card = whiteValues.get(i) >= 10 ? categoryMap.get(String.valueOf(whiteValues.get(i)))
								: String.valueOf(whiteValues.get(i));
						if (b == 6) {
							return WHITE_WINS_WITH + SPACE + categoryMap.get(String.valueOf(b));
						}
						return WHITE_WINS_WITH + SPACE + HIGH_CARD + COLON + SPACE + card;
					}
				}
				return TIE;
			}
		}
		return EMPTY;
	}

	private void buildFullHouseMsgWithOver(List<Integer> values, StringBuilder winner) {
		int threeSameValues = repeatValue(3, values).get(0);
		int pair = repeatValue(2, values).get(0);
		winner.append(COLON);
		winner.append(SPACE);
		winner.append(threeSameValues);
		winner.append(OVER);
		winner.append(pair);
	}

	private int sumValues(List<Integer> values) {
		return values.stream().reduce(0, Integer::sum);
	}

	private String compareValues(int b, int w, int type, List<Integer> blackValues, List<Integer> whiteValues) {
		StringBuilder winner = new StringBuilder();
		if (b == w) {
			return TIE;
		} else if (b > w) {
			winner.append(BLACK_WINS_WITH);
			winner.append(SPACE);
			winner.append(categoryMap.get(String.valueOf(type)));
			if (b == 7) {
				buildFullHouseMsgWithOver(blackValues, winner);
			}
			return winner.toString();
		} else {
			winner.append(WHITE_WINS_WITH);
			winner.append(SPACE);
			winner.append(categoryMap.get(String.valueOf(type)));
			if (w == 7) {
				buildFullHouseMsgWithOver(whiteValues, winner);
			}
			return winner.toString();
		}
	}
}