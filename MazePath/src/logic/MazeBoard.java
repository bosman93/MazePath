package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.activity.InvalidActivityException;

import logic.MazeCell.State;

public class MazeBoard {
	
	public MazeBoard(String filepath) throws IOException, InputException
	{
		loadMaze(filepath);
		if(start == null || end == null)
			throw new InputException();
	}
	
	public int getWidth()
	{
		return board[0].length;
	}
	
	public int getHeight()
	{
		return board.length;
	}
	
	public boolean isStart(int i, int j) throws ArrayIndexOutOfBoundsException
	{
		if(start.equals(board[i][j]))
			return true;
		else
			return false;
	}
	
	public boolean isEnd(int i, int j) throws ArrayIndexOutOfBoundsException
	{
		if(end.equals(board[i][j]))
			return true;
		else
			return false;
	}
	
	public boolean isWall(int i, int j) throws ArrayIndexOutOfBoundsException
	{
		return board[i][j].isWall();
	}
	
	public void setState(int i, int j, State state) throws ArrayIndexOutOfBoundsException, InvalidActivityException
	{
		board[i][j].setState(state);
	}
	
	public State getState(int i, int j) throws ArrayIndexOutOfBoundsException
	{
		return board[i][j].getState();
	}
	
	public MazeCell get(int i, int j) throws ArrayIndexOutOfBoundsException
	{
		return board[i][j];
	}
	
	public MazeCell getStart()
	{
		return start;
	}
	
	public MazeCell getEnd()
	{
		return end;
	}
	
	protected void loadMaze(String filepath) throws IOException, InputException
	{
		ArrayList<String> temp = new ArrayList<String>();
		
		Path file = Paths.get(filepath);
        BufferedReader reader = null;
        try
        {
            reader = Files.newBufferedReader(file, Charset.forName("UTF-8"));
            String line;
            
            if( (line = reader.readLine()) != null)
            {
            	temp.add(new String(line));
            	int width = line.length();
	            	
	            while ( (line = reader.readLine()) != null)          	
	            		temp.add(new String(line));
	            
	            int height = temp.size();
	            
	            board = new MazeCell[height][width];
	            
	            boolean isWallCurrent = false;
	            
	            for(int i = 0; i < height; i++) {
	            	for(int j = 0; j < width; j++) {
	            		
	            		String currentChar = temp.get(i).substring(j, j+1);
						
	            		if(currentChar.equals("#")) // sciana
							isWallCurrent = true;
	            		
						else if(currentChar.equals("0"))
							isWallCurrent = false;
	            		
						else if(currentChar.equals("E")) {
							if(start != null)
								throw new InputException(); // nie jest dozwolone wielokrotne wejscie
							isWallCurrent = false;
						}
	            		
						else if(currentChar.equals("X")) {
							if(end != null)
								throw new InputException(); // nie dozwolone wielokrotne wyjscie
							isWallCurrent = false;
						}
	            		
						
						board[i][j] = new MazeCell(i, j, isWallCurrent); // utworzenie nowej komorki tablicy
						
						if(currentChar.equals("E")) //zapamietaj gdzie start
							start = board[i][j];
						else if(currentChar.equals("X")) // zapamietaj gdzie koniec
							end = board[i][j];
	  	    	  	}
	            }
            }
        } catch(Exception e){
        	throw new InputException();
        }
        finally
        {
            if(reader != null)
            	reader.close();
        }	
	}
	
	protected MazeCell[][] board;
	protected MazeCell start = null;
	protected MazeCell end = null;;
}
