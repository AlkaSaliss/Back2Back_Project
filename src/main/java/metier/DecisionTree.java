package metier;

import java.io.Serializable;

public class DecisionTree implements Serializable{

	private int maxDepth; // La profondeur de l'arbre
	private int minSplit; //Nombre minimum d'individus qu'il faut pour diviser une feuille
	private double cp ; // Critère de complexité
	private  boolean gini; // Critère utilisée pour calculer l'impurété 
	private int minPerLeaf; //for weka
	private int maxBins; //for SparkML
	
	
	public DecisionTree() {
		this.maxDepth = 30;
		this.minSplit = 20;
		this.cp = 0.01d;
		this.gini = true;
		this.minPerLeaf=15;
		this.maxBins = 2;
	}

	
	public int getMaxBins() {
		return maxBins;
	}


	public void setMaxBins(int maxBins) {
		this.maxBins = maxBins;
	}

	public int getMaxDepth() {
		return maxDepth;
	}
	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}
	public int getMinSplit() {
		return minSplit;
	}
	public void setMinSplit(int minSplit) {
		this.minSplit = minSplit;
	}
	public double getCp() {
		return cp;
	}
	public void setCp(double cp) {
		this.cp = cp;
	}
	public int getMinPerLeaf() {
		return minPerLeaf;
	}
	public void setMinPerLeaf(int minPerLeaf) {
		this.minPerLeaf = minPerLeaf;
	}
	public boolean isGini() {
		return gini;
	}
	public void setGini(boolean gini) {
		this.gini = gini;
	}
	
		
}
