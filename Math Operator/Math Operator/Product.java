/************************************************************************************************************
*This Java code is written by "7", whose members are Can Karakuþ, Þükrü Burç Eryýlmaz, Bilgehan Avþer,
*Ömer Sercan Arýk and Onur Günlü.Please include a brief reference comment while using in other applications.
**************************************************************************************************************

/*
 *Product
 *finds the result of a product series
 *Author: Onur Gunlu
 */

public class Product extends Operation
{

	//Properties
    protected Function f ;
    protected Integer lowerBound;
    protected Integer upperBound;

    //Constructors
    /*
    *default constructor
    */
    public Product()
    {
        f = new Function();
        lowerBound = null;
        upperBound = null;
    }

    //initializes the function, lower and upper bounds as given 
    public Product( Function f, int lowerBound, int upperBound )
    {
        this.f = f;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    //finds the result of the product series
    public Double execute()
    {
        double product = 1;
        for ( int i = lowerBound; i <= upperBound; i++)
        {
            product *= f.valueAt(i);
        }
        Double prodct = new Double( product );
        return prodct;

    }

    //Method returning the function
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

    //Adds String s to the definition of Function
    public void addToDefinition( String s )
    {
        f.oldDefinition = f.definition;
        f.definition += s;
    }

	//returns true if the series is executable, false otherwise
	public boolean syntaxCheck()
    {
        if ( upperBound > lowerBound && lowerBound != null && upperBound != null )
            return true;
        else
            return false;
    }

    //Undos the last action
    public void undo()
    {
        f.definition = f.oldDefinition;
        f.oldDefinition = "";
    }
	
	//clears the properties of the product
    public void clear()
    {
        f.definition = "";
        f.oldDefinition = "";
    }
 
 	//returns the definition
    public String getDefinition()
    {
      	return f.getDefinition();
    }
	
	//sets the definition
    public void setDefinition(String s)
    {
     	f.setDefinition(s);
    }

}
