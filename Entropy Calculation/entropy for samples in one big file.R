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

cal_entropy_from_file=function(folder,file){
  ##Define directory path##
  ##get gpr files within all subfolders
  library(entropy)
  library(plyr)
  input=read.table(paste(folder,file,sep='\\'),sep='\t',header = F)
  data=LoadingData(input)
  result=apply(data, 2,entropy)
  result=t(t(result))
  write.table(result,paste(folder,'Entropy_result.txt',sep="\\"),quote=F)
}

cal_entropy_from_file("G:\\OneDrive\\experiments\\dog cancer\\120k HSA vs normal dog","dog normalized for R.txt")


