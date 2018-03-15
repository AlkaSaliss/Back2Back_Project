# Back2Back_Project

How to launch our project ? 

After cloning the project, the application can be launched with the launch.java class (in the default package). 

Our application requires to instantiate an object data with all the parameters needed to open the file and apply a model on it. 
We then need to instantiate an object DecisionTree or RandomForest. The idea is that with those two objects we can run a DecisionTree/RandomForest on the same dataset with the 3 tested machine learning libraries. However, we can't guarantee the exact same model to be runed by each library because they require different parameters.

* Parameters about the dataset: 
    * path (*String*)
    * header (true or false in *String*)
    * name of the target, the variable to explain (*String*)
    * what is the model's goal ? a classification or regression ? (Is the target categorical or continuous ?) (*Boolean* : true if classification, false if regression)
    * does the file have row names or id ? (should the first column be removed ?) (true or false in *String*)
    * what are the separator and the decimal in the file ? (*String*) 
    * have the dataset some categorical features ? If so, the user have to fill a list with the names of the categorical variables. (*ArrayList<String>*)
 * Parameters for a DecisionTree:
     * maxDepth : maximum depth of the tree (needed for SparkML) (*int*)
     * minSplit : minimum number of instances required for splitting a node (*int*)
     * cp : complexity criteria (*double*)
     * gini : criteria used for splitting (*Boolean* true for gini/classifcation, false for variance/regression)
     * minPerLeaf : minimum number of instances required in each final leaf (*int*)
     * maxBins : (*int*)
  * Parameters for a RandomForest:
     * ntrees : number of tree to aggregate (*int)
     * mtry : percentage of features to select for each tree (*double*)
     * sampsize : percentage of individuals to select for each tree (*double*)
     * maxDepth : maximum depth for each tree (*int*)
     * maxBins : (*int*)
     * seed : seed to fix the randomn behind the algorithm (*int*)
     * featureSubsetStrategy : type of the random forest algorithm in SparkML (*String*, "auto" by default)
     * gini : split criteria (gini for classifcation problem, variance for regression) (*Boolean* true for gini/classification, false for variance/regression)


