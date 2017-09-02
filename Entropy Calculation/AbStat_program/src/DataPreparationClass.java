import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

/*
 * The main purpose of this class is to take data from other files and extract the necessary data.
 * At the time of this writing, data can be extracted from a gpr, an excel file with raw data, or an excel file with normalized data
 */
public class DataPreparationClass {
	
	UsefulTools useful_tools = new UsefulTools();
	FolderAndFileHandler ffh = new FolderAndFileHandler();
	NormalizedDataHandler ndh = new NormalizedDataHandler();
	OutputHandler output_handler = new OutputHandler();
	boolean column_found = false;
	
	DataPreparationClass(FolderAndFileHandler ffh)
	{
		setFolderAndFileHandler(ffh);
	}
	DataPreparationClass()
	{
		
	}
	public void setFolderAndFileHandler(FolderAndFileHandler ffh)
	{
		this.ffh = ffh;
	}
	
	//needs to be written
	//this method takes the filepath of a gpr and outputs a file containing only the list of numbers
	//the type_of_median is most likely "F647 Median" or "F555 Median"
	public void extractDataFromGPRFile(String directory, String filename, String type_of_median)
	{
		boolean median_column_found = false;
		String filepath = Paths.get(directory+File.separator+filename).toString();
		String raw_gpr_string = useful_tools.storeTextFiletoString(filepath);
		String tab_delimited_gpr_string = produceGPRTextWithoutHeader(raw_gpr_string);
		ArrayList medians = new ArrayList();
		medians = getMediansFromGPRTextWithoutHeader(tab_delimited_gpr_string, type_of_median);
		String new_filename = useful_tools.addTextToFilename(filename, "_extracted");
		if(column_found)
		{
			useful_tools.storeArrayListtoFileOnNewLines(medians, Paths.get(directory+""+File.separator+"extracted").toString(), new_filename);
		}
	}
	
	
	//This method will extract the data from a list of GPR files and output files with just the list of numbers
	//the type_of_median is most likely "F647 Median" or "F555 Median"
	//this method returns an arraylist of the filenames
	public ArrayList extractDataFromAllGPRFilesInFolder(String directory, String type_of_median)
	{
		
		ArrayList samples_to_process = ffh.listFilesAsArrayList(directory);
		for(int i=0; i<samples_to_process.size(); i++)
		{
			String original_filename = samples_to_process.get(i).toString();
			extractDataFromGPRFile(directory, original_filename, type_of_median);
		}
		return samples_to_process;
	}
	
	
	
	//this extracts a specified partial column from a tab delimited text file and outputs a file with just the data
	//the string of the sample name is returned
	public String prepareDataFromTabDelimitedTextFile(String directory, String filename, int sample_name_row, int sample_name_column, int data_column, int data_starting_row)
	{
		String output_directory = Paths.get(directory+""+File.separator+"tdtDataExtracted").toString();
		useful_tools.setTabDelimitedText(useful_tools.storeTextFiletoString(directory+""+File.separator+""+filename));
		ArrayList medians = useful_tools.extractPartialColumn(data_column, data_starting_row);
		//make sure everything in the medians list is a double
		ArrayList medians2 = new ArrayList();
		for(int i=0; i<medians.size(); i++)
		{
			try
			{
				medians2.add(Double.valueOf(medians.get(i).toString()).doubleValue());
			}
			catch(Exception e)
			{
				
			}
		}
		
		String sample_name = useful_tools.extractCell(sample_name_row, sample_name_column);
		useful_tools.storeArrayListtoFileOnNewLines(medians2, output_directory, sample_name+"_numbers.txt");
		return sample_name;
	}
	
	//this method extracts the data from multiple samples in multiple columns and outputs each sample to its own text file
	//does the count start at 1 or 0 with this method?
	public ArrayList prepareMultipleSamplesFromTabDelimitedTextFile(String directory, String filename, int sample_name_row, int sample_name_column_start, int sample_name_column_end, int data_column_start, int data_column_end, int data_starting_row)
	{
		ArrayList names = new ArrayList();
		for(int column_count=data_column_start; column_count<=data_column_end; column_count++)
		{
			names.add(prepareDataFromTabDelimitedTextFile(directory, filename, sample_name_row, column_count, column_count, data_starting_row));
		}
		return names;
	}
	
	public void medianNormalizeDataFromFile(String directory, String filename)
	{
		ArrayList list = useful_tools.storeTextFiletoArrayList(Paths.get(directory+""+File.separator+""+filename).toString());
		double median = useful_tools.Median(list);
		ArrayList list2 = new ArrayList();
		for(int i=0; i<list.size(); i++)
		{
			list2.add(Double.valueOf(list.get(i).toString()).doubleValue()/median);
		}
		String new_filename = useful_tools.addTextToFilename(filename, "_med_norm");
		output_handler.outputList(list2, Paths.get(directory+""+File.separator+"median_normalized").toString(), filename);
		
	}
	
	public void medianNormalizeDataFromFolderOfFiles(String directory)
	{
		ArrayList samples_to_process = ffh.listFilesAsArrayList(directory);
		for(int i=0; i<samples_to_process.size(); i++)
		{
			String original_filename = samples_to_process.get(i).toString();
			try
			{
				medianNormalizeDataFromFile(directory, original_filename);
			}
			catch(Exception e)
			{
				
			}
		}
	}
	
	//this takes a list of doubles that has been normalized and converts this list into integers between the lower bound and upper bound
	public void convertNormalizedDataToIntegers(ArrayList list, int factor, String directory, String filename)
	{
		list = ndh.convertNormalizedDataToIntByMultiplyingFactor(list, factor);
		output_handler.outputList(list, Paths.get(directory+""+File.separator+"normalized_to_integers").toString(), filename);
		
	}
	
	public void convertFolderOfNormalizedDataToIntegers(String directory, int factor)
	{
		ArrayList samples_to_process = ffh.listFilesAsArrayList(directory);
		for(int i=0; i<samples_to_process.size(); i++)
		{
			String original_filename = samples_to_process.get(i).toString();
			convertNormalizedDataToIntegers(factor, directory, original_filename);
	
		}
	}
	public void convertNormalizedDataToIntegers(int factor, String directory, String filename)
	{
		ArrayList list = useful_tools.storeTextFiletoArrayList(Paths.get(directory+""+File.separator+""+filename).toString());
		convertNormalizedDataToIntegers(list, factor, directory, filename);
	}
	
	public String produceGPRTextWithoutHeader(String GPRText)
	{
		
		String return_string = GPRText;
		
		return_string = return_string.replaceAll("\r", "ZZZ");
		return_string = useful_tools.getFirstMatchofRegEx(return_string, "Block.+");
		//return_string = useful_tools.findAndReplacewithRegEx(return_string, ".+ZZZ\"Block\"", "Z");
		return_string = return_string.replaceAll("ZZZ", "\r");
		
		return return_string;
	}
	
	public ArrayList getF647MediansFromGPRTextWithoutHeader(String text)
	{
		return getMediansFromGPRTextWithoutHeader(text, "[\"F647 Median\"]");
	}
	public ArrayList getF555MediansFromGPRTextWithoutHeader(String text)
	{
		return getMediansFromGPRTextWithoutHeader(text, "[\"F555 Median\"]");
	}
	public ArrayList getMediansFromGPRTextWithoutHeader(String text, String type_of_median)
	{
		ArrayList medians = new ArrayList();
		useful_tools.setTabDelimitedText(text);
		//find out which column has the F647 medians in it
		column_found = false;
		int column_number = 0;
		try
		{
			while(!column_found)
			{
				
				String current_string = useful_tools.extractCell(0, column_number);
				
				if(current_string.indexOf(type_of_median)!=-1)
				{
					column_found = true;
				}
				else
				{	
					column_number++;
				}
			}
			
			
			medians = useful_tools.extractPartialColumn(column_number, 1);
		}
		catch(Exception e)
		{
			String temp_string = type_of_median.replaceAll("\\[", "");
			temp_string = temp_string.replaceAll("\\]", "");
			System.out.println("No column containing "+temp_string+ " was found");
		}
		
		return medians;
		
	}
	
	
	
	
	public String listFiles(String path)
	{ 
	 
	  String files;
	  File folder = new File(path);
	  File[] listOfFiles = folder.listFiles(); 
	  String return_string = "";
	  for (int i = 0; i < listOfFiles.length; i++) 
	  {
	 
	   if (listOfFiles[i].isFile()) 
	   {
	   files = listOfFiles[i].getName();
	       if (files.endsWith(".gpr") || files.endsWith(".GPR"))
	       {
	          return_string+=files+",";
	        }
	     }
	  }
	  return return_string;
		 
	}
	
	public ArrayList listFilesAsArrayList(String path)
	{
		return useful_tools.stringWithCommasToArrayList(listFiles(path));
	}

}
