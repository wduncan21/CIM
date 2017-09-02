#read in file, last one is the whole nr seqs
aa16_8o=read.table("J:\\final\\16aa-8-final.txt")
aa16_12o=read.table("J:\\final\\16aa-12-final.txt")
aa16_16o=read.table("J:\\final\\16aa-16-final.txt")
aa19_8o=read.table("J:\\final\\19aa-8-final.txt")
aa19_12o=read.table("J:\\final\\19aa-12-final.txt")
aa19_16o=read.table("J:\\final\\19aa-16-final.txt")
m35o=read.table("J:\\final\\35-2-100out-final.txt")
m70o=read.table("J:\\final\\70-2-100out-final.txt")
m140o=read.table("J:\\final\\140-2-100out-final.txt")
m272o=read.table("J:\\final\\272-1-100out-final.txt")
m323o=read.table("J:\\final\\323-2-100out-final.txt")
cim330ko=read.table("J:\\final\\330k-100out-final.txt")
m140_16aao=read.table("J:\\final\\M140-2-16aa-final.txt")
m272_16aao=read.table("J:\\final\\M272-2-16aa-final.txt")

#delete GI,leave occurence number and length
aa16_8o<-aa16_8o[,-1]
aa16_12o<-aa16_12o[,-1]
aa16_16o<-aa16_16o[,-1]
aa19_8o<-aa19_8o[,-1]
aa19_12o<-aa19_12o[,-1]
aa19_16o<-aa19_16o[,-1]
m35o<-m35o[,-1]
m70o<-m70o[,-1]
m140o<-m140o[,-1]
m272o<-m272o[,-1]
m323o<-m323o[,-1]
cim330ko<-cim330ko[,-1]
m140_16aao<-m140_16aao[,-1]
m272_16aao<-m272_16aao[,-1]

#select rows with occurence more than 1,change to other value as needed
aa16_8<-aa16_8o[which(aa16_8o[,2]>1),]
aa16_12<-aa16_12o[which(aa16_12o[,2]>1),]
aa16_16<-aa16_16o[which(aa16_16o[,2]>1),]
aa19_8<-aa19_8o[which(aa19_8o[,2]>1),]
aa19_12<-aa19_12o[which(aa19_12o[,2]>1),]
aa19_16<-aa19_16o[which(aa19_16o[,2]>1),]
m35<-m35o[which(m35o[,2]>1),]
m70<-m70o[which(m70o[,2]>1),]
m140<-m140o[which(m140o[,2]>1),]
m272<-m272o[which(m272o[,2]>1),]
m323<-m323o[which(m323o[,2]>1),]
cim330k<-cim330ko[which(cim330ko[,2]>1),]
m140_16aa<-m140_16aao[which(m140_16aao[,2]>1),]
m272_16aa<-m272_16aao[which(m272_16aao[,2]>1),]

#delete occurence number,leave only length
aa16_8<-aa16_8[,-2]
aa16_12<-aa16_12[,-2]
aa16_16<-aa16_16[,-2]
aa19_8<-aa19_8[,-2]
aa19_12<-aa19_12[,-2]
aa19_16<-aa19_16[,-2]
m35<-m35[,-2]
m70<-m70[,-2]
m140<-m140[,-2]
m272<-m272[,-2]
m323<-m323[,-2]
cim330k<-cim330k[,-2]
m140_16aa<-m140_16aa[,-2]
m272_16aa<-m272_16aa[,-2]

aa16_8n<-aa16_8o[,-2]
aa16_12n<-aa16_12o[,-2]
aa16_16n<-aa16_16o[,-2]
aa19_8n<-aa19_8o[,-2]
aa19_12n<-aa19_12o[,-2]
aa19_16n<-aa19_16o[,-2]
m35n<-m35o[,-2]
m70n<-m70o[,-2]
m140n<-m140o[,-2]
m272n<-m272o[,-2]
m323n<-m323o[,-2]
cim330kn<-cim330ko[,-2]
m140_16aan<-m140_16aao[,-2]
m272_16aan<-m272_16aao[,-2]

#change vector into matrix
aa16_8<-matrix(aa16_8)
aa16_12<-matrix(aa16_12)
aa16_16<-matrix(aa16_16)
aa19_8<-matrix(aa19_8)
aa19_12<-matrix(aa19_12)
aa19_16<-matrix(aa19_16)
m35<-matrix(m35)
m70<-matrix(m70)
m140<-matrix(m140)
m272<-matrix(m272)
m323<-matrix(m323)
cim330k<-matrix(cim330k)
m140_16aa<-matrix(m140_16aa)
m272_16aa<-matrix(m272_16aa)

aa16_8n<-matrix(aa16_8n)
aa16_12n<-matrix(aa16_12n)
aa16_16n<-matrix(aa16_16n)
aa19_8n<-matrix(aa19_8n)
aa19_12n<-matrix(aa19_12n)
aa19_16n<-matrix(aa19_16n)
m35n<-matrix(m35n)
m70n<-matrix(m70n)
m140n<-matrix(m140n)
m272n<-matrix(m272n)
m323n<-matrix(m323n)
cim330kn<-matrix(cim330kn)
m140_16aan<-matrix(m140_16aan)
m272_16aan<-matrix(m272_16aan)

#change all into numeric
aa16_8<-as.numeric(aa16_8)
aa16_12<-as.numeric(aa16_12)
aa16_16<-as.numeric(aa16_16)
aa19_8<-as.numeric(aa19_8)
aa19_12<-as.numeric(aa19_12)
aa19_16<-as.numeric(aa19_16)
m35<-as.numeric(m35)
m70<-as.numeric(m70)
m140<-as.numeric(m140)
m272<-as.numeric(m272)
m323<-as.numeric(m323)
cim330k<-as.numeric(cim330k)
m140_16aa<-as.numeric(m140_16aa)
m272_16aa<-as.numeric(m272_16aa)

aa16_8n<-as.numeric(aa16_8n)
aa16_12n<-as.numeric(aa16_12n)
aa16_16n<-as.numeric(aa16_16n)
aa19_8n<-as.numeric(aa19_8n)
aa19_12n<-as.numeric(aa19_12n)
aa19_16n<-as.numeric(aa19_16n)
m35n<-as.numeric(m35n)
m70n<-as.numeric(m70n)
m140n<-as.numeric(m140n)
m272n<-as.numeric(m272n)
m323n<-as.numeric(m323n)
cim330kn<-as.numeric(cim330kn)
m140_16aan<-as.numeric(m140_16aan)
m272_16aan<-as.numeric(m272_16aan)
#merge all matrix into a list
library(qpcR)
all<-list(aa16_8,aa16_12,aa16_16,aa19_8,aa19_12,aa19_16,m35,m70,m140,m272,m323,cim330k,m140_16aa,m272_16aa,aa16_8n,aa16_12n,aa16_16n,aa19_8n,aa19_12n,aa19_16n,m35n,m70n,m140n,m272n,m323n,cim330kn,m140_16aan,m272_16aan)
all<-do.call(qpcR:::cbind.na, all)


#change NA to 0
all[is.na(all)]<-0


#define result matrix
result<-rep(0,12*ncol(all))
result<-matrix(result,12)

#define length interval
min<-c(1,101,201,301,401,501,601,701,801,901,1001,10001)
max<-c(100,200,300,400,500,600,700,800,900,1000,10000,50000)

#get the number of seqs within a length interval
for (j in 1:length(min))
{
for (i in 1:ncol(all))
{
result[j,i]<-sum((all[,i]<=max[j])&(all[,i]>=min[j]))
}
}

#get the frequency matrix for all
freq<-rep(0,6*(ncol(result)))
freq<-matrix(freq,12)
for(i in 1:ncol(freq))
{
freq[,i]<-result[,i]/result[,i+14]
}

freq<-freq*100
write.table(freq,file="J:\\final\\freq_all.txt",sep="\t",row.names=c("length1-100","length101_200","length201_300","length301_400","length401_500","length501_600","length601_700","length701_800","length801_900","length901_1000","length1001_10000","length>10001"),col.names=c("aa16_8","aa16_12","aa16_16","aa19_8","aa19_12","aa19_16","m35","m70","m140","m272","m323","cim330k","m140_16aa","m272_16aa"))

