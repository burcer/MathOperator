/************************************************************************************************************
*This Java code is written by "7", whose members are Can Karakuþ, Þükrü Burç Eryýlmaz, Bilgehan Avþer,
*Ömer Sercan Arýk and Onur Günlü.Please include a brief reference comment while using in other applications.
**************************************************************************************************************

/*
 *Limit
 *evaluates the limits of functions at specified points
 *
 *Author: Bilgehan Avser
 */

public class Limit extends Operation
{
	//properties	
	Function f;
	Double point, sensitivity;
	
	//Constructors
	/*
	 *default constructor
	 */
	public Limit()
	{
		f = new Function();
		point = null;
		sensitivity = 0.00001; 
	}
	
	/*
	 *initializes function, point and the point as given
	 */
	public Limit( Function f, Double point, Double sensitivity)
	{
		this.f = f;
		this.point = point;
		this.sensitivity = sensitivity;
	}
	
	//methods
	/*
	 *finds the limit of the function at the currently specified point
	 */
	public Double execute()
	{
		if( syntaxCheck())
		{
			double a1 = f.valueAt( point - sensitivity);
			double a2 = f.valueAt( point + sensitivity); 		
			return ( a1 + a2) / 2;
		}
		else
			return null;
	}
	
	/*
	 *adds the given string to definition
	 */
	public void addToDefinition( String s)
	{
		f.addToDefinition( s);
	}
	
	/* 
	 *returns true if the expression is valid and false otherwise
	 */
	public boolean syntaxCheck()
	{
		if( point!= null)
			return true;
		else
			return false;
	}
	
	/*
	 *undos the last action
	 */
	public void undo()
	{
		f.undo();
	}
	
	/*
	 *clears the properties of the limit
	 */
	public void clear()
	{
		f = null;
		point = null;
		sensitivity = null;
	}
	
	/*
	 *setter method for the function
	 */
	public void setFunction( Function f)
	{
		this.f = f;

	}
	
	/*
	 *setter method for the point
	 */
	public void setPoint( double point)
	{
		this.point = point;	
	}
	
	/*
	 *setter method for the sensitivity
	 */ 
	public void setSensitivity( Double sensitivity)
	{
		this.sensitivity = sensitivity;
	}
	
	/*
	 *setter method for the definition
	 */
	public void setDefinition( String s)
	{
		f.setDefinition( s);
	}
	
	/*
	 *getter method for the function
	 */
	public Function getFunction()
	{
		return f;
	}
	
	/*
	 *getter method for the point
	 */
	public Double getPoint()
	{
		return point;
	}
	
	/*
	 *getter method for the sensitivity
	 */
	public Double getSensitivity()
	{
		return sensitivity;
	}
	
	/*
	 *getter method for the definition
	 */
	public String getDefinition()
	{
		return f.getDefinition();
	}

}