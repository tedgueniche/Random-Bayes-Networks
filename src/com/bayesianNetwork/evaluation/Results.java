package com.bayesianNetwork.evaluation;

/**
 * Represent a set of results for the evaluation framework
 * @author Ted Gueniche
 *
 */
public class Results {

	private int goodPredictedValues;
	private int badPredictedValues;
	private int predicted;
	private int changed;
	private int count;
	
	/**
	 * Instantiate an empty result set
	 */
	public Results() {
		goodPredictedValues = 0;
		badPredictedValues = 0;
		predicted = 0;
		changed = 0;
		count = 0;
	}
	
	/**
	 * Use the given distance to update the results
	 * @param dist Distance receive from comparing two conditions
	 */
	public void addResult(int dist, int predicted, int changed) {
		goodPredictedValues += predicted - dist;
		badPredictedValues += dist;
		this.predicted += predicted;
		this.changed += changed;
		count++;
	}
	
	/**
	 * Calculate the accuracy for this result set
	 * @return Return the accuracy [0,1]
	 */
	public double getAccuracy() {
		return ((double) goodPredictedValues / (goodPredictedValues + badPredictedValues));
	}
	
	/**
	 * Return the average number of dimensions guessed with good predictions
	 */
	public double getAverageGain() {
		return ((double) goodPredictedValues / count);
	}
	
	/**
	 * Return the ratio of prediction made against the total number of testing data
	 */
	public double getMatched() {
		return ((double) predicted / changed);
	}
}
