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
	
	
	public MinimizationResult() {
		gain = 0;
		loss = 0;
		count = 0;
		notMatched = 0;
	}
	
	
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
	
	
	public double getAvgGain() {
		return ((double) gain / (count - notMatched));
	}
	
	public double getAvgLoss() {
		return ((double) loss / (count - notMatched));
	}
	
	public double getCoverage() {
		return ((double) (count - notMatched) / count);
	}
}
