package com.nizite.boi_ai;

import com.nizite.boi_ai.problems.Problem;
import com.nizite.boi_ai.problems.sudoku.Sudoku;

public class Core {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// basic config stuff
		// setup log
		// decide what problem and solution to use based on config file
		// read problem file and turn on Problem
		String problem = "3\n"
					   + "\n"
					   + "--1.--3.--4\n"
					   + "4--.3--.---\n"
					   + "---.---.---\n"
					   + "...........\n"
					   + "---.---.---\n"
					   + "---.---.---\n"
					   + "---.---.---\n"
					   + "...........\n"
					   + "---.---.---\n"
					   + "---.---.---\n"
					   + "---.---.---";
		Problem sudoku = new Sudoku();
		try {
			sudoku.config("SquareMatrix", "", "1, 2, 3");
			//sudoku.init(problem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// read config file and turn on solution
		int iterations = 3;
		int population = 20;
		int mutation = 20;
		int crossover = 50;
		
		// let solution run until it needs to stop (time, memory, iterations)
	}

}
