package com.nizite.boi_ai;

import com.nizite.boi_ai.algorithms.Algorithm;
import com.nizite.boi_ai.algorithms.genetic.Genetic;
import com.nizite.boi_ai.algorithms.tabu.Tabu;
import com.nizite.boi_ai.problems.Problem;
import com.nizite.boi_ai.problems.sudoku.Sudoku;
import com.nizite.boi_ai.representations.Atom;
import com.nizite.boi_ai.utils.FileReader;

public class Core {

	public static void main(String[] args) throws NumberFormatException, Exception {

		// basic config stuff
		// setup log
		// decide what problem and solution to use based on config file
		// read problem file and turn on Problem
		// let solution run until it needs to stop (time, memory, iterations)
		
		sudoku();
		//sudokuTabu();
		
		// let solution run until it needs to stop (time, memory, iterations)
		
		System.out.println("Finished execution");
	}

	public static void sudoku() throws NumberFormatException, Exception {	
		//String problem = FileReader.toString("examples/problems/sudoku/9x9easy.txt"); //easy
		String problem = FileReader.toString("examples/problems/sudoku/9x9medium.txt"); //medium
		//String problem = FileReader.toString("examples/problems/sudoku/9x9hard.txt"); //hard
		Problem sudoku = new Sudoku();
		sudoku.config("SquareRandom", "", "1, 2, 3");
		sudoku.setup(problem);

		// read config file and turn on solution
		int iterations = 100000;
		int population = 20;
		double mutation = 0.2;
		double crossover = 0.8;
		Algorithm algorithm = new Genetic();
		algorithm.config(iterations, null, null);
		algorithm.setProblem(sudoku);
		algorithm.setup(population, mutation, crossover);
		algorithm.run();
		Atom solution = algorithm.getBestSolution();
		System.out.println(sudoku.getRepresentation().humanize(solution));
		System.out.println(solution.getFitness());
		System.out.println(algorithm.getStats());
	}

	
	public static void sudokuTabu() throws NumberFormatException, Exception {
		//String problem = FileReader.toString("examples/problems/sudoku/9x9easy.txt"); //easy
		String problem = FileReader.toString("examples/problems/sudoku/9x9medium.txt"); //medium
		//String problem = FileReader.toString("examples/problems/sudoku/9x9hard.txt"); //hard
		Problem sudoku = new Sudoku();

		sudoku.config("SquareValidRows", "", "1, 2, 3");
		sudoku.setup(problem);

		// read config file and turn on solution
		int iterations = 10000;
		int tabuSize = 10;
		Algorithm algorithm = new Tabu();
		algorithm.config(iterations, null, null);
		algorithm.setProblem(sudoku);
		algorithm.setup(tabuSize);
		algorithm.run();
		Atom solution = algorithm.getBestSolution();
		System.out.println(sudoku.getRepresentation().humanize(solution));
		System.out.println(solution.getFitness());
		System.out.println(algorithm.getStats());
	}
}
