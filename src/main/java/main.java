import org.apache.spark.api.java.JavaSparkContext;
import java.io.Serializable;
import fr.ensai.sparkml.SparkSession;
import metier.Data;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.regression.LabeledPoint;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SparkSession session = SparkSession.getInstance();
		JavaSparkContext sc = session.getSc();
		
		Data d = new Data("data/iris.csv", "Species", "true");
		JavaRDD<LabeledPoint> dataSpark = d.readSpark(sc);
		System.out.println(dataSpark.take(1));
	}

}
