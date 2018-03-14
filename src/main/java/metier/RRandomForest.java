package metier;

import javax.script.ScriptException;

import org.renjin.sexp.DoubleArrayVector;

public class RRandomForest extends RModel {
	
	private RandomForest rf = new RandomForest();
	private boolean classif; //to speify weither classification or regression

	public RRandomForest() throws Exception {
		
		super();
		
	}
	
	public void setCompleteData(Data d) throws Exception {
		super.setCompleteData(d);
		this.classif = d.isClassif();
	}

	@Override
	public void fit() throws Exception {
		
		if (this.rf.getMtry()==0) { this.getEngine().eval("mtry <- floor(sqrt(ncol(data)-1))"); }
		else { this.getEngine().eval("mtry <- floor((ncol(data)-1)*" + this.rf.getMtry()+")");}
		
		String options  = String.join(",", "mtry=mtry ", "ntree = " + this.rf.getNtrees(), "sampsize=floor(nrow(train)* "+this.rf.getSampsize() + ")");
		
		if (this.classif) {
			options += ",type ='classification'";
		}
		else {
			options += ",type ='regression'";
		}
		
		this.getEngine().eval("library(randomForest)"); 
		this.getEngine().eval("rf <- randomForest(formula, " + ",data=train, " + options + ")");

	}


//	@Override
	public double eval() throws Exception {
		
		if (this.classif) {
		this.getEngine().eval("pred <- predict(rf, test, type=\"class\" )");
		//engine.eval("print(pred)");
		this.getEngine().eval("confMat <- table(test[,targetName], pred)");
		this.getEngine().eval("accuracy <- sum(diag(confMat))/sum(confMat)");
		return  ((DoubleArrayVector) this.getEngine().eval("accuracy")).asReal();
		}
		else {
			super.getEngine().eval("pred <- predict(rf, test)");
			super.getEngine().eval("mse=sum((test[,targetName]-pred)**2)/nrow(test)");
			return  ((DoubleArrayVector) super.getEngine().eval("mse")).asReal();
		}
		
	}

	
	enum type {
		classification ("classification"),
		regression ("regression"),
		unsupervized ("unsupervized");
		
		/*
		 * get the svm method name as required by R (with "-"), as java doesnt accept variable names with "-"*/
		private String method;
		type(String method) { this.method = method;}
		
		public String toString() {
			return this.method;
		}
	}

}
