package Table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import Util.UtilityMethods;
import Deck.Card;
import Deck.Suit;
/**
 * An implementation of a player in a Poker game
 * @author Samuel Wong 2014
 */
public class Player {
    private Table table;          // The table this player belongs to
	private Card cardOne;         // First card given to the player
    private Card cardTwo;         // Second card given to the player

    /**
     * Constructor of Player
     * @param table - the table this player instance belongs to
     */
    public Player(Table table){   
    	this.table = table;
    }
    
    /**
     * Simulates a player receiving a card from the dealer
     * @param c - the card received
     */
    public void receiveCard(Card c){
    	if (cardOne==null){
    		cardOne = c;
    	} else if (cardTwo==null){
    		cardTwo = c;
    	} else {
    	    // Obligatory exception handling	
    	}
    }
    
    /**
     * Gets the players hand (two hards)
     * @return a set of cards containing two cards
     */
    public Set<Card> getCards(){
    	Set<Card> s = new HashSet<Card>();
    	s.add(cardOne);
    	s.add(cardTwo);
    	return s;
    }
    
    /**
     * @return a reference to the table this player belongs to
     */
    public Table getTable(){
    	return table;
    }
    
    /**
     * @return a string of the two cards in the player's hand
     */
    public String toString(){
    	return cardOne.toString() + ", " + cardTwo.toString();
    }
    
    /**
     * Calculates the 'score' of a hand which is a multiple of 100 depending on the
     * largest hand possible added to the value of the rank (1-13) to account for ties
     * @param cards
     * @return
     */
    public int getScore(List<Card> cards){
    	int maxScore = 0;                                            // Holds the current maximum score
    	cards.add(cardOne);                                          // Adds the player's hand to the cards on the table
    	cards.add(cardTwo);
    	UtilityMethods<Card> utils = new UtilityMethods<Card>();
    	Set<Set<Card>> hold = utils.getCombinations(cards,5);        // Generates all combinations of 5 cards using the player's 2 cards and the 5 on the table
	    Set<Set<Card>> newSet = new HashSet<Set<Card>>();
	    for(Set<Card> s : hold){
	    	if(s.size()==5){
	    		newSet.add(s);
	    	}
	    }
	    for(Set<Card> s : newSet){                                   // Calculates the score of each hand
	    	List<Integer> ranks = new ArrayList<Integer>();          // List of ranks in the 5 cards
	    	List<Suit> suits = new ArrayList<Suit>();                // List of suits in the 5 cards
	    	for(Card c : s){                                     
	    		ranks.addAll(c.getInt());
	    		suits.add(c.getSuit());
	    	}
	    	java.util.Collections.sort(ranks);                       // Sorts the list of ranks in ascending order
	    	int[] samerank = sameRanks(ranks);                       // Array containing the number of pairs (samerank[0]), triples (samerank[1]), and quads (samerank[2])
	    	boolean flush = isFlush(suits);                      
	    	boolean straight = isStraight(ranks);
	    	boolean quads = samerank[2] > 0;
	    	boolean fhouse = samerank[1] > 0 && samerank[0] > 0;
	    	boolean triple = samerank[1] > 0;
	    	boolean twop = samerank[0] > 1;
	    	boolean pair = samerank[0] > 0;
	    	int thisHand = ranks.get(4)*5 + ranks.get(3)*4 +         // For comparing two same hands (e.g. two flushes), generates a larger number for larger cards
	    			ranks.get(2)*3 + ranks.get(1)*2 + ranks.get(0);
	    	if(flush&&straight){                                     // Checking for hands in decreasing ranking (straight flush -> quads -> full house -> ... -> pair -> nothing)
	    		thisHand += 8000;
	    	} else if (quads){
	    		thisHand += 7000;
	    	} else if (fhouse){
	    		thisHand += 6000;
	    	} else if (flush){
	    		thisHand += 5000;
	    	} else if (straight){
	    		thisHand += 4000;
	    	} else if (triple){
	    		thisHand += 3000;
	    	} else if (twop){
	    		thisHand += 2000;
	    	} else if (pair){
	    		thisHand += 1000;
	    	}
	    	if(thisHand>maxScore){                                   // If this hand's score is greater than current max score, update max score
	    		maxScore = thisHand;
	    	}
	    }	    return maxScore;
    }
    
    /**
     * Old function to return the maximum integer given a list of integers
     * UtilityMethods.maxInt uses Collection<Integer> instead of List<Integer>
     * @param ranks - list of integer
     * @return the maximum integer
     */
    @Deprecated
    public int maxCards(List<Integer> ranks){
    	int max = 0;
    	for(Integer rnk : ranks){
    		if (rnk.intValue()>max){
    			max = rnk;
    		}
    	}
    	return max;
    }
    
    /**
     * Checks whether a list of suits are all the same
     * @param suits - list of suits
     * @return true of all the suits are the same; false otherwise.
     */
    public boolean isFlush(List<Suit> suits){
    	Iterator<Suit> i = suits.iterator();
    	while(i.hasNext()){
    		if(i.next()!=suits.get(0)){
    			return false;
    		}
    	}
    	return true;
    }
    
    /**
     * @param ranks - a list of ranks
     * @return the number of pairs, triples and quads
     */
    public int[] sameRanks(List<Integer> ranks){
    	int[] r = new int[14];
    	Iterator<Integer> i = ranks.iterator();
    	while(i.hasNext()){
    		r[i.next().intValue()-1]++;
    	}
    	int pairs = 0;
    	int triples = 0;
    	int quads = 0;
    	for(int j=1; j<14; j++){
    		if(r[j]==2){
    			pairs++;
    		} else if (r[j]==3){
    			triples++;
    		} else if (r[j]==4){
    			quads++;
    		}
    	}
    	int[] result = {pairs, triples, quads};
    	return result;
    }
    
    /**
     * @param ranks - a list of ranks
     * @return whether there is a straight (5 consecutive cards)
     */
    public boolean isStraight(List<Integer> ranks){
    	int consec = 1;
    	Iterator<Integer> i = ranks.iterator();
    	Integer last = i.next();
    	while(i.hasNext()){
    		Integer hold = i.next();
    		if(hold==last+1){
    			consec++;
    		} else {
    			consec = 1;
    		}
			last = hold;
    	}
    	return (consec>=5);
    }
    
    /**
     * Deprecated as UtilityMethods.getCombinations can do the same for generic types
     * @param group - list of elements
     * @param size - max size of combination
     * @return the set of combinations
     */
    @Deprecated
    public Set<Set<Card>> getCombinations(List<Card> group, int size){
    	Set<Set<Card>> resultingCombinations = new HashSet<Set<Card>>();
    	if(size==0 || group.size()==0){
    		resultingCombinations.add(new HashSet<Card>());
    	} else {
    		List<Card> remaining = new ArrayList<Card>(group);
    		Card X = remaining.remove(remaining.size()-1);
    		Set<Set<Card>> cExcX = getCombinations(remaining, size);
    		Set<Set<Card>> cIncX = getCombinations(remaining, size-1);
    		for (Set<Card> c : cIncX){
    			c.add(X);
    		}
    		resultingCombinations.addAll(cExcX);
    		resultingCombinations.addAll(cIncX);
    	}
    	return resultingCombinations;
    }
}
