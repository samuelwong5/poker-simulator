package Util;

import java.util.Set;

public class UtilityMethods {
	public static int maxInt(Set<Integer> score){
    	int max = 0;
    	for(Integer rnk : score){
    		if (rnk.intValue()>max){
    			max = rnk;
    		}
    	}
    	return max;
    }
}
