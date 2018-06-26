/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poker;

/**
 *
 * @author pj
 */
public class Card implements Comparable<Card>
{
    private String suit;
    private int value;
     
    public Card(String card)
    {        
          String [] split= card.split("");
          if (split==null)
          {
              throw new NullPointerException();
          }
          else
          {
               if(card.matches("((\\d||T||J||K||Q||A)(s||d||c||h))*"))
               {
                    this.value=calculateValue(split[0]);
                    this.suit=split[1];
               }
               else
               {
                   throw new IllegalArgumentException();
               }
             
          }
    }
    
    public String getSuit()
    {
        return suit;
    }
    
    public int getValue()
    {
        return value;
    } 
    
    private int calculateValue(String value)
    {
        switch (value) 
        {
            case "J":
                return 11;       
            case "Q":
                return 12;
            case "K":
                return 13;
            case "A":
                return 14;
            case "T":
                return 10;
            default:
                try
                {
                    return Integer.parseInt(value);
                }
                catch(NumberFormatException e1)
                {
                    throw new IllegalArgumentException();
                }
        }
    }
    @Override
    public int compareTo(Card card) 
    {
		int compareQuantity = card.getValue(); 
		//ascending order
		return this.value - compareQuantity;	
    }	
}
