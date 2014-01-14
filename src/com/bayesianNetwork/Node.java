package com.bayesianNetwork;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


/**
 * Represents a node in a Bayesian Network
 * @author Ted Gueniche
 *
 */
public class Node {

	private final static int smoothingVal = 1;
	
	private HashMap<Condition, Integer> data; //contains all the data
	private String attributeId; //node's id
	private List<String> parents; //list of parent for this node
	private int totalSupport;
	private List<Node> children; //link to children nodes
	
	/**
	 * Basic constructor
	 */
	public Node(String attributeId) {
		this.attributeId = attributeId;
		data = new HashMap<Condition, Integer>();
		children = new ArrayList<Node>();
		parents = new ArrayList<String>();
		totalSupport = 0;
	}
	
	/**
	 * Add a parent to the node
	 * @param id Id of the parent
	 */
	public void AddParent(String id) {
		parents.add(id);
	}

	/**
	 * Use a condition (a case) to update this node
	 * It removes all the non-covered fields from the condition
	 * @param condition Condition to use to update this node
	 */
	public void processCase(Condition condition) {
		
		Condition minCondition = minimizeCondition(condition);
		
		//Updating the node
		inc(minCondition);
	}
	
	/**
	 * Calculate the probability for this condition
	 * @param condition
	 * @return
	 */
	public double prob(Condition condition) {
		
		double probability = 0.0d;
		Condition minCondition = minimizeCondition(condition);
		Integer support = data.get(minCondition);
		
		if(support == null) {
			support = smoothingVal;
		}
		
		probability = (double) support / totalSupport;
		
		return probability;
	}
	
	/**
	 * Display the node as a list of condition with support
	 * One condition with support per line
	 */
	public String toString() {
		String output = "== Node for "+ attributeId + " ==\n";
		for(Entry<Condition, Integer> entry : data.entrySet()) {
			output += entry.getKey().toString() + "\tSup:"+ entry.getValue() + "\n";	
		}
		
		return output;
	}
	
	/**
	 * Incrementing a condition support by one
	 * @param condition Condition to match
	 */
	private void inc(Condition condition) {
	
		Integer value = data.get(condition);
		if(value == null) {
			value =  smoothingVal;
			totalSupport += smoothingVal + 1;
		} else {
			totalSupport++;
		}
		
		value++;
		data.put(condition, value);
	}
	
	/**
	 * Removes all the fields not covered by this node
	 * @param condition Condition to minimize
	 * @return The minimized condition
	 */
	private Condition minimizeCondition(Condition condition) {	
		Condition minCondition = new Condition();
	
		//adding all the fields from the list of parents
		for(String field : parents) {
			minCondition.setValue(field, condition.getValueById(field));
		}
		
		//adding the node's id value from the condition
		minCondition.setValue(attributeId, condition.getValueById(attributeId));
		
		return minCondition;
	}

	
	public static void main(String[] args) {
		
		Condition c1 = new Condition("1	2	1	2	1	2");
		Condition c2 = new Condition("1	1	1	1	1	1");
		Condition c3 = new Condition("1	1	1	1	2	1");
		Condition c4 = new Condition("1	1	1	2	1	1");
		Condition c5 = new Condition("1	1	2	1	1	2");
		Condition c6 = new Condition("1	1	2	1	1	1");
		Condition c7 = new Condition("1	1	2	1	2	2");
//		Condition c8 = new Condition("1	1	1	2	2	1");
//		Condition c9 = new Condition("1	1	1	1	2	2");
//		Condition c10 = new Condition("2	2	2	2	1	1");
//		Condition c11 = new Condition("2	1	1	2	1	1");
//		Condition c12 = new Condition("2	2	1	1	2	1");
//		Condition c13 = new Condition("2	1	1	1	2	1");
//		Condition c14 = new Condition("1	1	2	1	2	1");
		
		System.out.println(c1);
		
		Node A = new Node("R3");
		A.AddParent("R1");
		A.AddParent("R2");
		
		A.processCase(c1);
		A.processCase(c2);
		A.processCase(c3);
		A.processCase(c4);
		A.processCase(c5);
		A.processCase(c6);
		
		System.out.println(A);
		
		System.out.println(A.prob(new Condition("1	1	0")));
		
	}

}
