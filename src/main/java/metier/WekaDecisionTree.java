package metier;

//import weka.classifiers.AbstractClassifier;

//import org.renjin.sexp.DoubleArrayVector;

//import metier.RDecisionTree.split_criterion;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.M5P;

public class WekaDecisionTree  extends WekaModel{

	private DecisionTree dtree=new DecisionTree();  //class containing parameters for model
	//private AbstractClassifier mod= this.isClassif()? new J48(): new M5P(); // When want to use a sigle model object. AbstractClassifier is the super super class for J48 and M5P
	private J48 classifier= new J48();
	private M5P regTree= new M5P();
	private boolean classif; //to speify weither classification or regression
	
	
	
	public WekaDecisionTree() {
		super();
		
	}

	public void setCompleteData(Data d) throws Exception {
		super.setCompleteData(d);
		this.classif = d.isClassif();
	}
	
	//@Override
	public void fit() throws Exception {
		if(this.classif) {
			this.classifier.setMinNumObj(this.dtree.getMinPerLeaf());
			this.classifier.buildClassifier(this.getTrain());
		}
		else {
			this.regTree.setMinNumInstances(this.dtree.getMinPerLeaf());
			this.regTree.buildClassifier(this.getTrain());
		}
		
	}

	//@Override
	public double eval() throws Exception {
		
		Evaluation eval = new Evaluation(this.getTrain());
		if(this.classif) {
		eval.evaluateModel(this.classifier, this.getTest());
		return 1-eval.errorRate();
		}
		else {
			eval.evaluateModel(this.regTree, this.getTest());
			return Math.pow(eval.rootMeanSquaredError(),2);
		}
	}


	public DecisionTree getDtree() {
		return dtree;
	}


	public void setDtree(DecisionTree dtree) {
		this.dtree = dtree;
	}


	public J48 getClassifier() {
		return classifier;
	}


	public void setClassifier(J48 classifier) {
		this.classifier = classifier;
	}


	public M5P getRegTree() {
		return regTree;
	}


	public void setRegTree(M5P regTree) {
		this.regTree = regTree;
	}
	
	
	
//	@Override
//	 public double runLoop(int iterations, double propTrain) throws Exception {
//	 		// TODO Auto-generated method stub
//	 		double sum_eval = 0;
//	 		for(int i=0; i<=iterations; i++) {
//	 			this.split(propTrain);
//	 			//this.model = null;
//	 			this.fit();
//	 			sum_eval += this.eval();
//	 		}
//	 		return sum_eval/iterations;
//	 	}
}
