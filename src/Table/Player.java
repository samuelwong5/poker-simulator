package Table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import Deck.Card;
import Deck.Suit;

public class Player {
    private Table table;
	private Card cardOne;
    private Card cardTwo;
    public Player(Table table){
    	this.table = table;
    }
    public void receiveCard(Card c){
    	if (cardOne==null){
    		cardOne = c;
    	} else if (cardTwo==null){
    		cardTwo = c;
    	} else {
    	    // Obligatory exception handling	
    	}
    }
    public Set<Card> getCards(){
    	Set<Card> s = new HashSet<Card>();
    	s.add(cardOne);
    	s.add(cardTwo);
    	return s;
    }
    public String toString(){
    	return cardOne.toString() + ", " + cardTwo.toString();
    }
    public int getScore(List<Card> cards){
    	int max = 0;
    	cards.add(cardOne);
    	cards.add(cardTwo);
    	Set<Set<Card>> hold = getCombinations(cards,5);
	    Set<Set<Card>> newSet = new HashSet<Set<Card>>();
	    for(Set<Card> s : hold){
	    	if(s.size()==5){
	    		newSet.add(s);
	    	}
	    }
	    int maxScore = 0;
	    for(Set<Card> s : newSet){
	    	List<Integer> ranks = new ArrayList<Integer>();
	        Suit firstSuit;
	    	List<Suit> suits = new ArrayList<Suit>();
	    	for(Card c : s){
	    		ranks.addAll(c.getInt());
	    		suits.add(c.getSuit());
	    	}
	    	java.util.Collections.sort(ranks);
	    	boolean flush = isFlush(suits);
	    	boolean straight = isStraight(ranks);
	    	int[] samerank = sameRanks(ranks);
	    	boolean quads = samerank[2] > 0;
	    	boolean fhouse = samerank[1] > 0 && samerank[0] > 0;
	    	boolean triple = samerank[1] > 0;
	    	boolean twop = samerank[0] > 1;
	    	boolean pair = samerank[0] > 0;
	    	int thisHand = maxCards(ranks);
	    	if(flush&&straight){
	    		thisHand += 800;
	    	} else if (quads){
	    		thisHand += 700;
	    	} else if (fhouse){
	    		thisHand += 600;
	    	} else if (flush){
	    		thisHand += 500;
	    	} else if (straight){
	    		thisHand += 400;
	    	} else if (triple){
	    		thisHand += 300;
	    	} else if (twop){
	    		thisHand += 200;
	    	} else if (pair){
	    		thisHand += 100;
	    	}
	    	if(thisHand>maxScore){
	    		maxScore = thisHand;
	    	}
	    }	    return maxScore;
    }
    
    public int maxCards(List<Integer> ranks){
    	int max = 0;
    	for(Integer rnk : ranks){
    		if (rnk.intValue()>max){
    			max = rnk;
    		}
    	}
    	return max;
    }
    public boolean isFlush(List<Suit> suits){
    	Iterator<Suit> i = suits.iterator();
    	while(i.hasNext()){
    		if(i.next()!=suits.get(0)){
    			return false;
    		}
    	}
    	return true;
    }
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
    
    public Set<Set<Card>> getCombinations(List<Card> group, int size){
    	Set<Set<Card>> resultingCombinations = new HashSet<Set<Card>>();
    	//System.out.println(group.size());
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
