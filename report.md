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


<img src="data:image/jpeg;base64,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" width="200">
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
     $$ MSE = \frac{1}{n} \sum \limits_{i=1}^n (Y_i - \hat{Y_i})^2 \quad \text{for regression}$$
     $$ Accuracy = \frac{1}{n} \sum \limits_{i=1}^n  \mathbb{1}_{Y_i \neq  \hat{Y_i}} \quad \text{for classification}$$
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

<img src="/uploads/upload_7ffd4223d4e7f22898b60eacb99246fe.png" />

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

<img align=center src="/uploads/upload_fb28ca8df1dfa4e5585d0ab574ee7574.png" width=300/>

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