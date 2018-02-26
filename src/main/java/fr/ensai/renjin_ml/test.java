package fr.ensai.renjin_ml;

import java.util.ArrayList;
import java.util.List;

import org.renjin.sexp.DoubleArrayVector;
import org.renjin.sexp.ListVector;

import weka.classifiers.trees.J48;

public class test {

	public test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		   
		//create the tree object
		RDecisionTree tree = new RDecisionTree("quality~.", "class", 12);
		tree.fit("\"src/main/resources/winequality.csv\"", false);
		tree.predict();
			    
	   // ListVector vect = (ListVector) engine.get("rg");
	  // System.out.println(engine.get("rg$residuals"));
	  // List<String> test = List
	    //System.out.println( vect);
	   // System.out.println(engine.eval(vect.toString()));
	    
	   //System.out.println( df.class);
	    
				
		System.out.println("*************************************************\n");
		
		WekaDecisionTree treeWeka = new WekaDecisionTree("src/main/resources/winequality.csv", false, true);
		J48 model = treeWeka.fit((float)0.7);
		
		//treeWeka.predict(model);
		
		System.out.println("**********************Position***************************\n");
		//System.out.println(treeWeka.getData());
		//System.out.println(treeWeka.getAttributeIndex("chlorides"));
		
		System.out.println("*************************************************\n");
		//System.out.println(tree.getEngine().eval("print(train)"));
		//double a = ((DoubleArrayVector) tree.getEngine().eval("accuracy")).asReal();
		//ListVector a =  (ListVector) tree.getEngine().eval("pred");
				
		//System.out.println(tree.getEngine().eval("print(pred)"));
	
	    
	  }

}
