#amino acid pool to choose from
x<-c('A','D','E','F','G','H','K','L','N','P','Q','R','S','V','W','Y','I','M','T')
#read the name and length you need, input file should be "GI\acc length"
z<-read.table(file="/home/lwang138/blast/randomDB/test.txt",sep='$')
for(i in 1:nrow(z))
{
#generate peptides
y<-sample(x,z[i,2],replace=TRUE)
#paste all amino acid together
a<-paste(y,collapse='')
write.fasta(sequences = a, names = z[i,1], file.out = "/home/lwang138/blast/randomDB/test.fasta",open="a")
}