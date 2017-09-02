old<-read.table(file="F:\\Dropbox\\mask paper\\V7_change\\comparison\\out3.txt")
new<-sqldf("select reverse(V1) from old")
write.table(new,file="F:\\Dropbox\\mask paper\\V7_change\\comparison\\length-reversed.txt",quote=FALSE,row.names=FALSE,col.name=FALSE)
