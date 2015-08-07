package com.nizite.boi_ai.representations.sudoku;

import java.util.Random;

import com.nizite.boi_ai.representations.Atom;
import com.nizite.boi_ai.representations.Representation;
import com.nizite.boi_ai.utils.Parser;

// representation for sudoku
public class SquareMatrix extends Representation {
	private int _size;
	private String _square;

	@Override
	public void setProblem(Object... problem) {
		this._size = (int) problem[0];
		this._square = (String) problem[1];
		
		_problem = this.transformToRepresentation(this._square);
	}
	
	public void setRepresentation(String representation) {
		this._representation = this.transformToRepresentation(representation);
	}

	@Override
	protected void setSoftConstraints() {
		return;
	}

	@Override
	protected void setHardConstraints() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setObjectiveFunction() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Get blank copy of the space
	 * @return
	 */
	protected Integer[][] getBlankRepresentation() {
		Integer[][] representation = new Integer[this._size*this._size][this._size*this._size];
		return representation;
	}

	/**
	 * Get blank representation, fill it with things, return rep
	 * @param rep
	 * @return
	 */
	protected Integer[][] transformToRepresentation(String rep) {
		Integer[][] representation = this.getBlankRepresentation();
		
		for(int i = 0; i < this._size*this._size; i++) {
			for(int j = 0; j < this._size*this._size; j++) {
				try {
					representation[i][j] = Parser.stringToInt(rep.charAt((i+1) * (j+1)) + "");
				} catch (Exception e) {
					representation[i][j] = null;
				}
			}
		}
		
		return representation;
	}

	@Override
	public String getRepresentation(Atom atom) {
		String representation = "";
		for (int i = 0; i < this._size*this._size; i++) {
			for (int j = 0; j < this._size*this._size; j++) {
				if (this._representation[i][j] != null) {
					representation += Integer.toString(this._representation[i][j]);
				}
			}
			representation += "\r\n";
		}
		
		return representation;
	}

	@Override
	public Atom createAtom() {
		Atom newRep = new SquareMatrixAtom();
		String random = "";
		
		Random rn = new Random();
		for (int i = 0; i < (this._size*this._size*this._size*this._size)+1; i++) {
			int digit = rn.nextInt(9) + 1;
			random += Integer.toString(digit);
		}
		
		newRep.setAtom(random);
		return newRep;
	}

	@Override
	public String atomToString(Atom atom) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int calculateFitness(Atom atom) {
		Integer[][] prop = new Integer[this._size*this._size][this._size*this._size];
		int fitness = 0;
		for (int i = 0; i < this._size*this._size; i++) {
			for (int j = 0; j < this._size*this._size; j++) {
				prop[i][j] = this._representation[i][j];
				if (this._problem[i][j] != null && this._problem[i][j] != 0) {
					prop[i][j] = this._problem[i][j];
				}
				
				fitness += prop[i][j];
			}
		}
		
		int square = this._size*this._size;
		for(int i = 1; i < 10; i++) {
			fitness = fitness - i*square;
		}
		
		return fitness;
	}
}

class SquareMatrixAtom extends Atom {
	public Integer[][] transform(Object atom) {
		return (Integer[][])atom;
	}
}
