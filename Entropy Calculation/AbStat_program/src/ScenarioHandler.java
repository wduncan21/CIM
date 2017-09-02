import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;



public class ScenarioHandler {
	UsefulTools useful_tools = new UsefulTools();
	DataPreparationClassFast dpc = new DataPreparationClassFast();
	CalculationHandlerFast ch = new CalculationHandlerFast();
	OutputHandler oh = new OutputHandler();
	
	//usual number for the factor would be 10000 (aka 10,000)
	//examples of column titles to extract could be "F647 Median" or "F532 Median"
	public void find_summary_numbers_one_gpr(String directory, String sample_name, String column_title_to_extract)
	{
		
		
		String summary_directory = Paths.get(directory+""+File.separator+"summary").toString();
		
		if(!useful_tools.isMatchofRegEx(sample_name, "gpr"))
		{
			sample_name+=".gpr";
		}
		int[] data = dpc.extractIntDataFromGPRFile(directory, sample_name, column_title_to_extract);
		ch.calculateSummaryNumbers(data);
		ArrayList names = new ArrayList();
		names = useful_tools.stringWithCommasToArrayList(sample_name);
		oh.outputTableForCalculationHandler(summary_directory, ch, names);
	}
	
	//usual number for the factor would be 10000 (aka 10,000)
		//examples of column titles to extract could be "F647 Median" or "F532 Median"
		public void find_summary_numbers_one_gpr(String directory, String sample_name, String column_title_to_extract, int bin_size)
		{
			
			
			String summary_directory = Paths.get(directory+""+File.separator+"summary").toString();
			
			if(!useful_tools.isMatchofRegEx(sample_name, "gpr"))
			{
				sample_name+=".gpr";
			}
			int[] data = dpc.extractIntDataFromGPRFile(directory, sample_name, column_title_to_extract);
			ch.calculateSummaryNumbers(data, bin_size);
			ArrayList names = new ArrayList();
			names = useful_tools.stringWithCommasToArrayList(sample_name);
			oh.outputTableForCalculationHandler(summary_directory, ch, names);
		}
	
	
	//usual numbers for the min, max, and factor for the entropy would be 1, 65535, 10000 (aka 10,000)
	public void find_summary_numbers_from_folder_of_gprs(String directory, String column_title_to_extract)
	{
		
		String summary_directory = Paths.get(directory+""+File.separator+"summary").toString();
		ch.calculateSummaryNumbersOfAllFilesInDirectory(directory, column_title_to_extract);
		oh.outputTableForCalculationHandler(summary_directory, ch, ch.getFileNames());
	}
	
	//usual numbers for the min, max, and factor for the entropy would be 1, 65535, 10000 (aka 10,000)
	public void find_summary_numbers_from_folder_of_gprs(String directory, String column_title_to_extract, int bin_size)
	{
		
		String summary_directory = Paths.get(directory+""+File.separator+"summary").toString();
		ch.calculateSummaryNumbersOfAllFilesInDirectory(directory, column_title_to_extract, bin_size);
		oh.outputTableForCalculationHandler(summary_directory, ch, ch.getFileNames());
	}
	
	
	
	public void find_summary_numbers_from_tabdelimitedtext_raw_data(String directory, String filename, int sample_name_row, int sample_name_column_start, int sample_name_column_end, int data_column_start, int data_column_end, int data_starting_row)
	{
		String summary_directory = Paths.get(directory+""+File.separator+"summary").toString();
		ch.find_summary_numbers_from_tabdelimitedtext(directory, filename, false, sample_name_row, sample_name_column_start, sample_name_column_end, data_column_start, data_column_end, data_starting_row);
		oh.outputTableForCalculationHandler(summary_directory, ch, ch.getFileNames());
	}
	
	public void find_summary_numbers_from_tabdelimitedtext_raw_data(String directory, String filename, int sample_name_row, int sample_name_column_start, int sample_name_column_end, int data_column_start, int data_column_end, int data_starting_row, int bin_size)
	{
		String summary_directory = Paths.get(directory+""+File.separator+"summary").toString();
		ch.find_summary_numbers_from_tabdelimitedtext(directory, filename, false, sample_name_row, sample_name_column_start, sample_name_column_end, data_column_start, data_column_end, data_starting_row, bin_size);
		oh.outputTableForCalculationHandler(summary_directory, ch, ch.getFileNames());
	}
	
	public void find_summary_numbers_from_tabdelimitedtext_normalized_data(String directory, String filename, int sample_name_row, int sample_name_column_start, int sample_name_column_end, int data_column_start, int data_column_end, int data_starting_row)
	{
		String summary_directory = Paths.get(directory+""+File.separator+"summary").toString();
		ch.find_summary_numbers_from_tabdelimitedtext(directory, filename, true, sample_name_row, sample_name_column_start, sample_name_column_end, data_column_start, data_column_end, data_starting_row);
		oh.outputTableForCalculationHandler(summary_directory, ch, ch.getFileNames());
	}
	
	public void find_summary_numbers_from_tabdelimitedtext_normalized_data(String directory, String filename, int sample_name_row, int sample_name_column_start, int sample_name_column_end, int data_column_start, int data_column_end, int data_starting_row, int bin_size)
	{
		String summary_directory = Paths.get(directory+""+File.separator+"summary").toString();
		ch.find_summary_numbers_from_tabdelimitedtext(directory, filename, true, sample_name_row, sample_name_column_start, sample_name_column_end, data_column_start, data_column_end, data_starting_row, bin_size);
		oh.outputTableForCalculationHandler(summary_directory, ch, ch.getFileNames());
	}
	
	//example name of summary of might be "table_of_summary_numbers.txt"
	//example output file name: "complete_table_9-23-13.txt"
	public void collectAllSummaryFilesIntoOneTable(String directory, String name_of_summary_file, String output_file_name)
	{
		ArrayList list_of_matching_files = useful_tools.findAllFilesInDirectoryAndSubdirectoriesMatchingThisName(directory, name_of_summary_file);
		useful_tools.combineAllTablesIntoOneTable(list_of_matching_files, directory,output_file_name);
		
	}
	
	
	//note that I'm not sure if I really finished this code
	//there are better ways of combining many gpr files
	//the last time I did this I used the linux way
	//see [quickly combining many gpr files into one table 01-10-2014d2210]
	public void outputFolderOfGPRFilesAsTable(String directory, String column_title_to_extract)
	{
		
		
		String return_string = ch.outputFolderOfGPRFilesAsTable(directory, column_title_to_extract);
		useful_tools.createTextFile(directory, "gprs_as_table.txt", return_string);
	}
	
	/*
	 * I'll just write this method to work in Windows.
	 * The purpose of this method is to put files into their own folder
	 */
	public void placeFilesInFolderIntoTheirOwnFolder(String directory)
	{
		FolderAndFileHandler ffh = new FolderAndFileHandler();
		ArrayList samples_to_process = ffh.listFilesAsArrayList(directory);
		//first make all of the folders with a command like "mkdir"
		for(int i=0; i<samples_to_process.size();i++)
		{
			
			useful_tools.createDirectory(Paths.get(directory).toString()+File.separator+Integer.valueOf(i).toString());
			try{
				 
		    	   File afile =new File(Paths.get(directory).toString()+File.separator+samples_to_process.get(i).toString());
		 
		    	   if(afile.renameTo(new File(Paths.get(directory).toString()+File.separator+Integer.valueOf(i).toString() +File.separator+ afile.getName()))){
		    		//System.out.println("File is moved successful!");
		    	   }else{
		    		System.out.println("File is failed to move!");
		    	   }
		 
		    	}catch(Exception e){
		    		e.printStackTrace();
		    	}
		}
	}
	
}
