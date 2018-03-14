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

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.regression.LabeledPoint;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;


public class main {


	public static void main(String[] args) throws Exception {
		Logger.getLogger("org").setLevel(Level.OFF);
		Logger.getLogger("akka").setLevel(Level.OFF);
		
		//SparkSession session = SparkSession.getInstance();
		//JavaSparkContext sc = session.getSc();
		
		
		Data d = new Data("data/iris.csv", "true", "Species", "true", ",", ".", true, new ArrayList<String>());
		d.setNumClasses(3);
		DecisionTree dt = new DecisionTree();
		dt.setGini(true);
		dt.setMaxBins(2);
		dt.setMaxDepth(1);
		SparkDecisionTree sdt = new SparkDecisionTree(d, dt, 0.7);
		System.out.println("Resultats loop Spark DT : \n"+sdt.aggregateEval(5, 0.7));
		System.out.println("*************************************************************");
		
//		SparkRandomForest rf = new SparkRandomForest(d, 0.7);
//		rf.setParameters(3, categoricalFeaturesInfo, 5, "auto", "gini", 1, 2, 123);
//		System.out.println("Resultats loop Spark RF : \n"+rf.aggregateEval(5, 0.7));
//		
//		System.out.println("---- REGRESSION ---- \n");
//		
//		ArrayList<String> catnames = new ArrayList<String>();
//		catnames.add("cat");
//		catnames.add("cat2");
//		Data d1 = new Data("data/testreg2.csv", "true", "y", "false", ",", ".",false, catnames);
//		JavaRDD<LabeledPoint> dataSpark = d1.readSpark();
//		
//		SparkDecisionTree tree1 = new SparkDecisionTree(d1, 0.7);
//		tree1.setParameters(0, "variance", 1, 2);
//		System.out.println("Resultats loop Spark DT  regression: \n"+tree1.aggregateEval(5, 0.7));
		
	}

}
