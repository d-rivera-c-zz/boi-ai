package com.nizite.boi_ai;

import java.util.List;

import com.nizite.boi_ai.algorithms.Algorithm;
import com.nizite.boi_ai.algorithms.genetic.Genetic;
import com.nizite.boi_ai.problems.Problem;
import com.nizite.boi_ai.problems.sudoku.Sudoku;
import com.nizite.boi_ai.representations.Atom;
import com.nizite.boi_ai.representations.Representation;

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
			sudoku.setup(problem);
			List<String> constrains = sudoku.getHardConstraints();
			System.out.println("List of hard constraints to abide to:");
			for(String constrain : constrains) {
				System.out.println(constrain);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// read config file and turn on solution
		int iterations = 1;
		int population = 20;
		double mutation = 20;
		double crossover = 50;
		double selection = 100;
		Algorithm algorithm = new Genetic();
		algorithm.config(iterations, null);
		algorithm.setProblem(sudoku);
		algorithm.setup(population, selection, mutation, crossover);
		algorithm.run();
		Atom solution = algorithm.getBestSolution();
		System.out.println(solution.getRepresentation());
		System.out.println(algorithm.getStats());
		
		// let solution run until it needs to stop (time, memory, iterations)
		
		
		System.out.println("Finished execution");
	}

}
