package com.bayesianNetwork.controllers;

import java.util.Collections;
import java.util.List;

import com.bayesianNetwork.builders.RandomNetworkBuilder;
import com.bayesianNetwork.evaluation.Dataset;
import com.bayesianNetwork.evaluation.PredictionResults;
import com.bayesianNetwork.minimization.OnlineMinimizer;
import com.bayesianNetwork.network.Condition;
import com.bayesianNetwork.network.INetwork;
import com.bayesianNetwork.network.Value;
import com.bayesianNetwork.utilities.TimerBenchmark;

/**
 * In this experiment, the data are separated in training vs testing.
 * We take a random sample from each testing vector and replace it with 
 * wildcards. The prediction task consists in predicting the value of each wildcards.
 * @author Ted Gueniche
 *
 */
public class DimensionPredictionController {

	/**
	 * This method replace a portion of the given condition with some wildcards
	 * @param condition Condition to modify
	 * @param ratio Ratio of the condition to modify [0,1]
	 * @return The number of values changed
	 */
	public static int mutateCondition(Condition condition, double ratio) {
		//Ratio validation
		if(ratio < 0 || ratio > 1) {
			return 0;
		}
		
		//Calculating the number of wildcards and suffling the id list
		int wildcardCount = (int) ((double) condition.size() * ratio);
		List<String> ids = condition.getIds();
		Collections.shuffle(ids);
		
		//Setting the wildcards
		for(int i = 0 ; i < wildcardCount; i++) {
			condition.setValue(ids.get(i), new Value(ids.get(i), null));
		}
		
		return wildcardCount;
	}
	
	/**
	 * Calculate the distance between two conditions. 
	 * @return 0 when the two conditions are identical or it returns the number
	 * of distinct values. A wildcard is always considered identical to any value
	 */
	public static int distance(Condition A, Condition B) {
		
		int dist = 0;
		List<String> ids = A.getIds();
		for(String id : ids) {
			Integer a = A.getValueById(id).value;
			Integer b = B.getValueById(id).value;
			
			if(a == null || b == null) {
				continue;
			}
			else if(a.equals(b) == false) {
				dist++;
			}
		}
		
		return dist;
	}
	
	/**
	 * Use the OnlineMinimizer to guess some of the dimensions's values
	 * @param condition Condition to minimize
	 */
	public static void minimize(Condition condition, INetwork nets) {
		List<Value> result = OnlineMinimizer.minimize(nets, condition);
		for(Value val : result) {
			condition.setValue(val.id, val);
		}
	}
	
	public static void main(String[] args) {
		
		//Instantiate a Network Builder
		TimerBenchmark.start("Network Structure");
		int dimension = 73;
		int minLevelLength = 2;
		int maxLevelLength = 3;
		int minParent = 1;
		int maxParent = 3;
		RandomNetworkBuilder nb = new RandomNetworkBuilder(dimension, minLevelLength, maxLevelLength, minParent, maxParent);
		
		//Generate the networks
		INetwork nets = nb.generate(25);
		TimerBenchmark.pause("Network Structure");
		
		//Preparing the data
		TimerBenchmark.start("Preparing the datasets");
		Dataset dataset = new Dataset("C:\\Users\\Root\\Dropbox\\dev\\projects\\java\\BayesianNetworks\\data\\All.txt");
		dataset.prepare(0.95);
		TimerBenchmark.pause("Preparing the datasets");
		
		//Training the model2
		TimerBenchmark.start("Training the model");
		nets.processAllCases(dataset.trainingSet);
		TimerBenchmark.pause("Training the model");
		
		TimerBenchmark.start("Testing the model");
		double mutationRate = 0.1;
		PredictionResults results = new PredictionResults();
		for(Condition original : dataset.testingSet) {
			
			//Cloning, mutating and minimizing
			Condition mutated = original.clone();
			int wildcardAdded = mutateCondition(mutated, mutationRate);
			
			TimerBenchmark.start("Minimization");
			minimize(mutated, nets);
			TimerBenchmark.pause("Minimization");
			
			//Saving results
			int dist = distance(original, mutated);
			int predicted = wildcardAdded - mutated.countWildcards();
			results.addResult(dist, predicted, wildcardAdded);
			
		}
		TimerBenchmark.pause("Testing the model");
		
		//Evaluator's output
		System.out.println(TimerBenchmark.allToString());
		System.out.println("Coverage: "+ results.getMatched());
		System.out.println("Accuracy: "+ results.getAccuracy());
		System.out.println("Avg gain: "+ results.getAverageGain());
	}
}
