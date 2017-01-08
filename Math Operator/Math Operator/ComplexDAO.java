/************************************************************************************************************
*This Java code is written by "7", whose members are Can Karakuþ, Þükrü Burç Eryýlmaz, Bilgehan Avþer,
*Ömer Sercan Arýk and Onur Günlü.Please include a brief reference comment while using in other applications.
**************************************************************************************************************

This class represents algebraic operations that involves complex numbers.Uses the static methods that are
provided by ComplexAlgebra

Author: Can Karakuþ
*/
import java.util.ArrayList;

public class ComplexDAO extends Operation
{
	private String definition;
	private String oldDefinition;
	
	public ComplexDAO()
	{
		definition = "";
		oldDefinition = "";

	}
	
	public ComplexDAO(String def)
	{
		definition = def;
		oldDefinition = "";
	}
	
	public Complex execute()
	{
		return ComplexAlgebra.execute(definition);
	}
	
	public String getDefinition()
	{
		return definition;
	}
	
	public void addToDefinition(String s)
	{
		oldDefinition = definition;
		definition += s;
	}
	
	public void setDefinition(String s)
	{
		oldDefinition = definition;
		definition = s;
	}
	
	public void undo()
	{
		definition = oldDefinition;
	}
	
	//Converts the complex number c to the scientific notation as necessary.
	public static String scientificNotation(Complex c)
	{
		if(c.getImaginary() > 0 && c.getReal() != 0)
			return Operation.scientificNotation(c.getReal()) +  "+" +Operation.scientificNotation(c.getImaginary()) + "i";
		
		else if(c.getImaginary() == 0 && c.getReal() != 0)
			return Operation.scientificNotation(c.getReal());
		
		else if(c.getImaginary() < 0 && c.getReal() != 0)
			return Operation.scientificNotation(c.getReal()) + Operation.scientificNotation(c.getImaginary());
		
		else
			return Operation.scientificNotation(c.getImaginary());
	}
	
	public void clear()
	{
		oldDefinition = "";
		definition = "";	
	}
	
	//Checks the syntax of the expression
	public boolean syntaxCheck()
	{	
		//condition 1: One of the operators +,-,*,/ must occur just before and after the complex number
		//condition 2: Empty complex number
		//condition 3: No operations within a complex number
		for(int i=0; i<definition.length(); i++)
		{
			if(definition.charAt(i) == '[' && i != 0)
			{
				if(definition.charAt(i-1) != '+' && definition.charAt(i-1) != '-' && definition.charAt(i-1) != '*' && definition.charAt(i-1) != '/')	
					return false;
			}
			if(definition.charAt(i) == '[')
			{	
				if(definition.charAt(i+1) == ']')
					return false;			
				
				for(int j = i+1; j<definition.length(); j++)
				{
					ArrayList<Character> chars = new ArrayList<Character>();
					
					while(definition.charAt(j) != ']')
					{
						chars.add((Character)definition.charAt(j));
						j++;
					}
					
					if(chars.contains((Character)'p') || chars.contains((Character)'s') || chars.contains((Character)'c') ||chars.contains((Character)'t') ||chars.contains((Character)'g') ||chars.contains((Character)'a') ||chars.contains((Character)'b') ||chars.contains((Character)'n') ||chars.contains((Character)'d') ||chars.contains((Character)'m') ||chars.contains((Character)'l') ||chars.contains((Character)'*') ||chars.contains((Character)'/'))
						return false;
				}
				//----------: If it is inside an operation...
				for(int j = i-1; j>=0; j--)
				{
					String op;
					if(definition.charAt(j) == 'p' || definition.charAt(j) == 's' ||definition.charAt(j) == 'c' ||definition.charAt(j) == 't' ||definition.charAt(j) == 'g' ||definition.charAt(j) == 'a' ||definition.charAt(j) == 'b' ||definition.charAt(j) == 'n' ||definition.charAt(j) == 'd' ||definition.charAt(j) == 'l' ||definition.charAt(j) == 'm')
					{
						op = definition.charAt(j) + "";
						
						for(int k=i; k<definition.length(); k++)
						{
							if((definition.charAt(k) + "").equals( op.toUpperCase()))
							{
								return false;
							}
						}
						
						break;
					}
				}
				//----------
				//Check non-complex parts
				String s = definition;
				for(int j = i-1; j>=0; j--)
				{
					if(s.charAt(j) == '[')
					{
						s = Algebra.replace(s, j, i, "0");
						//Invoke syntaxCheck(String) in DAO.
					}		
				}	
			}
			
			else if(definition.charAt(i) == ']' && i != definition.length()-1)
			{
				if(definition.charAt(i+1) != '+' && definition.charAt(i+1) != '*' && definition.charAt(i+1) != '/')	
					return false;
			}
		}
		
		return true;
	}
}