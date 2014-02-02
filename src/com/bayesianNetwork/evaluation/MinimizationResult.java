package com.bayesianNetwork.evaluation;

/**
 * Represent a set of results for the evaluation framework
 * for the minimization task
 * @author Ted Gueniche
 *
 */
public class MinimizationResult {

	private int gain;
	private double loss;
	private int notMatched;
	private int count;
	
	/**
	 * Instantiate an empty result set
	 */
	public MinimizationResult() {
		gain = 0;
		loss = 0;
		count = 0;
		notMatched = 0;
	}
	
	/**
	 * Add a result for this specific task
	 * @param gain Number of dimension removed
	 * @param loss Quantity of precision lost
	 */
	public void addResult(int gain, double loss) {
		if(gain == 0) {
			notMatched++;
		}
		else {
			this.gain += gain;
			this.loss += loss;
		}
		count++;
	}
	
	/**
	 * Calculate the average gain in this experiment
	 */
	public double getAvgGain() {
		return ((double) gain / (count - notMatched));
	}
	
	/**
	 * Calculate the average loss in this experiment
	 */
	public double getAvgLoss() {
		return ((double) loss / (count - notMatched));
	}
	
	/**
	 */
	public double getCoverage() {
		return ((double) (count - notMatched) / count);
	}
}
