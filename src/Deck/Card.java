package Deck;

import java.util.ArrayList;
import java.util.List;

public class Card {
    private Rank rank;
    private Suit suit;
    public Card(Rank r, Suit s){
    	this.rank = r;
    	this.suit = s;
    }
    public Rank getRank(){
    	return rank;
    }
    public Suit getSuit(){
    	return suit;
    }
    @Override
    public boolean equals(Object obj){
    	if(obj==this){
    		return true;
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
    public int hashCode(){
    	return rank.getInt(rank) * 3 + suit.getInt(suit);
    }
    public String toString(){
    	return rank.toString(rank) + suit.toString(suit);
    }
    public List<Integer> getInt(){
    	if(rank==Rank.ACE){
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
    public int toInt(){
    	return rank.getInt(rank)-1;
    }
}
