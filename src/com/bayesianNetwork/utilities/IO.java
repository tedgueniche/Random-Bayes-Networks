package com.bayesianNetwork.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IO {

	
	/**
	 * Read a file and return the list of lines read
	 * @param path Path to the file
	 * @return Return the list or null on error
	 */
	public static List<String> readLinesFromFile(String path) {
		
		List<String> lines = new ArrayList<String>();
		String line;
		BufferedReader br;
		
		try {
			br = new BufferedReader(new FileReader(path));
			
			while( (line = br.readLine()) != null) {
				lines.add(line);
			}
			
			br.close();
		} catch (IOException e) {
			System.out.println(e);
			lines = null;
		}
		
		return lines;
	}
	
	

}
