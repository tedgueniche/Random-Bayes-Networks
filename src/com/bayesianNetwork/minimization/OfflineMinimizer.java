package com.bayesianNetwork.minimization;

import java.util.List;

import com.bayesianNetwork.network.Network;

/**
 * This class is used to discard dimensions in a Bayesian Network.
 * A dimension can be discarded if it does not affect the overall 
 * network. This process is offline meaning that it is used
 * in a static way on a fully constructed Bayesian Network.
 * @author Ted Gueniche
 *
 */
public class OfflineMinimizer {

	/**
	 * Tries to minimize a Bayesian Network
	 * @param net The Bayesian Network to minimize, this method does not modify the Bayesian Network.
	 * @return A list of minimization rules.
	 */
	public static List<Rule> minimize(Network net) {
		
		System.err.println("OfflineMinimizer::minimize() is not implemented");
		return null;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
