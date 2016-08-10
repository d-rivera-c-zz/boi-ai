/**
 * 
 */
package com.nizite.boi_ai.utils;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.*;


/**
 * @author d-rivera-c
 *
 */
public class ParserTest {


	/**
	 * Test method for {@link com.nizite.boi_ai.utils.Parser#stringToInt(java.lang.String)}.
	 * Mainly testing that an exception is thrown (loudly, because an exception is thrown by parseInt anyway
	 */
	@Test
	public void testStringToInt() {
	    try {
	        Parser.stringToInt("-nothing at all like a number");
	        fail("NumberFormatException expected");
	    } catch (Exception e) {
	    	assertThat(e, instanceOf(NumberFormatException.class));
	    }
	    
	    // numbers are parsed
	    assertThat(Parser.stringToInt("1"), is(1));
	}

	/**
	 * Test method for {@link com.nizite.boi_ai.utils.Parser#stringToIntArray(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testStringToIntArrayStringString() {
		// empty string, empty array
		assertArrayEquals(Parser.stringToIntArray("", ","), new int[]{});
		// string with , but separator different, result empty string?
		assertArrayEquals(Parser.stringToIntArray("1,2,3", "-"), new int[]{});
		// string with with space between separator
		assertArrayEquals(Parser.stringToIntArray("1,    2     ,    3", ","), new int[]{1,2,3});
		// string with invalid ints, strip invalid ints from array
		assertArrayEquals(Parser.stringToIntArray("1,a,3", ","), new int[]{1,3});
		// string full of valid ints
		assertArrayEquals(Parser.stringToIntArray("1,2,3", ","), new int[]{1,2,3});
	}

	/**
	 * Overload method. For extensive test see {@link testStringToIntArrayStringString}
	 * Test method for {@link com.nizite.boi_ai.utils.Parser#stringToIntArray(java.lang.String)}.
	 */
	@Test
	public void testStringToIntArrayString() {
		// not using default separator
		assertArrayEquals(Parser.stringToIntArray("1-2-3"), new int[]{});
		// use default separator ,
		assertArrayEquals(Parser.stringToIntArray("1,2,3"), new int[]{1,2,3});
	}

	/**
	 * Test method for {@link com.nizite.boi_ai.utils.Parser#integerToString(java.lang.Integer)}.
	 */
	@Test
	public void testIntegerToString() {
		fail("Not yet implemented");
	}

}
