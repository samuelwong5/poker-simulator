package Deck;

public enum Rank {
	
    TWO (2), THREE (3), FOUR (4), FIVE (5), SIX (6), SEVEN (7), 
    EIGHT (8), NINE (9), TEN (10), JACK (11), QUEEN (12), KING (13), ACE (14);
    
    private int value;
    
    private Rank(int value){
    	this.value = value;
    }
    
    // Returns the value of a rank (2-14)
    public int getInt(Rank r){
    	return value;
    }
    
    // Outputs a string representation of a rank
    public String toString(Rank r){
    	switch(r){
    	case TWO:
    		return "2";
    	case THREE:
    		return "3";
    	case FOUR:
    		return "4";
    	case FIVE:
    		return "5";
    	case SIX:
    		return "6";
    	case SEVEN:
    		return "7";
    	case EIGHT:
    		return "8";
    	case NINE:
    		return "9";
    	case TEN:
    		return "10";
    	case JACK:
    		return "J";
    	case QUEEN:
    		return "Q";
    	case KING:
    		return "K";
    	default:
    		return "A";
    	}
    }
}
