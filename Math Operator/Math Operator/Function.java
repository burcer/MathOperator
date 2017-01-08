/************************************************************************************************************
*This Java code is written by "7", whose members are Can Karakuþ, Þükrü Burç Eryýlmaz, Bilgehan Avþer,
*Ömer Sercan Arýk and Onur Günlü.Please include a brief reference comment while using in other applications.
**************************************************************************************************************

This class represents mathematical functions. It is a descendant class of DAO. It is used by all other 
Operation-descendant classes except DAO and ComplexDAO.

Author: Bilgehan Avþer
*/

import java.lang.*;
public class Function extends DAO
{
	String name;
	// constructors
	
	public Function()
	{
		super();
	}
	
	public Function( String definition)
	{
		super( definition);
	}
	
	public Function(String name, String d)
	{
		super(d);
		this.name = name;
	}
	
	//methods
	//The value of the Function at point x.
	public Double valueAt( double x)
	{
		Double result;
		String str = "";
		
		if( synCheck())
		{
			for( int i = 0; i<definition.length() ;i++)
			{
				if( definition.charAt(i) == 'x')
					str = str + x;
			
				else
					str = str + definition.charAt(i);
			}
			
			result = super.execute(str);
			return result;
		}
		else
			return null;
	}
	
	//Syntax check and correction
	private boolean synCheck()
	{
		for(int i=0; i<definition.length()-1; i++)
		{
			if( definition.charAt(i) == 'x')
			{
		//		if(Character.isDigit(definition.charAt( i-1)))
		//			definition = definition.substring(0, i) + '*' + definition.substring(i, definition.length());
				
				if(i != definition.length() && definition.charAt( i+1) == 'x')
					return false;
			}
		}
		return true;
	}	
	
	//Returns tha name of the Function entered by the user.
	public String toString()
	{
		return name;
	}
	public void setName(String s)
	{
		name = s;
	}
}