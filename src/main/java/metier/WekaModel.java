package metier;

import weka.core.Instances;

public class WekaModel extends Model {

	private String datapath; //file containing data
	private Instances completeDataSet, train, test;
	
	public WekaModel() {
		// TODO Auto-generated constructor stub
	}

	public String getDatapath() {
		return datapath;
	}

	public void setDatapath(String datapath) {
		this.datapath = datapath;
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

	@Override
	public void setCompleteData(Data d) throws Exception {
			this.completeDataSet = d.readWeka();

	}

	@Override
	public void split(Double propTrain) {
		this.completeDataSet.randomize(new java.util.Random(0));
		
		int trainSize = (int) Math.round(this.completeDataSet.numInstances() * propTrain);
		int testSize = this.completeDataSet.numInstances() - trainSize;
		this.train = new Instances(this.completeDataSet, 0, trainSize);
		this. test = new Instances(this.completeDataSet, trainSize, testSize);

	}

	@Override
	public void fit() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public double eval() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
