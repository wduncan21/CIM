import java.util.ArrayList;
import java.util.Collections;


public class NormalizedDataHandler {

	
	public static void main(String[] args) {
		
		NormalizedDataHandler normalized_data_handler = new NormalizedDataHandler();
		normalized_data_handler.testconvertNormalizedDataToIntegersFromXToY();
	}
	
	public void testconvertNormalizedDataToIntegersFromXToY()
	{
		ArrayList list = new ArrayList();
		double x1 = 0.3;
		double x2 = 0.6;
		double x3 = 1.5;
		list.add(x1);
		list.add(x2);
		list.add(x3);
		list = convertNormalizedDataToIntegersFromXToY(list, 1, 5);
		//the resulting list should be 1,2,5
		System.out.println(list);
	}
	
	//this method simply multiplies every number in the list by a factor and then converts the doubles to integer values
	public ArrayList convertNormalizedDataToIntByMultiplyingFactor(ArrayList list, double factor)
	{
		ArrayList list2 = new ArrayList();
		for(int i=0; i<list.size(); i++)
		{
			list2.add(Double.valueOf(Double.valueOf(list.get(i).toString()).doubleValue()*factor).intValue());
		}
		
		return list2;
	}
	
	//I wrote this method first, but I think this method would always put some values near the min and max (e.g. 1 and 65535).  I think I prefer the other method for normalization
	public ArrayList convertNormalizedDataToIntegersFromXToY(ArrayList list, int lower_bound, int upper_bound)
	{
		
		/*
		//for some reason the max function doesn't seem to be working
		double current_max =  Double.valueOf(Collections.max(list).toString()).doubleValue();
		double current_min =  Double.valueOf(Collections.min(list).toString()).doubleValue();
		*/
		//I'll copy the list to a new list that can be sorted without changing the order of the 1st list
		ArrayList list2 = new ArrayList();
		for(int i=0; i<list.size(); i++)
		{
			list2.add(Double.valueOf(list.get(i).toString()).doubleValue());
		}
		Collections.sort(list2);
		double current_max = Double.valueOf(list2.get(list2.size()-1).toString()).doubleValue();
		double current_min = Double.valueOf(list2.get(0).toString()).doubleValue();
		
		double current_range = current_max-current_min;
		double new_range = upper_bound-lower_bound;
		ArrayList new_list = new ArrayList();
		for(int i=0; i<list.size(); i++)
		{
			try
			{
				double current_number = Double.valueOf(list.get(i).toString()).doubleValue();
				
				double new_number = ((current_number-current_min)/current_range)*new_range+lower_bound;
				int i_new_number = Double.valueOf(new_number).intValue();
				new_list.add(i_new_number);
			}
			catch(Exception e)
			{
				
			}
		}
		return new_list;
	}
	
}
