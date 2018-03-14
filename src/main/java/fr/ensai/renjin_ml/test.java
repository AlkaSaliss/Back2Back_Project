
package fr.ensai.renjin_ml;


import java.util.ArrayList;
import java.util.Arrays;

//import java.util.ArrayList;
//import java.util.List;

import metier.*;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

public class test {

	public test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		Logger.getLogger("org").setLevel(Level.OFF);
		Logger.getLogger("akka").setLevel(Level.OFF);
		   
		
		
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
		

		
		
		System.out.println("******************R decision tree*******************************\n");
		
		RDecisionTree rtree=new RDecisionTree();
		rtree.setCompleteData(data);
		//System.out.println(rtree.getEngine().eval("print(head(data))"));
		System.out.println(rtree.aggregateEval(10, 0.8));
		
		
		
		System.out.println("******************R RandomForest tree*******************************\n");

		RRandomForest rf = new RRandomForest();
		data.setHasRowNames("true");
		rf.setCompleteData(data);
		
		
		
		System.out.println("--------------------------Testting regression-----------------------------\n");
		
		System.out.println("**********************WEKA Regression Tree***************************\n");
		WekaDecisionTree regTreeWeka = new WekaDecisionTree();
		Data regdata= new Data();
		//data.setPath("src/main/resources/winequality.csv");
		regdata.setPath("data/testreg2.csv");
		regdata.setTargetName("y");
		regdata.setClassif(false);
		ArrayList<String> catNames = new ArrayList<>(Arrays.asList("cat","cat2"));
		regdata.setCatFeaturesNames(catNames);

		regTreeWeka.setCompleteData(regdata);
		//System.out.println(treeWeka.getCompleteDataSet());
		//DecisionTree tree = new DecisionTree();
		regTreeWeka.setDtree(dtree); // Can be ommitted because dtree is create by default in DecisionTree()
		System.out.println(regTreeWeka.aggregateEval(100, 0.8));
		
		System.out.println("**********************WEKA  RF with regression***************************\n");
		WekaRandomForest rfweka = new WekaRandomForest();
		rfweka.setCompleteData(regdata);
		System.out.println(rfweka.aggregateEval(100, 0.8));
		
		
		
		
		System.out.println("**********************R Regression Tree***************************\n");
		
		
		RDecisionTree rTreeReg=new RDecisionTree();
		rTreeReg.setCompleteData(regdata);
		System.out.println(rTreeReg.aggregateEval(10, 0.8));
		
		
		
		System.out.println("**********************R  RF with regression***************************\n");

		RRandomForest rfReg = new RRandomForest();
		rfReg.setCompleteData(regdata);
		System.out.println(rfReg.aggregateEval(10, 0.7));

		
	    
	  }
	
	
	

}
