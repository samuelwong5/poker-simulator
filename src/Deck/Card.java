package Deck;

import java.util.ArrayList;
import java.util.List;

public class Card {
    private Rank rank;                                                // Rank of the card
    private Suit suit;                                                // Suit of the card
    public Card(Rank r, Suit s){                                      // Constructor
    	this.rank = r;
    	this.suit = s;
    }
    public Rank getRank(){                                            // Return the rank
    	return rank;
    }
    public Suit getSuit(){                                            // Return the suit
    	return suit;
    }
    @Override
    public boolean equals(Object obj){                                // Override the equals method;
    	if(obj==this){                                                // Two cards are equal if they have
    		return true;                                              // the same rank and suit
    	} else if (obj==null){
    		return false;
    	} else if (obj.getClass()==getClass()){
    		Card hold = (Card) obj;
    		return (hold.getRank()==rank && hold.getSuit()==suit);
    	} else {
    		return false;
    	}
    }
    
    @Override
    public int hashCode(){                                            // Override the hashcode method
    	return rank.getInt(rank) * 3 + suit.getInt(suit);
    }
    public String toString(){                                         // Convert to String               
    	return rank.toString(rank) + suit.toString(suit);
    }
    
    public List<Integer> getInt(){                                    // Returns 1 and 14 if rank is A; the value of the rank otherwise
    	if(rank==Rank.ACE){                                           // 1 and 14 useful to calculate straights (A-2-3-4-5 , 10-J-Q-K-A)
    		List<Integer> result = new ArrayList<Integer>();
    		result.add(1);
    		result.add(14);
    		return result;
    	} else {
    		List<Integer> result = new ArrayList<Integer>();
    		result.add(rank.getInt(rank));
    		return result;
    	}
    }
    public int toInt(){                                               // Returns the value of the rank - 1 
    	return rank.getInt(rank)-1;                                   // 2 -> 1, 3 -> 2 ... Q -> 11, K -> 12, A -> 13
    }
}
