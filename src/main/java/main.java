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
/*
<<<<<<< HEAD
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		SparkSession session = SparkSession.getInstance();
//		JavaSparkContext sc = session.getSc();
//		
//		Data d = new Data("data/iris.csv", "Species", "true");
//		JavaRDD<LabeledPoint> dataSpark = d.readSpark(sc);
//		System.out.println(dataSpark.take(1));
//	}
=======
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
		
		

	}
>>>>>>> branch 'master' of https://github.com/AlkaSaliss/Back2Back_Project.git
*/
}
