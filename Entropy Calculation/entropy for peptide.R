entropy_per_pep=function(pep_lib){
  #library is the data matrix of peps, with each peptide one each row
  ## this function calculates entropy per peptide, which means each pep gets
  ## its own entropy
  library(entropy)
  # keep only the complete cases
  pep_lib=pep_lib[pep_lib != ""]
  ## initiates the empty amino acid data frame for record
  freqs=matrix(rep(0,26),1)
  colnames(freqs)=LETTERS
  freqs=as.data.frame(freqs)
  drops <- c("B","J","O","U","X","Z")
  freqs_init=freqs[ , !(names(freqs) %in% drops)]
  freqs=freqs_init
  result_length=length(pep_lib)
  #entropy_result to store result
  entropy_result=matrix(rep(0,result_length),,1)
  test=0
  #calculate entropy for each pep
  for (pep in 1:result_length){
    freqs=freqs_init
    aas=unlist(strsplit(pep_lib[pep],""))
    #get the amino acids and add each one up
    for (aa in aas){
      freqs[1,aa]=freqs[1,aa]+1
    }
    #calculate and append current entropy to entr
    entropy_result[pep,1]=entropy(as.vector(t(freqs)))
    test=test+1
  }
  return (entropy_result)
}


entropy_per_lib=function(pep_lib){
  #library is the data matrix of peps, with each peptide one each row
  ## this function calculates entropy by the library, which means the amino acids
  ## of the whole lib is added together and outputs one entropy
  library(entropy)
  pep_lib=pep_lib[pep_lib != ""]
  ## initiates the empty amino acid data frame for record
  freqs=matrix(rep(0,26),1)
  colnames(freqs)=LETTERS
  freqs=as.data.frame(freqs)
  drops <- c("B","J","O","U","X","Z")
  freqs_init=freqs[ , !(names(freqs) %in% drops)]
  freqs=freqs_init
  result_length=length(pep_lib)
  #calculate entropy for each pep
  for (pep in 1:result_length){
    aas=unlist(strsplit(pep_lib[pep],""))
    #get the amino acids and add each one up
    for (aa in aas){
      freqs[1,aa]=freqs[1,aa]+1
    }
  }
  return (entropy(as.vector(t(freqs))))
}


## prepare the input
## input should have been sorted by # of rows, longest library on the left column
## shortest lib on the right column
input=read.table("G:\\OneDrive\\experiments\\entropy\\input.txt",header=FALSE,fill=TRUE)
out_per_pep=lapply(1:ncol(input), function(x) entropy_per_pep(input[x]))
out_per_pep_table=sapply(out_per_pep, '[', seq(max(lengths(out_per_pep))))
out_per_lib=apply(input,2,entropy_per_lib)

write.table(out_per_lib,"G:\\OneDrive\\experiments\\entropy\\out_lib.txt",sep='\t',quote=FALSE)

## optional boxplot for each library
library(ggplot2)
ggplot(data = melt(out_per_pep_table), aes(x=as.factor(Var2), y=value))+ geom_boxplot(aes(fill=as.factor(Var2)))