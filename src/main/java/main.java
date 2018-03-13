import org.apache.spark.api.java.JavaSparkContext;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import metier.SparkDecisionTree;
import fr.ensai.sparkml.SparkSession;
import metier.Data;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.regression.LabeledPoint;

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
		// TODO Auto-generated method stub
		SparkSession session = SparkSession.getInstance();
		JavaSparkContext sc = session.getSc();
		
		Data d = new Data("data/iris.csv", "Species", "true", "true");
//		JavaRDD<LabeledPoint> dataSpark = d.readSpark(sc);
//		System.out.println(dataSpark.take(1));
		
		Map<Integer, Integer> categoricalFeaturesInfo = new HashMap<Integer, Integer>();
		SparkDecisionTree tree = new SparkDecisionTree(sc, d, 0.7);
		tree.setParameters(3, categoricalFeaturesInfo, "gini", 1, 2);
		System.out.println(tree.runLoop(5, 0.7));

	}
>>>>>>> branch 'master' of https://github.com/AlkaSaliss/Back2Back_Project.git
*/
}
