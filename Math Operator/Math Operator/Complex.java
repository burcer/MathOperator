/************************************************************************************************************
*This Java code is written by "7", whose members are Can Karakuþ, Þükrü Burç Eryýlmaz, Bilgehan Avþer,
*Ömer Sercan Arýk and Onur Günlü.Please include a brief reference comment while using in other applications.
************************************************/

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
/*
 *Complex
 *Creates complex numbers and makes various operations using them
 *
 *Author : Sercan Arýk
 */
public class Complex{
	
	//properties
	//real and imaginary parts of the complex number as usual 
	private double real;
	private double imaginary;
	
	NumberFormat numFor2 = NumberFormat.getNumberInstance(new Locale("en", "US"));
	DecimalFormat frt = (DecimalFormat) numFor2;
	
	//constructors
	/*
	 *Default constructor
	 */
    public Complex()
    {
    	real = 0;
    	imaginary = 0;
    	frt.applyPattern("0.0000000");
    }

	/*
	 *Constructor that takes imaginary and real values and initializes them as specified
	 */
    public Complex(double realP, double imaginaryP){
    	 real = realP;
    	 imaginary = imaginaryP;
    	 frt.applyPattern("0.0000000");
  	}
  	
  	//methods
  	/*
  	 *adds the given complex to the current complex number and returns the result
  	 */
	public Complex add (Complex c) 
 	{
   	 	 Complex z = new Complex();
         z.imaginary = c.imaginary + imaginary;
         z.real = c.real + real;
         return z;   
    }

	 /*
	  *subtracts from the current number the given number and returns the result
	  */
     public Complex subtract (Complex c)
     {
     	 Complex z = new Complex();
     	 z.real =  real-c.real;
   	 	 z.imaginary = imaginary-c.imaginary;
         return z;
     }
     
     /*
      *multiplies the given complex number with the current number and returns the result
      */
     public Complex multiply (Complex c)
     {
         Complex z = new Complex();
         z.real = real*c.real - imaginary*c.imaginary;
         z.imaginary = real*c.imaginary + c.real*imaginary; 
         return z;
     }
     
     /*
      *multiplies the given real number with the current complex and returns the result
      */
     public Complex multiply(double i)
     {
     	return new Complex(this.real*i, this.imaginary*i);
     }
     
     /*
      *takes the reciprocal of a given complex as a complex and returns the result
      */
     public Complex reciprocal ()
     {
         Complex z = new Complex();
         z.real = real*(1/((real*real)+(imaginary*imaginary)));
         z.imaginary = imaginary*(-1/((real*real)+(imaginary*imaginary))); 
         return z;
     }
     
     /*
      *divides the current complex with the given complex and returns the result
      */
     public Complex divide (Complex c)
     {
     	return (multiply(c.reciprocal()));
     }
     
     /*
      *divides the current complex with the given real and returns the result
      */
     public Complex divide(double i)
     {
     	return new Complex(this.real/i, this.imaginary/i);
     }
     
     /*
      *takes the given real power of the current complex number and returns the result
      */
     public Complex pow(double d)
     {
     	double newNorm = Math.pow(getNorm(),d);
     	double newArg = getArgument()*d;
     	double newReal = newNorm*Math.cos(newArg);
     	double newImag = newNorm*Math.sin(newArg);
     	
     	return new Complex(newReal, newImag);
     	
     }
     
     /*
      *finds the cube root of the current complex number and returns the result
      */
     public Complex cubeRoot()
     {
     	NumberFormat numFor2 = NumberFormat.getNumberInstance(new Locale("en", "US"));
		DecimalFormat frt = (DecimalFormat) numFor2;
		frt.applyPattern("0.0000000");
		
		double newArg;
		
     	if(Double.parseDouble(frt.format(getArgument())) == Double.parseDouble(frt.format(Math.PI/2)))
     		newArg = 3*Math.PI/2;
     	else if(Double.parseDouble(frt.format(getArgument())) == Double.parseDouble(frt.format(Math.PI)))
     		newArg = Math.PI;
     	else if(Double.parseDouble(frt.format(getArgument())) == 0.0)
     		newArg = 0;
     	else if(Double.parseDouble(frt.format(getArgument())) == Double.parseDouble(frt.format(3*Math.PI/2)))
     		newArg = Math.PI/2;
     	else 
     		newArg = getArgument()/3;
     		
     	double newNorm = Math.pow(getNorm(), 1.0/3);
     	
     	double re = Math.cos(newArg)*newNorm;
     	double im = Math.sin(newArg)*newNorm;
     	
     	return new Complex(re, im);		
     }
     
     /*
      *returns the distance from the number in the complex plane to the origin
      */
     public double getNorm()
     {
     	 return Math.sqrt(real*real + imaginary*imaginary);
     }
     
     /*
      *returns the angle the complex makes with x axis in positive direction
      */
     public double getArgument()
     {
     	NumberFormat numFor2 = NumberFormat.getNumberInstance(new Locale("en", "US"));
		DecimalFormat frt = (DecimalFormat) numFor2;
		
		frt.applyPattern("0.0000000000");
     	
     	if(Double.parseDouble(frt.format(real))==0 && Double.parseDouble(frt.format(imaginary)) == 0) 
     		return 0.0;
    	else if(Double.parseDouble(frt.format(real))==0 && Double.parseDouble(frt.format(imaginary)) > 0)
    		return Math.PI/2;
    	else if(Double.parseDouble(frt.format(real))==0 && Double.parseDouble(frt.format(imaginary)) < 0)
    		return 3*Math.PI/2;
    	else if(Double.parseDouble(frt.format(real)) > 0 && Double.parseDouble(frt.format(imaginary)) == 0)
    		return 0.0;
    	else if(Double.parseDouble(frt.format(real)) < 0 && Double.parseDouble(frt.format(imaginary)) == 0)
    		return Math.PI;
    	else if(Double.parseDouble(frt.format(real)) < 0 && Double.parseDouble(frt.format(imaginary)) < 0)
    		return 2 * Math.PI - Math.acos(real / getNorm());
    	else if (Double.parseDouble(frt.format(real)) < 0 && Double.parseDouble(frt.format(imaginary)) > 0)
     		return Math.acos(real/getNorm());
     	else if (Double.parseDouble(frt.format(real)) > 0 && Double.parseDouble(frt.format(imaginary)) < 0)
     		return 2 * Math.PI - Math.acos(real/getNorm());
     	else if (Double.parseDouble(frt.format(real)) > 0 && Double.parseDouble(frt.format(imaginary)) > 0)
     		return Math.acos(real/getNorm());
     	else return 0; //just to keep the compiler happy
     	
     }
     
     /*
      *returns the imaginary part of the complex
      */
     public double getImaginary()
     {
     	return imaginary;
     }
     
     /*
      *returns the real part of the complex
      */
     public double getReal()
     {
     	return real;
     }
     
     /*
      *sets the real part of the complex number
      */
     public void setReal(double d)
     {
     	real = d;
     }
     
     /*
      *sets the imaginary part of the complex number
      */
     public void setImaginary(double d)
     {
     	imaginary = d;
     }
     
     /*
      *converts a complex number to a string form
      */
     public String complexToString()
     {
     		return "[" + getReal() + "_" + getImaginary() + "i]";
     }
     
     /*
      *converts a string to complex form
      */
     public static Complex stringToComplex(String c)
     {
		if(c.length() > 2)
		{
			if(c.substring(0,2).equals("[_"))						
				c = Algebra.replace(c, 1, 1, "");
			
			else if(c.substring(1,3).equals("[_"))
				c = Algebra.replace(c, 2, 2, "");
		}
	
		double re = 0;
		double im = 0;
		
		boolean notImaginary = true;
		
		for(int i = 0; i<c.length(); i++)
		{
			if(c.charAt(i) == 'i')
			{
				notImaginary = false;
				break;
			}
		}

		if(notImaginary)
		{
			for(int i = 0; i<c.length(); i++)
			{
				if(c.charAt(i) == '[')
				{
					for(int j = i+1; j<c.length(); j++)
					{
						if(c.charAt(j) == ']')
						{
							c = Algebra.replace(c, i, i, "");
							c = Algebra.replace(c, j-1, j-1, "");
						}
					}
				}
			}
			
			re = Double.parseDouble(c);
			im = 0;
		}
		else
		{
			for(int i=0; i<c.length(); i++)
			{
				if(c.charAt(i) == 'i')
				{
					for(int j=i-1; j>=0; j--)
					{
						if(c.charAt(j) == '_' || c.charAt(j) == '[')
						{	
							try
							{
								if(c.charAt(0) == '-')
									im = (-1) * Double.parseDouble(c.substring(j+1, i));
								else
									im = Double.parseDouble(c.substring(j+1, i));
									
							}
							catch(NumberFormatException e)
							{
								if(c.substring(j+1, i).equals("-") && c.charAt(0) == '-')
									im = 1;
								else if(c.substring(j+1, i).equals("") && c.charAt(0) == '-')
									im = -1;
								else if(c.substring(j+1, i).equals("-") && c.charAt(0) != '-')	
									im = -1;
								else
									im = 1;						
							}
							
							if(c.charAt(j) == '_')
							{
								if(c.charAt(0) == '-')
									re = (-1) * Double.parseDouble(c.substring(2, j));
								else
									re = Double.parseDouble(c.substring(1, j));
							}
							else if(c.charAt(j) == '[')
							{
								if(c.charAt(i+1) == '_')
									re = Double.parseDouble(c.substring(i+2, c.indexOf(']')));
								
								else re = 0;
							}
							break;
						}
					}
				}
			}
	
	
		}
		return new Complex(re, im);
	}
	
	/*
	 *returns the string representation of a complex number
	 */
	public String toString()
	{
		NumberFormat numFor1 = NumberFormat.getNumberInstance(new Locale("en", "US"));
		DecimalFormat ft = (DecimalFormat)numFor1;
		
		NumberFormat numFor2 = NumberFormat.getNumberInstance(new Locale("en", "US"));
		DecimalFormat frt = (DecimalFormat) numFor2;
		
		ft.applyPattern("0.000");
		frt.applyPattern("0.0000000");
		if(Double.parseDouble(frt.format(getImaginary())) > 0 && Double.parseDouble(frt.format(getReal())) != 0)
			return Double.parseDouble(ft.format(getReal())) + "+" + Double.parseDouble(ft.format(getImaginary())) + "i";
		
		else if(Double.parseDouble(frt.format(getImaginary())) < 0 && Double.parseDouble(frt.format(getReal())) != 0)
			return Double.parseDouble(ft.format(getReal())) + "" + Double.parseDouble(ft.format(getImaginary())) +"i";
		
		else if(Double.parseDouble(frt.format(getImaginary())) == 0 && Double.parseDouble(frt.format(getReal())) != 0)
			return Double.parseDouble(ft.format(getReal())) + "";
			
		else if(Double.parseDouble(frt.format(getImaginary())) == 0 && Double.parseDouble(frt.format(getReal())) == 0)
			return "0.0";
		else
			return Double.parseDouble(ft.format(getImaginary())) + "i";
	}
	
	/*
	 *converts the current complex number to polar form
	 */
	public String switchToPolar()
	{
		NumberFormat numFor1 = NumberFormat.getNumberInstance(new Locale("en", "US"));
		DecimalFormat ft = (DecimalFormat)numFor1;
		ft.applyPattern("0.000");
		
		return ft.format(getNorm()) + "cis" + ft.format(Math.toDegrees(getArgument()));
	}
	
	/*
	 *converts polar form to the normal form
	 */
	public String switchToNormal()
	{
		return toString();
	}
	
	/*
	 *returns true if two complex number are equal in terms of their 7 digits after to decimal point,
	 *and false otherwise
	 */ 
	public boolean equals(Complex c)
	{	
		frt.applyPattern("0.0000000");
		
		if( frt.format(Math.abs(this.imaginary)).equals(frt.format(Math.abs(c.imaginary))) 
		 && frt.format(Math.abs(this.real)).equals(frt.format(Math.abs(c.real))))
			return true;
		else
			return false;
	}

}
 	
 	
   