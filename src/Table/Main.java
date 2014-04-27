package Table;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		Table t = new Table(4);
		for(int i=0; i<10000; i++){
			System.out.println(t.play().toString() + " wins.\n\n");
			t.resetTable();
		}
		int[] hands = t.getHands();
		int totalHands = 40000;
	    StringBuilder sb = new StringBuilder();
		sb.append("Straight Flush: " + round(hands[8], totalHands) + "\n");
		sb.append("Quad:           " + round(hands[7], totalHands) + "\n");
		sb.append("Full House:     " + round(hands[6], totalHands) + "\n");
		sb.append("Flush:          " + round(hands[5], totalHands) + "\n");
		sb.append("Straight:       " + round(hands[4], totalHands) + "\n");
		sb.append("Triple:         " + round(hands[3], totalHands) + "\n");
		sb.append("Two Pair:       " + round(hands[2], totalHands) + "\n");
		sb.append("Pair:           " + round(hands[1], totalHands) + "\n");
		sb.append("Nothing:        " + round(hands[0], totalHands) + "\n");
		System.out.println(sb.toString());
		double[][] winners = t.getWinningPercentage();
		StringBuilder sb2 = new StringBuilder();
		for(int i=0; i<13; i++){
			for(int j=0; j<13; j++){
				sb2.append(winners[i][j] + " ");
			}
			sb2.append("\n");
		}
		System.out.println(sb2.toString());
	}

	
    public static String round(int a, int b){
    	double ax = (double) a;
    	double bx = (double) b;
    	double c = ax/bx;
    	StringBuilder sb = new StringBuilder();
    	String first = String.valueOf(a);
    	sb.append(first);
    	for(int i = 0; i<5-first.length(); i++){
    		sb.append(" ");
    	}
    	sb.append(c);
        return sb.toString();
    }
}
