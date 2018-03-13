package metier;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

//public class SparkSession
//{
//	
//	private SparkConf sparkConf;
//	private JavaSparkContext sc;
//	
//    /** Constructeur privé */
//    private SparkSession(String name)
//    {
//    	sparkConf = new SparkConf().setAppName(name).setMaster("local");
//    	sc = new JavaSparkContext(sparkConf);
//    }
//     
//    /** Instance unique non préinitialisée */
//    private static SparkSession INSTANCE = null;
//     
//    /** Point d'accès pour l'instance unique du singleton */
//    public static SparkSession getInstance()
//    {           
//        if (INSTANCE == null)
//        {   INSTANCE = new SparkSession("SparkSession"); 
//        }
//        return INSTANCE;
//    }
//}


public class SparkSession{

	  //Le singleton

	  private static SparkSession sess = new SparkSession();

	  //Variable d'instance

	  private JavaSparkContext sc;

	   

	  //Constructeur privé

	  private SparkSession(){
		SparkConf sconf = new SparkConf().setAppName("conf").setMaster("local");
	    	sc = new JavaSparkContext(sconf);
	  }

	   

	  //Méthode d'accès au singleton

	  public static SparkSession getInstance(){

	    if(sess == null){
		sess = new SparkSession();
		}      
	    return sess;
	  }

	   

	  //Accesseur 

	  public JavaSparkContext getContext(){

	    return this.sc;

	  }

	}
/*
public class SparkSession
{       
	
	private JavaSparkContext sc;
	
    private SparkSession(String name)
    {
    	SparkConf sparkConf = new SparkConf().setAppName(name).setMaster("local");
    	sc = new JavaSparkContext(sparkConf);
    }
     

	public JavaSparkContext getSc() {
		return sc;
	}

	
    private static class SingletonHolder
    {       
        private final static SparkSession instance = new SparkSession("SparkSession");
    }
 
    public static SparkSession getInstance()
    {
        return SingletonHolder.instance;
    }
} */