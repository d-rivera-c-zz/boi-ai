package com.nizite.boi_ai.problems;

import java.util.ArrayList;
import java.util.List;

import com.nizite.boi_ai.representations.Representation;
import com.nizite.boi_ai.utils.Parser;

/**
 * Base implementation to define an AI problem.
 * Should leave search space and boundaries really clear,
 * as well as all info necessary for a Representation and Algorithm to resolve.
 * It does not resolve anything by itself.
 * 
 * @author d-rivera-c
 * @version 0.1
 */
public abstract class Problem {
	
	/**
	 * Hardcoded constraints in plain text. 
	 * 
	 * The determination if a constraint is broken or not depends solely on representation 
	 * (calculating if there's three nurses in a shift changes between a matrix and a linked list implementation). 
	 * Therefore this is only a list of human readable strings, real work of calculation
	 * will be done by {@link Representation#calculateFitness} and {@link Representation#setConstraints}.
	 * 
	 * Since soft/hard constraints can change depending on what is being analyzed, 
	 * all constraints are put on the same list, and on program run it can be selected which to enforce as hard 
	 * or soft.
	 * 
	 */
	protected List<String> _constraints;
	
	/**
	 * Soft constraints to be enforced.
	 * Set up in {@link Problem#config(String, String)}, picked by the user.
	 * Follows the indexes set in {@link Problem#_constraints}
	 */
	protected int[] _soft;
	
	/**
	 * Hard constraints to be enforced.
	 * Set up in {@link Problem#config(String, String)}, picked by the user.
	 * Follows the indexes set in {@link Problem#_constraints}
	 */
	protected int[] _hard;
	
	/**
	 * Text-only explanation of what the objective function should do.
	 * The actual implementation is tied to the representation in {@link Representation#setObjectiveFunction}
	 */
	protected String _objectiveFunction;
	
	
	/* *********************** */
	/*      ABSTRACT FUNCS     */
	/* *********************** */
	
	/**
	 * Receives a String read from a config file and parses it in its important parts (unique to each problem).
	 * Sets problem specific variables. Should be used to parse a String defining the whole problem and divide 
	 * it into different variables. 
	 *
	 * @param setup
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public abstract void setup(String setup) throws NumberFormatException, Exception;
	
	/**
	 * The only way to get the problem's detail info after parsing and organizing the setup file.
	 * It's up to {@link Representation} to organize this into an Atom to be handled within its class.
	 * 
	 * @return Object[] with all the parsed info of the problem.
	 */
	public abstract Object[] getInfo();
	
	/* *********************** */
	/*      DEFINED FUNCS      */
	/* *********************** */
	
	/**
	 * Sets how many soft & hard constraints to be aware of. 
	 * While it doesn't do anything with it, all the config and setup is stored in the Problem to be
	 * accessible by Representations and Algorithms if needed.
	 * 
	 * @param soft
	 * @param hard
	 */
	public void config(String soft, String hard) 
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		this.setConstraints();
		this.setObjectiveFunction();

		this.setSoftConstraints(soft);
		this.setHardConstraints(hard);

	};
	
	/* *********************** */
	/*        SETTERS          */
	/* *********************** */
	
	/**
	 * Initializes text-only objective function as empty string
	 */
	protected void setObjectiveFunction() {
		_objectiveFunction = "";
	};
	
	/**
	 * Initialized {@link Problem#_constraints} as an empty list
	 */
	protected void setConstraints() {
		_constraints = new ArrayList<String>();
	}
	
	/**
	 * Set soft constraints that needs to be enforced.
	 * 
	 * @param soft
	 */
	protected void setSoftConstraints(String soft) {
		_soft = Parser.stringToIntArray(soft);
	}
	
	/**
	 * Set hard constraints that needs to be enforced.
	 * 
	 * @param hard
	 */
	protected void setHardConstraints(String hard) {
		_hard = Parser.stringToIntArray(hard);
	}
	
	/* *********************** */
	/*        GETTERS          */
	/* *********************** */
	
	/**
	 * @see Problem#_constraints
	 */
	public List<String> getConstraints() {
		return _constraints;
	}
	
	/**
	 * @see Problem#_soft
	 */
	public int[] getSoftConstraints() {
		return _soft;
	}
	
	/**
	 * Gets array of strings with the soft constraints that needs to be implemented as defined by the user.
	 * 
	 * @return String[]
	 */
	public String[] getSoftConstraintsAsString() {
		if (_soft == null || _soft.length == 0)
			return (new String[0]);

		String[] constraints = new String[_soft.length];
		for (int i = 0; i < _soft.length; i++) {
			constraints[i] = _constraints.get(_soft[i] - 1);
		}
		
		return constraints;
	}
	
	/**
	 * @see Problem#_hard
	 */
	public int[] getHardConstraints() {
		return _hard;
	}
	
	/**
	 * Gets array of strings with the hard constraints that needs to be implemented as defined by the user.
	 * 
	 * @return String[]
	 */
	public String[] getHardConstraintsAsString() {
		if (_hard == null || _hard.length == 0)
			return (new String[0]);

		String[] constraints = new String[_hard.length];
		for (int i = 0; i < _hard.length; i++) {
			constraints[i] = _constraints.get(_hard[i] - 1);
		}
		
		return constraints;
	}
	
	/**
	 * @see Problem#_objectiveFunction
	 */
	public String getObjectiveFunction() {
		return _objectiveFunction;
	};
}
