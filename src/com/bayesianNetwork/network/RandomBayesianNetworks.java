package com.bayesianNetwork.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;


/**
 * This data structure holds a random set of
 * Bayesian Networks. The purpose of such structure
 * is to minimize probability variations when querying
 * a Bayesian Network.
 * @author Ted Gueniche
 *
 */
public class RandomBayesianNetworks implements INetwork {

	/**
	 * The list of Bayesian Networks
	 */
	private List<Network> networks;
	
	/**
	 * Generate an empty random Bayesian Network
	 */
	public RandomBayesianNetworks() {
		networks = new ArrayList<>();
	}
	
	/**
	 * Add a Bayesian Network to the set
	 * @param net Bayesian Network to add
	 */
	public void AddNetwork(Network net) {
		networks.add(net);
	}
	
	/**
	 * Propagate this condition to all the networks
	 * @param condition
	 */
	public void processCase(Condition condition) {
		for(Network net : networks) {
			net.processCase(condition);
		}
	}
	
	/**
	 * Propagate all the given conditions to all the networks
	 */
	public void processAllCases(List<Condition> conditions) {
		for(Condition condition : conditions) {
			processCase(condition);
		}
	}
	
	
	/**
	 * Query all the Bayesian Network for the probability
	 * of this condition and return the mode if possible
	 * or the mean otherwise.
	 * @param condition Condition to evaluate
	 */
	public Double prob(Condition condition) {
		Double result = null;
		
		HashMap<Double, Integer> modeMap = new HashMap<Double, Integer>();
		
		int maxCount1 = -1;
		double maxValue1 = -1.0;
		int maxCount2 = -1;
		double maxValue2 = -1.0;
		for(Network net : networks) {
			
			//calculating the prob for this network
			double p = net.prob(condition);
			
			//updating the modeMap
			Integer curSup = modeMap.get(p);
			if(curSup == null) {
				curSup = 0;
			}
			curSup++;
			modeMap.put(p, curSup);
			
			if(maxCount1 < curSup) {
				maxCount1 = curSup;
				maxValue1 = p;
			} 
			else if(maxCount2 < curSup) {
				maxCount2 = curSup;
				maxValue2 = p;
			}
		}
		
		
		if(maxCount1 > maxCount2) {
			result = maxValue1;
			System.out.println("Mode: "+ result + " sup: "+ maxCount1);
		}
		else {
			result = ((maxValue1  + maxValue2) / 2.0);
			System.out.println("Mean: "+ result + " sup: "+ maxCount1 + ", "+ maxCount2);
		}
		
		return result;
	}



}
