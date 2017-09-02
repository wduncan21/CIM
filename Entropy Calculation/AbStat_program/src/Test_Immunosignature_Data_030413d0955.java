import java.io.File;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


public class Test_Immunosignature_Data_030413d0955 {

	private UsefulTools useful_tools = new UsefulTools();
	private DataPreparationClass dpc = new DataPreparationClass();
	private NormalizedDataHandler ndh = new NormalizedDataHandler();
	private CalculationHandler ch = new CalculationHandler();
	TestHandler test_handler = new TestHandler();
	ScenarioHandler sh = new ScenarioHandler();
	
	String gpr_data_directory = "";
	String result_data_directory = "";
	//set dye_type to "F555 Median" or "F647 Median"
	String dye_type = "F647 Median";
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		
		
		if(args.length>0)
		{
			Test_Immunosignature_Data_030413d0955 tid = new Test_Immunosignature_Data_030413d0955(args);
		}
		
		else
		{
			Test_Immunosignature_Data_030413d0955 tid = new Test_Immunosignature_Data_030413d0955();
			tid.test();	
		}
		
		
		
		
	}
	
	public Test_Immunosignature_Data_030413d0955()
	{
		
	}
	
	public Test_Immunosignature_Data_030413d0955(String[] args)
	{
		Test_Immunosignature_Data_030413d0955FromCommandLine(args);
	}
	
	public Test_Immunosignature_Data_030413d0955(String directory, String filename)
	{
		
		System.out.println(useful_tools.getTime());
	
		
		ScenarioHandler sh = new ScenarioHandler();
		
		sh.find_summary_numbers_one_gpr(directory, filename, "F532 Median");
		
		System.out.println(useful_tools.getTime());
	}
	
	
	
	public void Test_Immunosignature_Data_030413d0955FromCommandLine(String[] args)
	{
		/*
		//what are examples of the possible args parameter?
		//usual numbers for the min, max, and factor for the entropy would be 1, 65535, 10000 (aka 10,000)
		//examples of collumn titles to extract could be "F647 Median" or "F532 Median"
		//-find_summary_numbers_one_gpr(String directory, String sample_name, String column_title_to_extract, boolean entropy, int min_for_entropy, int max_for_entropy, double factor)
		//--command line version: find_summary_numbers_one_gpr directory sample_name column_title_to_extract entropy min_for_entropy max_for_entropy factor
		//-find_summary_numbers_from_folder_of_gprs(String directory, String column_title_to_extract, boolean entropy, int min_for_entropy, int max_for_entropy, int factor)
		//-command line version: find_summary_numbers_from_tabdelimitedtext_raw_data directory filename min_for_entropy max_for_entropy factor 
		//-find_summary_numbers_from_tabdelimitedtext_raw_data(String directory, String filename, int min_for_entropy, int max_for_entropy, int factor, boolean entropy, int sample_name_row, int sample_name_column_start, int sample_name_column_end, int data_column_start, int data_column_end, int data_starting_row)
		 //--command line version: find_summary_numbers_from_tabdelimitedtext_raw_data directory filename min_for_entropy max_for_entropy factor entropy sample_name_row sample_name_column_start sample_name_column_end data_column_start data_column_end data_starting_row
		 //^note that the data rows and columns would start from count 0
		  * //-find_summary_numbers_from_tabdelimitedtext_normalized_data(String directory, String filename, int min_for_entropy, int max_for_entropy, int factor, boolean entropy, int sample_name_row, int sample_name_column_start, int sample_name_column_end, int data_column_start, int data_column_end, int data_starting_row)
	 //--command line version: find_summary_numbers_from_tabdelimitedtext_normalized_data directory filename min_for_entropy max_for_entropy factor entropy sample_name_row sample_name_column_start sample_name_column_end data_column_start data_column_end data_starting_row
		//^note that the data rows and columns would start from count 0
		 * 
		 * //-collectAllSummaryFilesIntoOneTable(String directory, String name_of_summary_file, String output_file_name)
	 //--command line version: collectAllSummaryFilesIntoOneTable directory name_of_summary_file output_file_name
		 * 
		 * 
		 * 
		*/
		
		String return_string = useful_tools.getTime()+"\r\n";
		String directory = args[1];
		String call_details ="";
		if(args[0].equals("find_summary_numbers_one_gpr"))
		{
			
			call_details = "find_summary_numbers_one_gpr_"+args[2];
			if(args[4]!=null)
			{
				sh.find_summary_numbers_one_gpr(args[1], args[2], args[3], Integer.valueOf(args[4]).intValue());
			}
			else
			{
				sh.find_summary_numbers_one_gpr(args[1], args[2], args[3]);
			}
			
		}
		else if(args[0].equals("find_summary_numbers_from_folder_of_gprs"))
		{
			call_details = "find_summary_numbers_from_folder_of_gprs_"+args[1];
			if(args[3]!=null)
			{
				sh.find_summary_numbers_from_folder_of_gprs(args[1], args[2], Integer.valueOf(args[3]).intValue());
			}
			else
			{
				sh.find_summary_numbers_from_folder_of_gprs(args[1], args[2]);
			}
		}
		else if(args[0].equals("find_summary_numbers_from_tabdelimitedtext_raw_data"))
		{
			call_details = "find_summary_numbers_from_tabdelimitedtext_raw_data_"+args[1];
			if(args[9]!=null)
			{
				sh.find_summary_numbers_from_tabdelimitedtext_raw_data(args[1], args[2], Integer.valueOf(args[3]).intValue(), Integer.valueOf(args[4]).intValue(), Integer.valueOf(args[5]).intValue(), Integer.valueOf(args[6]).intValue(), Integer.valueOf(args[7]).intValue(), Integer.valueOf(args[8]).intValue(), Integer.valueOf(args[9]).intValue());
			}
			else
			{
				sh.find_summary_numbers_from_tabdelimitedtext_raw_data(args[1], args[2], Integer.valueOf(args[3]).intValue(), Integer.valueOf(args[4]).intValue(), Integer.valueOf(args[5]).intValue(), Integer.valueOf(args[6]).intValue(), Integer.valueOf(args[7]).intValue(), Integer.valueOf(args[8]).intValue());
			}
		}
		else if(args[0].equals("find_summary_numbers_from_tabdelimitedtext_normalized_data"))
		{
			call_details = "find_summary_numbers_from_tabdelimitedtext_normalized_data_"+directory;
			if(args[9]!=null)
			{
				sh.find_summary_numbers_from_tabdelimitedtext_normalized_data(args[1], args[2], Integer.valueOf(args[3]).intValue(), Integer.valueOf(args[4]).intValue(), Integer.valueOf(args[5]).intValue(), Integer.valueOf(args[6]).intValue(), Integer.valueOf(args[7]).intValue(), Integer.valueOf(args[8]).intValue(), Integer.valueOf(args[9]).intValue());
				
			}
			else
			{
				sh.find_summary_numbers_from_tabdelimitedtext_normalized_data(args[1], args[2], Integer.valueOf(args[3]).intValue(), Integer.valueOf(args[4]).intValue(), Integer.valueOf(args[5]).intValue(), Integer.valueOf(args[6]).intValue(), Integer.valueOf(args[7]).intValue(), Integer.valueOf(args[8]).intValue());
				
			}

		}
		else if(args[0].equals("collectAllSummaryFilesIntoOneTable"))
		{
			call_details = "collectAllSummaryFilesIntoOneTable_"+directory;
			sh.collectAllSummaryFilesIntoOneTable(args[1], args[2], args[3]);
		}
		else if(args[0].equals("placeFilesInFolderIntoTheirOwnFolder"))
		{
			call_details = "placeFilesInFolderIntoTheirOwnFolder_"+directory;
			sh.placeFilesInFolderIntoTheirOwnFolder(args[1]);
			
		}
		return_string+=useful_tools.getTime();
		//we need to make sure that the call details does not have any strange characters in it
		call_details = call_details.replaceAll(":", "C");
		call_details = call_details.replaceAll(Pattern.quote(File.separator), "S");
		useful_tools.createTextFile(directory, "runtime_info.txt", return_string);
	}

	
	public void test()
	{
		System.out.println(useful_tools.getTime());
		TestHandler th = new TestHandler();
		
		//String[] arguments = {"find_summary_numbers_from_tabdelimitedtext_normalized_data","C:\\Users\\kurtw_000\\Desktop\\temp","BC vs. Normal2.txt","2","1","2","1","2","6"};
		//String[] arguments = {"find_summary_numbers_from_tabdelimitedtext_normalized_data","C:\\Users\\kurtw_000\\Desktop\\temp","BC vs. Normal2.txt","2","1","2","1","2","6"};
		String[] arguments = {"find_summary_numbers_one_gpr", "C:\\Users\\kurtw_000\\Documents\\kurt\\storage\\CIM Research Folder\\DR\\2014\\02-18-2014d1330\\example_gprs","4-46 S2 F1 Hi P20 110512 ND145 50k S","F532 Median", "5"};
		
		Test_Immunosignature_Data_030413d0955FromCommandLine(arguments);
		
		
		//th.testOutputGPRsAsTable("C:\\Users\\kurtw_000\\Documents\\kurt\\storage\\CIM Research Folder\\DR\\2014\\01-10-2014d1031\\gpr_extraction_test","F647 Median");
		//th.testOutputGPRsAsTable("C:\\Users\\kurtw_000\\Documents\\kurt\\storage\\CIM Research Folder\\DR\\2013\\10-17-13\\alzheimers data", "F647 Median");
		//th.test_find_summary_numbers_one_gpr();
		//th.test_find_summary_numbers_one_small_gpr();
		//th.test_folder_of_gprs();
		//th.test_tabDelimitedText092013d1132();
		//th.test_tabDelimitedTextNormalized092013d1507();
		//th.test2_092013d1547();
		//th.test3_092013d1547();
		//th.testFileSearch092013d1648();
		//th.testFileCombination0923131027();
		//th.testPatternNotSurroundedBySurrouningPattern();
		//th.testPatternNotSurroundedBySurrouningPattern092413d1340();
		//th.test_find_summary_numbers_one_gpr092413d1626();
		//String[] args = {"find_summary_numbers_from_folder_of_gprs","F:\\kurt\\storage\\CIM Research Folder\\DR\\2013\\9-16-13\\entropy\\test8","F532 Median","true","1","20","10"};
		//th.test_folder_of_gprs092413d1734(args);
		//th.testNewSummaryMeasures092713d1106();
		
		//th.testPlaceFilesInTheirOwnDirectory();
		//String[] args = {"find_summary_numbers_one_gpr", "F:\\kurt\\storage\\CIM Research Folder\\DR\\2013\\10-1-13\\10k_gpr_test", "406971_bot-GRP2_01182012_young", "F647 Median", "false", "1", "65535", "10000"};
		//Test_Immunosignature_Data_030413d0955FromCommandLine(args);
		//th.testFindFiles();
		//th.testValueExtractionFromGPR_100513d1536();
		//th.testOneGPR_100713d1600();
		//th.testFolderOfGPRs_100713d1645();
		//th.testTableOfGPRs_100813d0936();
		//th.testTableOfGPRsForNormalizedData_100813d0950();
		//sh.find_summary_numbers_from_folder_of_gprs("S:/Research/DocInABox/People/Tiger/Entroy Study/FVBN 2013 Time series study/20130517_FVBN time series_pilot2", "F647 Median");
		System.out.println(useful_tools.getTime());
		
	}
	

}
