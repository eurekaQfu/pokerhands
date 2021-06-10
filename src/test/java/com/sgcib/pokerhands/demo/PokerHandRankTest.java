package com.sgcib.pokerhands.demo;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sgcib.pokerhands.model.Card;
import com.sgcib.pokerhands.model.Player;
import com.sgcib.pokerhands.model.Suit;
import com.sgcib.pokerhands.model.Value;

/**
 * Unit test for poker hand rank.
 * @author Qiang FU
 */
public class PokerHandRankTest {
	private static final String WHITE = "White";
	private static final String BLACK = "Black";
	private static final String TIE = "Tie.";
	private static final String WHITE_WINS_WITH_HIGH_CARD_ACE = "White wins. - with high card: Ace";
	private static final String BLACK_WINS_WITH_FULL_HOUSE_4_OVER_2 = "Black wins. - with full house: 4 over 2";
	private static final String BLACK_WINS_WITH_HIGH_CARD_9 = "Black wins. - with high card: 9";
	private static final String WHITE_WINS_WITH_STRAIGHT = "White wins. - with straight";
	private static final String WHITE_WINS_WITH_STRAIGHT_FLUSH = "White wins. - with straight flush";
	private static final String BLACK_WINS_WITH_STRAIGHT_FLUSH = "Black wins. - with straight flush";
	private static final String WHITE_WINS_WITH_FOUR_OF_A_KIND = "White wins. - with four of a kind";
	private static final String WHITE_WINS_WITH_THREE_OF_A_KIND = "White wins. - with three of a kind";
	private static final String WHITE_WINS_WITH_FLUSH = "White wins. - with flush";
	private static final String BLACK_WINS_WITH_FLUSH = "Black wins. - with flush";
	private static final String BLACK_WINS_WITH_PAIR = "Black wins. - with pair";
	private static final String WHITE_WINS_WITH_PAIR = "White wins. - with pair";
	private static final String BLACK_WINS_WITH_TWO_PAIRS = "Black wins. - with two pairs";
	private static final String WHITE_WINS_WITH_TWO_PAIRS = "White wins. - with two pairs";
	
	PokerHandRank rank;
	Player blackPlayer;
	Player whitePlayer;
	
	@Before
    public void setUp(){
		rank = new PokerHandRank();
	}
    
    @Test
    public void winWithHighCardWhenHighestCardIsAceAndSameCategory() {
    	// black 2H 3D 5S 9C KD, white 2C 3H 4S 8C AH
    	Set<Card> blackHand = Stream.of(
    			new Card(Value.TWO, Suit.HEART),
                new Card(Value.THREE, Suit.DIAMOND),
                new Card(Value.FIVE, Suit.SPADE),
                new Card(Value.NINE, Suit.CLUB),
                new Card(Value.KING, Suit.DIAMOND)).collect(Collectors.toSet());
    	Set<Card> whiteHand = Stream.of(
                new Card(Value.TWO, Suit.CLUB),
                new Card(Value.THREE, Suit.HEART),
                new Card(Value.FOUR, Suit.SPADE),
                new Card(Value.EIGHT, Suit.CLUB),
                new Card(Value.ACE, Suit.HEART)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
        Assert.assertEquals(WHITE_WINS_WITH_HIGH_CARD_ACE, result);
    }
    
    @Test
    public void winWithFullHouseWhenDiffCategory() {
    	// black 2H 4S 4C 2D 4H, white 2S 8S AS QS 3S full, house vs flush
        Set<Card> blackHand = Stream.of(
    			new Card(Value.TWO, Suit.HEART),
                new Card(Value.FOUR, Suit.SPADE),
                new Card(Value.FOUR, Suit.CLUB),
                new Card(Value.TWO, Suit.DIAMOND),
                new Card(Value.FOUR, Suit.HEART)).collect(Collectors.toSet());
    	Set<Card> whiteHand = Stream.of(
                new Card(Value.TWO, Suit.SPADE),
                new Card(Value.EIGHT, Suit.SPADE),
                new Card(Value.ACE, Suit.SPADE),
                new Card(Value.QUEEN, Suit.SPADE),
                new Card(Value.THREE, Suit.SPADE)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
        Assert.assertEquals(BLACK_WINS_WITH_FULL_HOUSE_4_OVER_2, result);
    }
    
    @Test
    public void winWithFullHouseWhenSameCategory() {
    	// black 2H 4S 4C 2D 4H, white 2C 3S 3C 3D 2S
        Set<Card> blackHand = Stream.of(
    			new Card(Value.TWO, Suit.HEART),
                new Card(Value.FOUR, Suit.SPADE),
                new Card(Value.FOUR, Suit.CLUB),
                new Card(Value.TWO, Suit.DIAMOND),
                new Card(Value.FOUR, Suit.HEART)).collect(Collectors.toSet());
    	Set<Card> whiteHand = Stream.of(
                new Card(Value.TWO, Suit.CLUB),
                new Card(Value.THREE, Suit.SPADE),
                new Card(Value.THREE, Suit.CLUB),
                new Card(Value.THREE, Suit.DIAMOND),
                new Card(Value.TWO, Suit.SPADE)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
        Assert.assertEquals(BLACK_WINS_WITH_FULL_HOUSE_4_OVER_2, result);
    }
    
    @Test
    public void winWithHighCardWhenHighestCardIsNineAndSameCategory() {
    	// black 2H 3D 5S 9C KD, white 2C 3H 4S 8C KH
        Set<Card> blackHand = Stream.of(
    			new Card(Value.TWO, Suit.HEART),
                new Card(Value.THREE, Suit.DIAMOND),
                new Card(Value.FIVE, Suit.SPADE),
                new Card(Value.NINE, Suit.CLUB),
                new Card(Value.KING, Suit.DIAMOND)).collect(Collectors.toSet());
    	Set<Card> whiteHand = Stream.of(
                new Card(Value.TWO, Suit.CLUB),
                new Card(Value.THREE, Suit.HEART),
                new Card(Value.FOUR, Suit.SPADE),
                new Card(Value.EIGHT, Suit.CLUB),
                new Card(Value.KING, Suit.HEART)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
        Assert.assertEquals(BLACK_WINS_WITH_HIGH_CARD_9, result);
    }
    
    @Test
    public void tieWithHighCardWhenSameCategory() {
    	// black 2H 3D 5S 9C KD, white 2D 3H 5C 9S KH
        Set<Card> blackHand = Stream.of(
    			new Card(Value.TWO, Suit.HEART),
                new Card(Value.THREE, Suit.DIAMOND),
                new Card(Value.FIVE, Suit.SPADE),
                new Card(Value.NINE, Suit.CLUB),
                new Card(Value.KING, Suit.DIAMOND)).collect(Collectors.toSet());
    	Set<Card> whiteHand = Stream.of(
                new Card(Value.TWO, Suit.DIAMOND),
                new Card(Value.THREE, Suit.HEART),
                new Card(Value.FIVE, Suit.CLUB),
                new Card(Value.NINE, Suit.SPADE),
                new Card(Value.KING, Suit.HEART)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
    	Assert.assertEquals(TIE, result);
    }
    
    @Test
    public void winWithStraightFlushRankedByHighestWhenSameCategory() {
    	// black 2H 3H 4H 5H 6H, white 7H 8H 9H 10H JH
        Set<Card> blackHand = Stream.of(
    			new Card(Value.TWO, Suit.HEART),
                new Card(Value.THREE, Suit.HEART),
                new Card(Value.FOUR, Suit.HEART),
                new Card(Value.FIVE, Suit.HEART),
                new Card(Value.SIX, Suit.HEART)).collect(Collectors.toSet());
    	Set<Card> whiteHand = Stream.of(
                new Card(Value.SEVEN, Suit.HEART),
                new Card(Value.EIGHT, Suit.HEART),
                new Card(Value.NINE, Suit.HEART),
                new Card(Value.TEN, Suit.HEART),
                new Card(Value.JACK, Suit.HEART)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
    	Assert.assertEquals(WHITE_WINS_WITH_STRAIGHT_FLUSH, result);
    }
    
    @Test
    public void winWithStraightFlushWhenDiffCategory() {
    	// black 2H 3H 4H 5H 6H, white 5C 8D 7H 7D JH, straight flush vs pair
        Set<Card> blackHand = Stream.of(
    			new Card(Value.TWO, Suit.HEART),
                new Card(Value.THREE, Suit.HEART),
                new Card(Value.FOUR, Suit.HEART),
                new Card(Value.FIVE, Suit.HEART),
                new Card(Value.SIX, Suit.HEART)).collect(Collectors.toSet());
    	Set<Card> whiteHand = Stream.of(
                new Card(Value.FIVE, Suit.CLUB),
                new Card(Value.EIGHT, Suit.DIAMOND),
                new Card(Value.SEVEN, Suit.HEART),
                new Card(Value.SEVEN, Suit.DIAMOND),
                new Card(Value.JACK, Suit.HEART)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
    	Assert.assertEquals(BLACK_WINS_WITH_STRAIGHT_FLUSH, result);
    }

    @Test
    public void winWithFourOfAKindWhenSameCategory() {
    	// black 2C 2D 2H 2S 6H, white 3C 3D 3H 3S 8H
        Set<Card> blackHand = Stream.of(
    			new Card(Value.TWO, Suit.CLUB),
                new Card(Value.TWO, Suit.DIAMOND),
                new Card(Value.TWO, Suit.HEART),
                new Card(Value.TWO, Suit.SPADE),
                new Card(Value.SIX, Suit.HEART)).collect(Collectors.toSet());
    	Set<Card> whiteHand = Stream.of(
                new Card(Value.THREE, Suit.CLUB),
                new Card(Value.THREE, Suit.DIAMOND),
                new Card(Value.THREE, Suit.HEART),
                new Card(Value.THREE, Suit.SPADE),
                new Card(Value.EIGHT, Suit.HEART)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
    	Assert.assertEquals(WHITE_WINS_WITH_FOUR_OF_A_KIND, result);
    }
    
    @Test
    public void winWithFourOfAKindWhenDiffCategory() {
    	// black 5C 5D 4H 4D JH, white 2C 2D 2H 2S 6H, four of a kind vs two pairs  
    	Set<Card> blackHand = Stream.of(
                new Card(Value.FIVE, Suit.CLUB),
                new Card(Value.FIVE, Suit.DIAMOND),
                new Card(Value.FOUR, Suit.HEART),
                new Card(Value.FOUR, Suit.DIAMOND),
                new Card(Value.JACK, Suit.HEART)).collect(Collectors.toSet());
    	Set<Card> whiteHand = Stream.of(
    			new Card(Value.TWO, Suit.CLUB),
                new Card(Value.TWO, Suit.DIAMOND),
                new Card(Value.TWO, Suit.HEART),
                new Card(Value.TWO, Suit.SPADE),
                new Card(Value.SIX, Suit.HEART)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
    	Assert.assertEquals(WHITE_WINS_WITH_FOUR_OF_A_KIND, result);
    }
    
    @Test
    public void whiteWinWithFlushWhenSameCategory() {
    	// black 2H 8H 4H 7H 6H, white 3C 8C 9C 7C JC
        Set<Card> blackHand = Stream.of(
    			new Card(Value.TWO, Suit.HEART),
                new Card(Value.EIGHT, Suit.HEART),
                new Card(Value.FOUR, Suit.HEART),
                new Card(Value.SEVEN, Suit.HEART),
                new Card(Value.SIX, Suit.HEART)).collect(Collectors.toSet());
    	Set<Card> whiteHand = Stream.of(
                new Card(Value.THREE, Suit.CLUB),
                new Card(Value.EIGHT, Suit.CLUB),
                new Card(Value.NINE, Suit.CLUB),
                new Card(Value.SEVEN, Suit.CLUB),
                new Card(Value.JACK, Suit.CLUB)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
    	Assert.assertEquals(WHITE_WINS_WITH_FLUSH, result);
    }
    
    @Test
    public void blackWinWithFlushWhenSameCategory() {
    	// white 2H 8H 4H 7H 6H, black 3C 8C 9C 7C JC
        Set<Card> whiteHand = Stream.of(
    			new Card(Value.TWO, Suit.HEART),
                new Card(Value.EIGHT, Suit.HEART),
                new Card(Value.FOUR, Suit.HEART),
                new Card(Value.SEVEN, Suit.HEART),
                new Card(Value.SIX, Suit.HEART)).collect(Collectors.toSet());
    	Set<Card> blackHand = Stream.of(
                new Card(Value.THREE, Suit.CLUB),
                new Card(Value.EIGHT, Suit.CLUB),
                new Card(Value.NINE, Suit.CLUB),
                new Card(Value.SEVEN, Suit.CLUB),
                new Card(Value.JACK, Suit.CLUB)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
    	Assert.assertEquals(BLACK_WINS_WITH_FLUSH, result);
    }
    
    @Test
    public void winWithFlushWhenDiffCategory() {
        // black 2H 8H 4H 5H 6H, white 7H 8S 9H 10H JH, Flush vs Straight
        Set<Card> blackHand = Stream.of(
    			new Card(Value.TWO, Suit.HEART),
                new Card(Value.EIGHT, Suit.HEART),
                new Card(Value.FOUR, Suit.HEART),
                new Card(Value.FIVE, Suit.HEART),
                new Card(Value.SIX, Suit.HEART)).collect(Collectors.toSet());
    	Set<Card> whiteHand = Stream.of(
                new Card(Value.SEVEN, Suit.HEART),
                new Card(Value.EIGHT, Suit.SPADE),
                new Card(Value.NINE, Suit.HEART),
                new Card(Value.TEN, Suit.HEART),
                new Card(Value.JACK, Suit.HEART)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
    	Assert.assertEquals(BLACK_WINS_WITH_FLUSH, result);
    }
    
    @Test
    public void winWithStraightWhenRankedByHighestAndSameCategory() {
    	// black 2H 3S 4H 5H 6H, white 7H 8S 9H 10H JH
        Set<Card> blackHand = Stream.of(
    			new Card(Value.TWO, Suit.HEART),
                new Card(Value.THREE, Suit.SPADE),
                new Card(Value.FOUR, Suit.HEART),
                new Card(Value.FIVE, Suit.HEART),
                new Card(Value.SIX, Suit.HEART)).collect(Collectors.toSet());
    	Set<Card> whiteHand = Stream.of(
                new Card(Value.SEVEN, Suit.HEART),
                new Card(Value.EIGHT, Suit.SPADE),
                new Card(Value.NINE, Suit.HEART),
                new Card(Value.TEN, Suit.HEART),
                new Card(Value.JACK, Suit.HEART)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
    	Assert.assertEquals(WHITE_WINS_WITH_STRAIGHT, result);
    }
    
    @Test
    public void winWithThreeOfAKindWhenSameCategory() {
    	// black 2C 2D 2H 5S 6H, white 3C 3D 3H 7S 8H
        Set<Card> blackHand = Stream.of(
    			new Card(Value.TWO, Suit.CLUB),
                new Card(Value.TWO, Suit.DIAMOND),
                new Card(Value.TWO, Suit.HEART),
                new Card(Value.FIVE, Suit.SPADE),
                new Card(Value.SIX, Suit.HEART)).collect(Collectors.toSet());
    	Set<Card> whiteHand = Stream.of(
                new Card(Value.THREE, Suit.CLUB),
                new Card(Value.THREE, Suit.DIAMOND),
                new Card(Value.THREE, Suit.HEART),
                new Card(Value.SEVEN, Suit.SPADE),
                new Card(Value.EIGHT, Suit.HEART)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
    	Assert.assertEquals(WHITE_WINS_WITH_THREE_OF_A_KIND, result);
    }
    
    @Test
    public void whiteWinWithTwoPairsRankedByHighestPairWhenSameCategory() {
    	// black 2H 2S 3S 3C 5H, white 2C 2D 4H 4D JH
        Set<Card> blackHand = Stream.of(
    			new Card(Value.TWO, Suit.HEART),
                new Card(Value.TWO, Suit.SPADE),
                new Card(Value.THREE, Suit.SPADE),
                new Card(Value.THREE, Suit.CLUB),
                new Card(Value.FIVE, Suit.HEART)).collect(Collectors.toSet());
    	Set<Card> whiteHand = Stream.of(
                new Card(Value.TWO, Suit.CLUB),
                new Card(Value.TWO, Suit.DIAMOND),
                new Card(Value.FOUR, Suit.HEART),
                new Card(Value.FOUR, Suit.DIAMOND),
                new Card(Value.JACK, Suit.HEART)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
    	Assert.assertEquals(WHITE_WINS_WITH_TWO_PAIRS, result);
    }
    
    @Test
    public void blackWinWithTwoPairsRankedByHighestPairWhenSameCategory() {
    	// white 2H 2S 3S 3C 5H, black 2C 2D 4H 4D JH
        Set<Card> whiteHand = Stream.of(
    			new Card(Value.TWO, Suit.HEART),
                new Card(Value.TWO, Suit.SPADE),
                new Card(Value.THREE, Suit.SPADE),
                new Card(Value.THREE, Suit.CLUB),
                new Card(Value.FIVE, Suit.HEART)).collect(Collectors.toSet());
    	Set<Card> blackHand = Stream.of(
                new Card(Value.TWO, Suit.CLUB),
                new Card(Value.TWO, Suit.DIAMOND),
                new Card(Value.FOUR, Suit.HEART),
                new Card(Value.FOUR, Suit.DIAMOND),
                new Card(Value.JACK, Suit.HEART)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
    	Assert.assertEquals(BLACK_WINS_WITH_TWO_PAIRS, result);
    }
    
    @Test
    public void whiteWinWithTwoPairsWhenSameHighestPairAndSameCategory() {
    	// black 2H 2S 4S 4C 5H, white 3C 3D 4H 4D JH
        Set<Card> blackHand = Stream.of(
    			new Card(Value.TWO, Suit.HEART),
                new Card(Value.TWO, Suit.SPADE),
                new Card(Value.FOUR, Suit.SPADE),
                new Card(Value.FOUR, Suit.CLUB),
                new Card(Value.FIVE, Suit.HEART)).collect(Collectors.toSet());
    	Set<Card> whiteHand = Stream.of(
                new Card(Value.THREE, Suit.CLUB),
                new Card(Value.THREE, Suit.DIAMOND),
                new Card(Value.FOUR, Suit.HEART),
                new Card(Value.FOUR, Suit.DIAMOND),
                new Card(Value.JACK, Suit.HEART)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
    	Assert.assertEquals(WHITE_WINS_WITH_TWO_PAIRS, result);
    }
    
    @Test
    public void blackWinWithTwoPairsWhenSameHighestPairAndSameCategory() {
    	// white 2H 2S 4S 4C 5H, black 3C 3D 4H 4D JH
        Set<Card> whiteHand = Stream.of(
    			new Card(Value.TWO, Suit.HEART),
                new Card(Value.TWO, Suit.SPADE),
                new Card(Value.FOUR, Suit.SPADE),
                new Card(Value.FOUR, Suit.CLUB),
                new Card(Value.FIVE, Suit.HEART)).collect(Collectors.toSet());
    	Set<Card> blackHand = Stream.of(
                new Card(Value.THREE, Suit.CLUB),
                new Card(Value.THREE, Suit.DIAMOND),
                new Card(Value.FOUR, Suit.HEART),
                new Card(Value.FOUR, Suit.DIAMOND),
                new Card(Value.JACK, Suit.HEART)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
    	Assert.assertEquals(BLACK_WINS_WITH_TWO_PAIRS, result);
    }
    
    @Test
    public void whiteWinWithTwoPairsRankedByRemainingCardWhenSameCategory() {
    	// black 2H 2S 4S 4C 5H, white 2C 2D 4H 4D JH
    	Set<Card> blackHand = Stream.of(
    			new Card(Value.TWO, Suit.HEART),
                new Card(Value.TWO, Suit.SPADE),
                new Card(Value.FOUR, Suit.SPADE),
                new Card(Value.FOUR, Suit.CLUB),
                new Card(Value.FIVE, Suit.HEART)).collect(Collectors.toSet());
    	Set<Card> whiteHand = Stream.of(
                new Card(Value.TWO, Suit.CLUB),
                new Card(Value.TWO, Suit.DIAMOND),
                new Card(Value.FOUR, Suit.HEART),
                new Card(Value.FOUR, Suit.DIAMOND),
                new Card(Value.JACK, Suit.HEART)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
    	Assert.assertEquals(WHITE_WINS_WITH_TWO_PAIRS, result);
    }
    
    @Test
    public void blackWinWithTwoPairsRankedByRemainingCardWhenSameCategory() {
    	// white 2H 2S 4S 4C 5H, black 2C 2D 4H 4D JH
    	Set<Card> whiteHand = Stream.of(
    			new Card(Value.TWO, Suit.HEART),
                new Card(Value.TWO, Suit.SPADE),
                new Card(Value.FOUR, Suit.SPADE),
                new Card(Value.FOUR, Suit.CLUB),
                new Card(Value.FIVE, Suit.HEART)).collect(Collectors.toSet());
    	Set<Card> blackHand = Stream.of(
                new Card(Value.TWO, Suit.CLUB),
                new Card(Value.TWO, Suit.DIAMOND),
                new Card(Value.FOUR, Suit.HEART),
                new Card(Value.FOUR, Suit.DIAMOND),
                new Card(Value.JACK, Suit.HEART)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
    	Assert.assertEquals(BLACK_WINS_WITH_TWO_PAIRS, result);
    }
    
    @Test
    public void tieWithTwoPairs() {
    	// black 2C 2D 4H 4D 5C, white 2H 2S 4S 4C 5H
    	Set<Card> blackHand = Stream.of(
                new Card(Value.TWO, Suit.CLUB),
                new Card(Value.TWO, Suit.DIAMOND),
                new Card(Value.FOUR, Suit.HEART),
                new Card(Value.FOUR, Suit.DIAMOND),
                new Card(Value.FIVE, Suit.CLUB)).collect(Collectors.toSet());
    	Set<Card> whiteHand = Stream.of(
    			new Card(Value.TWO, Suit.HEART),
                new Card(Value.TWO, Suit.SPADE),
                new Card(Value.FOUR, Suit.SPADE),
                new Card(Value.FOUR, Suit.CLUB),
                new Card(Value.FIVE, Suit.HEART)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
    	Assert.assertEquals(TIE, result);
    }
    
    @Test
    public void whiteWinWithPairRankedByHighestPairWhenSameCategory() {
    	// black 7H 6S 3S 3C 5H, white 5C 8D 4H 4D JH
        Set<Card> blackHand = Stream.of(
    			new Card(Value.SEVEN, Suit.HEART),
                new Card(Value.SIX, Suit.SPADE),
                new Card(Value.THREE, Suit.SPADE),
                new Card(Value.THREE, Suit.CLUB),
                new Card(Value.FIVE, Suit.HEART)).collect(Collectors.toSet());
    	Set<Card> whiteHand = Stream.of(
                new Card(Value.FIVE, Suit.CLUB),
                new Card(Value.EIGHT, Suit.DIAMOND),
                new Card(Value.FOUR, Suit.HEART),
                new Card(Value.FOUR, Suit.DIAMOND),
                new Card(Value.JACK, Suit.HEART)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
    	Assert.assertEquals(WHITE_WINS_WITH_PAIR, result);
    }
    
    @Test
    public void blackWinWithPairRankedByHighestPairWhenSameCategory() {
    	// white 7H 6S 3S 3C 5H, black 5C 8D 4H 4D JH
        Set<Card> whiteHand = Stream.of(
    			new Card(Value.SEVEN, Suit.HEART),
                new Card(Value.SIX, Suit.SPADE),
                new Card(Value.THREE, Suit.SPADE),
                new Card(Value.THREE, Suit.CLUB),
                new Card(Value.FIVE, Suit.HEART)).collect(Collectors.toSet());
    	Set<Card> blackHand = Stream.of(
                new Card(Value.FIVE, Suit.CLUB),
                new Card(Value.EIGHT, Suit.DIAMOND),
                new Card(Value.FOUR, Suit.HEART),
                new Card(Value.FOUR, Suit.DIAMOND),
                new Card(Value.JACK, Suit.HEART)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
    	Assert.assertEquals(BLACK_WINS_WITH_PAIR, result);
    }
    
    @Test
    public void whiteWinWithPairWhenSameHighestPairAndSameCategory() {
    	// black 7H 6S 4S 4C 5H, white 8C 9D 4H 4D JH
        Set<Card> blackHand = Stream.of(
    			new Card(Value.SEVEN, Suit.HEART),
                new Card(Value.SIX, Suit.SPADE),
                new Card(Value.FOUR, Suit.SPADE),
                new Card(Value.FOUR, Suit.CLUB),
                new Card(Value.FIVE, Suit.HEART)).collect(Collectors.toSet());
    	Set<Card> whiteHand = Stream.of(
                new Card(Value.EIGHT, Suit.CLUB),
                new Card(Value.NINE, Suit.DIAMOND),
                new Card(Value.FOUR, Suit.HEART),
                new Card(Value.FOUR, Suit.DIAMOND),
                new Card(Value.JACK, Suit.HEART)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
    	Assert.assertEquals(WHITE_WINS_WITH_PAIR, result);
    }

    @Test
    public void blackWinWithPairWhenSameHighestPairAndSameCategory() {
    	// white 7H 6S 4S 4C 5H, black 8C 9D 4H 4D JH
        Set<Card> whiteHand = Stream.of(
    			new Card(Value.SEVEN, Suit.HEART),
                new Card(Value.SIX, Suit.SPADE),
                new Card(Value.FOUR, Suit.SPADE),
                new Card(Value.FOUR, Suit.CLUB),
                new Card(Value.FIVE, Suit.HEART)).collect(Collectors.toSet());
    	Set<Card> blackHand = Stream.of(
                new Card(Value.EIGHT, Suit.CLUB),
                new Card(Value.NINE, Suit.DIAMOND),
                new Card(Value.FOUR, Suit.HEART),
                new Card(Value.FOUR, Suit.DIAMOND),
                new Card(Value.JACK, Suit.HEART)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
    	Assert.assertEquals(BLACK_WINS_WITH_PAIR, result);
    }
    
    @Test
    public void tieWithPair() {
    	// black 8C 9D 4H 4D JH, white 8H 9S 4S 4C JC
    	Set<Card> blackHand = Stream.of(
                new Card(Value.EIGHT, Suit.CLUB),
                new Card(Value.NINE, Suit.DIAMOND),
                new Card(Value.FOUR, Suit.HEART),
                new Card(Value.FOUR, Suit.DIAMOND),
                new Card(Value.JACK, Suit.HEART)).collect(Collectors.toSet());
        Set<Card> whiteHand = Stream.of(
    			new Card(Value.EIGHT, Suit.HEART),
                new Card(Value.NINE, Suit.SPADE),
                new Card(Value.FOUR, Suit.SPADE),
                new Card(Value.FOUR, Suit.CLUB),
                new Card(Value.JACK, Suit.CLUB)).collect(Collectors.toSet());
    	blackPlayer = new Player(BLACK, blackHand);
    	whitePlayer = new Player(WHITE, whiteHand);
    	String result = rank.compare(blackPlayer, whitePlayer);
    	Assert.assertEquals(TIE, result);
    }
}