# Back2Back Project

In this project, we aim to build a tool allowing us to easily launch back to back testing for machine learning algorithms. We work on three different machine learning libraries : Weka, Renjin and SparkML.

## How to launch our project ? 

After cloning the project, the application can be launched with the ```launch.java``` class (in the default package) : 

```git clone https://github.com/AlkaSaliss/Back2Back_Project.git```

Our application requires to instantiate a ```Data``` object with all the parameters required to open the file and apply a model on it. 
We then need to instantiate a ```DecisionTree``` or ```RandomForest``` object. The idea is that with those two objects, we can run a decision tree/random forest on the same dataset with the 3 considered machine learning libraries. However, we can't guarantee the exact same model configuration for each library, because they require different parameters which don't necessarly match.

* Parameters about the data: 
    * path : path of the file  (*String*)
    * header : does the file have header or not ? (true or false in *String*)
    * targetName : name of the target Y, the variable to explain (*String*)
    * classif : what is the model's goal ? a classification or regression ? (Is the target categorical or continuous ?) (*boolean* : true if classification, false if regression)
    * numClasses : if the target is categorial, the user have to provide its number of modality (*int*)
    * hasRowNames : does the file have row names or id ? (should the first column be removed ?) (true or false in *String*)
    * sep, dec : what are the separator and the decimal in the file ? (*String*) 
    * catNames : have the dataset some categorical features ? If so, the user have to fill a list with the names of the categorical variables. (*ArrayList<String>*)
 * Parameters for a ```DecisionTree```:
     * maxDepth : maximum depth of the tree (needed for SparkML) (*int*)
     * minSplit : minimum number of instances required for splitting a node (*int*)
     * cp : complexity criteria (*double*)
     * gini : criteria used for splitting (*boolean* true for gini/classifcation, false for variance/regression)
     * minPerLeaf : minimum number of instances required in each final leaf (*int*)
     * maxBins : number of bins used when discretizing continuous features (*int*)
  * Parameters for a ```RandomForest```:
     * ntrees : number of trees to aggregate (*int)
     * mtry : percentage of features to select for each tree (*double*)
     * sampsize : percentage of individuals to select for each tree (*double*)
     * maxDepth : maximum depth for each tree (*int*)
     * maxBins : number of bins used when discretizing continuous features (*int*)
     * seed : seed to fix the randomness (*int*)
     * featureSubsetStrategy : type of random forest algorithm in SparkML (*String*, "auto" by default)
     * gini : split criteria (gini for classifcation problem, variance for regression) (*boolean* true for gini/classification, false for variance/regression)
   
We have chosen in our ```launch.java``` to compare the libraries on a decision tree for a regression task. 
The dataset used has been generated randomly for the purpose of the application. We wanted a dataset with a continuous target and with both continuous and categorical features. 

Besides, our application also handle classification problem: we can apply a decision tree or a random forest on the iris dataset (located in data/iris.csv). 

### Authors

* **ABOUBACAR ALKA Mahamadou Salissou**
* **FORMAL Thibault**
* **GIRARD Naomi**
* **KEITA Seydou**
