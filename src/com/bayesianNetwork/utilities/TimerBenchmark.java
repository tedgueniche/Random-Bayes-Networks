package com.bayesianNetwork.utilities;

import java.util.HashMap;
import java.util.Map.Entry;


/**
 * Class used to measure execution time of code
 * use the start and stop method to start 
 * a timer and to stop it
 */
public class TimerBenchmark {
	
	public static final String TAG = "TimerBenchmark";
	public static HashMap<String, Long> timers;
	
	private TimerBenchmark() {}
	
	/**
	 * Starts a timer in millisecond
	 * @param name name of the timer to start
	 */
	public static void start(String name) {
		
		//Create the timer container if needed
		if(timers == null) {
			timers = new HashMap<String, Long>();
		}
		long curTime = System.currentTimeMillis();
		
		//if this timer did not exists
		if(timers.get(name) == null) {
			//pushing this timer
			timers.put(name, curTime);
		} 
		//if this timer was paused
		else {
			Long diff = timers.get(name);
			timers.put(name, curTime - diff);
		}
	}
	
	/**
	 * Pause an existing timer and return null
	 * @param name Name of the timer
	 * @return the time elapsed since the beginning of the timer or null
	 */
	public static Long pause(String name) {
		Long curTime = System.currentTimeMillis();
		Long startTime = timers.get(name);
		
		//if the timer does not exists then return null
		if(startTime == null) {
			return null;
		}
		
		//saving the time difference
		Long diff = curTime - startTime;
		timers.put(name, diff);
		return diff;
	}
	
	/**
	 * Stops a timer and return the time in millisecond
	 * @param name
	 * @return the time in millisecond or null if the timer does not exists
	 */
	public static Long stop(String name) {
		Long curTime = System.currentTimeMillis();
		Long startTime = timers.get(name);
		
		//reset this timer
		timers.put(name, null);
		
		return (startTime != null) ? (curTime - startTime) : null;
	}
	
	/**
	 * Return the string representation of a specific timer
	 * @param name Name of the time to display
	 */
	public static String toString(String name) {
		Long diff = timers.get(name);
		
		if(diff == null) {
			return "Timer [" + name + "] does not exists";
		} 
		else {
			return "Timer [" + name + "] "+ diff + "ms";
		}
	}
	
	/**
	 * Return the string representation of all the timers,
	 * one per line
	 */
	public static String allToString() {
		
		String output = "";
		for(Entry<String, Long> entry : timers.entrySet()) {
			output += toString(entry.getKey()) + "\n";
		}
		
		return output;
	}
	
}
