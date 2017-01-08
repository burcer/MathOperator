/************************************************************************************************************
*This Java code is written by "7", whose members are Can Karakuþ, Þükrü Burç Eryýlmaz, Bilgehan Avþer,
*Ömer Sercan Arýk and Onur Günlü.Please include a brief reference comment while using in other applications.
**************************************************************************************************************

This abtract class represents a signle mathematical operation. All the operation classes are descendants
of this class.

Author: Can Karakuþ
*/
public abstract class Operation
{
	public abstract Object execute();
	
	public abstract void addToDefinition(String s);
	
	public abstract void setDefinition(String s);
	
	public abstract String getDefinition();

	public abstract void undo();
	
	public abstract void clear();
	
	//If necessary, display the large or small number in sicentific notation
	public static String scientificNotation( Object number)
	{
		String scientificNotation = "";
		if ( number.getClass().getName().equals( "java.lang.Long"))
			scientificNotation += (Long.toString(( Long)number));
		if ( number.getClass().getName().equals( "java.lang.Double"))
		{
			double numeric = (( Double) number).doubleValue();
			if ( numeric >= 0.001 && numeric < 10000000 )
				scientificNotation += Double.toString(( Double) number);
			else
			{
				String numAsStr = Double.toString(( Double) number);
				int indexOfE = 0;
				for ( int i = 0; i < numAsStr.length() - 1; i ++)
				{
					if ( numAsStr.charAt(i) == 'E')
						indexOfE = i;
				}
				
				String basePart = numAsStr.substring( 0, indexOfE);
				String powerPart;
				powerPart = numAsStr.substring( indexOfE + 1, numAsStr.length());
				scientificNotation += basePart + "\u00B7" +"10";
				
				for ( int k = 0; k < powerPart.length(); k ++)
				{
					if ( powerPart.charAt( k) == '-')
						scientificNotation += "\u207B";	
					if ( powerPart.charAt( k) == '0')
						scientificNotation += "\u2070";
					if ( powerPart.charAt( k) == '1')
						scientificNotation += "\u00B9";
					if ( powerPart.charAt( k) == '2')
						scientificNotation += "\u00B2";	
					if ( powerPart.charAt( k) == '3')
						scientificNotation += "\u00B3";	
					if ( powerPart.charAt( k) == '4')
						scientificNotation += "\u2074";	
					if ( powerPart.charAt( k) == '5')
						scientificNotation += "\u2075";	
					if ( powerPart.charAt( k) == '6')
						scientificNotation += "\u2076";	
					if ( powerPart.charAt( k) == '7')
						scientificNotation += "\u2077";	
					if ( powerPart.charAt( k) == '8')
						scientificNotation += "\u2078";	
					if ( powerPart.charAt( k) == '9')
						scientificNotation += "\u2079";		
				}	
			}
			
		}
		return scientificNotation;

		}
		
}