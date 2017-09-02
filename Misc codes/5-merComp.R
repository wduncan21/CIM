test<-scan("D:\\CIM\\mask experiments\\n-mer\\16aa_12.csv",what=character())##this will get input of the file. The input file shall have 1 sequence each line with no other information. Be sure to put\\ instead of \ for the input file location
seq<-rep(0,4290000)# seq is vector to store the 5mers
max<-length(test)#max is the number of sequences
n<-1
for (i in 1:max)
｛
maxi<-nchar(test[i]) ## nchar will get the length for each peptide
startmaxi<-maxi-4 # change 4-3 for 4mer analysis and change to 2 for 3mer analysis
for( j in 1:startmaxi)
{
seq[n]<-substring(test[i],j,j+4)# change 4-3 for 4mer analysis and change to 2 for 3mer analysis,this line cuts each peptides into 5mer and put it into seq
n<-n+1
}
}
new=table(seq)## calculate the frequency of each 5mer
length=length(new) # length of new should be the number of unique 5mers
length/16^5 # this is the percentage of 5mer coverage, 16 is the #of a.a.,5 is 5mer,change as needed
