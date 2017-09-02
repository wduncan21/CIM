import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/*
 * The main purpose of this class is to take data from other files and extract the necessary data.
 * At the time of this writing, data can be extracted from a gpr, an excel file with raw data, or an excel file with normalized data
 */
public class DataPreparationClassFast {
	
	UsefulTools useful_tools = new UsefulTools();
	FolderAndFileHandler ffh = new FolderAndFileHandler();
	NormalizedDataHandler ndh = new NormalizedDataHandler();
	OutputHandler output_handler = new OutputHandler();
	boolean column_found = false;
	
	DataPreparationClassFast(FolderAndFileHandler ffh)
	{
		setFolderAndFileHandler(ffh);
	}
	DataPreparationClassFast()
	{
		
	}
	public void setFolderAndFileHandler(FolderAndFileHandler ffh)
	{
		this.ffh = ffh;
	}
	
	
	
	//the type_of_median is most likely "F647 Median" or "F555 Median"
	public int[] extractIntDataFromGPRFile(String directory, String filename, String type_of_median)
	{
		filename = Paths.get(directory+File.separator+filename).toString();
		ArrayList some_list = new ArrayList();
		CSVReader reader;
        try 
        {
        	
	        reader = new CSVReader(new FileReader(filename),'\t');
	        String[] row;
	        int column_found = -1;
	        while ((row = reader.readNext()) != null) 
	        {
	        	if(column_found!=-1)
	        	{
	        		some_list.add(row[column_found]);
	        	}
	        	if(column_found==-1)
	        	{
	        		for(int i=0; i<row.length; i++)
	        		{
	        			String current_string = row[i];
	        			if(current_string.equals(type_of_median))
	        			{
	        				column_found = i;
	        			}
	        		}
	        		
	        	}
        	
	        }
        }
        
        catch (FileNotFoundException e) 
        {
                System.err.println(e.getMessage());
        }
        catch (IOException e) 
        {
                System.err.println(e.getMessage());
        }
        
        //okay now convert this whole list to int values
        
        int[] iarray = new int[some_list.size()];
        for(int i=0; i<iarray.length;i++)
        {
        	iarray[i] = Double.valueOf(some_list.get(i).toString()).intValue();
        }
		return iarray;
		
		
	}
	
	//the type_of_median is most likely "F647 Median" or "F555 Median"
	public double[] extractDoubleDataFromGPRFile(String directory, String filename, String type_of_median)
	{
		filename = Paths.get(directory+File.separator+filename).toString();
		ArrayList some_list = new ArrayList();
		CSVReader reader;
        try 
        {
        	
	        reader = new CSVReader(new FileReader(filename),'\t');
	        String[] row;
	        int column_found = -1;
	        while ((row = reader.readNext()) != null) 
	        {
	        	if(column_found!=-1)
	        	{
	        		some_list.add(row[column_found]);
	        	}
	        	if(column_found==-1)
	        	{
	        		for(int i=0; i<row.length; i++)
	        		{
	        			String current_string = row[i];
	        			if(current_string.equals(type_of_median))
	        			{
	        				column_found = i;
	        			}
	        		}
	        		
	        	}
        	
	        }
        }
        
        catch (FileNotFoundException e) 
        {
                System.err.println(e.getMessage());
        }
        catch (IOException e) 
        {
                System.err.println(e.getMessage());
        }
        
        //okay now convert this whole list to int values
        
        double[] darray = new double[some_list.size()];
        for(int i=0; i<darray.length;i++)
        {
        	darray[i] = Double.valueOf(some_list.get(i).toString()).doubleValue();
        }
		return darray;
			
			
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
