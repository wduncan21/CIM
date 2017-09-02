import java.util.*;


public class Bin {

	ArrayList raw_numbers = new ArrayList();
	int size = 0;
	double lower_bound =0;
	double upper_bound = 0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	Bin(ArrayList raw_numbers, double lower_bound, double upper_bound)
	{
		initializeBin(raw_numbers, upper_bound, lower_bound);
	}
	Bin(ArrayList raw_numbers)
	{
		initializeBin(raw_numbers,0,1);
	}
	
	public void initializeBin(ArrayList raw_numbers, double upper_bound, double lower_bound)
	{
		setRawNumbers(raw_numbers);
		setLowerBound(lower_bound);
		setUpperBound(upper_bound);
	}
	public int getSize()
	{
		return size;
	}
	public ArrayList getRawNumbers()
	{
		return raw_numbers;
	}
	public void setLowerBound(double lower_bound)
	{
		this.lower_bound = lower_bound;
	}
	public void setUpperBound(double upper_bound)
	{
		this.upper_bound = upper_bound;
	}
	public void setRawNumbers(ArrayList raw_numbers)
	{
		this.size = raw_numbers.size();
		for(int i=0; i<this.size; i++)
		{
			this.raw_numbers.add(raw_numbers.get(i));
		}
	}
	
	public String toString()
	{
		String return_string = "Size: " + size + "\n" + raw_numbers.toString()+"\n";
		return return_string;
	}
}
