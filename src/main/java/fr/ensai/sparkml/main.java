package fr.ensai.sparkml;
import java.awt.Label;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.mllib.linalg.DenseVector;

public class main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
//		SparkConf sparkConf = new SparkConf().setAppName("SparkDecisionTree").setMaster("local");
//		JavaSparkContext sc = new JavaSparkContext(sparkConf);
	
//		Model data = new Model();
//		data.setRawDatafromCSV("toto.csv");
		
//		compare("RF", data, new );
		
		SparkSession session = SparkSession.getInstance();
		JavaSparkContext sc = session.getSc();
		
		
		
		Map<Integer, Integer> categoricalFeaturesInfo = new HashMap<Integer, Integer>();
		
		SparkDecisionTree tree = new SparkDecisionTree(sc, 3, categoricalFeaturesInfo, "gini", 1, 2);
		
		//JavaRDD<LabeledPoint> data = SparkDecisionTree.readData("data/iris.txt");
		//JavaRDD<LabeledPoint> cRDD = spark.read().textFile("C:/Temp/File0.csv").javaRDD();
		JavaRDD<String> data = sc.textFile("data/iris.txt");
		System.out.println(data.first());
		
		SQLContext a = new SQLContext(sc);
		
		JavaRDD<Row> test = a.read()
				.format("com.databricks.spark.csv")
				.option("inferSchema", "true")
				.option("header", "true")
				.load("data/iris.csv").javaRDD();

//		JavaRDD<LabeledPoint> parseddata = data
//			    .map((String line) -> {
//			        String[] parts = line.split(",");
//			        double[] points = new double[parts.length - 1];
//			        for (int i = 0; i < (parts.length - 1); i++) {
//			            points[i] = Double.valueOf(parts[i]);
//			            System.out.println(points[i]);
//			        }
//			        return new LabeledPoint(Double.valueOf(parts[parts.length - 1]), Vectors.dense(points));
//			    });
		
		System.out.println(test.count());
		System.out.println(test.first());
		
		List<Row> test2 = test.take(2);
		
		JavaRDD<LabeledPoint> labeldata = test
			    .map((Row line) -> {
			    	int rowsize =  line.length();
			    	String label = line.getString(line.length() - 1);
			    	String target = line.getAs("Species");
			    	int indextarget = line.fieldIndex("Species");
			    	System.out.println(target);
			    	Double Catlabel;
			    	if (label.equals("virginica")) {
			    		Catlabel = 2d;
			    	}
			    	else if (label.equals("versicolor")) {
			    		Catlabel = 1d;
			    	}
			    	else {
			    		Catlabel = 0d;
			    	}
			    	
			    	Map<String, Double> catmap = new HashMap<>();
			    	if(catmap.containsKey(target)) {
			    		Catlabel = catmap.get(target);
			    	}
			    	else {
			    		catmap.put(target, (double)catmap.size());
			    	}
			    	
			        //return new LabeledPoint(Catlabel, Vectors.dense(line.getDouble(1), line.getDouble(2), line.getDouble(3), line.getDouble(4)));
			    	double[] col = new double[rowsize-1];
			    	int j = 0;
			    	for(int i=1; i<rowsize; i++) {
			    		if(i != indextarget) {
			    			col[j] = (double)line.getDouble(i);
			    			j++;
			    		}
			    	}
			    	LabeledPoint labelpt = new LabeledPoint(Catlabel, Vectors.dense(col));
			    	return labelpt;
			    });
		
		
		
		JavaRDD<LabeledPoint>[] splits = labeldata.randomSplit(new double[]{0.75, 0.25});
		JavaRDD<LabeledPoint> trainingData = splits[0];
		JavaRDD<LabeledPoint> testData = splits[1];
		
		tree.fit(trainingData);
		System.out.println(tree.getModel());
		
		tree.predict(testData);
		System.out.println(tree.getPredictions().first());
		System.out.println("eval : "+tree.eval2(testData));
		
		
		System.out.println("count" + labeldata.count());
		
//		labeldata.foreach(d -> {
//	        System.out.println("features="+d.features() + " label=" + d.label());
//	    });
		

	}

}
