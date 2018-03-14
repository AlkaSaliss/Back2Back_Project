package metier;

import java.io.Serializable;

import metier.RRandomForest.type;

public class RandomForest implements Serializable{

	private int ntrees; //number of trees to aggregate
	private double mtry; // percentage of features to select for each tree
	private double sampsize; // percentage of individuals to take for each tree
	private int maxDepth; //maximum depth for each tree (sparkml)
	private int maxBins; //spark
	private int seed; //spark
	private String featureSubsetStrategy = "auto"; //type of algorithm (spark)
	private boolean gini;
	
	
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


	public int getMaxDepth() {
		return maxDepth;
	}


	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}


	public int getMaxBins() {
		return maxBins;
	}


	public void setMaxBins(int maxBins) {
		this.maxBins = maxBins;
	}


	public int getSeed() {
		return seed;
	}


	public void setSeed(int seed) {
		this.seed = seed;
	}


	public String getFeatureSubsetStrategy() {
		return featureSubsetStrategy;
	}


	public void setFeatureSubsetStrategy(String featureSubsetStrategy) {
		this.featureSubsetStrategy = featureSubsetStrategy;
	}


	public boolean isGini() {
		return gini;
	}


	public void setGini(boolean gini) {
		this.gini = gini;
	}


}
