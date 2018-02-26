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

import java.io.File;
import java.io.IOException;

import javax.script.ScriptException;

import org.renjin.script.RenjinScriptEngine;

import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.unsupervised.attribute.Remove;

public class Data  implements Serializable {
	String path;
	String header="true"; //true or false;
	String targetname;
	String hasRowNames="false";
	String sep = ","; //R
	String dec = "."; //R
	
	String catTarget = "false"; //weka
	String toNominal = "false"; //
	
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

	
	public void readR(RenjinScriptEngine engine) throws ScriptException {
		
		String rownames = this.hasRowNames.toUpperCase()=="true" ? "1" : "NULL";
		
		engine.eval("\"data <- read.csv(\"+path+\", header="+this.header.toUpperCase() +"," + sep + ", row.names="+rownames+")\"");
		
	}
	
	public Instances readWeka() throws Exception {
		
		File f = new File(this.path);
		CSVLoader cnv = new CSVLoader();
		cnv.setSource(f);
		Instances data = cnv.getDataSet();
		
		
		/*
		 * Retrieve the target column index and set this column as model dependant variable*/
		int targetIndex = data.attribute(this.targetname).index(); // target variable index
		data.setClassIndex(targetIndex);
		
		if (this.hasRowNames.toUpperCase() == "TRUE") {
			String[] options = new String[2];
			 options[0] = "-R";                                    // "range"
			 options[1] = "1";                                     // first attribute
			 Remove remove = new Remove();                         // new instance of filter
			 remove.setOptions(options);                           // set options
			 remove.setInputFormat(data);                          // inform filter about dataset **AFTER** setting options
			 data = Filter.useFilter(data, remove);   // apply filter
		}
		
		
		
		
		/*
		 * Convert numeric target variable to categories
		 * */
		if (toNominal.toUpperCase()=="TRUE") {
		
			NumericToNominal convert= new NumericToNominal();
	        String[] options= new String[2];
	        options[0]="-R";
	        options[1]= String.valueOf(targetIndex) ;
	        
	        convert.setOptions(options);
	        convert.setInputFormat(data);
	        data =Filter.useFilter(data, convert);
		}
		
		return data;
		
	}

	
	
	
}
