# Back2Back_Project

How to launch our project ? 

After cloning the project, the application can be launched with the launch.java class (in the default package). 

Our application requires to specify a few parameters about the dataset and the model before running the launchComparison() method: 
* Parameters about the dataset: 
    * path
    * header (true or false)
    * name of the target, the variable to explain
    * what is the model's goal ? a classification or regression ? (Is the target categorical or continuous ?)
    * does the file have row names or id ? (should the first column be removed ?)
    * what are the separator and the decimal in the file ?
    * have the dataset some categorical features ? If so, the user have to fill a list with the names of the categorical variables.


