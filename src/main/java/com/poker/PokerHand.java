package com.poker;
import java.util.Arrays;

public class PokerHand 
{
        private Card cards[];
        private int highestFullHouse=0;
        private int highestFourOfAKind=0;
        private int highestFullHouseDouble=0;
        private int highestThreeOfAKind=0;
        private int doubleFirstPair=0;
        private int doubleSecondPair=0;
        private int sideCard=0;
        private int singlePair;
        private int rank;
        
	
	public enum Result 
        {
		WIN,
		LOSS,
		TIE;
	}

	public PokerHand(String hand) 
        {          
            if(hand==null)
            {
                throw new IllegalArgumentException();
            }
            else
            {
                cards= new Card[5];
                String [] split= hand.split(" ");
                determineCards(split);
               
            }
        }
        private void determineCards(String[] split)
        {
            for(int i=0; i<split.length;i++)
            {
                    try 
                    {
                        cards[i]=new Card(split[i]);
                    } 
                    catch (NullPointerException e1) 
                    {
                        System.out.println("Null Exception");
                        throw new NullPointerException();
                    }
                    catch (IllegalArgumentException e2) 
                    {
                        System.out.println("Format Exception");
                        throw new IllegalArgumentException();
                    }
            }
            sortAndRank();
        }
        private void sortAndRank()
        {
            try
            {
                  Arrays.sort(cards); // sort in ascending order
                  this.rank=findRank(PokerHand.this);// find ranking of hand
            }
            catch(NullPointerException e1)
            {
                  System.out.println("Null Exception");
            }
        }
	public Result compareWith(PokerHand hand) 
        {
            //get value of ranking for both card and compare
            int handRank= hand.rank;
            int thisRank=this.rank;
            
            if(handRank<thisRank)//passed hand is a higher rank
            {
                return Result.LOSS;
            }
            else if(handRank>thisRank)// pass hand is a smaller rank
            {
                return Result.WIN;
            }
            else // if both are same rank then we need to break the tie with checking extra conditions
            {
                switch (handRank) 
                {
                    case 1:
                        return Result.TIE;
                    case 2:
                        return checkHighestCard(hand, this);
                    case 3:
                        //could do highestCardWins for else but will take more time as iterating through array
                        if(hand.highestFourOfAKind>this.highestFourOfAKind)
                        {
                            return Result.LOSS;
                        }
                        else if(hand.highestFourOfAKind<this.highestFourOfAKind)
                        {
                            return Result.WIN;
                        }
                        else
                        {       //check which side card has higher value                    
                                if(hand.sideCard>this.sideCard)
                                {
                                    return Result.LOSS;
                                }
                                else if(hand.sideCard<this.sideCard)
                                {
                                    return Result.WIN;
                                }
                                else
                                {
                                    return Result.TIE;
                                } 
                        }
                    case 4:
                        if(hand.highestFullHouse>this.highestFullHouse)
                        {
                            return Result.LOSS;
                        }
                        else if(hand.highestFullHouse<this.highestFullHouse)
                        {
                            return Result.WIN;
                        }
                        else
                        {
                            if(hand.highestFullHouseDouble>this.highestFullHouseDouble)
                            {
                                return Result.LOSS;
                            }
                            else if(hand.highestFullHouseDouble<this.highestFullHouseDouble)
                            {
                                return Result.WIN;
                            }
                            else
                            {
                                return Result.TIE;
                            }                                
                        }
                    case 5:
                        //highest value card wins, can go to 2nd, 3rd etc...
                        return highestCardWins(hand,this);
                    case 6:
                        //highest value card wins only 1st
                        return checkHighestCard(hand, this);
                    case 7:
                        if(hand.highestThreeOfAKind>this.highestThreeOfAKind)
                        {
                            return Result.LOSS;
                        }
                        else if(hand.highestThreeOfAKind<this.highestThreeOfAKind)
                        {
                            return Result.WIN;
                        }
                        else
                        {
                            return highestCardWins(hand,this);
                        }
                    case 8:
                        if(hand.doubleFirstPair>this.doubleFirstPair)
                        {
                            return Result.LOSS;
                        }
                        else if(hand.doubleFirstPair<this.doubleFirstPair)
                        {
                            return Result.WIN;
                        }
                        else
                        {
                            if(hand.doubleSecondPair>this.doubleSecondPair)
                            {
                                return Result.LOSS;
                            }
                            else if(hand.doubleSecondPair<this.doubleSecondPair)
                            {
                                return Result.WIN;
                            }
                            else
                            {
                                return highestCardWins(hand,this);
                            }                        
                        }
                    case 9:
                        if (hand.singlePair>this.singlePair)
                        {
                            return Result.LOSS;
                        }
                        else if (hand.singlePair<this.singlePair)
                        {
                            return Result.WIN;
                        }
                        else
                        {
                            return highestCardWins(hand, this);
                        }
                    case 10:
                        return highestCardWins(hand, this);                            
                   
                }       
            }
            
            return Result.TIE;
	}
        
       private Result highestCardWins(PokerHand hand, PokerHand currentHand)
       {
           //find highestCard to fold
          for(int i=hand.cards.length-1; i>=0; i--)
            {
                if(hand.cards[i].getValue()>currentHand.cards[i].getValue())
                {
                    return Result.LOSS;
                }
                else if(hand.cards[i].getValue()<currentHand.cards[i].getValue())
                {
                    return Result.WIN;
                }
            }
          return Result.TIE; 
       }
       
        private Result checkHighestCard(PokerHand hand, PokerHand currentHand)
        {
            if(hand.cards[4].getValue()>currentHand.cards[4].getValue())
            {
               
                return Result.LOSS;
            }
            else if(hand.cards[4].getValue()<currentHand.cards[4].getValue())
            {
                
                return Result.WIN;
            }
            else
            {
                 
                return Result.TIE;
            }
        }
        
        private int findRank(PokerHand hand)
        {
             if(isRoyalFlush(hand))
            {   
                return 1;
            }
            else if(isStraightFlush(hand))
            {
                return 2;
            }
            else if(isFourOfAKind(hand))
            {
                return 3;
            }
            else if(isFullHouse(hand))
            {
                return 4;
            }
            else if(isFlush(hand))
            {
                return 5;
            }
            else if(isStraight(hand))
            {
                return 6;
            }
            else if(isThreeOfAKind(hand))
            {
                return 7;
            }
            else if(isTwoPair(hand))
            {
                return 8;
            }
            else if(isPair(hand))
            {
                return 9;
            }
            else
            {
                return 10;
            }
        }
        
        private boolean isRoyalFlush(PokerHand hand)
        {
          // the highest hand - straight from 10 to an Ace
         int target[]={10,11,12,13,14};
         String targetSuit=hand.cards[0].getSuit();
         for(int i=0;i<hand.cards.length;i++) 
         {
             if(hand.cards[i].getValue()!=target[i] || !hand.cards[i].getSuit().equals(targetSuit))
             {
                 return false;
             }
          
         }
         return true;
        }
        
        private boolean isStraightFlush(PokerHand hand)
        {
            //cards in ascending numerical order of the same suit 
            String targetSuit=hand.cards[0].getSuit();
            int targetValue=hand.cards[0].getValue();
            
            for(int i=1 ; i< hand.cards.length ; i++)
            {
                targetValue++;
                if(!hand.cards[i].getSuit().equals(targetSuit))
                {
                    return false;
                }
                if(hand.cards[i].getValue()!=targetValue)
                {
                    return false;
                }
            }
            
            return true;
        }
        
       private boolean isFourOfAKind(PokerHand hand)
       {
           //record first two cards and then create a count of how many cards of each values there are
           int firstValue=hand.cards[0].getValue();
           int secondValue=hand.cards[1].getValue();
           int count=1;
           
           if(firstValue==secondValue)
           {
               count++;
           }
           else
           {
               sideCard=firstValue;
           }
           
           for(int i=2; i<hand.cards.length;i++)
           {
               if(hand.cards[i].getValue()==firstValue )
               {
                   if(sideCard==firstValue)
                   {
                       sideCard=secondValue;
                   }
                   highestFourOfAKind=firstValue;
                   count++;
               }
               else if(hand.cards[i].getValue()==secondValue )
               {
                   highestFourOfAKind=secondValue;
                   count++;
               }
               else
               {
                   sideCard=hand.cards[i].getValue();
               }
           }
           
           // if we have a count of 4 that signifies four of the same card
           return count==4;
       }
       
       private boolean isFullHouse(PokerHand hand)
       {
           //record the first 3 cards and then keep count of each card
           int firstValue=hand.cards[0].getValue();
           int secondValue=hand.cards[1].getValue();
           int thirdValue=hand.cards[2].getValue();
           int firstCount=1;
           int secondCount=1;
           int thirdCount=1;
           
           //check if any of first 3 values are equal to each other
           if(firstValue==secondValue)
           {
              firstCount++;
           }
           else if(firstValue==thirdValue)
           {
              firstCount++;
           }
           else if(secondValue==thirdValue)
           {
              secondCount++;
           }
           
           for(int i=3; i<hand.cards.length;i++)
           {
               if(hand.cards[i].getValue()==firstValue)
               {
                   firstCount++;
               }
               else if(hand.cards[i].getValue()==secondValue)
               {
                   secondCount++;
               }
               else if(hand.cards[i].getValue()==thirdValue)
               {
                   thirdCount++;
               }
           }
           
           // we are looking for two counts of 3 and 2 signifying a triple card and pair card
           if(firstCount==3 && (secondCount==2 || thirdCount==2))
           {
               if(secondCount==2)
               {
                  hand.highestFullHouseDouble=secondValue; 
               }
               else
               {
                  hand.highestFullHouseDouble=thirdValue;      
               }
   
               hand.highestFullHouse=firstValue;
               return true;
           }
           else if(secondCount==3 && (firstCount==2 || thirdCount==2))
           {
               if(firstCount==2)
               {
                  hand.highestFullHouseDouble=firstValue; 
               }
               else
               {
                  hand.highestFullHouseDouble=thirdValue;      
               }
               
               hand.highestFullHouse=secondValue;
               return true;
           }
           else if(thirdCount==3 && (secondCount==2 || firstCount==2))
           {
               if(secondCount==2)
               {
                  hand.highestFullHouseDouble=secondValue; 
               }
               else
               {
                  hand.highestFullHouseDouble=firstValue;      
               }
               hand.highestFullHouse=thirdValue;
               return true;
           }
           else
           {
               return false;
           }
          
       }
       
       private boolean isFlush(PokerHand hand)
       {
           // five cards of the same suit, no order
            String targetSuit=hand.cards[0].getSuit();
            
            for(int i=1 ; i< hand.cards.length ; i++)
            {
    
                if(!hand.cards[i].getSuit().equals(targetSuit))
                {
                    return false;
                }
           
            }
            
            return true;
       }
       
       private boolean isStraight(PokerHand hand)
       {
          
            int targetValue=hand.cards[0].getValue();
            //EXTRA CONDITION to allow starting from Ace
            //cards in ascending order with no suit requirement
            if(targetValue==14)
            {
                targetValue=1;
            }
            
            for(int i=1; i< hand.cards.length; i++)
            {
                targetValue++;           
                if(hand.cards[i].getValue()!=targetValue)
                {
                    return false;
                }
            }
            
            return true;
       }
       
       private boolean isThreeOfAKind(PokerHand hand)
       {
           //similar to full house but in this case we do not have a double pair
           //keep track of first 3 cards, check if any of them are equal to each other and determine if a count of 3 exists which signifies a triple card
           int firstValue=hand.cards[0].getValue();
           int secondValue=hand.cards[1].getValue();
           int thirdValue=hand.cards[2].getValue();
           int firstCount=1;
           int secondCount=1;
           int thirdCount=1;
           
           if(firstValue==secondValue)
           {
              firstCount++;
           }
           if(firstValue==thirdValue)
           {
              firstCount++;
           }
           else if(secondValue==thirdValue)
           {
              secondCount++;
           }
           
           for(int i=3; i<hand.cards.length;i++)
           {
               if(hand.cards[i].getValue()==firstValue)
               {
                   firstCount++;
               }
               else if(hand.cards[i].getValue()==secondValue)
               {
                   secondCount++;
               }
               else if(hand.cards[i].getValue()==thirdValue)
               {
                   thirdCount++;
               }
           }
           
           if(firstCount==3)
           {
               hand.highestThreeOfAKind=firstValue;
               return true;
           }
           else if(secondCount==3)
           {
               hand.highestThreeOfAKind=secondValue;
               return true;
           }
           else if(thirdCount==3)
           {
               hand.highestThreeOfAKind=thirdValue;
               return true;
           }
           else
           {
               return false;
           }
       }
       
       private boolean isTwoPair(PokerHand hand)
       {
          // double pairs of cards , keep record of pairs for comparison in cases of tie
          int count=0;
          int firstPair=0;
          int secondPair=0;
          
          
          for(int i=0 ; i<hand.cards.length; i++)
          {
              for(int j=i+1; j<hand.cards.length; j++)
              {
                  if(hand.cards[i].getValue()==hand.cards[j].getValue())
                  {    
                      if(firstPair==0)
                      {
                          firstPair=hand.cards[i].getValue();
                      }
                      else if(secondPair==0)
                      {
                           secondPair=hand.cards[i].getValue();
                      }
                      count++;
                  }
              }
          }
          
          if(firstPair>secondPair)
          {
              doubleFirstPair=firstPair;
              doubleSecondPair=secondPair;
          }
          else
          {
              doubleFirstPair=secondPair;
              doubleSecondPair=firstPair;
          }
          
          //count of 2 signifies 2 pairs of cards found
          return count==2;
       }
       
       private boolean isPair(PokerHand hand)
       {
          // one single pair of card
          int count=0;
          
          for(int i=0 ; i<hand.cards.length; i++)
          {
              for(int j=i+1; j<hand.cards.length; j++)
              {
                  if(hand.cards[i].getValue()==hand.cards[j].getValue())
                  {
                      count++;
                      singlePair=hand.cards[i].getValue();
                  }
                 
              }
          }
            
          return count==1;
       }
      
       public void printCards()
       {
           try
           {
                for(Card card : cards)
                {
                    System.out.print(card.getValue()+card.getSuit()+" ");
                }
                System.out.println();
           }
           catch(NullPointerException e1)
           {
                System.out.println("Null error in printing array");
           }
       }
        
        public static void main(String[] args) 
        {
           PokerHand hand1 = new PokerHand("Ts Js 5s Ks Qs");
           PokerHand hand2 = new PokerHand("Ts Js Qs Ks Qs");
           
           hand1.printCards();
           hand2.printCards();
           
           System.out.println(hand1.compareWith(hand2));
           
        }
}
