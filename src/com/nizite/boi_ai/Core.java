package com.nizite.boi_ai;

import java.io.IOException;

import com.nizite.boi_ai.algorithms.Algorithm;
import com.nizite.boi_ai.algorithms.genetic.Genetic;
import com.nizite.boi_ai.algorithms.tabu.Tabu;
import com.nizite.boi_ai.problems.Problem;
import com.nizite.boi_ai.problems.sudoku.Sudoku;
import com.nizite.boi_ai.representations.Atom;
import com.nizite.boi_ai.representations.Representation;
import com.nizite.boi_ai.representations.sudoku.SquareRandom;
import com.nizite.boi_ai.utils.FileReader;

public class Core {

	public static void main(String[] args) throws NumberFormatException, Exception {

		// basic config stuff
		// setup log
		// decide what problem and solution to use based on config file
		// read problem file and turn on Problem
		// let solution run until it needs to stop (time, memory, iterations)
		
//		sudoku();
		testSudoku();
		//sudokuTabu();
		
		// let solution run until it needs to stop (time, memory, iterations)
		
		System.out.println("Finished execution");
	}
	
	/**
	 * 
	 * @param p
	 * @param r
	 * @param a
	 * @throws Exception 
	 */
	public static void run(Problem p, Representation r, Algorithm a) throws Exception {
		r.setImplementedSoft(p.getSoftConstraints());
		r.setImplementedHard(p.getHardConstraints());
		r.setProblem(p.getInfo());
		a.setRepresentation(r);
		
		a.run();
		Atom solution = a.getBestSolution();
		System.out.println(r.humanize(solution));
		System.out.println(solution.getFitness());
		System.out.println(a.getStats());
	}
	
	public static void testSudoku() throws NumberFormatException, Exception {
		//String problem = FileReader.toString("examples/problems/sudoku/9x9easy.txt"); //easy
		//String problem = FileReader.toString("examples/problems/sudoku/9x9medium.txt"); //medium
		String problem = FileReader.toString("examples/problems/sudoku/9x9hard.txt"); //hard
		//String problem = FileReader.toString("examples/problems/sudoku/25x25medium.txt");
		Problem sudoku = new Sudoku();
		sudoku.config("", "1, 2, 3");
		sudoku.setup(problem);
		
		Representation square = new SquareRandom();

		// read config file and turn on solution
		int iterations = 100000;
		int population = 20;
		double mutation = 0.4;
		double crossover = 0.2;
		Algorithm algorithm = new Genetic();
		algorithm.config(iterations, null, null);
		algorithm.setup(population, mutation, crossover);
		
		run(sudoku, square, algorithm);
	}

//	public static void sudoku() throws NumberFormatException, Exception {	
//		//String problem = FileReader.toString("examples/problems/sudoku/9x9easy.txt"); //easy
//		//String problem = FileReader.toString("examples/problems/sudoku/9x9medium.txt"); //medium
//		String problem = FileReader.toString("examples/problems/sudoku/9x9hard.txt"); //hard
//		//String problem = FileReader.toString("examples/problems/sudoku/25x25medium.txt");
//		Problem sudoku = new Sudoku();
//		sudoku.config("SquareRandom", "", "1, 2, 3");
//		sudoku.setup(problem);
//
//		// read config file and turn on solution
//		int iterations = 100000;
//		int population = 20;
//		double mutation = 0.4;
//		double crossover = 0.2;
//		Algorithm algorithm = new Genetic();
//		algorithm.config(iterations, null, null);
//		algorithm.setProblem(sudoku);
//		algorithm.setup(population, mutation, crossover);
//		algorithm.run();
//		Atom solution = algorithm.getBestSolution();
//		System.out.println(sudoku.getRepresentation().humanize(solution));
//		System.out.println(solution.getFitness());
//		System.out.println(algorithm.getStats());
//	}
//
//	
//	public static void sudokuTabu() throws NumberFormatException, Exception {
//		String problem = FileReader.toString("examples/problems/sudoku/9x9easy.txt"); //easy
//		//String problem = FileReader.toString("examples/problems/sudoku/9x9medium.txt"); //medium
//		//String problem = FileReader.toString("examples/problems/sudoku/9x9hard.txt"); //hard
//		//String problem = FileReader.toString("examples/problems/sudoku/25x25medium.txt"); // freaking hard
//		Problem sudoku = new Sudoku();
//
//		sudoku.config("SquareRandom", "", "1, 2, 3");
//		sudoku.setup(problem);
//
//		// read config file and turn on solution
//		int iterations = 100000;
//		int tabuSize = 30;
//		Algorithm algorithm = new Tabu();
//		algorithm.config(iterations, null, null);
//		algorithm.setProblem(sudoku);
//		algorithm.setup(tabuSize);
//		algorithm.run();
//		Atom solution = algorithm.getBestSolution();
//		System.out.println(sudoku.getRepresentation().humanize(solution));
//		System.out.println(solution.getFitness());
//		System.out.println(algorithm.getStats());
//	}
}
