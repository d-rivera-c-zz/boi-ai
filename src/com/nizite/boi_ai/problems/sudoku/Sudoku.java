package com.nizite.boi_ai.problems.sudoku;

import java.util.ArrayList;

import com.nizite.boi_ai.problems.Problem;
import com.nizite.boi_ai.utils.Parser;

/**
 * Simple sudoku problem.
 * Rules are the same as basic sudoku, but size can be bigger than 9 squares.
 * 
 * @author d-rivera-c
 * @version 0.1
 */
public class Sudoku extends Problem {
	/**
	 * Core size of the squares. If the board is divided first into 3 big squares per row,
	 * then _size is 3
	 */
	private int    _size;
	
	/**
	 * Initial set of filled in number boxes to start with. It can be an empty board too.
	 */
	private String _square;

	/* *********************** */
	/*      DEFINED FUNCS      */
	/* *********************** */

	@Override
	/**
	 * Parses the String problem and also sets it in the representation
	 * @param setup String
	 */
	public void setup(String setup) throws NumberFormatException, Exception {
		this.setProblem(setup);
		this._representation.setImplementedSoft(this.getImplementedSoft());
		this._representation.setImplementedHard(this.getImplementedHard());
		this._representation.setProblem(this._size, this._square);
	}
	
	@Override
	protected void setProblem(String problem) throws Exception {
		String lines[] = problem.split("\\r?\\n");
		
		String square = "";
		for(int i = 2; i < lines.length; i++) {
			square += lines[i] + "\n";
		}

		this.setSize(Parser.stringToInt(lines[0]));
		this.setSquare(square);
	}
	
	/* *********************** */
	/*     OVERLOAD FUNCS      */
	/* *********************** */

	@Override
	/**
	 * Direct the class load to all representations specifically for Sudoku problem
	 * Called by (@link #config())
	 * @param rep
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	protected void setRepresentation(String rep) 
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		rep = "com.nizite.boi_ai.representations.sudoku."+rep;
		super.setRepresentation(rep);
	}


	@Override
	/**
	 * Sudoku problem doesn't have soft constraints,
	 * all constraints need to be fulfilled
	 */
	protected void setSoftConstraints() {
		this._soft = new ArrayList<String>();
	}


	@Override
	/**
	 * All sudoku constraints are hard.
	 * If not all are enforced (@link #setImplementedHard(int[] hard)
	 * the solution will not be what's expected for the problem
	 */
	protected void setHardConstraints() {
		super.setHardConstraints();
		this._hard.add("Each row must have all numbers 1-n^2");
		this._hard.add("Each column must have all numbers 1-n^2");
		this._hard.add("Each n x n square must have all numbers 1-n^2");
	}


	@Override
	protected void setObjectiveFunction() {
		super.setObjectiveFunction();
		this._objectiveFunction = "Minimize hard constrains broken";
	}

	/* *********************** */
	/* PROBLEM PERTINENT FUNCS */
	/* *********************** */

	/**
	 * TODO: docs
	 * @param size
	 */
	protected void setSize(int size) {
		this._size = size;
	}
	
	/**
	 * Strip ".", replace any non digit with 0 for easier parsing on representations,
	 * check that length is this._size^2
	 * @param square
	 * @return
	 * @throws Exception 
	 */
	protected void setSquare(String square) throws Exception {
		if(this._size == 0)
			throw new Exception("Size is not valid");

		_square = square;
	}
	
	/* GETTERS */
	public int getSize(){
		return this._size;
	}

	public String getSquare() {
		return this._square;
	}
}
