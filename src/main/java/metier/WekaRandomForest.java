package metier;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;

public class WekaRandomForest extends WekaModel {

	private metier.RandomForest rf = new metier.RandomForest();
	private RandomForest model = new RandomForest();
	
	
	public WekaRandomForest() {
//		this.ntree = 500;
//		this.mtry = 0;
//		this.sampsize = 70; //percentage of sample from 0 to 100
	}

	@Override
	public void fit() throws Exception {
		/*
		 * check if the proportion of feature for each iteration is given, 
		 * if not set it to squared root of the number of features in the model
		 * */
		if (this.rf.getMtry()==0) {
			this.rf.setMtry(Math.floor(Math.sqrt(this.getCompleteDataSet().numAttributes())));
			this.model.setNumFeatures((int) this.rf.getMtry());
			}
		else {
			this.model.setNumFeatures((int) Math.floor(this.rf.getMtry()*this.getCompleteDataSet().numAttributes()));
		}
		
		/*
		 * Unlike R, weka random forest take the sample size at every iteration as a percentage (from 0 to 100)
		 * so we need to multiply the parameter sampsize by 100 before passing it to weka model, since it is given as a proportion between 0 and 1
		 * */
		this.model.setBagSizePercent((int)Math.floor(this.rf.getSampsize()*100 ));
		this.model.setNumIterations(this.rf.getNtrees());//we set the number of trees to grow
		this.model.buildClassifier(this.getTrain());
		
	    
	}

	@Override
	public double eval() throws Exception {
		
		Evaluation eval = new Evaluation(this.getTrain());
		eval.evaluateModel(this.model, this.getTest());
		
		/*
		 * the evaluation metric depends on the model type : classif==accuracy and regression==mse
		 * */
		if (this.isClassif()) {
		
		return 1-eval.errorRate();
		}
		else {
			return Math.pow(eval.rootMeanSquaredError(), 2);
			}
		
	}


}
