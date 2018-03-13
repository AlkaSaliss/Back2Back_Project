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
	private Integer numClasses = 2;
	private Map<Integer, Integer> categoricalFeaturesInfo = new HashMap<Integer, Integer>();
	private Integer numTrees = 3; // Use more in practice.
	private String featureSubsetStrategy = "auto"; // Let the algorithm choose.
	private String impurity = "gini";
	private Integer maxDepth = 5;
	private Integer maxBins = 32;
	private Integer seed = 12345;
	private RandomForestModel model;
	
	public SparkRandomForest(Data d, double propTrain) throws Exception {
		this.setCompleteData(d);
		this.split(propTrain); //split initial 
		
	}

	public void setParameters(Integer numClasses, Map<Integer, Integer> categoricalFeaturesInfo, Integer numTree, String featureSubsetStrategy, 
			String impurity,
			Integer maxDepth, Integer maxBins, Integer seed) {
		this.numClasses = numClasses;
		this.categoricalFeaturesInfo = categoricalFeaturesInfo;
		this.numTrees = numTree; // Use more in practice.
		this.featureSubsetStrategy = featureSubsetStrategy; // Let the algorithm choose.
		this.impurity = "gini";
		this.maxDepth = maxDepth;
		this.maxBins = maxBins;
		this.seed = seed;
	}
	
	@Override
	public void fit() {
		this.model = RandomForest.trainClassifier(this.train, numClasses,
				  categoricalFeaturesInfo, numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins,
				  seed);
		
	}

	@Override
	public double eval() {
		JavaPairRDD<Double, Double> predictionAndLabel = this.test.mapToPair(p -> new Tuple2<>(this.model.predict(p.features()), p.label()));
		Double testErr = predictionAndLabel.filter(pl -> !pl._1().equals(pl._2())).count() / (double) this.test.count();
		return 1-testErr;
	}



//	@Override
//	public double aggregateEval(int iterations, double propTrain) {
//		// TODO Auto-generated method stub
//		double sum_eval = 0;
//		for(int i=0; i<=iterations; i++) {
//			this.split(propTrain);
//			//this.model = null;
//			this.fit();
//			sum_eval += this.eval();
//		}
//		return sum_eval/iterations;
//	}

}

