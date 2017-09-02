This jar file (some_name.jar) can be run with a command line.  There are basically six uses of this program:
-find_summary_numbers_one_gpr
-find_summary_numbers_from_folder_of_gprs
-find_summary_numbers_from_tabdelimitedtext_raw_data
-find_summary_numbers_from_tabdelimitedtext_normalized_data
-collectAllSummaryFilesIntoOneTable
-placeFilesInFolderIntoTheirOwnFolder


What are the summary numbers included?
entropy	normalized_shannon_entropy	max	min	cv	stdev	mean	median	fifth_percentile	ninety_fifth_percentile	entropy_normalized_data	normalized_entropy_normalized_data	max_normalized	min_normalized	stdev_normalized	mean_normalized	fifth_percentile_normalized	ninety_fifth_percentile_normalized	kurtosis	skew	dynamic_range	total_intensity_sum



Here is more information about the five uses of the program:

Note that the bin_size parameter is always optional.  The default bin_size of 1 will be used if the bin_size is not specified. (Note as of 4-3-14: it appears that the bin_size parameter is not optional so if you don't want to set it then enter 1 for the default bin size.)

//examples of column titles to extract could be "F647 Median" or "F532 Median"
//-find_summary_numbers_one_gpr(String directory, String sample_name, String column_title_to_extract, int bin_size(optional))
//--command line version: find_summary_numbers_one_gpr directory sample_name column_title_to_extract bin_size(optional)
//--example command: java -jar "F:\kurt\storage\CIM Research Folder\DR\2013\10-9-13\EntropyOfArray092013\some_name.jar" find_summary_numbers_one_gpr "F:\\kurt\\storage\\CIM Research Folder\\DR\\2013\\9-16-13\\entropy\\test7" "test2 9-18-13" "F532 Median" 5(optional)


//-find_summary_numbers_from_folder_of_gprs(String directory, String column_title_to_extract, int bin_size(optional))
//--command line version: find_summary_numbers_from_folder_of_gprs directory column_title_to_extract bin_size(optional)
//--example command: java -jar "F:\kurt\storage\CIM Research Folder\DR\2013\10-9-13\EntropyOfArray092013\some_name.jar" find_summary_numbers_from_folder_of_gprs "F:\kurt\storage\CIM Research Folder\DR\2013\9-16-13\entropy\test8" "F532 Median" 5(optional)


//-find_summary_numbers_from_tabdelimitedtext_raw_data(String directory, String filename, int sample_name_row, int sample_name_column_start, int sample_name_column_end, int data_column_start, int data_column_end, int data_starting_row, int bin_size(optional))
//--command line version: find_summary_numbers_from_tabdelimitedtext_raw_data directory filename sample_name_row sample_name_column_start sample_name_column_end data_column_start data_column_end data_starting_row bin_size(optional)
//^note that the data rows and columns would start from count 0
//--example command: java -jar "F:\kurt\storage\CIM Research Folder\DR\2013\10-9-13\EntropyOfArray092013\some_name.jar" find_summary_numbers_from_tabdelimitedtext_raw_data "F:\kurt\storage\CIM Research Folder\DR\2013\9-16-13\entropy\test9" "test_data_9-20-13d1124.txt" 0 1 2 1 2 1 5(optional)



//-find_summary_numbers_from_tabdelimitedtext_normalized_data(String directory, String filename, int sample_name_row, int sample_name_column_start, int sample_name_column_end, int data_column_start, int data_column_end, int data_starting_row, int bin_size(optional))
//--command line version: find_summary_numbers_from_tabdelimitedtext_normalized_data directory filename sample_name_row sample_name_column_start sample_name_column_end data_column_start data_column_end data_starting_row bin_size(optional)
//^note that the data rows and columns would start from count 0
//--example command: java -jar "F:\kurt\storage\CIM Research Folder\DR\2013\10-9-13\EntropyOfArray092013\some_name.jar" find_summary_numbers_from_tabdelimitedtext_normalized_data "F:\kurt\storage\CIM Research Folder\DR\2013\9-16-13\entropy\test10" "test_data_9-20-13d1124.txt" 0 1 2 1 2 1 5(optional)



//-collectAllSummaryFilesIntoOneTable(String directory, String name_of_summary_file, String output_file_name)
//--command line version: collectAllSummaryFilesIntoOneTable directory name_of_summary_file output_file_name
//--example command: java -jar some_name.jar collectAllSummaryFilesIntoOneTable "F:\kurt\storage\CIM Research Folder\DR\2013\9-16-13\entropy\test11" "table_of_summary_numbers.txt" "complete_table_092513d1115.txt"


//-placeFilesInFolderIntoTheirOwnFolder(String directory)
//--command line version: placeFilesInFolderIntoTheirOwnFolder directory
//--example command java -jar some_name.jar placeFilesInFolderIntoTheirOwnFolder "F:\kurt\storage\some_fake_filepath"

