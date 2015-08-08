package com.nizite.boi_ai.algorithms;

import com.nizite.boi_ai.problems.Problem;
import com.nizite.boi_ai.representations.Atom;
import com.nizite.boi_ai.representations.Representation;

public abstract class Algorithm {
	protected Problem _problem;
	protected Integer _iterations;
	protected Integer _time;
	
	protected Representation _representation;
	protected Atom _bestSolution;

	public void setProblem(Problem problem) {
		this._problem = problem;
	}
	
	/**
	 * time in minutes
	 * @param iterations
	 * @param time
	 */
	public void config(Integer iterations, Integer time) {
		this._iterations = iterations;
		this._time = time;
	};
	
	public abstract void setup(Object... setup);
	public abstract void run();
	public abstract Atom getBestSolution();
	public abstract String getStats();
	protected abstract void iteration();
	protected abstract void setRepresentation();
	protected abstract void setBestSolution();
}
