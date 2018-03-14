package metier;

import weka.core.Instances;

public abstract class WekaModel implements Model {

	/*
	 * Attributes containning the initial dataset, the train and test sets if splitting is performed 
	 * */
	private Instances completeDataSet;
	private Instances train;
	private Instances test;
	



	public WekaModel() {
		
	}

	

	/*
	 * Method that takes Data object and set it as a weka instance object in completeDataSet attribute
	 * */
	@Override
	public void setCompleteData(Data d) throws Exception {
			this.completeDataSet = d.readWeka();
	}

	/*
	 * Method that split the complet dataset into train and test, given the train set proportion (between 0 and 1)
	 * */
	@Override
	public void split(double propTrain) {
		this.completeDataSet.randomize(new java.util.Random(0));
		
		int trainSize = (int) Math.round(this.completeDataSet.numInstances() * propTrain);
		int testSize = this.completeDataSet.numInstances() - trainSize;
		this.train = new Instances(this.completeDataSet, 0, trainSize);
		this. test = new Instances(this.completeDataSet, trainSize, testSize);

	}



	public Instances getCompleteDataSet() {
		return completeDataSet;
	}



	public void setCompleteDataSet(Instances completeDataSet) {
		this.completeDataSet = completeDataSet;
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

	
//	public boolean isClassif() {
//		return classif;
//	}
//
//
//
//	public void setClassif(boolean classif) {
//		this.classif = classif;
//	}
	
//	@Override
//	public void fit() throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public double eval() throws Exception {
//		// TODO Auto-generated method stub
//		return 0;
//	}


	

}
