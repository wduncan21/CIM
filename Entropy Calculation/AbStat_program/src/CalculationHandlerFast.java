import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class CalculationHandlerFast {

	UsefulTools useful_tools = new UsefulTools();
	FolderAndFileHandler ffh = new FolderAndFileHandler();
	OutputHandler output_handler = new OutputHandler();
	DataPreparationClassFast dpc = new DataPreparationClassFast();
	

	
	//these values have been referred to as summary numbers
		ArrayList entropies = new ArrayList();
		ArrayList normalized_shannon_entropies = new ArrayList();
		ArrayList max = new ArrayList();
		ArrayList min = new ArrayList();
		ArrayList cv = new ArrayList();
		ArrayList stdev = new ArrayList();
		ArrayList mean = new ArrayList();
		ArrayList median = new ArrayList();
		ArrayList fifth_percentile = new ArrayList();
		ArrayList ninety_fifth_percentile = new ArrayList();
		
		
		ArrayList entropies_normalized = new ArrayList();
		ArrayList normalized_shannon_entropies_normalized = new ArrayList();
		ArrayList max_normalized = new ArrayList();
		ArrayList min_normalized = new ArrayList();
		ArrayList stdev_normalized = new ArrayList();
		ArrayList mean_normalized = new ArrayList();
		ArrayList fifth_percentile_normalized = new ArrayList();
		ArrayList ninety_fifth_percentile_normalized = new ArrayList();
		
		
		ArrayList kurtosis = new ArrayList();
		ArrayList skew = new ArrayList();
		ArrayList dynamic_range = new ArrayList(); //95th percentile divided by 5th percentile
		
		ArrayList sums = new ArrayList();
	
		ArrayList file_names = new ArrayList();
	
	

	
	//this method calculates the summary numbers from a list
	//these summary numbers are added to the arraylists stored by this class
		public void calculateSummaryNumbers(int[] list)
		{
			SummaryNumberCodeLean sncl = new SummaryNumberCodeLean();
			sncl.getSummaryNumbers(list);
			
		
			this.entropies.add(sncl.getEntropy());
			this.normalized_shannon_entropies.add(sncl.getNormalizedEntropy());
			this.max.add(sncl.getMax());
			this.min.add(sncl.getMin());
			this.cv.add(sncl.getCV());
			this.stdev.add(sncl.getSTDEV());
			this.mean.add(sncl.getMean());
			this.median.add(sncl.getMedian());
			this.fifth_percentile.add(sncl.fifth_percentile);
			this.ninety_fifth_percentile.add(sncl.ninety_fifth_percentile);
			
			this.entropies_normalized.add(sncl.getEntropyNormalizedData());
			this.normalized_shannon_entropies_normalized.add(sncl.getNormalizedEntropyNormalizedData());
			this.max_normalized.add(sncl.getMaxNormalized());
			this.min_normalized.add(sncl.getMinNormalized());
			this.stdev_normalized.add(sncl.getSTDEVNormalized());
			this.mean_normalized.add(sncl.getMeanNormalized());
			this.fifth_percentile_normalized.add(sncl.getFifthPercentileNormalized());
			this.ninety_fifth_percentile_normalized.add(sncl.getNinetyFifthPercentileNormalized());
			
			this.kurtosis.add(sncl.getKurtosis());
			this.skew.add(sncl.getSkew());
			this.dynamic_range.add(sncl.getDynamicRange());
			this.sums.add(sncl.getSum());
					
			
		}
		
		//this method calculates the summary numbers from a list
		//these summary numbers are added to the arraylists stored by this class
			public void calculateSummaryNumbers(int[] list, int bin_size)
			{
				SummaryNumberCodeLean sncl = new SummaryNumberCodeLean();
				sncl.getSummaryNumbers(list,bin_size);
				
			
				this.entropies.add(sncl.getEntropy());
				this.normalized_shannon_entropies.add(sncl.getNormalizedEntropy());
				this.max.add(sncl.getMax());
				this.min.add(sncl.getMin());
				this.cv.add(sncl.getCV());
				this.stdev.add(sncl.getSTDEV());
				this.mean.add(sncl.getMean());
				this.median.add(sncl.getMedian());
				this.fifth_percentile.add(sncl.fifth_percentile);
				this.ninety_fifth_percentile.add(sncl.ninety_fifth_percentile);
				
				this.entropies_normalized.add(sncl.getEntropyNormalizedData());
				this.normalized_shannon_entropies_normalized.add(sncl.getNormalizedEntropyNormalizedData());
				this.max_normalized.add(sncl.getMaxNormalized());
				this.min_normalized.add(sncl.getMinNormalized());
				this.stdev_normalized.add(sncl.getSTDEVNormalized());
				this.mean_normalized.add(sncl.getMeanNormalized());
				this.fifth_percentile_normalized.add(sncl.getFifthPercentileNormalized());
				this.ninety_fifth_percentile_normalized.add(sncl.getNinetyFifthPercentileNormalized());
				
				this.kurtosis.add(sncl.getKurtosis());
				this.skew.add(sncl.getSkew());
				this.dynamic_range.add(sncl.getDynamicRange());
				this.sums.add(sncl.getSum());
						
				
			}
		
		public void calculateSummaryNumbersOfAllFilesInDirectory(String directory, String type_of_median)
		{
			String output_directory = Paths.get(directory+""+File.separator+"summary_results").toString();
			ArrayList samples_to_process = ffh.listFilesAsArrayList(directory);
			for(int i=0; i<samples_to_process.size(); i++)
			{
				String filename = samples_to_process.get(i).toString();
				int[] data = dpc.extractIntDataFromGPRFile(directory, filename, type_of_median);
				
				try
				{
					calculateSummaryNumbers(data);
					file_names.add(filename);
				}
				catch(Exception e)
				{
					
				}
				
			}
			

		}
		
		
		public void calculateSummaryNumbersOfAllFilesInDirectory(String directory, String type_of_median, int bin_size)
		{
			String output_directory = Paths.get(directory+""+File.separator+"summary_results").toString();
			ArrayList samples_to_process = ffh.listFilesAsArrayList(directory);
			for(int i=0; i<samples_to_process.size(); i++)
			{
				String filename = samples_to_process.get(i).toString();
				int[] data = dpc.extractIntDataFromGPRFile(directory, filename, type_of_median);
				
				try
				{
					calculateSummaryNumbers(data,bin_size);
					file_names.add(filename);
				}
				catch(Exception e)
				{
					
				}
				
			}
			

		}
		
		
		public String outputFolderOfGPRFilesAsTable(String directory, String type_of_median)
		{
			String return_string = "";
			String output_directory = Paths.get(directory+""+File.separator+"summary_results").toString();
			ArrayList samples_to_process = ffh.listFilesAsArrayList(directory);
			ArrayList samples_to_process_refined = new ArrayList();
			//go through the list and remove anything that does not have a "gpr" extension
			for(int i=0; i<samples_to_process.size(); i++)
			{
				if(useful_tools.isMatchofRegEx(samples_to_process.get(i).toString(), ".+\\.gpr"))
				{
					samples_to_process_refined.add(samples_to_process.get(i));
				}
			}
			samples_to_process = samples_to_process_refined;
			ArrayList columns = new ArrayList();
			for(int i=0; i<samples_to_process.size(); i++)
			{
				String filename = samples_to_process.get(i).toString();
				return_string+=filename+"\t"; //generate the header for the return string
				int[] data = dpc.extractIntDataFromGPRFile(directory, filename, type_of_median);
				columns.add(data);
				
			}
			
			//now that we've collected all the data, put it into a nice string
			int number_of_rows = ((int[])columns.get(0)).length;
			int number_of_columns = columns.size();
			for(int row_count=0; row_count<number_of_rows; row_count++)
			{
				return_string += "\r\n";
				for(int column_count=0; column_count<number_of_columns; column_count++)
				{
					try
					{
						return_string+= ((int[])columns.get(column_count))[row_count]+"\t";
					}
					catch(Exception e)
					{
						
					}
				}
			}
			return return_string;
		}
	
		//this method calculates the summary numbers from a list (excluding entropy and assuming that the data is normalized)
		//these summary numbers are added to the arraylists stored by this class
			public void calculateSummaryNumbersNormalized(double[] list)
			{
				
				SummaryNumberCodeLeanForPreNormalizedData sncl_normalized = new SummaryNumberCodeLeanForPreNormalizedData();
				sncl_normalized.getSummaryNumbers(list);
				
				
				this.entropies.add("-");
				this.normalized_shannon_entropies.add("-");
				this.max.add("-");
				this.min.add("-");
				this.stdev.add("-");
				this.mean.add("-");
				this.median.add("-");
				this.fifth_percentile.add("-");
				this.ninety_fifth_percentile.add("-");
				
				
				
				this.entropies_normalized.add(sncl_normalized.getEntropyNormalizedData());
				this.normalized_shannon_entropies_normalized.add(sncl_normalized.getNormalizedEntropyNormalizedData());
				this.max_normalized.add(sncl_normalized.getMaxNormalized());
				this.min_normalized.add(sncl_normalized.getMinNormalized());
				this.cv.add(sncl_normalized.getCV());
				this.stdev_normalized.add(sncl_normalized.getSTDEVNormalized());
				this.mean_normalized.add(sncl_normalized.getMeanNormalized());
				this.fifth_percentile_normalized.add(sncl_normalized.getFifthPercentileNormalized());
				this.ninety_fifth_percentile_normalized.add(sncl_normalized.getNinetyFifthPercentileNormalized());
				
				
				this.kurtosis.add(sncl_normalized.getKurtosis());
				this.skew.add(sncl_normalized.getSkew());
				this.dynamic_range.add(sncl_normalized.getDynamicRange());
			}
	
			//this method calculates the summary numbers from a list (excluding entropy and assuming that the data is normalized)
			//these summary numbers are added to the arraylists stored by this class
				public void calculateSummaryNumbersNormalized(double[] list, int bin_size)
				{
					
					SummaryNumberCodeLeanForPreNormalizedData sncl_normalized = new SummaryNumberCodeLeanForPreNormalizedData();
					sncl_normalized.getSummaryNumbers(list, bin_size);
					
					
					this.entropies.add("-");
					this.normalized_shannon_entropies.add("-");
					this.max.add("-");
					this.min.add("-");
					this.stdev.add("-");
					this.mean.add("-");
					this.median.add("-");
					this.fifth_percentile.add("-");
					this.ninety_fifth_percentile.add("-");
					
					
					
					this.entropies_normalized.add(sncl_normalized.getEntropyNormalizedData());
					this.normalized_shannon_entropies_normalized.add(sncl_normalized.getNormalizedEntropyNormalizedData());
					this.max_normalized.add(sncl_normalized.getMaxNormalized());
					this.min_normalized.add(sncl_normalized.getMinNormalized());
					this.cv.add(sncl_normalized.getCV());
					this.stdev_normalized.add(sncl_normalized.getSTDEVNormalized());
					this.mean_normalized.add(sncl_normalized.getMeanNormalized());
					this.fifth_percentile_normalized.add(sncl_normalized.getFifthPercentileNormalized());
					this.ninety_fifth_percentile_normalized.add(sncl_normalized.getNinetyFifthPercentileNormalized());
					
					
					this.kurtosis.add(sncl_normalized.getKurtosis());
					this.skew.add(sncl_normalized.getSkew());
					this.dynamic_range.add(sncl_normalized.getDynamicRange());
				}
	
			public void find_summary_numbers_from_tabdelimitedtext(String directory, String filename, boolean normalized_data, int sample_name_row, int sample_name_column_start, int sample_name_column_end, int data_column_start, int data_column_end, int data_starting_row)
			{
				String full_file_path = Paths.get(directory+File.separator+filename).toString();
				String output_directory = Paths.get(directory+""+File.separator+"summary_results").toString();
				filename = Paths.get(directory+File.separator+filename).toString();
				ArrayList some_list = new ArrayList();
				
		        file_names = useful_tools.extractPartialRowWithOpenCSV(full_file_path, sample_name_row, sample_name_column_start, sample_name_column_end);
		        //go through and process all of the data columns
		        for(int i=data_column_start; i<=data_column_end; i++)
		        {
		        	
		        	ArrayList al_values = useful_tools.extractPartialColumnWithOpenCSV(full_file_path, i, data_starting_row);
		        	
		        	
		        	if(!normalized_data)
		        	{
		        		//convert this al_values list to an array of ints for fast processing
			        	int[] iarray = getIntValuesFromArrayList(al_values);
		        		calculateSummaryNumbers(iarray);
		        	}
		        	else
		        	{
		        		//convert this al_values list to an array of doubles for fast processing
		        		double[] darray = getDoubleValuesFromArrayList(al_values);
		        		
		        		/* for debugging
		        		if(i==data_column_start+1)
		        		{
		        			int k=0;
		        			k++;
		        		}
		        		*/
		        		calculateSummaryNumbersNormalized(darray);
		        	}
		        }
		        
			}
			
			
			public void find_summary_numbers_from_tabdelimitedtext(String directory, String filename, boolean normalized_data, int sample_name_row, int sample_name_column_start, int sample_name_column_end, int data_column_start, int data_column_end, int data_starting_row, int bin_size)
			{
				String full_file_path = Paths.get(directory+File.separator+filename).toString();
				String output_directory = Paths.get(directory+""+File.separator+"summary_results").toString();
				filename = Paths.get(directory+File.separator+filename).toString();
				ArrayList some_list = new ArrayList();
				
		        file_names = useful_tools.extractPartialRowWithOpenCSV(full_file_path, sample_name_row, sample_name_column_start, sample_name_column_end);
		        //go through and process all of the data columns
		        for(int i=data_column_start; i<=data_column_end; i++)
		        {
		        	
		        	ArrayList al_values = useful_tools.extractPartialColumnWithOpenCSV(full_file_path, i, data_starting_row);
		        	
		        	
		        	if(!normalized_data)
		        	{
		        		//convert this al_values list to an array of ints for fast processing
			        	int[] iarray = getIntValuesFromArrayList(al_values);
		        		calculateSummaryNumbers(iarray,bin_size);
		        	}
		        	else
		        	{
		        		//convert this al_values list to an array of doubles for fast processing
		        		double[] darray = getDoubleValuesFromArrayList(al_values);
		        		
		        		/* for debugging
		        		if(i==data_column_start+1)
		        		{
		        			int k=0;
		        			k++;
		        		}
		        		*/
		        		calculateSummaryNumbersNormalized(darray,bin_size);
		        	}
		        }
		        
			}
			
			
			public double[] getDoubleValuesFromArrayList(ArrayList al_values)
			{
				double[] darray = new double[al_values.size()];
				for(int j=0; j<al_values.size(); j++)
	        	{
	        		darray[j] = Double.valueOf(al_values.get(j).toString()).doubleValue();
	        	}
				return darray;
			}
			
			public int[] getIntValuesFromArrayList(ArrayList al_values)
			{
				int[] iarray = new int[al_values.size()];
				for(int j=0; j<al_values.size(); j++)
	        	{
	        		iarray[j] = Double.valueOf(al_values.get(j).toString()).intValue();
	        	}
				return iarray;
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
	
	public ArrayList getKurtosis()
	{
		return kurtosis;
	}

	public ArrayList getSkew()
	{
		return skew;
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
	public ArrayList getSum()
	{
		return sums;
	}
	public ArrayList getFileNames()
	{
		return file_names;
	}
	
}
