package metier;

import org.renjin.script.RenjinScriptEngine;
import org.renjin.script.RenjinScriptEngineFactory;

public abstract class RModel implements Model {

	private RenjinScriptEngine engine;
	private boolean classif; //to speify weither classification or regression
	
	public RModel() {
		
		RenjinScriptEngineFactory factory = new RenjinScriptEngineFactory();
	    // create a Renjin engine:
	    RenjinScriptEngine engine = factory.getScriptEngine();
		this.engine = engine;
	}
	
	//@Override because Model classes were implementing 
	public void setCompleteData(Data d) throws Exception {
		d.readR(this.engine);
		this.classif=d.isClassif();

	}
	
	

	//@Override
	public void split(double propTrain) throws Exception {
		//engine.eval("set.seed(123)");
		this.engine.eval("trainIndex <- sample(1:nrow(data), size=round(nrow(data)*"+propTrain+"), replace=F)");
		this.engine.eval("train <- data[trainIndex, ]");
		this.engine.eval("test <- data[-trainIndex, ]");
	}

	public RenjinScriptEngine getEngine() {
		return engine;
	}

	public void setEngine(RenjinScriptEngine engine) {
		this.engine = engine;
	}

	public boolean isClassif() {
		return classif;
	}

	public void setClassif(boolean classif) {
		this.classif = classif;
	}

//	//@Override
//	public void fit() throws Exception {
//		// TODO Auto-generated method stub
//
//	}
//
//	//@Override
//	public double eval() {
//		// TODO Auto-generated method stub
//		return 0;
//	}

	
	

}
