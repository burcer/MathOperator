/************************************************************************************************************
*This Java code is written by "7", whose members are Can Karakuþ, Þükrü Burç Eryýlmaz, Bilgehan Avþer,
*Ömer Sercan Arýk and Onur Günlü.Please include a brief reference comment while using in other applications.
**************************************************************************************************************

This class provides static methods for the usage of ComplexDAO. These methods process a String that represent
a mathematical operation which involves complex numbers.

Author: Can Karakuþ
*/
public class ComplexAlgebra
{
	//If the length of res is less than oldLength, subtracts the difference from i, else does nothing.
	private static int bringBack( int i, String result, int oldLength)
	{
		int newLength = result.length();
						
		if(newLength < oldLength)		
		{
			i -= 1 + (oldLength-newLength);
		}
		else
		{
			i--;
		}

		return i;
	}
	
	//Executes the String that represent a mathematical operation and returns the result.
	public static Complex execute( String f)
	{
		String result;
		
		//Remove unnecesary paranthesis
		for(int i=0; i<f.length(); i++)
		{
			if(f.charAt(i) == ')')
			{
				for(int j=i-1; j>=0; j--)
				{
					if(f.charAt(j) == '(')
					{	
						boolean noOperator = true;
						for(int k = j+1; k<i; k++)
						{
							if(f.charAt(k) == '+' || f.charAt(k) == '*' ||f.charAt(k) == '/')
							{
								noOperator = false;
								break;
							}
						}
						if(noOperator)
						{
							String rep = f.substring(j+1, i);
							f = Algebra.replace(f, j, i, rep);
						}
						
						break;
					}
				}
			}
		}
		//Unnecesary paranthesis have been removed...
		Complex c;
		double re;
		double im;
		
		if(noOperationCheck(f))
		{
			c = (Complex.stringToComplex(f));
			re = c.getReal();
			im = c.getImaginary();
		}
		else
		{
			for(int i=0; i<f.length(); i++)
			{	
				if(f.charAt(i) == ')')
				{
					for(int j=i-1; j>=0; j--)
					{
						if(f.charAt(j) == '(')
						{	
							result = operate(f.substring(j+1, i));
												
							int oldLength = f.substring(j+1, i).length() + 2;
							f = Algebra.replace( f, j, i, result);
							i = bringBack(i, result, oldLength);
						
							break;
						}
					}
				}
			}
	
			result = operate(f);
			f = Algebra.replace( f, 0, f.length()-1, result);
			re = 0;
			im = 0;
		
		
			for(int i=0; i<f.length(); i++)
			{
				if(f.charAt(i) == '_')
				{
					re = Double.parseDouble(f.substring(1,i));
					im = Double.parseDouble(f.substring(i+1, f.length()-2));
				}
			}
		}
		
		return new Complex(re, im);
		
	}
	
	//Executes the Strings that contain no paranthesis
	private static String operate(String f)
	{
		if(f.charAt(0) == '+')
		{
			f = Algebra.replace(f, 0, 0, "");
		}
		
		for(int i=0; i<f.length(); i++)
		{
			if(f.charAt(i) == '*' || f.charAt(i) == '/')
			{
				for(int j = i-1; j>=0; j--)
				{
					if(f.charAt(j) == '+' || f.charAt(j) == '*' || f.charAt(j) == '/' || j == 0)
					{
						for(int k = i+1; k<f.length(); k++)
						{
							if(f.charAt(k) == '+' || f.charAt(k) == '*' || f.charAt(k) == '/' || k == f.length()-1)
							{
								if(j != 0 && k!= f.length()-1)
								{
									Complex c1 = Complex.stringToComplex(f.substring(j+1, i));
									Complex c2 = Complex.stringToComplex(f.substring(i+1, k));
									
									int oldLength = f.substring(j+1, i).length();
									
									if(f.charAt(i) == '*')
									{
										String resultOfThis = c1.multiply(c2).complexToString();
										f = Algebra.replace(f, j+1, k-1, resultOfThis);
										i = bringBack(i, resultOfThis, oldLength);
									}
									else if(f.charAt(i) == '/')
									{
										String resultOfThis = c1.divide(c2).complexToString();
										f = Algebra.replace(f, j+1, k-1, resultOfThis);
										i = bringBack(i, resultOfThis, oldLength);
									}
								}
								else if(j == 0 && k!= f.length()-1)
								{
									Complex c1 = Complex.stringToComplex(f.substring(j, i));
									Complex c2 = Complex.stringToComplex(f.substring(i+1, k));
									
									int oldLength = f.substring(j, i).length();
									
									if(f.charAt(i) == '*')
									{
										String resultOfThis = c1.multiply(c2).complexToString();
										f = Algebra.replace(f, j, k-1, resultOfThis);
										i = bringBack(i, resultOfThis, oldLength);
									}
									else if(f.charAt(i) == '/')
									{
										String resultOfThis = c1.divide(c2).complexToString();
										f = Algebra.replace(f, j, k-1, resultOfThis);
										i = bringBack(i, resultOfThis, oldLength);
									}
								}
								else if(j != 0 && k== f.length()-1)
								{	
									Complex c1 = Complex.stringToComplex(f.substring(j+1, i));
									Complex c2 = Complex.stringToComplex(f.substring(i+1, k) + f.charAt(k));
									
									int oldLength = f.substring(j+1, i).length();
									
									if(f.charAt(i) == '*')
									{
										String resultOfThis = c1.multiply(c2).complexToString();
										f = Algebra.replace(f, j+1, k, resultOfThis);
										i = bringBack(i, resultOfThis, oldLength);
									}
									else if(f.charAt(i) == '/')
									{
										String resultOfThis = c1.divide(c2).complexToString();
										f = Algebra.replace(f, j+1, k, resultOfThis);
										i = bringBack(i, resultOfThis, oldLength);
									}
								}
								else
								{
									Complex c1 = Complex.stringToComplex(f.substring(j, i));
									Complex c2 = Complex.stringToComplex(f.substring(i+1, k) + f.charAt(k));
									
									int oldLength = f.substring(j, i).length();
									
									if(f.charAt(i) == '*')
									{
										String resultOfThis = c1.multiply(c2).complexToString();
										f = Algebra.replace(f, j, k, resultOfThis);
										i = bringBack(i, resultOfThis, oldLength);
									}
									else if(f.charAt(i) == '/')
									{
										String resultOfThis = c1.divide(c2).complexToString();
										f = Algebra.replace(f, j, k, resultOfThis);
										i = bringBack(i, resultOfThis, oldLength);
										
									}
								}
								break;
							}	
						}
						break;
					}
				}
			}
		}
		
		for(int i = 0; i<f.length(); i++)
		{
			if(f.charAt(i) == '+')
			{
				for(int j= i-1; j>=0; j--)
				{
					if(f.charAt(j) == '+' || j == 0)
					{
						for(int k = i+1; k<f.length(); k++)
						{
							if(f.charAt(k) == '+' || k == f.length()-1)
							{
								if(j != 0 && k!= f.length()-1)
								{
									Complex c1 = Complex.stringToComplex(f.substring(j+1, i));
									Complex c2 = Complex.stringToComplex(f.substring(i+1, k));
									
									int oldLength = f.substring(j+1, i).length();
									
									String resultOfThis = c1.add(c2).complexToString();
									f = Algebra.replace(f, j+1, k-1, resultOfThis);
									i = bringBack(i, resultOfThis, oldLength);
								}
								else if(j == 0 && k!= f.length()-1)
								{	
									Complex c1 = Complex.stringToComplex(f.substring(j, i));
									Complex c2 = Complex.stringToComplex(f.substring(i+1, k));
									
									int oldLength = f.substring(j, i).length();
									
									String resultOfThis = c1.add(c2).complexToString();
									f = Algebra.replace(f, j, k-1, resultOfThis);
									i = bringBack(i, resultOfThis, oldLength);
								}
								else if(j != 0 && k== f.length()-1)
								{
									Complex c1 = Complex.stringToComplex(f.substring(j+1, i));
									Complex c2 = Complex.stringToComplex(f.substring(i+1, k) + f.charAt(k));
									
									int oldLength = f.substring(j+1, i).length() + 1;
									
									String resultOfThis = c1.add(c2).complexToString();
									f = Algebra.replace(f, j+1, k, resultOfThis);
									i = bringBack(i, resultOfThis, oldLength);
								}
								else
								{
									Complex c1 = Complex.stringToComplex(f.substring(j, i));
									Complex c2 = Complex.stringToComplex(f.substring(i+1, k) + f.charAt(k));
												
									int oldLength = f.substring(j, i).length() + 1;
																		
									String resultOfThis = c1.add(c2).complexToString();
									f = Algebra.replace(f, j, k, resultOfThis);
									i = bringBack(i, resultOfThis, oldLength);
								}
							//	System.out.println(f);
								break;
							}
						}
						break;
					}
				}
			}
		}
		
	return f;
	}
	
	//Chekcs if String f has none of the operations +, * and / in it.
	private static boolean noOperationCheck(String f)
	{
		boolean noOperation = true;
		for(int i=0; i<f.length(); i++)
		{
			if(f.charAt(i) == '+' || f.charAt(i) == '*' || f.charAt(i) == '/')
			{
				noOperation = false;
				break;
			}
		}
		return noOperation;
	}
	
}