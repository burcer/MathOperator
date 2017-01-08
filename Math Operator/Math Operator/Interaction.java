/************************************************************************************************************
*This Java code is written by "7", whose members are Can Karakuþ, Þükrü Burç Eryýlmaz, Bilgehan Avþer,
*Ömer Sercan Arýk and Onur Günlü.Please include a brief reference comment while using in other applications.
**************************************************************************************************************

This class creates the main window of the program, creates all neccessary panels and adds to the frame.
It also includes certain static methods that are used during the interaction of these panels.

Author: Can Karakuþ
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.io.*;
import java.util.*;

public class Interaction extends JFrame implements ActionListener
{
	//Constants
	static final int WINDOW_WIDTH = 1024;
	static final int WINDOW_HEIGHT = 768;
	static final int TOOLBAR_HEIGHT = 60;
	static final int INTERACTION_WIDTH = 582;
	static final int PLOT_HEIGHT = 366;
	static final int LIBRARY_HEIGHT = 190;
	static final int PLOT_TOOLBAR_HEIGHT = 30;
	static final Color bgColor = new Color(225, 225, 225);
	
	static ResultPanel result;
	static PlotPanel plot;
	static InteractionPanel interaction;
	static LibraryPanel library;
	static ToolbarPanel toolbar;
	static JPanel plotToolbar;
	static JLabel unitWidth;
	static JLabel unitHeight;
	
	static double uw = PlotPanel.unitWidth;
	static double uh = PlotPanel.unitHeight;
	
	static Double value = null;
	static Integer mod = null;
	static boolean hasMod = false;
	
	static Operation op;
	
	public Interaction()
	{
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Math Operator");
		setResizable(false);
		setLayout(null);
		
	//Menus
		JMenuBar menus = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu help = new JMenu("Help");
		
		JMenu userhelp = new JMenu( "Math Operator Help");
		
		JMenuItem fuHelp = new JMenuItem( "Help for Function");
		JMenuItem plHelp = new JMenuItem( "Help for Plotting");
		JMenuItem calHelp = new JMenuItem( "Help for Calculus");
		JMenuItem eqHelp = new JMenuItem( "Help for Equation");
		JMenuItem sumProHelp = new JMenuItem( "Help for Sum-Product");
		JMenuItem comHelp = new JMenuItem( "Help for Complex");
		JMenuItem modHelp = new JMenuItem( "Help for Modular");
		JMenuItem libHelp = new JMenuItem( "Help for Library");
		JMenuItem synHelp = new JMenuItem( "Help for Syntax");
		JMenuItem tbarHelp = new JMenuItem( "Help for Toolbar"); 

		JMenuItem saveLibrary = new JMenuItem("Save Library");
		JMenuItem loadLibrary = new JMenuItem("Load Library");
		JMenuItem exit = new JMenuItem("Exit");
		
		saveLibrary.addActionListener(this);
		loadLibrary.addActionListener(this);
		exit.addActionListener(this);
		fuHelp.addActionListener(this);
		plHelp.addActionListener(this);
		calHelp.addActionListener(this);
		eqHelp.addActionListener(this);
		sumProHelp.addActionListener(this);
		comHelp.addActionListener(this);
		modHelp.addActionListener(this);
		libHelp.addActionListener(this);
		synHelp.addActionListener(this);
		tbarHelp.addActionListener(this);

		file.add(saveLibrary);
		file.add(loadLibrary);
		file.add(exit);
		
		JMenuItem about = new JMenuItem("About...");
		about.addActionListener(this);
		help.add(about);
		
		JMenu helpItem = new JMenu("Math Operator Help");
		helpItem.addActionListener(this);
		help.add(helpItem);
		
		helpItem.add(fuHelp);
		helpItem.add(plHelp);
		helpItem.add(calHelp);
		helpItem.add(eqHelp);
		helpItem.add(sumProHelp);
		helpItem.add(comHelp);
		helpItem.add(modHelp);
		helpItem.add(libHelp);
		helpItem.add(synHelp);
		helpItem.add(tbarHelp);
		
		menus.add(file);
		menus.add(help);
		
		setJMenuBar(menus);
	
	//Toolbar
		toolbar = new ToolbarPanel();
		toolbar.setBounds(0,0,WINDOW_WIDTH,TOOLBAR_HEIGHT);
		add(toolbar);		
				
	//Interaction	
		interaction = new InteractionPanel();
		interaction.setBounds(0,TOOLBAR_HEIGHT, INTERACTION_WIDTH, WINDOW_HEIGHT-TOOLBAR_HEIGHT);
		
		add(interaction);

		plot = new PlotPanel();
		plot.setBounds(INTERACTION_WIDTH, TOOLBAR_HEIGHT, WINDOW_WIDTH-INTERACTION_WIDTH, PLOT_HEIGHT);
		add(plot);
		
	//Plot toolbar
		plotToolbar = makePlotToolbar();
		add(plotToolbar);
		
		library = new LibraryPanel();
		library.setBounds(INTERACTION_WIDTH, TOOLBAR_HEIGHT+PLOT_HEIGHT+PLOT_TOOLBAR_HEIGHT, WINDOW_WIDTH-INTERACTION_WIDTH,LIBRARY_HEIGHT);
		add(library);
		
		result = new ResultPanel();
		result.setBounds(INTERACTION_WIDTH, TOOLBAR_HEIGHT+PLOT_HEIGHT+PLOT_TOOLBAR_HEIGHT+LIBRARY_HEIGHT,WINDOW_WIDTH-INTERACTION_WIDTH, WINDOW_HEIGHT-(TOOLBAR_HEIGHT+PLOT_HEIGHT+LIBRARY_HEIGHT));
		add(result);
		
		//Get focus to interaction panel
		//This part(until the comment line below),is taken from the Java tutorials
		//in the webpage of Sun Microsystems:
		//http://java.sun.com/docs/books/tutorial/uiswing/misc/focus.html
		addWindowFocusListener(new WindowAdapter() {
    		public void windowGainedFocus(WindowEvent e) {
     		   interaction.requestFocusInWindow();
   	    	}
	    });
		//----------------------------------------
		
		//Set the operation to DAO
		op = new DAO();
	}
	
	public static void use(Function f)
	{
		InteractionPanel.addExpression( "[" + f + "]");
		op.addToDefinition( f.getDefinition());	
		manageFocus();
	}
	
	//Creates a new function for the library
	public static void newFunction()
	{
		if( op instanceof Function)
		{
			String name = (String) JOptionPane.showInputDialog(interaction, "Function name:", "New Function", JOptionPane.PLAIN_MESSAGE);
			try
			{
				if (name.equals("") || name == null )
				{
					JOptionPane.showMessageDialog(interaction, "Please enter a valid name.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					InteractionPanel.endOperations();
					Function nf = new Function(name, op.getDefinition());
					DefaultMutableTreeNode dm = new DefaultMutableTreeNode(nf);			
					
					DefaultTreeModel model = (DefaultTreeModel) LibraryPanel.library.getModel();
					model.insertNodeInto(dm, LibraryPanel.root, model.getChildCount(LibraryPanel.root));
				
					LibraryPanel.savedFunctions.add(nf);
				}
			}
			catch(NullPointerException e)	{}
		}
		else 	
			JOptionPane.showMessageDialog(interaction, "You cannot save this operation.", "Error", JOptionPane.ERROR_MESSAGE);
	}
  
	public static void main(String[] args)
	{
		Interaction i = new Interaction();
		i.setVisible(true);	
	}
	
	public static void manageFocus()
	{
		interaction.grabFocus();
	}
	
	public static void setResult(Object d)
	{
		result.setResult(d);
	}
	
	public static void setExpression(String s)
	{
		interaction.setExpression(s);
	}
	
	public static void promptValueAt()
	{
		if(op instanceof Function)
		{
			String val = (String) JOptionPane.showInputDialog(interaction, "Value at:", "Value at...", JOptionPane.PLAIN_MESSAGE);
			
			try
			{
				value = Double.parseDouble(val);
				
				Function func = (Function)op;
				setResult(func.valueAt(value));
			}
			catch(NumberFormatException exc)
			{
				JOptionPane.showMessageDialog(interaction, "Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
				promptValueAt();
			}
			catch(NullPointerException ex){}
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String s = e.getActionCommand();
		if (s.equals("Save Library"))
		{
			PrintWriter outputStream = null;
   			int returnVal = LibraryPanel.saver.showSaveDialog(interaction);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				File file = LibraryPanel.saver.getSelectedFile();
				try
				{
					if(!file.getName().substring(file.getName().length()-4, file.getName().length()).equals(".txt"))
						JOptionPane.showMessageDialog(interaction, "Please enter a file name that has extension .txt", "Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(StringIndexOutOfBoundsException ex)
				{
					JOptionPane.showMessageDialog(interaction, "Please enter a file name that has extension .txt", "Error", JOptionPane.ERROR_MESSAGE);
				}
				try
				{
					outputStream = new PrintWriter(new FileOutputStream( file.getPath()));
					outputStream.println("this_is_a_function_file");
					
				}
				catch( IOException exc)
				{
					JOptionPane.showMessageDialog(interaction, "Please enter a valid file name.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(interaction, "Please enter a valid file name.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				Function f;
				for (int i = 0; i < LibraryPanel.savedFunctions.size(); i ++ )
				{
					f = (Function)LibraryPanel.savedFunctions.get(i);
					outputStream.println( "\\" + f.toString() + "\\" + f.getDefinition());				
				}
				outputStream.close();	
			}		
		}
		
		if (s.equals("Load Library"))
		{
			int returnVal = LibraryPanel.loader.showDialog(interaction, "Load");
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
            	File file = LibraryPanel.loader.getSelectedFile();
            	Scanner inputStream = null;
            	String fileName = file.getPath();
            	try
            	{
            		inputStream = new Scanner (new FileInputStream(fileName));
            	}
            	catch(IOException ex)
            	{
            		JOptionPane.showMessageDialog(interaction, "This file does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            	}   
            	String str = inputStream.nextLine();
            	if( str.equals("this_is_a_function_file"))
            	{
            		StringTokenizer functionSep;
            		String name;
            		String def;
            		String functionStr = inputStream.nextLine();
            		if( functionStr.charAt(0) == '\\')
            		{   
            			LibraryPanel.libName = file.getName().substring(0, file.getName().length()-4);
            			LibraryPanel.root = new DefaultMutableTreeNode(LibraryPanel.libName);
           			    ((DefaultTreeModel)LibraryPanel.library.getModel()).setRoot(null);
           			    ((DefaultTreeModel)LibraryPanel.library.getModel()).setRoot(LibraryPanel.root);
            			LibraryPanel.savedFunctions.clear();
            			while( functionStr.charAt(0) == '\\')
            			{
            				functionSep = new StringTokenizer(functionStr,"\\");
            				name = functionSep.nextToken();
            				def = functionSep.nextToken();
            				Function nf = new Function(name, def);
							LibraryPanel.root.add(new DefaultMutableTreeNode(nf));
							LibraryPanel.savedFunctions.add(nf);
            				try
            				{
            					functionStr = inputStream.nextLine();
            				}
            				catch(NoSuchElementException ex){
            					break;
            				}           			 
            			}
            		}
            	}
            	else
            	{
            		JOptionPane.showMessageDialog(interaction, "This is not a valid file.", "Error", JOptionPane.ERROR_MESSAGE);
            	}      	
        	}   
		}
		else if(s.equals("About..."))
		{
			ImageIcon logo = new ImageIcon( "logo.JPG");
			String message = "Math Operator\nVersion 1.0\n\nThis software is developed";
			message += " for educational \n purposes by \"7\" whose members are \n ";
			message += "Can Karakus \n Bilgehan Avser \n Sukru Burc Eryilmaz \n Omer Sercan Arik \n Onur Gunlu";
			JOptionPane.showMessageDialog(interaction, message, "About...", JOptionPane.PLAIN_MESSAGE, logo);			 
		}
		else if(s.equals("zoom in"))
		{
			if(PlotPanel.interval > 3.7)
				PlotPanel.interval /= 2;
			plot.values();
			plot.repaint();
			unitWidth.setText("Unit Width:" + PlotPanel.unitWidth);
			unitHeight.setText("Unit Height:" + PlotPanel.unitHeight);
		}
		else if(s.equals("zoom out"))
		{
			if(PlotPanel.interval < 27)
				PlotPanel.interval *= 2;
			plot.repaint();
			unitWidth.setText("Unit Width:" + PlotPanel.unitWidth);
			unitHeight.setText("Unit Height:" + PlotPanel.unitHeight);
		}
		else if(s.equals("plus q"))
		{
			if(PlotPanel.sens > 0.005)
				PlotPanel.sens -= 0.005;
			plot.repaint();
		}
		else if(s.equals("minus q"))
		{
			if(PlotPanel.sens < 0.1)
				PlotPanel.sens += 0.005;
			plot.repaint();
		}
		else if(s.equals("Exit"))
		{
			dispose();
			System.exit(0);
		}
		if ( s.equals("Help for Function"))
		{
			ImageIcon logo = new ImageIcon( "logo.JPG");
			String message = "Click on \"Function\" menu to operate on functions. To define\n" + 
							 "a new function simply choose \"Define\" option. As you see the \n" +
							 "function f, type in a definition for a function obeying syntax \n" +
							 "after that, you can use this function in library as explained in \n" +
							 "\"Help for Library\". To find the value of an already typed function\n" +
							 "at a specified point, use \"Value at\" option and then enter the\n" +
							 "number to the area in the window that appears. As you click on OK\n" +
							 "button, you can see the result in result area.";
			JOptionPane.showMessageDialog(interaction, message, "Help for Function", JOptionPane.PLAIN_MESSAGE, logo); 							   
		}
		
		if ( s.equals( "Help for Plotting"))
		{
			ImageIcon logo = new ImageIcon( "logo.JPG");
			String message = "After defining a function that is displayed on the interaction\n" +
							 "area you can choose this option to see the graph of the function\n" +
							 "in Plotting Area by clicking \"Plot\" option under \"Plotting\" menu.\n" +
							 "After defining an integral, you can use the \"Shaded Area\" option \n" +
							 "under the same menu to visualize the corresponding area under \n" +
							 "the curve. Similarly, you can use \"Tangent Line\" option after\n" +
							 "defining a derivative. You can also adjust the scale or the quality \n." +
							 "of the plot using the buttons below the Plotting Area.";
			JOptionPane.showMessageDialog(interaction, message, "Help for Plotting", JOptionPane.PLAIN_MESSAGE, logo); 		
		}
		
		if ( s.equals( "Help for Calculus"))
		{
			ImageIcon logo = new ImageIcon( "logo.JPG");
			String message = "If you want to find the limit of a function you can choose this option\n" +
							 "and type the function near the limit symbol. the\n" +
							 " You can see the result in the result area after clicking\n" +
							 "\"Execute\". To find the integral of a function between two points, simply\n" +
							 "click on \"Integral\" and enter the upper and lower bounds, along with the \n" + 
							 "function to be integrated. You can see the\n" +
							 "result in the result area. Derivative of a function at a point can be\n" +
							 "also found by choosing \"Derivative\", setting the point by keyboard and\n" +
							 "entering the function.";   
			JOptionPane.showMessageDialog(interaction, message, "Help for Calculus", JOptionPane.PLAIN_MESSAGE, logo); 		
		}
		
		if ( s.equals( "Help for Equation"))
		{
			ImageIcon logo = new ImageIcon( "logo.JPG");
			String message = "You can find the roots of a polynomial up to fourth degree. Choose the\n"+
							 "\"Define\" option for that task and start typing in the left hand side\n" +
							 "of the equation. As you are done with this side, press Enter to type\n" +
							 "the other side. To find the roots of the equation you write, click\n" + 
							 "\"Execute\" and see the roots in the result area.";
			JOptionPane.showMessageDialog(interaction, message, "Help for Equation", JOptionPane.PLAIN_MESSAGE, logo); 		
		}
		
		if ( s.equals( "Help for Sum-Product"))
		{
			ImageIcon logo = new ImageIcon( "logo.JPG");
			String message ="To evaluate a summation or a product, simply click on \"Summation\"" +
							"or \"Product\" options under Sum-Product Menu. First enter the lower" +
							"bound, then the upper bound, and finally the function. Click on the" + 
							"\"Execute\" button to see the result in the Result Area.";
			JOptionPane.showMessageDialog(interaction, message, "Help for Sum-Product", JOptionPane.PLAIN_MESSAGE, logo); 		
		}
		
		if ( s. equals( "Help for Complex"))
		{
			ImageIcon logo = new ImageIcon( "logo.JPG");
			String message = "To make operations that include complex numbers, choose \"Define\" and\n" +
							 "type the complex expression involving \"i\". To enter imaginary number" +
							 "i, use the key I on the keyboard. Use Enter key to stop entering the complex\n" +
							 "number. You can switch the form of the result (either polar or normal form)\n." +
							 "using \"Polar Form\" and \"Normal Form\" options under Complex menu.";
					 
			JOptionPane.showMessageDialog(interaction, message, "Help for Complex", JOptionPane.PLAIN_MESSAGE, logo); 
		}
		
		if ( s. equals( "Help for Modular"))
		{
			ImageIcon logo = new ImageIcon( "logo.JPG");
			String message = "In Equation mode or direct operation mode this option can be used. \n" +
							 "Click on \"Set Modulo\" and enter the modulo as an integer, then click\n" +
							 "OK. After that, unless you click Unset Modulo, all operations are made \n" +
							 "according to this modulo.";
			JOptionPane.showMessageDialog(interaction, message, "Help for Modular", JOptionPane.PLAIN_MESSAGE, logo); 
		}
		
		if ( s.equals( "Help for Library"))
		{
			ImageIcon logo = new ImageIcon( "logo.JPG");
			String message = "While a fully typed function is displayed, choose\"New\" and enter a name\n" +
							 "for the function to save it in the library. You can later use it in \n" +
							 "any operation that includes functions by clicking on its name and then\n" +
							 "\"Use\". If you click on \"Delete\", the selected function will be deleted\n"+
							 "from the library. Using Edit button, a selected function can be renamed. By \n" + 
							 "choosing \"Save Library\" in the \"File\" menu, you can save the library at a " +
							 "directory as a text file. To reach the library again choose \"Load Library\""+
							 "and choose the file.\n";
			JOptionPane.showMessageDialog(interaction, message, "Help for Library", JOptionPane.PLAIN_MESSAGE, logo); 					   
		}
		
		if ( s.equals( "Help for Toolbars"))
		{
			ImageIcon logo = new ImageIcon( "logo.JPG");
			String message = "The usage of \"Execute\" is explained in other sections. \"Undo cancels\n"+
							 "the last action and \"Clear\" clears the interaction area and current\n"+
							 "operation. The symbols are used in typing as explained in \"Syntax\".";
			JOptionPane.showMessageDialog(interaction, message, "Help for Toolbars", JOptionPane.PLAIN_MESSAGE, logo); 	
		}
		
		if ( s.equals( "Help for Syntax"))
		{
			ImageIcon logo = new ImageIcon( "logo.JPG");
			String message = "The constants e and " + "\u03C0" + " can be used directly from the toolbars \n" +
			                 "while typing. The operations between terms must be always indicated by \n"+
			                 "selecting the appropriate option in the toolbar. The paranthyses must\n" +
			                 "be put appropriately from the icons. While writing a fraction, first type\n"+
			                 "the numerator, click that icon and then type the denominator. to write\n" +
			                 "powers or roots, click the corresponding icon after typing the base, \n"+
			                 "then enter the power or the root, and click on the same icon to deactivate\n"+
			                 "that mode. The functions seen can be used directly by clicking and then\n"+
			                 "typing the inside. ";
			JOptionPane.showMessageDialog(interaction, message, "Help for Syntax", JOptionPane.PLAIN_MESSAGE, logo); 		          							
		}		
	}
	
	public static void promptSetModulo()
	{
		try
		{
			if(op instanceof DAO || op instanceof Equation)
			{
				String m = (String) JOptionPane.showInputDialog(interaction, "Modulo:", "Set Modulo", JOptionPane.PLAIN_MESSAGE);
				
				try
				{
					mod = Integer.parseInt(m);
					interaction.setModulo("mod " + mod);
					DAO.setModulo(mod);
						Equation.setModulo(mod);
					ToolbarPanel.setModulo.setText("Unset Modulo");
					ToolbarPanel.setModulo.setActionCommand("unset modulo");
				}
				catch(NumberFormatException exc)
				{
					if(m != null)
					{	
						JOptionPane.showMessageDialog(interaction, "Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
						promptSetModulo();
					}
				}
				catch(NullPointerException ex){}
		
			}
			else
				JOptionPane.showMessageDialog(interaction, "You cannot set modulo for this operation.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(NumberFormatException ex) {
			JOptionPane.showMessageDialog(interaction, "Please check your expression.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(NullPointerException exc) {
			JOptionPane.showMessageDialog(interaction, "Please check your expression.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(StringIndexOutOfBoundsException excptn) {
			JOptionPane.showMessageDialog(interaction, "Please check your expression.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(ArithmeticException excp){
			JOptionPane.showMessageDialog(interaction, "Please check your expression.", "Error", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	public static void edit()
	{
		try
		{
			String newName = (String)JOptionPane.showInputDialog(interaction, "New name for the function:", "Edit Function", JOptionPane.PLAIN_MESSAGE);
			
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)LibraryPanel.library.getLastSelectedPathComponent();
			
			Function func = (Function) node.getUserObject();
			func.setName(newName);
			node.setUserObject(func);
			
			DefaultTreeModel model = (DefaultTreeModel) LibraryPanel.library.getModel();
			model.reload();
		}
		catch(NullPointerException ex)
		{
			JOptionPane.showMessageDialog(interaction, "Please select a function.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void delete()
	{
		if(LibraryPanel.library.getLastSelectedPathComponent() == null)
			JOptionPane.showMessageDialog(interaction, "Please select a function.", "Error", JOptionPane.ERROR_MESSAGE);
		else
		{
			int option = JOptionPane.showConfirmDialog(interaction, "Are you sure you want to delete this function?",
									 "Delete Function", JOptionPane.OK_CANCEL_OPTION);
		
			if(option == JOptionPane.CANCEL_OPTION)
			{}
			else
			{
				DefaultTreeModel model = (DefaultTreeModel)LibraryPanel.library.getModel();
				model.removeNodeFromParent((DefaultMutableTreeNode)LibraryPanel.library.
											getLastSelectedPathComponent());
				model.reload();
			}		
		}
	}
	
	public static void plot()
	{
		try
		{
			if(Interaction.op instanceof Function)
			{
			
				PlotPanel.f = (Function)Interaction.op;
				plot.drawPlot();
				uw = plot.unitWidth;
				uh = plot.unitHeight;
				unitWidth.setText("Unit Width:" + +plot.unitWidth);
				unitHeight.setText("Unit Height:" + plot.unitHeight);
				//System.out.println(PlotPanel.max);
			
			}
		}
		catch(NumberFormatException ex) {
			JOptionPane.showMessageDialog(interaction, "Please check your expression.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(NullPointerException exc) {
			JOptionPane.showMessageDialog(interaction, "Please check your expression.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(StringIndexOutOfBoundsException excptn) {
			JOptionPane.showMessageDialog(interaction, "Please check your expression.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(ArithmeticException excp){
			JOptionPane.showMessageDialog(interaction, "Please check your expression.", "Error", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	public static void tangentLine()
	{
		try
		{
			if(Interaction.op instanceof Derivative)
			{	
				InteractionPanel.endOperations();
				Derivative d = (Derivative) Interaction.op;
				PlotPanel.f = d.getFunction();
				plot.drawTangentLine(d);
			}
			else
				JOptionPane.showMessageDialog(interaction, "Please evaluate a derivative first.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(NumberFormatException ex) {
			JOptionPane.showMessageDialog(interaction, "Please check your expression.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(NullPointerException exc) {
			JOptionPane.showMessageDialog(interaction, "Please check your expression.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(StringIndexOutOfBoundsException excptn) {
			JOptionPane.showMessageDialog(interaction, "Please check your expression.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(ArithmeticException excp){
			JOptionPane.showMessageDialog(interaction, "Please check your expression.", "Error", JOptionPane.ERROR_MESSAGE);
		}	
	}
	public static void shadedArea()
	{
		try
		{
			if(Interaction.op instanceof Integral)
			{	
				InteractionPanel.endOperations();
				Integral i = (Integral) Interaction.op;
				PlotPanel.f = i.getFunction();
				plot.shadedArea(i.getLowerBound(), i.getUpperBound());
			}
			else
				JOptionPane.showMessageDialog(interaction, "Please evaluate an integral first.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(NumberFormatException ex) {
			JOptionPane.showMessageDialog(interaction, "Please check your expression.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(NullPointerException exc) {
			JOptionPane.showMessageDialog(interaction, "Please check your expression.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(StringIndexOutOfBoundsException excptn) {
			JOptionPane.showMessageDialog(interaction, "Please check your expression.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(ArithmeticException excp){
			JOptionPane.showMessageDialog(interaction, "Please check your expression.", "Error", JOptionPane.ERROR_MESSAGE);
		}	
	}
	public JPanel makePlotToolbar()
	{
		JPanel pt = new JPanel();
		pt.setBounds(INTERACTION_WIDTH, TOOLBAR_HEIGHT+PLOT_HEIGHT, WINDOW_WIDTH-INTERACTION_WIDTH, PLOT_TOOLBAR_HEIGHT);
		pt.setBackground(bgColor);
		
		SpringLayout layout = new SpringLayout();
		
		unitWidth = new JLabel("Unit width:" + uw);
		unitHeight = new JLabel("Unit height:" + uh);
		unitWidth.setFont(new Font("Dialog", Font.PLAIN, 10));
		unitHeight.setFont(new Font("Dialog", Font.PLAIN, 10));
		JLabel zoom = new JLabel("Zoom:");
		JButton zoomIn = new JButton(new ImageIcon("zoomin.JPG"));
		JButton zoomOut = new JButton(new ImageIcon("zoomout.JPG"));
		
		zoomIn.setActionCommand("zoom in");
		zoomIn.addActionListener(this);
		zoomOut.setActionCommand("zoom out");
		zoomOut.addActionListener(this);
		
		JLabel quality = new JLabel("Quality:");
		JButton plusQ = new JButton("+");
		JButton minusQ = new JButton("-");
		
		plusQ.setActionCommand("plus q");
		plusQ.addActionListener(this);
		minusQ.setActionCommand("minus q");
		minusQ.addActionListener(this);
		
		zoomIn.setBackground(bgColor);
		zoomOut.setBackground(bgColor);
		plusQ.setBackground(bgColor);
		minusQ.setBackground(bgColor);
		
		zoomIn.setPreferredSize(new Dimension(35,30));
		zoomOut.setPreferredSize(new Dimension(35,30));
		plusQ.setPreferredSize(new Dimension(43,30));
		minusQ.setPreferredSize(new Dimension(43,30));
		
		pt.add(unitWidth);
		pt.add(unitHeight);
		pt.add(zoomIn);
		pt.add(zoomOut);
		pt.add(quality);
		pt.add(plusQ);
		pt.add(minusQ);
		pt.add(zoom);
		
		pt.setLayout(layout);
		layout.putConstraint(SpringLayout.WEST, unitWidth, 5, SpringLayout.WEST, pt);
		layout.putConstraint(SpringLayout.WEST, unitHeight, 15, SpringLayout.EAST, unitWidth);
		layout.putConstraint(SpringLayout.WEST, zoom, 10, SpringLayout.EAST, unitHeight);
		layout.putConstraint(SpringLayout.WEST, zoomIn, 5,  SpringLayout.EAST, zoom);
		layout.putConstraint(SpringLayout.WEST, zoomOut, 5,  SpringLayout.EAST, zoomIn);
		layout.putConstraint(SpringLayout.WEST, quality, 5, SpringLayout.EAST, zoomOut);
		layout.putConstraint(SpringLayout.WEST, plusQ, 5,  SpringLayout.EAST, quality);
		layout.putConstraint(SpringLayout.WEST, minusQ, 5,  SpringLayout.EAST, plusQ);
		
		return pt;
	}
	
	public static void mathError()
	{
		JOptionPane.showMessageDialog(interaction, "Math error.Please check your expression.", "Error", JOptionPane.ERROR_MESSAGE);
	}
}