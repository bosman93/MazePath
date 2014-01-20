package graphic;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.activity.InvalidActivityException;
import javax.naming.directory.InvalidAttributeValueException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import logic.InputException;
import logic.MazeBoard;
import logic.PathFinder;
import logic.PathNotFoundException;



public class MainFrame extends JFrame {
	
	protected MazeBoard board;
	protected BoardPanel panel;
	protected PathFinder finder;
	protected String filepath;
        

    public MainFrame() {
    	super("Maze");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(400, 200);
       
		panel = null;
		
		createGUI();
    }
   
    private void findPathActionActionEvent(ActionEvent evt) // do if generate button was pressed
    {

			try {
		    if( board == null || finder == null)
				throw new InvalidAttributeValueException();
		    	
		    	board = finder.findPath(filepath.substring(0, filepath.lastIndexOf('.')) + "_log.txt");
		    	
    			if(panel != null)
    				this.remove(panel);
    			panel = new BoardPanel(board);
				add(panel);
		    	panel.repaint();
		    	this.revalidate();
		    
			} catch(PathNotFoundException e){
				JOptionPane.showMessageDialog(null, 
    					"No path found!", "Error", 
						JOptionPane.ERROR_MESSAGE);
			} catch (InvalidAttributeValueException e) {
				JOptionPane.showMessageDialog(null, 
    					"No maze opened!", "Error", 
						JOptionPane.ERROR_MESSAGE);
			} catch ( InvalidActivityException | ArrayIndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(null, 
    					"Exception detected. Please check correctness of your file!", "Error", 
						JOptionPane.ERROR_MESSAGE);
			}
	} 

	private void openActionEvent(ActionEvent evt) 
	{
        JFileChooser chooser = new JFileChooser();
        
    	int respond = chooser.showOpenDialog(null);
		chooser.setDialogTitle("Load Maze");
	
    	if (respond == JFileChooser.APPROVE_OPTION) 
    	{
    		filepath = chooser.getSelectedFile().getPath();

    		try 
    		{
    			board = new MazeBoard(filepath);
    			finder = new PathFinder(board);
    			JOptionPane.showMessageDialog(null, "Maze upload success.");
    			
    			if(panel != null)
    				this.remove(panel);
    			panel = new BoardPanel(board);
				add(panel);
				revalidate();
    		}
    		catch(IOException | InputException ex) 
    		{
    			JOptionPane.showMessageDialog(null, 
		    					"File exception detected. Select correct file.", "Error", 
								JOptionPane.ERROR_MESSAGE);
    		}
    	}
	            
	}
	

	private void exitActionEvent(ActionEvent evt) 
	{
		int selection = JOptionPane.showConfirmDialog((Component) null, 
										"Are you sure?", "Exit", 
								        JOptionPane.YES_NO_OPTION);
		
		if(selection == JOptionPane.YES_OPTION)
			this.dispose();  
	}
	

	protected JMenuBar createGUI()
    {
    	 // Creates a menubar for a JFrame
        JMenuBar menuBar = new JMenuBar();
        
        // Add the menubar to the frame
        setJMenuBar(menuBar);
        
        // Define and add three drop down menu to the menubar
        JMenu fileMenu 		= new JMenu("Maze");
        
        menuBar.add(fileMenu);
        
        //buttons for FILE
        JMenuItem findPathAction 	= new JMenuItem("Find Path");
        JMenuItem openAction 		= new JMenuItem("Load");
        JMenuItem exitAction 		= new JMenuItem("Exit");

        
        // add all buttons to menubar
        fileMenu.add(findPathAction);
        fileMenu.add(openAction);
        fileMenu.addSeparator();;
        fileMenu.add(exitAction);

        
        // define actions ----------------------------------------------------
        
        findPathAction.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
            	findPathActionActionEvent(evt);
            }
        });
        
        openAction.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
            	openActionEvent(evt);
            }      
         });
        
        exitAction.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
                exitActionEvent(evt);
            }
        });
        // action's definition end ---------------------------------------------------
        
        return menuBar; 
    }    
}
