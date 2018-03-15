<h1> Back2Back testing of machine learning algorithms </h1>

<h2> ABOUBACAR ALKA Mahamadou Salissou, FORMAL Thibault, GIRARD Naomi, KEITA Seydou </h2>

<h1> Context & objectives </h1>

In this project, we aim to build a tool allowing us to easily launch Back2Back testing for machine learning algorithms. 


> Formally, Back to Back testing is performed to check the likeliness of operational results for a series of inputs on two or more similar components of different versions. 
> 

With the recent growing interest in machine learning in production systems, many frameworks/libraries have been developped. From the former (SAS, Weka etc.) to the new contenders (e.g. Tensorflow), Data Scientists have access to a wide range of tools. Their choice depend on objectives/needs (PoC, Distributed DataBases, Deep Learning etc.). Despite these specific features, most of the classical machine learning algorithms (e.g. linear regression, decision trees, random forests etc.) are part of these frameworks.

Obviously, Decision Trees in different libraries **should** behave the same, i.e. no matter their implementation. It is then interesting to compare these behaviors. Are the performances (~metrics) similar among the libraries ? What about computing performances ? We could even be interested in a question such as "for a classification task, does a random forest with SparkML is more precise (w.r.t. to accuracy) than a SVM with Weka ?"

*How can one achieve that ?*

For instance, we could consider the same dataset (let's say the iris dataset), and one type of model (let's say a decision tree), with given hyperparameters (e.g depth). For this exact same configuration, we can train and evaluate models with different libraries, and check if results (in terms of accuracy in our case) are similar or not.

***

For this project, we will focus on three machine learning libraries:


<img src="/images/index.jpeg" width="200">
<img src="https://2xbbhjxc6wk3v21p62t8n4d4-wpengine.netdna-ssl.com/wp-content/uploads/2017/06/spark-mllib-logo.png" width="200"> 
<img src="http://www.renjin.org/assets/img/logo.svg" width="200">

* [Weka](https://en.wikipedia.org/wiki/Weka_(machine_learning)) (*Waikato Environment for Knowledge Analysis*), is a suite of machine learning software written in Java, developed at the University of Waikato, New Zealand, and has been *in the game* for quite a long time now.

* [SparkML](https://spark.apache.org/docs/latest/ml-guide.html) is Spark Machine Learning Library. It allows to design machine learning algorithms in a distributed environment.

* [Renjin](http://www.renjin.org/) is an alternative interpreter for the R programming language designed to run in the JVM.

***

The main objective of this project is to build a tool allowing us to **simply** launch comparison procedures among different libraries, by abstracting the intern mechanisms of each library. For this purpose, we focused on software engineering aspects in order to propose a soft and flexible interface.

This project will serve as a basis for a larger project, which will include a web interface for back to back testing. Hence, we can imagine a scenario where a user:
* loads a dataset in a *csv* file
* gives some basic information about the data (the name of the column to explain Y, if there is a header etc.)
* chooses a type of model to test (e.g. random forest)
* sets some hyperparameters which correspond to the model configuration (for instance number of trees in this case)
*  then launches a comparison procedure, which will display in real time results like metrics or even better, graphs (in our future web interface). 
 
<h1> Modelization </h1>

In this part, we give insights and justifications for our model. First, we give general ideas for the project, and then we focus on each library in detail. Indeed, each library has its very own specific synthax/interface, and require different considerations. We also point out some limitations, giving us guidelines for future improvement.

<h2> General comments concerning the project </h2>

* For the project, we have considered only two kinds of machine learning algorithms: decision trees & random forests. The way the code has been structured allows us to easily add new machine learning algorithms (SVM, linear regression, boosting methods, etc.).
* These two algorithms both treat regression and classification. Hence, we need two kinds of metrics in order to evaluate models. 
</br>
<a href="http://www.codecogs.com/eqnedit.php?latex=MSE&space;=&space;\frac{1}{n}&space;\sum&space;\limits_{i=1}^n&space;(Y_i&space;-&space;\hat{Y_i})^2&space;\quad&space;\text{for&space;regression}" target="_blank"><img src="http://latex.codecogs.com/gif.latex?MSE&space;=&space;\frac{1}{n}&space;\sum&space;\limits_{i=1}^n&space;(Y_i&space;-&space;\hat{Y_i})^2&space;\quad&space;\text{for&space;regression}" title="MSE = \frac{1}{n} \sum \limits_{i=1}^n (Y_i - \hat{Y_i})^2 \quad \text{for regression}" /></a>
</br>
<a href="http://www.codecogs.com/eqnedit.php?latex=Accuracy&space;=&space;\frac{1}{n}&space;\sum&space;\limits_{i=1}^n&space;1_{Y_i&space;\neq&space;\hat{Y_i}}&space;\quad&space;\text{for&space;classification}" target="_blank"><img src="http://latex.codecogs.com/gif.latex?Accuracy&space;=&space;\frac{1}{n}&space;\sum&space;\limits_{i=1}^n&space;1_{Y_i&space;\neq&space;\hat{Y_i}}&space;\quad&space;\text{for&space;classification}" title="Accuracy = \frac{1}{n} \sum \limits_{i=1}^n 1_{Y_i \neq \hat{Y_i}} \quad \text{for classification}" /></a>

 Adding more metrics (e.g. *Mean Absolute Error* for regression or *Recall/Precision* for classification) is quite easy.
* Moreover, for the impurity hyperparameter, we set *Gini* for classification & *Entropy* for regression.
* In order to deal with data opening, we have chosen to use a ```Data``` class, which holds all needed metadata (e.g. name of the column to predict, if there is a header, the separator etc.), and contains three methods to open data in the formats required by each library. One could easily add other methods for other libraries. This class allows to manipulate only one data object, and mask the intern complexity of libraries' data management.
* We have implemented a general data model allowing to open any *csv* file, and deal with both categorical and continuous features/target. 
* Even though it may represent more *work* for the user (altough we can set some default parameters), the model allows more flexibility. We don't have to pre-suppose a data structure, as "always first column for the target" → the user will have to provide this information.
* For instance, the user will provide a boolean classif in ```Data``` in order to know whether it is a regression or a classification problem. This is needed to encode the target behavior, to decide which metric will be used etc.
* We have split the problem by library. Thus, we have a generic ```SparkModel``` abstract class, from which inherit all our models implemented in Spark (e.g. ```SparkDecitionTree``` etc.). We have the same ideas for the two remaining libraries.
* In order to get a clean interface, we took inspiration on the *Python scikit-learn* library, which is today a standard in the machine learning community. Hence, we want our model objects to have a *fit*, *eval*, and *split* methods. All our classes will implement this interface.
* Contrary to the *scikit-learn API*, each type of model contains the data (complete, train and test) as attributes. Hence, a model is **associated to a data set**. This can seem redundant and somehow limitating, but this will be explained why in the following.
* We deal with data splitting (train/test) **inside** classes. Hence, we cannot ensure the same split among the objects correponding to different libraries. But, when comparing models, we do not work on a single train/test split, but rather run several split procedures (and then train and test), and compute for instance the average performance. Hence, we can now compare results, which are statistically more significant (we are close to a cross-validation procedure).
* We also add a specific model object corresponding to a type of machine learning algorithm. For instance, we have a ```DecisionTree``` class, storing all metadata (which are the same, regardless of the library) concerning a decision tree (e.g. Depth, Impurity etc.). All our specialized objects (```SparkDecisionTree, WekaDecisionTree, RDecisionTree```) share a reference to this object.
* One problem is that models  in different libraries do not always require the same hyperparameters for training. Hence, when initializing a decision tree model to compare different libraries, we need to set all hyperparameters. Whereas ```SparkDecisionTree``` or others won't need all of them.
* We can instantiate a model this way: 
```
SparkDecisionTree sdt = new SparkDecisionTree(d, dt, proprTrain);
```
Where *d* is a ```Data``` object, *dt* is a ```DecisionTree``` object and *propTrain* is the proportion of training instances. This call is rather similar to other libraries, i.e.:
```
WekaDecisionTree wdt = new WekaDecisionTree(d, dt, proprTrain);
```
Sharing this same interface, we may have been able to improve this using a pattern behaving as an *Abstract Factory*, and giving us an instantiation such as:
```
FacoryDecisionTree fdt = new FactoryDecisionTree(d, dt, proprTrain);
SparkDecisionTree sdt = fdt.createModel("Spark");
```

* When we run the ```evalAggregate``` method from the ```Model``` class, we are in fact changing in the end the internal state of the model, because train and test sets are attributes of the models. As we split/train the data/model *nbIter* times, after the procedure, the model will correspond to the final iteration of ```evalAggregate```. → this behavior should be avoided

Below, is the (restricted) class diagram implemented. For simplicity, only the decision tree on a detailed part of Spark was represented, but the ideas were quite similar for the remaining parts.

</br>

<img src="/images/modelisation.png" />

<h2> Zoom on the libraries </h2>

<h2> Weka </h2>

* Weka is the simplest among the three libraries due to the fact that it's developed in Java. Hence, working with Weka is as easy as working with *standard* Java objects.
* We have chosen to create two Weka Models in the ```WekaDecisionTree``` class in order to distinguish regression tree ([M5P](http://weka.sourceforge.net/doc.dev/weka/classifiers/trees/M5P.html) instance) from classification tree ([J48](http://weka.sourceforge.net/doc.dev/weka/classifiers/trees/J48.html) instance). Indeed, unlike the two other libraries which require only one parameter to differentiate classification from regression, Weka deals with these two models using different classes.
```
private J48 classifier= new J48(); //for classification
private M5P regTree= new M5P(); //for regression
	
```
* We could improve this part by using the superclass of these classes ([AbstractClassifier](http://weka.sourceforge.net/doc.dev/weka/classifiers/AbstractClassifier.html)) and manage model fiting with *if and else*.

<h2> SparkML </h2>

* When using Spark, we need to manipulate a ```SparkContext``` object (after creating a ```SparkConfiguration```). The ```SparkContext``` allows the Spark driver application to access the cluster through a resource manager. In this case, we don't have a cluster, but still need the context to open data. Only one ```SparkContext``` must be active per JVM. Hence, we decided to use a Singleton pattern to deal with the context & provide a single access point to it.

<img align=center src="/images/singleton.png" width=300/>

</br>
</br>

* For training, Spark requires some informations not needed by the other libraries, such as the number of classes for the target label. With our structure, we can only compute this value when reading the data into Spark format (hence in the ```readSpark``` method from ```Data```). We then have to store this value as an attribute of the class in order to access it later (i.e. when we want to set this attribute for ```SparkModel```). This is kind of redundant, and maybe this could be avoided somehow.

<h2> Renjin </h2>

* Like SparkML, Renjin needs a context of type ```RenjinScriptEngine```. 
```
private RenjinScriptEngine engine;
	
```
* This is the **core** object that allows us to execute R code in Java. In contrast to SparkML, this context stores most of the variables needed to fit the model, such as, complete data set, train and test datasets, options to pass to the model, etc. 
For instance, to read a *csv* file, all that is needed is its path and some options (separator, row names etc.) and then to pass them as a string to the engine object this way:
```
/** Reading the data with r*/
engine.eval("data <- read.csv(\"" + this.path + "\", header=" + this.header.toUpperCase() + ", sep=" + this.sep + ", row.names=" + rownames + ")");
	
```

We have chosen this alternative because of its simplicity. It's a little difficult to use directly Java complex structure types in ```RenjinScript```. Storing in ```RenjinScript``` avoids errors and large code lines. We can see from the above example that our *csv* file is read and stored in an R dataframe named ```data``` in just one line of code; while reading it first in java and passing it to R would have required much more lines and a complex preprocessing.


<h1> Extensions & Improvements</h1>

* Add methods to reset models when changing data or split 
* Enhance/add comparison-like methods (```launchComparison```). This will strongly depend on how we model the user interface/interactions in the web part → hence it could be adjusted later.
* We could also add a general ```Method``` class from which our machine learning algorithms classes (```DecisionTree```, ```RandomForest```) will inherit. It will simplify and generalize our final method ```launchComparison``` (which for now is dependent of the models type). Hence, we could easily launch comparison for any kind of algorithm with a single method.
