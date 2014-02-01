package com.bayesianNetwork.minimization;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.bayesianNetwork.network.Condition;
import com.bayesianNetwork.network.INetwork;
import com.bayesianNetwork.network.Value;

/**
 * This class works with a given condition (the current state) and a Bayesian
 * Network. It tries to identify which of the not specified dimensions can be 
 * guessed with a probability over a minimum support threshold for the given condition.
 * It help reduce the number of dimensions when all the values for these dimensions is not yet
 * known. This is way it considered an online operation.
 * @author Ted Gueniche
 *
 */
public class OnlineMinimizer {

	
	public static final double threshold = 0.70;
	
	/**
	 * Tries to identify which dimensions values can be guessed
	 * @param net The Bayesian Network to minimize, this method does not modify the Bayesian Network.
	 * @param state Current state, the dimensions with a wildcard value are the one we try to guess.
	 * @return An unordered list of values. Where each value contains the dimension id and the guessed concrete value.
	 */
	public static List<Value> minimize(INetwork net, Condition state) {
		
		List<Value> values = new ArrayList<Value>();
		List<Value> wildcards = state.getAllWildcards();
		for(Value wildcard : wildcards) {
			
			//Used for score normalization and stats
			Double sum = 0.0;
			TreeMap<Double, Value> scores = new TreeMap<>();
			
			for(int i = Value.minRange ; i <= Value.maxRange ; i++) {
				Condition cState = state.clone();
				cState.setValue(wildcard.id, new Value(wildcard.id, i));
				
				//Calculating the probability of this state happening
				Double p = net.prob(cState);
				
				//Stats
				sum += p;
				scores.put(p, new Value(wildcard.id, i));
			}
			
			
			//Check if the best candidate has a high enough probability
			Double normScore = (scores.lastKey() / sum);
			if( normScore >= threshold) {
				Value condidate = scores.get(scores.lastKey());
				values.add(condidate);
			}
		}
		
		return values;
	}
}
