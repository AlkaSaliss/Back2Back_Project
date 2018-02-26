package fr.ensai.renjin_ml;

import java.util.List;

import javax.script.ScriptException;

import org.renjin.nmath.df;
//Import des packages pour l'évaluation des expressions R
import org.renjin.script.RenjinScriptEngine;
import org.renjin.script.RenjinScriptEngineFactory;
import org.renjin.sexp.ListVector;

public class RDecisionTree {

	/*
	 * Attributs de l'arbre
	 */
	private String formula;
	//private String dataPath;
	private String method;
	private int col_Y; //column number of y in the dataset 
	
	private RenjinScriptEngineFactory factory = new RenjinScriptEngineFactory();
    // create a Renjin engine:
    private RenjinScriptEngine engine = factory.getScriptEngine();
	
	
	
	
	
	
	public RDecisionTree(String form, String method, int coly) {
		
		this.formula = form;
		//this.dataPath = data;
		this.method = method;
		this.col_Y=coly;
		
		
	}
	
	public void fit(String dataPath, boolean hasRowname) throws ScriptException {
		/*
		 * loading the file
		 */
		if (hasRowname) {
		engine.eval("data <- as.data.frame(read.csv("+dataPath+", header=T, row.names=1))");
		} else {
			engine.eval("data <- as.data.frame(read.csv("+dataPath+", header=T, row.names=NULL))");
		}
		/*
		 * Spliting data into training and test sets
		 */
		//Shuffle the data rows
		engine.eval("set.seed(123)");
		engine.eval("data <- data[sample(1:nrow(data)), ]");
		
		/*engine.eval("n=2*floor(nrow(data)/3)");
		engine.eval("train=data[1:n,]");*/
		trainTestSplit(0.7f);
		
		//load the package for decision tree modeling
		engine.eval("library(rpart)"); 
		engine.eval("res <- rpart("+this.formula + ",data=train)");
		
		
	}
	
	public void predict() throws ScriptException {
		/*
		 * creating the test file
		 */
		
		//TODO
		
		//engine.eval("test <- na.omit(data[n+1:nrow(data),])");
		/*
		 * Prediction
		 */
		engine.eval("pred <- predict(res, test, type=\"class\")");
		//engine.eval("print(test[,"+this.col_Y+"])");
		//engine.eval("print(pred)");
		engine.eval("confMat <- table(test[,"+this.col_Y+"], pred)");
		engine.eval("accuracy <- sum(diag(confMat))/sum(confMat)");
		System.out.println("The tree accuracy is : " + engine.get("accuracy"));
		System.out.println("******************************************\n"
				+ "******************************************\n"
				+ "******************************************\n");
		
		engine.eval("library(partykit)");
		engine.eval("res2 <- as.party(res)");
		engine.eval("print(res2)");
		/*engine.eval("jpeg('/src/main/resources/treePlot.jpg')");
		engine.eval("plot(res2)");
		engine.eval("dev.off()");*/
		
		//engine.eval("plot(res, uniform=TRUE, branch=0.6, margin=0.05)");
		
	}
	
	public void trainTestSplit(float trainPercent) throws ScriptException {
		
		engine.eval("set.seed(123)");
		float prob0 = 1-trainPercent;
		float prob1=trainPercent;
		engine.eval("isTrain <- sample(c(0,1), size=nrow(data), replace=T, prob=c("+prob0+","+prob1+ "))");
		engine.eval("data$isTrain <- isTrain");
		engine.eval("train=data[data$isTrain==1, -ncol(data)]");
		engine.eval("test=data[data$isTrain==0, -ncol(data)]");
		
		
		
		//engine.eval("trainSize = floor(nrow(data)*" + trainPercent + ")");
		//engine.eval("train=data[1:trainSize,]");
		//engine.eval("test <- na.omit(data[trainSize+1:nrow(data),])");
		//engine.eval("test <- data[trainSize+1:nrow(data),-ncol(data)]");
		//engine.eval("test <- data[trainSize+1:nrow(data),-ncol(data)]");
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int getCol_Y() {
		return col_Y;
	}

	public void setCol_Y(int col_Y) {
		this.col_Y = col_Y;
	}

	public RenjinScriptEngineFactory getFactory() {
		return factory;
	}

	public void setFactory(RenjinScriptEngineFactory factory) {
		this.factory = factory;
	}

	public RenjinScriptEngine getEngine() {
		return engine;
	}

	public void setEngine(RenjinScriptEngine engine) {
		this.engine = engine;
	}
	
	
	
	

}
