package fr.ensai.renjin_ml;

import java.util.ArrayList;
import java.util.List;


import org.renjin.script.RenjinScriptEngine;
import org.renjin.script.RenjinScriptEngineFactory;
import org.renjin.sexp.DoubleArrayVector;
import org.renjin.sexp.ListVector;

import weka.classifiers.trees.J48;
import weka.knowledgeflow.Data;
import metier.*;

public class test {

	public test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		   
		//create the tree object
		//RDecisionTree tree = new RDecisionTree("quality~.", "class", 12);
		//tree.fit("\"src/main/resources/winequality.csv\"", false);
		//tree.predict();
			    
	   // ListVector vect = (ListVector) engine.get("rg");
	  // System.out.println(engine.get("rg$residuals"));
	  // List<String> test = List
	    //System.out.println( vect);
	   // System.out.println(engine.eval(vect.toString()));
	    
	   //System.out.println( df.class);
	    
		
		//WekaDecisionTree treeWeka = new WekaDecisionTree("src/main/resources/winequality.csv", false, true);
		//J48 model = treeWeka.fit((float)0.7);
		
		//treeWeka.predict(model);
		
		//System.out.println("**********************Position***************************\n");
		//System.out.println(treeWeka.getData());
		//System.out.println(treeWeka.getAttributeIndex("chlorides"));
		
		System.out.println("*************************************************\n");
		
		//System.out.println(tree.getEngine().eval("print(train)"));
		//double a = ((DoubleArrayVector) tree.getEngine().eval("accuracy")).asReal();
		//ListVector a =  (ListVector) tree.getEngine().eval("pred");
		
		
		System.out.println("**********************WEKA***************************\n");

		WekaDecisionTree tree_weka = new WekaDecisionTree();
		metier.Data d_weka = new metier.Data();
		d_weka.setPath("src/main/resources/winequality.csv");
		d_weka.setTargetname("quality");
		//d_weka.setHasRowNames("true");
		d_weka.setHasRowNames("false");
		d_weka.setToNominal("true");
		d_weka.readWeka();
		tree_weka.setCompleteData(d_weka);
		//System.out.println(tree_weka.getCompleteDataSet());
		tree_weka.split(0.8);
		tree_weka.fit();
		System.out.println(tree_weka.eval());
		
		
		
		
//		d.setPath("\"src/main/resources/winequality.csv\"");
//		d.setSep(",");
//		d.setHasRowNames("false");
//		d.setTargetname("quality");
//		d.setHeader("true");
//		d.setToNominal("true");
		System.out.println("******************R decision tree*******************************\n");

		RDecisionTree tree_test = new RDecisionTree();
		
		metier.Data d = new metier.Data();
		d.setPath("src/main/resources/winequality.csv");
		d.setSep(",");
	//	d.setHasRowNames("true");
		d.setHasRowNames("false");
		d.setTargetname("quality");
		d.setHeader("true");
		d.readR(tree_test.getEngine());
		tree_test.split(0.8);
		tree_test.fit();
		double acc = tree_test.eval();
		System.out.println(acc);
		
		
		
		
	
	    
	  }

}
