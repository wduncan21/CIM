
## get detailed run time for sub-functions within a function
Rprof(tmp <- tempfile())
## your code to evaluate here,like
## output includes next day change percentage, up or down, consecutive moves
trainData=FeatureSelection(data,AnalyzeClass)
## output summarizes the next day up rate given consecutive moves,
## e.g. -3 means the up rate after three downs in a row
#pval <- apply(data, 1, FastTtest) 
Rprof()
summaryRprof(tmp)


## calculate the overall run time
ptm <- proc.time()
## your code to evaluate here,like
ttt=data.frame(apply(Pos,1,mean))
proc.time() - ptm
