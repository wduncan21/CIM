#amino acid pool to choose from
x<-c('A','D','E','F','G','H','K','L','N','P','Q','R','S','V','W','Y','I','M','T')
z<-expand.grid(x,x,x,x,x,stringsAsFactors=FALSE)
y<-character(length=nrow(z))
y<-matrix(y,ncol=1)

for (i in 1:nrow(z))
{
y[i]<-paste(z[i,],collapse="")
}
write.table(y,file="B:\\experiments\\blast\\n-mer\\All_5mer.txt")
write.table(z,file="B:\\experiments\\blast\\n-mer\\All_5mer-sep.txt")