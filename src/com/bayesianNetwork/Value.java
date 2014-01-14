package com.bayesianNetwork;

/**
 * Represents a value with a string identifier
 * @author Ted Gueniche
 *
 * @param <T> Value's type
 */
public class Value {

	public String id;
	public Integer value;

	public Value(String id, Integer value) {
		this.id = id;
		this.value = value;
	}
	
	public String toString() {
		return id + ":"+ value;
	}
}
