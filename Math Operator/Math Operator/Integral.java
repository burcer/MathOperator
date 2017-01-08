/************************************************************************************************************
*This Java code is written by "7", whose members are Can Karakuþ, Þükrü Burç Eryýlmaz, Bilgehan Avþer,
*Ömer Sercan Arýk and Onur Günlü.Please include a brief reference comment while using in other applications.
***********************************************
/*
 *Integral 
 *This class finds the definite integral of a function 
 *
 *Author: Sercan Arýk
 */

public class Integral extends Operation
{
	//properties
	
	protected Function f;
	protected Double lowerBound;
	protected Double upperBound;
	protected double sensitivity;
	protected String oldDefinition;
	protected String definition = "";
	
	//constructors
	/*
	 *default constructor
	 */	
	public Integral()
	{
		f = new Function("");
		lowerBound = null;
		upperBound = null;
		setSensitivity();
	}
	
	/*
	 *takes the function, upper bouds and lower bouds of the integral
	 *as arguments and initializes them 
	 */
	public Integral(Function fu, double lowerB, double upperB)
	{
		f = fu;
		lowerBound = lowerB;
		upperBound = upperB;
		setSensitivity();
	}
	
	//methods
	/*
	 *finds the integral of the function with upper and lower bouds given
	 */
	public Double execute()
	{	
		double result = 0.0;
		  
		if (upperBound >= lowerBound)
		{  
			double step = (upperBound - lowerBound) / sensitivity;
			for (int i = 0; i < step; i++) 
			{
				Double x = new Double(lowerBound + sensitivity*0.5 + i*sensitivity);
				result = result + (sensitivity*(f.valueAt(x) .doubleValue()));
			}
			
			double d = result/sensitivity;
			result = ((int)Math.round(d))*sensitivity;

			Double res = new Double(result);
			return res;
		}
		
		else
		{
			double nupperBound = lowerBound;
			double nlowerBound = upperBound;
			
			double step = (nupperBound - nlowerBound) / sensitivity;
			for (int i = 0; i < step; i++) 
			{
				Double x = new Double(nlowerBound + sensitivity*0.5 + i*sensitivity);
				result = result + (sensitivity*(f.valueAt(x) .doubleValue()));
			}
		
			double d = result/sensitivity;
			result = -1*((int)Math.round(d))*sensitivity;
			
			Double res = new Double(result);
			return res;
		}
			
	}
	
	/*
	 *getter method for the definition
	 */
	public String getDefinition()
	{
		return f.getDefinition();
	}
	
	/*
	 *getter method for the sensitivity
	 */
	public double getSensitivity()
	{
		return sensitivity;
	}
	
	/*
	 *getter method for the upper bound
	 */
	public double getUpperBound()
	{
		return upperBound;
	}
	
	/*
	 *getter method for the lower bound
	 */
	public double getLowerBound()
	{
		return lowerBound;
	}
	
	/*
	 *setter method for the sensitivity
	 */
	public void setSensitivity()
	{
		sensitivity = 0.001;		 
	}
	
	/*
	 *setter method for the definition
	 */
	public void setDefinition(String s)
	{
		f.definition = s;	
	}
	
	/*
	 *setter method for the upper bound
	 */
	public void setUpperBound(double newUpBound)
	{
		upperBound = newUpBound;
	}
	
	/*
	 *setter method for the lower bound
	 */
	public void setLowerBound(double newLowBound)
	{
		lowerBound = newLowBound;
	}
	
	/*
	 *returns true if the syntax of the integrand is valid and executable, false
	 *otherwise
	 */
	public boolean syntaxCheck()
	{	
		if( lowerBound >= Double.MIN_VALUE && Double.MAX_VALUE >= upperBound && lowerBound != null && upperBound != null )
			return true;
		
		else return false;
	}
	
	/*
	 *adds the given string to the definition
	 */	
	public void addToDefinition(String S)
	{
		oldDefinition = f.getDefinition();
		definition = oldDefinition + S;
		f.setDefinition(definition);
	}
	
	/*
	 *undos the last action
	 */
	public void undo()
	{
		oldDefinition = f.getDefinition();
		definition = oldDefinition;
		f.setDefinition(definition);
	}
	
	/*
	 *clears the properties of the function
	 */
	public void clear()
	{
		oldDefinition = f.getDefinition();
		definition = "";
		f.setDefinition(definition);
	}
	
	/*
	 *getter method for the function
	 */
	public Function getFunction()
	{
		return f;
	}
	
}