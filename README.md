# Back2Back_Project

How to launch our project ? 

After cloning the project, the application can be launched with the launch.java class (in the default package). 

Our application requires to instantiate an object data with all the parameters needed to open the file and apply a model on it. 
We then need to instantiate an object DecisionTree or RandomForest. The idea is that with those two objects we can run a DecisionTree/RandomForest on the same dataset with the 3 tested machine learning libraries. However, we can't guarantee the exact same model to be runed by each library because they require different parameters.

* Parameters about the dataset: 
    * path
    * header (true or false)
    * name of the target, the variable to explain
    * what is the model's goal ? a classification or regression ? (Is the target categorical or continuous ?)
    * does the file have row names or id ? (should the first column be removed ?)
    * what are the separator and the decimal in the file ?
    * have the dataset some categorical features ? If so, the user have to fill a list with the names of the categorical variables.
 * Parameters for a DecisionTree:
     * maxDepth : maximum depth of the tree (needed for SparkML)
     * minSplit : minimum number of instances required for splitting a node
     * cp : complexity criteria
     * gini : criteria used for splitting (gini for classification, variance for regression)
     * minPerLeaf : minimum number of instances required in each final leaf
     * maxBins : 
  * Parameters for a RandomForest:
     * ntrees : number of tree to aggregate
     * mtry : percentage of features to select for each tree
     * sampsize : percentage of individuals to select for each tree
     * maxDepth : maximum depth for each tree
     * maxBins : 
     * seed : seed to fix the randomn behind the algorithm
     * featureSubsetStrategy : type of the random forest algorithm in SparkML ("auto" by default)
     * gini : split criteria (gini for classifcation problem, variance for regression) 


