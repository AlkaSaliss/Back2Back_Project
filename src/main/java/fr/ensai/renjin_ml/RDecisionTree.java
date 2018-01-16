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
	
	public void fit(String dataPath) throws ScriptException {
		/*
		 * loading the file
		 */
		engine.eval("data <- as.data.frame(read.csv("+dataPath+", header=T, row.names=1))");
		/*
		 * Spliting data into training and test sets
		 */
		//Shuffle the data rows
		engine.eval("set.seed(123)");
		engine.eval("data <- data[sample(1:nrow(data)), ]");
		
		engine.eval("n=2*floor(nrow(data)/3)");
		engine.eval("train=data[1:n,]");
		
		//load the package for decision tree modeling
		engine.eval("library(rpart)"); 
		engine.eval("res <- rpart("+this.formula + ",data=train)");
		
		
	}
	
	public void predict() throws ScriptException {
		/*
		 * creating the test file
		 */
		
		//TODO
		
		engine.eval("test <- na.omit(data[n+1:nrow(data),])");
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
	
	
	
	public static void main(String[] args) throws Exception {
	   
		//create the tree object
		RDecisionTree tree = new RDecisionTree("Species~.", "class", 5);
		tree.fit("\"src/main/resources/iris.csv\"");
		tree.predict();
		
		
		// create a script engine manager:
	   /* RenjinScriptEngineFactory factory = new RenjinScriptEngineFactory();
	    // create a Renjin engine:
	    RenjinScriptEngine engine = factory.getScriptEngine();

	    engine.eval("dat <- data.frame(x=1:10, y=(1:10)+rnorm(n=10))");
	    engine.eval("print(typeof(dat))");*/
	    
	 /*   String chemin = "\"src/main/resources/iris.csv\"";
	    engine.eval("data <- read.csv(" + chemin + ", header=T, row.names=1)");*/
	    //engine.eval("print(data)");
	  //  engine.eval("print(lm(y ~ x, df))");
	   /* engine.eval("rg <- lm(y ~ x, dat)");
	    
	    //engine.eval("install.packages(\"rpart\")");
		engine.eval("library(rpart)");
	    
	   // ListVector vect = (ListVector) engine.get("rg");
	  // System.out.println(engine.get("rg$residuals"));
	  // List<String> test = List
	    //System.out.println( vect);
	   // System.out.println(engine.eval(vect.toString()));
	    
	   //System.out.println( df.class);
	    
		System.out.println( engine.eval("packageVersion(\"rpart\")"));*/
		
		/*engine.eval("n=2*floor(nrow(data)/3)");
		engine.eval("train=data[1:n,]");
		engine.eval("library(rpart)"); //load the package
		engine.eval("fit <- rpart(Species ~ ., data=train)");*/
	    
	  }

}
