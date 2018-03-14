package metier;

//import javax.script.ScriptException;

import org.renjin.sexp.DoubleArrayVector;

public class RDecisionTree extends RModel {

	private DecisionTree dtree=new DecisionTree();
	private boolean classif; //to speify weither classification or regression
	
	
	public RDecisionTree() {
		
		super();
	}
	
	public void setCompleteData(Data d) throws Exception {
		super.setCompleteData(d);
		this.classif = d.isClassif();
	}
	
	@Override
	public void fit() throws Exception {
		String splitCriteria=this.dtree.isGini()?"gini":"information";
		String options  = String.join(",", "control=rpart.control(maxdepth="+this.dtree.getMaxDepth(), "minsplit = " + this.dtree.getMinSplit(), "cp="+this.dtree.getCp() + ")", "parms = list(split= '"+ splitCriteria+"')");

		if(this.classif) {
			options += ",method = '"+"class'";
		}
		else {
			options += ",method = '"+"anova'";
		}
		
		this.getEngine().eval("library(rpart)"); 
		this.getEngine().eval("dtree <- rpart(formula, " + ",data=train, " + options + ")");

	}

	

	@Override
	public double eval() throws Exception {
		if(this.classif) {
		super.getEngine().eval("pred <- predict(dtree, test, type=\"class\" )");
		//engine.eval("print(pred)");
		super.getEngine().eval("confMat <- table(test[,targetName], pred)");
		super.getEngine().eval("accuracy <- sum(diag(confMat))/sum(confMat)");
		return  ((DoubleArrayVector) super.getEngine().eval("accuracy")).asReal();
		}
		else {
			super.getEngine().eval("pred <- predict(dtree, test)");
			super.getEngine().eval("mse=sum((test[,targetName]-pred)**2)/nrow(test)");
			return  ((DoubleArrayVector) super.getEngine().eval("mse")).asReal();
			
		}
	}



}
