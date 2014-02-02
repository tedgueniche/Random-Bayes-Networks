package com.bayesianNetwork.network;

import java.util.List;

/**
 * Define the common interface used
 * for a bayesian network
 * @author Ted Gueniche
 *
 */
public interface INetwork {

	/**
	 * Train the network with this condition
	 */
	public void processCase(Condition condition);
	
	/**
	 * Train the network with this set of conditions
	 */
	public void processAllCases(List<Condition> conditions);
	
	/**
	 * Calculate the probability (non normalized) of this condition happening
	 */
	public Double prob(Condition condition);
}
