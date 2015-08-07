package com.nizite.boi_ai.problems.sudoku;

import java.util.ArrayList;

import com.nizite.boi_ai.problems.Problem;
import com.nizite.boi_ai.utils.Parser;


public class Sudoku extends Problem {
	private int    _size;
	private String _square;


	@Override
	public void setup(String setup) throws NumberFormatException, Exception {
		this.setProblem(setup);
		this._representation.setImplementedSoft(this.getImplementedSoft());
		this._representation.setImplementedHard(this.getImplementedHard());
		this._representation.setProblem(this._size, this._square);
	}

	@Override
	/**
	 * Direct the class load to all representations specifically for Sudoku problem
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
	protected void setProblem(String problem) throws Exception {
		String lines[] = problem.split("\\r?\\n");
		
		String square = "";
		for(int i = 2; i < lines.length; i++) {
			square += lines[i];
		}

		this.setSize(Parser.stringToInt(lines[0]));
		this.setSquare(square);
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

	/* SETTERS */

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
		
		square = square.trim();
		square = square.replace(".", "");

		// TODO: consider using Math library
		if(square.length() != (this._size * this._size * this._size * this._size))
			throw new Exception("Length of square doesn't match size given");
		
		String cleanSquare = "";
		for(char digit : square.toCharArray()) {
			String cleanDigit = digit + "";
			try {
				Parser.stringToInt(cleanDigit);
				cleanSquare += cleanDigit;
			} catch (Exception e) {
				cleanSquare += "0";
			}
		}
		
		this._square = cleanSquare;
	}
	
	/* GETTERS */
	public int getSize(){
		return this._size;
	}

	public String getSquare() {
		return this._square;
	}
}
