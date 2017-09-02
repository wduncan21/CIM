cal_entropy_from_gpr=function(file,folder,skip_lines=32){
  impordata <- read.table(paste(folder, file,sep="\\"),skip=skip_lines,sep='\t',header = T)
  data=impordata$F532.Median ## change according to need
  temp_entropy=entropy(data)
  temp_entropy
}


cal_entropy_from_folder=function(folder,skip_lines=32){
  ##Define directory path##
  ##get gpr files within all subfolders
  filenames <- list.files(folder,recursive=T, pattern=paste('*.gpr',sep=""))
  library(entropy)
  library(plyr)
  #result=sapply(filenames, cal_entropy_from_gpr,folder=folder)
  result=laply(filenames, cal_entropy_from_gpr,folder=folder,.drop = F,.progress= progress_text(char = '+'))
  result=cbind(filenames,result)
  write.table(result,paste(folder,'Entropy_result.txt',sep="\\"),quote=F)
}

cal_entropy_from_folder("S:\\Administration\\Biostatistics\\for Lu\\QC Entropy raw files")


