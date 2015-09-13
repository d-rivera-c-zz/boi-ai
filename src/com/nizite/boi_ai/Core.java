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

		// basic config stuff
		// setup log
		// decide what problem and solution to use based on config file
		// read problem file and turn on Problem
		// let solution run until it needs to stop (time, memory, iterations)
		
		sudokuEasy();
		//sudokuMedium();
		//sudokuHard();
		System.out.println("Finished execution");
	}

	public static void sudokuEasy() {
		// basic config stuff
		// setup log
		// decide what problem and solution to use based on config file
		// read problem file and turn on Problem
		String problem = "3\n"
					   + "\n"
					   + "--.7--6.-9-\n"
					   + "6--.3--.5--\n"
					   + "1-5-7.9--.6-8-3\n"
					   + "-7-.--.1--8\n"
					   + "2--.8--4.--9\n"
					   + "9--3.--.-7-\n"
					   + "8-1-5.--7.9-3-6\n"
					   + "--6.--3.--4\n"
					   + "-3-.1--8.--";
		Problem sudoku = new Sudoku();
		try {
			sudoku.config("SquareMatrix", "", "1, 2, 3");
			sudoku.setup(problem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// read config file and turn on solution
		int iterations = 100000;
		int population = 20;
		double mutation = 0.2;
		double crossover = 0.8;
		Algorithm algorithm = new Genetic();
		algorithm.config(iterations, null);
		algorithm.setProblem(sudoku);
		algorithm.setup(population, mutation, crossover);
		algorithm.run();
		Atom solution = algorithm.getBestSolution();
		System.out.println(sudoku.getRepresentation().humanize(solution));
		System.out.println(solution.getFitness());
		System.out.println(algorithm.getStats());
		
		// let solution run until it needs to stop (time, memory, iterations)
	}
	
	public static void sudokuMedium() {
		// basic config stuff
		// setup log
		// decide what problem and solution to use based on config file
		// read problem file and turn on Problem
		String problem = "3\n"
					   + "\n"
					   + "3-9-.--.8--6\n"
					   + "--.--.1--\n"
					   + "--.4--2.5-7-\n"
					   + "6--.-8-3.--5\n"
					   + "--8.--.7--\n"
					   + "2--.6-1-.--3\n"
					   + "-1-4.3--9.--\n"
					   + "--5.--.--\n"
					   + "8--3.--.-5-7";
		Problem sudoku = new Sudoku();
		try {
			sudoku.config("SquareMatrix", "", "1, 2, 3");
			sudoku.setup(problem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// read config file and turn on solution
		int iterations = 100000;
		int population = 40;
		double mutation = 0.3;
		double crossover = 0.7;
		Algorithm algorithm = new Genetic();
		algorithm.config(iterations, null);
		algorithm.setProblem(sudoku);
		algorithm.setup(population, mutation, crossover);
		algorithm.run();
		Atom solution = algorithm.getBestSolution();
		System.out.println(sudoku.getRepresentation().humanize(solution));
		System.out.println(solution.getFitness());
		System.out.println(algorithm.getStats());
		
		// let solution run until it needs to stop (time, memory, iterations)
	}
	
	public static void sudokuHard() {
		// basic config stuff
		// setup log
		// decide what problem and solution to use based on config file
		// read problem file and turn on Problem
		String problem = "3\n"
					   + "\n"
					   + "-4-2.--.-9-7\n"
					   + "8--.--.3--\n"
					   + "7-1-.--6.-8-\n"
					   + "--.5--8.--\n"
					   + "--4.--.7--\n"
					   + "--.7--3.--\n"
					   + "-2-.9--.-6-8\n"
					   + "--5.--.--2\n"
					   + "9-7-.--.1-5-";
		Problem sudoku = new Sudoku();
		try {
			sudoku.config("SquareMatrix", "", "1, 2, 3");
			sudoku.setup(problem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// read config file and turn on solution
		int iterations = 10000;
		int population = 20;
		double mutation = 0.3;
		double crossover = 0.7;
		Algorithm algorithm = new Genetic();
		algorithm.config(iterations, null);
		algorithm.setProblem(sudoku);
		algorithm.setup(population, mutation, crossover);
		algorithm.run();
		Atom solution = algorithm.getBestSolution();
		System.out.println(sudoku.getRepresentation().humanize(solution));
		System.out.println(solution.getFitness());
		System.out.println(algorithm.getStats());
		
		// let solution run until it needs to stop (time, memory, iterations)
	}
}
