package com.bayesianNetwork.controllers;

import java.util.List;

import com.bayesianNetwork.builders.RandomNetworkBuilder;
import com.bayesianNetwork.minimization.OnlineMinimizer;
import com.bayesianNetwork.network.Condition;
import com.bayesianNetwork.network.Network;
import com.bayesianNetwork.network.Value;

/**
 * The test controller is used to do basic experiment
 * on a simple bayesian network
 * @author Ted Gueniche
 *
 */
public class SignleNetworkTestController {


	public static void main(String[] args) {
		
		//Generating the network structure
		int dimension = 4;
		int minLevelLength = 2;
		int maxLevelLength = 2;
		int minParent = 2;
		int maxParent = 2;
		RandomNetworkBuilder nb = new RandomNetworkBuilder(dimension, minLevelLength, maxLevelLength, minParent, maxParent);
		Network net = nb.generate();
		
		//Training the network
		net.processCase(new Condition("1	2	1	1"));
		net.processCase(new Condition("2	1	1	1"));
		net.processCase(new Condition("1	1	2	1"));

		//Reduce the number of dimension of a test vector
		List<Value> result = OnlineMinimizer.minimize(net, new Condition("*	*	*	1"));
		for(Value val : result) {
			System.out.println(val);
		}
		
		
		
	}

}
