package fr.ensai.renjin_ml;

import org.omg.CORBA.WStringValueHelper;

import akka.japi.Effect;

//import java.util.ArrayList;
//import java.util.List;

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
		
		
		System.out.println("**********************WEKA decsion tree New Implementation***************************\n");
		WekaDecisionTree treeWeka = new WekaDecisionTree();
		Data data= new Data();
		//data.setPath("src/main/resources/winequality.csv");
		data.setPath("src/main/resources/iris.csv");
		//data.setTargetName("quality");
		data.setTargetName("Species");
		//data.setClassif(false);
		treeWeka.setCompleteData(data);
		//System.out.println(treeWeka.getCompleteDataSet());
		DecisionTree dtree = new DecisionTree();
		treeWeka.setDtree(dtree); // Can be ommitted because dtree is create by default in DecisionTree()
		System.out.println(treeWeka.aggregateEval(100, 0.8));
			
		
		System.out.println("**********************WEKA RandomForest***************************\n");

		WekaRandomForest rf_weka = new WekaRandomForest();
		rf_weka.setCompleteData(data);
		System.out.println(rf_weka.aggregateEval(1, 0.8));
//		metier.Data d_weka = new metier.Data();
//		d_weka.setPath("src/main/resources/iris.csv");
//		d_weka.setTargetname("Species");
//		d_weka.setHasRowNames("true");
//		//d_weka.setHasRowNames("false");
//		//d_weka.setToNominal("true");
//		d_weka.setToNominal("false");
//		d_weka.readWeka();
//		rf_weka.setCompleteData(d_weka);
//		//System.out.println(tree_weka.getCompleteDataSet());
////		tree_weka.split(0.8);
////		tree_weka.fit();
////		System.out.println(tree_weka.eval());
//		double acc_wekarf = rf_weka.runLoop(10, 0.8);
//		System.out.println(acc_wekarf);
		
		System.out.println("******************WEKA SVM*******************************\n");
//		WekaSVM w_svm = new WekaSVM();
//		w_svm.setCompleteData(d_weka);
//		//w_svm.split(0.8);
//		//w_svm.fit();
//		System.out.println(w_svm.runLoop(50, 0.8));

		
		
		
//		d.setPath("\"src/main/resources/winequality.csv\"");
//		d.setSep(",");
//		d.setHasRowNames("false");
//		d.setTargetname("quality");
//		d.setHeader("true");
//		d.setToNominal("true");
		System.out.println("******************R decision tree*******************************\n");

//		RDecisionTree tree_test = new RDecisionTree();
//		metier.Data d = new metier.Data();
//		d.setPath("src/main/resources/iris.csv");
//		d.setSep(",");
//		d.setHasRowNames("true");
//		//d.setHasRowNames("false");
//		d.setTargetname("Species");
//		d.setHeader("true");
//		d.readR(tree_test.getEngine());
		//System.out.println(tree_test.getMethod_()==null);
//		System.out.println(tree_test.getEngine().eval("print(table(data$quality))"));
//		tree_test.split(0.8);
//		tree_test.fit();
//		double acc = tree_test.eval();
//		System.out.println(acc);
//		tree_test.setMethod_(method.class_);
//		double accR = tree_test.runLoop(2, 0.8);
//		System.out.println(accR);
		
		RDecisionTree rtree=new RDecisionTree();
		rtree.setCompleteData(data);
		//System.out.println(rtree.getEngine().eval("print(head(data))"));
		System.out.println(rtree.aggregateEval(10, 0.8));
		
		
		
		System.out.println("******************R RandomForest tree*******************************\n");

		RRandomForest rf = new RRandomForest();
		data.setHasRowNames("true");
		rf.setCompleteData(data);
//		d.readR(rf.getEngine());
//		rf.setMtry(0.8);
//		double accrf = rf.runLoop(1, 0.8);
		System.out.println(rf.aggregateEval(10, 0.7));
	
		System.out.println("******************R SVM*******************************\n");
//		RSVM svmR = new RSVM();
//		d.readR(svmR.getEngine());
//		svmR.split(0.8);
//		svmR.fit();
		
//		System.out.println(svmR.runLoop(50, 0.8));
		
		
	
	    
	  }

}
