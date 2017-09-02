# User guide for using the code

## Introduction of the code
This code takes in data file and output classification confusion matrix with 10-fold cross-validation. Feature selection is done for each fold and SVM is used as the classifier. This code offers fast processing time because of no redundant being calculated in each step. R built in T-Test is very slow and was implemented from scratch, along with some other speed up modifications.

## User input
minimal coding is required to use this code. The input format must be as described below. The format is the general data format used in CIM. Check with Phil for more info. 

Separator for each column is Tab or \t for regular expression
First row: Peptide	Sample1	Sample2	.....
Second row: Peptide1 signal_Sample1	signal_Sample2.....
......

Overall, Each column is all signal for one sample, each row is all the signal for one peptide(feature)

## Parts need to be modified for use
User only need to modify:
1. line 199, change the read.table file to your datafile
2. line 206, analysis class, This is the sample group label used in datafile row 1.

Note: If not specified analysis class, can remove line 206 and delete ",AnalyzeClass" in line 207, the program automatically use all class labels in the data to perform classification.

## Running the file
Run the whole file after the above steps. The output will be the confusion matrix. Column is actual label, row is predicted label.