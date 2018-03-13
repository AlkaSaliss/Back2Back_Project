import org.apache.spark.api.java.JavaSparkContext;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import metier.SparkDecisionTree;
import metier.SparkRandomForest;
import metier.SparkSession;
import metier.Data;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.regression.LabeledPoint;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;


public class main {

	public static void main(String[] args) {
		Logger.getLogger("org").setLevel(Level.OFF);
		Logger.getLogger("akka").setLevel(Level.OFF);
		
		// TODO Auto-generated method stub
		//SparkSession session = SparkSession.getInstance();
		//JavaSparkContext sc = session.getSc();
		
		Data d = new Data("data/iris.csv", "Species", "true", "true", true);
//		JavaRDD<LabeledPoint> dataSpark = d.readSpark();
//		System.out.println(dataSpark.take(1));
		Map<Integer, Integer> categoricalFeaturesInfo = new HashMap<Integer, Integer>();
		SparkDecisionTree tree = new SparkDecisionTree(d, 0.7);
		tree.setParameters(3, categoricalFeaturesInfo, "gini", 1, 2);
		System.out.println("Resultats loop Spark DT : \n"+tree.aggregateEval(5, 0.7));
		System.out.println("*************************************************************\n******************************");
		
		SparkRandomForest rf = new SparkRandomForest(d, 0.7);
		rf.setParameters(3, categoricalFeaturesInfo, 5, "auto", "gini", 1, 2, 123);
		System.out.println("Resultats loop Spark RF : \n"+rf.aggregateEval(5, 0.7));
		
		System.out.println("---- REGRESSION ---- \n");
		
		Data d1 = new Data("data/testreg.csv", "y", "true", "false", false);
		JavaRDD<LabeledPoint> dataSpark = d1.readSpark();
		System.out.println(dataSpark.take(1));
		
		Map<Integer, Integer> categoricalFeaturesInfo1 = new HashMap<Integer, Integer>();
		SparkDecisionTree tree1 = new SparkDecisionTree(d1, 0.7);
		tree1.setParameters(0, categoricalFeaturesInfo1, "variance", 1, 2);
		System.out.println("Resultats loop Spark DT  regression: \n"+tree1.aggregateEval(5, 0.7));
		
	}

}
