package com.bayesianNetwork.network;
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

	public final static int smoothingVal = 0;
	
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
	 * Get the id of this node's id
	 */
	public String getId() {
		return attributeId;
	}
	
	/**
	 * Add a parent to the node
	 * @param id Id of the parent
	 */
	public void AddParent(String id) {
		parents.add(id);
	}
	
	/**
	 * Count the number of parents
	 */
	public int ParentSize() {
		return parents.size();
	}
	
	/**
	 * Check whether this node has the given parent
	 * @param id Id of the parent to check
	 * @return True if found, false otherwise
	 */
	public boolean hasParent(String id) {
		for(String parent : parents) {
			if(id.compareTo(parent) == 0) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Add a child to the node
	 * @param id Id of the parent
	 */
	public void AddChild(Node child) {
		children.add(child);
	}
	
	/**
	 * Get the list of children node for this node
	 * @return A list of unordered nodes
	 */
	public List<Node> getChildren() {
		return children;
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
		
		Condition minCondition = minimizeCondition(condition);
		
		//Getting the support for this condition
		double probability = 0.0d;
		Integer support = getSupport(minCondition);
		
		//Calculating the probability for this condition against this node
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
		List<Condition> possibleConditions = Condition.getConcretes(condition);
		for(Condition c : possibleConditions) {
		
			Integer value = getSupport(c);
			
			totalSupport++;
			value++;
			data.put(c, value);
		}
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
	
	/**
	 * Calculate the support for a given condition. For a condition
	 * containing wildcards, the support is the sum of the support of 
	 * all the possible concrete form of the condition
	 * @return Absolute support
	 */
	private Integer getSupport(Condition condition) {
		int support = 0;		
		
		//Sum all the possible concrete form of the condition
		List<Condition> possibleConditions = Condition.getConcretes(condition);
		for(Condition c : possibleConditions) {
			support += (data.get(c) == null) ? 0 : data.get(c);
		}
		
		return support;
	}

	/**
	 * Basic implementation:
	 * if there is only one condition then its support is 100%
	 * @return A list of invariant conditions
	 */
	private List<Condition> getInvariant() {
		
		List<Condition> conditions = new ArrayList<Condition>();
		
		if(data.size() == 1) {
			conditions.add(data.keySet().iterator().next());
		}
		
		return conditions;
	}
	
	
	public static void main(String[] args) {
		
		Condition c1 = new Condition("1	2	1	2	1	2");
		Condition c2 = new Condition("1	1	1	1	1	1");
		Condition c3 = new Condition("1	1	2	1	2	1");
		Condition c4 = new Condition("1	1	1	2	1	1");
		Condition c5 = new Condition("1	1	2	1	1	2");
		Condition c6 = new Condition("1	1	2	1	1	1");
		
		System.out.println(c1);
		
		Node A = new Node("R3");
		A.AddParent("R1");
		A.AddParent("R2");
		
		A.processCase(c1);
		A.processCase(c2);
		A.processCase(c3);
//		A.processCase(c4);
//		A.processCase(c5);
//		A.processCase(c6);
		
		System.out.println(A);
		
		System.out.println(A.prob(new Condition("1	1	*")));
		
		List<Condition> invariants = A.getInvariant();
		for(Condition c : invariants) {
			System.out.println(c);
		}
		
	}

}
