package metier;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.mllib.tree.RandomForest;
import org.apache.spark.mllib.tree.model.RandomForestModel;
import metier.Data;
import metier.SparkModel;
import scala.Tuple2;

public class SparkRandomForest  extends SparkModel  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private Integer numClasses = 2;
	//private Map<Integer, Integer> categoricalFeaturesInfo = new HashMap<Integer, Integer>();
	private Integer numTrees = 3; // Use more in practice.
	//private String featureSubsetStrategy = "auto"; // Let the algorithm choose.
	private String impurity = "gini";
	private RandomForestModel model;
	private metier.RandomForest rf;
	private boolean classif;
	private int numClasses;
	
	public SparkRandomForest(Data d, metier.RandomForest rf, double propTrain) throws Exception {
		this.setCompleteData(d);
		this.split(propTrain); //split initial 
		this.rf = rf;
		if(this.rf.isGini()) {
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

//	public void setParameters(Integer numClasses, Map<Integer, Integer> categoricalFeaturesInfo, Integer numTree, String featureSubsetStrategy, 
//			String impurity,
//			Integer maxDepth, Integer maxBins, Integer seed) {
//		this.numClasses = numClasses;
//		this.categoricalFeaturesInfo = categoricalFeaturesInfo;
//		this.numTrees = numTree; // Use more in practice.
//		this.featureSubsetStrategy = featureSubsetStrategy; // Let the algorithm choose.
//		this.impurity = "gini";
//		this.maxDepth = maxDepth;
//		this.maxBins = maxBins;
//		this.seed = seed;
//	}
	
	@Override
	public void fit() {
		if(this.classif) {
			this.model = RandomForest.trainClassifier(this.train, this.numClasses,
					  this.categoricalFeaturesInfo, this.rf.getNtrees(), this.rf.getFeatureSubsetStrategy(), this.impurity, this.rf.getMaxDepth(), this.rf.getMaxBins(),
					  this.rf.getSeed());
		}
		else {
			this.model = RandomForest.trainRegressor(this.train,
					  this.categoricalFeaturesInfo, this.rf.getNtrees(), this.rf.getFeatureSubsetStrategy(), this.impurity, this.rf.getMaxDepth(), this.rf.getMaxBins(),
					  this.rf.getSeed());
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

