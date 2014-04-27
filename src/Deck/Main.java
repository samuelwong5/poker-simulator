package Deck;

public class Main {

	public static void main(String[] args) {
		Deck d = new Deck();
		System.out.println(d.toString() + " " + d.size());
		d.shuffle();
		System.out.println(d.toString() + " " + d.size());
		d.getCard();
		System.out.println(d.toString() + " " + d.size());
		
	}

}
