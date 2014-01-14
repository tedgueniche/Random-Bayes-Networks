package com.bayesianNetwork;
import java.util.Map.Entry;
import java.util.TreeMap;


/**
 * Represent a condition in a Bayesian Node 
 * or as a entry in the database
 * @author Ted Gueniche
 *
 */
public class Condition {

	/*
	 * fields is automatically sorted so that two
	 * conditions with the same fields are represented the same, 
	 * even if the values were inserted in a different order
	 */
	private TreeMap<String, Value> fields;
	
	public Condition() {
		fields = new TreeMap<String, Value>();
	}
	
	public Condition(String input) {
		fields = new TreeMap<String, Value>();
		
		String[] values = input.split("\t");
		int index = 1;
		for(String value : values) {
			String id = "R" + index;
			fields.put(id, new Value(id, Integer.parseInt(value)));
			index++;
		}
	}
	
	public void setValue(String id, Value val) {
		fields.put(id, val);
	}
	
	public Value getValueById(String id) {
		return fields.get(id);
	}
	
	public String toString() {
		String output = "";
		for(Entry<String, Value> entry : fields.entrySet()) {
			output += entry.getValue().toString() + " ";
		}
		return output;
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		return other.hashCode() == hashCode();
	}
	
	
	
	
}
