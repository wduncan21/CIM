import java.io.File;
import java.io.FileFilter;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.io.*;

public class TestHandler {

	private UsefulTools useful_tools = new UsefulTools();
	private DataPreparationClass dpc = new DataPreparationClass();
	private NormalizedDataHandler ndh = new NormalizedDataHandler();
	private CalculationHandler ch = new CalculationHandler();
	private ScenarioHandlerOldMethods sh = new ScenarioHandlerOldMethods();
	private ScenarioHandler sh_new = new ScenarioHandler();

	/*
	 * entropy value should have been 2.56 which is what the program
	 * successfully returned cv should have been 0.54 which is what the program
	 * returned the median normalized list looks the way that it should the
	 * normalized list looks the way that it should the normalized cv was still
	 * 0.504 as it should be the normalized entropy was still 2.56 as it should
	 * be
	 */
	public void test_all_different() {
		// dpc.extractDataFromGPRFile("S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13",
		// "all_different.gpr",
		// "S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\extracted",
		// "all_different_extracted.gpr", "F647 Median");
		// ch.calculateEntropy("S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\extracted\\all_different_extracted.gpr",
		// 1, 1, 65535,
		// "S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\extracted\\entropy",
		// "all_different_extracted.gpr");
		// ch.calculateCV("S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\extracted\\all_different_extracted.gpr",
		// "S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\extracted\\cv",
		// "all_different_extracted_cv");
		// dpc.medianNormalizeDataFromFile("S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\extracted",
		// "all_different_extracted.gpr");
		// dpc.convertNormalizedDataToIntegers(10000,
		// "S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\extracted\\median_normalized",
		// "all_different_extracted.gpr");
		// ch.calculateCV("S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\extracted\\median_normalized\\normalized_to_integers\\\\all_different_extracted.gpr",
		// "S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\extracted\\median_normalized\\normalized_to_integers",
		// "all_different_normalized_cv");
		// ch.calculateEntropy("S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\extracted\\median_normalized\\normalized_to_integers\\all_different_extracted.gpr",
		// 1, 1, 65535,
		// "S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\extracted\\median_normalized\\normalized_to_integers",
		// "all_different_entropy");
	}

	/*
	 * Time for method to execute 2013/06/13 21:34:01 2013/06/13 21:43:21 so
	 * about 10 min
	 */
	// the nhl signifies that there are no maximally high or low values
	public void test_random_nhl() {
		String sample_name = "random_nhl";
		// dpc.extractDataFromGPRFile("S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13",
		// sample_name+".gpr", "F647 Median");
		// ch.calculateEntropy("S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\extracted\\"+sample_name+"_extracted.gpr",
		// 1, 1, 65535,
		// "S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\extracted\\entropy",
		// sample_name+"_extracted.gpr");
		// ch.calculateCV("S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\extracted\\"+sample_name+"_extracted.gpr",
		// "S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\extracted\\cv",
		// sample_name+"_extracted_cv");
		// dpc.medianNormalizeDataFromFile("S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\extracted",
		// sample_name+"_extracted.gpr");
		// dpc.convertNormalizedDataToIntegers(10000,
		// "S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\extracted\\median_normalized",
		// sample_name+"_extracted.gpr");
		// ch.calculateCV("S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\extracted\\median_normalized\\normalized_to_integers\\"+sample_name+"_extracted.gpr",
		// "S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\extracted\\median_normalized\\normalized_to_integers",
		// sample_name+"_normalized_cv");
		// ch.calculateEntropy("S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\extracted\\median_normalized\\normalized_to_integers\\"+sample_name+"_extracted.gpr",
		// 1, 1, 65535,
		// "S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\extracted\\median_normalized\\normalized_to_integers",
		// sample_name+"_entropy");
	}

	/*
	 * Entropy should have been 2.25 and it was The cv should have been 0.48 and
	 * it was The normalized cv was still 0.48 as it should be normalized
	 * entropy was 2.25 as it should be
	 */
	public void test_random_nhl_lesst_20() {
		sh.findEntropy_CV_NormEntropy_NormCV_from_one_gpr(
				"S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13",
				"random_nhl_lesst_20", 1, 20, 10);
	}

	/*
	 * entropy was 2.56 as it should be cv was 0.48 as it should be normalized
	 * cv was 0.51 as it should be normalized entropy was 2.56 as it should be
	 */

	public void test_all_different_lesst_20() {
		sh.findEntropy_CV_NormEntropy_NormCV_from_one_gpr(
				"S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13",
				"all_different_lesst_20", 1, 20, 10);
	}

	/*
	 * all values were zero as they should be
	 */
	public void test_all_high_lesst_20() {
		sh.findEntropy_CV_NormEntropy_NormCV_from_one_gpr(
				"S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13",
				"all_high_lesst_20", 1, 20, 10);
	}

	public void test_all_low_lesst_20() {
		sh.findEntropy_CV_NormEntropy_NormCV_from_one_gpr(
				"S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13",
				"all_low_lesst_20", 1, 20, 10);
	}

	/*
	 * entropy of all different was 2.56 as it should be. . all random: 2.25,
	 * all high: 0, all low:0 <- as it should be cv values {
	 * all_different_lesst_20_extracted.gpr 0.48680506023116343
	 * all_high_lesst_20_extracted.gpr 0.0 all_low_lesst_20_extracted.gpr 0.0
	 * random_nhl_lesst_20_extracted.gpr 0.4822635430965106 }<- as it should be
	 * entropy values of normalized data { all_different_lesst_20_extracted.gpr
	 * 2.5649493574615376 all_high_lesst_20_extracted.gpr 0.0
	 * all_low_lesst_20_extracted.gpr 0.0 random_nhl_lesst_20_extracted.gpr
	 * 2.245035274126178 }<- as it should be cv of normalized data {
	 * all_different_lesst_20_extracted.gpr 0.5104455570055113
	 * all_high_lesst_20_extracted.gpr 0.0 all_low_lesst_20_extracted.gpr 0.0
	 * random_nhl_lesst_20_extracted.gpr 0.4961946816619952 }<- as it should be
	 */
	public void test_folder_of_gprs_lesst_20() {
		String directory = "S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\test_gprs_lesst_20";
		sh.findEntropy_CV_NormEntropy_NormCV_from_folder_of_gprs(directory, 1,
				20, 10);
	}

	/*
	 * cv values { all_different_numbers.txt 0.48680506023116343
	 * all_high_numbers.txt 0.0 all_low_numbers.txt 0.0 all_random_numbers.txt
	 * 0.4822635430965106 }<- as it should be
	 * 
	 * entropy values { all_different_numbers.txt 2.5649493574615376
	 * all_high_numbers.txt 0.0 all_low_numbers.txt 0.0 all_random_numbers.txt
	 * 2.245035274126178 }<- as it should be
	 * 
	 * normalized entropy values { all_different_numbers.txt 2.5649493574615376
	 * all_high_numbers.txt 0.0 all_low_numbers.txt 0.0 all_random_numbers.txt
	 * 2.245035274126178 }<- as it should be
	 * 
	 * normalized cv values { all_different_numbers.txt 0.5104455570055113
	 * all_high_numbers.txt 0.0 all_low_numbers.txt 0.0 all_random_numbers.txt
	 * 0.4961946816619952 }<- as it should be
	 */
	public void test_tdt_raw_lesst_20() {
		String directory = "S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\tdt_lesst_20";
		String sample_name = "tdt_lesst_20.txt";
		sh.findEntropy_CV_NormEntropy_NormCV_from_tabdelimitedtext_raw_data(
				directory, sample_name, 1, 20, 10);
	}

	/*
	 * normalized cv values { all_different_numbers.txt 0.5104455570055113
	 * all_high_numbers.txt 0.0 all_low_numbers.txt 0.0 all_random_numbers.txt
	 * 0.4961946816619952 }<- as it shoud be normalized entropy values {
	 * all_different_numbers.txt 2.5649493574615376 all_high_numbers.txt 0.0
	 * all_low_numbers.txt 0.0 all_random_numbers.txt 2.245035274126178 }<- as
	 * it should be
	 */
	public void test_tdt_normalized_lesst_20() {
		String directory = "S:\\Research\\Cancer_Eradication\\Discovering tumor specific antigens\\entropy\\6-13-13\\tdt_lesst_20_normalized";
		String sample_name = "tdt_lesst_20_normalized.txt";
		sh.findEntropy_CV_NormEntropy_NormCV_from_tabdelimitedtext_normalized_data(
				directory, sample_name, 1, 20, 10);

	}

	/*
	 * the values look like they are what they are supposed to be entropy: 6.21
	 * and excel gave 6.27 (probably just rounding differences) cv: 5.707 and
	 * excel gave 5.707 normalized entropy: 5.21 and excel gave 5.26 normalized
	 * cv: 5.713 and excel gave 5.713
	 */
	public void large_gpr_test(String directory, String sample_name) {
		sh.findEntropy_CV_NormEntropy_NormCV_from_one_gpr(directory,
				sample_name, 1, 65535, 100);
	}

	/*
	 * Try to use the output table method
	 */
	public void testOutputTable() {
		String[] column_titles = { "cv", "entropy", "sd" };
		ArrayList cv = new ArrayList(
				useful_tools.stringWithCommasToArrayList("2,5,8"));
		ArrayList entropy = new ArrayList(
				useful_tools.stringWithCommasToArrayList("10,2,6"));
		ArrayList sd = new ArrayList(
				useful_tools.stringWithCommasToArrayList("35,62,12"));
		OutputHandler oh = new OutputHandler();
		oh.outputTableOfValues(column_titles,
				"C:\\Users\\kwhittem\\Desktop\\temp",
				"test_table_creation.txt", cv, entropy, sd);
	}

	public void testModeFunction() {
		ArrayList numbers = useful_tools
				.stringWithCommasToArrayList("6.8,2.3,2.2,2.8,2.9,2.2,2.2,3.5");
		System.out.println(useful_tools.mode(numbers));
		// the output was 2.2 as it should be.
	}

	/*
	public void test_find_summary_numbers_one_gpr() {
		sh_new.find_summary_numbers_one_gpr(
				"F:\\kurt\\storage\\CIM Research Folder\\DR\\2013\\9-16-13\\entropy\\test1",
				"test1 9-16-13", "F532 Median", true, 1, 65535, 10000);
		/*
		 * Here's the output from running this. name entropy entropy_normalized
		 * cv cv_normalized stdev stdev_normalized mean mean_normalized median
		 * mean_normalized median median_normalized mode mode_normalized test1
		 * 9-16-13 1.945910149055313 1.4698809333159983 0.3463060072388747
		 * 0.34605385407009376 2051.4673145963457 28.673033622950626
		 * 5923.857142857143 82.85714285714286 7125.0 100.0 [3215.0, 3500.0,
		 * 4719.0, 7125.0, 7172.0, 7753.0, 7983.0] [100.0]
		 
	}

	public void test_find_summary_numbers_one_small_gpr() {
		sh_new.find_summary_numbers_one_gpr("F:" + File.separator + "kurt"
				+ File.separator + "storage" + File.separator
				+ "CIM Research Folder" + File.separator + "DR"
				+ File.separator + "2013" + File.separator + "9-16-13"
				+ File.separator + "entropy" + File.separator + "test2",
				"test2 9-18-13", "F532 Median", true, 1, 20, 10);

	}

	public void test_find_summary_numbers_one_gpr092413d1626() {
		sh_new.find_summary_numbers_one_gpr(
				Paths.get(
						"F:\\kurt\\storage\\CIM Research Folder\\DR\\2013\\9-16-13\\entropy\\test7")
						.toString(), "test2 9-18-13.gpr", "F532 Median", true,
				1, 20, 10);
	}

	public void test_folder_of_gprs() {
		sh_new.find_summary_numbers_from_folder_of_gprs(
				Paths.get(
						"F:\\kurt\\storage\\CIM Research Folder\\DR\\2013\\9-16-13\\entropy\\test3")
						.toString(), "F532 Median", false, 1, 20, 10);
	}

	public void test_folder_of_gprs092413d1734(String[] args) {
		/*
		 * sh_new.find_summary_numbers_from_folder_of_gprs(Paths.get(
		 * "F:\\kurt\\storage\\CIM Research Folder\\DR\\2013\\9-16-13\\entropy\\test8"
		 * ).toString(), "F532 Median", true, 1, 20, 10);
		 *
		String return_string = useful_tools.getTime() + "\r\n";
		String directory = args[1];
		String call_details = "";
		if (args[0].equals("find_summary_numbers_from_folder_of_gprs")) {
			call_details = "find_summary_numbers_from_folder_of_gprs_"
					+ args[1];
			sh_new.find_summary_numbers_from_folder_of_gprs(args[1], args[2],
					Boolean.valueOf(args[3]).booleanValue(),
					Integer.valueOf(args[4]).intValue(),
					Integer.valueOf(args[5]).intValue(),
					Integer.valueOf(args[6]).intValue());
		}
		return_string += useful_tools.getTime();
		call_details = call_details.replaceAll(":", "C");
		call_details = call_details.replaceAll(Pattern.quote(File.separator),
				"S");
		useful_tools.createTextFile(directory, "runtime_info_" + call_details
				+ ".txt", return_string);

	}

	public void test_tabDelimitedText092013d1132() {

		sh_new.find_summary_numbers_from_tabdelimitedtext_raw_data(
				Paths.get(
						"F:\\kurt\\storage\\CIM Research Folder\\DR\\2013\\9-16-13\\entropy\\test4")
						.toString(), "test_data_9-20-13d1124.txt", 1, 20, 10,
				true, 0, 1, 2, 1, 2, 1);
	}

	public void test_tabDelimitedTextNormalized092013d1507() {
		sh_new.find_summary_numbers_from_tabdelimitedtext_normalized_data(
				Paths.get(
						"F:\\kurt\\storage\\CIM Research Folder\\DR\\2013\\9-16-13\\entropy\\test5")
						.toString(), "test_data_9-20-13d1124.txt", 1, 20, 10,
				true, 0, 1, 2, 1, 2, 1);

	}

	public void test2_092013d1547() {
		sh_new.find_summary_numbers_one_gpr(
				Paths.get(
						"F:\\kurt\\storage\\CIM Research Folder\\DR\\2013\\9-16-13\\entropy\\test6\\test2")
						.toString(), "test2 9-18-13", "F532 Median", true, 1,
				20, 10);

	}

	public void test3_092013d1547() {
		sh_new.find_summary_numbers_one_gpr(
				Paths.get(
						"F:\\kurt\\storage\\CIM Research Folder\\DR\\2013\\9-16-13\\entropy\\test6\\test3")
						.toString(), "test3 9-18-13", "F532 Median", true, 1,
				20, 10);
	}
*/
	public void testFileSearch092013d1648() {
		// this method is obsolete now
		// ArrayList list_of_matching_files =
		// useful_tools.findAllFilesInDirectoryAndSubdirectoriesMatchingThisName("table_of_summary_numbers.txt");
		// System.out.println(list_of_matching_files);
	}

	public void testFileCombination0923131027() {
		// this method is obsolete now
		// ArrayList list_of_matching_files =
		// useful_tools.findAllFilesInDirectoryAndSubdirectoriesMatchingThisName("table_of_summary_numbers.txt");
		// useful_tools.combineAllTablesIntoOneTable(list_of_matching_files,
		// "F:\\kurt\\storage\\CIM Research Folder\\DR\\2013\\9-16-13\\entropy\\test6","complete_table_9-23-13.txt");

	}

	public void testPatternNotSurroundedBySurrouningPattern() {
		String original_string = "test2 9-18-13, 1.5498260458782016, 1.5498260458782016, 0.3333333333333333, 0.3533431298606758, 2.0, 2.9277002188455996, 6.0, 8.285714285714286, 7.0, 10.0, [8.0, 7.0], 7.5, [11.0, 10.0], 10.5, 3.0, 4.0, 8.0, 11.0";
		// String new_string =
		// useful_tools.replacePatternNotSurroundedBySurroundingPattern(original_string,
		// ", ", "q", "[\\[\\]]");
		String new_string = useful_tools
				.replacePatternNotSurroundedBySurroundingPattern(
						original_string, ", ", "dogs", "[\\[\\]]");
		System.out.println(new_string);
	}

	public void testPatternNotSurroundedBySurrouningPattern092413d1340() {
		String original_string = "test2 9-18-13, 1.5498260458782016, 1.5498260458782016, 0.3333333333333333, 0.3533431298606758, 2.0, 2.9277002188455996, 6.0, 8.285714285714286, 7.0, 10.0, [8.0, 7.0], 7.5, [11.0, 10.0], 10.5, 3.0, 4.0, 8.0, 11.0";
		String new_string = useful_tools
				.replacePatternNotSurroundedBySurroundingPattern(
						original_string, ", ", "\t", "[\\[\\]]");
		System.out.println(new_string);
	}

	public void testNewSummaryMeasures092713d1106() {
		CalculationHandler ch = new CalculationHandler();
		ArrayList list = useful_tools
				.stringWithCommasToArrayList("500.3917234, 500.5751251, 500.672305, 500.7661147, 500.6772589, 499.9702976, 499.8386338, 500.0316725, 500.8332117, 500.9077526, 500.7550191, 500.8506918, 500.1316633, 500.5744003, 500.8166301, 500.5367321, 499.9153591, 500.2682707, 500.1667916, 500.5970729, 500.4483001, 499.3285012, 499.9562934, 500.1571456, 500.9486997, 500.7908068, 500.4623512, 499.843717, 500.4689319, 500.3733909, 500.5652717, 500.6916572, 499.8249215, 499.9089793, 500.6694149, 499.895742, 499.7915863, 500.125652, 499.1598809, 500.3398785, 500.6235984, 500.8782232, 500.736616, 500.1246697, 500.7349748, 499.6790629, 500.6056354, 500.2186754, 500.3277348, 500.1488291, 500.7953598, 499.7250754, 500.5030546, 500.2090929, 499.9668074, 499.7610796, 500.1964768, 500.8286604, 500.406173, 500.3008895, 500.0737466, 500.2091524, 500.6340189, 500.3786198, 500.1532252, 500.0615003, 500.3095212, 499.4398849, 500.7215175, 500.6548867, 500.6696331, 500.9981722, 500.3395971, 500.1103759, 499.3656867, 500.724592, 500.6846588, 500.7234281, 500.6065425, 500.9627658, 500.3106328, 500.2131401, 499.9306016, 500.5165701, 500.0441957, 500.2608334, 499.6758237, 500.4366925, 499.9332537, 499.9250351, 499.8734178, 500.4458012, 500.4569907, 500.6572512, 499.3168389, 499.8599726, 499.211784, 500.6205336, 500.7833425, 500.4936034, 500.2929669, 499.7700271, 499.930937, 500.6721333, 500.1859293, 499.552784, 500.5346672, 499.4309079, 500.1539773, 500.5905303, 500.8234138, 500.6725023, 500.4709132, 500.6296228, 500.1780068, 500.3494664, 500.0424169, 500.3042912, 500.1529768, 499.5925979, 500.2277825, 499.9126159, 500.1592047, 500.574941, 500.4379365, 500.3090078, 500.6776981, 500.3509234, 500.5658375, 500.3137285, 500.6665305, 500.4607934, 499.5538437, 500.4432506, 500.4879721, 500.9206885, 500.7071402, 500.116185, 500.8865479, 500.242032, 500.2750774, 500.5624795, 500.5873021, 499.5867613, 500.8733676, 500.7111643, 499.3881567, 500.9016418, 500.1319719, 500.3169748, 500.4804673, 500.8355918, 500.1680095, 500.9572458, 499.414129, 499.4212649, 500.4062622, 499.5608864, 500.7702302, 500.7834757, 500.2480679, 500.5754541, 500.1913949, 499.9094727, 500.6324394, 500.5218631, 499.9117982, 500.6120031, 499.8093171, 500.301554, 499.9184458, 500.9545696, 500.2153854, 500.3247454, 500.0296649, 500.7272996, 499.7922169, 500.7821681, 499.8654904, 500.8462955, 500.7358754, 499.9649851, 500.4266306, 500.1083452, 499.7358048, 500.9495429, 500.3532592, 500.2793056, 499.9473487, 500.7791725, 500.5575197, 500.7914091, 500.0748387, 500.4205993, 499.6337305, 499.8473815, 500.3370656, 500.5452377, 500.4158533, 500.1443358, 500.670412, 499.8048202, 499.6055467, 500.8798218, 500.8326435, 500.2064904, 500.7845091, 500.9643405, 500.487281, 500.7824308, 499.4197705, 499.6485561, 500.2949685, 500.9630048, 500.3605381, 500.4089037, 500.5595645, 500.00235, 500.0460119, 500.7300739, 500.6864629, 500.4022475, 499.8770019, 500.0175901, 500.7048735, 500.6920894, 500.2609766, 500.6175599, 500.2542499, 499.1546764, 500.6884725, 500.8889048, 500.8661534, 500.5040776, 499.7781124, 500.1303997, 499.5438302, 500.4546671, 499.7729189, 499.3080787, 500.5899093, 500.6885398, 500.4883892, 500.0479431, 500.0883053, 500.6411819, 500.8547794, 500.4735365, 499.57885, 500.1371056, 499.8730043, 500.7894331, 499.8520316, 500.2978486, 500.2359037, 500.6979097, 500.3823271, 499.3709388, 500.8786885, 500.6459692, 499.6993155, 500.8189725, 499.7774735, 500.6766473, 499.832898, 500.7387863, 500.2161769, 500.5438444, 500.2962102, 500.3717623, 499.8150883, 500.4817056, 500.9580456, 500.3128363, 499.3918625, 499.9207201, 499.7709363, 500.3036493, 500.8476039, 500.720209, 500.1540692, 500.7367995, 500.2946035, 500.1759923, 499.516026, 500.3172096, 500.9588117, 500.3883766, 500.4093876, 500.4376974, 499.9179786, 500.8735963, 500.5687089, 499.4831998, 500.5959552, 500.833975, 500.0490357, 500.3655519, 500.9479306, 500.7416689, 499.4466558, 500.1631049, 500.0037852, 500.4452044, 500.5041591, 500.5482757, 500.1204992, 499.9141322, 500.9581437, 500.6704242, 499.9653781, 500.5263352, 500.8394433, 500.3725995, 500.654633, 500.8122853, 500.8442453, 499.6177775, 500.8615968, 500.0230697, 500.5965651, 500.508775, 500.7743983, 500.2719829, 500.0438859, 500.176863, 500.841025, 500.1915214, 500.6374663, 500.2108044, 499.4488972, 499.7600929, 500.7276838, 500.3885944, 500.7883108, 500.9356791, 499.9234487, 500.5918174, 500.3838633, 499.5366609, 500.5809167, 500.8466357, 500.0227025, 500.694603, 500.6128196, 499.9182807, 500.9500225, 500.1786157, 500.8057129, 500.2478098, 499.7344552, 500.6098685, 500.7738702, 500.4235909, 500.7677348, 500.6855806, 499.736115, 500.7947628, 500.6491169, 500.554679, 499.5290901, 500.8712774, 500.6276637, 500.7448434, 500.1891717, 500.3283723, 500.4207274, 500.4451516, 500.4689544, 500.6768306, 500.3571312, 500.6284343, 500.114582, 499.6960603, 500.6494044, 500.5000224, 500.4954959, 500.9218073, 500.7693929, 500.6634267, 499.6968182, 500.2973639, 500.726941, 500.8031407, 500.4778985, 500.2634856, 499.9309844, 500.724834, 500.0919171, 500.8943633, 499.8568751, 500.6309752, 500.4155168, 500.8171787, 500.8915921, 500.8512398, 500.7533519, 499.337425, 499.6579072, 500.849115, 499.9598061, 500.5265123, 500.845484, 500.3351356, 500.5981719, 499.3801706, 500.4924514, 499.5698882, 500.9254884, 500.6824183, 500.2633544, 499.6700132, 500.8553899, 500.7923105, 500.7010598, 499.8665352, 500.4575845, 500.4083998, 500.4326521, 500.6447608, 500.8348079, 500.7692705, 500.0417751, 500.2775951, 500.4358273, 500.2710849, 500.5808656, 499.6864622, 500.4612688, 500.4895529, 500.0568581, 499.9669384, 500.7580516, 500.8990228, 500.5726707, 499.500028, 500.6315487, 500.0530467, 500.7189035, 499.0731407, 500.2168718, 500.5056937, 499.8762118, 499.8722847, 500.2838965, 500.3505868, 499.4340167, 500.1452382, 500.6879157, 499.7484489, 500.5089102, 500.2507824, 499.7243454, 500.9629751, 500.3832456, 500.4287842, 500.8358252, 500.5698134, 500.6647205, 499.8671834, 500.0930974, 500.7798805, 500.4164154, 500.6853578, 500.1133846, 499.9239126, 500.618342, 500.2659818, 500.1649334, 500.5752537, 500.5673759, 500.293104, 500.6220118, 500.4092133, 500.4231296, 500.5446423, 500.187704, 500.3706877, 500.8190882, 500.5274737, 500.5176926, 499.9049773, 500.5092833, 500.6253885, 499.3704993, 499.7333739, 500.2547553, 500.1779877, 499.3586263, 500.4851636, 500.6733819, 500.6548495, 500.6488949, 500.687689, 500.8296512, 499.906711, 499.5306384, 499.9532162, 500.4484347, 500.5874634");
		// ArrayList list =
		// useful_tools.stringWithCommasToArrayList("0.391723353, 0.57512513, 0.672305045, 0.766114745, 0.677258938, -0.029702449, -0.161366176, 0.031672483, 0.8332117, 0.907752552, 0.755019076, 0.850691772, 0.131663276, 0.574400279, 0.816630064, 0.536732093, -0.084640895, 0.268270706, 0.166791571, 0.597072875, 0.44830006, -0.671498804, -0.043706581, 0.157145593, 0.948699717, 0.79080682, 0.462351246, -0.156282991, 0.46893194, 0.373390881, 0.565271702, 0.691657222, -0.175078509, -0.09102073, 0.669414879, -0.10425796, -0.20841374, 0.125651968, -0.84011907, 0.339878472, 0.623598379, 0.878223184, 0.73661605, 0.12466968, 0.734974753, -0.320937054, 0.605635444, 0.218675362, 0.327734831, 0.148829147, 0.795359753, -0.27492458, 0.503054631, 0.209092892, -0.033192555, -0.238920445, 0.196476786, 0.828660352, 0.406172992, 0.300889547, 0.073746584, 0.209152377, 0.634018857, 0.378619805, 0.153225217, 0.061500302, 0.309521159, -0.560115058, 0.721517541, 0.654886707, 0.669633056, 0.998172202, 0.339597074, 0.110375909, -0.634313271, 0.724592025, 0.684658802, 0.723428118, 0.606542549, 0.962765787, 0.310632796, 0.213140144, -0.069398383, 0.516570143, 0.044195742, 0.260833384, -0.324176293, 0.436692529, -0.066746275, -0.074964882, -0.126582246, 0.445801158, 0.456990676, 0.657251214, -0.683161057, -0.14002738, -0.788215957, 0.620533627, 0.783342517, 0.493603381, 0.292966892, -0.229972898, -0.069062956, 0.672133266, 0.185929348, -0.447216018, 0.534667194, -0.569092101, 0.153977323, 0.590530291, 0.823413815, 0.672502315, 0.470913236, 0.629622782, 0.178006791, 0.349466376, 0.042416882, 0.304291244, 0.152976803, -0.407402075, 0.227782454, -0.087384089, 0.159204653, 0.574940964, 0.4379365, 0.309007791, 0.67769811, 0.350923405, 0.565837475, 0.313728464, 0.666530473, 0.460793362, -0.446156314, 0.443250589, 0.487972082, 0.920688479, 0.707140173, 0.116184969, 0.886547947, 0.242031956, 0.275077428, 0.562479489, 0.587302107, -0.41323874, 0.873367613, 0.711164257, -0.611843334, 0.901641811, 0.131971927, 0.316974849, 0.480467319, 0.835591792, 0.168009496, 0.95724582, -0.585871018, -0.578735121, 0.40626222, -0.439113598, 0.770230215, 0.783475661, 0.248067863, 0.575454051, 0.191394892, -0.090527342, 0.632439388, 0.52186306, -0.088201815, 0.61200314, -0.190682875, 0.301553989, -0.081554168, 0.954569565, 0.215385353, 0.324745435, 0.029664917, 0.727299561, -0.207783119, 0.782168058, -0.134509588, 0.846295469, 0.735875383, -0.035014947, 0.426630629, 0.108345156, -0.264195168, 0.949542876, 0.353259184, 0.279305587, -0.052651306, 0.77917245, 0.557519688, 0.791409139, 0.074838701, 0.420599295, -0.366269477, -0.152618532, 0.337065627, 0.54523771, 0.415853264, 0.144335791, 0.670412015, -0.195179795, -0.394453293, 0.879821791, 0.83264351, 0.206490412, 0.784509112, 0.964340479, 0.487280984, 0.782430759, -0.580229512, -0.351443914, 0.294968457, 0.963004829, 0.360538087, 0.408903675, 0.559564487, 0.002350041, 0.046011888, 0.730073936, 0.686462933, 0.402247517, -0.1229981, 0.017590142, 0.704873472, 0.692089351, 0.260976621, 0.617559874, 0.254249908, -0.845323604, 0.68847248, 0.888904847, 0.866153351, 0.5040776, -0.221887635, 0.130399704, -0.456169793, 0.454667146, -0.227081106, -0.691921291, 0.589909257, 0.688539765, 0.488389156, 0.047943139, 0.088305265, 0.641181868, 0.854779401, 0.473536497, -0.421150013, 0.137105592, -0.126995713, 0.789433146, -0.147968368, 0.297848561, 0.235903745, 0.697909723, 0.382327122, -0.62906122, 0.878688474, 0.645969163, -0.30068449, 0.818972523, -0.222526518, 0.676647302, -0.167102027, 0.738786305, 0.216176891, 0.543844392, 0.296210188, 0.371762292, -0.184911694, 0.481705555, 0.958045592, 0.312836289, -0.608137524, -0.079279909, -0.229063675, 0.303649252, 0.847603915, 0.72020898, 0.154069199, 0.736799493, 0.294603539, 0.175992309, -0.483973999, 0.317209612, 0.958811704, 0.388376554, 0.409387645, 0.437697387, -0.082021436, 0.873596319, 0.568708862, -0.516800183, 0.595955238, 0.83397503, 0.049035705, 0.365551939, 0.947930641, 0.741668889, -0.553344222, 0.16310489, 0.003785215, 0.445204424, 0.504159127, 0.548275695, 0.120499244, -0.085867767, 0.958143655, 0.670424183, -0.034621912, 0.526335233, 0.839443259, 0.372599478, 0.654632952, 0.812285274, 0.844245271, -0.382222463, 0.861596785, 0.023069693, 0.596565119, 0.50877502, 0.774398269, 0.271982877, 0.04388591, 0.176863016, 0.841024954, 0.191521367, 0.637466278, 0.21080436, -0.551102767, -0.239907059, 0.72768376, 0.388594415, 0.78831084, 0.935679103, -0.076551304, 0.591817442, 0.383863251, -0.463339109, 0.580916742, 0.846635688, 0.022702532, 0.694602951, 0.612819605, -0.081719266, 0.95002254, 0.178615732, 0.80571289, 0.247809753, -0.265544847, 0.609868457, 0.773870212, 0.423590908, 0.767734759, 0.685580641, -0.263884984, 0.794762831, 0.649116914, 0.554678986, -0.470909852, 0.87127739, 0.627663727, 0.744843412, 0.189171725, 0.328372345, 0.420727433, 0.445151562, 0.468954444, 0.676830564, 0.3571312, 0.628434256, 0.114582017, -0.303939678, 0.649404428, 0.500022427, 0.495495917, 0.921807261, 0.769392876, 0.663426735, -0.303181808, 0.297363866, 0.726940975, 0.803140727, 0.477898469, 0.263485606, -0.06901558, 0.724834025, 0.09191708, 0.894363263, -0.143124914, 0.63097517, 0.415516824, 0.817178703, 0.891592082, 0.851239797, 0.753351921, -0.662574959, -0.342092845, 0.84911502, -0.040193905, 0.526512259, 0.84548395, 0.335135593, 0.598171863, -0.619829418, 0.492451426, -0.430111843, 0.925488392, 0.682418254, 0.263354409, -0.329986764, 0.855389938, 0.792310525, 0.70105983, -0.133464784, 0.457584477, 0.408399797, 0.432652127, 0.644760769, 0.834807946, 0.769270453, 0.041775087, 0.27759506, 0.435827284, 0.271084885, 0.580865637, -0.313537788, 0.4612688, 0.489552851, 0.056858087, -0.033061619, 0.758051633, 0.899022818, 0.572670744, -0.49997204, 0.631548715, 0.053046749, 0.718903454, -0.926859267, 0.216871759, 0.505693655, -0.123788217, -0.127715346, 0.283896488, 0.350586806, -0.565983283, 0.1452382, 0.68791574, -0.251551066, 0.508910202, 0.250782409, -0.275654562, 0.962975078, 0.383245592, 0.428784189, 0.83582523, 0.56981335, 0.664720517, -0.132816556, 0.093097431, 0.779880467, 0.416415371, 0.685357773, 0.113384631, -0.07608743, 0.618341981, 0.265981768, 0.164933389, 0.575253697, 0.567375944, 0.293104047, 0.622011844, 0.40921335, 0.423129632, 0.544642338, 0.187703959, 0.37068772, 0.81908822, 0.527473656, 0.517692577, -0.09502274, 0.509283329, 0.625388485, -0.629500656, -0.266626078, 0.254755322, 0.177987676, -0.641373742, 0.485163646, 0.673381884, 0.654849546, 0.648894861, 0.687688968, 0.829651236, -0.093288973, -0.469361643, -0.046783812, 0.448434671, 0.5874634377");
		// sample data from here
		// http://pirate.shu.edu/~wachsmut/Teaching/MATH1101/Descriptives/box.html
		// http://pirate.shu.edu/~wachsmut/Teaching/MATH1101/00-data/distribution-data.xls
		// note that in one list I added 500 to make all of the values positive
		double mean = useful_tools.getMean(list);
		double stdev = useful_tools.getStdDev(list);

		/*
		 * System.out.println("skew: "+ch.Skew(list, mean, stdev)+"\r\n"); //the
		 * value from the skew was -0.6484780258591187 which was the same as the
		 * manual calculation I obtained //manual calculation here
		 * //"F:\kurt\storage\CIM Research Folder\DR\2013\9-27-13\example
		 * summary statistic calculations 9-27-13.xlsx"
		 * System.out.println("95th percentile: "+ch.percentile(list,
		 * 0.95)+"\r\n"); //the 95th percentile value was 500.880158105 which
		 * matches the excel value
		 * System.out.println("5th percentile: "+ch.percentile(list,
		 * 0.05)+"\r\n"); //the 5th percentile value was 499.5152261 which
		 * matches the excel value
		 * System.out.println("kurtosis: "+ch.kurtosis(list, mean,
		 * stdev)+"\r\n"); //the kurtosis was -0.3352291007583035 which matches
		 * the excel value
		 */

	}

	public void testPlaceFilesInTheirOwnDirectory() {
		sh_new.placeFilesInFolderIntoTheirOwnFolder(Paths.get(
				"C:\\Users\\kwhittem\\Desktop\\temp\\test").toString());
	}

	public void testFindFiles() {
		ArrayList list_of_files_to_find = useful_tools.stringWithCommasToArrayList("10009942_Bot_N13(135).gpr, 10010059_Batch522_Bot_177.gpr, 10010059_Batch522_Top_187.gpr, 10010060_Batch522_Bot_163.gpr, 10010060_Batch522_Top_182.gpr, 10010136_top_N-3(33).gpr, 10010474_Batch519_Bot_195.gpr, 10010474_Batch519_Top_194_02122013.gpr, 10010569_Batch498_Top_Norm154.gpr, 10010570_Batch498_Bot_Norm153.gpr, 10010571_Batch498_Top_Norm157.gpr, 10010573_Batch498_Bot_Norm156.gpr, 10010633_Batch507_Bot_162.gpr, 10010633_Batch507_Top_168.gpr, 10010706_Top_N-6(134).gpr, 10010718_Bot_N-20(151).gpr, 10010720_Top_N-4(43) K.gpr, 10010724_top_N-12(145).gpr, 10010728_Batch507_Bot_210.gpr, 10010744_Batch507_Bot_161.gpr, 10010744_Batch507_Bot_161.gpr, 10010744_Batch507_Top_185.gpr, 10010785_top_N-8(138).gpr, 10011031_Batch519_Bot_181.gpr, 10011031_Batch519_Top_186_02132013.gpr, 10011037_Top_N-10(142).gpr, 10011040_top_N-15(147).gpr, 10011292_top_N-19(152).gpr, 10011509_Batch522_Top_206.gpr, 10011585_Batch498_Bot_Norm157_10192012.gpr, 10011591_Batch498_Bot_Norm149.gpr, 10011601_Batch498_Bot_Norm159.gpr, 10016380_Batch554_Bot_201.gpr, 10016380_Batch554_Top_200.gpr, 10016382_Batch554_Top_ND160.gpr, 10016451_Batch545_Bot_191.gpr, 10016451_Batch545_Top_190.gpr, 10016463_Batch542_Bot_171.gpr, 10016463_Batch542_Top_165.gpr, 10016465_Batch542_Bot_212.gpr, 10016465_Batch542_Top_211.gpr, 10016466_Batch542_Bot_205.gpr, 10016836_Batch563_Bot_180.gpr, 10016836_Batch563_Top_188.gpr, 10017007_Batch550_Bot_167.gpr, 10017007_Batch550_Top_183.gpr, 10017089_Batch545_Bot_197.gpr, 10017089_Batch545_Top_196.gpr, 10017134_Batch548_Bot_176.gpr, 10017134_Batch548_Top_164.gpr, 10017248_Batch566_Bot_172.gpr, 10017248_Batch566_Top_184.gpr, 10017556_Batch548_Top_202.gpr, 10017591_Batch550_Top_175.gpr, 10017768_Batch563_Bot_214.gpr, 10017768_Batch563_Top_213.gpr, 10017798_Batch548_Bot_178.gpr, 10017798_Batch548_Top_173.gpr, 10017852_Batch550_Top_198.gpr, 10017933_Batch554_Bot_193.gpr, 10017933_Batch554_Top_192.gpr, 393429_normals_bot#100.gpr");
		for(int i=0; i<list_of_files_to_find.size(); i++)
		{
			ArrayList list_of_locations = useful_tools.findAllFilesInDirectoryAndSubdirectoriesMatchingThisName(Paths.get("S:\\Administration\\PeptideArrayCore").toString(), list_of_files_to_find.get(i).toString());
			System.out.println(list_of_locations);
		}
		
		
	}
	
	public void testValueExtractionFromGPR_100513d1536()
	{
		DataPreparationClass dpc = new DataPreparationClass();
		dpc.extractDataFromGPRFile("F:\\kurt\\storage\\CIM Research Folder\\DR\\2013\\10-5-13\\extraction_test", "oneu330kutest"+".gpr", "F532 Median");
		System.out.println("The data is now finished being extracted.");
	}
	
	public void testOneGPR_100713d1600()
	{
		sh_new.find_summary_numbers_one_gpr("F:\\kurt\\storage\\CIM Research Folder\\DR\\2013\\10-7-13\\code test 10-7-13\\one gpr", "oneu330kutest", "F532 Median");
	}
	public void testFolderOfGPRs_100713d1645()
	{
		sh_new.find_summary_numbers_from_folder_of_gprs("F:\\kurt\\storage\\CIM Research Folder\\DR\\2013\\10-7-13\\code test 10-7-13\\folder of gprs", "F532 Median");
	}
	
	public void testTableOfGPRs_100813d0936()
	{
		sh_new.find_summary_numbers_from_tabdelimitedtext_raw_data("F:\\kurt\\storage\\CIM Research Folder\\DR\\2013\\10-7-13\\code test 10-7-13\\table of data non-normalized test", "table_of_data.txt", 2, 1, 2, 1, 2, 3);
	}
	
	public void testTableOfGPRsForNormalizedData_100813d0950()
	{
		sh_new.find_summary_numbers_from_tabdelimitedtext_normalized_data("F:/kurt/storage/CIM Research Folder/DR/2013/10-7-13/code test 10-7-13/table of data normalized test", "table_of_data.txt", 2, 5, 6, 5, 6, 3);
	}
	
	public void testOutputGPRsAsTable(String directory, String column_title_to_extract)
	{
		sh_new.outputFolderOfGPRFilesAsTable(directory, column_title_to_extract);
	}
}
