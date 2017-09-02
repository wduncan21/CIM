import java.io.File;
import java.nio.file.Paths;
import java.util.*;

public class CalculationHandler {

	UsefulTools useful_tools = new UsefulTools();
	FolderAndFileHandler ffh = new FolderAndFileHandler();
	OutputHandler output_handler = new OutputHandler();
	
	
	//these values have been referred to as summary numbers
	ArrayList entropies = new ArrayList();
	ArrayList normalized_shannon_entropies = new ArrayList();
	ArrayList entropies_normalized = new ArrayList();
	ArrayList normalized_shannon_entropies_normalized = new ArrayList();
	ArrayList max = new ArrayList();
	ArrayList max_normalized = new ArrayList();
	ArrayList min = new ArrayList();
	ArrayList min_normalized = new ArrayList();
	ArrayList cv = new ArrayList();
	ArrayList cv_normalized = new ArrayList();
	ArrayList stdev = new ArrayList();
	ArrayList stdev_normalized = new ArrayList();
	ArrayList mean = new ArrayList();
	ArrayList mean_normalized = new ArrayList();
	ArrayList median = new ArrayList();
	ArrayList median_normalized = new ArrayList();
	ArrayList mode = new ArrayList();
	ArrayList mode_avg = new ArrayList();
	ArrayList mode_normalized = new ArrayList();
	ArrayList mode_normalized_avg = new ArrayList();
	ArrayList kurtosis = new ArrayList();
	ArrayList skew = new ArrayList();
	ArrayList fifth_percentile = new ArrayList();
	ArrayList ninety_fifth_percentile = new ArrayList();
	ArrayList dynamic_range = new ArrayList(); //95th percentile divided by 5th percentile
	ArrayList kurtosis_normalized = new ArrayList();
	ArrayList skew_normalized = new ArrayList();
	ArrayList fifth_percentile_normalized = new ArrayList();
	ArrayList ninety_fifth_percentile_normalized = new ArrayList();
	ArrayList dynamic_range_normalized = new ArrayList(); 
	
	
	//this method calculates the entropy from an arraylist (integers expected)
	public Histogram_Distribution calculateEntropy(ArrayList list, double size_of_bins, int min_possible_number, int max_possible_number, String output_directory, String output_filename)
	{
		Histogram_Distribution hd = new Histogram_Distribution();
		hd.createDistribution(size_of_bins, list, min_possible_number, max_possible_number);		
		output_handler.outputEntropy(hd, output_directory, output_filename);
		entropies.add(hd.getEntropy());
		normalized_shannon_entropies.add(hd.getNormalizedShannonEntropy());
		return hd;
	}
	
	public Histogram_Distribution calculateEntropyforNormalizedData(ArrayList list, double size_of_bins, String output_directory, String output_filename)
	{
		int min = Double.valueOf(useful_tools.getMin(list)).intValue();
		int max = Double.valueOf(useful_tools.getMax(list)).intValue();
		Histogram_Distribution hd = new Histogram_Distribution();
		hd.createDistribution(size_of_bins, list, min, max);		
		output_handler.outputEntropy(hd, output_directory, output_filename);
		entropies_normalized.add(hd.getEntropy());
		normalized_shannon_entropies_normalized.add(hd.getNormalizedShannonEntropy());
		return hd;
	}
	
	//this method calculates the entropy from a filepath and expects a list of numbers (integers) in the file
	public Histogram_Distribution calculateEntropy(String filepath, double size_of_bins, int min_possible_number, int max_possible_number, String output_directory, String output_filename)
	{
		ArrayList list = useful_tools.storeTextFiletoArrayList(filepath);
		return calculateEntropy(list, size_of_bins, min_possible_number, max_possible_number, output_directory, output_filename);
	}
	
	public Histogram_Distribution calculateEntropyforNormalizedData(String filepath, double size_of_bins, String output_directory, String output_filename)
	{
		ArrayList list = useful_tools.storeTextFiletoArrayList(filepath);
		return calculateEntropyforNormalizedData(list, size_of_bins, output_directory, output_filename);

	}
	
	public void calculateEntropyOfAllFilesInDirectory(String directory, double size_of_bins, int min_possible_number, int max_possible_number)
	{
		
		String output_directory = Paths.get(directory+File.separator+"entropy_results").toString();
		ArrayList samples_to_process = ffh.listFilesAsArrayList(directory);
		String return_string = "";
		ArrayList all_entropies = new ArrayList();
		for(int i=0; i<samples_to_process.size(); i++)
		{
			String filename = samples_to_process.get(i).toString();
			try
			{
				Histogram_Distribution hd = calculateEntropy(Paths.get(directory+""+File.separator+""+filename).toString(), size_of_bins, min_possible_number, max_possible_number, output_directory, filename);
				all_entropies.add(hd.getEntropy());
				//entropies.add(hd.getEntropy());
			}
			catch(Exception e)
			{
				
			}
		}
		output_handler.outputTwoListsOfValues("Samples", "Entropy", samples_to_process, all_entropies, output_directory, "all_entropy_values.txt");
	}
	
	public void calculateEntropyOfAllFilesInDirectoryForNormalizedData(String directory, double size_of_bins)
	{
		String output_directory = Paths.get(directory+""+File.separator+"entropy_results").toString();
		ArrayList samples_to_process = ffh.listFilesAsArrayList(directory);
		String return_string = "";
		ArrayList all_entropies = new ArrayList();
		for(int i=0; i<samples_to_process.size(); i++)
		{
			String filename = samples_to_process.get(i).toString();
			Histogram_Distribution hd = calculateEntropyforNormalizedData(Paths.get(directory+""+File.separator+""+filename).toString(), size_of_bins, output_directory, filename);
			all_entropies.add(hd.getEntropy());
		}
		output_handler.outputTwoListsOfValues("Samples", "Entropy", samples_to_process, all_entropies, output_directory, "all_entropy_values.txt");
	}
	
	//this method calculates the cv from a list
	//the CV (coefficient of variation) is the ratio of the standard deviation to the mean
	public double calculateCV(ArrayList list, String output_directory, String output_filename)
	{
		
		//make sure everything in the medians list is a double
		ArrayList list2 = new ArrayList();
		for(int i=0; i<list.size(); i++)
		{
			try
			{
				list2.add(Double.valueOf(list.get(i).toString()).doubleValue());
			}
			catch(Exception e)
			{
				
			}
		}
		double stdev = useful_tools.getStdDev(list);
		double mean = useful_tools.getMean(list);
		double cv = stdev/mean;
		output_handler.outputCV(cv, output_directory, output_filename);
		return cv;
	}
	
	//this method calculates the cv from a filepath and expects a list of numbers in the file
	public double calculateCV(String filepath, String output_directory, String output_filename)
	{
		ArrayList list = useful_tools.storeTextFiletoArrayList(filepath);
		return calculateCV(list, output_directory, output_filename);
	}
	
	public void calculateCVOfAllFilesInDirectory(String directory)
	{
		String output_directory = Paths.get(directory+""+File.separator+"cv_results").toString();
		ArrayList samples_to_process = ffh.listFilesAsArrayList(directory);
		String return_string = "";
		ArrayList all_cvs = new ArrayList();
		for(int i=0; i<samples_to_process.size(); i++)
		{
			String filename = samples_to_process.get(i).toString();
			double cv = calculateCV(Paths.get(directory+""+File.separator+""+filename).toString(), output_directory, filename);
			all_cvs.add(cv);
		}
		output_handler.outputTwoListsOfValues("Samples", "CV", samples_to_process, all_cvs, output_directory, "all_cv_values.txt");
	}
	
	public void calculateSummaryNumbersExcludingEntropyOfAllFilesInDirectory(String directory, boolean for_normalized_data)
	{
		String output_directory = Paths.get(directory+""+File.separator+"summary_results").toString();
		ArrayList samples_to_process = ffh.listFilesAsArrayList(directory);
		String return_string = "";
		ArrayList all_cvs = new ArrayList();
		for(int i=0; i<samples_to_process.size(); i++)
		{
			String filename = samples_to_process.get(i).toString();
			ArrayList list = useful_tools.storeTextFiletoArrayList(Paths.get(directory+""+File.separator+""+filename).toString());
			if(!for_normalized_data)
			{
				try
				{
					calculateSummaryNumbersExcludingEntropy(list);
				}
				catch(Exception e)
				{
					
				}
			}
			else
			{
				try
				{
					calculateSummaryNumbersExcludingEntropyNormalized(list);
				}
				catch(Exception e)
				{
					
				}
			}
			//double cv = calculateCV(directory+""+File.separator+""+filename, output_directory, filename);
			//all_cvs.add(cv);
		}
		

	}
	
	
	//this method calculates the summary numbers from a list (excluding entropy)
	//these summary numbers are added to the arraylists stored by this class
		public void calculateSummaryNumbersExcludingEntropy(ArrayList list)
		{
			
			//make sure everything in the medians list is a double
			ArrayList list2 = new ArrayList();
			for(int i=0; i<list.size(); i++)
			{
				try
				{
					list2.add(Double.valueOf(list.get(i).toString()).doubleValue());
				}
				catch(Exception e)
				{
					
				}
			}
			double stdev = useful_tools.getStdDev(list);
			double mean = useful_tools.getMean(list);
			double cv = stdev/mean;
			double median =useful_tools.Median(list);
			ArrayList mode = useful_tools.mode(list);
			double min = useful_tools.getMin(list);
			double max = useful_tools.getMax(list);
			double kurtosis = useful_tools.kurtosis(list, mean, stdev);
			double skew = useful_tools.Skew(list, mean, stdev);
			double ninety_fifth_percentile = useful_tools.percentile(list, 0.95);
			double fifth_percentile = useful_tools.percentile(list, 0.05);
			double dynamic_range = ninety_fifth_percentile/fifth_percentile;
			
			this.stdev.add(stdev);
			this.mean.add(mean);
			this.cv.add(cv);
			this.median.add(median);
			this.mode.add(mode);
			this.mode_avg.add(useful_tools.getMean(mode));
			this.min.add(min);
			this.max.add(max);
			this.kurtosis.add(kurtosis);
			this.skew.add(skew);
			this.ninety_fifth_percentile.add(ninety_fifth_percentile);
			this.fifth_percentile.add(fifth_percentile);
			this.dynamic_range.add(dynamic_range);
			
			
			
		}
	
		//this method calculates the summary numbers from a list (excluding entropy and assuming that the data is normalized)
		//these summary numbers are added to the arraylists stored by this class
			public void calculateSummaryNumbersExcludingEntropyNormalized(ArrayList list)
			{
				
				//make sure everything in the medians list is a double
				ArrayList list2 = new ArrayList();
				for(int i=0; i<list.size(); i++)
				{
					try
					{
						list2.add(Double.valueOf(list.get(i).toString()).doubleValue());
					}
					catch(Exception e)
					{
						
					}
				}
				double stdev = useful_tools.getStdDev(list);
				double mean = useful_tools.getMean(list);
				double cv = stdev/mean;
				double median =useful_tools.Median(list);
				ArrayList mode = useful_tools.mode(list);
				double min = useful_tools.getMin(list);
				double max = useful_tools.getMax(list);
				double kurtosis = useful_tools.kurtosis(list, mean, stdev);
				double skew = useful_tools.Skew(list, mean, stdev);
				double ninety_fifth_percentile = useful_tools.percentile(list, 0.95);
				double fifth_percentile = useful_tools.percentile(list, 0.05);
				double dynamic_range = ninety_fifth_percentile/fifth_percentile;
				
				this.stdev_normalized.add(stdev);
				this.mean_normalized.add(mean);
				this.cv_normalized.add(cv);
				this.median_normalized.add(median);
				this.mode_normalized.add(mode);
				this.mode_normalized_avg.add(useful_tools.getMean(mode));
				this.min_normalized.add(min);
				this.max_normalized.add(max);
				this.kurtosis_normalized.add(kurtosis);
				this.skew_normalized.add(skew);
				this.ninety_fifth_percentile_normalized.add(ninety_fifth_percentile);
				this.fifth_percentile_normalized.add(fifth_percentile);
				this.dynamic_range_normalized.add(dynamic_range);
			}
	
	

		
	
	
	public void calculateAllValuesExcludingEntropyInDirectory(String directory)
	{
		String output_directory = Paths.get(directory+""+File.separator+"summary_number_results").toString();
		ArrayList samples_to_process = ffh.listFilesAsArrayList(directory);
		String return_string = "";
		
		for(int i=0; i<samples_to_process.size(); i++)
		{
			String filename = samples_to_process.get(i).toString();
			double cv = calculateCV(Paths.get(directory+""+File.separator+""+filename).toString(), output_directory, filename);
			
		}
		

	}
	
	
	public ArrayList getEntropies()
	{
		return entropies;
	}
	public ArrayList getNormalizedShannonEntropies()
	{
		return normalized_shannon_entropies;
	}
	public ArrayList getEntropiesNormalized()
	{
		return entropies_normalized;
	}
	public ArrayList getNormalizedShannonEntropiesNormalized()
	{
		return normalized_shannon_entropies_normalized;
	}
	public ArrayList getCV()
	{
		return cv;
	}
	public ArrayList getCVNormalized()
	{
		return cv_normalized;
	}
	public ArrayList getStdev()
	{
		return stdev;
	}
	public ArrayList getStdevNormalized()
	{
		return stdev_normalized;
	}
	public ArrayList getMean()
	{
		return mean;
	}
	public ArrayList getMeanNormalized()
	{
		return mean_normalized;
	}
	public ArrayList getMedian()
	{
		return median;
	}
	public ArrayList getMedianNormalized()
	{
		return median_normalized;
	}
	public ArrayList getMode()
	{
		return mode;
	}
	public ArrayList getModeNormalized()
	{
		return mode_normalized;
	}
	public ArrayList getMin()
	{
		return min;
	}
	public ArrayList getMinNormalized()
	{
		return min_normalized;
	}
	public ArrayList getMax()
	{
		return max;
	}
	public ArrayList getMaxNormalized()
	{
		return max_normalized;
	}
	public ArrayList getModeAvg()
	{
		return mode_avg;
	}
	public ArrayList getModeNormalizedAvg()
	{
		return mode_normalized_avg;
	}
	
	public ArrayList getKurtosis()
	{
		return kurtosis;
	}
	public ArrayList getKurtosisNormalized()
	{
		return kurtosis_normalized;
	}
	public ArrayList getSkew()
	{
		return skew;
	}
	public ArrayList getSkewNormalized()
	{
		return skew_normalized;
	}
	public ArrayList getNinetyFifthPercentile()
	{
		return ninety_fifth_percentile;
	}
	public ArrayList getNinetyFifthPercentileNormalized()
	{
		return ninety_fifth_percentile_normalized;
	}
	public ArrayList getFifthPercentile()
	{
		return fifth_percentile;
	}
	public ArrayList getFifthPercentileNormalized()
	{
		return fifth_percentile_normalized;
	}
	public ArrayList getDynamicRange()
	{
		return dynamic_range;
	}
	public ArrayList getDynamicRangeNormalized()
	{
		return dynamic_range_normalized;
	}
}
