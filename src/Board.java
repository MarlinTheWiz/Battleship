/* Marlin Jayasekera - 40033721
 * COMP 249
 * Assignment 1
 * February 1st, 2017
 */

/**
 * The board class defines the "canvas" of where the game BattleShip will be played.
 * It contains any and every value of the board itself
 * 
 * @author Marlin Jayasekera - 40033721
 *
 */

public class Board {
	
	private char[][] board = new char[8][8];
	
	/**
	 * 
	 */
	public Board(){
		
		
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				board[i][j] = '-';
			}
		}
	}
	
	public void setBoard(String p, char t){
		
		String x = p.toUpperCase();
		
		char a = x.charAt(0);
		char b = p.charAt(1);
		int i;
		int j;
		
		
		if(p.length() == 2 && (int) a > 64 && (int) a < 73 && 
		(int) b > 48 && (int) b < 57){
			
			i = (int) a - 65; // (int) 'A' is 65 so the index would be 0 - 8
			j = (int) b - 49; // (int) '1' is 49 so the index be 0 - 8
			
			if (board[i][j] == '-')
				board[i][j] = t;				
			else
				System.out.println("Coordinates already used! Try Again");
					
			}
		
		else
			System.out.println("Invalid input, Try Again");
		}
		
	
	public String toString(){
		
		String b = "";
		
		for(int i = 0; i < 8; i++){
			
			b += "\n\t";
			
			for(int j = 0; j < 8; j++){
				
				b += board[i][j];
				b += " ";
				
				
			}
		}
		
		return b;
		
	}
	
	public char[][] getBoard() {
		return board;
	}
	
	public char getBoardValue(int i, int j) {
		return board[i][j];
	}
	
	public char getBoardValue(String p) {
		
		String a = p.toUpperCase();
		
		int i = (int) a.charAt(0) - 65;
		int j = (int) p.charAt(1) - 49;
		return board[i][j];
	}
	
	public void setBoardValue(int i, int j, char c) {
		board[i][j] = c;
	}	

}

