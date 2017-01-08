/************************************************************************************************************
*This Java code is written by "7", whose members are Can Karakuþ, Þükrü Burç Eryýlmaz, Bilgehan Avþer,
*Ömer Sercan Arýk and Onur Günlü.Please include a brief reference comment while using in other applications.
**************************************************************************************************************

This class creates the panel that the result is displayed. It has methods to display the result in
various forms, update, clear it.

Author: Can Karakuþ
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.text.NumberFormat;
import java.text.DecimalFormat;

public class ResultPanel extends JPanel implements ActionListener
{
	final int WIDTH = 442;
	final int HEIGHT = 192;
	final int TITLE_HEIGHT = 25;
	
	static String result = "";
	static JLabel res;
	static boolean polar = false;
	
	NumberFormat numFor = NumberFormat.getNumberInstance(new Locale("en", "US"));
	DecimalFormat ft = (DecimalFormat)numFor;
	
	public ResultPanel()
	{
		setLayout(null);
		ft.applyPattern("###.##");
		setBounds(0,0,WIDTH,HEIGHT);
		
	//Title	
		JPanel title = new JPanel();
		title.setBounds(0,0,WIDTH,TITLE_HEIGHT);
		title.setBackground(new Color(128,128,255));
		
		JLabel titleLabel = new JLabel("Result");
		
		title.add(titleLabel);
		add(title);
		
	//Result area
		JPanel area = new JPanel();
		area.setBackground(Color.WHITE);
		area.setBounds(0,TITLE_HEIGHT,WIDTH, HEIGHT-TITLE_HEIGHT);
		
		res = new JLabel("={ " + result + " }");
		res.setFont(new Font("Dialog", Font.PLAIN, 16));
		area.add(res);
		
		add(area);
		
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawLine(0,0,0,HEIGHT);
		g.drawLine(0,0,WIDTH,0);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		setResult(3.0);
	}
	
	public static void refresh()
	{
		res.setText("={ " + result + " }");
	}
	
	public void setResult(Object r)
	{
		if(r != null)
		{
			if(r instanceof Complex)
			{
				if(polar)
				{
					Complex c = (Complex)r;
					res.setText("={ " + c.switchToPolar() + " }");
				}
				else
					res.setText("={ " + r.toString() + " }");
			}
			else if(r instanceof ArrayList)
			{
				Equation eq = (Equation) Interaction.op;
				
				if(eq.hasValidRoots)
				{	
					ArrayList hs = (ArrayList) r;
					ArrayList<String> s = new ArrayList<String>();
					//Do not print duplicate roots
					for(int i=0; i<hs.size(); i++)
					{
						if(!s.contains(hs.get(i).toString()))				
						{	
							if(!(hs.get(i) instanceof Complex))
								s.add(hs.get(i).toString());
							else
							{
								s.add(hs.get(i).toString());
							}
						}
					}
					
					String str = "";
					Iterator it = s.iterator();
					while(it.hasNext())
						str += it.next() + ", ";
					
					str = str.substring(0,str.length()-2);	
					res.setText("={ " + str + " }");
					hs.clear();
				}
				else
				{
					res.setText("={ No valid roots }");
				}
			}
			else
			{
				if(Double.isNaN((Double)r))
					Interaction.mathError();
				else
				{
					result = ft.format( (Double)r);				
					res.setText("={ " + result + " }");
				}
			}
		}
	}
	
	public static void clear()
	{
		result = "";
		refresh();
	}
}