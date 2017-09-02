import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Paths;
import java.util.ArrayList;


public class FolderAndFileHandler {

	
	UsefulTools useful_tools = new UsefulTools();
	private String current_directory = "";
	
	public void setCurrentDirectory(String current_directory)
	{
		 this.current_directory = current_directory; 
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
	   
	       if (files.endsWith(".gpr") || files.endsWith(".GPR") || files.endsWith("txt") || files.endsWith("TXT") || files.endsWith(""))
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
