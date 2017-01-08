/************************************************************************************************************
*This Java code is written by "7", whose members are Can Karakuþ, Þükrü Burç Eryýlmaz, Bilgehan Avþer,
*Ömer Sercan Arýk and Onur Günlü.Please include a brief reference comment while using in other applications.
**************************************************************************************************************
*/

import java.text.DecimalFormat;
import java.util.*;

/*
 *DAO 
 *This class performs operations on the direct interactions area and without 
 *defining function or equation
 *
 *Author: Sukru Burc Eryilmaz
 */

public class DAO extends Operation
{
	protected String definition;
	protected String oldDefinition;
	protected static boolean hasMod;
	protected static int modulo; 

	/*
	 *Default constructor
	 */
	public DAO()
	{
		definition = "";
		oldDefinition = "";
		hasMod = false; 
	}
	
	/*
	 *The constructor that takes the definition of operation as argument
	 */ 
	public DAO( String definition)
	{
		this.definition = definition;
		oldDefinition = "";
		hasMod = false;	
	}
	
	/*
	 *This method finds the result of operation considering whether modulo is chosen
	 *or not
	 */ 
	public Double execute( String definition)
	{
		if ( hasMod == true)
			return executeWithMod( definition);
		else 
			return executeWithoutMod( definition);
	}
	
	/*
	 *This method finds the result of the current operation with mod
	 */
	 private Double executeWithMod( String definition)
	 
	{
		
			if ( Algebra.execute( definition) < 0.0005)
				return new Double(Algebra.execute( definition));
			else
			{
				long num, denum, intRes = 0;
				double result = Algebra.execute( definition);
				double rounded = result;
				num = Math.round( rounded * 1000);
				denum = 1000;
				long newNum = Math.abs( num) / gcd( Math.abs( num), denum);
				long newDenum = denum / gcd( Math.abs( num), denum);
				
				if ( gcd( modulo, newDenum) == 1)
				{
					while ( newNum % newDenum != 0)
					{
						newNum += modulo;
					}
					
					intRes = newNum / newDenum;
					
					while ( intRes >= modulo)
					{
						intRes -= modulo;
					}
				}
				
				if ( result < 0)
					intRes = modulo - intRes;
					
				return (Double)(double)(new Long( intRes));
			}
	}
	
	/*
	 *This method finds the result of the current operation if there is not
	 *a specified modulo using the mothod in Algebra class
	 */
	private Double executeWithoutMod( String definition)
	{
		return Algebra.execute(definition);
	}
	
	/*
	 *Recursive method to find greatest common divisor
	 */
	public static long gcd( long a, long b)
	{
		if ( a == 0)
			return b;
		if ( b == 0)
			return a;
		else 
			return gcd( Math.max( a, b) % Math.min( a, b), Math.min( a, b)); 
	}
	
	/*
	 *setter mothod for the definition
	 */
	public void setDefinition( String def)
	{
		oldDefinition = definition;
		definition = def;
	}
	
	/*
	 *getter mothod for the definition
	 */
	public String getDefinition()
	{
		return definition;
	}
	
	/*
	 *adds the given string to current definition 
	 */
	public void addToDefinition(String s)									
	{
		oldDefinition = definition;
		definition += s;
	}

	/*
	 *undos the last action
	 */
	public void undo()
	{
		definition = oldDefinition;
	}
	
	/*
	 *clears the definition of the operation
	 */
	public void clear()
	{
		definition = "";
		oldDefinition = "";
	}
	
	/*
	 *setter method for the modulo
	 */
	public static void setModulo( int mod)
	{
		hasMod = true;
		modulo = mod;
	}
	
	/*
	 *cancels the modulo 
	 */
	public static void unsetModulo()
	{
		hasMod = false;
	}
	
	//Dummy methods
	public Double execute()
	{
		return new Double(0);
	}
	
}