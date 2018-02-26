package metier;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.regression.LabeledPoint;

public abstract class SparkModel extends Model{
	JavaRDD<LabeledPoint> completeDataSet;
	JavaRDD<LabeledPoint> train; 
	JavaRDD<LabeledPoint> test;
	JavaSparkContext sc;
	
	@Override
	public void setCompleteData(Data d) {
		this.completeDataSet = d.readSpark(sc);
	}
	
	@Override
	public void split(Double propTrain) {
		JavaRDD<LabeledPoint>[] splits = this.completeDataSet.randomSplit(new double[]{propTrain, 1-propTrain});
		this.train = splits[0];
		this.test = splits[1];
	}

	
	
}
