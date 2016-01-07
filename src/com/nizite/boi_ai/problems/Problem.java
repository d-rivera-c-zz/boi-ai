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
	 * Hardcoded soft constraints. 
	 * Only a list of human readable string, real work of calculation
	 * will be done by {@link Representation#calculateFitness(com.nizite.boi_ai.representations.Atom)}
	 */
	protected List<String> _soft;
	
	/**
	 * Hardcoded hard constraints. 
	 * Only a list of human readable string, real work of calculation 
	 * will be done by {@link Representation#calculateFitness(com.nizite.boi_ai.representations.Atom)
	 */
	protected List<String> _hard;
	
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
		this.setSoftConstraints();
		this.setHardConstraints();
		this.setObjectiveFunction();
		this.setImplementedSoft(Parser.stringToIntArray(soft));
		this.setImplementedHard(Parser.stringToIntArray(hard));
		
		this.setRepresentation(rep);
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
	 * Initializes {@link Problem#_soft} as an empty list
	 */
	protected void setSoftConstraints() {
		this._soft = new ArrayList<String>();
	};
	
	/**
	 * Initializes {@link Problem#_hard} as an empty list
	 */
	protected void setHardConstraints() {
		this._hard = new ArrayList<String>();
	};
	
	/**
	 * TODO: check that the numbers are within the _soft array keys
	 * 
	 * Initializes {@link Problem#_implementedSoft} with an array
	 * 
	 * @param soft array of constraints to implement
	 */
	protected void setImplementedSoft(int[] soft) {
		this._implementedSoft = soft;
	};
	
	/**
	 * TODO check that all the numbers in array are within the _hard keys
	 * 
	 * Initializes {@link Problem#_implementedHard} with an array
	 * 
	 * @param hard array of constraints to implement
	 */
	protected void setImplementedHard(int[] hard) {
		this._implementedHard = hard;
	};
	
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
	 * @see Problem#_soft
	 */
	public List<String> getSoftConstraints() {
		return this._soft;
	};
	
	/**
	 * @see Problem#_hard
	 */
	public List<String> getHardConstraints() {
		return this._hard;
	};
	
	/**
	 * @see Problem#_implementedSoft
	 */
	public int[] getImplementedSoft() {
		return this._implementedSoft;
	}
	
	/**
	 * @see Problem#_implementedHard
	 */
	public int[] getImplementedHard() {
		return this._implementedHard;
	}
	
	/**
	 * Gets implemented soft constrains selected as a text-readable list
	 * @see Problem#_soft
	 * @return ArrayList<String> text of implemented soft constrains
	 */
	public List<String> getImplementedSoftAsList() {
		ArrayList<String> implemented = new ArrayList<String>();
		for(int imp : this._implementedSoft) {
			implemented.add(this._soft.get(imp));
		}
		return implemented;
	};
	
	/**
	 * Gets implemented hard constrains selected as a text-readable list
	 * @see Problem#_hard
	 * @return ArrayList<String> text of implemented hard constrains
	 */
	public List<String> getImplementedHardAsList() {
		ArrayList<String> implemented = new ArrayList<String>();
		for(int imp : this._implementedHard) {
			implemented.add(this._soft.get(imp));
		}
		return implemented;		
	};
	
	/**
	 * @see Problem#_objectiveFunction
	 */
	public String getObjectiveFunction() {
		return this._objectiveFunction;
	};
}
