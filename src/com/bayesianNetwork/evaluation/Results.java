package com.bayesianNetwork.evaluation;

/**
 * Represent a set of results for the evaluation framework
 * @author Ted Gueniche
 *
 */
public class Results {

	private int goodPrediction;
	private int badPrediction;
	private int goodPredictedValues;
	private int notMatched;
	
	/**
	 * Instantiate an empty result set
	 */
	public Results() {
		goodPrediction = 0;
		badPrediction = 0;
		goodPredictedValues = 0;
		notMatched = 0;
	}
	
	/**
	 * Use the given distance to update the results
	 * @param dist Distance receive from comparing two conditions
	 */
	public void addResult(int dist, int valuesChanged) {
		if(valuesChanged == 0) {
			notMatched++;
		}
		else if(dist == 0) {
			goodPrediction++;
			goodPredictedValues += valuesChanged;
		}
		else {
			badPrediction++;
		}
	}
	
	/**
	 * Calculate the accuracy for this result set
	 * @return Return the accuracy [0,1]
	 */
	public double getAccuracy() {
		return ((double) goodPrediction / (goodPrediction + badPrediction));
	}
	
	/**
	 * Return the average number of dimensions guessed with good predictions
	 */
	public double getAverageGain() {
		return ((double)goodPredictedValues / goodPrediction);
	}
	
	/**
	 * Return the ratio of prediction made against the total number of testing data
	 */
	public double getMatched() {
		return ((double) goodPrediction + badPrediction) / (goodPrediction + badPrediction + notMatched);
	}
}
