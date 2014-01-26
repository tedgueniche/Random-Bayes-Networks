package com.bayesianNetwork.controllers;

import java.util.List;

import com.bayesianNetwork.builders.RandomNetworkBuilder;
import com.bayesianNetwork.minimization.OnlineMinimizer;
import com.bayesianNetwork.network.Condition;
import com.bayesianNetwork.network.Network;
import com.bayesianNetwork.network.Value;

/**
 * 
 * @author Ted Gueniche
 *
 */
public class SignleNetworkController {


	public static void main(String[] args) {
		
		//Generating the network structure
		int dimension = 4;
		int minLevelLength = 2;
		int maxLevelLength = 2;
		int minParent = 2;
		int maxParent = 2;
		RandomNetworkBuilder nb = new RandomNetworkBuilder(dimension, minLevelLength, maxLevelLength, minParent, maxParent);
		Network net = nb.generate();
		
		net.processCase(new Condition("1	2	1	1"));
		net.processCase(new Condition("2	1	1	1"));
		net.processCase(new Condition("1	1	2	1"));
		
		
//		Condition toTest1 = new Condition("2	1	1	1");
//		Condition toTest2 = new Condition("1	1	1	1");
//		
//		Double score1Raw = net.prob(toTest1);
//		Double score2Raw = net.prob(toTest2);
//		
//		Double sum = score1Raw + score2Raw;
//		
//		
//		System.out.println(score1Raw / sum);
//		System.out.println(score2Raw / sum);
		

		
		List<Value> result = OnlineMinimizer.minimize(net, new Condition("*	*	*	1"));
		for(Value val : result) {
			System.out.println(val);
		}
		
		
		
	}

}
