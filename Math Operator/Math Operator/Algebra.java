/************************************************************************************************************
*This Java code is written by "7", whose members are Can Karakuþ, Þükrü Burç Eryýlmaz, Bilgehan Avþer,
*Ömer Sercan Arýk and Onur Günlü.Please include a brief reference comment while using in other applications.
**************************************************************************************************************
*/
/*
This class provides static methods to be used by other classes. These
static methods process Strings that represent mathematical
operations.

Author: Can Karakuþ
*/
public class Algebra
{
	//A method that takes String s, and replaces its part between indexes i and j with the String rep.
	public static String replace( String s, int j, int i, String rep)
	{	
		String str;
		
		if(j != 0 && i != (s.length()-1))
		{		
			str = s.substring(0, j) + rep + s.substring(i+1, s.length()-1) + s.charAt(s.length()-1);
		}
		else if(j == 0 && i != (s.length()-1))
		{
			str = rep + s.substring(i+1);
		}
		else if( j != 0 && i == (s.length()-1))
		{
			str = s.substring(0,j) + rep;
		}
		else
		{
			str = rep;
		}
		return str;
	}
	
	//If the length of res is less than oldLength, subtracts the difference from i, else does nothing.
	private static int bringBack(int i, double res, int oldLength)
	{
		String stringResult;
		int newLength;
		
		stringResult = "" + res;
		newLength = stringResult.length();
						
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
	
	//If there are consecutive minus signs in String f, removes them
	public static String handleDoubleMinus(String f)
	{
		for(int i=0; i<f.length()-1; i++)
		{
			if(f.substring(i,i+2).equals("--"))
			{
				f = replace(f, i, i+1, "");
			}
		}
		return f;
	}
	
	//Executes the String f, which represents a mathematical operation, and returns the result.
	public static Double execute(String f)
	{	
		double result;
		
		//Remove unnecessary paranthesis
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
							if(f.charAt(k) == '+' || f.charAt(k) == '*' ||f.charAt(k) == '/' ||f.charAt(k) == 'p' ||f.charAt(k) == 's' ||f.charAt(k) == 'c' ||f.charAt(k) == 't' ||f.charAt(k) == 'g' ||f.charAt(k) == 'a' ||f.charAt(k) == 'b' ||f.charAt(k) == 'n' ||f.charAt(k) == 'd' ||f.charAt(k) == 'l' || f.charAt(k) == 'm')
							{
								noOperator = false;
								break;
							}
						}
												
						if(noOperator)
						{
							String rep = f.substring(j+1, i);
							f = replace(f, j, i, rep);
							
							f = handleDoubleMinus(f);
						}
						
						break;
					}
				}
			}
		}

		//Paranthesis that contain only numbers have disappeared
		boolean noParanthesisLeft = false;
		
		for( int i=0; i< f.length(); i++)
		{	
			if(f.charAt(i) == ')')
			{	
				for(int j = i-1; j>=0; j--)
				{
					if(f.charAt(j) == '(')
					{
						double res;		
						int oldLength;
						oldLength = f.substring(j+1,i).length() + 2;
											
						res = operate(f.substring(j+1,i));			//Execute and plug in the result
						f = replace(f, j, i,"" + res);
						
						String buffer = handleDoubleMinus(f);		//Handle double minus case
						
						if(!f.equals(buffer))
							i = i-2;
							
						f = buffer;
																	
						i = bringBack(i, res, oldLength);
						
						break;
					}
				}
			}

		}
		
		result = operate(f);
		return (Double) result;		
	}
	
	//Executes a String that contains no paranthesis
	private static double operate(String f)
	{	
		double r, res;
		
		//Remove unnecesary +s
		if(f.charAt(0) == '+')
		{
			f = replace(f, 0, 0, "");
		}
		
		//CONSTANTS e and pi
		for(int i=0; i<f.length(); i++)
		{
			if(f.charAt(i) == 'e')
			{
				f = replace(f, i, i, "" + Math.E);
				i += 16;
			}
			
			if(f.charAt(i) == 'I')
			{	
				f = replace(f, i, i, "" + Math.PI);
				i += 16;
			}
		}
		
		//OPERATORS - power		
		for(int i=0; i<f.length(); i++)
		{			
			if(f.charAt(i) == 'p')								//Power
			{	
				double x = 0;
				double power = 1;		//just to keep compiler happy
				int j,k;
				
				for(j=i-1; j>=0; j--)
				{
					if(f.charAt(j) == '+' || f.charAt(j) == '*' ||f.charAt(j) == '/' || j == 0)
					{	
						if( j == 0)						
						{	
							x = Double.parseDouble(f.substring(j,i));
						}
						else
						{
							x = Double.parseDouble(f.substring(j+1,i));
						}
												
						break;
					}
				}
				
				for(k=i+1; k<f.length(); k++)
				{
					if(f.charAt(k) == 'P')
					{
						power = Double.parseDouble(f.substring(i+1, k));
						break;
					}
				}
				
				double resultOfThis = Math.pow(x,power);

				if( j == 0)
				{
					int oldLength = f.substring(j, i).length();
										
					f =replace(f, j , k, "" + resultOfThis);
					i = bringBack(i, resultOfThis, oldLength);
				}
				else
				{	
					int oldLength = f.substring(j+1, i).length();
									
					f =replace(f, j+1 , k, "" + resultOfThis);
					i = bringBack(i, resultOfThis, oldLength);
				}
				
				String buffer = handleDoubleMinus(f);		//Handle double minus case
						
				if(!f.equals(buffer))
					i = i-2;
							
				f = buffer;			
			}
			
			//Operators - root
			if(f.charAt(i) == 'r')								//Root
			{	
				double deg = 0;
				double x = 1;		//just to keep compiler happy
				int j,k;
				
				for(j=i-1; j>=0; j--)
				{
					if(f.charAt(j) == '+' || f.charAt(j) == '*' ||f.charAt(j) == '/' || j == 0)
					{	
						if( j == 0)						
						{	
							deg = Double.parseDouble(f.substring(j,i));
						}
						else
						{
							deg = Double.parseDouble(f.substring(j+1,i));
						}
												
						break;
					}
				}
				
				for(k=i+1; k<f.length(); k++)
				{
					if(f.charAt(k) == 'R')
					{
						x = Double.parseDouble(f.substring(i+1, k));
						break;
					}
				}
				
				double resultOfThis = Math.pow(x, 1/deg);

				if( j == 0)
				{
					int oldLength = f.substring(j, i).length();
										
					f =replace(f, j , k, "" + resultOfThis);
					i = bringBack(i, resultOfThis, oldLength);
				}
				else
				{	
					int oldLength = f.substring(j+1, i).length();
									
					f =replace(f, j+1 , k, "" + resultOfThis);
					i = bringBack(i, resultOfThis, oldLength);
				}
				
				String buffer = handleDoubleMinus(f);		//Handle double minus case
						
				if(!f.equals(buffer))
					i = i-2;
							
				f = buffer;			
			}
			
			//Operators - sin
			if(f.charAt(i) == 's')
			{
				double x = 0;
				int j;
				
				for(j=i+1; j<f.length(); j++)
				{
					if(f.charAt(j) == 'S')
					{
						x = Double.parseDouble(f.substring(i+1,j));
						break;
					}
				}

				f = replace(f, i, j, "" + Math.sin(x)); 
				
				String buffer = handleDoubleMinus(f);		//Handle double minus case
						
				if(!f.equals(buffer))
					i = i-2;
							
				f = buffer;
			}
			
			//Operators - cos
			if(f.charAt(i) == 'c')
			{
				double x = 0;
				int j;
				
				for(j=i+1; j<f.length(); j++)
				{
					if(f.charAt(j) == 'C')
					{
						x = Double.parseDouble(f.substring(i+1,j));
						break;
					}
				}
				
				f = replace(f, i, j, "" + Math.cos(x));
				String buffer = handleDoubleMinus(f);		//Handle double minus case
						
				if(!f.equals(buffer))
					i = i-2;
							
				f = buffer;

			}
			
			//Operators - tan
			if(f.charAt(i) == 't')
			{
				double x = 0;
				int j;
				
				for(j=i+1; j<f.length(); j++)
				{
					if(f.charAt(j) == 'T')
					{
						x = Double.parseDouble(f.substring(i+1,j));
						break;
					}
				}
				
				f = replace(f, i, j, "" + Math.tan(x));
				String buffer = handleDoubleMinus(f);		//Handle double minus case
						
				if(!f.equals(buffer))
					i = i-2;
							
				f = buffer;
			}
			
			//Operators - cotangent
			if(f.charAt(i) == 'g')
			{
				double x = 0;
				int j;
				
				for(j=i+1; j<f.length(); j++)
				{
					if(f.charAt(j) == 'G')
					{
						x = Double.parseDouble(f.substring(i+1,j));
						break;
					}
				}
				f = replace(f, i, j, "" + (1.0 /Math.tan(x)));
				String buffer = handleDoubleMinus(f);		//Handle double minus case
						
				if(!f.equals(buffer))
					i = i-2;
							
				f = buffer;
			}
			
			//Operators - arcsine
			if(f.charAt(i) == 'a')
			{
				double x = 0;
				int j;
				
				for(j=i+1; j<f.length(); j++)
				{
					if(f.charAt(j) == 'A')
					{
						x = Double.parseDouble(f.substring(i+1,j));
						break;
					}
				}
				
				f = replace(f, i, j, "" + Math.asin(x));
				String buffer = handleDoubleMinus(f);		//Handle double minus case
						
				if(!f.equals(buffer))
					i = i-2;
							
				f = buffer;
			}
			
			//Operators - arccosine
			if(f.charAt(i) == 'b')
			{
				double x = 0;
				int j;
				
				for(j=i+1; j<f.length(); j++)
				{
					if(f.charAt(j) == 'B')
					{
						x = Double.parseDouble(f.substring(i+1,j));
						break;
					}
				}
				
				f = replace(f, i, j, "" + Math.acos(x));
				String buffer = handleDoubleMinus(f);		//Handle double minus case
						
				if(!f.equals(buffer))
					i = i-2;
							
				f = buffer;
			}
			
			//Operators - arctangent
			if(f.charAt(i) == 'n')
			{
				double x = 0;
				int j;
				
				for(j=i+1; j<f.length(); j++)
				{
					if(f.charAt(j) == 'N')
					{
						x = Double.parseDouble(f.substring(i+1,j));
						break;
					}
				}
				
				f = replace(f, i, j, "" + Math.atan(x)); 
				String buffer = handleDoubleMinus(f);		//Handle double minus case
						
				if(!f.equals(buffer))
					i = i-2;
							
				f = buffer;
			}
			
			//Operators - arccotangent
			if(f.charAt(i) == 'd')
			{
				double x = 0;
				int j;
				
				for(j=i+1; j<f.length(); j++)
				{
					if(f.charAt(j) == 'D')
					{
						x = Double.parseDouble(f.substring(i+1,j));
						break;
					}
				}
				f = replace(f, i, j, "" + (Math.PI/2.0 - Math.atan(x))); 
				String buffer = handleDoubleMinus(f);		//Handle double minus case
						
				if(!f.equals(buffer))
					i = i-2;
							
				f = buffer;
			}
			
			//Operators - ln
			if(f.charAt(i) == 'l')
			{
				double x = 0;
				int j;
				
				for(j=i+1; j<f.length(); j++)
				{
					if(f.charAt(j) == 'L')
					{
						x = Double.parseDouble(f.substring(i+1,j));
						break;
					}
				}
												
				f = replace(f, i, j, "" + Math.log(x));
				String buffer = handleDoubleMinus(f);		//Handle double minus case
						
				if(!f.equals(buffer))
					i = i-2;
							
				f = buffer;
			}
			
			//Operators - log
			if(f.charAt(i) == 'm')
			{
				double base = 2;
				double x = 1;		//just to keep compiler happy
				int j,k;
				
				for(j=i-1; j>=0; j--)
				{
					if(f.charAt(j) == '+' ||f.charAt(j) == '*' ||f.charAt(j) == '/' || j == 0)
					{	
						if( j == 0)
						{	
							base = Double.parseDouble(f.substring(j,i));
						}
						else
						{
							base = Double.parseDouble(f.substring(j+1,i));
						}
						break;
					}
				}
				
				for(k=i+1; k<f.length(); k++)
				{
					if(f.charAt(k) == 'M')
					{
						x = Double.parseDouble(f.substring(i+1, k));
						break;
					}
				}
				
				double resultOfThis = Math.log(x) / Math.log(base);

				if( j == 0)
				{
					int oldLength = f.substring(j, i).length();
										
					f =replace(f, j , k, "" + resultOfThis);
					i = bringBack(i, resultOfThis, oldLength);
				}
				else
				{	
					int oldLength = f.substring(j+1, i).length();
									
					f =replace(f, j+1 , k, "" + resultOfThis);
					i = bringBack(i, resultOfThis, oldLength);
				}
			
				String buffer = handleDoubleMinus(f);		//Handle double minus case
						
				if(!f.equals(buffer))
					i = i-2;
							
				f = buffer;
			}
			
		}
		
		//Only (+,-,*,/) left
		
		//Handle *S AND /S
		for( int i=0; i<f.length(); i++)
		{		
			if(f.charAt(i) == '*' || f.charAt(i) == '/')
			{
				for(int j=i-1; j>=0; j--)
				{
					if(f.charAt(j) == '*' || f.charAt(j) == '/' || f.charAt(j) == '+' || j == 0)
					{
						for(int k=i+1; k<f.length(); k++)
						{
							if(f.charAt(k) == '*' || f.charAt(k) == '/' || f.charAt(k) == '+' || k == f.length()-1)
							{
								if(f.charAt(i) == '*')
								{
									r = arithmetic(f, '*', j, i, k);

									if(k != f.length()-1 && j != 0)
									{
										int oldLength = f.substring(j+1,i).length();
										f = replace(f, j+1, k-1, "" + r);
										i = bringBack(i, r, oldLength);
									}
									else if(k == f.length()-1 && j != 0)
									{	
										int oldLength = f.substring(j+1,i).length();
										f = replace(f, j+1, k, "" + r);
										i = bringBack(i, r, oldLength);
									}
									else if(k != f.length()-1 && j == 0)
									{	
										int oldLength = f.substring(j,i).length();
										f = replace(f, j, k-1, "" + r);
										i = bringBack(i, r, oldLength);
									}
									else
									{	
										int oldLength = f.substring(j,i).length();
										f = replace(f, j, k, "" + r);
										i = bringBack(i, r, oldLength);
									}
									//System.out.println(f);
								}
								else if(f.charAt(i) == '/')
								{
									r = arithmetic(f, '/', j, i, k);
					
									if(k != f.length()-1 && j != 0)
									{
										int oldLength = f.substring(j+1,i).length();
										f = replace(f, j+1, k-1, "" + r);
										i = bringBack(i, r, oldLength);
									}
									else if(k == f.length()-1 && j != 0)
									{	
										int oldLength = f.substring(j+1,i).length();
										f = replace(f, j+1, k, "" + r);
										i = bringBack(i, r, oldLength);
									}
									else if(k != f.length()-1 && j == 0)
									{	
										int oldLength = f.substring(j,i).length();
										f = replace(f, j, k-1, "" + r);
										i = bringBack(i, r, oldLength);
									}
									else
									{	
										int oldLength = f.substring(j,i).length();
										f = replace(f, j, k, "" + r);
										i = bringBack(i, r, oldLength);
									}
									//System.out.println(f);
								}
								String buffer = handleDoubleMinus(f);		//Handle double minus case
						
								if(!f.equals(buffer))
									i = i-2;
							
								f = buffer;
							}
						}
					}
				}
			}
		}
		
		//Handle +s
		for( int i=0; i<f.length(); i++)
		{
			if(f.charAt(i) == '+')
			{				
				for(int j=i-1; j>=0; j--)
				{	
					if(f.charAt(j) == '+' || j == 0)
					{
						for(int k=i+1; k<f.length(); k++)
						{
							if(f.charAt(k) == '+' || k == f.length()-1)
							{	
								if(f.charAt(i) == '+')
								{
									r = arithmetic(f, '+', j, i, k);
									
									if(k != f.length()-1 && j != 0)
									{
										int oldLength = f.substring(j+1,i).length();
										f = replace(f, j+1, k-1, "" + r);
										i = bringBack(i, r, oldLength);
									}
									else if(k == f.length()-1 && j != 0)
									{	
										int oldLength = f.substring(j+1,i).length();
										f = replace(f, j+1, k, "" + r);
										i = bringBack(i, r, oldLength);
									}
									else if(k != f.length()-1 && j == 0)
									{	
										int oldLength = f.substring(j,i).length();
										f = replace(f, j, k-1, "" + r);
										i = bringBack(i, r, oldLength);
									}
									else
									{	
										int oldLength = f.substring(j,i).length();
										f = replace(f, j, k, "" + r);
										i = bringBack(i, r, oldLength);
									}
									//System.out.println(f);
									String buffer = handleDoubleMinus(f);		//Handle double minus case
						
									if(!f.equals(buffer))
										i = i-2;
							
									f = buffer;
								
								}
							}
						}
					}
				} 
			}
		}
		
		res = Double.parseDouble(f);
		return res;

	}
	
	//Executes addition, subtraction, multiplication and division
	private static double arithmetic( String f, char op, int j, int i, int k)
	{	
		String first, second;
		double firstNum, secondNum, result = 0;
		
		//Determine the numbers
		if( j != 0 && k != f.length()-1)
		{	
			first = f.substring(j+1, i);
			second = f.substring(i+1, k);
		}
		else if( j == 0 && k != f.length()-1)
		{
			first = f.substring(0, i);
			second = f.substring(i+1, k);
		}
		else if(j != 0 && k == f.length()-1)
		{
			first = f.substring(j+1, i);
			second = f.substring(i+1, k) + f.charAt(k);
		}
		else 
		{
			first = f.substring(0,i);
			second = f.substring(i+1, k) + f.charAt(k);
		}
		
		firstNum = Double.parseDouble(first);
		secondNum = Double.parseDouble(second);
		
		//Determine the operation and operate
		if(op == '*')
		{
			result = firstNum * secondNum;
		}
		else if(op == '/')
		{
			result = firstNum / secondNum;
		}
		else if(op == '+')
		{
			result = firstNum + secondNum;
		}
		
		return result;	
	
	}
	
}