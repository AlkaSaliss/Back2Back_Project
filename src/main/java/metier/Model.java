package metier;

public  interface Model {
	public abstract void setCompleteData(Data d);
	public abstract void split(double propTrain);
	public abstract void fit();
	public abstract double eval();
	
	public default double aggregateEval(int iterations, double propTrain) {
		double sum_eval = 0;
		for(int i=0; i<iterations; i++) {
			this.split(propTrain);
			//this.model = null;
			this.fit();
			sum_eval += this.eval();
		}
		return sum_eval/iterations;
	};
}
