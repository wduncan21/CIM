co=read.table("G:\\OneDrive\\experiments\\common disease signature\\new infections\\correlation",header=T)
library(corrplot)
row.names(co)=colnames(co)
co=as.matrix(co)

## define the color spectrum
col3 <- colorRampPalette(c("yellow","blue", "white","red")) 

## correlation clustering on all samples
corrplot(co, order="hclust",method="circle", addrect=2, cl.lim=c(0,1),col=col3(10))

## get only the bacterial and viral infections
BV=co[-c(2,8,11,13),-c(2,8,11,13)]
## correlation clustering on bacterial and viral only

corrplot(BV, order="hclust",method="circle", addrect=2, cl.lim=c(0,1),col=col3(10))


