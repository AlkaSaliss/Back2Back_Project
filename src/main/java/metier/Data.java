package metier;

import java.io.File;
import java.io.IOException;

import javax.script.ScriptException;

import org.renjin.script.RenjinScriptEngine;

import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.unsupervised.attribute.Remove;

public class Data {
	String path;
	String header="true";
	String targetname;
	String hasRowNames="false"; //R
	String sep = ","; //R
	String dec = "."; //R
	
	String catTarget = "false"; //weka
	String toNominal = "false"; //
	
	
	
	
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
