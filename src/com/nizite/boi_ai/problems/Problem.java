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
	 * Basic representation to be used.
	 * Add problem definition, defined by initial space.
	 */
	protected Representation _representation;
	
	/**
	 * Hardcoded constraints.
	 * Since soft/hard constraints can change depending on what is being analyzed, 
	 * all constraints are put on the same list, and on program run can be selected.
	 * Only a list of human readable string, real work of calculation
	 * will be done by {@link Representation#calculateFitness(com.nizite.boi_ai.representations.Atom)}
	 */
	protected List<String> _constraints;
	
	/**
	 * Soft constraints to be enforced.
	 * Set up in {@link Problem#config(String, String, String), picked by the user.
	 * Follows the indexes set in {@link Problem#_soft}
	 */
	protected int[] _implementedSoft;
	
	/**
	 * Hard constraints to be enforced.
	 * Set up in {@link Problem#config(String, String, String)}, picked by the user.
	 * Follows the indexes set in {@link Problem#_hard}
	 */
	protected int[] _implementedHard;
	
	/**
	 * Text-only explanation of what the objective function should do.
	 * The actual implementation is tied to the representation.
	 */
	protected String _objectiveFunction;
	
	
	/* *********************** */
	/*      ABSTRACT FUNCS     */
	/* *********************** */
	
	/**
	 * Receives a String read from a config file and parses it
	 * in its important parts (unique to each problem).
	 * Saves in the unique variables all info needed.
	 * Also instantiates Representation with things set up in config and picks SC and HC
	 * 
	 * @param setup
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public abstract void setup(String setup) throws NumberFormatException, Exception;
	
	
	/**
	 * TODO: check exceptions
	 * 
	 * Sets problem specific variables.
	 * Should be used to parse a String defining the whole problem and divide it into different
	 * variables.
	 * Should be called by {@link Problem#setup(String)}
	 * 
	 * @param problem String that defines the problem
	 * @throws Exception
	 */
	protected abstract void setProblem(String problem) throws Exception;

	
	/* *********************** */
	/*      DEFINED FUNCS      */
	/* *********************** */
	
	/**
	 * Sets what representation is going to be used and how many soft & hard constraints
	 * to be aware of. Mainly to be passed to Representation instance.
	 * 
	 * @param rep
	 * @param soft
	 * @param hard
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public void config(String rep, String soft, String hard) 
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		this.setConstraints();
		this.setObjectiveFunction();
		
		this.setRepresentation(rep);
		_representation.setImplementedSoft(Parser.stringToIntArray(soft));
		_representation.setImplementedHard(Parser.stringToIntArray(hard));
	};	
	

	/**
	 * TODO: doesn't make sure class actually exists
	 * TODO: check the exception thrown and reduce/compress them
	 * 
	 * Creates a new Representation object, with the subclass detailed in {@link Problem#config(String, String, String)}.
	 * The representation is stored in {@link Problem#_representation}
	 * 
	 * @param rep name of the {@link Representation} class to use
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	protected void setRepresentation(String rep) 
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> c = Class.forName(rep);
		this._representation = (Representation) c.newInstance();
	};
	
	/**
	 * Initialized {@link Problem#_constraints} as an empty list
	 */
	protected void setConstraints() {
		_constraints = new ArrayList<String>();
	}

	/**
	 * Initializes text-only objective function as empty string
	 */
	protected void setObjectiveFunction() {
		this._objectiveFunction = "";
	};
	
	/**
	 * @see Problem#_representation
	 */
	public Representation getRepresentation() {
		return this._representation;
	};
	
	/**
	 * @see Problem#_constraints
	 */
	public List<String> getConstraints() {
		return _constraints;
	}
	
	/**
	 * @see Problem#_objectiveFunction
	 */
	public String getObjectiveFunction() {
		return this._objectiveFunction;
	};
}
