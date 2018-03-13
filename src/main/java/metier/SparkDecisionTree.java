package metier;

import java.io.Serializable;
import java.util.Map;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.DecisionTree;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;
import org.apache.spark.rdd.RDD;
import org.apache.spark.mllib.tree.configuration.Algo;
import org.apache.spark.mllib.tree.configuration.Strategy;
import org.apache.spark.mllib.tree.impurity.Entropy;
import org.apache.spark.mllib.tree.impurity.Gini;
import org.apache.spark.mllib.tree.impurity.Impurity;

import scala.Tuple2;

public class SparkDecisionTree extends SparkModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer numClasses; // Needed for spark classif (0 for regression ?)
	private Map<Integer, Integer> categoricalFeaturesInfo;
	private String impurity = "gini";
	private Integer maxDepth = 5;
	private Integer maxBins;
	//private static JavaSparkContext sc;
	private DecisionTreeModel model;
	
	public SparkDecisionTree(Data d, double propTrain) {
		this.setCompleteData(d);
		this.split(propTrain); //split initial 
	}
	
	public void setParameters(Integer numClasses, Map<Integer, Integer> categoricalFeaturesInfo, String impurity,
			Integer maxDepth, Integer maxBins) {
		this.numClasses = numClasses;
		this.categoricalFeaturesInfo = categoricalFeaturesInfo;
		this.impurity = impurity;
		this.maxDepth = maxDepth;
		this.maxBins = maxBins;
	}
	
//	@Override
//	public void fit() {
//		if(this.classif) {
//			this.model = DecisionTree.trainClassifier(this.train, this.numClasses,
//					  this.categoricalFeaturesInfo, this.impurity, this.maxDepth, this.maxBins);
//		}
//		else {
//			this.model = DecisionTree.trainRegressor(this.train,
//					  this.categoricalFeaturesInfo, this.impurity, this.maxDepth, this.maxBins);
//		}
//	}
	
	public void fit() {
		if(this.classif) {
			//Strategy(scala.Enumeration.Value algo, Impurity impurity, int maxDepth, int numClasses, int maxBins, java.util.Map<java.lang.Integer,java.lang.Integer> categoricalFeaturesInfo)
//
//			Strategy stratclassif = new Strategy(Algo.Classification(), Gini.instance(), this.maxDepth, this.numClasses, this.maxBins, this.categoricalFeaturesInfo);
//			this.model = DecisionTree.train(this.train.rdd(), stratclassif);
			this.model = DecisionTree.trainClassifier(this.train, this.numClasses,
					  this.categoricalFeaturesInfo, this.impurity, this.maxDepth, this.maxBins);
		}
		else {
//			Strategy stratreg = new Strategy(Algo.Regression(), Entropy.instance(), this.maxDepth, this.numClasses, this.maxBins, this.categoricalFeaturesInfo);
//			this.model = DecisionTree.train(this.train.rdd(), stratreg);
			this.model = DecisionTree.trainRegressor(this.train,
					  this.categoricalFeaturesInfo, this.impurity, this.maxDepth, this.maxBins);
		}
	}

	@Override
	public double eval() {
		// TODO Auto-generated method stub
		if(this.classif) {
			JavaPairRDD<Double, Double> predictionAndLabel = this.test.mapToPair(p -> new Tuple2<>(this.model.predict(p.features()), p.label()));
			Double testErr = predictionAndLabel.filter(pl -> !pl._1().equals(pl._2())).count() / (double) this.test.count();
			return 1-testErr;
		}
		else {
			JavaPairRDD<Double, Double> predictionAndLabel =  this.test.mapToPair(p -> new Tuple2<>(this.model.predict(p.features()), p.label()));
			double testMSE = predictionAndLabel.mapToDouble(pl -> {double diff = pl._1() - pl._2(); return diff * diff;}).mean();
			return testMSE;
		}
		
	}

}
