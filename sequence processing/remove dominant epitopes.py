# -*- coding: utf-8 -*-
"""
Created on Thu Mar  2 13:24:20 2017

@author: lwang138
"""

input=open('G:/OneDrive/experiments/common disease signature/\
First Infectious Disease Dataset/finding epitopes/5mer epitopes seqs.txt',\
'r')
target='ARLKR' ## epitope to delete
minimer=2 ## minimum lenth to delete, 3 means will delete ARL,RLK,LKR to ARLKR

epis=input.read()
input.close()

epitopes=epis.split("\n") ##one epitope each line
epitope_seqs=epitopes[:]
maxlength=len(target)

for i in range(maxlength-minimer+1): ## i is the start position of sub epitope
    for length in range(minimer,maxlength+1):## length is the length of the sub epitope
        if i+length>5: ## make sure the sub epitope is not out of boundary
            continue
        check_target=target[i:i+length] ## get the sub epitope
        epitope_seqs=[seqs for seqs in epitope_seqs if\
        check_target not in seqs] ## check if sub epitope is in the list,return
                                  ## the ones does not have the sub epitopes
        

output=open('G:/OneDrive/experiments/common disease signature/\
First Infectious Disease Dataset/finding epitopes/removed dominant epitopes.txt',\
'w')
output.write("\n".join(epitope_seqs))
output.close()