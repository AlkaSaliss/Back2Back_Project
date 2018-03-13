package metier;

import org.renjin.sexp.DoubleArrayVector;

public class RSVM extends RModel {
	
	private double gamma;  
	private double cost;
	private kernel kernel_;
	private int degree ;
	private type type_ = type.C_classification;

	
	public RSVM() {
		super();
		this.gamma = 0;
		this.cost = 1;
		this.kernel_ = kernel.radial;
		this.degree = 3;
	}

	@Override
	public void fit() throws Exception {
		
		if (this.gamma == 0) {this.getEngine().eval("gamma = 1/(ncol(data)-1)"); }
		else { this.getEngine().eval("gamma = "+ this.gamma); }
		
		String options  = String.join(",", "kernel='"+this.kernel_.toString() + "'", "gamma = gamma" , "cost="+this.cost, "type= '" + this.type_.toString() + "'" );
		
		this.getEngine().eval("library(e1071)");
		this.getEngine().eval("svm_ <- svm(formula, data=train," + options+ ")");

	}

	@Override
	public double eval() throws Exception {
		super.getEngine().eval("pred <- predict(svm_, test, type=\"class\" )");
		super.getEngine().eval("confMat <- table(test[,targetName], pred)");
		super.getEngine().eval("accuracy <- sum(diag(confMat))/sum(confMat)");
		return  ((DoubleArrayVector) super.getEngine().eval("accuracy")).asReal();
		
	}

	@Override
	 public double runLoop(int iterations, double propTrain) throws Exception {
	 		double sum_eval = 0;
	 		for(int i=0; i<iterations; i++) {
	 			this.split(propTrain);
	 			this.fit();
	 			sum_eval += this.eval();
	 			
	 		}
	 		return sum_eval/(iterations);
	 	}
	
	enum kernel {
		radial, polynomial, linear ,sigmoid
	}
	enum type {
		C_classification ("C-classification"),
		nu_classification ("nu-classification") ,
		one_classification ("one-classification"),
		eps_regression ("eps-regression"),
		nu_regression ("nu-regression");
		
		/*
		 * get the svm method name as required by R (with "-"), as java doesnt accept variable names with "-"*/
		private String method;
		type(String method) { this.method = method;}
		
		public String toString() {
			return this.method;
		}
	}
	
	public double getGamma() {
		return gamma;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public kernel getKernel_() {
		return kernel_;
	}

	public void setKernel_(kernel kernel_) {
		this.kernel_ = kernel_;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}


}
