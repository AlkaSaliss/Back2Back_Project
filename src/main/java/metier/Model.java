package metier;

public abstract class Model {
	public abstract void setCompleteData(Data d);
	public abstract void split(double propTrain);
	public abstract void fit();
	public abstract double eval();
	public abstract double runLoop(int iterations, double propTrain);
}
