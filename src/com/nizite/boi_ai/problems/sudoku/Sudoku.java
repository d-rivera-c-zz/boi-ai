package com.nizite.boi_ai.problems.sudoku;

import java.util.ArrayList;
import java.util.List;

import com.nizite.boi_ai.problems.Problem;
import com.nizite.boi_ai.representations.Representation;
import com.nizite.boi_ai.utils.Parser;


public class Sudoku extends Problem {
	private int    _size;
	private String _square;


	@Override
	public void init(String setup) {
		String lines[] = setup.split("\\r?\\n");
		
		String square = "";
		for(int i = 2; i < lines.length; i++) {
			square += lines[i];
		}
		
		this.setup(Parser.stringToInt(lines[0]), square);
	}


	@Override
	public Representation getRepresentation() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Representation getProblem() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<String> getSoftConstraints() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<String> getHardConstraints() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<String> getImplementedSoft() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<String> getImplementedHard() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getObjectiveFunction() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected void setup(Object... config) {
		this._size = (int) config[0];
		this._square = (String) config[1];
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
	protected void setProblem(String problem) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}
	
	
	/* GETTERS */
	public int getSize(){
		return this._size;
	}

	public String getSquare() {
		return this._square;
	}
}
