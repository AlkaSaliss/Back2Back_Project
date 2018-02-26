package metier;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

public class Data  implements Serializable {
	String path;
	String header="true"; //true or false;
	String targetname;
	String hasRowNames="false";
	
	public Data(String path, String targetname, String header) {
		this.path = path;
		this.targetname = targetname;
		this.header = header;
	}
	
	/**
     * Function that read a csv file and return a JavaRDD LabeledPoint (first column being the target variable)
     * 
     */
	
	public JavaRDD<LabeledPoint> readSpark(JavaSparkContext sc){
		SQLContext sqlcontext = new SQLContext(sc);
		
		JavaRDD<Row> test = sqlcontext.read()
				.format("com.databricks.spark.csv")
				.option("inferSchema", "true")
				.option("header", this.header)
				.load(this.path).javaRDD();

		
		JavaRDD<LabeledPoint> labeldata = test
			    .map((Row line) -> {
			    	int rowsize =  line.length();
			    	String target = line.getAs(this.targetname);
			    	int indextarget = line.fieldIndex(this.targetname);
			    	
			    	Double Catlabel;
			    	Map<String, Double> catmap = new HashMap<>();
			    	if(catmap.containsKey(target)) {
			    		Catlabel = catmap.get(target);
			    	}
			    	else {
			    		catmap.put(target, (double)catmap.size());
			    		Catlabel = catmap.get(target);
			    	}
			    	
			     
			    	double[] col = new double[rowsize-1];
			    	int j = 0;
			    	
			    	if(this.hasRowNames.equals("true")) {
			    		int i=1;
			    	}
			    	else {
			    		int i = 0;
			    	}
			    	for(int i=1; i<rowsize; i++) {
			    		if(i != indextarget) {
			    			col[j] = (double)line.getDouble(i);
			    			j++;
			    		}
			    	}

			    	LabeledPoint labelpt = new LabeledPoint(Catlabel, Vectors.dense(col));
			    	return labelpt;
			    });
		return labeldata;
	}
	
}
