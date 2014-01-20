package logic;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.activity.InvalidActivityException;

import logic.MazeCell.State;

public class PathFinder {
	
	protected MazeBoard board;
	protected Queue<MazeCell> possibleMovement;
	protected Stack<String> log;
	
	public PathFinder(MazeBoard board)
	{
		this.board = board;
		possibleMovement = new LinkedList<MazeCell>();
		
		MazeCell temp = board.getStart();
		try {
			temp.setState(State.VISITED);
		} catch (InvalidActivityException e) {
			e.printStackTrace();
		}
		
		possibleMovement.add(temp);
	}
	
	public MazeBoard findPath(String resultLogFilePath) throws PathNotFoundException, InvalidActivityException, ArrayIndexOutOfBoundsException //zwraca tablice z zaznaczonym sladem
	{
		while(!possibleMovement.isEmpty())
		{
		
			MazeCell currentCell = possibleMovement.poll(); // wyciagniecie pierwszego elementu w kolejce, czyli punktu startowego
			
			int currentI = currentCell.getI();
			int currentJ = currentCell.getJ();
			
			if(currentCell.equals(board.getEnd())) {
			      // cofamy się do punktu Startowego, wymazując kroki (na podstawie zapisanego stanu ruchu), 
				  // np. State.LEFT - wracamy w przeciwnym kierunku czyli w prawo, stad currentJ++ itp
				log = new Stack<>();
				
				while(!currentCell.equals(board.getStart())) {
					  
					switch(currentCell.getState()) {
						case LEFT: 
							board.setState(currentI, currentJ, State.VISITED);
							log.push("Left");
							currentJ++; 
							break;
						case UP: 
							board.setState(currentI, currentJ, State.VISITED); 
							log.push("Up");
							currentI++; 
							break;
						case RIGHT: 
							board.setState(currentI, currentJ, State.VISITED);
							log.push("Right");
							currentJ--;
							break;
						case DOWN: 
							board.setState(currentI, currentJ, State.VISITED);
							log.push("Down");
							currentI--; 
							break;
						default: 
							break;
					}
					
					currentCell = board.get(currentI, currentJ);
				}
				
				saveLogToFile(resultLogFilePath);
				break;                // Koniec algorytmu
			}
				
				  // szukamy wszystkich dróg wyjścia z bieżącej pozycji. 
				  // do kolejki dodajemy możliwe drogi w labiryncie (oznaczone odpowiednim kierunkiem ruchu)

			if((currentJ > 0) && (board.getState(currentI, currentJ - 1) == State.NONE) && (!board.isWall(currentI, currentJ - 1)) ) {
				board.setState(currentI, currentJ - 1, State.LEFT); // ślad przejścia
				possibleMovement.add(board.get(currentI, currentJ - 1));// współrzędne do kolejki
			}
			  
			if((currentI > 0) && (board.getState(currentI - 1, currentJ) == State.NONE)  && (!board.isWall(currentI - 1, currentJ)) ) {
				board.setState(currentI - 1, currentJ, State.UP); // ślad przejścia
				possibleMovement.add(board.get(currentI - 1, currentJ));// współrzędne do kolejki
			}
			  
			if((currentJ < board.getWidth()) && (board.getState(currentI, currentJ + 1) == State.NONE)  && (!board.isWall(currentI, currentJ + 1))) {
				board.setState(currentI, currentJ + 1, State.RIGHT); // ślad przejścia
				possibleMovement.add(board.get(currentI, currentJ + 1));// współrzędne do kolejki
			}
			  
			if((currentI < board.getHeight()) && (board.getState(currentI + 1, currentJ) == State.NONE)  && (!board.isWall(currentI + 1, currentJ))) {
				board.setState(currentI + 1, currentJ, State.DOWN); // ślad przejścia
				possibleMovement.add(board.get(currentI + 1, currentJ));// współrzędne do kolejki
			}
		}
		
		if(board.getEnd().getState() == State.VISITED)
			return this.board;
		else
			throw new PathNotFoundException();
	}

	private void saveLogToFile(String resultLogFilePath) {
		Path file = Paths.get(resultLogFilePath);
        BufferedWriter writer = null;
        try {
            writer = Files.newBufferedWriter(file, Charset.forName("UTF-8"));
            
            while(!log.isEmpty()) 
                writer.write(log.pop() + "\n");
                
        } catch(IOException e) {
        	e.printStackTrace();
        	
        } finally {
        	try{
        		if(writer != null) 
        			writer.close();
        	} catch(IOException e){
        		e.printStackTrace();
        	}
        }
    }
}
