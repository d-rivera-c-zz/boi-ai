package com.nizite.boi_ai.utils;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * TODO: doc
 * @author experiments
 *
 */
public class Parser {
	
	/**
	 * TODO: doc
	 * @param toParse
	 * @return
	 */
	public static int stringToInt(String toParse) throws NumberFormatException {
		int parsed = Integer.parseInt(toParse);
		return parsed;
	}
	
	/**
	 * TODO: doc
	 * @param toParse
	 * @return
	 */
	public static int[] stringToIntArray(String toParse) {
		ArrayList<Integer> ints = new ArrayList<Integer>();
		String[] numbers = toParse.split(",\\s*");
		
		// get all valid ints from string
		for(String number : numbers) {
			try {
				int test = Parser.stringToInt(number);
				ints.add(test);
			} catch (NumberFormatException e) {
				// TODO: making this a logger issue
				System.out.println("Problem parsing");
			}
		}

		int[] intsArray = new int[ints.size()];
	    Iterator<Integer> iterator = ints.iterator();
	    for (int i = 0; i < intsArray.length; i++) {
	    	Integer x = iterator.next();
	    	if (x != null)
	    		intsArray[i] = x.intValue();
	    }

		return intsArray;
	}
}
