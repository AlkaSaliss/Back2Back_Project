package fr.ensai.renjin_ml;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;

import weka.core.Instances;
import weka.classifiers.trees.J48;
import weka.classifiers.Evaluation;

import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.unsupervised.attribute.Remove;




public class WekaDecisionTreeBis {

	
	private String datapath; //file containing data
	private Instances data;
	private Instances train, test;
	private int kfold;
	
	
	
	public WekaDecisionTreeBis(String path, boolean hasRowName, boolean toNumeric) throws Exception {
		this.datapath = path;
		
		//BufferedReader reader = new BufferedReader(new FileReader(path));
		
		File f = new File(path);
		CSVLoader cnv = new CSVLoader();
		cnv.setSource(f);
		this.data = cnv.getDataSet();
		
		
		//this.data = new Instances(reader);
		this.data.setClassIndex(this.data.numAttributes() - 1);
		//reader.close();
		
		if (hasRowName){
			String[] options = new String[2];
			 options[0] = "-R";                                    // "range"
			 options[1] = "1";                                     // first attribute
			 Remove remove = new Remove();                         // new instance of filter
			 remove.setOptions(options);                           // set options
			 remove.setInputFormat(this.data);                          // inform filter about dataset **AFTER** setting options
			 this.data = Filter.useFilter(this.data, remove);   // apply filter
		}
		
		
		
		
		/*
		 * Convert numeric target variable to categories
		 * */
		if (toNumeric) {
		
		NumericToNominal convert= new NumericToNominal();
        String[] options= new String[2];
        options[0]="-R"; //remove option 
        options[1]= String.valueOf(this.data.numAttributes() ) ;
        
        convert.setOptions(options);
        convert.setInputFormat(this.data);
        this.data =Filter.useFilter(this.data, convert);
        
		}
		
		
	}
	
	public int getAttributeIndex(String label) {
		return this.data.attribute(label).index();
	}
	
	public String getDatapath() {
		return datapath;
	}

	public void setDatapath(String datapath) {
		this.datapath = datapath;
	}

	public Instances getData() {
		return data;
	}

	public void setData(Instances data) {
		this.data = data;
	}

	public Instances getTrain() {
		return train;
	}

	public void setTrain(Instances train) {
		this.train = train;
	}

	public Instances getTest() {
		return test;
	}

	public void setTest(Instances test) {
		this.test = test;
	}

	public int getKfold() {
		return kfold;
	}

	public void setKfold(int kfold) {
		this.kfold = kfold;
	}

	public J48 fit(float trainPercent) throws Exception {
		
		J48 model = new J48();
		
		trainTestSplit(trainPercent);
		
		model.buildClassifier(this.train);
		
		
		return model;
	}
	
	
	public void predict (J48 model) throws Exception {
		
		Evaluation eval = new Evaluation(this.train);
		
		eval.evaluateModel(model, this.test);
		System.out.println(model);
		
		System.out.println(eval.toSummaryString("\nResults\n======\n", false));
		
	}
	
	public void trainTestSplit(float percent) {
		
		this.data.randomize(new java.util.Random(0));
		
		int trainSize = (int) Math.round(this.data.numInstances() * percent);
		int testSize = this.data.numInstances() - trainSize;
		this.train = new Instances(this.data, 0, trainSize);
		this. test = new Instances(this.data, trainSize, testSize);
	}
	
	

}
