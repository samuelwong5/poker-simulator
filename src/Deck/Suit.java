package Deck;

public enum Suit {
	
    DIAMONDS, CLUBS, HEARTS, SPADES;
    
    public int getInt(Suit s){
    	switch(s){
    	case DIAMONDS:
    		return 1;
    	case CLUBS:
    		return 2;
    	case HEARTS:
    		return 3;
    	default:
    		return 4;
    	}
    }
    
    public String toString(Suit s){
    	switch(s){
    	case DIAMONDS:
    		return "D";
    	case CLUBS:
    		return "C";
    	case HEARTS:
    		return "H";
    	default:
    		return "S";
    	}
    }
}
