package com.nizite.boi_ai.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {

	public static BufferedReader toBufferReader(String path) throws IOException {
		Path p = Paths.get(path);
		BufferedReader reader = Files.newBufferedReader(p);
		return reader;
	}
	
	public static String toString(BufferedReader reader) throws IOException {
		String content = "";
		String line = reader.readLine();
		while (line != null) {
			content += line+"\n";
			line = reader.readLine();
		}
		
		return content;
	}
	
	// if receiving string to parse to string, we assume it's a path to a file
	public static String toString(String path) throws IOException {
		BufferedReader reader = FileReader.toBufferReader(path);
		String content = FileReader.toString(reader);
		FileReader.close(reader);
		return content;
	}
	
	public static void close(BufferedReader reader) throws IOException {
		reader.close();
	}
}