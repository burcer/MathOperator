/************************************************************************************************************
*This Java code is written by "7", whose members are Can Karakuþ, Þükrü Burç Eryýlmaz, Bilgehan Avþer,
*Ömer Sercan Arýk and Onur Günlü.Please include a brief reference comment while using in other applications.
**************************************************************************************************************
*/
import java.text.*;
/**
 *Derivative 
 *This class finds the derivative of a function at a specified point
 *
 *Author: Bilgehan Avser
 */

public class Derivative extends Operation 
{
	//properties
	
	Function f;
	Double point, sensitivity;
    final int MAX = 100;
	double error2, fac, h, ans, error = 100000;
	double table[][] = new double[MAX][MAX];

    
    //constructors
    
    /*
     *Default constructor
     */
    public Derivative() 
    {
    	super();
    	f = new Function();
    	point = null;
    	sensitivity = 0.001;
    }
    
    public Derivative( Function f, double point)
    {
    	super();
    	this.f = f;
    	this.point = point;
    	this.sensitivity = 0.001;
    }
    
    
    //methods
    /*
     *this method includes the algorithm of finding the derivative of a function
     */
    public Double execute()
    {
    	h = sensitivity;
		table[1][1] = ( f.valueAt( point + h)- f.valueAt( point - h)) / ( 2 * h);

		for ( int i = 2; i <= MAX; i++) 
		{
			h /= 1.4;
			table[1][i] = ( f.valueAt( point + h) - f.valueAt( point - h)) / ( 2 *h);
			fac = 1.96;
			for ( int j=2; j <= i; j++) 
			{
				table[j][i] = ( table[j-1][i] * fac - table[j-1][i-1]) / (fac - 1);
				fac = 1.96 * fac;
				error2 = Math.max( Math.abs( table[j][i] - table[j-1][i]), Math.abs( table[j][i] - table[j-1][i-1]));
				if ( error2 <= error) 
				{
					error = error2;
					ans = table[j][i];
				}	
			}
		
			if ( Math.abs( table[i][i] - table[i-1][i-1]) >= 2.0 * (error)) break;
		}			

	return ans;

    }
    
    /*
     *getter method for the function of the derivative
     */
    public Function getFunction()
    {
    	return f;
    }
    
    /*
     *getter method for the point at which derivative will be executed
     */
    public double getPoint()
    {
    	return point;
    }
    
    /*
     *getter method for the sensitivity of algorithm that finds derivative. 
     */
    public double getSensitivity()
    {
    	return sensitivity;
    }
    
    /*
     *getter method for the sensitivity of algorithm that finds derivative. 
     */
    public void setSensitivity( double sensitivity)
    {
    	this.sensitivity = sensitivity;
    }
    
    /*
     *setter method for the point to execute the derivative
     */
    public void setPoint( double point)
    {
    	this.point = point;
    }
    
    /*
     *setter method for the function of the derivative
     */
    public void setFunction( Function f)
    {
    	this.f = f;
    }
    
    /*
     *adds the given string to the definition of the function
     */
    public void addToDefinition( String s)
    {
    	f.setDefinition( "" + f.getDefinition() + s);
    }
    
    /*
     *determines whether the syntax of the function is valid
     *returns true if it is, false otherwise
     */
    public boolean syntaxCheck()
    {
    	if( point == null)
    		return false;
    	else
    		return true;
    }
    
    /*
     *setter method for the definition of the function
     */
    public void setDefinition( String s)
    {
    	f.setDefinition( s);
    }
    
    /*
     *getter method for the definition of the string
     */
    public String getDefinition()
    {
    	return f.getDefinition();
    }
    
    /*
     *clears the properties of the function
     */
    public void clear()
    {
    	f = null;
    	point = null;
    	sensitivity = null;
    }
    
    /*
     *undos the last action
     */
    public void undo()
    {
    	f.undo();
    }
}