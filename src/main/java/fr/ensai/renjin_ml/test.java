package fr.ensai.renjin_ml;

import weka.classifiers.trees.J48;

public class test {

	public test() {
		// TODO Auto-generated constructor stub
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
		
		System.out.println("*************************************************\n");
		
		WekaDecisionTree treeWeka = new WekaDecisionTree("src/main/resources/iris.csv", true);
		J48 model = treeWeka.fit((float)0.7);
		
		treeWeka.predict(model);
		
		System.out.println("*************************************************\n");
		System.out.println(treeWeka.getTest());
	
	    
	  }

}
