######################################################
######			 Experiment Setup				######
######################################################

# Change name to Experiment Setup
# V2 20140807
	# add write sample info file function
	# check statistic number: correct
	# correct gprtable flag problem. Line 45
  # put add additional info into seperate script
  # make varaible more clearly to read
# V1 2013 Bart
	# Gives Column Statistics for All GPR in a Directory

# setwd("\\\\biofs.biodesign.asu.edu\\CIM\\Research\\DocInABox\\People\\Tiger\\2014 FVBN Drug Treatment\\20141022_Drug Treatment\\gpr")
# source("D:\\Dropbox\\R\\scripts\\1Experiment Setup.R")

library(seqinr)
library(e1071)
library(tools)

###CHANGE THESE VALUES HERE####
Fmedian<-9		#This is the column of the gpr with the wavelength you want to keep
fileout<-"2014FVBN_Drug treatment_2014"		#This is the name of the file to save
# Naming must be "filename","Barcode","Batch","Position","Mice","Age","Run Date"
### Returns
	## gpr.a with peptide names in 1st column; gpr.b not; gprtable for raw
#### END CHANGE VALUES

read.gpr<-function(fnamein)
	{
	temp1<-readLines(fnamein,2)	#gets first two lines of a gpr with format information
	if (temp1[1]=="ATF\t1.0")	#asks if the file is a Axon Text File 
		{	
		temp2<-unlist(strsplit(temp1[2], split="\t"))	#gets number of header rows in the file
		temp3<-as.numeric(temp2[1])+2				#calculates the number of rows to skip
		data<-read.table(fnamein,sep='\t',skip=temp3,header=T)
		}
	return(data)
	}

get.data<-function(fnamein,columns)
	{
	temp1<-readLines(fnamein,2)	#gets first two lines of a gpr with format information
	if (temp1[1]=="ATF\t1.0")	#asks if the file is a Axon Text File 
		{	
		temp2<-unlist(strsplit(temp1[2], split="\t"))	#gets number of header rows in the file
		temp3<-as.numeric(temp2[1])+2				#calculates the number of rows to skip
		data<-read.table(fnamein,sep='\t',skip=temp3,header=T)
		temp5<-cbind(data[,columns],data$Flags)
		temp5<-as.data.frame(temp5)
		names(temp5)<-c(fnamein,"Flags") # change from fnamein,"Flags" to make flag colunm names
		print(c(fnamein,"Flags")) # change from fnamein,"Flags" to make flag colunm names
		return(temp5)
		}
	}

make.gpr.list<-function(filelist)
	{
  gprlist<-NULL;gprlist<-as.vector(gprlist) # need to generate gprlist
  for (j in c(1:length(filelist)))
		{
		fntemp<-s2c(filelist[j])
		lf<-length(fntemp)
		exten<-c2s(fntemp[(lf-3):lf]) #captures the extension
		if (exten==".gpr")
			{
			gprlist<-append(gprlist,filelist[j])
			}
		}
	return(gprlist)
	}

filelist<-list.files()
filelist<-make.gpr.list(filelist)	#reduces filelist to just the gprs

#Assumes first file in the list is a gpr
tempgpr<-read.gpr(filelist[1]) # use first gpr file to get info
feature.num<-length(tempgpr$Name)	#number of features in the gpr
gprtable<-as.data.frame(matrix(data=NA,nrow=feature.num,ncol=3))	#two columns to add for signal and flags


gprtable[,1]<-tempgpr$Name
gprcolvec<-c(Fmedian)	#Fmedian is input above, 9 is foreground signal and flags is in the script as $Flags
gprtable[,2:3]<-cbind(tempgpr[,gprcolvec],tempgpr$Flags)	#gets foreground signal
name<-c("Peptide",filelist[1],"Flags")
names(gprtable)<-name

numfiles<-length(filelist)
for (j in c(2:numfiles))
	{
	fnamein<-filelist[j]
	temp6<-get.data(fnamein,gprcolvec)
	try(gprtable<-cbind(gprtable,temp6)) #as a try statement in case temp6 is not returned
	}	

####Removes Bad Flags
valcol<-NULL
ncgt<-ncol(gprtable)
j<-2
while(j<ncgt)
	{
	a<-append(a,gprtable[1,j])
	valcol<-append(valcol,j)
	k<-j+1
	flagbad<-(row.names(gprtable[gprtable[,k]=="-100",]))
	gprtable[flagbad,j]<-NA
	j<-j+2
	}

## replace flag
gprtable[gprtable==0] <- "P"
gprtable[gprtable==-75] <- "P"
gprtable[gprtable==-100] <- "A"


## Raw data from gprs
# INPUT: gprtable,valcol
# OUTPUT: data- list with values med.em,peplist.u,gpr.b,gpr.a
clean<-function(gprtable,valcol)
{
	colnames(gprtable)<-file_path_sans_ext(colnames(gprtable)) # remove .gpr extension
	gpr.np<-gprtable[,valcol] # data without peptide names
	gpr.p<-cbind(gprtable[,1],gpr.np) # data with peptide names
	## remove landing lights from data
	pep.ld<-gpr.p[,1]=="LANDING_LIGHT"
	gpr.pnl<-gpr.p[!pep.ld,]
	## remove empty
	empty<-grepl("EMPTY",gpr.pnl[,1])
	pep.em<-gpr.pnl[empty,] # get expression for EMPTY spots
	med.em<-t(data.frame(apply(pep.em[,2:ncol(pep.em)],2,function(i)median(i,na.rm=T)))) # get median value for empty ones in each sample, verified
	## Unique peptide list
	peplist<-rbind(data.frame(gpr.pnl[!empty,1]),"EMPTY") # Peplist no landing light with 1 empty
	peplist.u<-make.unique(as.matrix(peplist[,1])) # peptide CSGPkHrWrHkPkHrWrHkk repeat twice. change WrHkk to WrHkk.1. Make unique of WrHkk peptide, change 2nd one to WrHkk.1; need to be as.matrix
	# check peptide name correct or not. 
	# Expect to see "CSGPkHrWrHkPkHrWrHkk.1","CSGPkHrWrHkPkHrWrHkk,CSGPkHrWrHkPkHrWrHkk.1","0"
	print(peplist.u[!peplist.u==peplist]) # show only "CSGPkHrWrHkPkHrWrHkk.1" is different
	## No Landing light & EMPTY. No peptide names in 1st column. Row names as peptide list.u
	gpr.b<-rbind(gpr.pnl[!empty,2:ncol(gpr.pnl)],med.em);rownames(gpr.b)<-peplist.u 
	print(rownames(gpr.b)[grepl("CSGPkHrWrHkPkHrWrHkk",rownames(gpr.b))]) # show two peptide correct
	print("Duplicated peptide number")
  print(sum(duplicated(rownames(gpr.b))))# show whether still have any duplicate or not
  ## No Landing light & EMPTY; With peptide in 1st column
	gpr.a<-cbind(peplist.u,gpr.b) # combine non EMPTY spots with single empty for each sample. gpr.a 1st column is factor, other are numerics.
	data<-list(med.em,peplist.u,gpr.b,gpr.a);names(data)<-c("med.em","peplist.u","gpr.b","gpr.a")
  data
}

data<-clean(gprtable,valcol)
gpr.a<-data$gpr.a
gpr.b<-data$gpr.b
#med.em<-data$med.em
peplist.u<-data$peplist.u

## Generate data output##
fnameout<-paste(fileout,"_gprtable",sep='')
write.csv(gpr.b,file=paste(fnameout,".csv"),row.names=TRUE,quote=FALSE) #CHANGE TO gpr.b for clean data without peptide name, gpr.a for clean data with peptide name, gprtable for raw data


## Calculates column stats use gpr.b
ncd<-ncol(gpr.b)
statistics<-matrix(0,7,ncd)
statistics<-as.data.frame(statistics)
row.names(statistics)<-c("Array","Median","95th %tile","5th %tile","DNR","Kurtosis","Skewness")
statistics[1,]<-names(gpr.b)
statistics[2,]<-apply(gpr.b,2,median,na.rm=TRUE)
statistics[3,]<-round(apply(gpr.b,2,quantile,0.95,na.rm=TRUE),2)
statistics[4,]<-round(apply(gpr.b,2,quantile,0.05,na.rm=TRUE),2)
statistics[5,]<-round((as.numeric(statistics[3,])/as.numeric(statistics[4,])),2)
statistics[6,]<-round(apply(gpr.b,2,kurtosis,na.rm=TRUE),2)
statistics[7,]<-round(apply(gpr.b,2,skewness,na.rm=TRUE),2)
statistics=t(statistics)

# print(statistics)
write.table(statistics,file=paste(fileout,"_stats.txt"),sep='\t',row.names=F,col.names=T)
