package metier;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.core.SelectedTag;

public class WekaSVM extends WekaModel {

	private LibSVM model = new LibSVM();
	private double gamma;
	private double cost;
	private kernel kernel_;
	private int degree ;
	private type type_ = type.C_classification;
	
	
	public WekaSVM() {
		
		this.gamma = 0;
		this.model.setGamma(this.gamma);
		this.cost = 1;
		this.model.setCost(this.cost);
		this.degree = 3;
		this.model.setDegree(this.degree);
		this.kernel_ = kernel.radial;
		this.model.setKernelType(new SelectedTag(this.kernel_.k, LibSVM.TAGS_KERNELTYPE) );
		this.type_ = type.C_classification;
		this.model.setSVMType(new SelectedTag(this.type_.t_, LibSVM.TAGS_SVMTYPE));
	}

	@Override
	public void fit() throws Exception {
		
		if (this.gamma==0) { this.gamma = 1/(this.getCompleteDataSet().numAttributes()-1); 
		this.model.setGamma(this.gamma);}
		
		this.model.buildClassifier(this.getTrain());

	}

	@Override
	public double eval() throws Exception {
		
		Evaluation eval = new Evaluation(this.getTrain());
		eval.evaluateModel(this.model, this.getTest());
		return 1-eval.errorRate();
	}
	
	@Override
	 public double runLoop(int iterations, double propTrain) throws Exception {
	 		double sum_eval = 0;
	 		for(int i=0; i<=iterations; i++) {
	 			this.split(propTrain);
	 			this.fit();
	 			sum_eval += this.eval();
	 		}
	 		return sum_eval/iterations;
	 	}
	
	enum kernel {
		
		radial(2), polynomial(1), linear(0) ,sigmoid(3);
		private int k;
		
		kernel(int k) {this.k = k;}
		
	}
	enum type {
		C_classification (0),
		nu_classification (1) ,
		one_classification (2),
		eps_regression (3),
		nu_regression (4);
		
		private int t_;
		type(int t) { this.t_ = t;}
		
	}
	public LibSVM getModel() {
		return model;
	}

	public void setModel(LibSVM model) {
		this.model = model;
	}

	public double getGamma() {
		return gamma;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
		this.model.setGamma(gamma);
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
		this.model.setCost(cost);
	}

	public kernel getKernel_() {
		return kernel_;
	}

	public void setKernel_(kernel kernel_) {
		this.kernel_ = kernel_;
		this.model.setKernelType(new SelectedTag(kernel_.k, LibSVM.TAGS_KERNELTYPE) );
		
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
		this.model.setDegree(degree);
	}

	public type getType_() {
		return type_;
	}

	public void setType_(type type_) {
		this.type_ = type_;
		this.model.setSVMType(new SelectedTag(type_.t_, LibSVM.TAGS_SVMTYPE));
	}
	
	

}
