import java.io.Serializable;
import java.util.Map;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.DecisionTree;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;
import org.apache.spark.mllib.util.MLUtils;

import scala.Tuple2;

public class SparkDecisionTree implements Serializable{

	private Integer numClasses;
	private Map<Integer, Integer> categoricalFeaturesInfo;
	private String impurity = "gini";
	private Integer maxDepth = 5;
	private Integer maxBins;
	private static JavaSparkContext sc;
	private DecisionTreeModel model;
	private JavaPairRDD<Double, Double> predictions;
	
	public SparkDecisionTree(JavaSparkContext sc, Integer numClasses, Map<Integer, Integer> categoricalFeaturesInfo, String impurity,
			Integer maxDepth, Integer maxBins) {
		this.sc = sc;
		this.numClasses = numClasses;
		this.categoricalFeaturesInfo = categoricalFeaturesInfo;
		this.impurity = impurity;
		this.maxDepth = maxDepth;
		this.maxBins = maxBins;
	}

	public Integer getNumClasses() {
		return numClasses;
	}

	public void setNumClasses(Integer numClasses) {
		this.numClasses = numClasses;
	}

	public Map<Integer, Integer> getCategoricalFeaturesInfo() {
		return categoricalFeaturesInfo;
	}

	public void setCategoricalFeaturesInfo(Map<Integer, Integer> categoricalFeaturesInfo) {
		this.categoricalFeaturesInfo = categoricalFeaturesInfo;
	}

	public String getImpurity() {
		return impurity;
	}

	public void setImpurity(String impurity) {
		this.impurity = impurity;
	}

	public Integer getMaxDepth() {
		return maxDepth;
	}

	public void setMaxDepth(Integer maxDepth) {
		this.maxDepth = maxDepth;
	}

	public Integer getMaxBins() {
		return maxBins;
	}

	public void setMaxBins(Integer maxBins) {
		this.maxBins = maxBins;
	}
	
	static JavaRDD<LabeledPoint> readData(String path) {
		JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(sc.sc(), path).toJavaRDD();
		return data;
	}
	
	public void fit(JavaRDD<LabeledPoint> data) {
		model = DecisionTree.trainClassifier(data, numClasses,
				  categoricalFeaturesInfo, impurity, maxDepth, maxBins);
	}
	
	public DecisionTreeModel getModel() {
		return model;
	}
	
	public void predict(JavaRDD<LabeledPoint> data){
		predictions = 
				data.mapToPair(new PairFunction<LabeledPoint, Double, Double>() {
					public Tuple2<Double, Double> call(LabeledPoint p) {
						return new Tuple2<Double, Double>(model.predict(p.features()), p.label());
					}
				});
	}
	
	public JavaPairRDD<Double, Double> getPredictions(){
		return predictions;
	}
	
//	public BinaryClassificationMetrics metrics() {
//		BinaryClassificationMetrics metrics = new BinaryClassificationMetrics(this.getPredictions());
//		return metrics;
//	}
	

	
	public Double eval(JavaRDD<LabeledPoint> testData) {
		JavaPairRDD<Double, Double> predictionAndLabel =
				testData.mapToPair(new PairFunction<LabeledPoint, Double, Double>() {
					public Tuple2<Double, Double> call(LabeledPoint p) {
						return new Tuple2<Double, Double>(model.predict(p.features()), p.label());
					}
				});
		Double testErr =
				1.0 * predictionAndLabel.filter(new Function<Tuple2<Double, Double>, Boolean>() {
					public Boolean call(Tuple2<Double, Double> pl) {
						return !pl._1().equals(pl._2());
					}
				}).count() / testData.count();
		return testErr;
	}
	
	public Double eval2(JavaRDD<LabeledPoint> testData) {
		JavaPairRDD<Double, Double> predictionAndLabel = testData.mapToPair(p -> new Tuple2<>(model.predict(p.features()), p.label()));
		Double testErr = predictionAndLabel.filter(pl -> !pl._1().equals(pl._2())).count() / (double) testData.count();
		return testErr;
	}

}
