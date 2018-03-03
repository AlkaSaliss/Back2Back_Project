package metier;

import javax.script.ScriptException;

import org.renjin.script.RenjinScriptEngine;
import org.renjin.script.RenjinScriptEngineFactory;

public abstract class RModel extends Model {

	private RenjinScriptEngine engine;
	
	public RModel() {
		
		RenjinScriptEngineFactory factory = new RenjinScriptEngineFactory();
	    // create a Renjin engine:
	    RenjinScriptEngine engine = factory.getScriptEngine();
		this.engine = engine;
	}
	
	@Override
	public void setCompleteData(Data d) throws Exception {
		d.readR(this.engine);

	}

	public RenjinScriptEngine getEngine() {
		return engine;
	}

	public void setEngine(RenjinScriptEngine engine) {
		this.engine = engine;
	}

	@Override
	public void split(Double propTrain) throws Exception {
		//engine.eval("set.seed(123)");
		this.engine.eval("trainIndex <- sample(1:nrow(data), size=round(nrow(data)*"+propTrain.toString()+"), replace=F)");
		this.engine.eval("train <- data[trainIndex, ]");
		this.engine.eval("test <- data[-trainIndex, ]");
	}

//	@Override
//	public void fit() throws Exception {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public double eval() {
//		// TODO Auto-generated method stub
//		return 0;
//	}

}
