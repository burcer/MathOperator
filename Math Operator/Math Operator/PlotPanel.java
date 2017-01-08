/************************************************************************************************************
*This Java code is written by "7", whose members are Can Karakuþ, Þükrü Burç Eryýlmaz, Bilgehan Avþer,
*Ömer Sercan Arýk and Onur Günlü.Please include a brief reference comment while using in other applications.
**************************************************************************************************************

This class creates the PlotPanel and makes all plotting-related operations. Controlled by Interaction and 
ToolbarPanel

Author: Bilgehan Avþer
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale; 

public class PlotPanel extends JPanel
{
	static final int WIDTH = 442;
	static final int HEIGHT = 366;
	static final int TITLE_HEIGHT = 25;
	static final int GRID_WIDTH = 17;
	static double sens;
	static boolean isPlotModeOn = false;
	static boolean isTangentModeOn = false;
	static boolean isIntegralModeOn = false;
	static Double interval;
	static Double inp;
	static Double [][] results;
	static Function f;
	static Derivative d;
	static double max = 0.0;
	static double hsens;
	static double vsens;
	static double ll;
	static double ul;
	static double unitHeight;
	static double unitWidth;
	static int li;
	static int ui;
	static Color areaColor;
	static Color functionColor;
	static Color tangentColor;
	static int num_of_inp;
	public PlotPanel()
	{
		setLayout(null);
	
	//Title	
		JPanel title = new JPanel();
		title.setBounds(0,0,WIDTH,TITLE_HEIGHT);
		title.setBackground(new Color(128,128,255));
		
		JLabel titleLabel = new JLabel("Plotting");
		
		title.add(titleLabel);
		add(title);
		
	//Plot area
		JPanel plot = new JPanel();
		plot.setBounds(0, TITLE_HEIGHT, WIDTH, HEIGHT-TITLE_HEIGHT);
		plot.setBackground(Color.WHITE);
		add(plot);
		
		functionColor = Color.RED;	
		areaColor = Color.ORANGE;
		tangentColor = Color.MAGENTA;
		
		interval = 13.0;
	}
	
	public void paint( Graphics g)
	{
		super.paint(g);	
	//Grid
		g.setColor(new Color(200, 200, 250));
		
		for(int i = GRID_WIDTH; i < WIDTH; i+= GRID_WIDTH)
		{
			g.drawLine(i, TITLE_HEIGHT, i, HEIGHT);
		}
		
		for(int i = TITLE_HEIGHT; i < HEIGHT; i+=GRID_WIDTH)
		{
			g.drawLine(0, i, WIDTH, i);
		}
		
	//Axes
		g.setColor(Color.BLACK);
		g.drawLine(WIDTH/2, TITLE_HEIGHT, WIDTH/2, HEIGHT);
		g.drawLine(0, (HEIGHT+TITLE_HEIGHT)/2, WIDTH, (HEIGHT+TITLE_HEIGHT)/2);	
		
		g.drawLine(0,0,WIDTH,0);
		g.drawLine(0,0,0,HEIGHT);
		
		if( isPlotModeOn)
		{
			max = 0;
			g.setColor( functionColor);
			num_of_inp = (int) (( 2 * interval) / sens);
			results = new Double[num_of_inp][2];
			inp = ( -1 * interval);
			values();
			/*
			for( int i = 0; i < num_of_inp; i+=1)
			{
				NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
				DecimalFormat df = (DecimalFormat)nf;
				
				df.applyPattern("0.0000");
				inp = Double.parseDouble( df.format( inp));
				results[i][0] = inp;
				try
				{
					//System.out.println( "xxx===>" + inp);
					if( f.valueAt( inp)==null || f.valueAt( inp).isNaN() || Math.abs( f.valueAt( inp)) > 500 )
						results[i][1] = null;
				
					else	
						results[i][1] = f.valueAt( inp);	
						
					inp += sens;
				
					if( results[i][1]!= null && Math.abs( results[i][1]) > max)
						max = Math.abs( results[i][1]); 
						//System.out.println(max);
						
				}
				catch( Exception e)
				{
					results[i][1] = null;
					inp += sens;
				}
			}
			hsens = ( WIDTH / 2) / interval;
			vsens = ( ( HEIGHT - TITLE_HEIGHT) / 2) / max;
			
			NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
			DecimalFormat df = (DecimalFormat)nf;
			df.applyPattern("0.00");
			
			unitWidth = interval / 13;
			unitHeight = max / 9 ;
			
			unitWidth = Double.parseDouble( df.format( unitWidth));
			unitHeight = Double.parseDouble( df.format( unitHeight));
			System.out.println("unitHeight: " + unitHeight);
			System.out.println("unitWidth: " + unitWidth + hsens);
			System.out.println("max: " + max);
			*/
			for( int i = 0; i < -1 + num_of_inp; i+=1)
			{
				//System.out.println("" + i + " ---> " + results[i][0] + " = " + results[i][1]);
				if( results[i][1] != null && results[i + 1][1] != null)
				{
					g.drawLine( WIDTH / 2 + (int)( results[i][0] * hsens), 
							  ( TITLE_HEIGHT + ((HEIGHT - TITLE_HEIGHT) / 2)) - (int)( results[i][1] * vsens),
							    WIDTH / 2 + (int)( results[i + 1][0] * hsens),
							  ( TITLE_HEIGHT + ((HEIGHT - TITLE_HEIGHT) / 2)) -(int)( results[ i + 1][1] * vsens));
				}
			}
		}
		
		if( isTangentModeOn)
		{
			g.setColor( tangentColor);
			Double n = d.getFunction().valueAt(d.getPoint()) - ( d.execute() * d.getPoint());
			if( Math.abs( d.getPoint()) <= interval)
			{
				g.drawLine( WIDTH / 2 + (int)( d.getPoint() * hsens),
						  ( TITLE_HEIGHT + ((HEIGHT - TITLE_HEIGHT) / 2)) - (int)( d.getFunction().valueAt( d.getPoint())  * vsens),
				            WIDTH,
				          ( TITLE_HEIGHT + ((HEIGHT - TITLE_HEIGHT) / 2)) - (int)(( results[num_of_inp - 1][0] * d.execute() + n) * vsens));
				
				g.drawLine( WIDTH / 2 + (int)( d.getPoint() * hsens),
						  ( TITLE_HEIGHT + ((HEIGHT - TITLE_HEIGHT) / 2)) - (int)( d.getFunction().valueAt( d.getPoint())  * vsens),
				            0,
				          ( TITLE_HEIGHT + ((HEIGHT - TITLE_HEIGHT) / 2)) + (int)(( results[0][0] * d.execute() * -1 - n) * vsens));
			}	
		}
		
		if( isIntegralModeOn)
		{
			//System.out.println( "1. stage");
			if( ll < ul)
			{
				//System.out.println( "2. stage");
				for( int i = 0 ; i < num_of_inp - 1 ; i++)
					if( results[i][0] == ll || (results[i][0] < ll && results [i+1][0] > ll ) || (results[i][0] > ll && results [i+1][0] < ll ))
						li = i;
				
				for( int i = num_of_inp - 2 ; i > 0 ; i--)
					if( results[i][0] == ul || (results[i][0] < ul && results [i+1][0] > ul ) || (results[i][0] > ul && results [i+1][0] < ul ))
					{
						ui = i;
						break;
					}
				//System.out.println( "--->" + li + " " + ui);
				g.setColor( areaColor);
				for( int i = li ; i <= ui  ; i++)
				{
					//System.out.println( "3. stage");
					
					if( results[i][1] != null && results[i][1] >= 0 )
						g.drawLine( WIDTH / 2 + (int)( results[i][0] * hsens), 
								  ( TITLE_HEIGHT + ((HEIGHT - TITLE_HEIGHT) / 2)) + 1 - (int)( results[i][1] * vsens),
									WIDTH / 2 + (int)( results[i + 1][0] * hsens),
							  	  ( TITLE_HEIGHT + ((HEIGHT - TITLE_HEIGHT) / 2))  );
					else if( results[i][1] != null )
						g.drawLine( WIDTH / 2 + (int)( results[i][0] * hsens), 
								  ( TITLE_HEIGHT + ((HEIGHT - TITLE_HEIGHT) / 2)) - 1 - (int)( results[i][1] * vsens),
									WIDTH / 2 + (int)( results[i + 1][0] * hsens),
							  	  ( TITLE_HEIGHT + ((HEIGHT - TITLE_HEIGHT) / 2))  );
				}
				
			}
		}
	//	System.out.println( "" + unitHeight + " " + unitWidth);
	}
	
	public void drawPlot()
	{		
		interval = 13.0;
		sens = 0.1;
		values();
		isPlotModeOn = true;
		isTangentModeOn = false;
		isIntegralModeOn = false;
		max = 0;
		num_of_inp = (int) (( 2 * interval) / sens);
		results = new Double[num_of_inp][2];
		inp = ( -1 * interval);
	
		repaint();
		max = 0;
	}
	public void drawTangentLine( Derivative d)
	{
		this.d = d;
		drawPlot();
		isPlotModeOn = true;
		isTangentModeOn = true;
		isIntegralModeOn = false;
		repaint();
	}
	public void shadedArea( double lowerLimit, double upperLimit)
	{
		ll = lowerLimit;
		ul = upperLimit;
		drawPlot();
		isPlotModeOn = true;
		isTangentModeOn = false;
		isIntegralModeOn = true;
		repaint();
	}
	public static void values()
	{
		max = 0;
		num_of_inp = (int) (( 2 * interval) / sens);
		results = new Double[num_of_inp][2];
		inp = ( -1 * interval);
		for( int i = 0; i < num_of_inp; i+=1)
		{
			NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
			DecimalFormat df = (DecimalFormat)nf;
			
			df.applyPattern("0.0000");
			inp = Double.parseDouble( df.format( inp));
			results[i][0] = inp;
			try
			{
			//System.out.println( "xxx===>" + inp);
				if( f.valueAt( inp)==null || f.valueAt( inp).isNaN() || Math.abs( f.valueAt( inp)) > 500 )
					results[i][1] = null;
			
				else	
					results[i][1] = f.valueAt( inp);	
					
				inp += sens;
			
				if( results[i][1]!= null && Math.abs( results[i][1]) > max)
					max = Math.abs( results[i][1]); 
					//System.out.println(max);
					
			}
			catch( Exception e)
			{
				results[i][1] = null;
				inp += sens;
			}
		}
		hsens = ( WIDTH / 2) / interval;
		vsens = ( ( HEIGHT - TITLE_HEIGHT) / 2) / max;
		
		NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
		DecimalFormat df = (DecimalFormat)nf;
		df.applyPattern("0.000");
		
		unitWidth = interval / 13;
		unitHeight = max / 9 ;
		//System.out.println( "=>" + interval + " "  + max);
		unitWidth = Double.parseDouble( df.format( unitWidth));
		unitHeight = Double.parseDouble( df.format( unitHeight));
		
	} 	
}