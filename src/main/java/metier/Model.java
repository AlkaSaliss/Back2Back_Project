package metier;

public abstract class Model {
	public abstract void setCompleteData(Data d) throws Exception;
	public abstract void split(Double propTrain) throws Exception;
	public abstract void fit() throws Exception;
	public abstract double eval() throws Exception;
}
