package metier;
import java.util.HashMap;
import java.util.Map;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.regression.LabeledPoint;


public abstract class SparkModel implements Model{
	JavaRDD<LabeledPoint> completeDataSet;
	JavaRDD<LabeledPoint> train; 
	JavaRDD<LabeledPoint> test;
//	Boolean classif;
	Map<Integer, Integer> categoricalFeaturesInfo;
//	int numClasses;
	
	//@Override
	public void setCompleteData(Data d) {
		this.completeDataSet = d.readSpark();
//		this.classif = d.isClassif();
		this.categoricalFeaturesInfo = d.getCategoricalFeaturesInfo();
//		this.numClasses = d.getNumClasses();
	}
	
	@Override
	public void split(double propTrain) throws Exception{
		JavaRDD<LabeledPoint>[] splits = this.completeDataSet.randomSplit(new double[]{propTrain, 1-propTrain});
		this.train = splits[0];
		this.test = splits[1];
	}

	public JavaRDD<LabeledPoint> getCompleteDataSet() {
		return completeDataSet;
	}

	public void setCompleteDataSet(JavaRDD<LabeledPoint> completeDataSet) {
		this.completeDataSet = completeDataSet;
	}

	public JavaRDD<LabeledPoint> getTrain() {
		return train;
	}

	public void setTrain(JavaRDD<LabeledPoint> train) {
		this.train = train;
	}

	public JavaRDD<LabeledPoint> getTest() {
		return test;
	}

	public void setTest(JavaRDD<LabeledPoint> test) {
		this.test = test;
	}
	
	
}
