package com.bayesianNetwork.network;

/**
 * Represents a value with a string identifier
 * @author Ted Gueniche
 *
 * @param <T> Value's type
 */
public class Value {

	/**
	 * Id of the value
	 */
	public String id;
	
	/**
	 * Actual value as an Integer
	 */
	public Integer value;

	
	/**
	 * Inclusive range of possible values
	 */
	public static final int minRange = 1;
	public static final int maxRange = 2;
	
	public Value(Value other) {
		id = new String(other.id);
		value = (other.value == null) ? null : new Integer(other.value);
	}
	
	/**
	 * Constructor from an id and a value
	 */
	public Value(String id, Integer value) {
		this.id = id;
		this.value = value;
	}
	
	public String toString() {
		return id + ":"+ ((value == null)? "*" : value);
	}
}
