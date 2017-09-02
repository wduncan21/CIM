read<-read.table("F:\\CIM\\DTRA\\pepcor.txt",sep='\t', header=F)

row.names(read)<-read[,1]
read<-read[,-1]
read2<-read
r=cor(t(read),t(read2))

neg=1
while (neg==1){

for (i in 1:ncol(r)){

diff<-length(which(r[,i]<0.9))
if (diff >3){
r<-r[,-i]
r<-r[-i,]
neg=1
break}
neg=0
}
}

write.table(r,file="F:\\CIM\\DTRA\\cor-selected.txt",col.names=T,row.names=T,quote=F,sep='\t')


dissimilarity <-1-r
distance <- as.dist(dissimilarity)
plot(hclust(distance), main="Dissimilarity = 1 - Correlation", xlab="")
