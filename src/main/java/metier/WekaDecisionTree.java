package metier;

import org.renjin.sexp.DoubleArrayVector;

import metier.RDecisionTree.split_criterion;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;

public class WekaDecisionTree  extends WekaModel{

	//private int maxdepth;
	//private int minsplit;
	//private double cp ;
	//private  split_criterion split;

	private int min_per_leaf; 
	private J48 model = new J48(); 
	
	
	public WekaDecisionTree() {
		super();
		//this.maxdepth = 30;
		this.min_per_leaf = 15;
		//this.cp = 0.01d;
		//this.split = split_criterion.gini;
	}

	
	@Override
	public void fit() throws Exception {
		
		this.model.buildClassifier(this.getTrain());
	
	}

	@Override
	public double eval() throws Exception {
		
		Evaluation eval = new Evaluation(this.getTrain());
		
		eval.evaluateModel(model, this.getTest());
		//System.out.println(eval.toSummaryString("\nResults\n======\n", false));
		return 1-eval.errorRate();
	}
}
