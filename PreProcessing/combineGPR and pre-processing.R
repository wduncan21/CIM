##dir /B >out.txt

library("limma", lib.loc="E:/Rpackage")

##Define directory path##
TF = "G:\\OneDrive\\experiments\\Human FS Array\\09012016\\GPR"
##get gpr files within all subfolders
files <- list.files(TF,recursive=T, pattern=paste('*.gpr',sep=""))
# read all gprs
RG <- read.maimages(files, source="genepix",columns=list(G="F635 Median"),path=TF, green.only=TRUE)# read the GPR files

test<-scan("G:\\OneDrive\\experiments\\Human FS Array\\09012016\\sample list.txt",what=character())#scan sample list
raw<-RG$E #get the signals from RG
colnames(raw)<-test #change colname to sample name
gene<-RG$gene  # get the gene list
gene<-gene$ID  # get only the peptide

median<-apply(raw,2,median) #get the median for each sample
norm<-sweep(raw,2,median,"/") #normalize the samples

ready<-avearrays(norm) #average samples with same name

## need to add gene column to dataset

require(data.table)
ready<-data.table(ready)
ready<-cbind(ready,gene)
final<-ready[ , lapply(.SD , mean ) , by = gene ] ##average signals with the same peptide

cols <- names(final)[2:ncol(final)]
final[,(cols) := round(.SD,5), .SDcols=cols]#round digits

write.table(final,"G:\\OneDrive\\experiments\\Human FS Array\\all ready",quote=F,row.names = FALSE,sep='	')
