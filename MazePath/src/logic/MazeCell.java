package logic;

import javax.activity.InvalidActivityException;

public class MazeCell {
	
	protected boolean isWall;

	protected State state = State.NONE;
	protected int i, j;
	
	public MazeCell(int i, int j, boolean isWall)	
	{
		this.isWall = isWall;
		this.i = i;
		this.j = j;
	}
	
	public int getI()
	{
		return i;
	}
	
	public int getJ()
	{
		return j;
	}
	
	public void setState(State direction) throws InvalidActivityException
	{
		if(isWall == true)
			throw new InvalidActivityException();
		
		this.state = direction;
	}
	
	public State getState()
	{
		return state;
	}
	
	public boolean isWall()	
	{
		return isWall;
	}
	
	public enum State 
	{
		LEFT, RIGHT, UP, DOWN, // poprzedni ruch (ktory zaprowadzil nas na dane pole)
		NONE, 		// brak stanu
		VISITED;	// pole odwiedzone
	}
}
