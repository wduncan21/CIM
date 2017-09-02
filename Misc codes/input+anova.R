
#cutoff=4.067455e-07 #define cutoff
#anovagroup=c("Den","Mal","Syp")#ask which classes to use in anova


## loading data function, including make peptide as row.name, sample class as col.name, and make data into numeric and matrix

input<-read.table("G:\\OneDrive\\experiments\\common disease signature\\First Infectious Disease Dataset\\330k llnl raw.csv",header=F,sep = ",")#class in row 1, row names is peptide

#need to fix this function!!
LoadingData=function(input){
   data<-as.matrix(input)  #into data.matrix
   rownames(data)<-data[,1]
   data<-data[,-1] #change peptides into row names and delete peptide column

   label<-data[1,]
   colnames(data)=label
   data<-data[-1,] #change class into label and delete class row
   class(data)<-'numeric'#change class to numeric
   
}

LoadingData(input)

anovainput=data[, colnames(data) %in% anovagroup]## select the classes you want to use in anova
#anovainput=test[rownames(test) %in% anovagroup,]## select the classes you want to use in anova
#labelanova=rownames(anovainput)
labelanova=colnames(anovainput)

##anova,probabily need to redefine after labelanova is changed
anovafunction<-function(x){anova(aov(x~labelanova))[['Pr(>F)']][1]}

#results <- apply(anovainput,2,anovafunction) # each sample in one row

results <- apply(anovainput,1,anovafunction) #each sample is in one column, mostedly used type
combine<-cbind(data,results)  #combine the pvalue with the raw data

anovaout=combine[order(combine[,'results'],decreasing = FALSE),]# sort by pval, cutoff be 0.05/120k=4.067455e-07per tukey correction

cutoff=4.067455e-07
sigpep<-subset(anovaout,results<cutoff)# this is the # of sig peptides
nrow(sigpep)#check how many are there ,if too many, use top 2000

toppep=anovaout[1:2000,]
##t=t(toppep)## in the common type need to tranpse it
write.csv(t,file='G:\\OneDrive\\experiments\\common disease signature\\new infections\\ANOVA3classesForWeka.csv')#write the result
