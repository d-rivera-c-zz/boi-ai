package com.nizite.boi_ai.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Helper for reading files and converting them to appropriate input/output types
 * 
 * @author d-rivera-c
 * @version 0.1
 */
public class FileReader {

	/**
	 * Converts a file (identified by path) to a buffered reader
	 * 
	 * @param path
	 * @return BufferedReader of the file
	 * @throws IOException when file not found
	 */
	public static BufferedReader toBufferReader(String path) throws IOException {
		Path p = Paths.get(path);
		BufferedReader reader = Files.newBufferedReader(p);
		return reader;
	}
	
	/**
	 * Converts a reader to string.
	 * 
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public static String toString(BufferedReader reader) throws IOException {
		String content = "";
		String line = reader.readLine();
		while (line != null) {
			content += line+"\n";
			line = reader.readLine();
		}
		
		return content;
	}
	
	/**
	 * Its assumed that the string received as parameter is the path of the file which should be 
	 * transformed to string.
	 * 
	 * @param path
	 * @return String
	 * @throws IOException
	 */
	public static String toString(String path) throws IOException {
		BufferedReader reader = FileReader.toBufferReader(path);
		String content = FileReader.toString(reader);
		FileReader.close(reader);
		return content;
	}
	
	/**
	 * Closes the buffer.
	 * 
	 * @param reader
	 * @throws IOException
	 */
	public static void close(BufferedReader reader) throws IOException {
		reader.close();
	}
}