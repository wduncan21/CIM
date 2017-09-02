import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class UsefulTools {

	private String tab_delimited_text = "";
	private int max_columns = 0;
	private int max_rows =0;
	private String[][] sa_cells;
	
	
	UsefulTools()
	{
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UsefulTools useful_tools = new UsefulTools();
		
		//this is just temporary code to test stuff
		
		
		
	}
	
	private void testMedianFunction()
	{
		/*
		 * 
		 */
		UsefulTools useful_tools = new UsefulTools();
		String data = useful_tools.storeTextFiletoString(Paths.get("S:"+File.separator+"Research"+File.separator+"Cancer_Eradication"+File.separator+"Discovering tumor specific antigens"+File.separator+"entropy"+File.separator+"3-1-13"+File.separator+"sample_analysis"+File.separator+"CIM10K Dogs Median Norm"+File.separator+"median_test.txt").toString());
		useful_tools.setTabDelimitedText(data);
		ArrayList list = useful_tools.extractColumn(0);
		/*
		double d = 2;
		ArrayList test = new ArrayList();
		test.add(d);
		
		System.out.println(test.get(0).getClass());
		*/
		
		System.out.println(list);
		//useful_tools.convertArrayListOfStringsToDoubles(list);
		System.out.println(useful_tools.Median(list));
	}
	
	//this function accepts an arraylist containing arraylists which hold numbers
	//the function will then return an arraylist containing only the numbers considered "consistent"
	public ArrayList findConsistentNumbers(ArrayList list_of_lists)
	{
		double threshold = 1;
		int occurrence_frequency = 20;
		ArrayList consistentList = new ArrayList();
		ArrayList list_of_matches = new ArrayList();
		ArrayList occurrences = new ArrayList();
		//first sort all of the lists
		for(int i=0; i<list_of_lists.size(); i++)
		{
			if(list_of_lists.get(i) instanceof ArrayList)
			{
				Collections.sort((ArrayList)list_of_lists.get(i));
			}
			else
			{
				break;
			}
		}
		//perform these operations for every arraylist
		for(int i=0; i<list_of_lists.size(); i++)
		{
			if(list_of_lists.get(i) instanceof ArrayList)
			{
				ArrayList current_list = (ArrayList)list_of_lists.get(i);
				for(int j=0; j<current_list.size(); j++)
				{
					boolean match = false;
					//note I changed k=i+1 to k=0 here to include more similar numbers
					for(int k =0; k<list_of_lists.size(); k++) //compare the item in the current list with the next list
					{
						ArrayList compare_list = (ArrayList)list_of_lists.get(k);
						for(int l=0; l<compare_list.size(); l++)
						{
							double compare_number = -1;
							//don't match a number with itself in the same list
							if(i!=k)
							{
								compare_number = Double.valueOf(compare_list.get(l).toString()).doubleValue();
							}
							double current_number = Double.valueOf(current_list.get(j).toString()).doubleValue();
							//if the current number in the compare list is too large, then break out of this loop
							if(compare_number - current_number>threshold)
							{
								break;
							}
							if(compare_number==current_number || ((compare_number-current_number)<threshold && (compare_number-current_number)>0) || ((current_number-compare_number)<threshold && (current_number-compare_number)>0) )
							{
								//if there hasn't been a match before, add this number to the list of matches
								//set the number of occurrences to 1 so far
								if(match==false)
								{
									list_of_matches.add(current_list.get(j));
									occurrences.add(Integer.valueOf(1));
									match = true;
								}
								else
								{
									//otherwise just increase the number of occurrences
									Integer integer = Integer.valueOf(Integer.valueOf(occurrences.get(occurrences.size()-1).toString()).intValue()+1);
									
									occurrences.set(occurrences.size()-1, integer);
								}
							}
						}
					}
				}
			}
			else
			{
				break;
			}
		}
		for(int i=0; i<list_of_matches.size(); i++)
		{
			//if the number was found more than x % of the time add it to the consistent list
			if(Double.valueOf(occurrences.get(i).toString()).doubleValue()/list_of_lists.size()*100>occurrence_frequency)
			{
				consistentList.add(list_of_matches.get(i));
			}
		}
		return consistentList;
	}
	
	//this shifts all of the numbers in an arraylist by a certain amount
	public ArrayList shiftNumbers(double amount, ArrayList the_list)
	{
		for(int i=0; i<the_list.size(); i++)
		{
			double d=0;
			try {
				String s = the_list.get(i).toString();
		         d = Double.valueOf(s.trim()).doubleValue();
		      } catch (NumberFormatException nfe) {
		         System.out.println("NumberFormatException: " + nfe.getMessage());
		      }
			the_list.set(i, Double.valueOf(d+amount));
		}
		return the_list;
	}
	
	public ArrayList stringWithCommasToArrayList(String s)
	{
		
		ArrayList return_list = new ArrayList();
		if(s.length()>0)
		{
		String[] input_array = s.split(",");
		for(int i=0; i<input_array.length; i++)
		{
			return_list.add(input_array[i]);
		}
		for(int i=0; i<return_list.size(); i++)
		{
			String s_variable = return_list.get(i).toString();
			if(s_variable.substring(0, 1).equals(" "))
			{
				s_variable = s_variable.substring(1,s_variable.length());
				return_list.set(i, s_variable);
			}
		}
		}
		return return_list;
	}
	
	public ArrayList convertDoublestoArray(double[] s)
	{
		ArrayList the_list = new ArrayList();
		for(int i=0; i<s.length; i++)
		{
			the_list.add(Double.valueOf(s[i]));
		}
		return the_list;
	}
	
	//this is a recursive function that will take an array list that may be made of
    //arraylists and make it one list that is only filled with objects
    public ArrayList flattenList(ArrayList main_list)
    {
        ArrayList new_list = new ArrayList();
        //go through every element in the main_list
        for(int i=0; i<main_list.size(); i++)
        {
            
            ArrayList temporary_list = new ArrayList();
            //if this element is an arraylist
            if(main_list.get(i) instanceof ArrayList)
            {
                //store the first element of the main list into a temporary list
                temporary_list = (ArrayList)main_list.get(i);
                //go through all of the elements in this arraylist that is a sublist of the main list
                for(int j=0; j<temporary_list.size(); j++)
                {
                    //if this element of this sublist is not an array list
                    //we've reached a base case so we should add this single object
                    //to the new list.
                    if(!(temporary_list.get(j) instanceof ArrayList))
                    {
                        new_list.add(temporary_list.get(j));
                    }
                    //if we haven't reached the base case
                    //add this array list to the new_list and then recursively call
                    //the flattenList function to flatten this new list
                    //eventually the base case will be reached.
                    else
                    {
                        new_list.add(temporary_list.get(j));
                        new_list = flattenList(new_list);
                    }
                }
            }
            //if the object wasn't an array list to start with, then just add it to the new list
            else
            {
                new_list.add(main_list.get(i));
            }
                    
        }
        //return the new flattened list we've made
        return new_list;
    } 
    
    public ArrayList removeDuplicates(ArrayList l1)
    {
    	ArrayList l2 = new ArrayList();
    	for(int l1_count=0; l1_count<l1.size(); l1_count++)
    	{
    		boolean has_element=false;
    		for(int l2_count=0; l2_count<l2.size(); l2_count++)
    		{
    			if(l1.get(l1_count).equals(l2.get(l2_count)))
    			{
    				has_element = true;
    			}
    		}
    		if(!has_element)
    		{
    			l2.add(l1.get(l1_count));
    		}
    	}
    	return l2;
    }
    
	//this function returns an arraylist with two numbers the starting index of the string within the sequence and the ending index of the string
	public ArrayList findPositionOfString(String string, String query)
	{
		ArrayList position_data = new ArrayList();
		int start_index = 0;
		
		while(true)
		{
			int current_index = string.indexOf(query, start_index);
			if(current_index>-1)
			{
				position_data.add(Integer.valueOf(current_index+1));
				position_data.add(Integer.valueOf(current_index+query.length()));
				start_index = current_index+1;
			}
			else
			{
				break;
			}
		}
		return position_data;
	}
	
	public boolean approximatelyEquivalent(double first_number, double second_number, double threshold)
	{
		if(first_number==second_number || ((second_number-first_number)<threshold && (second_number-first_number)>0) || ((first_number-second_number)<threshold && (first_number-second_number)>0) )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int getMatches(String input, String match_to_count)
	{
		
		int start_index = 0;
		int count = 0;
		while(true)
		{
			int current_index = input.indexOf(match_to_count, start_index);
			if(current_index>-1)
			{
				start_index = current_index+1;
				count++;
			}
			else
			{
				break;
			}
		}
		return count;
	}
	
	public int getMatchesRegEx(String input, String match_to_count)
	{
		
		int start_index = 0;
		int count = 0;
		while(true)
		{
			try
			{
				String substring = input.substring(start_index, input.length()-1);
				String instance_of_match_to_count = getFirstMatchofRegEx(substring, match_to_count);
				int current_index = input.indexOf(instance_of_match_to_count, start_index);
				
				if(current_index>-1 && !instance_of_match_to_count.equals(""))
				{
					start_index = current_index+1;
					count++;
				}
				else
				{
					break;
				}
			}
			catch(Exception e)
			{
				break;
			}
		}
		return count;
	}
	
	public String getFirstMatchofRegEx(String input_string, String regex)
	{
		Pattern pattern = 
            Pattern.compile(regex);

            Matcher matcher = 
            pattern.matcher(input_string);
            
            String first_match = "";
            boolean found = false;
            while (matcher.find()) {
                if(found ==false)
                {
                	first_match = matcher.group();
                }
                found = true;
            }
            return first_match;
	}
	
	public ArrayList getAllMatchesofRegEx(String input_string, String regex)
	{
		ArrayList return_list = new ArrayList();
		boolean all_found = false;
		while(!all_found)
		{
			String match = getFirstMatchofRegEx(input_string, regex);
			if(match.equals(""))
			{
				all_found=true;
			}
			else
			{
				return_list.add(match);
				int start_position = input_string.indexOf(match);
				input_string = input_string.substring(start_position+1,input_string.length());
			}
		}
		return return_list;
	}
	
	public boolean isMatchofRegEx(String input_string, String regex)
	{
		Pattern pattern = 
            Pattern.compile(regex);

            Matcher matcher = 
            pattern.matcher(input_string);
            
            String first_match = "";
            boolean found = false;
            while (matcher.find()) {
                if(found ==false)
                {
                	first_match = matcher.group();
                }
                found = true;
            }
            return found;
	}
	
	public String generateCodeForClass(String class_name, String variables)
	{
		ArrayList list_variables = stringWithCommasToArrayList(variables);
		return generateCodeForClass(class_name, list_variables);
	}
	
	//each item in the variables ArrayList should be a string with the type and name of the variable
	public String generateCodeForClass(String class_name, ArrayList variables)
	{
		ArrayList type = new ArrayList();
		ArrayList name = new ArrayList();
		String word = "[A-za-z0-9_]+";
		for(int i=0; i<variables.size(); i++)
		{
			String s_variable = variables.get(i).toString();
			String s_type = getFirstMatchofRegEx(s_variable, word + " ");
			s_type = getFirstMatchofRegEx(s_type, word);
			String s_name = getFirstMatchofRegEx(s_variable, " " + word);
			s_name = getFirstMatchofRegEx(s_name, word);
			type.add(s_type);
			name.add(s_name);
			
		}
		String return_string = "public class " + class_name + " {";
		for(int i=0; i<variables.size(); i++)
		{
			return_string+= "\n" + "\t" + "private " + variables.get(i).toString() + ";";
		}
		return_string+="\n\n\tpublic " + class_name + "(" + variables.get(0);
		for(int i=1; i<variables.size(); i++)
		{
			return_string+=", " + variables.get(i);
		}
		return_string+=")\n\t{";
		for(int i=0; i<variables.size(); i++)
		{
			return_string+="\n\t\tthis." + name.get(i) + " = " + name.get(i) + ";";
		}
		return_string+="\n\t}\n\n";
		for(int i=0; i<variables.size(); i++)
		{
			return_string+= "\n" + "\t" + "public void set_" + 
				name.get(i).toString() + "(" + type.get(i).toString() + " " + 
				name.get(i).toString() + ")"
				+ "\n" + "\t" + "{" + "\n" +"\t\t" + "this." + 
				name.get(i).toString() + " = " + name.get(i).toString() + ";"
				+ "\n" + "\t" + "}" + "\n" + "\t" + "public " + type.get(i) + " get_" +
				name.get(i).toString() + "()" + "\n\t{"
				+"\n\t\t" + "return " + name.get(i).toString() + ";" + "\n\t" + "}"
				;
		}
		return_string += "\n}";
		
		
		return return_string;
	}
	
	
	
	/*This function takes in an arraylist consisting of lists.  The first item of each list should be
	 * the name of that list.  This analyze list function then generates a new list containing lists
	 * of every possible combination of all of the lists.
	 */
	public ArrayList analyzeLists(ArrayList lists)
	{
		ArrayList return_list = new ArrayList();
		int number_of_lists = lists.size();
		int number_of_combination_lists = factorial(number_of_lists)+1;
		//in order to explore all permutations of combinations of lists I will
		//simply count up to the number of possible list combinations in binary.
		for(int i=0; i<=number_of_combination_lists; i++)
		{
			String current_combination = Integer.toString(i, 2);
			ArrayList new_list = new ArrayList();
			
			
			for(int list_number=0; list_number<current_combination.length(); list_number++)
			{
				boolean included = false;
				try
				{
					if(current_combination.substring(list_number, list_number+1).equals("1"))
					{
						included = true;
					}
					if(included == true)
					{
						new_list.add( lists.get(current_combination.length()-1-list_number)  );
					}
				}
				catch(Exception e)
				{
					
				}
			}
			
			//now that we have all of the lists that should be combined, let's combine them.
			//When we combine them we should combine them in order from the largest list to the smallest
			//so let's sort them
			//There are better ways to sort, but this is a fairly quick way to think of
			ArrayList sorted_list = new ArrayList();
			boolean sorted = false;
			
			while(!sorted)
			{
				boolean something_was_sorted = false;
				for(int new_list_num=1; new_list_num<new_list.size(); new_list_num++)
				{
					for(int from_beginning=0; from_beginning<new_list_num; from_beginning++)
					{
						int new_list_num_length = ((ArrayList)new_list.get(new_list_num)).size();
						int from_beginning_length = 0;
						from_beginning_length = ((ArrayList)new_list.get(from_beginning)).size();
						if( new_list_num_length>from_beginning_length )
						{
							
							ArrayList temp = new ArrayList( (ArrayList)new_list.get(new_list_num) );
							new_list.remove(new_list_num);
							new_list.add(from_beginning, temp);
							something_was_sorted = true;
							break;
						}
					}
				
				}
				if(something_was_sorted == false)
				{
					sorted = true;
				}
			}
			//the list is sorted so let's combine all the lists contained in new_list and add this list to the return_list
			try
			{
				ArrayList intersection_list = new ArrayList((ArrayList)new_list.get(0));
				
				String name_of_list = (String)intersection_list.get(0);
				
				for(int intersection_count=1; intersection_count<new_list.size(); intersection_count++)
				{
					String additional_name = (String)((ArrayList)new_list.get(intersection_count)).get(0);
					name_of_list+= "_and_" + additional_name;
					intersection_list.retainAll((ArrayList)new_list.get(intersection_count));
				}
				if(new_list.size()>1)
				{
					intersection_list.add(0, name_of_list);
				}
				return_list.add(intersection_list);
			}
			catch(Exception e)
			{
				
			}
		}
		
		
		
		return return_list;
	}

	// Evaluate n!
	public static int factorial( int n )
	{
	    if( n <= 1 )     // base case
	        return 1;
	    else
	        return n * factorial( n - 1 );
	}
	
	public void createDirectory(String directory_name)
	{
		File dir = new File(directory_name);  dir.mkdir();
		
	}
	
	//This method takes a list of strings on different lines and stores them to an arraylist
	public ArrayList storeTextFiletoArrayList(String file_path)
	{
		ArrayList return_list = new ArrayList();
		try{
			   FileInputStream fstream = new FileInputStream(file_path);
			   // Get the object of DataInputStream
			   DataInputStream in = new DataInputStream(fstream);
			   BufferedReader br = new BufferedReader(new InputStreamReader(in));
			   String strLine;
			   //Read File Line By Line
			   while ((strLine = br.readLine()) != null)   {
			   
				   return_list.add(strLine);
			   }
			   //Close the input stream
			   in.close();
			     }catch (Exception e){//Catch exception if any
			   System.err.println("Error: " + e.getMessage());
			   }
			   return return_list;
	}
	
	//This method takes a list of ints on different lines and stores them to an int arraylist
		public int[] storeTextFiletoIntArray(String file_path)
		{
			ArrayList return_list = new ArrayList();
			try{
				   FileInputStream fstream = new FileInputStream(file_path);
				   // Get the object of DataInputStream
				   DataInputStream in = new DataInputStream(fstream);
				   BufferedReader br = new BufferedReader(new InputStreamReader(in));
				   String strLine;
				   //Read File Line By Line
				   while ((strLine = br.readLine()) != null)   {
				   
					   return_list.add(strLine);
				   }
				   //Close the input stream
				   in.close();
				     }catch (Exception e){//Catch exception if any
				   System.err.println("Error: " + e.getMessage());
				   }
			int size = return_list.size();
			int[] ireturn_list = new int[size];
			for(int i=0; i<return_list.size(); i++)
			{
				ireturn_list[i] = Integer.valueOf(return_list.get(i).toString()).intValue();
			}
				   return ireturn_list;
		}
	
	/*
	 * The purpose of this method is to add text to a filename without adding it after the extension
	 */
	public String addTextToFilename(String filename, String text_to_add)
	{
		
		String return_string = filename;
		if(filename.matches(".+\\..+"))
		{
			String original_filename = filename;
		
			String first_part_of_filename = findAndReplacewithRegEx(filename, "(.+)\\.(.+)", "$1");
			String extension = "."+findAndReplacewithRegEx(original_filename, "(.+)\\.(.+)", "$2");
			return_string =first_part_of_filename+text_to_add+extension;
		}
		return return_string;

	}
	
	public void storeArrayListtoFileOnNewLines(ArrayList list, String directory, String filename)
	{
		String output_string = "";
		for(int i=0; i<list.size(); i++)
		{
			output_string+= list.get(i).toString()+"\r\n";
		}
		createTextFile(directory, filename, output_string);
	}
	
	public String storeTextFiletoString(String file_path)
	{
		String return_string = "";
		ArrayList return_list = new ArrayList();
		try{
			   FileInputStream fstream = new FileInputStream(file_path);
			   // Get the object of DataInputStream
			   DataInputStream in = new DataInputStream(fstream);
			   BufferedReader br = new BufferedReader(new InputStreamReader(in));
			   String strLine;
			   //Read File Line By Line
			   while ((strLine = br.readLine()) != null)   {
			   
				   return_list.add(strLine);
			   }
			   //Close the input stream
			   in.close();
			     }catch (Exception e){//Catch exception if any
			   System.err.println("Error: " + e.getMessage());
			   }
	     for(int i=0; i<return_list.size(); i++)
	     {
	    	 return_string+=return_list.get(i) + "\r";
	     }
	     return return_string;
	}
	/*
	public double evaluateExpression(String expression)
	{
		Expression expression_object = new Expression(expression);
		return expression_object.evaluate();
	}
	*/
	
	public boolean doesFileExist(String filePathString)
	{
		boolean return_value = false;
		File f = new File(filePathString);
		if(f.exists()) { return_value=true; }
		return return_value;

	}
	public void createTextFile(String directory, String file_name, String text)
	{
		createNecessaryDirectories(directory);
		try
		{
		   Writer output = null;
		   File file = new File(Paths.get(directory+""+File.separator+"" + file_name).toString() );
		   output = new BufferedWriter(new FileWriter(file));
		   output.write(text);
		   output.close();
		}
		catch(Exception e)
		{
		}
		
	}
	//this function makes all the directories necessary to put a file into a certain path.  The function goes through to check whether directories already exist or need to be created.
	public void createNecessaryDirectories(String directory)
	{
		File fdirectory = new File(directory);
		if(!fdirectory.exists())
		{
			String prestring = findAndReplacewithRegEx(directory, "(.+)"+Pattern.quote(File.separator)+"(.+)", "$1");
			String poststring = findAndReplacewithRegEx(directory, "(.+)"+Pattern.quote(File.separator)+"(.+)", "$2");
			predirectory_check_string_process(prestring, poststring);
		}
	}
	
	//this is a recursive method which will keep going through and making directories until all of the directories in the specified filepath have been made
	public void predirectory_check_string_process(String prestring, String poststring)
	{
		File fdirectory = new File(prestring);
		if(!fdirectory.exists())
		{
			String original_prestring = prestring;
			prestring = findAndReplacewithRegEx(prestring, "(.+)"+Pattern.quote(File.separator)+"(.+)", "$1");
			poststring = findAndReplacewithRegEx(original_prestring, "(.+)"+Pattern.quote(File.separator)+"(.+)", "$2")+Pattern.quote(File.separator)+poststring;
			predirectory_check_string_process(prestring, poststring);
		}
		else
		{
			//get the first item of the poststring and make a directory
			String first_item_of_poststring = findAndReplacewithRegEx(poststring, "(.+?)"+Pattern.quote(File.separator)+"(.+)", "$1");
			String new_post_string = findAndReplacewithRegEx(poststring, "(.+?)"+Pattern.quote(File.separator)+"(.+)", "$2");
			String new_pre_string = Paths.get(prestring+File.separator+first_item_of_poststring).toString();
			
			if(!first_item_of_poststring.equals(new_post_string))
			{
				createDirectory(new_pre_string);
				predirectory_check_string_process(new_pre_string, new_post_string);
			}
			else
			{
				createDirectory(new_pre_string);
			}
		}
	}
	
	public void appendTextFile(String directory, String file_name, String text)
	{
		try
		{
		   Writer output = null;
		   File file = new File(Paths.get(directory+""+File.separator+"" + file_name).toString() );
		   output = new BufferedWriter(new FileWriter(file,true));
		   output.write(text);
		   output.close();
		}
		catch(Exception e)
		{
		}
	}
	
	public String getTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public String getFilenameTime()
	{
		String time = getTime().replaceAll("/", "-");
		return time.replaceAll(":", "c");	
	}
	public String findAndReplacewithRegEx(String text, String find, String replace)
	{
		Pattern patt = Pattern.compile(find);
		  Matcher m = patt.matcher(text);
		  StringBuffer sb = new StringBuffer(text.length());
		  while (m.find()) {
		    m.appendReplacement(sb, replace);
		  }
		  m.appendTail(sb);
		  return sb.toString();
	}
	
	
	
	public void setTabDelimitedText(String tab_delimited_text)
	{
		this.tab_delimited_text = tab_delimited_text;
		String[] sa_rows = tab_delimited_text.split("\r");
		
		if(sa_rows.length==1)
		{
			sa_rows = tab_delimited_text.split("\n");
			
		}
		max_rows = sa_rows.length;
		//now find the maximum number of columns so we can store everything in our sa_cells array
		
		ArrayList columns = new ArrayList();
		for(int i=0; i<sa_rows.length; i++)
		{
			String[] sa_columns = sa_rows[i].split("\t");
			if(sa_columns.length>max_columns)
			{
				max_columns = sa_columns.length;
			}
			columns.add(sa_columns);
		}
		//now that we have the max columns and rows, just put everything in place
		sa_cells = new String[max_rows][max_columns];
		int total_cells = max_rows*max_columns;
		for(int i=0; i<sa_rows.length; i++)
		{
			String[] sa_columns = (String[])columns.get(i);
			for(int j=0; j<sa_columns.length; j++)
			{
				sa_cells[i][j] = sa_columns[j];
			}
		}
		
	}
	
	public String[][] getCells()
	{
		return sa_cells;
	}
	
	

	public ArrayList extractColumn(int column_number)
	{
		ArrayList temporary_list = extractArray(0, column_number, -1, column_number);
		ArrayList return_list = new ArrayList();
		for(int i=0; i<temporary_list.size(); i++)
		{
			return_list.add(((ArrayList)temporary_list.get(i)).get(0));
		}
		return return_list;
	}
	
	public ArrayList extractPartialColumn(int column_number, int start_row)
	{
		ArrayList temporary_list = extractArray(start_row, column_number, -1, column_number);
		ArrayList return_list = new ArrayList();
		for(int i=0; i<temporary_list.size(); i++)
		{
			return_list.add(((ArrayList)temporary_list.get(i)).get(0));
		}
		return return_list;
	}
	
	public ArrayList extractPartialColumnWithOpenCSV(String full_filepath, int column_number, int start_row)
	{
		CSVReader reader;
		ArrayList return_list = new ArrayList();
        try 
        {
        	
	        reader = new CSVReader(new FileReader(full_filepath),'\t');
	        String[] row;
	        int row_count = 0;
	       
	        while ((row = reader.readNext()) != null) 
	        {
	        	if(row_count>=start_row)
	        	{
	        		return_list.add(row[column_number]);
	        
	        	}
	        	row_count++;
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
        return return_list;
	        
	}
	
	public ArrayList extractPartialRowWithOpenCSV(String full_filepath, int row_number, int start_column, int end_column)
	{
		CSVReader reader;
		ArrayList return_list = new ArrayList();
        try 
        {
        	
	        reader = new CSVReader(new FileReader(full_filepath),'\t');
	        String[] row;
	        int row_count = 0;
	       
	        while ((row = reader.readNext()) != null) 
	        {
	        	if(row_number==row_count)
	        	{
	        		for(int i=start_column; i<=end_column; i++)
	        		{
	        			return_list.add(row[i]);
	        		}
	        		break;
	        	}
	        	row_count++;
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
        return return_list;
	        
	}
	public ArrayList extractRow(int row_number)
	{
		return (ArrayList)extractArray(row_number, 0, row_number, -1).get(0);
	}
	public ArrayList extractPartialRow(int row_number, int start_column)
	{
		return (ArrayList)extractArray(row_number, start_column, row_number, -1).get(0);
	}
	public String extractCell(int row, int column)
	{
		String return_value = extractArray(row,column,row,column).get(0).toString();
		return_value = return_value.replaceAll("\\[", "");
		return_value = return_value.replaceAll("\\]", "");
		return return_value;
	}
	
	/*
	 * extracts an array from a tab delimited text file and returns this as an array of arraylists.  If you don't know the end row or end column, just enter -1 for this parameter 
	 */
	
	public ArrayList extractArray(int start_row, int start_column)
	{
		return extractArray(start_row,start_column,-1,-1);
	}
	
	
	public ArrayList extractArray(int start_row, int start_column, int end_row, int end_column)
	{
		ArrayList return_list = new ArrayList();
		if(start_row==-1)
		{
			start_row=0;
		}
		if(start_column==-1)
		{
			start_column=0;
		}
		if(end_row==-1)
		{
			end_row=max_rows-1;
		}
		if(end_column==-1)
		{
			end_column=max_columns-1;
		}
		for(int row_count = start_row; row_count<=end_row; row_count++)
		{
			ArrayList row = new ArrayList();
			for(int column_count = start_column; column_count<=end_column; column_count++)
			{
				row.add(sa_cells[row_count][column_count]);
			}
			return_list.add(row);
		}
		return return_list;
	}
	
	
	public String callSystemCommand(String path, String[] commandArray)
	{
		String return_string = "";
		try 
	{ 
			ProcessBuilder launcher = new ProcessBuilder();
		    Map<String, String> environment = launcher.environment();
		    
	    launcher.redirectErrorStream(true);
		    launcher.directory(new File(path));

		    environment.put("name", "var");
		    //System.out.println(System.getenv("Path"));
		    launcher.command(commandArray);
		    Process p = launcher.start(); // And launch a new process
		    BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    String line;
	    while ((line = output.readLine()) != null)
		      return_string += line + "\n";

		    // The process should be done now, but wait to be sure.
		    p.waitFor();
		    
	} 
	catch(IOException e1) {
		System.out.println("There was an error: "+e1.getMessage());
	} 
	catch(InterruptedException e2) {
		System.out.println("There was an error: "+e2.getMessage());
	} 
	catch (Exception e3)
	{
		System.out.println("There was an error: "+e3.getMessage());
	}
	return return_string;
	//System.out.println("Process: " + commandArray[0] + " is done"); 
	}
	
	public static double Median(ArrayList original_list)
	{
		//copy values into a new list so that we don't modify the first list
		ArrayList values = copyArrayList(original_list);
		//first make sure we are dealing with a list of doubles
		if(!values.get(0).getClass().equals("class java.lang.Double"))
		{
			values = convertArrayListOfStringsToDoubles(values);
			
		}
		
	    Collections.sort(values);
	   
	    if (values.size() % 2 == 1)
		return Double.valueOf(String.valueOf(values.get((values.size()+1)/2-1)).toString()).doubleValue();
	    else
	    {
		double lower = Double.valueOf(String.valueOf(values.get(values.size()/2-1)).toString()).doubleValue();
		double upper = Double.valueOf(String.valueOf(values.get(values.size()/2)).toString()).doubleValue();
	 
		return (lower + upper) / 2.0;
	    }	
	}
	
	public double getMean(ArrayList values)
    {
		double size = Double.valueOf(values.size()).doubleValue();
		double[] data = new double[values.size()];
		for(int i=0; i<values.size(); i++)
		{
			data[i] = Double.valueOf(values.get(i).toString()).doubleValue();
		}
        double sum = 0.0;
        for(double a : data)
            sum += a;
            return sum/size;
    }
	
	 public double getVariance(ArrayList values)
     {
		double size = Double.valueOf(values.size()).doubleValue();
		double[] data = new double[values.size()];
		for(int i=0; i<values.size(); i++)
		{
			data[i] = Double.valueOf(values.get(i).toString()).doubleValue();
		}
		/*
         double mean = getMean(values);
         double temp = 0;
         for(double a :data)
             temp += (mean-a)*(mean-a);
             return temp/size;
             */
		if (data.length == 0) return Double.NaN;
        double avg = getMean(values);
        double sum = 0.0;
        for (int i = 0; i < data.length; i++) {
            sum += (data[i] - avg) * (data[i] - avg);
        }
        return sum / (data.length - 1);
     }
	 
	 double getStdDev(ArrayList values)
     {
         return Math.sqrt(getVariance(values));
     }

	
	public static ArrayList convertArrayListOfStringsToDoubles(ArrayList values)
	{
		for(int i=0; i<values.size(); i++)
		{
			values.set(i, Double.valueOf(values.get(i).toString()).doubleValue());
		}
		return values;
	}
	
	/*
	 * get the minimum value of an arraylist
	 */
	public double getMin(ArrayList list)
	{
		double min_value = Double.valueOf(list.get(0).toString()).doubleValue();
		for(int i=1; i<list.size(); i++)
		{
			double current_value = Double.valueOf(list.get(i).toString()).doubleValue();
			if(current_value<min_value)
			{
				min_value = current_value;
			}
		}
		return min_value;
	}
	public double getMax(ArrayList list)
	{
		double max_value = Double.valueOf(list.get(0).toString()).doubleValue();
		for(int i=1; i<list.size(); i++)
		{
			double current_value = Double.valueOf(list.get(i).toString()).doubleValue();
			if(current_value>max_value)
			{
				max_value = current_value;
			}
		}
		return max_value;
	}
	
	/*
	 * This method assumes that their are an equal number of rows in each column
	 */
	public void outputTableOfValues(String[] column_titles, String directory, String filename, ArrayList... lists)
	{
		String return_string = "";
		//make the header
		for(int i=0; i<column_titles.length-1; i++)
		{
			return_string+=column_titles[i]+"\t";
		}
		return_string+=column_titles[column_titles.length-1]+"\r\n";
		//now go through and add the values at each row
		
		for(int row_count=0; row_count<lists[0].size(); row_count++)
		{
			for(int column_count=0; column_count<lists.length-1; column_count++)
			{
				String current_value = "-";
				try
				{
					current_value = lists[column_count].get(row_count).toString();
				}
				catch(Exception e)
				{
					
				}
				
				return_string+= current_value+"\t";
				
			}
			try
			{
				return_string+= lists[lists.length-1].get(row_count).toString()+"\r\n";
			}
			catch(Exception e)
			{
				return_string+="-\r\n";
			}
			
		}
		createTextFile(directory, filename, return_string);

	}
	
	
	
	 public ArrayList mode(ArrayList list)
     {
         
       ArrayList <Double> most=new ArrayList <Double> ();
       
       double num = 0;
       double count = 0;
       
       for(int i = 0; i < list.size(); i++)
       {
            double cur_num = Double.valueOf(list.get(i).toString()).doubleValue();
            double cur_count = 1;
       
            for(int i2 = 0; i2 < list.size(); i2++)
            {
                if(Double.valueOf(list.get(i2).toString()).doubleValue() == cur_num)
                    cur_count++;
            }
            
            if(cur_count > count)
            {
                //new high
                num = cur_num;
                count = cur_count;
                most.clear(); //clear list..found higher.

 
                    most.add(num); // found another

            }
            else if(cur_count == count)
            {
                //same high as other number...but add both for now..
                
                //check to see if its already on the list
                boolean flag = false;               
                for(int i3 = 0; i3 < most.size(); i3++)
                {
                    if(most.get(i3) == cur_num)
                    {
                        flag = true; //duplicate
                    }
                }
                if(flag == false)
                {
                    most.add(cur_num); // found another
                }
            }
            
                
        }
         
       //most.add(10);
       //most.add(48);
       
        return most;
        
    }
	 
	 
	 public static ArrayList copyArrayList(ArrayList original_list)
	 {
		 ArrayList new_list = new ArrayList();
		 for(int i=0; i<original_list.size(); i++)
		 {
			 new_list.add(original_list.get(i).toString());
		 }
		 return new_list;
	 }
	 
	 public ArrayList searchForFile(File rootDirectory, FileFilter filter){
		    ArrayList results = new ArrayList<File>();
		    for(File currentItem : rootDirectory.listFiles(filter)){
		      if(currentItem.isDirectory()){
		          results.addAll(searchForFile(currentItem, filter));
		      }
		      else{
		          results.add(currentItem);
		      }
		    }
		    return results;
		}
	 
	 public ArrayList searchForFile(File rootDirectory, FilenameFilter filter){
		    ArrayList results = new ArrayList<File>();
		    File[] files_to_search = rootDirectory.listFiles();
		    for(int i=0; i<files_to_search.length; i++)
		    {
		    	File current_file = files_to_search[i];
		    	if(current_file.isDirectory())
		    	{
		    		results.addAll(searchForFile(current_file, filter));
		    	}
		    }
		    File[] matching_files = rootDirectory.listFiles(filter);
		    for(int i=0; i<matching_files.length; i++)
		    {
		    	results.add(matching_files[i]);
		    }
		    /*
		    for(File currentItem : rootDirectory.listFiles(filter)){
		      if(currentItem.isDirectory()){
		          results.addAll(searchForFile(currentItem, filter));
		      }
		      else{
		          results.add(currentItem);
		      }
		      */
		    
		    return results;
		}
	 
		public ArrayList findAllFilesInDirectoryAndSubdirectoriesMatchingThisName(String directory, String name)
		{
			final String file_name = name;
			ArrayList list_of_matching_files = new ArrayList();
			FilenameFilter filter = new FilenameFilter() {
		        public boolean accept(File directory, String fileName) {
		            return fileName.equals(file_name.toString());
		        }
		        };
		        list_of_matching_files = searchForFile(new File(Paths.get(directory).toString()), filter);
		        return list_of_matching_files;
		}
		
		/*
		 * This method accepts an arraylist of file locations of txt files in tab delimited text format, and then it makes a new file with all of these similar tables combined into one table
		 */
		public void combineAllTablesIntoOneTable(ArrayList list_of_files, String output_directory, String output_name)
		{
			String output_string = "";
			//first get the header
			String first_file_location = list_of_files.get(0).toString();
			String table = storeTextFiletoString(first_file_location);
			setTabDelimitedText(table);
			ArrayList header = extractRow(0);
			
			String s_header = convertArrayListToTabDelimitedText(header);
			output_string = s_header+"\r\n";
			//now go through and add all of the rows from all of the tables to the output string
			for(int i=0; i<list_of_files.size(); i++)
			{
				String file_location = list_of_files.get(i).toString();
				table = storeTextFiletoString(file_location);
				setTabDelimitedText(table);
				boolean still_more_rows = true;
				int count = 1; //don't start the count at 0 since this would be the header row and we already stored that
				while(still_more_rows)
				{
					try
					{
						ArrayList row = extractRow(count);
						count++;
						String s_row = convertArrayListToTabDelimitedText(row);
						output_string+=s_row+"\r\n";
					}
					catch(Exception e)
					{
						still_more_rows = false;
					}
				}
			}
			createTextFile(output_directory, output_name, output_string);
		}
		
		
		public String convertArrayListToTabDelimitedText(ArrayList list)
		{
			String list_as_string = list.toString();
			String return_string = findAndReplacewithRegEx(list_as_string, "^\\[", "");
			return_string = findAndReplacewithRegEx(return_string, "\\]$", "");
			//return_string = return_string.replaceAll("(?<!\\[)(.+)(, )(.+)(?!\\])", "$1q$3");
			
			

			//return_string = findAndReplacewithRegEx(return_string,"(?<!\\[.{0,"+string_length+"})(, )(?!.{0,"+string_length+"}\\])", "q");
			
			/*
			 * matching a pattern that is not surrounded by another pattern turns out to be a complex task
			 * There's a webpage about it here
			 * http://stackoverflow.com/questions/1191397/regex-to-match-values-not-surrounded-by-another-char
			 * We only want there to be a match if there is an even number of surrounding_pattern after the pattern.  If there is an odd number, than that means the pattern is within at a least two surround_pattern
			 * I tried to accomplish this task with regular expressions.  However, this was proving difficult.  Instead, I think I will just write a function (possibly recursive)
			 */
			String pattern = ", ";
			String surrounding_pattern = "[\\[\\]]";
			
			return_string = replacePatternNotSurroundedBySurroundingPattern(return_string, pattern, "\t", surrounding_pattern);
			
			return return_string;
		}
		
		/*
		 * * We only want there to be a match if there is an even number of surrounding_pattern after the pattern.  If there is an odd number, than that means the pattern is within at a least two surround_pattern
		 */
		public String replacePatternNotSurroundedBySurroundingPattern(String original_string, String pattern, String replacement_pattern, String surrounding_pattern)
		{
			int shift = replacement_pattern.length()-pattern.length();
			//first get all the positions of the pattern
			ArrayList positions = findPositionOfString(original_string, pattern);
			//we actually only want the even positions of the string since the odd positions are just where all of the instances of pattern end instead of begin
			//this code is a little bit tricky and I had to draw it on paper to visualize how to do this
			//"F:\kurt\storage\CIM Research Folder\DR\2013\9-24-13\remove odd numbered items from a list 20130924_092117.jpg"
			
			positions = removeOddElementsFromArrayList(positions);
			//now for each position create a substring from that position to the end of the string and count how many times the surrounding pattern occurs.  If the surrounding pattern occurs an even number of times, then replace the pattern with the replacement pattern
			for(int i=0; i<positions.size(); i++)
			{
				
				int current_position = Integer.valueOf(positions.get(i).toString()).intValue();
				try
				{
					int end_of_string = original_string.length();
					String substring = original_string.substring(current_position-1, original_string.length());
					int number_of_surrounding_pattern_positions  = getMatchesRegEx(substring, surrounding_pattern);
					
					if(number_of_surrounding_pattern_positions%2==0)
					{
						substring = substring.replaceFirst(pattern, replacement_pattern);
						original_string = original_string.substring(0, current_position-1) + substring;
						//we just took away characters of size pattern and added characters of size replacement_pattern
						positions = updateAllPositions(positions, shift, i+1);
						
					}
				}
				catch(Exception e)
				{
					
				}
			}
			
			
			return original_string;
		}
		
		private ArrayList removeOddElementsFromArrayList(ArrayList list)
		{
			int number_of_iterations = (list.size()+1)/2;
			for(int i=1; i<=number_of_iterations; i++)
			{
				try
				{
					list.remove(i);
				}
				catch(Exception e)
				{
					
				}
				
			}
			return list;
		}
		
		private ArrayList updateAllPositions(ArrayList list, int shift, int starting_index)
		{
			for(int j=starting_index; j<list.size(); j++)
			{
				list.set(j, Integer.valueOf(list.get(j).toString()).intValue()+shift);
			}
			return list;
		}
		
		
		public double kurtosis(ArrayList list, double mean, double stdev)
		{
			double dInterim = 0;
			double dCount = Double.valueOf(list.size()).doubleValue();
			double dMultiplier = ((dCount)*(dCount+1)) / ((dCount - 1) * (dCount - 2)*(dCount-3));
			double dSubtractor = 3 * (Math.pow(dCount - 1, 2)) / ((dCount - 2) * (dCount - 3));
			for(int i=1; i<=list.size(); i++)
			{
				double current_data = Double.valueOf(list.get(i-1).toString()).doubleValue();
				dInterim = dInterim + Math.pow(((current_data - mean) / stdev), 4);
			}
			return (dMultiplier * dInterim - dSubtractor);
		}
		
		public double kurtosis(int[] list, double mean, double stdev)
		{
			double dInterim = 0;
			double dCount = (double)list.length;
			double dMultiplier = ((dCount)*(dCount+1)) / ((dCount - 1) * (dCount - 2)*(dCount-3));
			double dSubtractor = 3 * (Math.pow(dCount - 1, 2)) / ((dCount - 2) * (dCount - 3));
			for(int i=1; i<=list.length; i++)
			{
				double current_data = (double)list[i-1];
				dInterim = dInterim + Math.pow(((current_data - mean) / stdev), 4);
			}
			return (dMultiplier * dInterim - dSubtractor);
		}
		
		public double kurtosis(double[] list, double mean, double stdev)
		{
			double dInterim = 0;
			double dCount = (double)list.length;
			double dMultiplier = ((dCount)*(dCount+1)) / ((dCount - 1) * (dCount - 2)*(dCount-3));
			double dSubtractor = 3 * (Math.pow(dCount - 1, 2)) / ((dCount - 2) * (dCount - 3));
			for(int i=1; i<=list.length; i++)
			{
				double current_data = (double)list[i-1];
				dInterim = dInterim + Math.pow(((current_data - mean) / stdev), 4);
			}
			return (dMultiplier * dInterim - dSubtractor);
		}
		
		public double Skew(ArrayList list, double mean, double stdev)
		{
			double dInterim = 0;
			double dCount = Double.valueOf(list.size()).doubleValue();
			double dMultiplier =  (dCount) / ((dCount - 1) * (dCount - 2));
			for(int i=1; i<=list.size(); i++)
			{
				double current_data = Double.valueOf(list.get(i-1).toString()).doubleValue();
				dInterim = dInterim + Math.pow(((current_data - mean) / stdev),3);
			}
			double dSkewness = dMultiplier * dInterim;
			return(dSkewness);
		}
		
		
		public double Skew(int[] list, double mean, double stdev)
		{
			double dInterim = 0;
			double dCount = (double)list.length;
			double dMultiplier =  (dCount) / ((dCount - 1) * (dCount - 2));
			for(int i=1; i<=list.length; i++)
			{
				double current_data = (double)list[i-1];
				dInterim = dInterim + Math.pow(((current_data - mean) / stdev),3);
			}
			double dSkewness = dMultiplier * dInterim;
			return(dSkewness);
		}
		
		public double Skew(double[] list, double mean, double stdev)
		{
			double dInterim = 0;
			double dCount = (double)list.length;
			double dMultiplier =  (dCount) / ((dCount - 1) * (dCount - 2));
			for(int i=1; i<=list.length; i++)
			{
				double current_data = (double)list[i-1];
				dInterim = dInterim + Math.pow(((current_data - mean) / stdev),3);
			}
			double dSkewness = dMultiplier * dInterim;
			return(dSkewness);
		}
		
		
		//the percentile number should be a value inbetween 0 and 1
		//I think all of the values in the original list need to be positive for this function to work
		public double percentile(ArrayList original_list, double percentile)
		{
			double return_value = 0;
			//first copy the list to a new arraylist so that all of the elements are doubles
			ArrayList list = new ArrayList();
	    	//I need to copy the arraylist so that everything in the list is a double
	    	for(int i=0; i<original_list.size(); i++)
	    	{
	    		list.add(Double.valueOf(original_list.get(i).toString()).doubleValue());
	    	}
			Collections.sort(list);
			
			int N = list.size();
			
			double n = (N + 1) * percentile;
			//Another method: double n = (N-1)*percentile+1;
			if(n==Double.valueOf(1.0).doubleValue())
			{
				return_value= Double.valueOf(list.get(0).toString()).doubleValue();
			}
			else if(n==N)
			{
				return_value= Double.valueOf(list.get(N-1).toString()).doubleValue();
			}
			else
			{
				int k = Double.valueOf(n).intValue();
				double d = n-k;
				return_value= Double.valueOf(list.get(k-1).toString()).doubleValue()+d*(Double.valueOf(list.get(k).toString()).doubleValue()-Double.valueOf(list.get(k-1).toString()).doubleValue());
			}

			return return_value;
			
		}
		
		
		//the percentile number should be a value inbetween 0 and 1
				//I think all of the values in the original list need to be positive for this function to work
		//this function requires that the the list is sorted
				public double percentile(int[] list, double percentile)
				{
					double return_value = 0;
					
					
					int N = list.length;
					
					double n = (N + 1) * percentile;
					//Another method: double n = (N-1)*percentile+1;
					if(n==Double.valueOf(1.0).doubleValue())
					{
						return_value= (double)list[0];
					}
					else if(n==N)
					{
						return_value= list[N-1];
					}
					else
					{
						int k = Double.valueOf(n).intValue();
						double d = n-k;
						return_value= (double)list[k-1]+d*((double)list[k]-(double)list[k-1]);
					}

					return return_value;
					
				}
				
				
				//the percentile number should be a value inbetween 0 and 1
				//I think all of the values in the original list need to be positive for this function to work
				public double percentile(double[] original_list, double percentile)
				{
					double return_value = 0;
					double[] list = new double[original_list.length];
					//first copy the list to a new arraylist so that all of the elements are doubles
					
			    	//I need to copy the arraylist so that everything in the list is a double
			    	for(int i=0; i<original_list.length; i++)
			    	{
			    		list[i] = original_list[i];
			    	}
					Arrays.sort(list);
					
					int N = list.length;
					
					double n = (N + 1) * percentile;
					//Another method: double n = (N-1)*percentile+1;
					if(n==Double.valueOf(1.0).doubleValue())
					{
						return_value= list[0];
					}
					else if(n==N)
					{
						return_value= list[N-1];
					}
					else
					{
						int k = Double.valueOf(n).intValue();
						double d = n-k;
						return_value= list[k-1]+d*(list[k]-list[k-1]);
					}

					return return_value;
					
				}
}
