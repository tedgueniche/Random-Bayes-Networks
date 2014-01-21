package com.bayesianNetwork.network;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;


/**
 * Represent a condition in a Bayesian Node 
 * or as a entry in the database
 * @author Ted Gueniche
 *
 */
public class Condition implements Cloneable {

	/**
	 * fields is automatically sorted so that two
	 * conditions with the same fields are represented the same, 
	 * even if the values were inserted in a different order
	 */
	private TreeMap<String, Value> fields;
	
	/**
	 * Number of "wildcard" values (unknown values) in this
	 * condition 
	 */
	private Integer wildCardCount;
	
	/**
	 * Generic constructor, creates an empty condition
	 */
	public Condition() {
		fields = new TreeMap<String, Value>();
		wildCardCount = 0;
	}
	
	/**
	 * Construct a condition from its string representation
	 * values are tab separated.
	 */
	public Condition(String input) {
		fields = new TreeMap<String, Value>();
		wildCardCount = 0;
		
		String[] values = input.split("\t");
		int index = 1;
		for(String value : values) {
			
			//generating the index
			String id = "R" + index;
			
			//if the value is unknown (symbolized by *)
			if(value.compareTo("*") == 0) {
				fields.put(id, new Value(id, null));
				wildCardCount++;
			} 
			else {
				fields.put(id, new Value(id, Integer.parseInt(value)));
			}
			index++;
		}
	}
	
	/**
	 * Set a specific value in the condition
	 * @param id Id of the value
	 * @param val Actual value
	 */
	public void setValue(String id, Value val) {
		
		//incrementing the number of wildcard if needed
		if(val.value == null) {
			wildCardCount++;
		}
		else if(fields.get(id) != null && fields.get(id).value == null) {
			wildCardCount--;
		}
		
		fields.put(id, val);
	}
	
	/**
	 * Returns a value given its id
	 */
	public Value getValueById(String id) {
		return fields.get(id);
	}
	
	/**
	 * Return the number of wildCard in this condition
	 * @return
	 */
	public Integer countWildcards() {
		return wildCardCount;
	}
	

	
	public static List<Condition> getConcretes(Condition condition) {
		
		List<Condition> conditions = new ArrayList<Condition>();
		
		if(condition.countWildcards() == 0) {
			conditions.add(condition);
		}
		else {
			getConcretesHelper(condition, conditions, 0);
		}
		return conditions;
	}
	
	private static void getConcretesHelper(Condition condition, List<Condition> conditions, int offset) {
		
		int index = 0;
		for(Entry<String, Value> entry : condition.fields.entrySet()) {
			if(index < offset) {
				index++;
				continue;
			}
			if(entry.getValue().value == null) {
				for(int i = Value.minRange ; i <= Value.maxRange ; i++) {
					Condition c =  condition.clone();
					c.setValue(entry.getKey(), new Value(entry.getKey(), i));
					
					if(c.countWildcards() == 0) {
						conditions.add(c);
					}
					else {
						getConcretesHelper(c, conditions, (index + 1));
					}
				}
				
				break;
			}
		}
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
	
	@Override
	public Condition clone() {
		Condition c = new Condition();
		c.fields = new TreeMap<>();
		for(Entry<String, Value> entry : fields.entrySet()) {
			c.fields.put(new String(entry.getKey()), new Value(entry.getValue()));
		}
		c.wildCardCount = new Integer(wildCardCount);
		return c;
	}
	
	
}
