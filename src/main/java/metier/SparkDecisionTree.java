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
	private String impurity = "gini";
	private DecisionTreeModel model;
	private metier.DecisionTree dt;
	private boolean classif;
	private int numClasses;
	
	
	public SparkDecisionTree(Data d, metier.DecisionTree dt, double propTrain) throws Exception {
		this.setCompleteData(d);
		this.split(propTrain); //split initial 
		this.dt = dt;
		if(this.dt.isGini()) {
			this.impurity = "gini";
		}
		else {
			this.impurity = "variance";
		}
	}
	
	public void setCompleteData(Data d) {
		super.setCompleteData(d);
		this.classif = d.isClassif();
		this.numClasses = d.getNumClasses();
	}

	public void fit() {
		if(this.classif) {
			this.model = org.apache.spark.mllib.tree.DecisionTree.trainClassifier(this.train, this.numClasses,
					  this.categoricalFeaturesInfo, this.impurity, this.dt.getMaxDepth(), this.dt.getMaxBins());
		}
		else {
			this.model = org.apache.spark.mllib.tree.DecisionTree.trainRegressor(this.train,
					  this.categoricalFeaturesInfo, this.impurity, this.dt.getMaxDepth(), this.dt.getMaxBins());
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
