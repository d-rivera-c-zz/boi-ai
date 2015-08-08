package com.nizite.boi_ai.representations;

import java.util.ArrayList;
import java.util.List;

public abstract class Representation {
	protected List<Lambda> _hard;
	protected List<Lambda> _soft;
	protected Lambda _objective;
	
	protected Atom _problem;
	
	public Representation() {
		this.setObjectiveFunction();
	}
	
	public abstract void setProblem(Object... problem); //might be easier to have the problem with the rep and not as a separate uniq rep
	
	public abstract Atom blankAtom();
	
	public abstract Atom createAtom();
	
	public abstract Atom stringToAtom(String representation);
	
	public abstract String atomToString(Atom atom);
	
	public abstract double calculateFitness(Atom atom);
	
	/**
	 * TODO: docs
	 * @param soft
	 */
	/**
	 * soft constraints to be enforced, picked by config
	 * follows indexes of _soft
	 */
	//TODO check that all index in implementer are in soft
	public void setImplementedSoft(int[] soft) {
		this.setSoftConstraints(soft);
	};
	
	/**
	 * 
	 * @param hard
	 */
	/**
	 * hard constraints to be enforced, picked by config
	 * follows indexes of _hard
	 */
	//TODO check that all index in implemented are in hard
	public void setImplementedHard(int[] hard) {
		this.setHardConstraints(hard);
	};
	
	protected void setSoftConstraints(int[] soft) {
		_soft = new ArrayList<Lambda>();
	};
	
	protected void setHardConstraints(int[] hard) {
		_hard = new ArrayList<Lambda>();
	};
	
	protected abstract void setObjectiveFunction();
	
}

