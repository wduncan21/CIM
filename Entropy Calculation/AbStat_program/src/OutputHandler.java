import java.util.*;

public class OutputHandler {

	UsefulTools useful_tools = new UsefulTools();
	
	public double outputEntropy(Histogram_Distribution hd, String directory, String filename)
	{
		useful_tools.createTextFile(directory, filename+"_details.txt", hd.printDetailedInformation());
		return hd.getEntropy();
	}
	
	public double outputCV(double cv, String directory, String filename)
	{
		useful_tools.createTextFile(directory, filename+"_cv_value.txt", Double.valueOf(cv).toString());
		return cv;
	}
	
	/*
	 * This method assumes that the number of column1 values is equal to the number of column2 values
	 */
	public void outputTwoListsOfValues(String column1_title, String column2_title, ArrayList column1_values, ArrayList column2_values, String directory, String filename)
	{
		String return_string = column1_title+"\t"+column2_title+"\r\n";
		for(int i=0; i<column1_values.size(); i++)
		{
			String column1_value = column1_values.get(i).toString();
			String column2_value = column2_values.get(i).toString();
			return_string += column1_value+"\t"+column2_value+"\r\n";
			
		}
		useful_tools.createTextFile(directory, filename, return_string);

	}
	
	/*
	 * This method assumes that there are an equal number of rows in each column
	 */
	public void outputTableOfValues(String[] column_titles, String directory, String filename, ArrayList... lists)
	{
		useful_tools.outputTableOfValues(column_titles, directory, filename, lists);
		

	}
	
	public void outputTableForCalculationHandler(String directory, CalculationHandlerFast ch, ArrayList names)
	{
		String[] column_titles = {"name", "entropy","normalized_shannon_entropy","max","min", "cv","stdev","mean","median","fifth_percentile","ninety_fifth_percentile","entropy_normalized_data","normalized_entropy_normalized_data","max_normalized","min_normalized","stdev_normalized","mean_normalized","fifth_percentile_normalized","ninety_fifth_percentile_normalized","kurtosis","skew","dynamic_range", "total_intensity_sum"};
		useful_tools.outputTableOfValues(column_titles, directory, "table_of_summary_numbers.txt", names, ch.getEntropies(),ch.getNormalizedShannonEntropies(),ch.getMax(),ch.getMin(),ch.getCV(),ch.getStdev(),ch.getMean(),ch.getMedian(),ch.getFifthPercentile(),ch.getNinetyFifthPercentile(),ch.getEntropiesNormalized(),ch.getNormalizedShannonEntropiesNormalized(),ch.getMaxNormalized(),ch.getMinNormalized(),ch.getStdevNormalized(),ch.getMeanNormalized(),ch.getFifthPercentileNormalized(),ch.getNinetyFifthPercentileNormalized(),ch.getKurtosis(),ch.getSkew(), ch.getDynamicRange(),ch.getSum());
	}
	
	
	public void outputTableForCalculationHandler(String directory, CalculationHandler ch, ArrayList names)
	{
		String[] column_titles = {"name", "entropy","normalized_shannon_entropy","entropy_normalized","normalized_shannon_entropy_normalized","cv","cv_normalized","stdev","stdev_normalized","mean","mean_normalized","median","median_normalized","mode","mode_avg","mode_normalized","mode_normalized_avg","min","min_normalized","max","max_normalized","kurtosis","kurtosis_normalized","skew","skew_normalized","95th_percentile","95th_percentile_normalized","5th_percentile","5th_percentile_normalized","dynamic_range","dynamic_range_normalized"};
		useful_tools.outputTableOfValues(column_titles, directory, "table_of_summary_numbers.txt", names, ch.getEntropies(),ch.getNormalizedShannonEntropies(), ch.getEntropiesNormalized(), ch.getNormalizedShannonEntropiesNormalized(),  ch.getCV(), ch.getCVNormalized(), ch.getStdev(), ch.getStdevNormalized(), ch.getMean(), ch.getMeanNormalized(), ch.getMedian(), ch.getMedianNormalized(),ch.getMode(), ch.getModeAvg(), ch.getModeNormalized(), ch.getModeNormalizedAvg(), ch.getMin(),ch.getMinNormalized(),ch.getMax(),ch.getMaxNormalized(),ch.getKurtosis(),ch.getKurtosisNormalized(),ch.getSkew(),ch.getSkewNormalized(),ch.getNinetyFifthPercentile(),ch.getNinetyFifthPercentileNormalized(),ch.getFifthPercentile(),ch.getFifthPercentileNormalized(),ch.getDynamicRange(),ch.getDynamicRangeNormalized() );
	}
	public void outputList(ArrayList list, String directory, String filename)
	{
		useful_tools.storeArrayListtoFileOnNewLines(list, directory, filename);
	}
	
	
}
