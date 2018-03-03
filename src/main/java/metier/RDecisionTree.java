package metier;

import javax.script.ScriptException;

import org.renjin.sexp.DoubleArrayVector;

public class RDecisionTree extends RModel {

	private int maxdepth;
	private int minsplit;
	private double cp ;
	private  split_criterion split;
	
	
	
	public RDecisionTree() {
		
		super();
		this.maxdepth = 30;
		this.minsplit = 20;
		this.cp = 0.01d;
		this.split = split_criterion.gini;
	}
	
	@Override
	public void fit() throws Exception {
		
		String params  = String.join(",", "maxdepth="+this.maxdepth, "minsplit = " + this.minsplit, "cp="+this.cp, "split="+this.split);
		super.getEngine().eval("");
		super.getEngine().eval("library(rpart)"); 
		//super.getEngine().eval("dtree <- rpart(formula, " + ",data=train,"+params+ ")");
		super.getEngine().eval("dtree <- rpart(formula, " + ",data=train,"+ ")");

	}

	@Override
	public double eval() throws Exception {
		super.getEngine().eval("pred <- predict(dtree, test, type=\"class\" )");
		//engine.eval("print(pred)");
		super.getEngine().eval("confMat <- table(test[,targetName], pred)");
		super.getEngine().eval("accuracy <- sum(diag(confMat))/sum(confMat)");
		return  ((DoubleArrayVector) super.getEngine().eval("accuracy")).asReal();
		
	}
	
	enum split_criterion{
		information, gini
	}

	public int getMaxdepth() {
		return maxdepth;
	}

	public void setMaxdepth(int maxdepth) {
		this.maxdepth = maxdepth;
	}

	public int getMinsplit() {
		return minsplit;
	}

	public void setMinsplit(int minsplit) {
		this.minsplit = minsplit;
	}

	public double getCp() {
		return cp;
	}

	public void setCp(double cp) {
		this.cp = cp;
	}

	public split_criterion getSplit() {
		return split;
	}

	public void setSplit(split_criterion split) {
		this.split = split;
	}
	

}
