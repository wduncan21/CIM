########################################
########################################
### used after reading table from data file
########################################

LoadingData=function(input){
  if (missing(input))
    stop("Error:Need to specify dataset for analysis")
  
  data<-as.matrix(input)  #into data.matrix
  rownames(data)<-data[,1]
  data<-data[,-1] #change peptides into row names and delete peptide column
  
  label<-data[1,]
  colnames(data)=label
  data<-data[-1,] #change class into label and delete class row
  class(data)<-'numeric'#change class to numeric
  data
}


########################################
########################################
## calculate fold change. 
########################################
## takes two inputs, the dataset Selected, and the vector implying locations group
## group is the location of one of the groups


FoldChange=function(data,group,fold=1.1){
  Pos=data[,group]
  Neg=data[,-group]
  ## Get the fold change, only select Fold Change >1.1 for each disease 
  ## then merge the mean column with dataset for t-test calculation
  #MeanPos=as.matrix(apply(Pos,1,mean))
  #MeanNeg=as.matrix(apply(Neg,1,mean))
  ## Get the fold change, only select Fold Change >1.1 for each disease 
  ## then merge the mean column with dataset for t-test calculation
  #MeanPos=as.matrix(apply(Pos,1,mean))
  #MeanNeg=as.matrix(apply(Neg,1,mean))
  MeanPos=apply(Pos,1,mean)
  MeanNeg=apply(Neg,1,mean)
  FoldChange=MeanPos/MeanNeg
  #colnames(FoldChange)="FoldChange"
  CombineFold=cbind(data,FoldChange)##,MeanPos,MeanNeg
  TTestInput=subset(CombineFold,FoldChange>fold)
  TTestInput=subset(TTestInput,select=-FoldChange)
  TTestInput
}

########################################
########################################
## ttest with speed. increase ttest calculation by 3 fold, assuming non-equal variance
########################################
## takes two inputs, the dataset, and the vector implying locations group
## group is the location of one of the groups
FastTtest=function(data,group) {
  MeanPlus=apply(data[,group],1,mean)
  MeanMinus=apply(data[,-group],1,mean)
  #MeanPlus=as.matrix(apply(data[,group],1,mean))
  #MeanMinus=as.matrix(apply(data[,-group],1,mean))
  dif=MeanPlus-MeanMinus
  n1=ncol(data[,group])
  n2=ncol(data[,-group])
  #var1=as.matrix(apply(data[,group],1,var))
  #var2=as.matrix(apply(data[,-group],1,var))
  
  var1=apply(data[,group],1,var)
  var2=apply(data[,-group],1,var)
  
  t=dif/sqrt(var1/n1+var2/n2)
  df=(var1/n1+var2/n2)^2/(((var1/n1)^2)/(n1-1)+((var2/n2)^2)/(n2-1))
  pval = 2*pt(-abs(t), df)
  #colnames(pval)=c('pval')
  pval
} 





########################################
########################################
##One Vs All Selection, currently only ttest, can change to other tests after modify codes
########################################
# data need to be one sample each columnm, class as column name
# AnalyzeClass section ask which classes to use in feature selection 
# can be like===c("Den","Mal","Syp")
FeatureSelection=function(dataset,AnalyzeClass,FoldChange=TRUE,cutoff){

  ########
  ## checking inputs
  if (missing(AnalyzeClass)){
    print("AnalyzeClass not specified, using all classes in dataset for analysis")
    AnalyzeClass=unique(colnames(data))
  }
  if (missing(cutoff)){
    cutoff=nrow(data)
  }
  ########
  
  ## select the classes you want to use in ttest
  Selected=dataset[, colnames(dataset) %in% AnalyzeClass]
  
  ## main body, apply through each class and select 100 top peptides
  PepList=lapply(AnalyzeClass,function(x){
    group=which( colnames(Selected)==x )## get the col.number by col.name
    
    ##Calculate fold change to select significant peptides if needed
    if(FoldChange==TRUE){
      TTestInput=FoldChange(Selected,group)
    } else {TTestInput=Selected}
    #continue to calculate ttest and add to matrix
    pval=FastTtest(data=TTestInput,group=group)
  
    CombinePval<-cbind(TTestInput,pval)
    # sort by p value
    SortbyPval=CombinePval[order(CombinePval[,'pval'],decreasing = FALSE),]
    
    ## select at most 100 peptides each group
    SigPep=subset(SortbyPval,pval<cutoff)
    if(nrow(SigPep)>100){
      SigPep=SigPep[1:100,]
    }
    SigPep=subset(SigPep,select=-pval)
    #SigPep=cbind(SigPep,source=x)
    SigPep
  })
  SigPepFinal=do.call(rbind,PepList)
  SigPepFinal
}



## True k-fold cross validation that split dataset into training and testing first then do feature selection
## need to run function FeatureSelection first, it is used in this function
CrossValidationR=function(dataset,fold,AnalyzeClass){
  if (missing(dataset))
    stop("Error:Need to specify dataset for analysis")
  if (missing(fold)){fold=10}
  if (missing(AnalyzeClass)){
    print("warning:AnalyzeClass not specified, using all classes in dataset for analysis")
    AnalyzeClass=unique(colnames(dataset))
  }
  
  
  library(e1071)
  ## select samples in the to be analyzed classes
  dataset=dataset[, colnames(dataset) %in% AnalyzeClass]## 

  #Randomly shuffle the data
  dataset<-dataset[,sample(ncol(dataset))]
  
  ##store peptides and make rownames unique
  pep=rownames(dataset)
  rownames(dataset)=1:nrow(dataset)
  #Create 10 equally size folds
  folds <- cut(seq(1,ncol(dataset)),breaks=fold,labels=FALSE)
  
  #Perform k fold cross validation
  CrossValOut=lapply(1:fold,function(i){
    
    #Segement your data by fold using the which() function 
    testIndexes <- which(folds==i,arr.ind=TRUE)
    
    #split the training and testing data
    testDataraw <- dataset[,testIndexes]
    trainDataraw <- dataset[,-testIndexes]
    
    #run feature selection
    trainData=FeatureSelection(trainDataraw,AnalyzeClass) # need to make sure there is no source column for the output
    
    #order the rows ,make peptides unique and get corresponding test data peptides
    trainData=trainData[ order(as.numeric(row.names(trainData))),]
    trainData=unique(trainData)
    testData=testDataraw[rownames(testDataraw) %in% rownames(trainData),]
    
    #classes need to arrange in rows
    trainData=t(trainData)
    testData=t(testData)
    #get the classed and make row.names unique
    classTrain=as.factor(row.names(trainData))
    rownames(trainData) <- 1:nrow(trainData)
    classTest=as.factor(row.names(testData))
    rownames(testData) <- 1:nrow(testData)
    
    
    #train and test the SVM model for current fold
    SvmModel=svm(trainData,classTrain)
    pred=predict(SvmModel,testData)
    # need to change the table into factors, or risk of conformable
    table(pred,factor(classTest, levels = levels(pred)))
  }
  )
  CrossValResult=Reduce("+",CrossValOut)
}

## get the input
input=read.table("G:\\OneDrive\\experiments\\dog cancer\\120k HSA vs normal dog\\dog normalized for R.txt",header=F,sep = "	")
data=LoadingData(input)



#specify the classese to be analyzed
#AnalyzeClass=c("Bor","Cha","Den","Flu","Hep","HIV","Lym","Mal","Syp","Tub","Val","Wnv","ND")#ask which classes to use in feature selection
AnalyzeClass=c("HSA","ND")#ask which classes to use in feature selection
matrix=CrossValidationR(data,10,AnalyzeClass)
print(matrix)
