package com.sgcib.pokerhands.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.sgcib.pokerhands.model.Category;
import com.sgcib.pokerhands.model.Player;
import com.sgcib.pokerhands.model.Suit;
import com.sgcib.pokerhands.model.Value;

public class PokerHandRank {
	private static final String OVER = " over ";
	private static final String EMPTY = "";
	private static final String COLON = ":";
	private static final String SPACE = " ";
	private static final String WINS_WITH = " wins. - with";
	private static final String TIE = "Tie.";
	public static Map<Integer, Category> categoryMap = new HashMap<Integer, Category>();

	public String compare(Player black, Player white) {
		int b = getCategory(black);
		int w = getCategory(white);
		categoryMap.put(1, Category.HIGH_CARD);
		categoryMap.put(2, Category.PAIR);
		categoryMap.put(3, Category.TWO_PAIRS);
		categoryMap.put(4, Category.THREE_OF_A_KIND);
		categoryMap.put(5, Category.STRAIGHT);
		categoryMap.put(6, Category.FLUSH);
		categoryMap.put(7, Category.FULL_HOUSE);
		categoryMap.put(8, Category.FOUR_OF_A_KIND);
		categoryMap.put(9, Category.STRAIGHT_FLUSH);
		List<Value> blackValues = black.getAllValues();
		List<Value> whiteValues = white.getAllValues();
		StringBuilder winner = new StringBuilder();
		// different category
		if (b > w) {
			// black wins
			return buildWinnerWithDiffCategory(black.getName(), b, blackValues, winner);
		} else if (b < w) {
			// white wins
			return buildWinnerWithDiffCategory(white.getName(), w, whiteValues, winner);
		} else {
			// same category
			// Straight flush or Straight ranked by their highest card
			if (b == 9 || b == 5) {
				return compareValues(blackValues.get(blackValues.size() - 1).getValue(), whiteValues.get(whiteValues.size() - 1).getValue(), b, black, white);
			}
			// Four of a kind, Full House or Three of a Kind
			// Ranked by the sum value of the x cards
			if (b == 8 || b == 7 || b == 4) {
				return compareValues(sumValues(blackValues), sumValues(whiteValues), b, black, white);
			}
			// Two Pairs
			// ranked by the value of their highest pair.
			// Hands with the same highest pair are ranked by the value of their other pair.
			// If these values are the same the hands are ranked by the value of the remaining card
			if (b == 3) {
				List<Value> blackTwoPair1Values = repeatValue(2, blackValues);
				Value blackTwoPair1Value = blackTwoPair1Values.get(1);
				Value blackTwoPair2Value = blackTwoPair1Values.get(0);
				List<Value> whiteTwoPair1Values = repeatValue(2, whiteValues);
				Value whiteTwoPair1Value = whiteTwoPair1Values.get(1);
				Value whiteTwoPair2Value = whiteTwoPair1Values.get(0);
				int blackTwoPair1 = blackTwoPair1Value.getValue();
				int whiteTwoPair1 = whiteTwoPair1Value.getValue();
				int blackTwoPair2 = blackTwoPair2Value.getValue();
				int whiteTwoPair2 = whiteTwoPair2Value.getValue();
				int blackTwoPairRemaining = getRemainingCardValuesExcludingPair(blackValues,
						Arrays.asList(blackTwoPair1Value, blackTwoPair2Value)).get(0).getValue();
				int whiteTwoPairRemaining = getRemainingCardValuesExcludingPair(whiteValues,
						Arrays.asList(whiteTwoPair1Value, whiteTwoPair2Value)).get(0).getValue();
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
				return isBlackWinWithTwoPairs ? black.getName() + WINS_WITH + SPACE + categoryMap.get(b).getName() : white.getName() + WINS_WITH + SPACE + categoryMap.get(b).getName();
			}
			// Pair
			// a pair are ranked by the value of the cards forming the pair.
			// If same, ranked by the values of the cards not forming the pair, decreasing order
			if (b == 2) {
				Value blackOnePairValue = repeatValue(2, blackValues).get(0);
				int blackOnePair = blackOnePairValue.getValue();
				Value whiteOnePairValue = repeatValue(2, whiteValues).get(0);
				int whiteOnePair = whiteOnePairValue.getValue();
				if (blackOnePair > whiteOnePair) {
					return black.getName() + WINS_WITH + SPACE + categoryMap.get(b).getName();
				} else if (blackOnePair < whiteOnePair) {
					return white.getName() + WINS_WITH + SPACE + categoryMap.get(b).getName();
				} else {
					blackValues = getRemainingCardValuesExcludingPair(blackValues, Arrays.asList(blackOnePairValue));
					whiteValues = getRemainingCardValuesExcludingPair(whiteValues, Arrays.asList(whiteOnePairValue));
					for (int i = blackValues.size() - 1; i > 0; i--) {
						if (blackValues.get(i).getValue() > whiteValues.get(i).getValue()) {
							return black.getName() + WINS_WITH + SPACE + categoryMap.get(b).getName();
						} else if (blackValues.get(i).getValue() < whiteValues.get(i).getValue()) {
							return white.getName() + WINS_WITH + SPACE + categoryMap.get(b).getName();
						}
					}
					return TIE;
				}
			}

			// High Card or Flush
			if (b == 1 || b == 6) {
				for (int i = blackValues.size() - 1; i > 0; i--) {
					if (blackValues.get(i).getValue() > whiteValues.get(i).getValue()) {
						String card = blackValues.get(i).getLongName();
						if (b == 6) {
							return black.getName() + WINS_WITH + SPACE + categoryMap.get(b).getName();
						}
						return black.getName() + WINS_WITH + SPACE + categoryMap.get(b).getName() + COLON + SPACE + card;
					} else if (blackValues.get(i).getValue() < whiteValues.get(i).getValue()) {
						String card = whiteValues.get(i).getLongName();
						if (b == 6) {
							return white.getName() + WINS_WITH + SPACE + categoryMap.get(b).getName();
						}
						return white.getName() + WINS_WITH + SPACE + categoryMap.get(b).getName() + COLON + SPACE + card;
					}
				}
				return TIE;
			}
		}
		return EMPTY;
	}
	
	private int getCategory(Player player) {
		List<Integer> list = new ArrayList<Integer>();
		List<Value> cardValues = player.getAllValues();
		List<Suit> cardSuits = player.getAllSuits();
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
	
	private <T> List<T> repeatValue(int nbRepeat, List<T> values) {
		Map<T, Long> valueCount = repeatGroup(values);
		return valueCount.entrySet().stream().filter(e -> e.getValue().intValue() == nbRepeat).map(Map.Entry::getKey).sorted()
				.collect(Collectors.toList());
	}

	private <T> Map<T, Long> repeatGroup(List<T> values) {
		return values.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}

	private List<Value> getRemainingCardValuesExcludingPair(List<Value> values, List<Value> pairValues) {
		return values.stream().filter(e -> !pairValues.contains(e)).collect(Collectors.toList());
	}

	private boolean isFourOfAKind(List<Value> values) {
		return repeatValue(4, values).size() == 1 && repeatValue(1, values).size() == 1;
	}

	private boolean isFullHouse(List<Value> values) {
		return repeatValue(3, values).size() == 1 && repeatValue(2, values).size() == 1;
	}

	private boolean isFlush(List<Suit> suits) {
		return repeatValue(5, suits).size() == 1;
	}

	private boolean isThreeOfAKind(List<Value> values) {
		return repeatValue(3, values).size() == 1 && repeatValue(1, values).size() == 2;
	}

	private boolean isPair(List<Value> values) {
		return repeatValue(2, values).size() == 1;
	}

	private boolean isTwoPairs(List<Value> values) {
		return repeatValue(2, values).size() == 2;
	}
	
	private int getStraightFlush(List<Value> values, List<Suit> suits) {
		if (getStraight(values) == 5 && getFlush(suits) == 6) return 9;
		return 0;
	}
	
	private int getFourOfAKind(List<Value> values) {
		if (isFourOfAKind(values)) return 8;
		return 0;
	}

	private int getFullHouse(List<Value> values) {
		if (isFullHouse(values)) return 7;
		return 0;
	}

	private int getFlush(List<Suit> suits) {
		if (isFlush(suits)) return 6;
		return 0;
	}

	private int getStraight(List<Value> values) {
		for (int i = 1; i < values.size(); i++) {
			if (values.get(i).getValue() - values.get(i - 1).getValue() != 1) return 0;
		}
		return 5;
	}

	private int getThreeOfAKind(List<Value> values) {
		if (isThreeOfAKind(values)) return 4;
		return 0;
	}

	private int getTwoPairs(List<Value> values) {
		if (isTwoPairs(values)) return 3;
		return 0;
	}

	private int getPair(List<Value> values) {
		if (isPair(values)) return 2;
		return 0;
	}

	private String buildWinnerWithDiffCategory(String playerName, int categoryIndex, List<Value> values, StringBuilder winner) {
		winner.append(playerName);
		winner.append(WINS_WITH);
		winner.append(SPACE);
		winner.append(categoryMap.get(categoryIndex).getName());
		if (categoryIndex == 7) {
			buildFullHouseMsgWithOver(values, winner);
		}
		return winner.toString();
	}

	private void buildFullHouseMsgWithOver(List<Value> values, StringBuilder winner) {
		Value threeSameValues = repeatValue(3, values).get(0);
		Value pair = repeatValue(2, values).get(0);
		winner.append(COLON);
		winner.append(SPACE);
		winner.append(threeSameValues.getValue());
		winner.append(OVER);
		winner.append(pair.getValue());
	}

	private int sumValues(List<Value> values) {
		return values.stream().map(v -> v.getValue()).reduce(0, Integer::sum);
	}

	private String compareValues(int b, int w, int categoryIndex, Player black, Player white) {
		StringBuilder winner = new StringBuilder();
		if (b == w) {
			return TIE;
		} else if (b > w) {
			return buildWinnerWithDiffCategory(black.getName(), categoryIndex, black.getAllValues(), winner);
		} else {
			return buildWinnerWithDiffCategory(white.getName(), categoryIndex, white.getAllValues(), winner);
		}
	}
}
