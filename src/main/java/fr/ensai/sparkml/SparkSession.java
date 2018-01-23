package fr.ensai.sparkml;
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


public class SparkSession
{       
	
	private JavaSparkContext sc;
	
    /** Constructeur privé */  
    private SparkSession(String name)
    {
    	SparkConf sparkConf = new SparkConf().setAppName(name).setMaster("local");
    	sc = new JavaSparkContext(sparkConf);
    }
     

	public JavaSparkContext getSc() {
		return sc;
	}

	
    /** Holder */
    private static class SingletonHolder
    {       
        /** Instance unique non préinitialisée */
        private final static SparkSession instance = new SparkSession("SparkSession");
    }
 
    /** Point d'accès pour l'instance unique du singleton */
    public static SparkSession getInstance()
    {
        return SingletonHolder.instance;
    }
}