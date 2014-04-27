package Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Deck.Card;
import Deck.Deck;

public class Table {
	double[][] winners, startHands;
	private Map<Set<Card>, Integer> winningHands;
    private Map<Integer,Set<Card>> scores;
	private Deck deck;
    private Set<Player> players;
    private Set<Card> tableCards;
    private int playerCount;
    private int[] hands;
	public Table(int playerCount){
    	if(playerCount>10 || playerCount < 2){
    		throw new IllegalArgumentException();            // Supports maximum 10 players and minimum 2 players
    	}
    	this.playerCount = playerCount;
    	this.hands = new int[9];
    	this.winningHands = new HashMap<Set<Card>, Integer>();
    	winners = new double[13][];
    	startHands = new double[13][];
    	for(int i=0; i<13; i++){
    		winners[i] = new double[13];
    		startHands[i] = new double[13];
    	}
		resetTable();
	}
	public void resetTable(){
		this.deck = new Deck();                                   // Initialize deck
		this.deck.shuffle();
		this.tableCards = new HashSet<Card>();                    // Initialize cards on the table
    	this.players = new HashSet<Player>();
    	for(int i=0; i<playerCount; i++){                    // Create players
    		this.players.add(new Player(this));
    	}
	}
	public void distributeCardsToPlayers(){
		for (Player p : players){
			p.receiveCard(deck.getCard());
			p.receiveCard(deck.getCard());
		}
	}
	public void placeCards(int amount){
		for(int i=0; i<amount; i++){
			tableCards.add(deck.getCard());
		}
	}
	public void burnCard(){
		deck.getCard();
	}
	public Set<Card> getTableCards(){
		return tableCards;
	}
	public String calculateScore(){
		this.scores = new HashMap<Integer,Set<Card>>();
		StringBuilder sb = new StringBuilder();
		int playerIndex = 1;
		for (Player p : players){
			int score = p.getScore(new ArrayList<Card>(tableCards));
			scores.put(Integer.valueOf(score), p.getCards());
			sb.append("Player " + playerIndex++ + " : " + p.toString() + " with a ");
			if(score>800){
				sb.append("Straight Flush!!!");
				hands[8]++;
			} else if (score>700){
			    sb.append("Four of a Kind!");
			    hands[7]++;
			} else if (score>600){
				sb.append("Full House!");
				hands[6]++;
			} else if (score>500){
				sb.append("Flush!");
				hands[5]++;
			} else if (score>400){
				sb.append("Straight!");
				hands[4]++;
			} else if (score>300){
				sb.append("Three of a Kind.");
				hands[3]++;
			} else if (score>200){
				sb.append("two pair.");
				hands[2]++;
			} else if (score>100){
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
	public void setWinner(){
		Set<Integer> handValues = scores.keySet();
		Set<Card> winningHand = scores.get(Util.UtilityMethods.maxInt(handValues));
		increment(winningHand, winners);
		for(Player p : players){
			increment(p.getCards(), startHands);
		}
		//winningHands.put(winningHand, winningHands.get(winningHand));
	}
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
		return scores.get(maxInt(scores.keySet()));
	}
	public int[] getHands(){
		return hands;
	}
	public int maxInt(Set<Integer> score){
    	int max = 0;
    	for(Integer rnk : score){
    		if (rnk.intValue()>max){
    			max = rnk;
    		}
    	}
    	return max;
    }
	public Map<Set<Card>,Integer> getWinningHands(){
		return winningHands;
	}
	public double[][] getWinners(){
		return winners;
	}
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
}
