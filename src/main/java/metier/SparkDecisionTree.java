package metier;

import java.util.Map;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;

public class SparkDecisionTree {
	private Integer numClasses;
	private Map<Integer, Integer> categoricalFeaturesInfo;
	private String impurity = "gini";
	private Integer maxDepth = 5;
	private Integer maxBins;
	private static JavaSparkContext sc;
	private DecisionTreeModel model;

	
	public SparkDecisionTree(JavaSparkContext sc, Integer numClasses, Map<Integer, Integer> categoricalFeaturesInfo, String impurity,
			Integer maxDepth, Integer maxBins) {
		this.sc = sc;
		this.numClasses = numClasses;
		this.categoricalFeaturesInfo = categoricalFeaturesInfo;
		this.impurity = impurity;
		this.maxDepth = maxDepth;
		this.maxBins = maxBins;
	}
}
