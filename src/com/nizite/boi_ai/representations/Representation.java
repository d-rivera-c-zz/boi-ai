package com.nizite.boi_ai.representations;

import java.util.ArrayList;
import java.util.List;

import com.nizite.boi_ai.problems.Problem;

/**
 * Representation of a problem.
 * It can be a matrix, list, etc.
 * Handles calculation of fitness and transformation from object representation 
 * to string representation to be used across all problems and solutions
 * 
 * @author d-rivera-c
 * @version 0.1
 */
public abstract class Representation {
	/**
	 * Representation of the problem. Used to persist hardcoded restrictions.
	 * Since the definition of the Problem depends on the Rep, it makes sense to store it as an Atom.
	 */
	protected Atom _problem;
	
	/**
	 * List of all functions to determine if a constraint was broken or not.
	 * The constraints to be implemented are determined by 
	 * {@link Representation#_hard} and {@link Representation#_soft}
	 */
	protected List<Lambda> _constraints;
	
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
	 * Base objective function that calculates fitness of solution.
	 * Puts weight on {@link Representation#_hard} and {@link Representation#_soft}
	 * Objective function will always need to be a minimize function to simplify implementation
	 */
	protected Lambda _objective;
	
	/**
	 * Makes sure the objective function is always initialized
	 */
	public Representation() {
		this.setObjectiveFunction();
		this.setConstraints();
	}
	
	/* *********************** */
	/*      ABSTRACT FUNCS     */
	/* *********************** */
	
	/**
	 * Only sets important information of the {@link Problem},
	 * which can be accessed using {@link Problem#getInfo()}
	 * 
	 * @see Representation#_problem
	 * @param problem
	 * @throws Exception
	 */
	public abstract void setProblem(Object[] problem) throws Exception;
	
	/**
	 * @see Representation#_objective
	 */
	protected abstract void setObjectiveFunction();
	
	/**
	 * Creates a blank representation of an atom.
	 * Depending on the implementation, it can be "semi-blank", pre-filled with hard problem constraints.
	 * 
	 * @return Atom
	 */
	public abstract Atom blankAtom();
	
	/**
	 * Creates a new Atom filled with random "solution".
	 * Any new representation creation constrains (like "row needs to have only valid numbers") 
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
	 * Transforms a String-rep (numbers/bits/options separated by "-") to an Atom.
	 * Depends on every implementation of a Rep.
	 * Normally this will need to do a split("-") and put each number in the corresponding place in the rep.
	 * 
	 * @param representation
	 * @return Atom
	 */
	public abstract Atom stringToAtom(String representation);
	
	/**
	 * Transform a representation to a String separating each "option" with "-"
	 * Does not check validity of rep or anything, only transforms.
	 * 
	 * @param atom Atom
	 * @return String
	 */
	public abstract String atomToString(Atom atom);
	
	/**
	 * Transforms an Atom to a human readable version of the rep.
	 * This is different than {@link Representation#atomToString(Atom)} because it's not meant to 
	 * be used within the code process and calculations, only to display the result back to the user.
	 * 
	 * @param atom Atom
	 * @return String
	 */
	public abstract String humanize(Atom atom);
	
	/**
	 * Transform a human readable version of the rep to a String that can be parsed
	 * with {@link Representation#stringToAtom(String)}.
	 * Normally the first setup of the problem (passed here by {@link com.nizite.boi_ai.Core}) 
	 * will be written in a "humanized" string and will need to be parsed by this function.
	 * 
	 * @param rep String
	 * @return Atom
	 * @throws Exception
	 */
	public abstract Atom dehumanize(String rep) throws Exception;
	
	/**
	 * Gets all states a bit (that's it, a cell in the representation) can have.
	 * All sanitization and generic processing are called in {@link Representation#getAllowedStates}.
	 * @param avoidSelf if set removes element from the list so it can't be picked again,
	 *        it will not be enforced if we are left with an empty list after removing self
	 * @return ArrayList<String> states
	 */
	protected abstract ArrayList<String> getStates(String avoidSelf);
	
	/**
	 * Finds all neighbors of a solution.
	 * Depending on the movement function and size of the representation, the number of neighbors 
	 * can increment a lot. Use the "pivot" point (current Atom) to select a subset of neighbors.
	 * If no pivot is implemented, a random pivot will be selected to get a subset of neighbors.
	 * Since no enforcement is done on the "random pivot" selection, Reps can change this accordingly
	 * if it's believed the neighbor function will work better that way.
	 * We don't worry about the size of the neighbors list, if it becomes a problem, the class 
	 * implementing it should do something about it.
	 * 
	 * @param current solution to get the neighbors from.
	 * @return ArraList<Atom> neighbors
	 */
	public abstract ArrayList<Atom> getNeighbors(Atom current);

	
	/* *********************** */
	/*      DEFINED FUNCS      */
	/* *********************** */

	/**
	 * Figures out the "states" a "box" can have.
	 * For example, for sudoku is numbers from 1 to n^2, for scheduling it's all people/equipment/containers
	 * that can be allocated in one space.
	 * 
	 * THINK: allow optional "position"  int/string, could be useful when there's restriction on
	 * solutions and not all the states are acceptable for a rep
	 * THINK: allow null as states
	 * @param avoidSelf if set removes element from the list so it can't be picked again,
	 *        it will not be enforced if we are left with an empty list after removing self
	 * @return List<String> with all possible states.
	 */
	public ArrayList<String> getAllowedStates(String avoidSelf) {
		ArrayList<String> states = this.getStates(avoidSelf);
		
		if (avoidSelf != null) {
			while(states.remove(avoidSelf));
		}
		if (states.size() == 0) {
			return this.getAllowedStates(null);
		}

		return states;
	}

	/* *********************** */
	/*        SETTERS          */
	/* *********************** */

	/**
	 * @see Representation#_constraints
	 */
	protected void setConstraints() {
		_constraints = new ArrayList<Lambda>();
	}
	
	/**
	 * Hard constraints to be enforced, picked by config
	 * follows indexes of {@link Representation#_hard}
	 * 
	 * @param hard int[]
	 */
	public void setImplementedHard(int[] hard) {
		_hard = new ArrayList<Lambda>();

		for (int s: hard) {
			_hard.add(_constraints.get((s - 1)));
		}
	};

	/**
	 * Soft constraints to be enforced, picked by config
	 * follows indexes of {@link Representation#_soft}
	 * 
	 * @param soft int[]
	 */
	public void setImplementedSoft(int[] soft) {
		_soft = new ArrayList<Lambda>();
		
		for (int s: soft) {
			_soft.add(_constraints.get((s - 1)));
		}
	};
	
	/* *********************** */
	/*        GETTERS          */
	/* *********************** */
	
	/**
	 * Getter for {@link Representation#_problem}
	 * @return Atom problem
	 */
	public Atom getProblem() {
		return _problem;
	}
}

