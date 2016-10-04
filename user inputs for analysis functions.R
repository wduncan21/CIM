
## get the input
## this function is for 1 file only
input<-read.table("G:\\OneDrive\\experiments\\common disease signature\\new infections\\all ready delete bad samples.txt",header=F,sep = "	")
#class in row 1, row names is peptide
data=LoadingData(input)



#specify the classese to be analyzed, can be omitted,need to delete AnalyzeClass in 
#the function below
#AnalyzeClass=c("Bor","Cha","Den","Flu","Hep","HIV","Lym","Mal","Syp","Tub","Val","Wnv","ND")#ask which classes to use in feature selection
AnalyzeClass=c("Bor","Den","Syp")
#ask which classes to use in feature selection


matrix=CrossValidationR(data,10,AnalyzeClass)
