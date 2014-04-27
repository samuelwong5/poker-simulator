package Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UtilityMethods<T> {
	/**
	 * Returns the maximum integer within a collection
	 * @param score - the set of integers
	 * @return the maximum integer
	 */
	public static int maxInt(Collection<Integer> collection){
    	int max = 0;
    	for(Integer rnk : collection){
    		if (rnk.intValue()>max){
    			max = rnk;
    		}
    	}
    	return max;
    }
	
	/**
	 * Returns a string representing the value of a/b
	 * @param a - integer divident
	 * @param b - integer divisor
	 * @return - string a/b
	 */
	public static String divideToString(int a, int b){
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
	
    /**
     * All the combinations of size and less using groups
     * @param group - list of elements
     * @param size - maximum size of the combination
     * @return combinations of elements
     */
    public Set<Set<T>> getCombinations(List<T> group, int size){
    	assert (group.size()>=size) : "Number of elements must be greater than the number in each combination!";
    	Set<Set<T>> resultingCombinations = new HashSet<Set<T>>();
    	if(size==0 || group.size()==0){
    		resultingCombinations.add(new HashSet<T>());
    	} else {
    		List<T> remaining = new ArrayList<T>(group);
    		T X = remaining.remove(remaining.size()-1);
    		Set<Set<T>> cExcX = getCombinations(remaining, size);
    		Set<Set<T>> cIncX = getCombinations(remaining, size-1);
    		for (Set<T> c : cIncX){
    			c.add(X);
    		}
    		resultingCombinations.addAll(cExcX);
    		resultingCombinations.addAll(cIncX);
    	}
    	return resultingCombinations;
    }
}
