package com.nizite.boi_ai.representations;

public abstract class Representation {
	/**
	 * soft constraints to be enforced, picked by config
	 * follows indexes of _soft
	 */
	protected int[] _implementedSoft;
	
	/**
	 * hard constraints to be enforced, picked by config
	 * follows indexes of _hard
	 */
	protected int[] _implementedHard;
	
	protected Atom _problem;
	
	
	public abstract void setProblem(Object... problem); //might be easier to have the problem with the rep and not as a separate uniq rep

	
	public void setObjectiveGoal() {
		// set min or max, if function needs to be more or less
	}
	
	public abstract Atom createAtom();
	
	public abstract String atomToString(Atom atom);
	
	public abstract int calculateFitness(Atom atom);
	
	/**
	 * TODO: docs
	 * @param soft
	 */
	//TODO check that all index in implementer are in soft
	public void setImplementedSoft(int[] soft) {
		this._implementedSoft = soft;
	};
	
	/**
	 * 
	 * @param hard
	 */
	//TODO check that all index in implemented are in hard
	public void setImplementedHard(int[] hard) {
		this._implementedHard = hard;
	};
	
	protected abstract void setSoftConstraints();
	protected abstract void setHardConstraints();
	protected abstract void setObjectiveFunction();
	
}

