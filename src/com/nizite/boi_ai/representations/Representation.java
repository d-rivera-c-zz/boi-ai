package com.nizite.boi_ai.representations;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a problem.
 * It can be a matrix, list, etc.
 * Handles calculation of fitness and transformation from object representation 
 * to string representation to be used across all problems and solutions
 * @author d-rivera-c
 * @version 0.1
 */
public abstract class Representation {
	/**
	 * Representation of the problem
	 * Used to persist hardcoded restrictions
	 * Problem is set within the rep ti avoid mixing Problem and Rep all the time
	 * Since the definition of the Problem depends on the Rep, it makes sense to store it as an Atom
	 */
	protected Atom _problem;
	
	/**
	 * List of functions associated with hard constrains
	 * All constrains numbered on {@link Problem} needs to be implemented
	 */
	protected List<Lambda> _hard;
	
	/**
	 * List of function associated with soft constrains
	 * All constrains numbered on {@link Problem} needs to be implemented
	 */
	protected List<Lambda> _soft;
	
	/**
	 * Base objective function that calculates fitness of solution
	 * Puts weight on {@link Representation#_hard} and {@link Representation#_soft}
	 * Objective function will always need to be a minimize function to simplify implementation
	 */
	protected Lambda _objective;
	
	/**
	 * Makes sure the objective function is always initialized
	 */
	public Representation() {
		this.setObjectiveFunction();
	}
	
	/* *********************** */
	/*      ABSTRACT FUNCS     */
	/* *********************** */
	
	/**
	 * @see Representation#_problem
	 * @param problem
	 * @throws Exception
	 */
	public abstract void setProblem(Object... problem) throws Exception;
	
	/**
	 * Creates a blank representation of an atom
	 * @return Atom
	 */
	public abstract Atom blankAtom();
	
	/**
	 * Creates a new Atom filled with random "solution"
	 * Any new representation creation constrains (like "row needs to have only valid numbers" 
	 * needs to be defined here
	 * @return Atom
	 */
	public abstract Atom createAtom();
	
	/**
	 * Calculate fitness of an atom based on the objective function defined
	 * @param atom Atom
	 * @return double
	 */
	public abstract double calculateFitness(Atom atom);
	
	/**
	 * Transforms a String-rep (numbers/bits/options separated by "-") to an Atom
	 * Depends on every implementation of a Rep.
	 * Normally this will need to do a split("-") and put each number in the corresponding place in the rep
	 * @param representation
	 * @return Atom
	 */
	public abstract Atom stringToAtom(String representation);
	
	/**
	 * Transform a representation to a String separating each "option" with "-"
	 * Does not check validity of rep or anything, only transforms
	 * @param atom Atom
	 * @return String
	 */
	public abstract String atomToString(Atom atom);
	
	/**
	 * Transforms an Atom to a human readable version of the rep.
	 * This is different than {@link Representation#atomToString(Atom)} because it's not meant to 
	 * be used within the code process and calculations, only to display the result back to the user
	 * @param atom Atom
	 * @return String
	 */
	public abstract String humanize(Atom atom);
	
	/**
	 * Transform a human readable version of the rep to a String that can be parsed
	 * with {@link Representation#stringToAtom(String)}.
	 * Normally the first setup of the problem (passed here by {@link Core} 
	 * will be written in a "humanized" string and will need to be parsed by this function
	 * @param rep String
	 * @return String
	 * @throws Exception
	 */
	public abstract String dehumanize(String rep) throws Exception;
	
	/* *********************** */
	/*      DEFINED FUNCS      */
	/* *********************** */

	/**
	 * Soft constraints to be enforced, picked by config
	 * follows indexes of {@link Representation#_soft}
	 * 
	 * TODO check that all indexes are implemented in soft
	 * @param soft int[]
	 */
	public void setImplementedSoft(int[] soft) {
		this.setSoftConstraints(soft);
	};
	
	/**
	 * Hard constraints to be enforced, picked by config
	 * follows indexes of {@link Representation#_hard}
	 * 
	 * TODO check that all indexes are implemented in hard
	 * @param hard int[]
	 */
	public void setImplementedHard(int[] hard) {
		this.setHardConstraints(hard);
	};
	
	/**
	 * Sets basic soft constrains, all implementations need to extend this
	 * @param soft int[]
	 */
	protected void setSoftConstraints(int[] soft) {
		_soft = new ArrayList<Lambda>();
	};
	
	/**
	 * Sets basic hard constrains, all implementations need to extend this
	 * @param hard
	 */
	protected void setHardConstraints(int[] hard) {
		_hard = new ArrayList<Lambda>();
	};
	
	/**
	 * @see Representation#_objective
	 */
	protected abstract void setObjectiveFunction();
	
	/**
	 * Getter for {@link Representation#_problem}
	 * @return
	 */
	public Atom getProblem() {
		return _problem;
	}
	
	
	/* PROBLEM SPECIFIC FUNCTIONS */
	//TODO try to get rid of this
	public abstract String mutate(String premutation);
	
}

