norm<-read.table("G:\\CIM\\DHS\\norm1", header = TRUE, sep = "\t",quote = "")
test<-scan("G:\\CIM\\DHS\\sample list.txt",what=character())#scan sample list
colnames(norm)<-test
library("limma", lib.loc="E:/Rpackage")
ready<-avearrays(norm) #average samples with same name

ready<-round(ready,digits=5) #round digits
class(ready)<-"numeric"

write.table(final,"G:\\OneDrive\\experiments\\common disease signature\\DHS samples\\normmerged.txt",quote=F,row.names = FALSE,sep='\t')

require(data.table)
norm<-data.table(norm)
final<-norm[ , lapply(.SD , mean ) , by = gene ]

