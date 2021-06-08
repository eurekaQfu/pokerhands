package com.sgcib.pokerhands.demo;

import org.junit.Before;
import org.junit.Test;

import com.sgcib.pokerhands.demo.PokerHands;

import junit.framework.Assert;
/**
 * Unit test for poker hands
 * clubs, diamonds, hearts, or spades denoted C, D, H, and S
 * @author Qiang FU
 */
public class PokerHandsTest {
	PokerHands poker;
	
	@Before
    public void setUp(){
		poker = new PokerHands();
	}
    
    @Test
    public void winWithHighCardWhenHighestCardIsAceAndSameCategory() {
    	String result = poker.compare("2H 3D 5S 9C KD", "2C 3H 4S 8C AH");
        Assert.assertEquals("White wins. - with high card: Ace", result);
    }
    
    @Test
    public void winWithFullHouseWhenDiffCategory() {
    	// full house vs flush
    	String result = poker.compare("2H 4S 4C 2D 4H", "2S 8S AS QS 3S");
        Assert.assertEquals("Black wins. - with full house: 4 over 2", result);
    }
    
    @Test
    public void winWithFullHouseWhenSameCategory() {
    	String result = poker.compare("2H 4S 4C 2D 4H", "2C 3S 3C 3D 2S");
        Assert.assertEquals("Black wins. - with full house", result);
    }
    
    @Test
    public void winWithHighCardWhenHighestCardIsNineAndSameCategory() {
    	String result = poker.compare("2H 3D 5S 9C KD", "2C 3H 4S 8C KH");
        Assert.assertEquals("Black wins. - with high card: 9", result);
    }
    
    @Test
    public void tieWithHighCardWhenSameCategory() {
    	String result = poker.compare("2H 3D 5S 9C KD", "2D 3H 5C 9S KH");
        Assert.assertEquals("Tie.", result);
    }
    
    @Test
    public void winWithStraightFlushRankedByHighestWhenSameCategory() {
        String result = poker.compare("2H 3H 4H 5H 6H", "7H 8H 9H 10H JH");
        Assert.assertEquals("White wins. - with straight flush", result);
    }
    
    @Test
    public void winWithStraightFlushWhenDiffCategory() {
    	// straight flush vs pair
        String result = poker.compare("2H 3H 4H 5H 6H", "5C 8D 7H 7D JH");
        Assert.assertEquals("Black wins. - with straight flush", result);
    }

    @Test
    public void winWithFourOfAKindWhenSameCategory() {
    	String result = poker.compare("2C 2D 2H 2S 6H", "3C 3D 3H 3S 8H");
        Assert.assertEquals("White wins. - with four of a kind", result);
    }
    
    @Test
    public void winWithFourOfAKindWhenDiffCategory() {
    	// four of a kind vs two pairs
    	String result = poker.compare("2C 2D 2H 2S 6H", "5C 5D 4H 4D JH");
        Assert.assertEquals("Black wins. - with four of a kind", result);
    }
    
    @Test
    public void winWithFlushWhenSameCategory() {
    	String result = poker.compare("2H 8H 4H 7H 6H", "3C 8C 9C 7C JC");
        Assert.assertEquals("White wins. - with flush", result);
    }
    
    @Test
    public void winWithFlushWhenDiffCategory() {
    	// Flush vs Straight
    	String result = poker.compare("2H 8H 4H 7H 6H", "7H 8S 9H 10H JH");
        Assert.assertEquals("Black wins. - with flush", result);
    }
    
    @Test
    public void winWithStraightWhenRankedByHighestAndSameCategory() {
        String result = poker.compare("2H 3S 4H 5H 6H", "7H 8S 9H 10H JH");
        Assert.assertEquals("White wins. - with straight", result);
    }
    
    @Test
    public void winWithThreeOfAKindWhenSameCategory() {
    	String result = poker.compare("2C 2D 2H 5S 6H", "3C 3D 3H 7S 8H");
        Assert.assertEquals("White wins. - with three of a kind", result);
    }
    
    @Test
    public void winWithTwoPairsRankedByHighestPairWhenSameCategory() {
    	String result = poker.compare("2H 2S 3S 3C 5H", "2C 2D 4H 4D JH");
        Assert.assertEquals("White wins. - with two pairs", result);
    }
    
    @Test
    public void winWithTwoPairsWhenSameHighestPairAndSameCategory() {
    	String result = poker.compare("2H 2S 4S 4C 5H", "3C 3D 4H 4D JH");
        Assert.assertEquals("White wins. - with two pairs", result);
    }
    
    @Test
    public void winWithTwoPairsRankedByRemainingCardWhenSameCategory() {
    	String result = poker.compare("2H 2S 4S 4C 5H", "2C 2D 4H 4D JH");
        Assert.assertEquals("White wins. - with two pairs", result);
    }
    
    @Test
    public void winWithPairRankedByHighestPairWhenSameCategory() {
    	String result = poker.compare("7H 6S 3S 3C 5H", "5C 8D 4H 4D JH");
        Assert.assertEquals("White wins. - with pair", result);
    }
    
    @Test
    public void winWithPairWhenSameHighestPairAndSameCategory() {
    	String result = poker.compare("7H 6S 4S 4C 5H", "8C 9D 4H 4D JH");
        Assert.assertEquals("White wins. - with pair", result);
    }
    
}