package com.bayesianNetwork.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.bayesianNetwork.utilities.IO;

public class Concatenator {

	
	public static void main(String[] args) {
		
		
		List<String> EI = IO.readLinesFromFile("C:\\Users\\Root\\Dropbox\\dev\\projects\\java\\BayesianNetworks\\data\\EI.txt");
		List<String> SN = IO.readLinesFromFile("C:\\Users\\Root\\Dropbox\\dev\\projects\\java\\BayesianNetworks\\data\\SN.txt");
		List<String> JP = IO.readLinesFromFile("C:\\Users\\Root\\Dropbox\\dev\\projects\\java\\BayesianNetworks\\data\\JP.txt");
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\Root\\Dropbox\\dev\\projects\\java\\BayesianNetworks\\data\\All.txt"));
			for(int i = 0 ; i < EI.size(); i++) {
				bw.write(EI.get(i) + "\t" + SN.get(i) + "\t" + JP.get(i) + "\r\n");
			}
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
