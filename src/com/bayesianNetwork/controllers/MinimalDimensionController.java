package com.bayesianNetwork.controllers;

import java.util.List;

import com.bayesianNetwork.builders.RandomNetworkBuilder;
import com.bayesianNetwork.evaluation.Dataset;
import com.bayesianNetwork.evaluation.MinimizationResult;
import com.bayesianNetwork.network.Condition;
import com.bayesianNetwork.network.INetwork;
import com.bayesianNetwork.network.Value;
import com.bayesianNetwork.utilities.TimerBenchmark;


/**
 * In this experiment, we try to reduce the number of dimensions
 * for a vector while keeping a high confidence of a specific value within
 * that vector.
 * @author Ted Gueniche
 *
 */
public class MinimalDimensionController {

	/**
	 * Calculate the probability of the given attribute with its current value
	 * against all other possible values
	 * @param condition Condition to evaluate
	 * @param id Id of the attribute to evaluate
	 */
	public static Double prob(INetwork nets, Condition condition, String id) {
		
		//Prob of the given condition
		Double pOriginal = nets.prob(condition);
		int curValue = condition.getValueById(id).value;
		
		//cloning the current condition to avoid modification
		Condition conditionOthers = condition.clone();
		Double pTotal = pOriginal;
		
		//Summing all the probability of all other possibility for this attribute id
		for(int i = Value.minRange; i <= Value.maxRange; i++) {
			
			if(i != curValue) {
				conditionOthers.setValue(id, new Value(id, i));
				Double pCurrent = nets.prob(conditionOthers);
				pTotal += pCurrent;
			}
		}

		//Normalizing the probability
		return pOriginal / pTotal;
	}
	
	
	/**
	 * Eliminate the best attribute that affect the less the overall probability
	 * @param condition Condition to minimize
	 * @param id Id of the attribute that can't change
	 * @return The best value to change with a wildcard
	 */
	public static Value minimize(INetwork nets, Condition condition, String id) {
		
		//if there is only one attribute left, then nothing to do
		if( (condition.size() - condition.countWildcards()) < 2 ) {
			return null;
		}
		
		//Used to save the best value and its probability
		Double maxProb = 0.0;
		Value maxValue = null;
		
		
		//Evaluate the replacement of each individual attribute with a wildcard
		List<String> ids = condition.getIds();
		for(String aId : ids) {
			if(aId.equals(id) == false && condition.getValueById(aId).value != null) {
				
				//Replace this attribute with a wildcard
				Condition toTest = condition.clone();
				Value v = new Value(aId, null);
				toTest.setValue(aId, v);
				
				//Calculate its probability
				Double p = prob(nets,toTest, id);
				if(p > maxProb) {
					maxProb = p;
					maxValue = v;
				}
			}
		}
		
		return maxValue;
	}
	
	
	public static void main(String[] args) {
		
		//Instantiate a Network Builder
		TimerBenchmark.start("Network Structure");
		int dimension = 22;
		int minLevelLength = 2;
		int maxLevelLength = 3;
		int minParent = 1;
		int maxParent = 3;
		RandomNetworkBuilder nb = new RandomNetworkBuilder(dimension, minLevelLength, maxLevelLength, minParent, maxParent);
		
		//Generate the networks
		INetwork nets = nb.generate(5);
		TimerBenchmark.pause("Network Structure");
		
		//Preparing the data
		TimerBenchmark.start("Preparing the datasets");
		Dataset dataset = new Dataset("C:\\Users\\Root\\Dropbox\\dev\\projects\\java\\BayesianNetworks\\data\\EI.txt");
		dataset.prepare(0.92);
		TimerBenchmark.pause("Preparing the datasets");
		
		//Training the model2
		TimerBenchmark.start("Training the model");
		nets.processAllCases(dataset.trainingSet);
		TimerBenchmark.pause("Training the model");
		
		
		TimerBenchmark.start("Testing the model");
		String toKeep = "R22";
		Double threshold = 0.80;
		MinimizationResult results = new MinimizationResult();
		for(Condition original : dataset.testingSet) {
			
			//Save the original confidence
			double originalProb = prob(nets, original, toKeep);
			double curProb = originalProb;
			
			//While the confidence is still high
			//we can remove more dimensions
			while(curProb > threshold) {
				
				Value v = minimize(nets, original, toKeep);
				if(v == null) {
					break;
				}
				
				//Tries to remove the least important dimension
				Condition toTest = original.clone();
				toTest.setValue(v.id, v);
				curProb = prob(nets, toTest, toKeep);
				
				//The following exit hole is to 
				//avoid setting a value that will lower the confidence
				if(curProb > threshold) {
					original.setValue(v.id, v);
				}
				else {
					break;
				}
			}
			
			//Calculating the precision loss and the dimension gain
			double finalProb = prob(nets, original, toKeep);
			double probLoss = originalProb - finalProb;
			int gain = original.countWildcards();
			
			results.addResult(gain, probLoss);
			
		}
		TimerBenchmark.pause("Testing the model");
		
		//Evaluator's output
		System.out.println(TimerBenchmark.allToString());
		System.out.println("Avg loss: "+ results.getAvgLoss());
		System.out.println("Avg gain: "+ results.getAvgGain());
		System.out.println("Coverage: "+ results.getCoverage());
		
	}
}
