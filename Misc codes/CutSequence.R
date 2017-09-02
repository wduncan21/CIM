test<-scan("J:\\rnm.txt",what=character())
result<-rep(0,20000)
result<-matrix(result,20000)
start<-1
seq<-1
for(i in 100:10000)
{
end<-start+i 
result[seq,1]<-substring(test,start,end)
start<-end+1
seq<-seq+1
}
i<-100
for(i in 100:10000)
{
end<-start+i 
result[seq,1]<-substring(test,start,end)
start<-end+1
seq<-seq+1
}
i<-10000
for(i in seq(10000,50000,10000))
{
end<-start+i 
result[seq,1]<-substring(test,start,end)
start<-end+1
seq<-seq+1
}
write(result,file="J:\\rnd-out.txt",ncolumns=1)
