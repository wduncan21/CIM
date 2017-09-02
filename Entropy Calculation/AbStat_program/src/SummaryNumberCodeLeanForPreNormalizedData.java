import java.util.ArrayList;
import java.util.Arrays;


public class SummaryNumberCodeLeanForPreNormalizedData {

	UsefulTools useful_tools = new UsefulTools();
	int[] numbers;
	int[] values; //this will be a copied version of the numbers for sorting later on (for the median)
	double[] dnumbers;
	int size = 0; //number of elements in dataset
	int sum = 0; //sum of elements in dataset
	double entropy = 0;
	double n_entropy =0;
	double normalized_entropy = 0;
	double cv = 0;
	double n_cv = 0;
	double stdev = 0;
	double mean = 0;
	double median = 0;
	int min = 0;
	int max = 0;
	double dmin = 0;
	double dmax = 0;
	double kurtosis = 0;
	double skew = 0;
	double ninety_fifth_percentile = 0;
	double fifth_percentile = 0;
	double dynamic_range = 0;
	
	
	double nonn_entropy = 0; //nonn indicates that this values was calculated from non-normalized data
	double nonn_normalized_entropy = 0;
	double nonn_cv = 0;
	double nonn_stdev = 0;
	double nonn_mean = 0;
	double nonn_median = 0;
	double nonn_min = 0;
	double nonn_max = 0;
	double nonn_ninety_fifth_percentile = 0;
	double nonn_fifth_percentile = 0;
	double nonn_dynamic_range = 0;
	
	double n_mean = 0;
	double n_stdev =0;
	double n_min = 0;
	double n_max = 0;
	double n_ninety_fifth_percentile = 0;
	double n_fifth_percentile =0;
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SummaryNumberCodeLean sn = new SummaryNumberCodeLean();
		System.out.println(sn.getTime());
		sn.TestEntropyCalculation_100513d1153();
		System.out.println(sn.getTime());
	}

	public void TestEntropyCalculation_100513d1103()
	{
		int[] iarray = {3,5,10,12,5,5};
		System.out.println(calculateEntropy(iarray));
		//value was 1.24 which was correct
	}
	public void TestEntropyCalculation_100513d1153()
	{
		//I'll store a pre-normalized text file with 330,000 values into an double array
		

		
		//now median normalize the list
		double[] normalized_array ={3.0,5.0};
		//now get a few more numbers
		n_mean = getMean(normalized_array);
		getMinAndMax(normalized_array);
		n_min = dmin;
		n_max = dmax;
		n_stdev = calculateSTDEV(normalized_array, n_mean);
		n_ninety_fifth_percentile = useful_tools.percentile(normalized_array, 0.95);
		n_fifth_percentile = useful_tools.percentile(normalized_array, 0.05);
		kurtosis = useful_tools.kurtosis(normalized_array, mean, stdev);
		skew = useful_tools.Skew(normalized_array, mean, stdev);
		dynamic_range = n_ninety_fifth_percentile/n_fifth_percentile;
		int[] iarray=multiply_by_factor_and_convert_to_int(normalized_array,10000);
		
		calculateEntropy(iarray);
		
		
		outputValues();
	}
	
	
	public void getSummaryNumbers(double[] normalized_array)
	{
		size = normalized_array.length;
		n_mean = getMean(normalized_array);
		getMinAndMax(normalized_array);
		n_min = dmin;
		n_max = dmax;
		n_stdev = calculateSTDEV(normalized_array, n_mean);
		n_cv = n_stdev/n_mean;
		n_ninety_fifth_percentile = useful_tools.percentile(normalized_array, 0.95);
		n_fifth_percentile = useful_tools.percentile(normalized_array, 0.05);
		
		kurtosis = useful_tools.kurtosis(normalized_array, n_mean, n_stdev);
		skew = useful_tools.Skew(normalized_array, n_mean, n_stdev);
		dynamic_range = n_ninety_fifth_percentile/n_fifth_percentile;
		//now convert the numbers into an integer array
		//do this in a manner so that the max is not greater than 100,000
		int factor = 10000;
		boolean appropriate_factor_found = false;
		while(!appropriate_factor_found)
		{
			if(dmax*factor>100000)
			{
				factor=factor/10;
			}
			else
			{
				appropriate_factor_found=true;
			}
		}
		int[] iarray=multiply_by_factor_and_convert_to_int(normalized_array,factor);
		
		calculateEntropy(iarray);
	}
	
	public void getSummaryNumbers(double[] normalized_array, int bin_size)
	{
		size = normalized_array.length;
		n_mean = getMean(normalized_array);
		getMinAndMax(normalized_array);
		n_min = dmin;
		n_max = dmax;
		n_stdev = calculateSTDEV(normalized_array, n_mean);
		n_cv = n_stdev/n_mean;
		n_ninety_fifth_percentile = useful_tools.percentile(normalized_array, 0.95);
		n_fifth_percentile = useful_tools.percentile(normalized_array, 0.05);
		
		kurtosis = useful_tools.kurtosis(normalized_array, n_mean, n_stdev);
		skew = useful_tools.Skew(normalized_array, n_mean, n_stdev);
		dynamic_range = n_ninety_fifth_percentile/n_fifth_percentile;
		//now convert the numbers into an integer array
		//do this in a manner so that the max is not greater than 100,000
		int factor = 10000;
		boolean appropriate_factor_found = false;
		while(!appropriate_factor_found)
		{
			if(dmax*factor>100000)
			{
				factor=factor/10;
			}
			else
			{
				appropriate_factor_found=true;
			}
		}
		int[] iarray=multiply_by_factor_and_convert_to_int(normalized_array,factor);
		
		calculateEntropy(iarray,bin_size);
	}
	
	public double calculateEntropy(int[] numbers)
	{
		this.numbers = numbers;
		
		
		
		
		size = numbers.length;
		values = new int[size];
		max = numbers[0]; //not necessary for entropy calculation
		min = numbers[0];
		
		//first get the min and max

		for(int i=0; i<size; i++)
		{
			
			sum+=numbers[i]; //for the calculation of the mean
			values[i] = numbers[i]; //copying the list to a new list that will be sorted later on
			if(numbers[i]<min) //not necessary for entropy calculation
			{
				min = numbers[i]; //not necessary for entropy calculation
			}
			if(numbers[i]>max) //not necessary for entropy calculation
			{
				max = numbers[i]; //not necessary for entropy calculation
			}
		}
		int[] counts = new int[max+1];
		for(int i=0; i<size; i++)
		{
			try
			{
			counts[numbers[i]]++;
			}
			catch(Exception e)
			{
				int k=0;
				
			}
		
			
		}
		double running_sum = 0;
		for(int i=0; i<=max; i++)
		{
			int value = counts[i];
			if(value!=0)
			{
				double ratio = ((double)value)/size;
				running_sum+=ratio*Math.log(ratio);
			}
		}
		entropy = -running_sum;
		normalized_entropy = entropy/Math.log(size);
		mean = ((double)sum)/size;
		return entropy;
	}
	

	public double calculateEntropy(int[] numbers, double bin_size)
	{
		this.numbers = numbers;
		
		
		
		
		size = numbers.length;
		values = new int[size];
		max = numbers[0]; //not necessary for entropy calculation
		min = numbers[0];
		
		//first get the min and max

		for(int i=0; i<size; i++)
		{
			
			sum+=numbers[i]; //for the calculation of the mean
			values[i] = numbers[i]; //copying the list to a new list that will be sorted later on
			if(numbers[i]<min) //not necessary for entropy calculation
			{
				min = numbers[i]; //not necessary for entropy calculation
			}
			if(numbers[i]>max) //not necessary for entropy calculation
			{
				max = numbers[i]; //not necessary for entropy calculation
			}
		}
		int[] counts = new int[Double.valueOf(Math.ceil((max+1)/bin_size)).intValue()];
		for(int i=0; i<size; i++)
		{
			try
			{
				int current_number = numbers[i];
				int current_bin = Double.valueOf(Math.ceil(current_number/bin_size)).intValue();
				counts[current_bin-1]++;
			}
			catch(Exception e)
			{
				int k=0;
				
			}
		
			
		}
		//System.out.println("Counts: " + counts.length+","+counts[0]+","+counts[1]+","+counts[2]+","+counts[3]+"\r\n");
		//^this was just for checking some things
		double running_sum = 0;
		for(int i=0; i<counts.length; i++)
		{
			int value = counts[i];
			if(value!=0)
			{
				double ratio = ((double)value)/size;
				running_sum+=ratio*Math.log(ratio);
			}
		}
		entropy = -running_sum;
		normalized_entropy = entropy/Math.log(size);
		mean = ((double)sum)/size;
		return entropy;
	}
	
	 public double calculateSTDEV(int[] numbers)
     {
        double sum = 0.0;
        for (int i = 0; i <size; i++) {
            sum += (numbers[i] - mean) * (numbers[i] - mean);
        }
        stdev = Math.sqrt(sum / (size - 1));
        return stdev;
     }
	 
	 public double calculateSTDEV(double[] numbers, double mean)
     {
        double sum = 0.0;
        for (int i = 0; i <size; i++) {
            sum += (numbers[i] - mean) * (numbers[i] - mean);
        }
        stdev = Math.sqrt(sum / (size - 1));
        return stdev;
     }
	 
	public String getTime()
	{
		return useful_tools.getTime();
	}
	
	
	public double calculateMedian(int[] numbers)
	{
		    Arrays.sort(values);
	
		   
		    if (size % 2 == 1)
		    {
		    	median = values[(size+1)/2-1];
		    }
			
		    else
		    {
				double lower = values[size/2-1];
				double upper = values[size/2];
			 
				median = (lower + upper) / 2.0;
			
		    }
		    return median;
}
	
	
	public double[] median_normalize(int[] array, double median)
	{
		double[] return_list = new double[array.length];
		for(int i=0; i<array.length; i++)
		{
			return_list[i]=(double)array[i]/median;
		}
		return return_list;
		
	}
	
	public int[] multiply_by_factor_and_convert_to_int(double[] darray, int factor)
	{
		int[] return_array = new int[darray.length];
		for(int i=0; i<darray.length; i++)
		{
			
			return_array[i] = Double.valueOf(darray[i]*factor).intValue();
		}
		return return_array;
	}
	
	public double getMean(double[] values)
    {
        double sum = 0.0;
        for(int i=0; i<values.length; i++)
        {
        	sum+=values[i];
        }
        return sum/(double)values.length;
    }
	
	public void getMinAndMax(double[] values)
	{
		dmin = values[0];
		dmax = values[0];
		for(int i=0; i<values.length; i++)
		{
			if(values[i]<dmin)
			{
				dmin = values[i];
			}
			if(values[i]>dmax)
			{
				dmax = values[i];
			}
		}
	}
	
	public void outputValues()
	{
		System.out.println("Entropy: " +nonn_entropy+"\r\n");
		System.out.println("Normalized Entropy: " +nonn_normalized_entropy+"\r\n");
		System.out.println("Mean: " + nonn_mean);
		System.out.println("STDEV: " + nonn_stdev);
		System.out.println("CV: " + nonn_cv);
		System.out.println("Median: " + nonn_median);
		System.out.println("Min: " + nonn_min);
		System.out.println("Max: " + nonn_max);
		System.out.println("Kurtosis: " + kurtosis);
		System.out.println("Skew: " + skew);
		System.out.println("Ninetey_fifth_percentile: " + nonn_ninety_fifth_percentile);
		System.out.println("Fifth_percentile: " + nonn_fifth_percentile);
		System.out.println("Dynamic_range: "+ dynamic_range);
		
		System.out.println("Entropy_Normalized_Data: " + entropy);
		System.out.println("Normalized_Entropy_Normalized_Data: " + normalized_entropy);
		System.out.println("Mean_normalized: " + n_mean);
		System.out.println("STDEV_normalized: " + n_stdev);
		System.out.println("Min_normalized: "+ n_min);
		System.out.println("Max_normalized: " + n_max);
		System.out.println("Ninetey_fifth_percentile_normalized: " +n_ninety_fifth_percentile);
		System.out.println("Fifth_percentile_normalized: " + n_fifth_percentile);
	}
	
	
	
	
	public double getEntropyNormalizedData()
	{
		return entropy;
	}
	public double getNormalizedEntropyNormalizedData()
	{
		return normalized_entropy;
	}
	
	public double getMaxNormalized()
	{
		return n_max;
	}
	
	public double getMinNormalized()
	{
		return n_min;
	}
	public double getCV()
	{
		return n_cv;
	}
	public double getSTDEVNormalized()
	{
		return n_stdev;
	}
	public double getMeanNormalized()
	{
		return n_mean;
	}
	public double getFifthPercentileNormalized()
	{
		return n_fifth_percentile;
	}
	public double getNinetyFifthPercentileNormalized()
	{
		return n_ninety_fifth_percentile;
	}
	public double getKurtosis()
	{
		return kurtosis;
	}
	public double getSkew()
	{
		return skew;
	}
	public double getDynamicRange()
	{
		return dynamic_range;
	}
}
