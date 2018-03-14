import org.apache.spark.api.java.JavaSparkContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import metier.SparkDecisionTree;
import metier.SparkRandomForest;
import metier.SparkSession;
import metier.Data;
import metier.DecisionTree;
import metier.RandomForest;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.regression.LabeledPoint;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;


public class main {


	public static void main(String[] args) throws Exception {
		Logger.getLogger("org").setLevel(Level.OFF);
		Logger.getLogger("akka").setLevel(Level.OFF);

		Data d = new Data("data/iris.csv", "true", "Species", "true", ",", ".", true, new ArrayList<String>());
		d.setNumClasses(3);
		DecisionTree dt = new DecisionTree();
		dt.setGini(true);
		dt.setMaxBins(2);
		dt.setMaxDepth(1);
		
		SparkDecisionTree sdt = new SparkDecisionTree(d, dt, 0.7);
		System.out.println("Classif on iris:");
		System.out.println("Resultats loop Spark DT : \n"+sdt.aggregateEval(5, 0.7));
		System.out.println("*************************************************************");
		
		
		RandomForest rf = new RandomForest();
		rf.setGini(true);
		rf.setNtrees(5);
		rf.setFeatureSubsetStrategy("auto");
		rf.setMaxDepth(1);
		rf.setMaxBins(2);
		rf.setSeed(1234);
		
		SparkRandomForest srf = new SparkRandomForest(d, rf, 0.7);
		System.out.println("Resultats loop Spark RF  : \n"+srf.aggregateEval(5, 0.7));
		System.out.println("*************************************************************");
		
		ArrayList<String> catnames = new ArrayList<String>();
		catnames.add("cat");
		catnames.add("cat2");
		Data d1 = new Data("data/testreg2.csv", "true", "y", "false", ",", ".",false, catnames);
		DecisionTree dt1 = new DecisionTree();
		dt1.setGini(false);
		dt1.setMaxBins(2);
		dt1.setMaxDepth(1);
		SparkDecisionTree sdt1 = new SparkDecisionTree(d1, dt1, 0.7);
		System.out.println("Regression on generated data with categorical features:");
		System.out.println("Resultats loop Spark DT : \n"+sdt1.aggregateEval(5, 0.7));
		System.out.println("*************************************************************");
		
		RandomForest rf1 = new RandomForest();
		rf1.setGini(false);
		rf1.setMaxBins(2);
		rf1.setMaxDepth(1);
		SparkRandomForest srf1 = new SparkRandomForest(d1, rf1, 0.7);
		System.out.println("Resultats loop Spark RF : \n"+srf1.aggregateEval(5, 0.7));
		System.out.println("*************************************************************");
				
	}

}
