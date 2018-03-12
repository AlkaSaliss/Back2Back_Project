//package metier;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaSparkContext;
//
//public class Session{
//
//	  //Le singleton
//
//	  private static Session sess = new Session();
//
//	  //Variable d'instance
//
//	  private JavaSparkContext sc;
//
//	   
//
//	  //Constructeur privé
//
//	  private Session(){
//		SparkConf sconf = new SparkConf().setAppName(name).setMaster("local");
//	    	sc = new JavaSparkContext(sparkConf);
//
//	  }
//
//	   
//
//	  //Méthode d'accès au singleton
//
//	  public static Session getInstance(){
//
//	    if(sess == null){
//		sess = new Session();
//		}      
//	    return sess;
//
//	  }
//
//	   
//
//	  //Accesseur 
//
//	  public String getContext(){
//
//	    return this.sc;
//
//	  }
//
//	}