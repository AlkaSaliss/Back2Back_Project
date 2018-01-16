package fr.ensai.renjin_ml;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import weka.core.Instances;
import weka.classifiers.trees.J48;
import weka.classifiers.Evaluation;




public class WekaDecisionTree {

	
	private String datapath; //file containing data
	private Instances data;
	private Instances train, test;
	private int kfold;
	
	
	
	public WekaDecisionTree(String path) throws Exception {
		this.datapath = path;
		
		BufferedReader reader = new BufferedReader(new FileReader(path));
		this.data = new Instances(reader);
		this.data.setClassIndex(this.data.numAttributes() - 1);
		reader.close();
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
