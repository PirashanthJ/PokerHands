package com.poker;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.poker.PokerHand.Result;
import java.io.ByteArrayOutputStream;

/**
 * Unit test for simple App.
 */
public class PokerHandTest 
{
	@Test (expected = IllegalArgumentException.class)
	public void newHandTestNull() 
        {
                new PokerHand(null);
	}
        @Test (expected = IllegalArgumentException.class)
        public void invalidInput() 
        {	
                //small "a" as opposed to "A"
                new PokerHand("as 2h 5c Jd Td");
    	}
        @Test (expected = IllegalArgumentException.class)
        public void invalidInput2() 
        {	
                //empty string input
                new PokerHand("");
    	}
        @Test (expected = IllegalArgumentException.class)
        public void invalidInput3() 
        {	
                //random or invalid input
                new PokerHand("sgrgegeg");
    	}
	@Test
	public void highCardWin() 
        {
		PokerHand hand1 = new PokerHand("As 2h 5c Jd Td");
		PokerHand hand2 = new PokerHand("Kc 2s 5h Jh Tc");

		assertEquals(Result.WIN, hand1.compareWith(hand2));
		assertEquals(Result.LOSS, hand2.compareWith(hand1));
	}
        
        @Test
        public void royalFlush()
        {
                PokerHand hand1 = new PokerHand("Ts Js As Ks Qs");
		PokerHand hand2 = new PokerHand("Kc 2s 5h Jh Tc");
                
                //RoyalFlush wins
		assertEquals(Result.WIN, hand1.compareWith(hand2));
                
                //RoyalFlush draw
                hand2 = new PokerHand("Ts Js Qs Ks As");
		assertEquals(Result.TIE, hand2.compareWith(hand1));
                
                //RoyalFlush different suit
                hand1 = new PokerHand("Tc Js As Ks Qs");
                assertEquals(Result.LOSS, hand1.compareWith(hand2));
        }
        @Test
        public void straightFlush()
        {
                PokerHand hand1 = new PokerHand("5s 6s 7s 8s 9s");
		PokerHand hand2 = new PokerHand("Kd 2s 5h Jh Tc");
                
                // WIN
                assertEquals(Result.WIN, hand1.compareWith(hand2));
                
                //LOSS
                hand2 = new PokerHand("6s 7s 8s 9s Ts");
                assertEquals(Result.LOSS, hand1.compareWith(hand2));
                
                //different suit
                hand2 = new PokerHand("6d 7s 8s 9s Ts");
                assertEquals(Result.WIN, hand1.compareWith(hand2));
                
        }
        @Test 
        public void fourOfAKind()
        {
            PokerHand hand1 = new PokerHand("As Ac Ad Ah 9s");
            PokerHand hand2 = new PokerHand("Kd 2s 5h Jh Tc");
            
            //WIN
            assertEquals(Result.WIN, hand1.compareWith(hand2));
            
            //LOSS, highest kind
            hand2 = new PokerHand("Ks Kc Kd Kh 9s");
            assertEquals(Result.LOSS, hand2.compareWith(hand1));
            
            //WIN, highest side card
            hand2 = new PokerHand("As Ac Ad Ah Ts");
            assertEquals(Result.WIN, hand2.compareWith(hand1));
        }
        @Test
        public void fullHouse ()
        {
            PokerHand hand1 = new PokerHand("Ks Kc Kd Jh Js");
            PokerHand hand2 = new PokerHand("Qd Qs 5h Th Tc");
            
            //WIN
            assertEquals(Result.WIN, hand1.compareWith(hand2));
            
            //LOSS, highest three
            hand2 = new PokerHand("Ad As Ah Th Tc");
            assertEquals(Result.LOSS, hand1.compareWith(hand2));
            
            //LOSS, highest two matching cards
            hand2 = new PokerHand("Kd Ks Kh Qh Qc");
            assertEquals(Result.LOSS, hand1.compareWith(hand2));
            
            //TIE
            hand2 = new PokerHand("Ks Kc Kd Jh Js");
            assertEquals(Result.TIE, hand1.compareWith(hand2));
            
        }
        @Test
        public void flush()
        {
            PokerHand hand1 = new PokerHand("5s 6s 7s As Qs");
            PokerHand hand2 = new PokerHand("5d 6s 7h 8h 9c");
            
            //WIN
            assertEquals(Result.WIN, hand1.compareWith(hand2));
            
            //LOSS
            hand2 = new PokerHand("Ad As Ah 6s Ac");
            assertEquals(Result.LOSS, hand1.compareWith(hand2));
            
            //WIN, highest ranked card
            hand2 = new PokerHand("5s 6s 7s Qs Ks");
            assertEquals(Result.WIN, hand1.compareWith(hand2));
            
            //TIE
            hand2 = new PokerHand("5s 6s 7s As Qs");
            assertEquals(Result.TIE, hand1.compareWith(hand2));
            
        }
        @Test
        public void straight()
        {
            PokerHand hand1 = new PokerHand("6d 7s 8c 9s Ts");
            PokerHand hand2 = new PokerHand("Jd Js Jh 8h 9c");
            
             //WIN
            assertEquals(Result.WIN, hand1.compareWith(hand2));
            
            //LOSS
            hand2 = new PokerHand("Ad As Ah 6s Ac");
            assertEquals(Result.LOSS, hand1.compareWith(hand2));
            
            //TIE
            hand2 = new PokerHand("6d 7s 8c 9s Ts");
            assertEquals(Result.TIE, hand1.compareWith(hand2));
            
            //WIN, highest card
            hand2 = new PokerHand("5d 6s 7c 8s 9s");
            assertEquals(Result.WIN, hand1.compareWith(hand2));
            
        }
        @Test
        public void threeOfAKind()
        {
            PokerHand hand1 = new PokerHand("6d 6s 6c 9s Ts");
            PokerHand hand2 = new PokerHand("Jd Js 8h 5h 9c");
            
            //WIN
             assertEquals(Result.WIN, hand1.compareWith(hand2));
             
            //LOSS
            hand2 = new PokerHand("Ad As Ah 6s Ac");
            assertEquals(Result.LOSS, hand1.compareWith(hand2));
            
            //LOSS, highestRanking
            hand2 = new PokerHand("7d 7s 7c 9s Ts");
            assertEquals(Result.LOSS, hand1.compareWith(hand2));
            
            //LOSS, SIDE CARD
             hand2 = new PokerHand("6d 6s 6c 9s As");
             assertEquals(Result.LOSS, hand1.compareWith(hand2));
            
        }
        @Test
        public void twoPairs()
        {
            PokerHand hand1 = new PokerHand("Td Ts 6c 6s Ks");
            PokerHand hand2 = new PokerHand("Ad As 8h 6h 2c");
            
             //WIN
             assertEquals(Result.WIN, hand1.compareWith(hand2));
             
             //LOSS, Side card
             hand2 = new PokerHand("Td Ts 6c 6s As");
             assertEquals(Result.LOSS, hand1.compareWith(hand2));
             
             //Tie
             hand2 = new PokerHand("Td Ts 6c 6s Ks");
             assertEquals(Result.TIE, hand1.compareWith(hand2));
                
        }
        @Test
        public void onePair()
        {
             PokerHand hand1 = new PokerHand("Ad As 8c 6s 2s");
             PokerHand hand2 = new PokerHand("Kd Js Th 6h 2c");
             
             //WIN
             assertEquals(Result.WIN, hand1.compareWith(hand2));
             
             //LOSS, side card
             hand2 = new PokerHand("Ad As 8c 6s Ks");
             assertEquals(Result.LOSS, hand1.compareWith(hand2));
             
             //TIE
             hand2 = new PokerHand("Ad As 8c 6s 2s");
             assertEquals(Result.TIE, hand1.compareWith(hand2));
        }
        @Test 
        public void highestCard()
        {
             PokerHand hand1 = new PokerHand("Kd Js Th 6h Ac");
             PokerHand hand2 = new PokerHand("Kd Js Th 6h 2c");
             
             //WIN
             assertEquals(Result.WIN, hand1.compareWith(hand2));
             
             //LOSS
             hand2 = new PokerHand("Kd Js Th Ah Ac");
             assertEquals(Result.LOSS, hand1.compareWith(hand2));
             
             //TIE
             hand2 = new PokerHand("Kd Js Th 6h Ac");
             assertEquals(Result.TIE, hand1.compareWith(hand2));
        }
        
}
