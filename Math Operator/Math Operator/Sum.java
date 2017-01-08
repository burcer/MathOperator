/************************************************************************************************************
*This Java code is written by "7", whose members are Can Karakuþ, Þükrü Burç Eryýlmaz, Bilgehan Avþer,
*Ömer Sercan Arýk and Onur Günlü.Please include a brief reference comment while using in other applications.
**************************************************************************************************************

/*
 *Sum
 *evaluates given sum series
 *Author: Onur Gunlu
 */

public class Sum extends Operation
{

    //Properties
    protected Function f;
    protected Integer lowerBound;
    protected Integer upperBound;
				
    //Constructors
    /*
     *default constructor
     */
    public Sum()
    {
        f = new Function();
        lowerBound = null;
        upperBound = null;
    }

    /*
     *initializes the function, lower and upper bounds
     */
    public Sum( Function f, int lowerBound, int upperBound )
    {
        this.f = f;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }
        		
	//methods
	/*
	 *finds the result of the sum series
	 */
    public Double execute()
    {
        double sum = 0;

        for ( int i = lowerBound; i <= upperBound; i++)
        {
             sum += f.valueAt(i);
        }
        return (Double)sum;
    }

    //getter method for the function
    public Function getFunction()
    {
        return f;
    }

    //Method returning upperBound
    public int getUpperBound()
    {
        return upperBound;
    }

    //Method returning lowerBound
    public int getLowerBound()
    {
        return lowerBound;
    }

    //Method setting upperBound
    public void setUpperBound( int upperBound)
    {
        this.upperBound = upperBound;
    }

    //Method setting lowerBound
    public void setLowerBound( int lowerBound)
    {
        this.lowerBound = lowerBound;
    }

    //adds the given string to the definition
    public void addToDefinition( String s )
    {
        f.oldDefinition = f.definition;
        f.definition += s;
    }

    //returns true if the series is executable, false otherwise
    public boolean syntaxCheck()
    {
        if ( upperBound > lowerBound && upperBound != null && lowerBound != null)
            return true;
        else
            return false;
    }
		
	//undos the last action
    public void undo()
    {
        f.definition = f.oldDefinition;
    }
	
	//clears the properties of the series
    public void clear()
    {
        f.definition = "";
        f.oldDefinition = "";
    }
        
    //getter method for the definition
    public String getDefinition()
    {
     	return f.getDefinition();
    }
        
    //setter method for the definition
	public void setDefinition(String s)
    {
      	f.setDefinition(s);
    }
}

