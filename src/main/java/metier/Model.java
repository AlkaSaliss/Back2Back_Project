package metier;

public abstract class Model {
	public abstract void setCompleteData(Data d);
	public abstract void split(Double propTrain);
	public abstract void fit();
	public abstract double eval();
}
