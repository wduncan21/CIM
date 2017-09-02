test<-scan("G:\\OneDrive\\experiments\\1.txt",what=character())#scan your peptides
test<-cbind(test,nchar(test))#column bind the peptides with their lenth
colnames(test)<-c("a","b")#change column names so you can call it later
test<-as.data.frame(test)#change test to data.frame
n11<-subset(test,test$b==11)#this one will get 11mer,change according to need