package com.bayesianNetwork.minimization;

import com.bayesianNetwork.network.Condition;
import com.bayesianNetwork.network.Value;

/**
 * Represent an relative or absolute rule
 * for minimizing a Bayesian Network
 * @author Ted Gueniche
 *
 */
public class Rule {

	private Condition prefix;
	private Condition suffix;
	
	private double support;
	
	public Rule() {
		prefix = new Condition();
		suffix = new Condition();
		support = 0.0;
	}
	
	public Rule(Condition prefix, Condition suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}
	
	
	public double getSup() {
		return support;
	}
	
	public void addPrefix(String id, Value value) {
		prefix.setValue(id, value);
	}
	
	public void addSuffix(String id, Value value) {
		suffix.setValue(id, value);
	}

}
