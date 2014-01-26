package com.bayesianNetwork.builders;

import java.util.ArrayList;
import java.util.List;

import com.bayesianNetwork.network.Condition;
import com.bayesianNetwork.network.Network;

/**
 * This data structure holds a random set of
 * Bayesian Networks. The purpose of such structure
 * is to minimize probability variations when querying
 * a Bayesian Network.
 * @author Ted Gueniche
 *
 */
public class RandomBayesianNetworks {

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
	 * Query all the Bayesian Network for the probability
	 * of this condition and return the mode if possible
	 * or the mean otherwise.
	 * @param condition Condition to evaluate
	 */
	public double prob(Condition condition) {
		return 0.0;
	}

}
