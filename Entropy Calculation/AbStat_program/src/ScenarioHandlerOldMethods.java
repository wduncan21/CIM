import java.io.File;
import java.nio.file.Paths;


public class ScenarioHandlerOldMethods {
	UsefulTools useful_tools = new UsefulTools();
	DataPreparationClass dpc = new DataPreparationClass();
	CalculationHandler ch = new CalculationHandler();
	
	public void findEntropy_CV_NormEntropy_NormCV_from_one_gpr(String directory, String sample_name, int min_for_entropy, int max_for_entropy, double factor)
	{
		
		String extracted_directory = Paths.get(directory+File.separator+"extracted"+File.separator).toString();
		String extracted_filename = sample_name+"_extracted.gpr";
		String extracted_full_filepath = extracted_directory+extracted_filename;
	
		dpc.extractDataFromGPRFile(directory, sample_name+".gpr", "F647 Median");
		ch.calculateEntropy(extracted_full_filepath, 1, min_for_entropy, max_for_entropy, Paths.get(directory+""+File.separator+"entropy").toString(), useful_tools.addTextToFilename(sample_name, "_entropy"));
		ch.calculateCV(extracted_full_filepath, Paths.get(directory+""+File.separator+"cv").toString(), useful_tools.addTextToFilename(sample_name, "_cv"));		
		dpc.medianNormalizeDataFromFile(extracted_directory, sample_name+"_extracted.gpr");
		dpc.convertNormalizedDataToIntegers(Double.valueOf(factor).intValue(), Paths.get(extracted_directory+""+File.separator+"median_normalized").toString(), extracted_filename);
		ch.calculateCV(Paths.get(extracted_directory+""+File.separator+"median_normalized"+File.separator+"normalized_to_integers"+File.separator+""+extracted_filename).toString(), Paths.get(extracted_directory+""+File.separator+"median_normalized"+File.separator+"normalized_to_integers").toString(), sample_name);
		ch.calculateEntropyforNormalizedData(Paths.get(extracted_directory+""+File.separator+"median_normalized"+File.separator+"normalized_to_integers"+File.separator+""+extracted_filename).toString(), 1, Paths.get(extracted_directory+""+File.separator+"median_normalized"+File.separator+"normalized_to_integers").toString(), sample_name+"_entropy");

	}
	
	public void findEntropy_CV_NormEntropy_NormCV_from_folder_of_gprs(String directory, int min_for_entropy, int max_for_entropy, int factor)
	{
		dpc.extractDataFromAllGPRFilesInFolder(directory, "F647 Median");
		ch.calculateEntropyOfAllFilesInDirectory(Paths.get(directory+""+File.separator+"extracted").toString(), 1, min_for_entropy, max_for_entropy);
		ch.calculateCVOfAllFilesInDirectory(Paths.get(directory+""+File.separator+"extracted").toString());
		dpc.medianNormalizeDataFromFolderOfFiles(Paths.get(directory+""+File.separator+"extracted").toString());
		dpc.convertFolderOfNormalizedDataToIntegers(Paths.get(directory+""+File.separator+"extracted"+File.separator+"median_normalized").toString(), factor);
		ch.calculateCVOfAllFilesInDirectory(Paths.get(directory+""+File.separator+"extracted"+File.separator+"median_normalized"+File.separator+"normalized_to_integers").toString());
		ch.calculateEntropyOfAllFilesInDirectoryForNormalizedData(Paths.get(directory+""+File.separator+"extracted"+File.separator+"median_normalized"+File.separator+"normalized_to_integers").toString(), 1);
	}
	
	public void findEntropy_CV_NormEntropy_NormCV_from_tabdelimitedtext_raw_data(String directory, String filename, int min_for_entropy, int max_for_entropy, int factor)
	{
		dpc.prepareMultipleSamplesFromTabDelimitedTextFile(directory, filename, 0, 0, 3, 0, 3, 1);
		ch.calculateEntropyOfAllFilesInDirectory(Paths.get(directory+""+File.separator+"tdtDataExtracted").toString(), 1, min_for_entropy, max_for_entropy);
		ch.calculateCVOfAllFilesInDirectory(Paths.get(directory+""+File.separator+"tdtDataExtracted").toString());
		dpc.medianNormalizeDataFromFolderOfFiles(Paths.get(directory+""+File.separator+"tdtDataExtracted").toString());
		dpc.convertFolderOfNormalizedDataToIntegers(Paths.get(directory+""+File.separator+"tdtDataExtracted"+File.separator+"median_normalized").toString(), factor);
		ch.calculateCVOfAllFilesInDirectory(Paths.get(directory+""+File.separator+"tdtDataExtracted"+File.separator+"median_normalized"+File.separator+"normalized_to_integers").toString());
		ch.calculateEntropyOfAllFilesInDirectoryForNormalizedData(Paths.get(directory+""+File.separator+"tdtDataExtracted"+File.separator+"median_normalized"+File.separator+"normalized_to_integers").toString(), 1);

	}
	
	public void findEntropy_CV_NormEntropy_NormCV_from_tabdelimitedtext_normalized_data(String directory, String filename, int min_for_entropy, int max_for_entropy, int factor)
	{
		dpc.prepareMultipleSamplesFromTabDelimitedTextFile(directory, filename, 0, 0, 3, 0, 3, 1);
		dpc.convertFolderOfNormalizedDataToIntegers(Paths.get(directory+""+File.separator+"tdtDataExtracted").toString(), factor);
		ch.calculateEntropyOfAllFilesInDirectoryForNormalizedData(Paths.get(directory+""+File.separator+"tdtDataExtracted"+File.separator+"normalized_to_integers").toString(), 1);
		ch.calculateCVOfAllFilesInDirectory(Paths.get(directory+""+File.separator+"tdtDataExtracted"+File.separator+"normalized_to_integers").toString());
		
	}
}
