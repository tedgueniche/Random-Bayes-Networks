package com.bayesianNetwork.network;

import java.util.HashSet;
import java.util.Set;

/**
 * Represent a Bayesian Network as a whole
 * @author Ted Gueniche
 *
 */
public class Network implements INetwork {

	
	/**
	 * Root node of the bayesian network
	 * All the nodes in the network can be found using 
	 * this root.
	 */
	public Node Root;
	
	/**
	 * Not used
	 */
	public Network() {}
	
	/**
	 * Propagate this condition to all the node in the network
	 * @param condition
	 */
	public void processCase(Condition condition) {
		Root.processCase(condition);
		
		Set<Node> seen = new HashSet<>();
		seen.add(Root);
		
		processCase(seen, Root, condition);
	}
	
	/**
	 * Internal method use to process a case
	 * @param node Node to start with, this node needs to be already process
	 * @param condition Condition to process
	 */
	private void processCase(Set<Node> seen, Node node, Condition condition) {
		for(Node child : node.getChildren()) {
			
			if(seen.contains(child)) {
				continue;
			}
			seen.add(child);
			
			//Process this case on this child
			child.processCase(condition);
			
			//Recursive call for this node's children
			processCase(seen, child, condition);
		}
	}
	
	/**
	 * Calculate the probability of the given condition
	 * through the whole network
	 * @param condition Condition to test
	 * @return The probability of this condition happening
	 */
	public Double prob(Condition condition) {
		Double probability =  Root.prob(condition);
		
		Set<Node> seen = new HashSet<>();
		seen.add(Root);
		
		//Start the recursive call through the network
		probability *= prob(seen, Root, condition);
		
		return probability;
	}
	
	/**
	 * Internal method use to calculate the probability
	 * @param node The node's children to evaluate
	 * @param condition The condition to test
	 */
	private Double prob(Set<Node> seen, Node node, Condition condition) {
		
		Double probability = 1d;
		for(Node child : node.getChildren()) {
			
			if(seen.contains(child)) {
				continue;
			}
			seen.add(child);
			
			//Calculating the prob for this child
			Double childProb = child.prob(condition);
			probability *= (childProb == 0.0) ? Node.smoothingVal : childProb;
			
			//recursive call
			probability *= prob(seen, child, condition);
		}
		
		return probability;
	}
}
