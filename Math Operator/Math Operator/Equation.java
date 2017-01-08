/************************************************************************************************************
*This Java code is written by "7", whose members are Can Karakuþ, Þükrü Burç Eryýlmaz, Bilgehan Avþer,
*Ömer Sercan Arýk and Onur Günlü.Please include a brief reference comment while using in other applications.
**************************************************************************************************************
*/
import java.util.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/*
 *Equation
 *Finds the roots of an equation either with a given modulo in integers or without modulo in complex plane
 *
 *Author: Sukru Burc Eryilmaz
 */

public class Equation extends Operation
{	
	protected static ArrayList allRoots; //Arraylist of the roots of the current equation
	protected Function f, f1, f2;// f1 is the left and f2 right hand side, f is s.t. f = 0
	protected Function oldf, oldf1, oldf2;
	protected static boolean hasMod;
	protected static long mod;
	protected String definition;
	protected static boolean isRootAvailable;// returns false if the equation is not a polynomial with at most 4th deg
	protected boolean next; //determines whether the first function is selected or the second
	protected boolean hasValidRoots;//returns false if the equation is in the form c = 0
	static NumberFormat numFor2 = NumberFormat.getNumberInstance(new Locale("en", "US"));
	static DecimalFormat frt = (DecimalFormat) numFor2;
	
	/*
	 *Default constructor
	 */
	public Equation()
	{
		allRoots = new ArrayList();
		f = new Function("");
		f1 = new Function("");
		f2 = new Function("");
		frt.applyPattern("0.0000000");
	}
	
	/*
	 *constructor that takes two functions for left and right hand sides
	 */
	public Equation ( Function f1, Function f2)
	{
		this.f1 = f1;
		this.f2 = f2;
	    f = new Function( f1.getDefinition() + "+-(" + f2.getDefinition() + ")");
		definition = f.getDefinition();
		allRoots = new ArrayList();
	}
	
	/*
	 *Constructor that takes two functions n addition to the mod
	 */
	public Equation( Function f1, Function f2, int mod)
	{
		this.f1 = f1;
		this.f2 = f2;
	    f = new Function( f1.getDefinition() + "+-(" + f2.getDefinition() + ")");
		definition = f.getDefinition();
		this.mod = mod;
		allRoots = new ArrayList();
	}
	
	/*
	 *Finds roots of a cubic equation
	 *In finding the roots of a cubic equation, the formula in the site
	 *http://mathworld.wolfram.com/CubicFormula.html has been utilized. The following method
	 *uses this formula.
	 *The site provides the general solution method for a polynomial of third degree.
	*/
	public static Complex[] solveCubic( String coefficients)
	{//exception falan olabilir belki( a3 sýfýrsa)
		NumberFormat numFor2 = NumberFormat.getNumberInstance(new Locale("en", "US"));
		DecimalFormat frt = (DecimalFormat) numFor2;
	
		frt.applyPattern("0.0000000");
		
		StringTokenizer coefGetter = new StringTokenizer( coefficients, "[],");
		double a4 = Double.parseDouble(coefGetter.nextToken());
		double a3 = Double.parseDouble(coefGetter.nextToken());
		double a2 = Double.parseDouble(coefGetter.nextToken());
		double a1 = Double.parseDouble(coefGetter.nextToken());
		double a0 = Double.parseDouble(coefGetter.nextToken());
		
		//coefficients in complex form	
		Complex c3 = new Complex( 1, 0);
		Complex c2 = new Complex( a2 / a3, 0);
		Complex c1 = new Complex( a1 / a3, 0);
		Complex c0 = new Complex( a0 / a3, 0);
		
		//some required variables
		Complex Q = c1.multiply( 3).subtract( c2.pow(2)).divide( 9);
		Complex R = c1.multiply(c2).multiply(9).subtract( c0.multiply(27)).subtract( c2.pow(3).multiply(2)).divide(54);
		Complex D = Q.pow(3).add( R.pow(2));
		Complex S = R.add(D.pow(0.5)).cubeRoot();
		Complex T;
		
		if ( S.getImaginary()!= 0)
			T = new Complex(S.getReal(), - S.getImaginary()); 
		else T = R.subtract(D.pow(0.5)).cubeRoot(); 
		
		Complex[] roots = new Complex[3];
		
		Complex z1 = round(c2.multiply( -1.0/3).add( S.add(T)));
		
		Complex z2 = round(c2.multiply( -1.0/3).subtract( S.add(T).multiply(0.5))
									.add( S.subtract(T).multiply(new Complex( 0, Math.pow(3,0.5)/2))));
						
		Complex z3 = round(c2.multiply( -1.0/3).subtract( S.add(T).multiply(0.5))
								.subtract( S.subtract(T).multiply(new Complex( 0, Math.pow(3,0.5)/2))));
							
		roots[0] = z1;
		roots[1] = z2;
		roots[2] = z3;
		
		return roots;
	}
	
	/*
	 *Finds the roots of a quartic (with degree 4) equation 
	 *In finding the roots of a quartic equation, the formula in the site
	 *http://mathworld.wolfram.com/QuarticEquation.html has been utilized. The following method
	 *uses this formula.
	 *The site provides the general solution method for a polynomial of fourth degree.
	*/
	public static Complex[] solveQuartic( String coefficients)
	{
		NumberFormat numFor2 = NumberFormat.getNumberInstance(new Locale("en", "US"));
		DecimalFormat frt = (DecimalFormat) numFor2;
	
		frt.applyPattern("0.0000000");
		
		StringTokenizer coefGetter = new StringTokenizer( coefficients, "[],");
		double a4 = Double.parseDouble(coefGetter.nextToken());
		double a3 = Double.parseDouble(coefGetter.nextToken());
		double a2 = Double.parseDouble(coefGetter.nextToken());
		double a1 = Double.parseDouble(coefGetter.nextToken());
		double a0 = Double.parseDouble(coefGetter.nextToken());
		
		//converts the polynomial to a monic one
		double u4 = 1;
		double u3 = a3 / a4;
		double u2 = a2 / a4;
		double u1 = a1 / a4;
		double u0 = a0 / a4;
		
		Complex[] rootsOfCubic = solveCubic( "[,0.0,1.0," + (-u2) + "," + ( u1 * u3 - 4 * u0) + "," + ( 4 * u2 * u0 - u1 * u1 - u3 * u3 * u0) + ",]");
		double y = 0;
		
		for ( int i = 0; i < 3; i ++)
		{
			if ( rootsOfCubic[i].getImaginary() == 0)
			{
				y = rootsOfCubic[i].getReal();
				break;
			}			
		}
		
		Complex cy = new Complex( y, 0);
		Complex c4 = new Complex( 1, 0);	
		Complex c3 = new Complex( u3, 0);
		Complex c2 = new Complex( u2, 0);
		Complex c1 = new Complex( u1, 0);
		Complex c0 = new Complex( a0, 0);
		
		Complex R = c3.multiply( c3.multiply( 0.25)).subtract(c2).add(cy).pow(0.5); 
		Complex D;
		Complex E;
		 
		if ( R.getImaginary() == 0 && R.getReal() == 0)
		{
			D = c3.multiply( c3.multiply(0.75)).subtract(c2.multiply(2)).add( cy.multiply(cy).subtract( c0.multiply(4)).pow(0.5).multiply(2)).pow(0.5);
			E = c3.multiply( c3.multiply(0.75)).subtract(c2.multiply(2)).subtract( cy.multiply(cy).subtract( c0.multiply(4)).pow(0.5).multiply(2)).pow(0.5);
		}
		
		else
		{
			D = c3.multiply( c3.multiply(0.75)).subtract(c2.multiply(2)).subtract( R.multiply(R)).add(c2.multiply(c3.multiply(4)).subtract( c1.multiply(8)).subtract(c3.multiply(c3.multiply(c3))).multiply( R.reciprocal().multiply(0.25))).pow(0.5);
			E = c3.multiply( c3.multiply(0.75)).subtract(c2.multiply(2)).subtract( R.multiply(R)).subtract(c2.multiply(c3.multiply(4)).subtract( c1.multiply(8)).subtract(c3.multiply(c3.multiply(c3))).multiply( R.reciprocal().multiply(0.25))).pow(0.5);
		}
		
		Complex[] roots = new Complex[4];
		
		Complex z1 = round(c3.multiply(-0.25).add(R.multiply(0.5)).add(D.multiply(0.5)));
		
		Complex z2 = round(c3.multiply(-0.25).add(R.multiply(0.5)).add(D.multiply(-0.5)));

		Complex z3 = round(c3.multiply(-0.25).add(R.multiply(-0.5)).add(E.multiply(0.5)));
		
		Complex z4 = round(c3.multiply(-0.25).add(R.multiply(-0.5)).add(E.multiply(-0.5)));
	
		roots[0] = z1;
		roots[1] = z2;
		roots[2] = z3;
		roots[3] = z4;
		
		return roots;
	}
	
	//solves a polynomial with deg 2
	public Complex[] solveQuadratic( String coefficients)
	{
		NumberFormat numFor2 = NumberFormat.getNumberInstance(new Locale("en", "US"));
		DecimalFormat frt = (DecimalFormat) numFor2;
	
		frt.applyPattern("0.0000000");
		
		StringTokenizer coefGetter = new StringTokenizer( coefficients, "[],");
	
		double a4 = Double.parseDouble(coefGetter.nextToken());
		double a3 = Double.parseDouble(coefGetter.nextToken());
		double a2 = Double.parseDouble(coefGetter.nextToken());
		double a1 = Double.parseDouble(coefGetter.nextToken());
		double a0 = Double.parseDouble(coefGetter.nextToken());
		
		Complex c2 = new Complex(a2, 0);
		Complex c1 = new Complex(a1, 0);
		Complex c0 = new Complex(a0, 0);
		Complex zero = new Complex(0, 0);
		
		//Descriminant
		Complex D = c1.multiply(c1).subtract(c0.multiply(c2.multiply(4)));
		
		Complex z1 = new Complex(Double.parseDouble(frt.format(zero.subtract(c1).subtract( D.pow(0.5))
												.divide(c2.multiply(2)).getReal())), Double.parseDouble(frt
												.format(zero.subtract(c1).subtract( D.pow(0.5)).divide(c2
												.multiply(2)).getImaginary())));
		
		Complex z2 = new Complex(Double.parseDouble(frt.format(zero.subtract(c1).add( D.pow(0.5))
												.divide(c2.multiply(2)).getReal())), Double.parseDouble(frt
												.format(zero.subtract(c1).add( D.pow(0.5)).divide(c2.multiply(2)).getImaginary())));
		
		Complex[] roots = new Complex[2];
		roots[0] = z1;
		roots[1] = z2;
		return roots;
	}
	
	//solves a polynomial with deg 1(linear equation)
	public Double[] solveLinear( String coefficients)
	{
		NumberFormat numFor2 = NumberFormat.getNumberInstance(new Locale("en", "US"));
		DecimalFormat frt = (DecimalFormat) numFor2;
	
		frt.applyPattern("0.0000000");
		
		StringTokenizer coefGetter = new StringTokenizer( coefficients, "[],");
	
		double a4 = Double.parseDouble(coefGetter.nextToken());
		double a3 = Double.parseDouble(coefGetter.nextToken());
		double a2 = Double.parseDouble(coefGetter.nextToken());
		double a1 = Double.parseDouble(coefGetter.nextToken());
		double a0 = Double.parseDouble(coefGetter.nextToken());
		
		Double[] roots = new Double[1];
		roots[0] = Double.valueOf(frt.format(-a0 / a1));
		return roots;
	}
	
	/*
	 *finds the roots of the given equation considering the modulo mode
	 */
	public Object execute()
	{
		if ( hasMod == true)
		{
			executeWithMod();
			isRootAvailable = true;
		}
		else executeWithoutMod();
		return allRoots;
	}
	
	/*
	 *is invoked when the equation is to be executed and modulo is not specified
	 */
	public void executeWithoutMod()
	{ 
		String coefficients = convertToCoefArray( f.definition);
		StringTokenizer coefGetter = new StringTokenizer( coefficients, "[],");
		double a4 = Double.parseDouble(coefGetter.nextToken());
		double a3 = Double.parseDouble(coefGetter.nextToken());
		double a2 = Double.parseDouble(coefGetter.nextToken());
		double a1 = Double.parseDouble(coefGetter.nextToken());
		double a0 = Double.parseDouble(coefGetter.nextToken());
		Object[] roots;
		
		if( a4 != 0)
		{
			roots = solveQuartic( coefficients);
			for ( int i = 0; i < roots.length; i ++)
			{
				allRoots.add( roots[i]);
			}
			hasValidRoots = true;
		}
		else 
		{
			if ( a3 != 0)
			{
				roots = solveCubic( coefficients);
				for ( int i = 0; i < roots.length; i ++)
				{
					allRoots.add( roots[i]);
				}
				hasValidRoots = true;
			}
			else
			{
				if ( a2 != 0)
				{
					roots = solveQuadratic( coefficients);
					for ( int i = 0; i < roots.length; i ++)
					{
						allRoots.add( roots[i]);
					}
					hasValidRoots = true;
				}
				else
				{
					if ( a1 != 0)
					{
						roots = solveLinear( coefficients);
						allRoots.add(roots[0]);
						hasValidRoots = true;
					}
					else hasValidRoots = false; //there are either no roots or infinitely many roots	
				}
			}
		}
	}
	
	//returns a string as coefficient array of an expression without paranthysis 
	public static String rearrange( String d)
	{
		
		StringTokenizer termseperator = new StringTokenizer( d, "+");
	    String [] terms = new String[termseperator.countTokens()];
		int i = 0;
		while( termseperator.hasMoreTokens())
	    {
	    	terms[i] = termseperator.nextToken();
	    	i ++;
	    }
	    int[] signs = new int[ terms.length];
	    for ( int k = 0; k < terms.length; k ++)
	    {
	    	int index = 0;
	    	while ( terms[ k].charAt( index) == '-')
	    		index ++;
	    	if ( index % 2 == 0)
	    		signs[ k] = 1;
	    	else signs[ k] = -1;
	    	terms[k] = terms[k].substring( index, terms[k].length());
	    }
	    StringTokenizer factorsep;
	    double[] coefficients = new double[ 5];
	    for( int j = 0; j < 5; j ++)
	    	coefficients[j] = 0;
	    String factor;
	    for ( int k = 0; k < terms.length; k ++)
	    {
	    	
	    	double c = 1;//coefficient
	    	int p = 0;//power
	    	factorsep = new StringTokenizer( terms[k], "*");
	    	while (factorsep.hasMoreTokens())
	    	{
	    		factor = factorsep.nextToken();
	    		if ( factor.equals( "x"))
	    			p ++;
	    		if ( factor.length() > 2 && factor.substring(0, 2).equals( "xp"))
	    		{
	    			p += Integer.parseInt( factor.substring(2, factor.length() - 1));
	    		}
	    		if (factor.charAt(0) != 'x') c *= Double.parseDouble( factor);
	    		
	    	}
	    
	    	try
	    	{
	    		coefficients[ p] += signs[k] * c;	
	    		isRootAvailable = true;
	    	}
	    	catch( ArrayIndexOutOfBoundsException e)
	    	{
	    		isRootAvailable = false;
	    	}
	    }
	    
	    String toReturn = "[," + coefficients[4] + "," + coefficients[3] + "," + coefficients[2];
	    toReturn += "," + coefficients[1] + "," + coefficients[0] + ",]";
	    return toReturn; 		
	}
	
	//converts terms that are not included in a paranthyse which does not include another parant.
	public static String convertIndTerms( String d)
	{
		d = eliminateMinus( d);
		String toReturn = new String(d);
		StringTokenizer termSep = new StringTokenizer( d, "+-*[]");
		String term;
		int index;
		int length;
		String[] tokens = new String [ termSep.countTokens()];
		int i = 0;
		
		while ( termSep.hasMoreTokens())
		{
			tokens[ i] = termSep.nextToken();
			i ++;			
		}
		
		for ( int k = 0; k < tokens.length ; k ++)
		{
			length = tokens[k].length();
			for ( int j = 0; j <= d.length() - length; j ++)
			{
				if ( d.substring( j, j + length).equals( tokens[k]))
				{
					if ( j == 0 )
					{
						d = replace( j, j + length, d, rearrange(d.substring( j, j + length)));
						break;
					}
					else if ( j + length == d.length() )
					{
						d = replace( j, d.length(), d, rearrange(d.substring( j, j + length)));
						break;
					}
					else 
					{
						
						if (d.charAt( j + length) == '*'|| d.charAt( j + length) == '+'||d.charAt( j + length) == '-'|| d.charAt( j - 1) == '+'|| d.charAt(j - 1) == '*' || d.charAt( j - 1) == '-')
					    {
					    	if ( j == 1 && d.charAt(0) == '-')
					    	{
					    		d = replace( 0, j + length, d, convertToNeg( rearrange(d.substring(j, j + length))));
					    		break;
					    	}
					    	else if( j > 1 && d.substring( j - 2, j).equals( "[-"))
					    	{
					    		d = replace( j - 1, j + length, d, convertToNeg( rearrange(d.substring(j, j + length))));
					    		break;
					    	}
					    	else
					    	{
					    		d = replace( j, j + length, d, rearrange(d.substring( j, j + length)));
					   			break;
					   		}
					   	}
					    else if (d.charAt(j - 1)=='[' && d.charAt(j + length) == ']')
					    {
					    	d = replace( j - 1, j + length + 1, d, rearrange(d.substring(j, j + length)));
					    	
					    }
					}
				}
			}
		}
		
		return d;	
	}
	
	//converts a function string to a coefficient array representation
	public static String convertToCoefArray(String definition)
	{
		String toReturn = new String( definition); 
		
		//eliminates unnecessary parantysis
		for(int i = 0; i < toReturn.length(); i++)
		{
			if(toReturn.charAt(i) == ')')
			{
				for(int j=i-1; j>=0; j--)
				{
					if(toReturn.charAt(j) == '(')
					{	
						boolean noOperator = true;
						for(int k = j+1; k<i; k++)
						{																																																																												
							if(toReturn.charAt(k) == '+' || toReturn.charAt(k) == '*' 
							||toReturn.charAt(k) == '/'||toReturn.charAt(k) == 's' ||
							toReturn.charAt(k) == 'c' ||toReturn.charAt(k) == 't' ||
							toReturn.charAt(k) == 'g' ||toReturn.charAt(k) == 'a' ||
							toReturn.charAt(k) == 'b' ||toReturn.charAt(k) == 'n' ||
							toReturn.charAt(k) == 'd' ||toReturn.charAt(k) == 'l' || 
							toReturn.charAt(k) == 'm' || toReturn.charAt(k) == 'r')
							{
								noOperator = false;
								break;
							}
						}
												
						if(noOperator)
						{
							String rep = toReturn.substring(j+1, i);
							toReturn = Algebra.replace(toReturn, j, i, rep);
							
							toReturn = Algebra.handleDoubleMinus(toReturn);
						}
						
						break;
					}
				}
			}
		}
		
		toReturn = toReturn.replace( ')', ']');	
		toReturn = toReturn.replace('(', '[');	
		toReturn = eliminateMinus(toReturn);	
		toReturn = convertIndTerms(toReturn);
		
		int p = 0;
		
		for ( int i = 0; i < toReturn.length(); i ++)
		{
			if ( toReturn.charAt(i) == '[')
				p ++; 
		}
		
		int toBack;
		//looks for a "]-]" sequence and converts it to "]]"
		for (int i = 0; i < toReturn.length() - 3; i ++)
		{
			if( toReturn.substring(i, i + 3).equals("[-["))
			{
				for (int j = i + 3; j < toReturn.length(); j ++)
				{
					if ( toReturn.charAt(j) == '[')
						break;
					if ( toReturn.charAt(j) == ']')
					{
						toReturn = replace( i + 1, j + 1, toReturn, convertToNeg(toReturn.substring(i + 2, j + 1)));
						break;
					}
				}
			}
		}
		
		//looks for +, -, * until there are only two paranthyses left 
		while ( p > 1)
		{
			for ( int j = 0; j < toReturn.length() - 1; j ++)
			{
				if ( toReturn.substring( j, j + 2).equals( "]]"))
				{
					for ( int k = j - 1; k > 0; k --)
					{
						if ( toReturn.charAt(k) == ']')
							break;
						else if( toReturn.charAt(k) == '[')
						{
							if ( toReturn.charAt( k - 1) == '[')
							{
								toReturn = replace( k - 1, j + 2, toReturn, toReturn.substring( k, j + 1));
								break;
							}
						}
					}	
				}			
			}
		for (int i = 0; i < toReturn.length() - 3; i ++)
		{
			if( toReturn.substring(i, i + 3).equals("[-["))
			{
				for (int j = i + 3; j < toReturn.length(); j ++)
				{
					if ( toReturn.charAt(j) == '[')
						break;
					if ( toReturn.charAt(j) == ']')
					{
						toReturn = replace( i + 1, j + 1, toReturn, convertToNeg(toReturn.substring(i + 2, j + 1)));
						break;
					}
				}
			}
		}
			
			for( int c = 0; c < toReturn.length() - 3; c ++)
			{
				if( toReturn.substring( c, c + 3).equals( "]*["))
				{
					for( int d = c - 1; d >= 0; d --)
					{
						if ( toReturn.charAt( d) == ']')
							break;
						else if( toReturn.charAt( d) == '[')
						{
							for( int e = c + 3; e < toReturn.length(); e ++ )
							{
								if ( toReturn.charAt( e) == '[')								
									break;
								else if (toReturn.charAt( e) == ']')
								{
									toBack = c - d + 1 - (multiply( toReturn.substring(d, c + 1), toReturn.substring( c + 2, e + 1))).length();
									toReturn = replace(d, e + 1, toReturn, multiply( toReturn.substring(d, c + 1), toReturn.substring( c + 2, e + 1)));
									if( toBack > 0) c -= toBack - 1;
									break;
								}
							}
							break;
						}
					}
				}
			}
			
			for ( int l = 0; l < toReturn.length() - 1; l ++)
			{
				if ( toReturn.substring( l, l + 2).equals( "]]"))
				{
					for ( int k = l - 1; k > 0; k --)
					{
						if ( toReturn.charAt(k) == ']')
							break;
						else if( toReturn.charAt(k) == '[')
						{
							if ( toReturn.charAt( k - 1) == '[')
							{
								toReturn = replace( k - 1, l + 2, toReturn, toReturn.substring( k, l + 1));
								break;
							}
						}
					}	
				}			
			}
			for( int c = 0; c < toReturn.length() - 3; c ++)
			{
				if( toReturn.substring( c, c + 3).equals( "]+["))
				{
					for( int d = c - 1; d >= 0; d --)
					{
						if ( toReturn.charAt( d) == ']')
							break;
						else if( toReturn.charAt( d) == '[')
						{
							for( int e = c + 3; e < toReturn.length(); e ++ )
							{
								if ( toReturn.charAt( e) == '[')								
									break;
								else if ( toReturn.charAt( e) == ']')
								{
									if (((e == toReturn.length() - 1) && (d == 0)) || ((e == toReturn.length() - 1) && (toReturn.charAt(d - 1) != '*'))||((d == 0) && (toReturn.charAt(e + 1) != '*')) || ((toReturn.charAt(d - 1) != '*')&& toReturn.charAt(e + 1) != '*'))
									{
										toBack = c - d + 1 - (add( toReturn.substring(d, c + 1), toReturn.substring( c + 2, e + 1))).length();
										toReturn = replace(d, e + 1, toReturn, add( toReturn.substring(d, c + 1), toReturn.substring( c + 2, e + 1)));
				
										if( toBack > 0) c -= toBack - 1;
				
										break;
									}
								}
							}
							break;
						}
					}
				}
				for ( int l = 0; l < toReturn.length() - 1; l ++)
			{
				if ( toReturn.substring( l, l + 2).equals( "]]"))
				{
					for ( int k = l - 1; k > 0; k --)
					{
						if ( toReturn.charAt(k) == ']')
							break;
						else if( toReturn.charAt(k) == '[')
						{
							if ( toReturn.charAt( k - 1) == '[')
							{
								toReturn = replace( k - 1, l + 2, toReturn, toReturn.substring( k, l + 1));
								break;
							}
						}
					}	
				}			
			}
				
				if(c < toReturn.length() - 3 && toReturn.substring( c, c + 3).equals( "]-["))
				{
				
					for( int d = c - 1; d >= 0; d --)
					{
						if ( toReturn.charAt( d) == ']')
							break;
						else if( toReturn.charAt( d) == '[')
						{
							for( int e = c + 3; e < toReturn.length(); e ++ )
							{
								if ( toReturn.charAt( e) == '[')								
									break;
								else if ( toReturn.charAt( e) == ']')
								{
									if((e == toReturn.length() - 1) || (d == 0))
									{
										if ((e == toReturn.length() - 1) && (d == 0))
										{
										toBack = c - d + 1 - (subtract( toReturn.substring(d, c + 1), toReturn.substring( c + 2, e + 1))).length();
										
										toReturn = replace(d, e + 1, toReturn, subtract( toReturn.substring(d, c + 1), toReturn.substring( c + 2, e + 1)));
									
										if( toBack > 0) c -= toBack - 1;
								
										break;
										}
										else if ( e < toReturn.length() - 1 && toReturn.charAt(e + 1) != '*')
										{
										toBack = c - d + 1 - (subtract( toReturn.substring(d, c + 1), toReturn.substring( c + 2, e + 1))).length();
										toReturn = replace(d, e + 1, toReturn, subtract( toReturn.substring(d, c + 1), toReturn.substring( c + 2, e + 1)));
										if( toBack > 0) c -= toBack - 1;
										break;
										}
										else if ( d > 0 && toReturn.charAt(d - 1) != '*')
										{
										toBack = c - d + 1 - (subtract( toReturn.substring(d, c + 1), toReturn.substring( c + 2, e + 1))).length();
										toReturn = replace(d, e + 1, toReturn, subtract( toReturn.substring(d, c + 1), toReturn.substring( c + 2, e + 1)));
										if( toBack > 0) c -= toBack - 1;
										break;
										}
									}
									else if (toReturn.charAt(e + 1) !='*' && (toReturn.charAt(d - 1) != '*'))
									{
										toBack = c - d + 1 - (subtract( toReturn.substring(d, c + 1), toReturn.substring( c + 2, e + 1))).length();
										toReturn = replace(d, e + 1, toReturn, subtract( toReturn.substring(d, c + 1), toReturn.substring( c + 2, e + 1)));
										if( toBack > 0) c -= toBack - 1;
										break;
									}
									
								}
							}
							break;
						}
					}
			    }
			    for ( int l = 0; l < toReturn.length() - 1; l ++)
			{
				if ( toReturn.substring( l, l + 2).equals( "]]"))
				{
					for ( int k = l - 1; k > 0; k --)
					{
						if ( toReturn.charAt(k) == ']')
							break;
						else if( toReturn.charAt(k) == '[')
						{
							if ( toReturn.charAt( k - 1) == '[')
							{
								toReturn = replace( k - 1, l + 2, toReturn, toReturn.substring( k, l + 1));
								break;
							}
						}
					}	
				}			
			}
			}
	
			p = 0;
			for ( int i = 0; i < toReturn.length(); i ++)
			{
				if ( toReturn.charAt(i) == '[')
					p ++; 
			}
		}
		if ( toReturn.charAt(0) == '-') return convertToNeg(toReturn.substring(1,toReturn.length()));
		else return toReturn; 
	}
	
	/*
	 *finds the roots of the current equation with specified modulo
	 */
	public void executeWithMod()
	{
		hasValidRoots = true;
		double toCheck;
		double rounded;
		long num, denum, intRes;
		long newNum,newDenum;
		for ( int i = 0; i < mod; i ++)
		{
			intRes = 0;
			toCheck = (double)( f.valueAt((double) i));
			rounded = toCheck;
			num = Math.round( rounded * 1000);
			denum = 1000;
			newNum = Math.abs( num) / DAO.gcd( Math.abs( num), denum);
			newDenum = denum / DAO.gcd( Math.abs( num), denum);
			if ( DAO.gcd( mod, newDenum) == 1)
			{
				while ( newNum % newDenum != 0)
				{
					newNum += mod;
				}
						
				intRes = newNum / newDenum;
						
				while ( intRes >= mod)
				{
					intRes -= mod;
				}
						
			}
			if ( toCheck < 0 && intRes != 0)
				intRes = mod - intRes;	
			
			if ( intRes == 0)
				allRoots.add( new Integer(i));	
		}	
	}
	
	/*
	 *converts a coefficient array like -[.....] to [....]
	 */
	public static String convertToNeg( String s)
	{
		StringTokenizer coefficientGetter = new StringTokenizer( s, "[],");
		double[] coef = new double[5];
		int i = 4;
		while ( coefficientGetter.hasMoreTokens())
		{
			coef [i] = Double.parseDouble(coefficientGetter.nextToken());
			i --;
		}
		return "[," + (-coef[4]) + "," + (-coef[3])	+ "," + (-coef[2]) + "," + (-coef[1]) + "," + (-coef[0]) + ",]";
	}
	
	//puts either  - or + instead of multiple signs
	public static String eliminateMinus( String def)
	{
			
		int i = 0;	
		while( def.indexOf( "+-") != -1 || def.indexOf( "--") != -1)
		{
			i = def.indexOf( "+-");
			int x = 0;
			for (int j = i + 1; def.charAt(j) == '-'; j ++)
				x ++;
			if ( x % 2 == 0)
			{
				def = replace( i + 1, i + x + 1, def, "");
			}
			else def = replace( i, i + x + 1, def, "-");
		}
		return def;
	}		
	
	//multiplies two coef string arrays as real polynomial product
	public static String multiply( String a, String b)
	{
		StringTokenizer coefficientGetterA = new StringTokenizer( a, "[],");
		double[] coefA = new double[5];
		int i = 4;
		while ( coefficientGetterA.hasMoreTokens())
		{
			coefA [i] = Double.parseDouble(coefficientGetterA.nextToken());
			i --;
		}
		StringTokenizer coefficientGetterB = new StringTokenizer( b, "[],");
		double[] coefB = new double[5];
		i = 4;
		while ( coefficientGetterB.hasMoreTokens())
		{
			coefB [i] = Double.parseDouble( coefficientGetterB.nextToken());
			i --;
		}
		
		double[] result = new double[5];
		double coef8 = coefA[4] * coefB[4];
		double coef7 = coefA[4] * coefB[3] + coefA[3] * coefB[4];
		double coef6 = coefA[4] * coefB[2] + coefA[3] * coefB[3] + coefA[2] * coefB[4];
		double coef5 = coefA[4] * coefB[1] + coefA[3] * coefB[2] + coefA[2] * coefB[3] + coefA[1] * coefB[4];  
		
		if( coef5 != 0 || coef6 != 0 || coef7 != 0 || coef8 != 0)
			isRootAvailable = false;
		
		result[4] = coefA[0] * coefB[4] + coefA[1] * coefB[3] + coefA[2] * coefB[2] + coefA[3] * coefB[1] + coefA[4] * coefB[0];
		result[3] = coefA[0] * coefB[3] + coefA[1] * coefB[2] + coefA[2] * coefB[1] + coefA[3] * coefB[0];
		result[2] = coefA[0] * coefB[2] + coefA[1] * coefB[1] + coefA[2] * coefB[0];
		result[1] = coefA[0] * coefB[1] + coefA[1] * coefB[0];
		result[0] = coefA[0] * coefB[0];
		
		String toReturn = "[" + "," + result[4] + "," + result[3] + "," + result[2] + "," + result[1] + "," + result[0] + ",]";
		return toReturn;
	}
	
	//adds two coef string arrays as real polynomial addition
	public static String add( String a, String b)
	{
		StringTokenizer coefficientGetterA = new StringTokenizer( a, "[],");
		double[] coefA = new double[5];
		int i = 4;
		while ( coefficientGetterA.hasMoreTokens())
		{
			coefA [i] = Double.parseDouble(coefficientGetterA.nextToken());
			i --;
		}
		StringTokenizer coefficientGetterB = new StringTokenizer( b, "[],");
		double[] coefB = new double[5];
		i = 4;
		while ( coefficientGetterB.hasMoreTokens())
		{
			coefB [i] = Double.parseDouble( coefficientGetterB.nextToken());
			i --;
		}
		
		double[] result = new double[5];
	
		result[4] = coefA[4] + coefB[4];
		result[3] = coefA[3] + coefB[3];
		result[2] = coefA[2] + coefB[2];
		result[1] = coefB[1] + coefA[1];
		result[0] = coefA[0] + coefB[0];
		
		String toReturn = "[" + "," + result[4] + "," + result[3] + "," + result[2] + "," + result[1] + "," + result[0] + ",]";
		return toReturn;
	}
	
	//subtracts from coef string array the second as real polynomial subtraction
	public static String subtract( String a, String b)
	{
		StringTokenizer coefficientGetterA = new StringTokenizer( a, "[],");
		double[] coefA = new double[5];
		int i = 4;
		while ( coefficientGetterA.hasMoreTokens())
		{
			coefA [i] = Double.parseDouble(coefficientGetterA.nextToken());
			i --;
		}
		
		StringTokenizer coefficientGetterB = new StringTokenizer( b, "[],");
		double[] coefB = new double[5];
		i = 4;
		while ( coefficientGetterB.hasMoreTokens())
		{
			coefB [i] = Double.parseDouble( coefficientGetterB.nextToken());
			i --;
		}
		
		double[] result = new double[5];
	
		result[4] = coefA[4] - coefB[4];
		result[3] = coefA[3] - coefB[3];
		result[2] = coefA[2] - coefB[2];
		result[1] = coefA[1] - coefB[1];
		result[0] = coefA[0] - coefB[0];
		
		String toReturn = "[" + "," + result[4] + "," + result[3] + "," + result[2] + "," + result[1] + "," + result[0] + ",]";
		return toReturn;
	}
	
	//puts the given string instead of the string between specified indexes
	public static String replace( int i, int j, String initial, String toPut)
	{
			String s1 = initial.substring( 0, i);
			String s2 = initial.substring( j, initial.length());
			return s1 + toPut + s2;
	}
	
	/*
	 *adds given string to the definition of the functions
	 */
	public void addToDefinition( String s)
	{
		if ( next == true)
		{
			oldf2 = new Function( f2.definition);
			f2.addToDefinition(s);
			f = new Function(f1.definition + "+-("+ f2.definition + ")");
		}
		else 
		{
			oldf1 = new Function( f1.definition);
			f1.addToDefinition(s);
			f = new Function(f1.definition + "+-("+ f2.definition + ")");
		}
	}
	
	/*
	 *clears the functions, makes their definitions empty
	 */
	public void clear()
	{
		f1.clear();
		f2.clear();
		f.clear();
	}
	
	/*
	 *undos the last action
	 */
	public void undo()
	{
		String temp;
		if ( next == true)
		{
			temp = f2.definition;
			f2 = new Function ( oldf2.definition);
			oldf2 = new Function( temp);
		}
		else
		{
			temp = f1.definition;
			f1 = new Function ( oldf1.definition);
			oldf1 = new Function( temp);
		}
	}
	
	/*
	 *returns the defnition of one of the functions
	 */
	public String getDefinition()
	{
		if(next)
			return f1.getDefinition();
		else
			return f2.getDefinition();
	}
	
	/*
	 *sets the definition of one of the functions
	 */
	public void setDefinition(String s)
	{
		if(next)
			f1.setDefinition(s);
		else
			f2.setDefinition(s);
	}
	
	/*
	 *returns true if the functions are valid, fales otherwise
	 */
	public boolean syntaxCheck()
	{
		if(!f1.getDefinition().equals("") && !f1.getDefinition().equals(""))
			return true;
		else return false;
	}
	
	/*
	 *sets the modulo as the argument
	 */
	public static void setModulo(int a)
	{
		hasMod = true;
		mod = a;
	}
	
	/*
	 *cancels the modulo selection
	 */
	public static void unsetModulo()
	{
		hasMod = false;
	}
	
	/*
	 *rounds the components of a complex number
	 */
	public static Complex round(Complex c)
	{
		Complex com = new Complex();
		com.setReal(Double.parseDouble(frt.format(c.getReal())));
		com.setImaginary(Double.parseDouble(frt.format(c.getImaginary())));
		return com;
	}

}
