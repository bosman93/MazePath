package graphic;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import logic.MazeBoard;
import logic.MazeCell.State;

public class BoardPanel extends JPanel
{
	private MazeBoard board;

	public BoardPanel(MazeBoard board) 
	{
		super();
		this.board = board;
		setBackground(Color.WHITE);
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		if(board != null)
			paintBoard(g);
	}


	
	protected void paintBoard(Graphics g)
	{
		int height = board.getHeight();
		int width = board.getWidth();
		
		for(int i = 0; i < height; ++i)
			for(int j = 0; j < width; ++j) {
				
				if(board.isWall(i, j) == true)
					g.setColor(Color.gray);    
				else if(board.getState(i, j) == State.VISITED) 
					g.setColor(Color.orange); 
				else 
					g.setColor(Color.white);					
				
				int yUnit = ((this.getSize().height) / height);
				int xUnit = ((this.getSize().width) / width);
				int unit = Math.min(xUnit, yUnit);
				g.fillRect((j)*unit,(i)*unit, unit, unit);	
			}
	}
}
