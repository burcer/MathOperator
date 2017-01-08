/************************************************************************************************************
*This Java code is written by "7", whose members are Can Karakuþ, Þükrü Burç Eryýlmaz, Bilgehan Avþer,
*Ömer Sercan Arýk and Onur Günlü.Please include a brief reference comment while using in other applications.
**************************************************************************************************************
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.util.ArrayList;
import java.io.*;
import javax.swing.filechooser.*;

/*
 *Library 
 *This class stores functions in a temporary library or a file and retrieves and
 *uses them whenever needed by the user
 *
 *Author: Sukru Burc Eryilmaz
 */ 

public class LibraryPanel extends JPanel implements ActionListener
{	
	//properties
	final int WIDTH = 442;
	final int HEIGHT = 190;
	final int TITLE_HEIGHT = 25;
	final int BUTTONS_MARGIN = 320;
	final int TOP_MARGIN = 25;
	final int BUTTON_WIDTH = 90;
	final int BUTTON_HEIGHT = 22;
	final int BUTTON_SPACING = 10;
	
	
	JButton newF;
	JButton use;
	JButton edit;
	JButton delete;
	
	Function f = null;
	static ArrayList savedFunctions;
	
	static JTree library;
	static DefaultMutableTreeNode root;
	static String libName = "[Default Library]";
	static JScrollPane libView;
	static JFileChooser loader, saver;
	
	/*
	 *Default constructor
	 */
	public LibraryPanel()
	{
		setLayout(null);
	
	//Title
		JPanel title = new JPanel();
		title.setBounds(0,0,WIDTH,TITLE_HEIGHT);
		title.setBackground(new Color(128,128,255));
		
		JLabel titleLabel = new JLabel("Library");
		
		title.add(titleLabel);
		add(title);		
		
	//Area
		JPanel area = new JPanel();
		area.setBackground( Color.WHITE);
		area.setBounds(0, TITLE_HEIGHT, WIDTH, HEIGHT-TITLE_HEIGHT);
		
	//Tree
		root = new DefaultMutableTreeNode(libName);
		
		library = new JTree(root);
		library.setBounds(10,TITLE_HEIGHT+10, BUTTONS_MARGIN-15,HEIGHT-TITLE_HEIGHT-15);
		library.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
//		library.addTreeSelectionListener(this);
		
		libView = new JScrollPane(library);
		add(library);
				
		savedFunctions = new ArrayList();
		loader = new JFileChooser();
		loader.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		saver = new JFileChooser();
		saver.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		TextFileFilter filt = new TextFileFilter();
		loader.addChoosableFileFilter(filt);

	//Buttons
		newF = new JButton( "New");
		newF.setBackground( new Color(225,225,225));
		newF.setBounds(BUTTONS_MARGIN, TITLE_HEIGHT + TOP_MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT);
		newF.setActionCommand("new");
		newF.addActionListener(this);
		add(newF);
		
		use = new JButton( "Use");
		use.setBackground( new Color(225,225,225));
		use.setBounds(BUTTONS_MARGIN, TITLE_HEIGHT + TOP_MARGIN + BUTTON_HEIGHT + BUTTON_SPACING, BUTTON_WIDTH, BUTTON_HEIGHT);
		use.setActionCommand("use");
		use.addActionListener(this);
		add(use);
		
		edit = new JButton( "Edit");
		edit.setBackground( new Color(225,225,225));
		edit.setBounds(BUTTONS_MARGIN, TITLE_HEIGHT + TOP_MARGIN + BUTTON_HEIGHT*2 + BUTTON_SPACING*2, BUTTON_WIDTH, BUTTON_HEIGHT);
		edit.setActionCommand("edit");
		edit.addActionListener(this);
		add(edit);
		
		delete = new JButton( "Delete");
		delete.setBackground( new Color(225,225,225));
		delete.setBounds(BUTTONS_MARGIN, TITLE_HEIGHT + TOP_MARGIN + BUTTON_HEIGHT*3 + BUTTON_SPACING*3, BUTTON_WIDTH, BUTTON_HEIGHT);
		delete.setActionCommand("delete");
		delete.addActionListener(this);
		add(delete);
		
		add(area);	
	}
	
	/*
	 *???????????????????????????
	 */
	public void paint( Graphics g)
	{
		super.paint(g);
		g.drawLine(0,0,0,HEIGHT);
		g.drawLine(0,0,WIDTH,0);
	}
	
	/*
	 *main method to test the panel
	 */
	public static void main(String[] args)
	{
		JFrame p = new JFrame();
		p.setSize(600,400);
		p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LibraryPanel lp = new LibraryPanel();
		p.add(lp);
		p.setVisible(true);
	}
	
	/*
	 *implementations of the method inherited from JButton
	 *determines the option selected and does appropriate action
	 */
	public void actionPerformed(ActionEvent e)
	{
		String c = e.getActionCommand();
		
		if(c.equals("use"))
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)library.getLastSelectedPathComponent();

    		if (node == null)
    			JOptionPane.showMessageDialog(Interaction.interaction, "Please select a function.", "Error", JOptionPane.ERROR_MESSAGE);
			else
			{
				Object nodeInfo = node.getUserObject();
				if( node.isLeaf())
				{
					Function f = (Function) nodeInfo;
					Interaction.use(f);
				}
			}
    	}	
		
		else if(c.equals("new"))
		{
			Interaction.newFunction();
		}
		else if(c.equals("edit"))
		{
			Interaction.edit();
		}
		else if(c.equals("delete"))
		{
			Interaction.delete();
		}
	}
	
	/*
	 *adds a newly created function to the library tree
	 */
	public void newFunction(Function f)
	{
		DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode(f);
		root.add(dmtn);
	}
	
	/*
	 *In the implementation of the following code segment, the code from the web page
	 *http://www.leepoint.net/notes-java/GUI/containers/20dialogs/35filefilter.html
	 *has been utilized. The page contains information on how to use file filters.
	 */
	private class TextFileFilter extends javax.swing.filechooser.FileFilter
	{
		public TextFileFilter()
		{
			
		}
		public boolean accept(File f) 
		{
        	return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
    	}
    
    	public String getDescription() 
    	{
        	return "Text files";
   		}
	}	
}