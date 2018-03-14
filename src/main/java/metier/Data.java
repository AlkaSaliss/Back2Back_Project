package metier;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import java.io.File;
import javax.script.ScriptException;
import org.renjin.script.RenjinScriptEngine;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.unsupervised.attribute.Remove;

public class Data implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String path;
	String header="true"; //true or false;
	String targetName;
	String hasRowNames="false";
	String sep = ","; //R
	String dec = "."; //R
//	String catTarget = "false"; //weka
	String toNominal = "false"; //
//	private Map<Integer, Integer> categoricalFeaturesInfo;
	
	boolean classif=true;
	ArrayList<String> catFeaturesNames = new ArrayList<String>();
	
	
	
	public Data(String path, String header, String targetName, String hasRowNames, String sep, String dec, boolean classif, ArrayList<String> catFeaturesNames) {

		this.path = path;
		this.targetName = targetName;
		this.header = header;
		this.hasRowNames = hasRowNames;
		this.classif = classif;
		this.sep = sep;
		this.dec = dec;
		this.classif = classif;
		this.catFeaturesNames = catFeaturesNames;
		
	}
	
	public Data() {
		
	}

	
	/**
     * Function that read a csv file and return a JavaRDD LabeledPoint (first column being the target variable)
     * 
     */

	public JavaRDD<LabeledPoint> readSpark_old(){
		SQLContext sqlcontext = new SQLContext(SparkSession.getInstance().getContext());
		JavaRDD<Row> test = sqlcontext.read()
				.format("com.databricks.spark.csv")
				.option("inferSchema", "true")
				.option("header", this.header)
				.load(this.path).javaRDD();
		Map<String, Double> catmap = new HashMap<>();
		JavaRDD<LabeledPoint> labeldata = test
			    .map((Row line) -> {
			    	int rowsize =  line.length();
			    	String target = line.getAs(this.targetName);
			    	int indextarget = line.fieldIndex(this.targetName);
			    	
			    	Double Catlabel;
			    	if(catmap.containsKey(target)) {
			    		Catlabel = catmap.get(target);
			    	}
			    	else {
			    		catmap.put(target, (double)catmap.size());
			    		Catlabel = catmap.get(target);
			    	}
			    	
			    	
			    	int n = rowsize-1;
			    	
			    	int j = 0;
			    	
			    	int start=0;
			    	if(this.hasRowNames.equals("true")) {
			    		start=1;
			    		n--;
			    	}
			    	double[] col = new double[n];
			    	for(int i = start; i<rowsize; i++) {
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
	
	public JavaRDD<LabeledPoint> readSpark(){
		SQLContext sqlcontext = new SQLContext(SparkSession.getInstance().getContext());
		JavaRDD<Row> test = sqlcontext.read()
				.format("com.databricks.spark.csv")
				.option("inferSchema", "true")
				.option("header", this.header)
				.load(this.path).javaRDD();

		Map<String, Double> catmap = new HashMap<>();
		JavaRDD<LabeledPoint> labeldata = test
			    .map((Row line) -> {
			    	int rowsize =  line.length();
			    	int indextarget = line.fieldIndex(this.targetName);
			    	//String target = line.getAs(this.targetName);

			    	Double finalLabel;
			    	
			    	if(this.classif) {
			    		String target = line.getAs(this.targetName);
			    		Double Catlabel;
				    	if(catmap.containsKey(target)) {
				    		Catlabel = catmap.get(target);
				    	}
				    	else {
				    		catmap.put(target, (double)catmap.size());
				    		Catlabel = catmap.get(target);
				    	}
				    	finalLabel = Catlabel;
			    	}
			    	else {
			    		finalLabel = line.getDouble(indextarget);
			    	}
			    	
			    	
			    	int n = rowsize-1;
			    	
			    	int j = 0;
			    	
			    	int start=0;
			    	if(this.hasRowNames.equals("true")) {
			    		start=1;
			    		n--;
			    	}
			    	double[] col = new double[n];
			    	for(int i = start; i<rowsize; i++) {
			    		if(i != indextarget) {
			    			col[j] = (double)line.getDouble(i);
			    			j++;
			    		}
			    	}

			    	LabeledPoint labelpt = new LabeledPoint(finalLabel, Vectors.dense(col));
			    	return labelpt;
			    });
		return labeldata;
	}

	
	public void readR(RenjinScriptEngine engine) throws ScriptException {
		
		/*
		 * if the input file has rownames,(we suppose it is yhe 1st column),
		 * then we pass 1 as row.names param in r read.csv function
		 * else : we pas NULL */
		String rownames = this.hasRowNames.equalsIgnoreCase("true") ? "1" : "NULL";
		
		/*
		 * Reading the data with r*/
		engine.eval("data <- read.csv(\""+this.path+"\", header="+this.header.toUpperCase() +", sep=" + this.sep + ", row.names="+rownames+")");
		/*
		 * Storing the target variable in r
		 * */
		engine.eval("targetName <- \"" + this.targetName + "\"");
		
		/*
		 * if we are doing classification, we convert (coerce) the target variable to a factor one
		 * */
		if (this.classif) {
			engine.eval("data[, targetName] <- as.factor(data[, targetName])");
		}
		
		/*
		 * Creating the formula needed for most r models
		 * */
		engine.eval("formula <- as.formula(paste(targetName, \"~ .\"))");
		
		/*
		 * Convert/coerce all categorical variables as factors in R
		 * */
		
		if (this.catFeaturesNames.size()>0){
			/*
			 * Add quotes for each categorical variable name in the catFeaturesNames
			 * */
			ArrayList<String> tmp = new ArrayList<String>();
			for (String s:this.catFeaturesNames) {
				tmp.add("\""  + s+ "\"");
			}
			String factors = String.join(",", tmp);
			
			/*
			 * Converting list of categorical features as R vector
			 * */
			factors = "c(" + factors + ")";
			engine.eval("cat_vars <- " + factors);
			engine.eval("for (v in cat_vars) {data[, v] = as.factor(data[, v])}");
		}
		

		
	}
	
	public Instances readWeka() throws Exception {
		
		File f = new File(this.path);
		CSVLoader cnv = new CSVLoader();
		cnv.setSource(f);
		Instances data = cnv.getDataSet();
		
		
		/*
		 * Retrieve the target column index and set this column as the model dependant variable
		 * */
		int targetIndex = data.attribute(this.targetName).index(); // target variable index
		data.setClassIndex(targetIndex);
		
		/*
		 * If dataset has rownames column (we assume it is the 1st column)
		 * we remove it
		 * */
		if (this.hasRowNames.equalsIgnoreCase("true")) {
		
			String[] options = new String[2];
			 options[0] = "-R";                                    // "range"
			 options[1] = "1";                                     // first attribute
			 Remove remove = new Remove();                         // new instance of filter
			 remove.setOptions(options);                           // set options
			 remove.setInputFormat(data);                          // inform filter about dataset **AFTER** setting options
			 data = Filter.useFilter(data, remove);   // apply filter
		}
		
		
		
		
		/*
		 * Convert target variable to weka nominal one if we are performing classification
		 * */
		if (this.classif) {
		
			NumericToNominal convert= new NumericToNominal();
	        String[] options= new String[2];
	        options[0]="-R";
	       // options[1]= String.valueOf(data.numAttributes()) ;
	        options[1]= String.valueOf(targetIndex+1) ;
	        
	        convert.setOptions(options);
	        convert.setInputFormat(data);
	        data = Filter.useFilter(data, convert);
	   
		}
		
		/*
		 * Convert/coerce categorical variables to weka Nominal if necessary
		 * */
		if (this.catFeaturesNames.size()>0){
			
			for (String s:this.catFeaturesNames) {
				
				int var_index = data.attribute(s).index(); // retrieve the categorical variable index
			
				NumericToNominal convert= new NumericToNominal();
		        String[] options= new String[2];
		        options[0]="-R";
		        options[1]= String.valueOf(var_index+1) ;
		        convert.setOptions(options);
		        convert.setInputFormat(data);
		        data = Filter.useFilter(data, convert);
			
			}
		}
		
		return data;
		
	}

/*
 * Getters and setters
 * */
	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public String getHeader() {
		return header;
	}


	public void setHeader(String header) {
		this.header = header;
	}


	public String getTargetName() {
		return targetName;
	}


	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}


	public String getHasRowNames() {
		return hasRowNames;
	}


	public void setHasRowNames(String hasRowNames) {
		this.hasRowNames = hasRowNames;
	}


	public String getSep() {
		return sep;
	}


	public void setSep(String sep) {
		this.sep = sep;
	}


	public String getDec() {
		return dec;
	}


	public void setDec(String dec) {
		this.dec = dec;
	}


	public boolean isClassif() {
		return classif;
	}


	public void setClassif(boolean classif) {
		this.classif = classif;
	}


	public ArrayList<String> getCatFeaturesNames() {
		return catFeaturesNames;
	}


	public void setCatFeaturesNames(ArrayList<String> catFeaturesNames) {
		this.catFeaturesNames = catFeaturesNames;
	}

	
	
}
