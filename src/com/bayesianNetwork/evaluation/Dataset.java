package com.bayesianNetwork.evaluation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bayesianNetwork.network.Condition;
import com.bayesianNetwork.utilities.IO;

/**
 * Represent a dataset for the evaluation framework.
 * @author Root
 *
 */
public class Dataset {

	private String path;
	
	/**
	 * Contains the training data, must call prepare to populate
	 */
	public List<Condition> trainingSet;
	
	/**
	 * Contains the testing data, must call prepare to populate
	 */
	public List<Condition> testingSet;
	
	/**
	 * Construct an empty dataset, use prepare() to load the dataset
	 * @param path Path to the data file
	 */
	public Dataset(String path) {
		this.path = path;
		testingSet = new ArrayList<Condition>();
		trainingSet = new ArrayList<Condition>();
	}
	
	
	/**
	 * Split the data into a training set and a testing set
	 * @param trainingRatio The ratio of data to put in the training set
	 */
	public void prepare(double trainingRatio) {
		List<String> fullSet = readFile();
		Collections.shuffle(fullSet);
		
		//calculate the number of data in the training set
		int trainingCount = (int) ((double) fullSet.size() * trainingRatio);
		
		//generating the training set
		List<String> trainingRawSet = fullSet.subList(0, trainingCount);
		for(String str : trainingRawSet) {
			trainingSet.add(new Condition(str));
		}
		
		//generating the testing set
		List<String> testingRawSet = fullSet.subList(trainingCount, fullSet.size() - 1);
		for(String str : testingRawSet) {
			testingSet.add(new Condition(str));
		}
	}
	
	
	/**
	 * Reads the data file
	 */
	private List<String> readFile() {
		return IO.readLinesFromFile(path);
	}
}
