#!/usr/bin/python

"""
To run from command line:
        CalcEnrichments.py librarySeqs.txt input.txt 5 7 >outend46.txt
The last two numbers indicate the range of lengths to consider.
for example, 3 7 considers all k-mers of lengths 3 to 6
"""

from collections import Counter
from scipy.stats import hypergeom,binom

def window(string,length):
    if isinstance(string,list): # If its a list of things instead of one thing
        out = map(lambda x: window(x,length), string) # window all of them and put them together
        return denest(out)
    out = []
    for i in range(len(string) - length + 1):
        out = out + [string[i:i+length]]
    return out

defaultwin = lambda peps: [k for i in peps for j in range(3,7) for k in window(i,j) ]

def choose(n, k):
    """
    A fast way to calculate binomial coefficients by Andrew Dalke (contrib).
    """
    if 0 <= k <= n:
        ntok = 1
        ktok = 1
        for t in xrange(1, min(k, n - k) + 1):
            ntok *= n
            ktok *= t
            n -= 1
        return ntok // ktok
    else:
        return 0

def FDR(x):
    """
    Assumes a list or numpy array x which contains p-values for multiple tests
    Copied from p.adjust function from R  
    """
    o = [i[0] for i in sorted(enumerate(x), key=lambda v:v[1],reverse=True)]
    ro = [i[0] for i in sorted(enumerate(o), key=lambda v:v[1])]
    q = sum([1.0/i for i in xrange(1,len(x)+1)])
    l = [q*len(x)/i*x[j] for i,j in zip(reversed(xrange(1,len(x)+1)),o)]
    l = [l[k] if l[k] < 1.0 else 1.0 for k in ro]
    return l

class PeptideLibrary(object):


def __init__(self,peps,windowfunc=defaultwin):


self.peps = peps


self.windowfunc = windowfunc


subseqs = windowfunc(peps)


self.N_pop = len(subseqs)


self.pepcounts = Counter(subseqs)


def p_hypergeom(self,N_pop,n_chosen,K_pop,k_success):


return hypergeom.cdf(k_success,N_pop,K_pop,n_chosen)


def test_seq(self,seq,k_success,n_chosen):


K_pop = self.pepcounts.get(seq,0)


return self.p_hypergeom(self.N_pop,n_chosen,K_pop,k_success)


def calc_enrichments(self,subseqs,n_chosen):


return [(i,self.test_seq(i,subseqs[i],n_chosen)) for i in subseqs]



if __name__ == "__main__":

import sys,csv

def read(arg):


with open(arg) as f:



return [i for i in f.read().split() if len(i) > 0]

frm = int(sys.argv[3]) # first length range

to = int(sys.argv[4]) # second length range

winfnc = lambda peps: [k for i in peps for j in range(frm,to) for k in window(i,j) ]

library = PeptideLibrary(read(sys.argv[1]),winfnc)

chosen = read(sys.argv[2])

subseqs = library.windowfunc(chosen)

n_chosen = len(subseqs)

subseqs = Counter(subseqs)

res = [(i, subseqs[i], library.pepcounts[i], 1-binom.cdf(subseqs[i],n_chosen,library.pepcounts[i]*1.0/library.N_pop), subseqs[i]*1.0/library.pepcounts[i]) for i in subseqs]



res_t = zip(*res)

pvals = res_t[3]

pvals_adj = FDR(pvals)

res_t += [pvals_adj]

res = zip(*res_t)



for i,j,k,l,m,n in sorted(res,key=lambda x:x[3]):


#if k > 1:


print "{0}\t{1}\t{2}\t{3}\t{4}\t{5}".format(i,j,k,l,m,n)
