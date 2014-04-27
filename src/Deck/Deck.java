package Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class Deck {
    private List<Card> deck;                                // Deck of cards
    public Deck(){                                          // Constructor
    	deck = new ArrayList<Card>(52);                     // Initializes a deck of 52 cards in order
    	for(Rank r : Rank.values()){
    		for(Suit s : Suit.values()){
    			deck.add(new Card(r, s));
    		}
    	}
    }
    
    public Card getCard(){                                  // Get the card on the top of the deck
    	if(deck.isEmpty()){
    	    throw new NoSuchElementException();	
    	}
    	return deck.remove(0);
    }
    
    public void shuffle(){                                  // Shuffles the deck
    	Random rnd = new Random();
    	List<Card> newDeck = new ArrayList<Card>(size());
    	while(!deck.isEmpty()){
    		newDeck.add(deck.remove(rnd.nextInt(size())));
    	}
    	deck = newDeck;
    }
    
    public int size(){                                      // Returns the size of the deck
    	return deck.size();
    }
    
    public String toString(){                               
    	StringBuilder sb = new StringBuilder();
    	sb.append("{");
    	for(int i=0; i<size(); i++){
    		sb.append(deck.get(i).toString());
    		if(!(i==size()-1)){
    			sb.append(",");
    		}
    	}
    	sb.append("}");
    	return sb.toString();
    }
}
