######################################################
######       Class Prediction        ######
######################################################

#Randomized cross validation
#SVM Linear kernel


#require(e1071)
#require(sva)

trainTestSplit = function(data,classes){
	classIndexes = list()
	# gather classes into a dictionary-like datastructure
	for(i in 1:length(classes)){
		currentClass = classes[i]
		currentClassList = classIndexes[[currentClass]]
		if(is.null(currentClassList))
			currentClassList = c()
		currentClassList = c(i,currentClassList)
		classIndexes[[currentClass]] = currentClassList
	}
	# select one from each class, only works for one for each class.
	testSetIndexes = sapply(classIndexes, function(i)sample(i,1))
	XTest = data[,testSetIndexes]
	XTrain = data[,-testSetIndexes]
	classesTest = classes[testSetIndexes]
	classesTrain = classes[-testSetIndexes]
	out = list()
	out$train_data = XTrain
	out$test_data = XTest
	out$train_classes = classesTrain
	out$test_classes = classesTest
	out$indicies = testSetIndexes
	out
}

multiTTest = function(data,classes,cutoff){
	# T test on each class against the others. selects feature indexes
	uClasses = unique(classes)
	featureIndexes = c()
	selected = rep(FALSE,nrow(data))
	for(i in uClasses){
		pVals = tryCatch({
			apply(data,1,function(row){
				classInd = classes == i
				t.test(row[classInd],row[-classInd])$p.value
			})
		},
		error = function(e){
			1			
		},
		warning = function(w){
			1
		}
		)
		p.adj=p.adjust(pVals,method = "fdr") # adjust P value.
    	selected = selected | p.adj <= cutoff # | is OR, generate T,F select index, combine all significant peptides for each class		
	}
	selected
}

crossValIteration = function(data, classes, p, shuffle=F){
	# Perform "leave one from each class out" cross validation
	# Does SVA adjustment
	# data: an n by m matrix where columns are samples and rows are features
	# classes: a length m vector of class labels
	# p: P value cutoff
	# p.adjust: adjust p value method. 
	#	FALSE: Do not do p adjustment
	#	"BH": Benjamini and Hochberg adjustment
	#	check documentation for p.adjust for more methods
	# shuffle: Shuffle the class labels randomly without replation
	#print(classes)
	if(shuffle)
		classes = sample(classes,replace=F)
	#print(classes)
	splittedData = trainTestSplit(data,classes)# list with train_data, test_data, train_classes, test_classes fields
	
	XTrain = splittedData$train_data	
	XTest = splittedData$test_data	
	YTrain = as.factor(splittedData$train_classes)
	YTest = as.factor(splittedData$test_classes)	
	
	#YTrain.model = model.matrix(~YTrain)
	#YTrain.model0 = model.matrix(~1,data=YTrain)
	#trainSv = sva(XTrain,YTrain.model,YTrain.model0)
	
	#fsvaobj = fsva(dbdat=XTrain, mod=YTrain.model, sv=trainSv, newdat=XTest)
	#XTrain_adjusted = fsvaobj$db
	#XTest_adjusted = fsvaobj$new
	print("selecting values")
	selected = multiTTest(XTrain,splittedData$train_classes,p)
	print("finished selecting values")
	XTrain.selected = XTrain[selected,]
	XTest.selected = XTest[selected,]
	#print(YTrain)
	print(ncol(XTrain))
	model = svm(t(XTrain.selected),YTrain,kernel="radial")# SVM function

	prediction = predict(model,t(XTest.selected)) # SVM prediction
	result = list()
	result$true = YTest
	result$prediction = prediction
	result$indicies = splittedData$indicies
	result$selected = selected
	result
}	

sensitivity = function(predicted,true,class){
	predicted = as.character(predicted) == class
	true = as.character(true) == class
	correct = apply(cbind(predicted,true),1,function(i)i[1]==i[2]&i[1])
	sum(correct)/sum(true)
}

specificity = function(predicted,true,class){
	predicted = !(as.character(predicted) == class)
	true = !(as.character(true) == class)
	correct = apply(cbind(predicted,true),1,function(i)i[1]==i[2]&i[1])
	sum(correct)/sum(true)
}

accuracy = function(predicted,true,class){
	predicted = as.character(predicted) == class
	true = as.character(true) == class
	correct = apply(cbind(predicted,true),1,function(i)i[1]==i[2])
	sum(correct)/length(predicted)
}

crossVal = function(data, classes, p, shuffle=F, n=100){
	results = lapply(1:n, function(i)crossValIteration(data, classes, p, shuffle))
	#return(results)
	indicies = sapply(results,function(i)i$indicies)
	uClasses = unique(classes)
	sensMat = matrix(nrow=n,ncol=length(uClasses))
	specMat = matrix(nrow=n,ncol=length(uClasses))
	accMat = matrix(nrow=n,ncol=length(uClasses))
	colnames(sensMat) = uClasses
	colnames(specMat) = uClasses
	colnames(accMat) = uClasses
	for(i in 1:length(uClasses)){
		sensMat[,i] = sapply(results,function(k)sensitivity(k$prediction,k$true,uClasses[i]))
		specMat[,i] = sapply(results,function(k)specificity(k$prediction,k$true,uClasses[i]))
		accMat[,i] = sapply(results,function(k)accuracy(k$prediction,k$true,uClasses[i]))
	}
	indexMat = sapply(results,function(x)x$indicies)	
	selectedMat = sapply(results,function(x)x$selected)
	out = list()
	out$sensitivity = sensMat
	out$specificity = specMat
	out$accuracy = accMat
	out$indicies = indexMat
	out$selected = selectedMat
	out
}

run = function(data,labels){
	#Example for running the above functions
	#assumes data and labels are in working directory
	#and class labels are in column 8 of labels
	#data = read.csv("data.csv")
	#labels = read.csv("labels.csv")
	#data = data[,2:ncol(data)]
	
	#data = data[,16:ncol(data)] # remove QC
	stage = labels[,2]
	#stage = stage[16:length(stage)] # remove QC
	classes = as.character(stage) # make a char vector of classes
	
	data[is.na(data)] = 0 # remove NAs

	data = as.matrix(data)

	#return(crossValIteration(data,classes,0.05))
	res = crossVal(data,classes,0.05,n=2)
	write.csv(res$sensitivity,"sensitivity.csv")	
	write.csv(res$specificity,"specificity.csv")	
	write.csv(res$accuracy,"accuracy.csv")	
	write.csv(res$selected,"selected.csv")	

	res = crossVal(data,classes,0.05,n=2,shuffle=T)
	write.csv(res$sensitivity,"sensitivity_shuff.csv")	
	write.csv(res$specificity,"specificity_shuff.csv")	
	write.csv(res$accuracy,"accuracy_shuff.csv")	
	write.csv(res$selected,"selected_shuff.csv")
	res
}