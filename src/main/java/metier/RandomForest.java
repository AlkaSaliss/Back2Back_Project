package metier;

import metier.RRandomForest.type;

public class RandomForest {

	private int ntrees; //number of trees to aggregate
	private double mtry; // percentage of features to select for each tree
	private double sampsize; // percentage of individuals to take for each tree
	//private int maxDepth; //maximum depth for each tree
	
	
	public RandomForest() {

		this.ntrees = 500;
		this.mtry = 0;
		this.sampsize = 0.7;
	//	this.maxDepth = 10;
	}


	public int getNtrees() {
		return ntrees;
	}


	public void setNtrees(int ntrees) {
		this.ntrees = ntrees;
	}


	public double getMtry() {
		return mtry;
	}


	public void setMtry(double mtry) {
		this.mtry = mtry;
	}


	public double getSampsize() {
		return sampsize;
	}


	public void setSampsize(double sampsize) {
		this.sampsize = sampsize;
	}


}
