package com.bayesianNetwork.controllers;

import java.util.List;

import com.bayesianNetwork.builders.RandomNetworkBuilder;
import com.bayesianNetwork.network.Condition;
import com.bayesianNetwork.network.RandomBayesianNetworks;
import com.bayesianNetwork.utilities.IO;
import com.bayesianNetwork.utilities.TimerBenchmark;

public class RandomNetworksController {

	
	
	public static void main(String[] args) {
		
		//Instantiate a Network Builder
		TimerBenchmark.start("Network Structure");
		int dimension = 26;
		int minLevelLength = 4;
		int maxLevelLength = 8;
		int minParent = 2;
		int maxParent = 4;
		RandomNetworkBuilder nb = new RandomNetworkBuilder(dimension, minLevelLength, maxLevelLength, minParent, maxParent);
		
		//Generate the networks
		RandomBayesianNetworks nets = nb.generate(10);
		TimerBenchmark.pause("Network Structure");
		
		//Processing the training data
		TimerBenchmark.start("Process training data");
		List<String> lines = IO.readLinesFromFile("C:\\Users\\Root\\Dropbox\\dev\\projects\\java\\BayesianNetworks\\data\\SN.txt");
		while(lines.size() > 0) {
			String line = lines.remove(0);
			Condition c = new Condition(line);
			nets.processCase(new Condition(line));
		}
		TimerBenchmark.pause("Process training data");
		
		
		//Some testing
		Condition t1 = new Condition("*	*	*	*	*	*	*	*	1	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	1");
		Condition t2 = new Condition("*	*	*	*	*	*	*	*	1	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	2");
		
		Double t1Score = nets.prob(t1);
		Double t2Score = nets.prob(t2);
		Double sum = t1Score + t2Score;
		System.out.println(t1Score / sum);
		
		
		
		System.out.println(TimerBenchmark.allToString());
		
	}
}
