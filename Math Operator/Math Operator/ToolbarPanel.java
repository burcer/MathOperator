/************************************************************************************************************
*This Java code is written by "7", whose members are Can Karakuþ, Þükrü Burç Eryýlmaz, Bilgehan Avþer,
*Ömer Sercan Arýk and Onur Günlü.Please include a brief reference comment while using in other applications.
**************************************************************************************************************

This class creates the toolbar for the program and calls proper methods when user clicks on the buttons.

Author: Can Karakuþ
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ToolbarPanel extends JPanel implements ActionListener
{
	//Buttons
	JButton function;
	JButton plotting;
	JButton calculus;
	JButton equation;
	JButton sumProduct;
	JButton complex;
	JButton modular;
	
	//Pop up menus
	JPopupMenu funcMenu;
	JPopupMenu plotMenu;
	JPopupMenu calcMenu;
	JPopupMenu equMenu;
	JPopupMenu sumProMenu;
	JPopupMenu complexMenu;
	JPopupMenu modMenu;
	
	static JMenuItem setModulo;
	
	//Constants
	final int BUTTON_HEIGHT =35;
	final int BUTTON_WIDTH = 132;
	final int TOP_MARGIN = 15;
	final int LEFT_MARGIN = 13;
	final int BUTTON_SPACING = 10;
	final int MENU_ITEM_HEIGHT = 25;
	final Color bgColor = new Color(225,225,225);
	
	//Constructor
	public ToolbarPanel()
	{
		setLayout(null);
	
	//Create the buttons, set icons and texts
		
		function = new JButton(); 
		plotting = new JButton();
		calculus = new JButton();
		equation = new JButton();
		sumProduct = new JButton();
		complex = new JButton();
		modular = new JButton();
	
	//Icons
		ImageIcon funcIcon = new ImageIcon("funcIcon.JPG");
		function.setIcon(funcIcon);
		
		ImageIcon plotIcon = new ImageIcon("plotIcon.JPG");
		plotting.setIcon(plotIcon);
		
		ImageIcon calcIcon = new ImageIcon("calcIcon.JPG");
		calculus.setIcon(calcIcon);
		
		ImageIcon equIcon = new ImageIcon("equIcon.JPG");
		equation.setIcon(equIcon);
		
		ImageIcon sumIcon = new ImageIcon("sumIcon.JPG");
		sumProduct.setIcon(sumIcon);
		
		ImageIcon complexIcon = new ImageIcon("complexIcon.JPG");
		complex.setIcon(complexIcon);
		
		ImageIcon modIcon = new ImageIcon("modIcon.JPG");
		modular.setIcon(modIcon);
		
		function.setText("Function");
		plotting.setText("Plotting");
		calculus.setText("Calculus");
		equation.setText("Equation");
		sumProduct.setText("Sum-Product");
		complex.setText("Complex");
		modular.setText("Modular");
		
		function.setBackground(bgColor);
		plotting.setBackground(bgColor);
		calculus.setBackground(bgColor);
		equation.setBackground(bgColor);
		sumProduct.setBackground(bgColor);
		complex.setBackground(bgColor);
		modular.setBackground(bgColor);
		
		function.setBounds(LEFT_MARGIN,TOP_MARGIN,BUTTON_WIDTH,BUTTON_HEIGHT);
		plotting.setBounds(LEFT_MARGIN + BUTTON_WIDTH*1 + BUTTON_SPACING*1,TOP_MARGIN,BUTTON_WIDTH,BUTTON_HEIGHT);
		calculus.setBounds(LEFT_MARGIN + BUTTON_WIDTH*2 + BUTTON_SPACING*2,TOP_MARGIN,BUTTON_WIDTH,BUTTON_HEIGHT);
		equation.setBounds(LEFT_MARGIN + BUTTON_WIDTH*3 + BUTTON_SPACING*3,TOP_MARGIN,BUTTON_WIDTH,BUTTON_HEIGHT);
		sumProduct.setBounds(LEFT_MARGIN + BUTTON_WIDTH*4 + BUTTON_SPACING*4,TOP_MARGIN,BUTTON_WIDTH,BUTTON_HEIGHT);
		complex.setBounds(LEFT_MARGIN + BUTTON_WIDTH*5 + BUTTON_SPACING*5,TOP_MARGIN,BUTTON_WIDTH,BUTTON_HEIGHT);
		modular.setBounds(LEFT_MARGIN + BUTTON_WIDTH*6 + BUTTON_SPACING*6,TOP_MARGIN,BUTTON_WIDTH,BUTTON_HEIGHT);
		
		function.setActionCommand("function");
		function.setActionCommand("plotting");
		function.setActionCommand("calculus");
		function.setActionCommand("equation");
		function.setActionCommand("sumProduct");
		function.setActionCommand("complex");
		function.setActionCommand("modular");
		
		add(function);
		add(plotting);
		add(calculus);
		add(equation);
		add(sumProduct);
		add(complex);
		add(modular);
		
	//Create the pop-up menus
		funcMenu = new JPopupMenu();
		
		JMenuItem defineFunction = new JMenuItem("Define");
		defineFunction.setActionCommand("define function");
		defineFunction.addActionListener(this);
		JMenuItem valueAt = new JMenuItem("Value at...");
		valueAt.setActionCommand("value at");
		valueAt.addActionListener(this);
		
		funcMenu.add(defineFunction);
		funcMenu.add(valueAt);
		funcMenu.setPopupSize(BUTTON_WIDTH, MENU_ITEM_HEIGHT*2);
		function.setActionCommand("function");
		function.addActionListener(this);
		
		plotMenu = new JPopupMenu();
		
		JMenuItem plot = new JMenuItem("Plot");
		plot.setActionCommand("plot");
		plot.addActionListener(this);
		JMenuItem tangentLine = new JMenuItem("Tangent Line");
		tangentLine.setActionCommand("tangent line");
		tangentLine.addActionListener(this);
		JMenuItem shadedArea = new JMenuItem("Shaded Area");
		shadedArea.setActionCommand("shaded area");
		shadedArea.addActionListener(this);
		
		plotMenu.add(plot);
		plotMenu.add(tangentLine);
		plotMenu.add(shadedArea);
		plotMenu.setPopupSize(BUTTON_WIDTH, MENU_ITEM_HEIGHT*3);
		plotting.setActionCommand("plotting");
		plotting.addActionListener(this);
		
		calcMenu = new JPopupMenu();
		
		JMenuItem limit = new JMenuItem("Limit");
		limit.setActionCommand("limit");
		limit.addActionListener(this);
		JMenuItem derivative = new JMenuItem("Derivative");
		derivative.setActionCommand("derivative");
		derivative.addActionListener(this);
		JMenuItem integral = new JMenuItem("Integral");
		integral.setActionCommand("integral");
		integral.addActionListener(this);
				
		calcMenu.add(limit);
		calcMenu.add(derivative);
		calcMenu.add(integral);
		calcMenu.setPopupSize(BUTTON_WIDTH, MENU_ITEM_HEIGHT*3);
		calculus.setActionCommand("calculus");
		calculus.addActionListener(this);
		
		equMenu = new JPopupMenu();
		
		JMenuItem defineEquation = new JMenuItem("Define");
		defineEquation.setActionCommand("define equation");
		defineEquation.addActionListener(this);
				
		equMenu.add(defineEquation);
		equMenu.setPopupSize(BUTTON_WIDTH, MENU_ITEM_HEIGHT);
		equation.setActionCommand("equation");
		equation.addActionListener(this);
		
		sumProMenu = new JPopupMenu();
		
		JMenuItem sum = new JMenuItem("Summation");
		sum.setActionCommand("sum");
		sum.addActionListener(this);
		JMenuItem product = new JMenuItem("Product");
		product.setActionCommand("product");
		product.addActionListener(this);
				
		sumProMenu.add(sum);
		sumProMenu.add(product);
		sumProMenu.setPopupSize(BUTTON_WIDTH, MENU_ITEM_HEIGHT*2);
		sumProduct.setActionCommand("sum product");
		sumProduct.addActionListener(this);
		
		complexMenu = new JPopupMenu();
		
		JMenuItem defineComplex = new JMenuItem("Define");
		defineComplex.setActionCommand("define complex");
		defineComplex.addActionListener(this);
		JCheckBoxMenuItem polarForm = new JCheckBoxMenuItem("Polar Form", false);
		polarForm.setActionCommand("polar form");
		polarForm.addActionListener(this);
		JCheckBoxMenuItem normalForm = new JCheckBoxMenuItem("Normal Form", true);
		normalForm.setActionCommand("normal form");
		normalForm.addActionListener(this);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(normalForm);
		bg.add(polarForm);
		
		complexMenu.add(defineComplex);
		complexMenu.add(normalForm);
		complexMenu.add(polarForm);
		complexMenu.setPopupSize(BUTTON_WIDTH, MENU_ITEM_HEIGHT*3);
		complex.setActionCommand("complex");
		complex.addActionListener(this);
		
		modMenu = new JPopupMenu();
		
		setModulo = new JMenuItem("Set Modulo");
		setModulo.setActionCommand("set modulo");
		setModulo.addActionListener(this);
				
		modMenu.add(setModulo);
		modMenu.setPopupSize(BUTTON_WIDTH, MENU_ITEM_HEIGHT);
		modular.setActionCommand("modular");
		modular.addActionListener(this);
	}
	
	//ActionListener method
	public void actionPerformed(ActionEvent e)
	{
		String com = e.getActionCommand();
		
		if(com.equals("function"))
			funcMenu.show(this, (int)function.getBounds().getX(), (int)function.getBounds().getY()+BUTTON_HEIGHT);
			
		else if(com.equals("plotting"))	
			plotMenu.show(this, (int)plotting.getBounds().getX(), (int)plotting.getBounds().getY()+BUTTON_HEIGHT);
			
		else if(com.equals("calculus"))		
			calcMenu.show(this, (int)calculus.getBounds().getX(), (int)calculus.getBounds().getY()+BUTTON_HEIGHT);
			
		else if(com.equals("equation"))	
			equMenu.show(this, (int)equation.getBounds().getX(), (int)equation.getBounds().getY()+BUTTON_HEIGHT);
		
		else if(com.equals("sum product"))		
			sumProMenu.show(this, (int)sumProduct.getBounds().getX(), (int)sumProduct.getBounds().getY()+BUTTON_HEIGHT);
			
		else if(com.equals("complex"))		
			complexMenu.show(this, (int)complex.getBounds().getX(), (int)complex.getBounds().getY()+BUTTON_HEIGHT);
		
		else if(com.equals("modular"))		
			modMenu.show(this, (int)modular.getBounds().getX(), (int)modular.getBounds().getY()+BUTTON_HEIGHT);

		else if(com.equals("define function"))
		{
			Interaction.op = new Function();
			InteractionPanel.setPreExp("f(x)=");		
		}
		else if(com.equals("value at"))
		{	
			InteractionPanel.endOperations();
			Interaction.promptValueAt();
		}
		else if(com.equals("plot"))
		{
			InteractionPanel.endOperations();
			Interaction.plot();
		}
		else if(com.equals("tangent line"))
		{
			InteractionPanel.endOperations();
			Interaction.tangentLine();
		}
		else if(com.equals("shaded area"))
		{
			InteractionPanel.endOperations();
			Interaction.shadedArea();
		}
		else if(com.equals("limit"))
		{
			InteractionPanel.setExpression("");
			InteractionPanel.setPreExp("lim");
			InteractionPanel.setLowerBound("x" + "\u2192" + "_");
			InteractionPanel.setOperationInput();
			Interaction.op = new Limit();
		}
		else if(com.equals("integral"))
		{
			InteractionPanel.setExpression("");
			InteractionPanel.setPreExp("\u222B");
			InteractionPanel.setEquals("dx");
			
			Interaction.op = new Integral();
			InteractionPanel.setOperationInput();
		}
		else if(com.equals("sum"))
		{
			InteractionPanel.setExpression("");
			InteractionPanel.setPreExp("\u2211");
						
			Interaction.op = new Sum();
			InteractionPanel.setOperationInput();
		}
		else if(com.equals("product"))
		{
			InteractionPanel.setExpression("");
			InteractionPanel.setPreExp("\u03A0");
						
			Interaction.op = new Product();
			InteractionPanel.setOperationInput();
		}
		else if(com.equals("derivative"))
		{
			InteractionPanel.setExpression("");
			InteractionPanel.setPreExp(new ImageIcon("deriv.JPG"));
			InteractionPanel.setLowerBound("x =_");
			InteractionPanel.setOperationInput();
			Interaction.op = new Derivative();
		}
		else if(com.equals("define equation"))
		{
			InteractionPanel.setEquals(" = ");
			Interaction.op = new Equation();
		}
		else if(com.equals("define complex"))
		{
			if(Interaction.op instanceof DAO)
			{
				Interaction.op = new ComplexDAO(Interaction.op.getDefinition());
			}
			if(Interaction.op instanceof ComplexDAO)
			{
				Interaction.op.addToDefinition("[");
				InteractionPanel.setExpression( InteractionPanel.getExpression().substring(0, InteractionPanel.getExpression().length()-1) + "(_)");
				InteractionPanel.setComplexInput();
			}
		}
		else if(com.equals("polar form"))
		{
			ResultPanel.polar = true;
		}
		else if(com.equals("normal form"))
		{
			ResultPanel.polar = false;
		}
		else if(com.equals("set modulo"))
		{
			Interaction.promptSetModulo();
		}
		else if(com.equals("unset modulo"))
		{
			setModulo.setText("Set Modulo");
			setModulo.setActionCommand("set modulo");
			Equation.unsetModulo();
			DAO.unsetModulo();
			InteractionPanel.setModulo("");
		}

		Interaction.manageFocus();
	}
}