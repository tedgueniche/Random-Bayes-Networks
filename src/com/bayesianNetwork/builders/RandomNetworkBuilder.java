package com.bayesianNetwork.builders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bayesianNetwork.network.Network;
import com.bayesianNetwork.network.Node;
import com.bayesianNetwork.network.RandomBayesianNetworks;

/**
 * Build a random Bayesian network
 * Some restriction applies: 
 * 	- circular path are not allowed
 * 	- restriction on parent's number and child's number
 * 
 * The network is generated level by level
 * we add transition between brother nodes and
 * from the upper level to the current one.
 * We do not add transition to a higher level, 
 * only downward or sideway.
 * 
 * @author Ted Gueniche
 *
 */
public class RandomNetworkBuilder {

	private int dimension;
	private int minLevelLength;
	private int maxLevelLength;
	private int minParent;
	private int maxParent;
	
	
	public RandomNetworkBuilder(int dimension, int minLevelLength, int maxLevelLength, int minParent, int maxParent) {
		this.dimension = dimension;
		this.minLevelLength = minLevelLength;
		this.maxLevelLength = maxLevelLength;
		this.minParent = minParent;
		this.maxParent = maxParent;
	}
	
	/**
	 * Generate a random Bayesian Network
	 * @return
	 */
	public Network generate() {

		//Generating the list of node to add in the network
		// one node per dimension
		List<Integer> toAdd = new ArrayList<Integer>();
		for(int i = 1 ; i <= dimension ; i++) {
			toAdd.add(i);
		}

		Collections.shuffle(toAdd);
		
		//Creating the random root node
		int randId = randomBetween(0, dimension - 1);
		Node root = new Node("R"+ toAdd.get(randId));
		toAdd.remove(randId);
		
		//Adding the levels for the network
		List<Node> curLevel = new ArrayList<Node>();
		curLevel.add(root);
		AddLevel(curLevel, toAdd);
		
		Network net = new Network();
		net.Root = root;
		return net;
	}
	
	/**
	 * Generate a random set of Bayesian Networks
	 * @param count Number of network in the set
	 * @return A RandomBayesianNetworks
	 */
	public RandomBayesianNetworks generate(int count) {
		
		RandomBayesianNetworks networks = new RandomBayesianNetworks();
		for(int i = 0 ; i < count ; i++) {
			networks.AddNetwork(generate());
		}
		
		return networks;
	}
	
	/**
	 * Recursively adds one level at the time to the network
	 */
	public void AddLevel(List<Node> prevLevel, List<Integer> toAdd) {
		
		if(toAdd.size() == 0) {
			return;
		}
		
		//Adding a random number of node in this level
		int nodeNum = randomBetween(minLevelLength, maxLevelLength);
		List<Node> curLevel = new ArrayList<Node>();
		for(int i = 0 ; i < nodeNum && toAdd.size() > 0 ; i++) {
			Integer id = toAdd.get(0);
			toAdd.remove(0);
			
			curLevel.add(new Node("R"+ id));
		}
		
		//generating a list of candidate for the transitions
		List<Node> candidates = new ArrayList<Node>();
		candidates.addAll(prevLevel);
		candidates.addAll(curLevel);
		
		//Adding transition to each of the generated nodes
		for(Node node : curLevel) {
			
			//Shuffling the order of the candidate
			Collections.shuffle(candidates);
			
			int parentNum = randomBetween(minParent, maxParent);
			
			//Adding the candidate
			for(Node candidate : candidates) {
				if(node.ParentSize() < parentNum && 
						candidate.getId().compareTo(node.getId()) != 0 && 
						candidate.hasParent(node.getId()) == false) {
					node.AddParent(candidate.getId());
					candidate.AddChild(node);
				}
			}
		}
		
		//Recursive call for the next level
		AddLevel(curLevel, toAdd);
		
		return;
	}
	
	/**
	 * Pick a number in the defined range (inclusively)
	 * @param rangeStart Inclusive start of the range
	 * @param rangeEnd Inclusive end of the range
	 */
	public int randomBetween(int rangeStart, int rangeEnd) {
		double rand = Math.random();
		
		rand *= ((rangeEnd - rangeStart) + 1);
		rand += rangeStart;
		
		return (int) rand;
	}
	
}
