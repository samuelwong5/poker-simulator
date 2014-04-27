package Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Util.UtilityMethods;
import Deck.Card;
import Deck.Deck;

public class Table {
	double[][] winners, startHands;                              // Amount of each combination of starting hands and winning hand for every round (ranks and on/off-suit)
    private Map<Integer,Set<Card>> scores;                       // Maps the scores of the players to the cards in each round
	private Deck deck;                                           // The current deck
    private Set<Player> players;                                 // The set of players
    private Set<Card> tableCards;                                // The cards on the table
    private int playerCount;                                     // The amount of players
    private int[] hands;                                         // Amount of each type of hand (Straight flush, Four of a Kind, etc)
    /**
     * Constructor for table
     * @param playerCount - amount of players
     */
	public Table(int playerCount){
    	if(playerCount>10 || playerCount < 2){
    		throw new IllegalArgumentException();                // Supports maximum 10 players and minimum 2 players
    	}
    	this.playerCount = playerCount;
    	this.hands = new int[9];
    	winners = new double[13][];
    	startHands = new double[13][];
    	for(int i=0; i<13; i++){
    		winners[i] = new double[13];
    		startHands[i] = new double[13];
    	}
		resetTable();
	}
	
	/**
	 * Resets the table by creating a new deck and new players
	 */
	public void resetTable(){
		this.deck = new Deck();                                   // Initialize deck
		this.deck.shuffle();
		this.tableCards = new HashSet<Card>();                    // Initialize cards on the table
    	this.players = new HashSet<Player>();
    	for(int i=0; i<playerCount; i++){                         // Create players
    		this.players.add(new Player(this));
    	}
	}
	
	/** 
	 * Distributes 2 cards to every player
	 */
	public void distributeCardsToPlayers(){
		for (Player p : players){
			p.receiveCard(deck.getCard());
			p.receiveCard(deck.getCard());
		}
	}
	
	/**
	 * Places a certain amount of cards on the table
	 * @param amount - the amount of cards to be placed
	 */
	public void placeCards(int amount){
		for(int i=0; i<amount; i++){
			tableCards.add(deck.getCard());
		}
	}
	
	/**
	 * Simulates 'burning' a card in poker
	 */
	public void burnCard(){
		deck.getCard();
	}
	
	/**
	 * @return the cards on the table
	 */
	public Set<Card> getTableCards(){
		return tableCards;
	}
	
	/**
	 * Calculates the score of the current game
	 * @return a string containing each player's cards, their score, and the cards on the table
	 */
	public String calculateScore(){
		this.scores = new HashMap<Integer,Set<Card>>();
		StringBuilder sb = new StringBuilder();
		int playerIndex = 1;
		for (Player p : players){
			int score = p.getScore(new ArrayList<Card>(tableCards));
			scores.put(Integer.valueOf(score), p.getCards());
			sb.append("Player " + playerIndex++ + " : " + p.toString() + " with a ");
			if(score>8000){
				sb.append("Straight Flush!!!");
				hands[8]++;
			} else if (score>7000){
			    sb.append("Four of a Kind!");
			    hands[7]++;
			} else if (score>6000){
				sb.append("Full House!");
				hands[6]++;
			} else if (score>5000){
				sb.append("Flush!");
				hands[5]++;
			} else if (score>4000){
				sb.append("Straight!");
				hands[4]++;
			} else if (score>3000){
				sb.append("Three of a Kind.");
				hands[3]++;
			} else if (score>2000){
				sb.append("two pair.");
				hands[2]++;
			} else if (score>1000){
				sb.append("pair.");
				hands[1]++;
			} else {
				sb.append("nothing special :c");
				hands[0]++;
			}
			sb.append("\n");
		}
		sb.append("\nCards on Table:\n");
		for (Card c : tableCards){
			sb.append(c.toString() + ", ");
		}
		
		return sb.substring(0, sb.length()-2).toString();
	}
	
	/**
	 * Updates the tracking of winning hands
	 */
	public void setWinner(){
		Set<Integer> handValues = scores.keySet();
		Set<Card> winningHand = scores.get(Util.UtilityMethods.maxInt(handValues));
		increment(winningHand, winners);
		for(Player p : players){
			increment(p.getCards(), startHands);
		}
		//winningHands.put(winningHand, winningHands.get(winningHand));
	}
	
	/**
	 * Increments the array depending on the cards (ranks and on/off suit)
	 * @param set - the two cards
	 * @param array - the array
	 */
	public void increment(Set<Card> set, double[][] array){
		List<Card> winningHandList = new ArrayList<Card>(set);
		if(winningHandList.get(0).getSuit()==winningHandList.get(1).getSuit()){
			int firstCard = winningHandList.get(0).toInt();
			int secondCard = winningHandList.get(1).toInt();
			if(firstCard<secondCard){
				array[firstCard-1][secondCard-1]++;
			} else {
				array[secondCard-1][firstCard-1]++;
			}
		} else {
			int firstCard = winningHandList.get(0).toInt();
			int secondCard = winningHandList.get(1).toInt();
			if(firstCard<secondCard){
				array[secondCard-1][firstCard-1]++;
			} else {
				array[firstCard-1][secondCard-1]++;
			}
		}
	}
	
	/**
	 * Simulates a round of Hold'em
	 * 1. Distribute cards
	 * 2. Burn card
	 * 3. Place 3 cards (flop)
	 * 4. Burn card
	 * 5. Place 1 card (turn)
	 * 6. Burn card
	 * 7. Place 1 card (river)
	 * @return the winning hand
	 */
	public Set<Card> play(){
		distributeCardsToPlayers();
		burnCard();
		placeCards(3); // Flop
		burnCard();
		placeCards(1); // Turn
		burnCard();
		placeCards(1); // River
		System.out.println(this.calculateScore());
		setWinner();
		return scores.get(Util.UtilityMethods.maxInt(scores.keySet()));
	}
	
	/**
	 * @return the number of straight flushes, quads etc
	 */
	public int[] getHands(){
		return hands;
	}
	
	/*@Deprecated
	public int maxInt(Set<Integer> score){
    	int max = 0;
    	for(Integer rnk : score){
    		if (rnk.intValue()>max){
    			max = rnk;
    		}
    	}
    	return max;
    }*/
	
    /**
     * Gets the amount of wins of each hand (Two ranks, on/off suit)
     * @return the amount
     */
	public double[][] getWinners(){
		return winners;
	}
	
	/**
	 * Gets the winning percentage of each hand (Two ranks, on/off suit)
	 * @return the winning percentages
	 */
	public double[][] getWinningPercentage(){
		double[][] result = new double[13][];
		for(int i=0; i<13; i++){
			result[i] = new double[13];
			for(int j=0; j<13; j++){
				result[i][j] = winners[i][j] / startHands[i][j];
			}
		}
		return result;
	}
	
	/**
	 * GetWinners() used instead
	 * @returns a map of the set of winning hands (2 cards) to the number of wins
	 */
	@Deprecated
	public Map<Set<Card>,Integer> getWinningHands(){
		//return winningHands;
		return null;
	}
}
