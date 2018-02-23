package com.nizite.boi_ai.utils;

import java.util.ArrayList;

/**
 * Utils for parsing numbers (mostly)
 * 
 * @author d-rivera-c
 * @version 0.1
 */
public class Parser {
	
	/**
	 * Transform a string to an int.
	 * It throws exception and doesn't just default the int to 0 because 0 could be a valid value for an int.
	 * Prefer this over plain parseInt to add a "throws" to the call
	 * 
	 * @param toParse string to transform
	 * @return int
	 * @throws NumberFormatException when toParse couldn't be transformed into a valid int
	 */
	public static int stringToInt(String toParse) throws NumberFormatException {
		int parsed = Integer.parseInt(toParse);
		return parsed;
	}
	
	/**
	 * Accepts a string to be use as a separator on split, removes all spaces before and after the separator
	 * @param toParse
	 * @param separator defaults to ","
	 * @return int[] string to int array
	 */
	public static int[] stringToIntArray(String toParse, String separator) {
		if (separator == null || separator.trim().equals(""))
			separator = ",";
		
		ArrayList<Integer> ints = new ArrayList<Integer>();
		String[] numbers = toParse.split( "\\s*" + separator + "\\s*");
		
		// get all valid ints from string
		for(String number : numbers) {
			try {
				int test = Parser.stringToInt(number);
				ints.add(test);
			} catch (NumberFormatException e) {
				// TODO: making this a logger issue
				System.out.println("Problem parsing "+number);
			}
		}

		int[] intsArray = new int[ints.size()];
		int i = 0;
		for (Integer number : ints) {
			intsArray[i] = number.intValue();
			i++;
		}

		return intsArray;
	}

	/**
	 * Splits a string by ","  and returns int[]
	 * 
	 * @param toParse
	 * @return int[]
	 */
	public static int[] stringToIntArray(String toParse) {
		return Parser.stringToIntArray(toParse, null);
	}
	
	/**
	 * Creates a valid string out of an integer. If integer is not valid it will return ""
	 * @param number
	 * @return String int (can be null) to String
	 */
	public static String integerToString(Integer number) {
		if (number == null)
			return "";
		return Integer.toString(number);
	}
}
