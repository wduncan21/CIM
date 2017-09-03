# -*- coding: utf-8 -*-
"""
Created on Thu Jul 13 14:56:33 2017

@author: lwang138
"""
def Remove_reps(seqs):
    """
    removes peptides with same a.a. occur>2 times,eg.QQPFP:keep, QQPPP:delete
    seqs: list.list of input string
    return one variable
    no_rep_seqs: list, of output string
    """
    import collections
    no_rep_seqs=[]
    for seq in seqs:
        if max(collections.Counter(seq).values())<3:
            no_rep_seqs.append(seq)
    return no_rep_seqs


def Remove_not_on_array_peps(seqs,aa_to_delete):
    """ 
    seqs: list of string
    aa_to_delete: list of a.a.'s to delete,they should be absent on array
    rerturn one variable 
    On_array_peps: peptides that has only the a.a.s on arrays
    """
    On_array_peps=[]
    for pep in seqs:
        present=0
        for i in aa_to_delete:
            if i in pep:
                present=1
                break
        if present==0:
            On_array_peps.append(pep)
    return On_array_peps
    
def importing_data(file,num_sample):
    """
    file:the location of the file,eg. file='G:/abc/test.txt'
    return three variables
    data: comtains all the array data
    peps: contains all the peptides
    group: contains all the group information
    peps match to the rows of data, group match to the column of data
    """
    import numpy as np
    data=np.genfromtxt(file,skip_header=1,usecols=range(1,num_sample+1))
    peps=np.genfromtxt(file,skip_header=1,usecols=[0],dtype=str)
    group=np.genfromtxt(file,max_rows=1,usecols=range(1,num_sample+1),dtype=str)
    return data,peps,group

def find_IEDB_peps_from_array(array_peps,IEDB_peps):
    """
    array_peps:np.array or list of peptides on array
    IEDB_peps:np.array or list of peptides or motifs to find on array
    return one variables
    matched_peps:dictionary: key is the epitope, vak are peptides that are found to match IEDB epitope
    """
    matched_peps={}
    array_peps=list(array_peps)
    IEDB_peps=list(IEDB_peps)
    for i in IEDB_peps:
        for pep in array_peps:
            if i in pep:
                if i not in matched_peps.keys():
                    matched_peps[i]=[pep]
                else:
                    matched_peps[i].extend([pep])
    return matched_peps

def T_test(data,group,broad=True):
    """
    data: np.array of the data on array
    group: contains class information
    broad: compare normal versus non-normal if true, or every pair-wise comparison
        if false, false has not been implemented yet
    return p value of the data in original order
    result=[]
    
    """
    import scipy.stats
    import numpy as np
    result=[]
    if broad:
        infection=np.where(group!='Normal')
        normal=np.where(group=='Normal')
        for line in data:
            p_val=scipy.stats.ttest_ind(line[infection],line[normal])[1]
            result.append(p_val)
    return result
    

def select_significant_epitopes(array_peps,IEDB_peps,data,group,min_sig=2,cutoff=1/330000):
    """
    array_peps:np.array or list of peptides on array
    IEDB_peps:np.array or list of peptides or motifs to find on array
    data:np.array of the data on array
    group: contains class information
    cutoff=1/330000 by default
    return two variable
    significant_epitopes:dict, keys are epitopes, val[0] are the corresponding
        peptides, val[1] are the p_val for the corresponding peptides
    matched_peps: described in find_IEDB_peps_from_array
    """
    import numpy as np
    significant_epitopes={}
    matched_peps=find_IEDB_peps_from_array(array_peps,IEDB_peps)
    for i in matched_peps.keys():
        selected_data=data[np.in1d(array_peps, matched_peps[i])]
        p_val=T_test(selected_data,group)
        if len(p_val)==0:
            continue
        elif len(p_val)==1:
            if p_val[0]<cutoff:
                significant_epitopes[i]=[matched_peps[i],p_val]
        else:
            if sorted(p_val)[min_sig-1]<cutoff:
                significant_epitopes[i]=[matched_peps[i],p_val]
    return significant_epitopes,matched_peps
    
    
def IEDB_resampling(IEDB,array_peps,data,group,rate=100,sample=2000):
    """
    IEDB: list of string of epitopes to sample from
    array_peps, data, group are described above
    rate: how many times to do resampling
    sample: sample size
    returns one variable
    results: list stores all the ratios
    """
    results=[]
    import random
    Not_on_array=['T', 'C', 'I', 'M']
    for i in range(rate):
        samp=random.sample(IEDB,sample)
        No_rep=Remove_reps(samp)
        On_array_peps=Remove_not_on_array_peps(No_rep,Not_on_array)
        sig_epitopes,matched_peps=select_significant_epitopes(array_peps,On_array_peps,data,group,min_sig=2,cutoff=1/330000)
        ratio=len(sig_epitopes)/len(matched_peps)
        results.append(ratio)
    return results


def write_sig_peps(sig_epitopes,data,file,cutoff=1/330000):
    """
    given significant epitopes from function select_significant_epitopes,
    find only the significant peptides and write to file the values
    """
    import numpy as np
    temp=list(sig_epitopes.values())
    ### get only the peptides with significant p-values
    temp=[np.array(x[0])[np.where(np.array(x[1])<1/330000)].tolist() for x in temp]
    sig_peps=[item for sub in temp for item in sub]
    ## get the data for these peptides
    sig_data=np.array([])
    for x in sig_peps:
        if len(sig_data)==0:
            sig_data=data[np.in1d(peps, x)]
        else:
            sig_data=np.append(sig_data,data[np.in1d(peps, x)],axis=0)
     ##write the data to files
    np.savetxt(file, np.column_stack([sig_peps,sig_data]),delimiter=',',fmt='%s' )#'%1.7f'

def Epitope_enrich_plot(steps,IEDB,peps,On_array_peps,data,group,top_k=True,Not_on_array=['T', 'C', 'I', 'M']):
    """
    for the given step size, generate the sig_ratio.enrichment of the given
    range and plot it, top_k decides whether cumulative or interval calculation
    """
    res={} 
    old_i=0
    if top_k:    
        sig_epitopes_old=0
        matched_peps_old=0
        for i in steps:
            temp_IEDB=IEDB[old_i:i]
            No_rep=Remove_reps(temp_IEDB)
            On_array_peps=Remove_not_on_array_peps(No_rep,Not_on_array)
            sig_epitopes_large,matched_peps_large=select_significant_epitopes(peps,On_array_peps,data,group,min_sig=2,cutoff=1/330000)
            sig_epitopes_old=len(sig_epitopes_large)+sig_epitopes_old
            matched_peps_old=len(matched_peps_large)+matched_peps_old
            ratio=sig_epitopes_old/matched_peps_old
            res[i]=ratio
            old_i=i
    
    else:
        for i in steps:
            temp_IEDB=IEDB[old_i:i]
            No_rep=Remove_reps(temp_IEDB)
            On_array_peps=Remove_not_on_array_peps(No_rep,Not_on_array)
            sig_epitopes_large,matched_peps_large=select_significant_epitopes(peps,On_array_peps,data,group,min_sig=2,cutoff=1/330000)
            ratio=len(sig_epitopes_large)/len(matched_peps_large)
            res[i]=ratio
            old_i=i
            
    import matplotlib.pyplot as plt
    plt.xlabel('Top epitopes')
    plt.ylabel('enriched ratio')
    plt.plot(*zip(*sorted(res.items())))


f = open('G:/OneDrive/experiments/common disease signature/First Infectious Disease Dataset/finding epitopes/IEDB_5mers.txt', 'r')
test=f.readlines()
IEDB=[x[:-1] for x in test]
f.close()
## IEDB contains all peptides occur at least 30 times
top_IEDB=IEDB[:2000]
## f contains top 2000 peptides occur at least 30 times
No_rep=Remove_reps(top_IEDB)
## KALGL is the 100th top peptide, occured 80 times
short_no_rep=No_rep[:No_rep.index('KALGL')]

## delete epitopes T C I M since they are not on 330k array
Not_on_array=['T', 'C', 'I', 'M']
On_array_peps_short=Remove_not_on_array_peps(short_no_rep,Not_on_array)
On_array_peps=Remove_not_on_array_peps(No_rep,Not_on_array)

## now check on 330k array,import data first
file='G:/OneDrive/experiments/common disease signature/First Infectious Disease Dataset/330k llnl set.txt'
data,peps,group=importing_data(file,118)

## find the epitopes that are significant on array,shorter and longer version
sig_epitopes_short,matched_peps_short=select_significant_epitopes(peps,On_array_peps_short,data,group,min_sig=2,cutoff=1/330000)
sig_epitopes,matched_peps=select_significant_epitopes(peps,On_array_peps,data,group,min_sig=2,cutoff=1/330000)
"""
Once we identified the peptides that are enriched, Let's get the heatmap 
using JMP on the identified peptides, sig_epitopes_short first
"""

write_sig_peps(sig_epitopes_short,data,file='G:/OneDrive/experiments/common disease \
signature/First Infectious Disease Dataset/finding epitopes/IEDB_sig_data_short.txt')

write_sig_peps(sig_epitopes,data,file='G:/OneDrive/experiments/common disease \
signature/First Infectious Disease Dataset/finding epitopes/IEDB_sig_data.txt')


"""
above identified the IEDB top peptides and their significance on array. Now let's
do random sampling of IEDB peptides to see if the percentage of 
sig_epitopes/matched_peps chances
"""
sig_ratios=IEDB_resampling(IEDB,peps,data,group,rate=1000,sample=2000)

## get som descriptive statistics on the random sampling info
from scipy import stats
stats.describe(sig_ratios)


"""
Let's be generous and generate epitopes as many epitopes as possible and yet 
remain significance from background random generation, this means at least at
upper 2.5% percentile of the background distribution
"""
## calculate upper 2.5% percentile
import numpy as np
target=np.percentile(sig_ratios,97.5)

## using >20 times occruence in IEDB(6089) as upper limit and 2000 as lower limit
## generate all the ones in the middle, step size at 1000 for now
steps=list(range(1000,7000,1000))
Epitope_enrich_plot(steps,IEDB,peps,On_array_peps,data,group)

"""
target lies in between 2000 to 3000, reduce step size to 2000 and redo analysis
"""
steps=list(range(2000,3001,200))
Epitope_enrich_plot(steps,IEDB,peps,On_array_peps,data,group)


"""
2600 is decided as the final target, run more analysis
"""
target_IEDB=IEDB[:2600]
No_rep=Remove_reps(target_IEDB)
Not_on_array=['T', 'C', 'I', 'M']
On_array_peps=Remove_not_on_array_peps(No_rep,Not_on_array)
sig_epitopes_target,matched_peps_target=select_significant_epitopes(peps,On_array_peps,data,group,min_sig=2,cutoff=1/330000)
write_sig_peps(sig_epitopes_target,file='G:/OneDrive/experiments/common disease \
signature/First Infectious Disease Dataset/finding epitopes/IEDB_sig_data_target.txt')


## generate IEDB grand figure by using a lot more peptides
## generate all the ones in the middle, step size increase at Fibonacci speed
steps=[1000*2**x for x in range(10)]
Epitope_enrich_plot(steps,IEDB,peps,On_array_peps,data,group)

"""
instead of top k ones， calculate from k to k+2000 interval ones
"""
steps=[x*1000 for x in steps]
Epitope_enrich_plot(steps,IEDB,peps,On_array_peps,data,group,top_k=False)
