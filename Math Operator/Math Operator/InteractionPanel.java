/************************************************************************************************************
*This Java code is written by "7", whose members are Can Karakuþ, Þükrü Burç Eryýlmaz, Bilgehan Avþer,
*Ömer Sercan Arýk and Onur Günlü.Please include a brief reference comment while using in other applications.
**************************************************************************************************************
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Stack;
/*
This class creates the panel that the user interacts while entering expressions.It contains buttons and menus
for the interaction, along with ActionListener and KeyListener methods.It invokes proper methods in the listeners.

Author: Can Karakuþ
*/
public class InteractionPanel extends JPanel implements ActionListener, KeyListener
{
	static final Color bgColor = new Color(225,225,225);
	static final int WIDTH = 582;
	static final int HEIGHT = 708;
	static final int STRIP_HEIGHT = 30;
	static final int LEFT_MARGIN = 20;
	static final int TOP_MARGIN = 4;
	static final int BUTTON_WIDTH = 80;
	static final int BUTTON_HEIGHT = 22;
	static final int BUTTON_SPACING = 10;
	static final int OPERATORS_MARGIN = 300;
	static final int OPERATOR_WIDTH = 34;
	static final int OPERATOR_HEIGHT = BUTTON_HEIGHT;
	static final int TITLE_HEIGHT = 25;
	
	static final int LABEL_LEFT_MARGIN = 30;
	static final int LABEL_MARGIN = 5;
	static final int LABEL_TOP_MARGIN = 20;
	static final int DENOMINATOR_MARGIN = 5;
	static final int BOUND_BOTTOM_MARGIN = 5;
	static final int BOUND_TOP_MARGIN = 5;
	
	int lineLength;
	int preExpTopMargin = LABEL_TOP_MARGIN;
	int rootStartX;
	int rootStartY;
	int rootEndX;
	
	JPanel iArea;
	JPopupMenu piMenu;
	JPopupMenu plusMenu;
	JPopupMenu parMenu;
	JPopupMenu fracMenu;
	JPopupMenu powMenu;
	JPopupMenu rootMenu;
	JPopupMenu sinMenu;
	JPopupMenu arcMenu;
	JPopupMenu logMenu;
	JButton[] operators;
	static JToggleButton powerButton;
	static JToggleButton sqrt;
	static JToggleButton cbrt;
	static JToggleButton frrt;
	SpringLayout layout;
			
	protected static JLabel expression;
	protected static JLabel denominator;
	protected static JLabel preExp;
	protected static JLabel equals;
	protected static JLabel second;
	protected static JLabel lowerBound;
	protected static JLabel upperBound;
	protected static JLabel modulo;
	
	static Stack def = new Stack();
	static Stack lastStack = def;
	
	static boolean superscript = false;
	static boolean fraction = false;
	static boolean operationInput = false;
	static boolean secondInput = false;
	static boolean upperInput = false;
	static boolean lowerInput = false;
	static boolean complexInput = false;
//	static boolean rootInput = false;
	
	static Stack lastDefinition = new Stack();
	static Stack lastExpression = new Stack();
	
	Limit li;
	Integral in;
	Sum su;
	Product pr;
	Derivative de;
	
	public InteractionPanel()
	{
		setLayout(null);
	
	//Title	
		JPanel title = new JPanel();
		title.setBounds(0,0,WIDTH,TITLE_HEIGHT);
		title.setBackground(new Color(128,128,255));
		
		JLabel titleLabel = new JLabel("Interaction",JLabel.LEFT);
		
		title.add(titleLabel);
		add(title);
	//Buttons	
		JButton execute = new JButton("Execute");
		JButton undo = new JButton("Undo");
		JButton clear = new JButton("Clear");
		
		execute.setBackground(bgColor);
		undo.setBackground(bgColor);
		clear.setBackground(bgColor);
		
		execute.setBounds(LEFT_MARGIN,TOP_MARGIN+TITLE_HEIGHT,BUTTON_WIDTH,BUTTON_HEIGHT);
		undo.setBounds(LEFT_MARGIN + BUTTON_WIDTH + BUTTON_SPACING,TOP_MARGIN+TITLE_HEIGHT,BUTTON_WIDTH,BUTTON_HEIGHT);
		clear.setBounds(LEFT_MARGIN+ BUTTON_WIDTH*2 + BUTTON_SPACING*2,TOP_MARGIN+TITLE_HEIGHT,BUTTON_WIDTH,BUTTON_HEIGHT);
		
		execute.setActionCommand("100");
		execute.addActionListener(this);
		clear.setActionCommand("101");
		clear.addActionListener(this);
		undo.setActionCommand("102");
		undo.addActionListener(this);
		
		
		add(execute);
		add(undo);
		add(clear);
	
	//Operators bar
		makeMenus();
		
	//oldExpression = new JLabel("");
	//Interaction area
		iArea = new JPanel();
		iArea.setBounds(0, TITLE_HEIGHT+STRIP_HEIGHT,WIDTH,HEIGHT-(TITLE_HEIGHT+STRIP_HEIGHT));
		iArea.setBackground(Color.WHITE);
		
		layout = new SpringLayout();
		iArea.setLayout(layout);
						
		expression = new JLabel("_");
		expression.setForeground(Color.BLUE);
		expression.setFont(new Font("Dialog", Font.ITALIC, 20));
		
		denominator = new JLabel("");
		denominator.setForeground(Color.BLUE);
		denominator.setFont(new Font("Dialog", Font.ITALIC, 20));
		
		preExp = new JLabel("");
		preExp.setFont(new Font("Dialog", Font.ITALIC, 24));
		
		equals = new JLabel("");
		equals.setFont(new Font("Dialog", Font.ITALIC, 20));
		
		second = new JLabel("");
		second.setForeground(Color.BLUE);
		second.setFont(new Font("Dialog", Font.ITALIC, 20));
		
		upperBound = new JLabel("");
		upperBound.setForeground(Color.BLUE);
		upperBound.setFont(new Font("Dialog", Font.ITALIC, 14));
		
		lowerBound = new JLabel("");
		lowerBound.setForeground(Color.BLUE);
		lowerBound.setFont(new Font("Dialog", Font.ITALIC, 14));
		
		modulo = new JLabel("");
		modulo.setFont(new Font("Dialog", Font.BOLD, 16));
		
		iArea.add(expression);
		iArea.add(denominator);
		iArea.add(preExp);
		iArea.add(equals);
		iArea.add(second);
		iArea.add(lowerBound);
		iArea.add(upperBound);
		iArea.add(modulo);
		
		layout.putConstraint(SpringLayout.WEST, preExp, LABEL_LEFT_MARGIN, SpringLayout.WEST, iArea);
		layout.putConstraint(SpringLayout.NORTH, preExp, preExpTopMargin, SpringLayout.NORTH, iArea);
				
		layout.putConstraint(SpringLayout.WEST, expression, LABEL_MARGIN, SpringLayout.EAST, preExp);
		layout.putConstraint(SpringLayout.NORTH, expression, LABEL_TOP_MARGIN, SpringLayout.NORTH, iArea);

		layout.putConstraint(SpringLayout.WEST, denominator, LABEL_MARGIN, SpringLayout.EAST, preExp);
		layout.putConstraint(SpringLayout.NORTH, denominator, 5, SpringLayout.SOUTH, expression);
		
		layout.putConstraint(SpringLayout.WEST, equals, LABEL_MARGIN, SpringLayout.EAST, expression);
		layout.putConstraint(SpringLayout.NORTH, equals, LABEL_TOP_MARGIN, SpringLayout.NORTH, iArea);
		
		layout.putConstraint(SpringLayout.WEST, second, LABEL_MARGIN, SpringLayout.EAST, equals);
		layout.putConstraint(SpringLayout.NORTH, second, LABEL_TOP_MARGIN, SpringLayout.NORTH, iArea);
		
		layout.putConstraint(SpringLayout.WEST, upperBound, LABEL_LEFT_MARGIN, SpringLayout.WEST, iArea);
		layout.putConstraint(SpringLayout.SOUTH, upperBound, BOUND_BOTTOM_MARGIN, SpringLayout.NORTH, preExp);
		
		layout.putConstraint(SpringLayout.WEST, lowerBound, LABEL_LEFT_MARGIN, SpringLayout.WEST, iArea);
		layout.putConstraint(SpringLayout.NORTH, lowerBound, BOUND_TOP_MARGIN, SpringLayout.SOUTH, preExp);
		
		layout.putConstraint(SpringLayout.WEST, modulo, LABEL_LEFT_MARGIN, SpringLayout.WEST, iArea);
		layout.putConstraint(SpringLayout.NORTH, modulo, 550, SpringLayout.NORTH, iArea);
		
		lineLength = ((Double)expression.getPreferredSize().getWidth()).intValue();
		
		add(iArea);
		
		setFocusable(true);
		addKeyListener(this);
		
	}
	//-----------------------------------------------------CONTROLLER-------------------------------------------
	public void actionPerformed(ActionEvent e)
	{
		int code = Integer.parseInt(e.getActionCommand());
		
		//	Store the last data
		if(code != 0 &&  code != 1 && code != 2 && code != 3 && code != 100 && code != 101 && code != 102 &&
		   code != 5 && code != 6 && code != 7 &&	code != 8 && code != 9)
		{
			lastDefinition.push(Interaction.op.getDefinition());
			if(fraction)
				lastExpression.push(denominator.getText());
			else if(secondInput)
				lastExpression.push(second.getText());
			else
				lastExpression.push(expression.getText());
		}
		
		if(code == 10 || code == 11 || code == 12 || code == 13)
		{
			if(!lastStack.isEmpty())							
			{
				if(Interaction.op.getDefinition().charAt(Interaction.op.getDefinition().lastIndexOf(((Character)lastStack.peek()).charValue())+1) != '(')	
				{
					while(!lastStack.empty())
						Interaction.op.addToDefinition("" + Character.toUpperCase(((Character)lastStack.pop()).charValue()));
				}
						
				else if(Interaction.op.getDefinition().charAt(Interaction.op.getDefinition().length()-1) == ')')
				{
					while(!lastStack.empty())
						Interaction.op.addToDefinition("" + Character.toUpperCase(((Character)lastStack.pop()).charValue()));
				}	
			}
		}
		
		switch(code)
		{
			//Menus			
			case 0: piMenu.show(this, OPERATORS_MARGIN,TITLE_HEIGHT + TOP_MARGIN + OPERATOR_HEIGHT); break;
			case 1: plusMenu.show(this, OPERATORS_MARGIN+OPERATOR_WIDTH,TITLE_HEIGHT + TOP_MARGIN + OPERATOR_HEIGHT); break;
			case 2: parMenu.show(this, OPERATORS_MARGIN+OPERATOR_WIDTH*2, TITLE_HEIGHT + TOP_MARGIN + OPERATOR_HEIGHT); break;
			
			//fraction
			case 3:
				fraction = true;
				expression.setText(expression.getText().substring(0, expression.getText().length()-1));
				denominator.setText("_");
				expression.setForeground(Color.BLACK);
				if(Interaction.op instanceof Function)
				{
					preExpTopMargin += ( ((Double)expression.getPreferredSize().getHeight()).intValue() / 2) + 3;
					layout.putConstraint(SpringLayout.NORTH, preExp, preExpTopMargin, SpringLayout.NORTH, iArea);
				}
				
			//Draw fraction line
				repaint();
				
				while(!lastStack.empty())
						Interaction.op.addToDefinition("" + Character.toUpperCase(((Character)lastStack.pop()).charValue()));
				
				Interaction.op.setDefinition("(" + Interaction.op.getDefinition() + ")/(");				
				grabFocus();
				break;
			
			//power
			case 4: 
				 if(powerButton.isSelected())
				 {
				 	if(secondInput)
				 		setExpression(second.getText().substring(0, second.getText().length()-1) + "-");
				 	else if(fraction)
				 		setExpression(denominator.getText().substring(0, denominator.getText().length()-1) + "-");
				 	else
				 		setExpression(expression.getText().substring(0, expression.getText().length()-1) + "-");
				 	
				 	
				 	String s = Interaction.op.getDefinition();
				 	//System.out.println(s.charAt(s.length()-1) == 'x');
//				 	if(!s.equals(""))
//				 		if( Character.isDigit(s.charAt(s.length()-1)) || s.charAt(s.length()-1) == 'x' || s.charAt(s.length()-1) == ')')
				 			Interaction.op.addToDefinition("p(");
				 	superscript = true;
				 }
				 else
				 {	
				 	superscript = false;
				 	
				 	if(secondInput)
				 		setExpression(second.getText().substring(0, second.getText().length()-1) + "_");
				 	else if(fraction)
				 		setExpression(denominator.getText().substring(0, denominator.getText().length()-1) + "_");
				 	else
				 		setExpression(expression.getText().substring(0, expression.getText().length()-1) + "_");
				 	
				 	String s = Interaction.op.getDefinition();
//				 	if(!s.equals(""))
//				 		if( Character.isDigit(s.charAt(s.length()-1)) || s.charAt(s.length()-1) == 'x' )
				 			Interaction.op.addToDefinition(")P");
				 	
				 }
			  	 grabFocus();
				 break;
			
		//	case 5: rootMenu.show(this, OPERATORS_MARGIN+OPERATOR_WIDTH*5, TITLE_HEIGHT + TOP_MARGIN + OPERATOR_HEIGHT); break;	
			case 6: sinMenu.show(this, OPERATORS_MARGIN+OPERATOR_WIDTH*5,TITLE_HEIGHT + TOP_MARGIN + OPERATOR_HEIGHT); break;
			case 7:	arcMenu.show(this, OPERATORS_MARGIN+OPERATOR_WIDTH*6,TITLE_HEIGHT + TOP_MARGIN + OPERATOR_HEIGHT); break;
			case 8:	logMenu.show(this, OPERATORS_MARGIN+OPERATOR_WIDTH*7,TITLE_HEIGHT + TOP_MARGIN + OPERATOR_HEIGHT); break;
			
			//Operator buttons
			case 10: 
				if(complexInput)
				{
					if(!fraction)
						insertExpression(expression, "+");
					else
						insertExpression(denominator, "+");
						
					Interaction.op.addToDefinition("_");
				}
				else
				{
					addExpression("+");  
					Interaction.op.addToDefinition("+");
				}
				grabFocus();
				break;
			case 11: 
				if(complexInput)
				{
					if(!fraction)
						insertExpression(expression, "-");
					else
						insertExpression(denominator, "-");
						
					Interaction.op.addToDefinition("_-");
				}
				else if(superscript)
				{
					addExpression("\u207B");
					Interaction.op.addToDefinition("-");
				}
				else
				{
					addExpression("-");  
					Interaction.op.addToDefinition("+-");
				}
				grabFocus();
				break;
			case 12: 
				addExpression("\u00B7"); 
				Interaction.op.addToDefinition("*");
				grabFocus(); 
				break;
			case 13: 
				addExpression("/");
			    Interaction.op.addToDefinition("/");
			    grabFocus(); 
			    break;
			case 14:
				if(!superscript)	
					addExpression(".");
				else
					addExpression("\u00B7");
						
				if(!lowerInput && !upperInput)
					Interaction.op.addToDefinition(".");
				
				grabFocus();
				break;
			case 20: 
				addExpression("("); 
				lastStack.push(new Stack());
				setLastStack();
				Interaction.op.addToDefinition("(");
				grabFocus(); 
				break;
			case 21: 
				addExpression(")"); 
				
				if(!lastStack.empty())
					Interaction.op.addToDefinition("" + Character.toUpperCase(((Character)lastStack.pop()).charValue()));
				
				lastStack = def;
				toParentStack();
				lastStack.pop();
				
				Interaction.op.addToDefinition(")");	
				
				grabFocus(); 
				break;
/*			case 50:
				if(sqrt.isSelected())
				{
					addExpression("\u221A");
					rootInput = true;
		
					if(fraction)
					{
						rootStartX = denominator.getX() + ((Double)denominator.getPreferredSize().getWidth()).intValue();
						rootStartY = denominator.getY() + STRIP_HEIGHT + TITLE_HEIGHT;
					}
					if(secondInput)
					{
						rootStartX = second.getX() + ((Double)second.getPreferredSize().getWidth()).intValue();
						rootStartY = second.getY() + STRIP_HEIGHT + TITLE_HEIGHT;
					}
					else
					{
						rootStartX = expression.getX() + ((Double)expression.getPreferredSize().getWidth()).intValue();
						rootStartY = expression.getY() + STRIP_HEIGHT + TITLE_HEIGHT;
					}
					
					Interaction.op.addToDefinition(2 + "r(");
				}
				else
				{
					rootInput = false;
					Interaction.op.addToDefinition(")R");
				}
				
				grabFocus();
				break;
			case 51:
				if(cbrt.isSelected())
				{
					addExpression("\u221B");
					rootInput = true;
					
					if(fraction)
					{
						rootStartX = denominator.getX() + ((Double)denominator.getPreferredSize().getWidth()).intValue();
						rootStartY = denominator.getY() + STRIP_HEIGHT + TITLE_HEIGHT;
					}
					if(secondInput)
					{
						rootStartX = second.getX() + ((Double)second.getPreferredSize().getWidth()).intValue();
						rootStartY = second.getY() + STRIP_HEIGHT + TITLE_HEIGHT;
					}
					else
					{
						rootStartX = expression.getX() + ((Double)expression.getPreferredSize().getWidth()).intValue();
						rootStartY = expression.getY() + STRIP_HEIGHT + TITLE_HEIGHT;
					}
					
					Interaction.op.addToDefinition(3 + "r(");
				}
				else
				{
					rootInput = false;
					Interaction.op.addToDefinition(")R");
				}
				
				grabFocus();
				break;
			case 52:
				if(frrt.isSelected())
				{
					addExpression("\u221C");
					rootInput = true;
					
					if(fraction)
					{
						rootStartX = denominator.getX() + ((Double)denominator.getPreferredSize().getWidth()).intValue();
						rootStartY = denominator.getY() + STRIP_HEIGHT + TITLE_HEIGHT;
					}
					if(secondInput)
					{
						rootStartX = second.getX() + ((Double)second.getPreferredSize().getWidth()).intValue();
						rootStartY = second.getY() + STRIP_HEIGHT + TITLE_HEIGHT;
					}
					else
					{
						rootStartX = expression.getX() + ((Double)expression.getPreferredSize().getWidth()).intValue();
						rootStartY = expression.getY() + STRIP_HEIGHT + TITLE_HEIGHT;
					}
					
					Interaction.op.addToDefinition(4 + "r(");
				}
				else
				{
					rootInput = false;
					Interaction.op.addToDefinition(")R");
				}
				
				grabFocus();
				break;
*/			case 60: 
				addExpression("sin");
				Interaction.op.addToDefinition("s");
				lastStack.push((Character)'s');
				grabFocus(); 
				break;
			case 61: 
				addExpression("cos"); 
				
				Interaction.op.addToDefinition("c");
				lastStack.push((Character)'c');
				
				grabFocus(); 
				break;
			case 62: 
				addExpression("tan"); 
				
				Interaction.op.addToDefinition("t");
				lastStack.push((Character)'t');
				
				grabFocus(); 
				break;
			case 63: 
				addExpression("cot"); 
				
				Interaction.op.addToDefinition("g");
				lastStack.push((Character)'g');
				
				grabFocus(); 
				break;
			case 70: 
				addExpression("arcsin"); 
				
				Interaction.op.addToDefinition("a");
				lastStack.push((Character)'a');
				
				grabFocus(); 
				break;
			case 71: 
				addExpression("arccos");
				
				Interaction.op.addToDefinition("b");
				lastStack.push((Character)'b');
				
				grabFocus(); 
				break;
			case 72: 
				addExpression("arctan"); 
				
				Interaction.op.addToDefinition("n");
				lastStack.push((Character)'n');
				
				grabFocus(); 
				break;
			case 73: 
				addExpression("arccot");
				
				Interaction.op.addToDefinition("d");
				lastStack.push((Character)'d');
				
				grabFocus(); 
				break;
			case 80: 				
				String baseString = (String) JOptionPane.showInputDialog(this, "Base of logarithm:", "Logarithm", JOptionPane.PLAIN_MESSAGE);
			
				try
				{
					int base = Integer.parseInt(baseString);
					Interaction.op.addToDefinition(base + "m");
					addExpression("log");
					setBase(base);
				}
				catch(NumberFormatException exc)
				{
					JOptionPane.showMessageDialog(this, "Please enter an integer greater than 1.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(NullPointerException ex){}
						
				lastStack.push((Character)'m');
				grabFocus(); 
				break;
			case 81: 
				addExpression("ln"); 
				
				Interaction.op.addToDefinition("l");
				lastStack.push((Character)'l');
				
				grabFocus(); 
				break;
			case 90: 
				if(lowerInput)
					addLowerBound("3.14");
				else if(upperInput)
					addUpperBound("3.14");
				else if(operationInput && (Interaction.op instanceof Limit || Interaction.op instanceof Derivative))
				{	
					lowerInput = true;
					addLowerBound("3.14");
				}
				else
				{
					addExpression("\u03C0"); 				
					Interaction.op.addToDefinition("I");
				}
				
				grabFocus(); 
				break;
			case 91: 
				if(lowerInput)
					addLowerBound("2.71");
				else if(upperInput)
					addUpperBound("2.71");
				else if(operationInput && (Interaction.op instanceof Limit || Interaction.op instanceof Derivative))
				{	
					lowerInput = true;
					addLowerBound("2.71");
				}
				else
				{
					addExpression("e"); 
					Interaction.op.addToDefinition("e");
				}
				grabFocus(); 
				break;
			case 100:											//Execute
				endOperations();
				ResultPanel.clear();
				
				try
				{
					if(Interaction.op.getClass().getName().equals("DAO"))
					{	
						DAO dao = (DAO)Interaction.op;
						Interaction.setResult(dao.execute(dao.getDefinition()));
					}
					else if(Interaction.op instanceof Equation)
					{
						Equation eq = (Equation)Interaction.op;
						
						Object o = eq.execute();	

						if(eq.isRootAvailable)
							Interaction.setResult(o);
						else
							JOptionPane.showMessageDialog(this, "Please enter polynomials up to 4th degree.", "Error", JOptionPane.ERROR_MESSAGE);
					}	
					else if(!(Interaction.op instanceof Function))
					{
						Interaction.setResult(Interaction.op.execute());
					}
				}
				catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "Please check your expression.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(NullPointerException exc) {
					JOptionPane.showMessageDialog(this, "Please check your expression.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(StringIndexOutOfBoundsException excptn) {
					JOptionPane.showMessageDialog(this, "Please check your expression.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(ArithmeticException excp){
					JOptionPane.showMessageDialog(this, "Please check your expression.", "Error", JOptionPane.ERROR_MESSAGE);
				}	
				expression.setForeground(Color.BLACK);
				denominator.setForeground(Color.BLACK);
				second.setForeground(Color.BLACK);
				upperBound.setForeground(Color.BLACK);
				lowerBound.setForeground(Color.BLACK);
				
				grabFocus();
				break;	
			case 101:					//Clear
				clearPreExp();
				setUpperBound("");
				setLowerBound("");
				setEquals("");
				setSecond("");
				setModulo("");
				preExpTopMargin = LABEL_TOP_MARGIN;
				layout.putConstraint(SpringLayout.NORTH, preExp, preExpTopMargin, SpringLayout.NORTH, iArea);
				secondInput = false;
				operationInput = false;
				complexInput = false;
				upperInput = false;
				lowerInput = false;
//				rootInput = false;
				expression.setForeground(Color.BLUE);
				denominator.setForeground(Color.BLUE);
				second.setForeground(Color.BLUE);
				upperBound.setForeground(Color.BLUE);
				lowerBound.setForeground(Color.BLUE);
				denominator.setText("");
				Interaction.op.clear();
				Interaction.op = new DAO();
				DAO.unsetModulo();
				Equation.unsetModulo();
				ToolbarPanel.setModulo.setText("Set Modulo");
				ToolbarPanel.setModulo.setActionCommand("set modulo");
				ResultPanel.clear();
				powerButton.setSelected(false);
				superscript = false;
				grabFocus();
				getGraphics().setColor( Color.WHITE);
        		repaint();
        		fraction = false;
        		setExpression("_");
        		
        		lastDefinition.clear();
        		lastExpression.clear();
        		
				break;
			
			case 102:													//Undo
				String def = Interaction.op.getDefinition();
			
				if(def.length() <= 1 ||(def.length() > 1 && !(def.substring(def.length()-2, def.length()).equals("/(") && fraction == true)))
				{
					if(def.length() > 1 && def.substring(def.length()-2, def.length()).equals("p("))
					{	
						powerButton.setSelected(false);
						superscript = false;
					}
						if(!lastStack.empty())
							if(def.charAt(def.length()-1) == 's' || def.charAt(def.length()-1) == 'c' ||
								def.charAt(def.length()-1) == 't' || def.charAt(def.length()-1) == 'g' ||
								def.charAt(def.length()-1) == 'a' || def.charAt(def.length()-1) == 'b' ||
								def.charAt(def.length()-1) == 'n' || def.charAt(def.length()-1) == 'd' ||
								def.charAt(def.length()-1) == 'l' || def.charAt(def.length()-1) == 'm' ||
								def.charAt(def.length()-1) == 'r')
									lastStack.pop();
					
					if(!lastDefinition.empty())
					{
						Interaction.op.setDefinition((String)lastDefinition.pop());
						if(fraction)
							denominator.setText((String)lastExpression.pop());
						else if(secondInput)
							second.setText((String)lastExpression.pop());
						else
							expression.setText((String)lastExpression.pop());
					}
				}
				grabFocus();
			break;
			default: break;
		}
		grabFocus();
	//Manage the length of the fraction line and center
		if(fraction)
		{
			lineLength = Math.max(((Double)expression.getPreferredSize().getWidth()).intValue(), ((Double)denominator.getPreferredSize().getWidth()).intValue());
						
			if(expression.getPreferredSize().getWidth() > denominator.getPreferredSize().getWidth())
			{
				int diff = ((Double)(expression.getPreferredSize().getWidth() - denominator.getPreferredSize().getWidth())).intValue()/2;
				layout.putConstraint(SpringLayout.WEST, denominator, LABEL_MARGIN + diff, SpringLayout.EAST, preExp);
			}
			else
		    {
		  		int diff = ((Double)(denominator.getPreferredSize().getWidth() - expression.getPreferredSize().getWidth())).intValue()/2;
		    	layout.putConstraint(SpringLayout.WEST, expression, LABEL_MARGIN + diff, SpringLayout.EAST, preExp);
		    }		
			
			//repaint();
		}
/*		if(rootInput)
		{
			if(fraction)
				rootEndX = denominator.getX() + ((Double)denominator.getPreferredSize().getWidth()).intValue();
			else if(secondInput)
				rootEndX = second.getX() + ((Double)second.getPreferredSize().getWidth()).intValue();
			else
				rootEndX = expression.getX() + ((Double)expression.getPreferredSize().getWidth()).intValue();
				
			repaint();
		}*/
	}
	//KeyListener methods
	public void keyPressed(KeyEvent e)
	{
		//	Store the last data
		String s = KeyEvent.getKeyText(e.getKeyCode());
	
		if(s.equals("1") || s.equals("2") || s.equals("3") || s.equals("4") || s.equals("5") || 
		   s.equals("6") || s.equals("7") || s.equals("8") || s.equals("9") || s.equals("0") || s.equals("X") || 
		   	(complexInput && s.equals("I")))
		{
			lastDefinition.push(Interaction.op.getDefinition());
			if(fraction)
				lastExpression.push(denominator.getText());
			else if(secondInput)
				lastExpression.push(second.getText());
			else
				lastExpression.push(expression.getText());
		}
		
		  if(operationInput)
		  {
		  	if(Interaction.op instanceof Limit ||
	   	       Interaction.op instanceof Derivative)
		  	{
		  		lowerInput = true;
		  		
		  		if(e.getKeyCode() == KeyEvent.VK_0 ||
	  		     e.getKeyCode() == KeyEvent.VK_1 ||
	  		     e.getKeyCode() == KeyEvent.VK_2 ||
	  		     e.getKeyCode() == KeyEvent.VK_3 ||
	  		     e.getKeyCode() == KeyEvent.VK_4 ||
	  		     e.getKeyCode() == KeyEvent.VK_5 ||
	  		     e.getKeyCode() == KeyEvent.VK_6 ||
	  	  	  	 e.getKeyCode() == KeyEvent.VK_7 ||
	  	   	     e.getKeyCode() == KeyEvent.VK_8 ||
	  	     	 e.getKeyCode() == KeyEvent.VK_9 ||
	  	     	 e.getKeyCode() == KeyEvent.VK_MINUS)
	  	     	{
	  	     		if(e.getKeyCode() == KeyEvent.VK_MINUS)
	  	     			addLowerBound("-");
	  	     		else
	  	     			addLowerBound(KeyEvent.getKeyText(e.getKeyCode()));
	  	     	}
		  	}
		  	if(Interaction.op instanceof Sum ||
		  	   Interaction.op instanceof Product ||
		  	   Interaction.op instanceof Integral)
		  	{
		  		if(e.getKeyCode() == KeyEvent.VK_0 ||
	  		     e.getKeyCode() == KeyEvent.VK_1 ||
	  		     e.getKeyCode() == KeyEvent.VK_2 ||
	  		     e.getKeyCode() == KeyEvent.VK_3 ||
	  		     e.getKeyCode() == KeyEvent.VK_4 ||
	  		     e.getKeyCode() == KeyEvent.VK_5 ||
	  		     e.getKeyCode() == KeyEvent.VK_6 ||
	  	  	  	 e.getKeyCode() == KeyEvent.VK_7 ||
	  	   	     e.getKeyCode() == KeyEvent.VK_8 ||
	  	     	 e.getKeyCode() == KeyEvent.VK_9)
	  	     	{
	  	     		if(lowerInput)	
	  	     			addLowerBound(KeyEvent.getKeyText(e.getKeyCode()));
	  	     		if(upperInput)
	  	     			addUpperBound(KeyEvent.getKeyText(e.getKeyCode()));
	  	     	}
	  	     	else if(e.getKeyCode() == KeyEvent.VK_MINUS)
	  	     	{
	  	     		if(lowerInput)	
	  	     			addLowerBound("-");
	  	     		if(upperInput)
	  	     			addUpperBound("-");
	  	     	}
		  	}
		  }
		  else
		  {
	  		  if(superscript == false)
	  	  	  {	  	 
	  		 	 if(e.getKeyCode() == KeyEvent.VK_0 ||
	  		 	     e.getKeyCode() == KeyEvent.VK_1 ||
	  		 	     e.getKeyCode() == KeyEvent.VK_2 ||
	  		 	  	 e.getKeyCode() == KeyEvent.VK_3 ||
	  		 	  	 e.getKeyCode() == KeyEvent.VK_4 ||
	  			     e.getKeyCode() == KeyEvent.VK_5 ||
	  			     e.getKeyCode() == KeyEvent.VK_6 ||
	  	  		  	 e.getKeyCode() == KeyEvent.VK_7 ||
	  	   		     e.getKeyCode() == KeyEvent.VK_8 ||
	  	   	  	 	 e.getKeyCode() == KeyEvent.VK_9 ||
	  		    	 e.getKeyCode() == KeyEvent.VK_X)
	  			 {   
	   				  if(complexInput && e.getKeyCode() != KeyEvent.VK_X)
	   				  {
	   				  	  if(!fraction)
	  			 			  insertExpression(expression, KeyEvent.getKeyText(e.getKeyCode()));
	  			 		  else
	  			 			  insertExpression(denominator,KeyEvent.getKeyText(e.getKeyCode()));
	  			 		
	  				   	  Interaction.op.addToDefinition(KeyEvent.getKeyText(e.getKeyCode()));
	   				  }
	   				  else if(!(e.getKeyCode() == KeyEvent.VK_X && Interaction.op.getClass().getName().equals("DAO")))
	   				  {	
	   				  	addExpression(KeyEvent.getKeyText(e.getKeyCode()));
	   				  	Interaction.op.addToDefinition(KeyEvent.getKeyText(e.getKeyCode()).toLowerCase());
	   				  }
	   				  
	  			 }	 
	  			 else if(e.getKeyCode() == KeyEvent.VK_I && complexInput)
	  			 {
	  			 	if(!fraction)
	  			 		insertExpression(expression,"i");
	  			 	else
	  			 		insertExpression(denominator,"i");
	  			 		
	  			 	Interaction.op.addToDefinition("i");
	  			 }  
	  		 }
	 
	  		 else if(e.getKeyCode() != KeyEvent.VK_ENTER)
	  		 {
	  		 	if(e.getKeyCode() == KeyEvent.VK_0)
	  		 		addExpression("\u2070");
	  		 	else if(e.getKeyCode() == KeyEvent.VK_1)
	  		 		addExpression("\u00B9");
	  		 	else if(e.getKeyCode() == KeyEvent.VK_2)
	  		 		addExpression("\u00B2");
	  		 	else if(e.getKeyCode() == KeyEvent.VK_3)
	  		 		addExpression("\u00B3");
	  		 	else if(e.getKeyCode() == KeyEvent.VK_4)
	  		 		addExpression("\u2074");
	  		 	else if(e.getKeyCode() == KeyEvent.VK_5)
	  		 		addExpression("\u2075");
	  		 	else if(e.getKeyCode() == KeyEvent.VK_6)
	  		 		addExpression("\u2076");
	  		 	else if(e.getKeyCode() == KeyEvent.VK_7)
	  		 		addExpression("\u2077");
	  		 	else if(e.getKeyCode() == KeyEvent.VK_8)
	  		 		addExpression("\u2078");
	  		 	else if(e.getKeyCode() == KeyEvent.VK_9)
	  		 		addExpression("\u2079");
	  		 	else if(e.getKeyCode() == KeyEvent.VK_MINUS)
	  		 		addExpression("\u207B");
	  		 	
	  		 	if(e.getKeyCode() == KeyEvent.VK_MINUS) 
	  		 		Interaction.op.addToDefinition("-");
	  		 	else 	 		
	  	 			Interaction.op.addToDefinition(KeyEvent.getKeyText(e.getKeyCode()).toLowerCase());	
	  		 }
	  	 }
	  	 //End operation input
	  	 if(e.getKeyCode() == KeyEvent.VK_ENTER)
	  	 {
	  	 	if(Interaction.op instanceof Limit)
	  	 	{
	  	 		lowerBound.setText(lowerBound.getText().substring(0, lowerBound.getText().length()-1));
	  	 		expression.setText("_");
	  	 		lowerBound.setForeground(Color.BLACK);
	  	 		li = (Limit)Interaction.op;
	  	 		String point = lowerBound.getText().substring(2,lowerBound.getText().length()-1) + 
	  	 					   lowerBound.getText().charAt(lowerBound.getText().length()-1);
		
	  	 		li.setPoint(Double.parseDouble(point));
	 
	  	 		Interaction.op = li;
	  	 		operationInput = false;
	  	 		lowerInput = false;
	  	 	}
	  	 	else if(Interaction.op instanceof Derivative)
	  	 	{
	  	 		lowerBound.setText(lowerBound.getText().substring(0, lowerBound.getText().length()-1));
	  	 		lowerBound.setForeground(Color.BLACK);
	  	 		expression.setText("_");
	  	 		de = (Derivative)Interaction.op;
	  	 		String point = lowerBound.getText().substring(3,lowerBound.getText().length()-1) + 
	  	 					   lowerBound.getText().charAt(lowerBound.getText().length()-1);
	  	 		
	  	 		de.setPoint(Double.parseDouble(point));
	  	 		
	  	 		Interaction.op = de;
	  	 		operationInput = false;
	  	 		lowerInput = false;
	  	 	}
	  	 	else if(Interaction.op instanceof Integral)
	  	 	{
	  	 		in = (Integral)Interaction.op;
	  	 		
  	 			if(lowerInput)
  	 			{
  	 				upperBound.setText("_");
  	 				lowerBound.setText(lowerBound.getText().substring(0, lowerBound.getText().length()-1));
  	 				lowerBound.setForeground(Color.BLACK);
  	 				lowerInput = false;
  	 				upperInput = true;
  	 				  	 				
  	 				in.setLowerBound(Double.parseDouble(lowerBound.getText()));
  	 			}
  	 			else if(upperInput)
  	 			{
  	 				expression.setText("_");
  	 				upperBound.setText(upperBound.getText().substring(0, upperBound.getText().length()-1));
  	 				upperBound.setForeground(Color.BLACK);
  	 				upperInput = false;
  	 				operationInput = false;
  	 				
  	 				in.setUpperBound(Double.parseDouble(upperBound.getText()));
  	 				
  	 				Interaction.op = in;
  	 			}
	  	 	}
	  	 	else if(Interaction.op instanceof Sum)
	  	 	{
	  	 		su = (Sum)Interaction.op;

  	 			if(lowerInput)
  	 			{
  	 				upperBound.setText("_");
  	 				lowerBound.setText(lowerBound.getText().substring(0, lowerBound.getText().length()-1));
  	 				lowerBound.setForeground(Color.BLACK);
  	 				lowerInput = false;
  	 				upperInput = true;
  	 				  	 				
  	 				String lowText = lowerBound.getText().substring(2, lowerBound.getText().length()-1)
  	 								+ lowerBound.getText().charAt(lowerBound.getText().length()-1);
  	 				
  	 				su.setLowerBound(Integer.parseInt(lowText));
  	 			}
  	 			else if(upperInput)
  	 			{
  	 				expression.setText("_");
  	 				upperBound.setText(upperBound.getText().substring(0, upperBound.getText().length()-1));
  	 				upperBound.setForeground(Color.BLACK);
  	 				upperInput = false;
  	 				operationInput = false;
  	 				su.setUpperBound(Integer.parseInt(upperBound.getText()));
  	 				
  	 				Interaction.op = su;
  	 			}
	  	 	}
	  	 	else if(Interaction.op instanceof Product)
	  	 	{
	  	 		pr = (Product)Interaction.op;

  	 			if(lowerInput)
  	 			{
  	 				upperBound.setText("_");
  	 				lowerBound.setText(lowerBound.getText().substring(0, lowerBound.getText().length()-1));
  	 				lowerBound.setForeground(Color.BLACK);
  	 				lowerInput = false;
  	 				upperInput = true;
  	 				
  	 				String lowText = lowerBound.getText().substring(2, lowerBound.getText().length()-1)
  	 								+ lowerBound.getText().charAt(lowerBound.getText().length()-1);
  	 				
  	 				pr.setLowerBound(Integer.parseInt(lowText));
  	 			}
  	 			else if(upperInput)
  	 			{
  	 				expression.setText("_");
  	 				upperBound.setText(upperBound.getText().substring(0, upperBound.getText().length()-1));
  	 				upperBound.setForeground(Color.BLACK);
  	 				upperInput = false;
  	 				operationInput = false;
  	 				  	 				
  	 				pr.setUpperBound(Integer.parseInt(upperBound.getText()));
  	 				
  	 				Interaction.op = pr;
  	 			}
	  	 	}
	  	 	else if(Interaction.op instanceof Equation)
	  	 	{
	  	 		expression.setText(expression.getText().substring(0, expression.getText().length()-1));
	  	 		second.setText("_");
	  	 		expression.setForeground(Color.BLACK);
	  	 		endOperations();
	  	 		Equation eq = (Equation)Interaction.op;
	  	 		eq.next = true;
	  	 		Interaction.op = eq;
	  	 		
	  	 		secondInput = true;
	  	 		superscript = false;
	  	 		powerButton.setSelected(false);
	  	 	}
	  	 	else if(complexInput)
	  	 	{
	  	 		complexInput = false;
	  	 		Interaction.op.addToDefinition("]");
	  	 		
	  	 		if(!fraction)
	  	 			setExpression(expression.getText().substring(0, expression.getText().length()-2) + 
	  	 						expression.getText().charAt(expression.getText().length()-1) + 
	  	 						expression.getText().charAt(expression.getText().length()-2));
	  	 		else
	  	 			setExpression(denominator.getText().substring(0, denominator.getText().length()-2) + 
	  	 						denominator.getText().charAt(denominator.getText().length()-1) + 
	  	 						denominator.getText().charAt(denominator.getText().length()-2));	
	  	 	}
	  	 }
	  	//Manage the length of the fraction line and center
		if(fraction)
		{
			lineLength = Math.max(((Double)expression.getPreferredSize().getWidth()).intValue(), ((Double)denominator.getPreferredSize().getWidth()).intValue());
						
			if(expression.getPreferredSize().getWidth() > denominator.getPreferredSize().getWidth())
			{
				int diff = ((Double)(expression.getPreferredSize().getWidth() - denominator.getPreferredSize().getWidth())).intValue()/2;
				layout.putConstraint(SpringLayout.WEST, denominator, LABEL_MARGIN + diff, SpringLayout.EAST, preExp);
			}
			else
		    {
		  		int diff = ((Double)(denominator.getPreferredSize().getWidth() - expression.getPreferredSize().getWidth())).intValue()/2;
		    	layout.putConstraint(SpringLayout.WEST, expression, LABEL_MARGIN + diff, SpringLayout.EAST, preExp);
		    }		
			
			repaint();
		}
/*		if(rootInput)
		{
			if(fraction)
				rootEndX = denominator.getX() + ((Double)denominator.getPreferredSize().getWidth()).intValue();
			else if(secondInput)
				rootEndX = second.getX() + ((Double)second.getPreferredSize().getWidth()).intValue();
			else
				rootEndX = expression.getX() + ((Double)expression.getPreferredSize().getWidth()).intValue();
			repaint();
	  	}*/
	  	 //DAO dao = (DAO) Interaction.op;System.out.println(dao.getDefinition());
	  }
	public void keyReleased(KeyEvent evt)
	  {
	  }
	public void keyTyped(KeyEvent evt)
	  {
	  }
//---------------------------------------------------------------------------------------------------------------	  
	public static void setLastStack()
	  {
	  	  if(!lastStack.empty())
	  	  {
	   		  if(lastStack.peek() instanceof Stack)
	   		  {
	   		  	 lastStack = (Stack)lastStack.peek();
	   		  	 setLastStack();
	   		  }
	   	  }
	  }
	//Before the invocation of this method, def value must be assigned to lastStack
	public static void toParentStack()
	  {
	  	  if(!lastStack.empty())
	  	  {	  
	  	 	  if(lastStack.peek() instanceof Stack)
	   		  {
	   		  	  if(!((Stack)lastStack.peek()).empty())
	   		  	  {
	   		   		 lastStack = (Stack)lastStack.peek();
	   	  			 toParentStack();
	   	  		  }
	   	  	  }
	   	  }	 
	  }
	public static String getExpression()
	{
		if(!fraction)
			return expression.getText();
		else
			return denominator.getText();
	}
	public static void setExpression(String s)
	{
		if(!superscript)
		{
			if(!secondInput)
			{
				if(fraction == false && !lowerInput && !upperInput)
					expression.setText(s);
				else if(!lowerInput && !upperInput)
					denominator.setText(s);
				else if(lowerInput)
					lowerBound.setText(s);
				else if(upperInput)
					upperBound.setText(s);	
			}
			else
			{
				second.setText(s);
			}
		}
		else
		{
			if(!secondInput)
			{
				if(fraction == false)
					expression.setText(s);
				else
					denominator.setText(s);
			}
			else
			{
				second.setText(s);
			}
		}	
	}
	public static void setPreExp(String s)
	{
		preExp.setText(s);
	}
	public static void clearPreExp()
	{
		preExp.setText("");
		preExp.setIcon(null);
	}
	public static void addExpression(String s)
	{
		//oldExpression.setText(expression.getText());
		if(!superscript)
		{
			if(!secondInput)
			{
				if(fraction == false && !lowerInput && !upperInput)
					expression.setText(expression.getText().substring(0, expression.getText().length()-1) + s.toLowerCase() + "_");
				else if(!lowerInput && !upperInput)
					denominator.setText(denominator.getText().substring(0, denominator.getText().length()-1) + s.toLowerCase() + "_");
				else if(lowerInput)
					lowerBound.setText(lowerBound.getText().substring(0, lowerBound.getText().length()-1) + s.toLowerCase() + "_");
				else if(upperInput)
				{
					upperBound.setText(upperBound.getText().substring(0, upperBound.getText().length()-1) + s.toLowerCase() + "_");
				}
			}
			else
			{
				second.setText(second.getText().substring(0, second.getText().length()-1) + s.toLowerCase() + "_");
			}
		}
		else
		{
			if(!secondInput)
			{
				if(fraction == false)
					expression.setText(expression.getText().substring(0, expression.getText().length()-1) + s.toLowerCase() + "-");
				else
					denominator.setText(denominator.getText().substring(0, denominator.getText().length()-1) + s.toLowerCase() + "-");
			}
			else
			{
				second.setText(second.getText().substring(0, second.getText().length()-1) + s + "-");
			}
		}	
		
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawLine(0,STRIP_HEIGHT+TITLE_HEIGHT,WIDTH,STRIP_HEIGHT+TITLE_HEIGHT);
		g.drawLine(0,0,WIDTH,0);
		g.drawLine(WIDTH-1,0,WIDTH-1,HEIGHT-1);
		if(fraction)
		{
			int lineHeight = STRIP_HEIGHT + TITLE_HEIGHT + LABEL_TOP_MARGIN + ((Double)expression.getPreferredSize().getHeight()).intValue() + 3;
			int initialX = LABEL_LEFT_MARGIN + ((Double)preExp.getPreferredSize().getWidth()).intValue() + LABEL_MARGIN;
			
			g.drawLine(initialX, lineHeight , initialX + lineLength, lineHeight);		
		}
/*		if(rootInput)
		{
			int startX = rootStartX + 3;
			int endX = rootEndX;
			int y = rootStartY + 2;
			g.drawLine(startX, y, endX, y);
		}*/	
	}
	public static void endOperations()
	{
		if(powerButton.isSelected())
		{
			powerButton.setSelected(false);
			superscript = false;
			Interaction.op.addToDefinition(")P");
			
			String sExp = expression.getText();
			String sSec = second.getText();
			String sFrac = denominator.getText();
			
			if(!secondInput && !fraction && sExp.charAt(sExp.length()-1) == '-')
				expression.setText(sExp.substring(0, sExp.length()-1) + "_");
			
			else if(!fraction && sSec.charAt(sSec.length()-1) == '-')
				second.setText(sSec.substring(0, sSec.length()-1) + "_");
			
			else if(sFrac.charAt(sFrac.length()-1) == '-')
				denominator.setText(sFrac.substring(0, sFrac.length()-1) + "_");
		}
/*		if(rootInput)
		{
			Interaction.op.addToDefinition(")R");
			sqrt.setSelected(false);
			cbrt.setSelected(false);
			frrt.setSelected(false);
			rootInput = false;
		}*/
		while(!lastStack.empty())
			Interaction.op.addToDefinition("" + Character.toUpperCase(((Character)lastStack.pop()).charValue()));
		
		if(complexInput)
		{
			Interaction.op.addToDefinition("]");
			complexInput = false;
		}
		
		if(fraction)
		{
			Interaction.op.addToDefinition(")");
			fraction = false;
		}
	}			
	public void makeMenus()
	{
		operators = new JButton[9];
		
		ImageIcon[] icons = new ImageIcon[9];
		
		icons[0] = new ImageIcon("piIcon.JPG");
		icons[1] = new ImageIcon("plusIcon.JPG");
		icons[2] = new ImageIcon("parIcon.JPG");
		icons[3] = new ImageIcon("fracIcon.JPG");
		icons[4] = new ImageIcon("powIcon.JPG");
	//	icons[5] = new ImageIcon("rootIcon.JPG");
		icons[6] = new ImageIcon("sinIcon.JPG");
		icons[7] = new ImageIcon("arcIcon.JPG");
		icons[8] = new ImageIcon("logIcon.JPG");
		
		for(int i=0; i<9; i++)
		{
			if(i != 4 && i!=5)
			{
				operators[i] = new JButton();
				operators[i].setBackground(bgColor);
				operators[i].setIcon(icons[i]);	
				
				if(i < 5)
					operators[i].setBounds(OPERATORS_MARGIN+i*OPERATOR_WIDTH, TOP_MARGIN+TITLE_HEIGHT, OPERATOR_WIDTH, OPERATOR_HEIGHT);
				else
					operators[i].setBounds(OPERATORS_MARGIN+(i-1)*OPERATOR_WIDTH, TOP_MARGIN+TITLE_HEIGHT, OPERATOR_WIDTH, OPERATOR_HEIGHT);
				
				operators[i].setActionCommand(i + "");
				operators[i].addActionListener(this);
				this.add(operators[i]);
			}
		}
		
		piMenu = new JPopupMenu();
		
		JMenuItem pi = new JMenuItem("pi");
		pi.setActionCommand("90");
		pi.addActionListener(this);
		pi.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		piMenu.add(pi);
		JMenuItem e = new JMenuItem("e");
		e.setActionCommand("91");
		e.addActionListener(this);
		e.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		piMenu.add(e);
		
		piMenu.setPopupSize(OPERATOR_WIDTH, OPERATOR_HEIGHT*2);
		
		
		parMenu = new JPopupMenu();
		
		JMenuItem openPar = new JMenuItem("  (");
		openPar.setActionCommand("20");
		openPar.addActionListener(this);
		openPar.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		parMenu.add(openPar);
		JMenuItem closePar = new JMenuItem("  )");
		closePar.setActionCommand("21");
		closePar.addActionListener(this);
		closePar.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		parMenu.add(closePar);
		
		parMenu.setPopupSize(OPERATOR_WIDTH, OPERATOR_HEIGHT*2);
		
		
		plusMenu = new JPopupMenu();
		
		JMenuItem plus = new JMenuItem(" +");
		plus.setActionCommand("10");
		plus.addActionListener(this);
		plus.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		plusMenu.add(plus);
		JMenuItem minus = new JMenuItem(" -");
		minus.setActionCommand("11");
		minus.addActionListener(this);
		minus.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		plusMenu.add(minus);
		JMenuItem cross = new JMenuItem(" x");
		cross.setActionCommand("12");
		cross.addActionListener(this);
		cross.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		plusMenu.add(cross);
		JMenuItem div = new JMenuItem(" /");
		div.setActionCommand("13");
		div.addActionListener(this);
		div.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		plusMenu.add(div);
		JMenuItem dec = new JMenuItem(" .");
		dec.setActionCommand("14");
		dec.addActionListener(this);
		dec.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		plusMenu.add(dec);
		plusMenu.setPopupSize(OPERATOR_WIDTH, OPERATOR_HEIGHT*5);
		
	//JToggleButton for power
		powerButton = new JToggleButton(icons[4]);
		powerButton.setActionCommand("4");
		powerButton.addActionListener(this);
		powerButton.setBounds(OPERATORS_MARGIN+4*OPERATOR_WIDTH, TOP_MARGIN+TITLE_HEIGHT, OPERATOR_WIDTH, OPERATOR_HEIGHT);
		powerButton.setBackground(bgColor);
		add(powerButton);
		
/*		rootMenu = new JPopupMenu();
		
		sqrt = new JToggleButton("\u221A");
		sqrt.setBackground(bgColor);
		sqrt.setActionCommand("50");
		sqrt.addActionListener(this);
		sqrt.setFont(new Font("Dialog", Font.PLAIN, 14));
		rootMenu.add(sqrt);
		cbrt = new JToggleButton("\u221B");
		cbrt.setBackground(bgColor);
		cbrt.setActionCommand("51");
		cbrt.addActionListener(this);
		cbrt.setFont(new Font("Dialog", Font.PLAIN, 14));
		rootMenu.add(cbrt);
		frrt = new JToggleButton("\u221C");
		frrt.setBackground(bgColor);
		frrt.setActionCommand("52");
		frrt.addActionListener(this);
		frrt.setFont(new Font("Dialog", Font.PLAIN, 14));
		rootMenu.add(frrt);		
		
		rootMenu.setPopupSize(OPERATOR_WIDTH, ((Double)(OPERATOR_HEIGHT*4.1)).intValue());
*/		
		sinMenu = new JPopupMenu();
		
		JMenuItem sin = new JMenuItem("sin");
		sin.setActionCommand("60");
		sin.addActionListener(this);
		sin.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		sinMenu.add(sin);
		JMenuItem cos = new JMenuItem("cos");
		cos.setActionCommand("61");
		cos.addActionListener(this);
		cos.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		sinMenu.add(cos);
		JMenuItem tan = new JMenuItem("tan");
		tan.setActionCommand("62");
		tan.addActionListener(this);
		tan.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		sinMenu.add(tan);
		JMenuItem cot = new JMenuItem("cot");
		cot.setActionCommand("63");
		cot.addActionListener(this);
		cot.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		sinMenu.add(cot);		
		
		sinMenu.setPopupSize(OPERATOR_WIDTH, OPERATOR_HEIGHT*4);
		
		
		arcMenu = new JPopupMenu();
		
		JMenuItem asin = new JMenuItem("sin");
		asin.setActionCommand("70");
		asin.addActionListener(this);
		asin.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		arcMenu.add(asin);
		JMenuItem acos = new JMenuItem("cos");
		acos.setActionCommand("71");
		acos.addActionListener(this);
		acos.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		arcMenu.add(acos);
		JMenuItem atan = new JMenuItem("tan");
		atan.setActionCommand("72");
		atan.addActionListener(this);
		atan.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		arcMenu.add(atan);
		JMenuItem acot = new JMenuItem("cot");
		acot.setActionCommand("73");
		acot.addActionListener(this);
		acot.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		arcMenu.add(acot);		
		
		arcMenu.setPopupSize(OPERATOR_WIDTH, OPERATOR_HEIGHT*4);
		
		
		logMenu = new JPopupMenu();
		
		JMenuItem log = new JMenuItem("log");
		log.setActionCommand("80");
		log.addActionListener(this);
		log.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		logMenu.add(log);
		JMenuItem ln = new JMenuItem("ln");
		ln.setActionCommand("81");
		ln.addActionListener(this);
		ln.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		logMenu.add(ln);
	
		logMenu.setPopupSize(OPERATOR_WIDTH, OPERATOR_HEIGHT*2);
		
	}				
	public static void setUpperBound(String s)
	{
		upperBound.setText(s);
	}		
	public static void setLowerBound(String s)
	{
		lowerBound.setText(s);
	}
	public static void setOperationInput()
	{
		operationInput = true;
		
//		if(Interaction.op instanceof Derivative ||
//		   Interaction.op instanceof Limit)
//			lowerInput = true;
		
		if(Interaction.op instanceof Integral ||
		   Interaction.op instanceof Sum ||
		   Interaction.op instanceof Product)
		{	
			lowerInput = true;
			
			if(Interaction.op instanceof Sum || Interaction.op instanceof Product)
				setExpression("x=_");
			else
				setExpression("_");
		}
	}
	public static void addLowerBound(String s)
	{
		addExpression(s);
	}
	public static void addUpperBound(String s)
	{
		addExpression(s);
	}
	public static void setEquals(String s)
	{
		equals.setText(s);
	}
	public static void setPreExp(Icon i)
	{
		preExp.setIcon(i);
	}
	public static void setComplexInput()
	{
		complexInput = true;
	}
	public static void insertExpression(JLabel jl, String s)
	{
		if(jl.equals(expression))
			expression.setText(expression.getText().substring(0, expression.getText().length()-2) + s 
				+ expression.getText().substring(expression.getText().length()-2, expression.getText().length()));
		else if(jl.equals(denominator))
			denominator.setText(denominator.getText().substring(0, denominator.getText().length()-2) + s 
				+ denominator.getText().substring(denominator.getText().length()-2, denominator.getText().length()));
	}	
	public static void setModulo(String s)
	{
		modulo.setText(s);
	}	
	public static void setBase(int base)
	{
		Stack digits = new Stack();
		
		while(base > 0)
		{
			digits.push(base%10);
			base = base / 10;
		}
		
		while(!digits.empty())
		{
			if((Integer)digits.peek() == 0)
				addExpression("\u2080");
			else if((Integer)digits.peek() == 1)
				addExpression("\u2081");
			else if((Integer)digits.peek() == 2)
				addExpression("\u2082");
			else if((Integer)digits.peek() == 3)
				addExpression("\u2083");
			else if((Integer)digits.peek() == 4)
				addExpression("\u2084");
			else if((Integer)digits.peek() == 5)
				addExpression("\u2085");
			else if((Integer)digits.peek() == 6)
				addExpression("\u2086");
			else if((Integer)digits.peek() == 7)
				addExpression("\u2087");
			else if((Integer)digits.peek() == 8)
				addExpression("\u2088");
			else if((Integer)digits.peek() == 9)
				addExpression("\u2089");
			
			digits.pop();
		}
	}
	public static void setSecond(String s)
	{
		second.setText(s);
	}
}