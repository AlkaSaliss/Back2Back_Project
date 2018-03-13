package metier;

import metier.DecisionTree.splitCriterion;

public class DecisionTree {

	private int maxDepth; // La profondeur de l'arbre
	private int minSplit; //Nombre minimum d'individus qu'il faut pour diviser une feuille
	private double cp ; // Critère de complexité
	private  splitCriterion splitCrit; // Critère utilisée pour calculer l'impurété 
	private int minPerLeaf; //for weka
	
	
	public DecisionTree() {
		this.maxDepth = 30;
		this.minSplit = 20;
		this.cp = 0.01d;
		this.splitCrit = splitCriterion.gini;
		this.minPerLeaf=15;
	}

	
	enum splitCriterion{
		information, gini
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
	public splitCriterion getSplitCrit() {
		return splitCrit;
	}
	public void setSplitCrit(splitCriterion splitCrit) {
		this.splitCrit = splitCrit;
	}
	public int getMinPerLeaf() {
		return minPerLeaf;
	}
	public void setMinPerLeaf(int minPerLeaf) {
		this.minPerLeaf = minPerLeaf;
	}
	
	
	
	
	
}
