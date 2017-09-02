import java.util.*;
import java.math.*;
/*
 * The purpose of the class is to contain information about a distribution of numbers that could be used in a histogram.
 * 
 * Histogram_Distribution Object
        original raw list of numbers
        List of bins
        total number of different bins with a different number of elements
        bin size
        create distribution(bin size, list of numbers for distribution)
            set the bin size
            create all of the bins (a bunch of ArrayLists in Java)
            go through all of the numbers and assign each one to the appropriate bin
            determine the number of bins with different numbers of elements and return this distribution
 */

public class Histogram_Distribution {

	private UsefulTools useful_tools = new UsefulTools();
	
	private ArrayList raw_numbers = new ArrayList();
	private ArrayList temporary_holder_for_raw_numbers = new ArrayList();
	private ArrayList bins = new ArrayList();
	private ArrayList frequencies_in_bins = new ArrayList();
	private ArrayList frequencies_in_bins_without_duplicates = new ArrayList();
	private ArrayList list_of_frequencies_without_zeros = new ArrayList();
	private int sum_of_non_zero_frequencies = 0; //if this value remains at 0, then this indicates that the sum of nonzero frequencies was not determined 
	private int minimum_of_distribution = 0;
	private int maximum_of_distribution = 65536;
	private int number_of_bins = 0;
	private int number_of_bins_with_different_number_of_elements = 0;
	private boolean evenly_split = false;
	private double size_of_bins = 0;
	private double size_of_last_bin = 0;
	private double entropy_of_distribution = 0;
	private double normalized_shannon_entropy_of_distribution=0;
	private boolean get_details_about_distribution = true;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Histogram_Distribution hd = new Histogram_Distribution();
		hd.test_class1();
	}
	
	Histogram_Distribution()
	{
		
	}
	
	public void test_class1()
	{
		//the list of numbers here is the first 100 prime numbers from wikipedia
		Histogram_Distribution hd = new Histogram_Distribution();
		hd.createDistribution(57,useful_tools.stringWithCommasToArrayList("2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101,103,107,109,113,127,131,137,139,149,151,157,163,167,173,179,181,191,193,197,199,211,223,227,229,233,239,241,251,257,263,269,271,277,281,283,293,307,311,313,317,331,337,347,349,353,359,367,373,379,383,389,397,401,409,419,421,431,433,439,443,449,457,461,463,467,479,487,491,499,503,509,521,523,541"));
		//hd.printFrequenciesOfBins();
		System.out.println(hd);
	}
	
	
	public Histogram_Distribution createDistribution(double size_of_bins, ArrayList raw_numbers, int min_possible_number, int max_possible_number)
	{
		this.minimum_of_distribution = min_possible_number;
		this.maximum_of_distribution = max_possible_number;
		return createDistribution(size_of_bins, raw_numbers);
	}
	
	public Histogram_Distribution createDistribution(double size_of_bins, ArrayList raw_numbers)
	{
		this.raw_numbers = raw_numbers;
		this.size_of_bins= size_of_bins;
		double d_number_of_bins = (maximum_of_distribution-minimum_of_distribution)/size_of_bins;
		
		this.number_of_bins = Double.valueOf(d_number_of_bins).intValue();
		if(Double.valueOf((maximum_of_distribution-minimum_of_distribution)%number_of_bins).doubleValue()==0)
		{
			evenly_split = true;
		}
		else
		{
			evenly_split = false;
		}
		
		this.size_of_last_bin = maximum_of_distribution-minimum_of_distribution-size_of_bins*(number_of_bins);
		//copy all of the raw numbers into the temporary holder
		for(int i=0; i<raw_numbers.size(); i++)
		{
			temporary_holder_for_raw_numbers.add(raw_numbers.get(i));
		}
		//create and fill up all the bins
		for(int bin_count=-1; bin_count<number_of_bins; bin_count++)
		{
			
			ArrayList raw_numbers_for_bin = new ArrayList();
			double lower_bound = 0;
			double upper_bound = 0;
			if(bin_count<number_of_bins-1)
			{
				lower_bound = minimum_of_distribution+this.size_of_bins*bin_count;
				upper_bound = minimum_of_distribution+this.size_of_bins*(bin_count+1);
			}
			else
			{
				lower_bound = minimum_of_distribution+this.size_of_bins*bin_count;
				upper_bound = minimum_of_distribution+this.size_of_bins*(bin_count+1)+this.size_of_last_bin;
				
			}
			//determine which numbers should go in this bin
			for(int i=0; i<temporary_holder_for_raw_numbers.size(); i++)
			{
				
				double current_number = Double.valueOf(temporary_holder_for_raw_numbers.get(i).toString());
				if(current_number>lower_bound && current_number<=upper_bound)
				{
					raw_numbers_for_bin.add(current_number);
					temporary_holder_for_raw_numbers.remove(i);
					i--;
				}
			}
			bins.add(new Bin(raw_numbers_for_bin, lower_bound, upper_bound));
			
		}
		
		
		determineNumberOfBinsWithDifferentNumberOfElements();
		determineFrequenciesWithoutZero();
		determineEntropyOfDistribution();

		
		return this;
		
	}
	public void determineFrequenciesWithoutZero()
	{
		for(int i=0; i<frequencies_in_bins.size(); i++)
		{
			int current_frequency = Integer.valueOf(frequencies_in_bins.get(i).toString());
			if(current_frequency!=0)
			{
				list_of_frequencies_without_zeros.add(current_frequency);
				this.sum_of_non_zero_frequencies+=current_frequency;
			}
		}
	}
	public void determineNumberOfBinsWithDifferentNumberOfElements()
	{
		this.number_of_bins_with_different_number_of_elements = 0;
		//get a list of all the bins
		for(int bin_count=0; bin_count<bins.size(); bin_count++)
		{
			frequencies_in_bins.add(((Bin)bins.get(bin_count)).getSize());
		}
		frequencies_in_bins_without_duplicates = useful_tools.removeDuplicates(frequencies_in_bins);
		this.number_of_bins_with_different_number_of_elements = frequencies_in_bins_without_duplicates.size();
	}
	
	public int getNumberOfBinsWithDifferentNumberOfElements()
	{
		return this.number_of_bins_with_different_number_of_elements;
	}
	
	public double getEntropy()
	{
		return entropy_of_distribution;
	}
	public double getNormalizedShannonEntropy()
	{
		return normalized_shannon_entropy_of_distribution;
	}
	
	
	
	/*
	 * The formula for Shannon's Entropy is -Sum (from i=0 to n) p(Xi)*log(Xi).
So for example, let's say the young person after immunizations has this distribution (extreme case but this is just an example):
1,000 peptides with 30,000-40,000 intensity
8,000 peptides with 40,000-50,000 intensity
1,000 peptides with 50,000-60,000 intensity
Then the entropy would be
-(1000/10000*log(1000/10000)+8000/10000*log(8000/10000)+1000/10000*log(1000/10000))=0.639
	 */
	public double determineEntropyOfDistribution()
	{
		double sum = 0;
		double total_number_of_elements=raw_numbers.size();
		if(total_number_of_elements==7)
		{
			int k=0;
			k++;
		}
		//-Sum (from i=0 to n) p(Xi)*log(Xi)
		for(int i=0; i<frequencies_in_bins.size(); i++)
		{
			double current_frequency = 0;
			current_frequency = Double.valueOf(frequencies_in_bins.get(i).toString());
			if(current_frequency!=0)
			{
				double ratio = current_frequency/total_number_of_elements;
				double ratio_and_log =ratio*Math.log(ratio);
				sum+=ratio_and_log;
			}
		}
		sum = 0-sum;
		this.entropy_of_distribution = sum;
		this.normalized_shannon_entropy_of_distribution = this.entropy_of_distribution/Math.log(total_number_of_elements);
		return this.entropy_of_distribution;
	}
	
	public String printContentsOfBins()
	{
		String return_string = "";
		for(int bin_count=0; bin_count<this.number_of_bins; bin_count++)
		{
			return_string+=((Bin)bins.get(bin_count)).toString() + "\n";
		}
		//System.out.println(return_string);
		return return_string;
	}
	
	public String printFrequenciesOfBins()
	{
		String return_string = "";
		for(int bin_count=0; bin_count<this.number_of_bins; bin_count++)
		{
			return_string+= bin_count + "\t" + String.valueOf(((Bin)bins.get(bin_count)).getSize()) + "\n";
		}
		//System.out.println(return_string);
		return return_string;
	}
	public String printDetailedInformation()
	{
		String return_string = "";
		
		return_string+="Entropy of Distribution: " + entropy_of_distribution +"\n";
		return_string+="Normalized Shannon Entropy of Distribution: " + normalized_shannon_entropy_of_distribution+"\n"; 
		return_string+="Number of Bins in Distribution: " + number_of_bins + "\n";
		return_string+="Size of Bin in Distribution: " + size_of_bins + "\n";
		return_string+="Size of Last Bin in Distribution: " + size_of_last_bin + "\n";
		return_string+="Number of Bins with Different Number of Elements: "+number_of_bins_with_different_number_of_elements+"\n";
		return_string+="Set Minimum of Distribution: " + minimum_of_distribution+"\n";
		return_string+="Set Maximum of Distribution: " + maximum_of_distribution+"\n";
		return_string+="All numbers in distribution: " + raw_numbers.toString()+"\n";
		return_string+="Total Number of Numbers in Distribution: " + String.valueOf(raw_numbers.size())+"\n";
		return_string+="Unique Frequencies found in bins: " + frequencies_in_bins_without_duplicates.toString()+"\n";
		return_string+="All Non Zero Frequencies: " + list_of_frequencies_without_zeros.toString()+"\n";
		return_string+="Sum of non zero frequencies: " + this.sum_of_non_zero_frequencies+"\n";
		return_string+="\n\n\n----------------\nFrequencies of bins\n";
		return_string+=printFrequenciesOfBins();
		return_string+="\n\n\n----------------\nContents of bins\n";
		return_string+=printContentsOfBins();
		return return_string;
	}
	
	public String toString()
	{
		
		return printDetailedInformation();
	}
	


	
}
