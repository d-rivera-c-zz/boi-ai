package com.nizite.boi_ai.problems.sudoku;

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
	private int _size;
	
	/**
	 * Initial set of filled in number boxes to start with. It can be an empty board too.
	 * See `examples` folder for ideas on board definition.
	 */
	private String _square;

	/* *********************** */
	/*      DEFINED FUNCS      */
	/* *********************** */

	/**
	 * Parses the String problem and also sets it in the representation
	 * TODO @todo: compress every exception into exception to bubble it up
	 * 
	 * @param setup String
	 */
	@Override
	public void setup(String setup) throws NumberFormatException, Exception {
		// splits the sudoku squares to parse it into an object
		String lines[] = setup.split("\\r?\\n");
		
		String square = "";
		for (int i = 2; i < lines.length; i++) {
			square += lines[i] + "\n";
		}

		this.setSize(Parser.stringToInt(lines[0]));
		this.setSquare(square);
	}
	
	/**
	 * Gives all details parsed from the setup initial String as objects.
	 * Usually used by {@link com.nizite.boi_ai.representations.Representation}
	 * 
	 * @return Object[] size and square
	 */
	@Override
	public Object[] getInfo() {
		Object[] info = new Object[2];
		info[0] = _size;
		info[1] = _square;
		return info;
	}
	
	/* *********************** */
	/*     OVERLOAD FUNCS      */
	/* *********************** */

	/**
	 * All sudoku constraints are hard.
	 * If not all are enforced (@link #setImplementedHard(int[] hard)
	 * the solution will not be what's expected for the problem, but the user still has the choice.
	 * TODO @todo add rules for magic sudokus?
	 * 
	 * @see Problem#setConstraints
	 */
	protected void setConstraints() {
		super.setConstraints();
		_constraints.add("Each row must have all numbers 1-n^2");
		_constraints.add("Each column must have all numbers 1-n^2");
		_constraints.add("Each n x n square must have all numbers 1-n^2");
	}

	/**
	 * @see Problem#setObjectiveFunction
	 */
	protected void setObjectiveFunction() {
		super.setObjectiveFunction();
		_objectiveFunction = "Minimize hard constrains broken";
	}

	/* *********************** */
	/* PROBLEM PERTINENT FUNCS */
	/* *********************** */

	/**
	 * While this could be calculated from _square itself, it's not certain the square will
	 * be made correctly, so this is added as an extra check.
	 * 
	 * @see Sudoku#_size
	 * @param size
	 * @throws Exception
	 */
	protected void setSize(int size) throws Exception {
		if (size == 0)
			throw new Exception("Size is not valid");

		_size = size;
	}
	
	/**
	 * TODO @todo: Strip ".", replace any non digit with 0 for easier parsing on representations,
	 * TODO @todo: check that length is _size^2
	 * 
	 * @see Sudoku#_square
	 * @param square
	 * @throws Exception 
	 */
	protected void setSquare(String square) throws Exception {
		_square = square;
	}
	
	/* *********************** */
	/*        GETTERS          */
	/* *********************** */
	
	/**
	 * @see Sudoku#_size
	 */
	public int getSize() {
		return _size;
	}

	/**
	 * @see Sudoku#_square
	 */
	public String getSquare() {
		return _square;
	}
}
