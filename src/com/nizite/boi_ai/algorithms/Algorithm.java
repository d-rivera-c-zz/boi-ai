package com.nizite.boi_ai.algorithms;

import com.nizite.boi_ai.representations.Atom;
import com.nizite.boi_ai.representations.Representation;

/**
 * Implementation of solutions.
 * Base for each algorithm to implement.
 * 
 * @author d-rivera-c
 * @version 0.1
 */
public abstract class Algorithm {
	/**
	 * Number of iterations to run.
	 * Normally if this is set, the {@link Algorithm#_time} variable will not be set
	 */
	protected Integer _iterations;
	
	/**
	 * Time limit to run the iterations.
	 * Normally if this is set, the {@link Algorithm#_iterations} variable will not be set
	 * 
	 * TODO @todo this
	 */
	protected Integer _time;
	
	/**
	 * Counts the number of iterations an algorithm is allowed to run on the same point of the search space.
	 * When an algorithm get stuck in one point of the search space for too long, it's a good idea
	 * to make incentives to explore other spaces.
	 * Equal null if not required.
	 * 
	 * TODO @todo break after x iterations if best solution remains the same (check if it's sane to do it)
	 */
	protected Integer _explorationBreakpoint;
	
	/**
	 * Representation object. Used to get the Problem Atom and calculate fitness and get Atoms
	 */
	protected Representation _representation;
	
	/**
	 * Holder for the best solution found so far
	 */
	protected Atom _bestSolution;

	/* *********************** */
	/*      ABSTRACT FUNCS     */
	/* *********************** */

	/**
	 * Handles algorithm pertinent setup, like percentage of mutations or 
	 * number of individuals, length of list, etc
	 * 
	 * @param setup
	 * @throws Exception 
	 */
	public abstract void setup(Object... setup) throws Exception;
	
	/**
	 * How one loop of an iteration should be handled
	 */
	protected abstract void iteration();
	
	/* *********************** */
	/*      DEFINED FUNCS      */
	/* *********************** */
	
	/**
	 * Configuration used by all algorithms.
	 * If more than one is set (example: time and iterations), then whichever is reached first will be the stop criteria
	 * TODO @todo So far only cares about stop criteria
	 * 
	 * @param iterations
	 * @param time
	 * @param explorationBP
	 */
	public void config(Integer iterations, Integer time, Integer explorationBP) {
		_iterations = iterations;
		_time = time;
		_explorationBreakpoint = explorationBP;
	};
	
	/**
	 * Runs the loops until time or number of loops is reached
	 * TODO @todo time stop, exploration breakpoint stop
	 */
	public void run() {
		for (int i = 0; i < _iterations; i++) {
			this.iteration();
			
			//TODO @todo change for logger
			if (i%1000 == 0)
				System.out.println("Iteration "+i);
		}
	};
	
	/* *********************** */
	/*        SETTERS          */
	/* *********************** */
	
	/**
	 * Validates that representation is valid (not null)
	 * 
	 * @see Algorithm#_representation
	 * @throws Exception 
	 */
	public void setRepresentation(Representation representation) throws Exception {
		if (representation == null)
			throw new Exception("Representation should not be null");

		_representation = representation;
	};
	
	/**
	 * @see Algorithm#_bestSolution
	 * 
	 * @param best
	 */
	protected void setBestSolution(Atom best) {
		_bestSolution = best;
	};
	
	/* *********************** */
	/*        GETTERS          */
	/* *********************** */
	
	/**
	 * @see Algorithm#_bestSolution
	 * @return Atom
	 */
	public Atom getBestSolution() {
		return _bestSolution;
	};
	
	/**
	 * Calculates stats, like time taken to run, number of loops, 
	 * improvements in best solutions.
	 * Used for statistics and comparition purposes.
	 * 
	 * TODO @todo this
	 * 
	 * @return String
	 */
	public String getStats() {
		return null;
	};
}
