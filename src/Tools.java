/* Marlin Jayasekera - 40033721
 * COMP 249
 * Assignment 1
 * February 1st, 2017
 */

import java.util.Scanner;
import java.util.Arrays;

public class Tools {
	
	static Scanner input = new Scanner(System.in);

	private static int hShips = 0;
	private static int hGrenades = 0;
	private static int cShips = 0;
	private static int cGrenades = 0;
	private static boolean g = true;
	private static boolean compG = true;
	private static int turnSkip = 0;
	private static Board bShown = new Board();
	private static Board bHidden = new Board();
	private static String[] compP = new String[10];
	
	/**
	 * Method to place the initial pieces to start the game
	 * 
	 * Asks the player positions to place 6 ships and 4 grenades on the Hidden board
	 * 
	 * @return Hidden Board
	 */
	public static Board startGame(){
		
		while(hShips < 6){
			
			System.out.print("Enter the coordinates of your ship: ");
			String s = input.nextLine();
			
			String p = s.toUpperCase();
			
			if(outOfBounds(p)){
				System.out.println("Invalid Input");
				continue;
			}
			else{
				if(bHidden.getBoardValue(p) == '-'){
					bHidden.setBoard(p, 's');
					hShips++;
				}
				else
					System.out.println("Position Taken");
			}
		}
		
		while(hGrenades < 4){
			
			System.out.print("Enter the coordinates of your grenade: ");
			String s = input.nextLine();
			
			String p = s.toUpperCase();
			
			if(outOfBounds(p)){
				System.out.println("Invalid Input");
				continue;
			}
			else{
				if(bHidden.getBoardValue(p) == '-'){
					bHidden.setBoard(p, 'g');
					hGrenades++;
				}
				else
					System.out.println("Position Taken");
			}
		}
		
		while(cShips < 6){
			
			int i = (int) (Math.random() * 8);
			int j = (int) (Math.random() * 8);

			if(outOfBounds(i, j) || !(bHidden.getBoardValue(i, j) == '-')){
				continue;
			}
			else{
				bHidden.setBoardValue(i, j, 'S');
				cShips++;
			}	
		}
		
		while(cGrenades < 4){
			
			int i = (int) (Math.random() * 8);
			int j = (int) (Math.random() * 8);

			if(outOfBounds(i, j) || !(bHidden.getBoardValue(i, j) == '-')){
				continue;
			}
			else{
				bHidden.setBoardValue(i, j, 'G');
				cGrenades++;
			}	
		}
		
		System.out.println(bHidden);
		return bHidden;
	}
	
	/**
	 * Method to check if the position asked exists on the board or if its out of bounds
	 * 
	 * @param p Position in letter + number form
	 * @return Boolean whether it is or isn't out of bounds
	 */
	public static boolean outOfBounds(String p){
		
		if(p.length() != 2){
			return false;
		}
		else{
			int i = (int) p.charAt(0) - 65;
			int j = (int) p.charAt(1) - 49;
			
			return !(i < 8 && j < 8);
		}
		
		
	}
	
	/**
	 * 
	 * Method to check if the index asked exists on the board or if its out of bounds
	 * 
	 * @param i Index of Row	
	 * @param j Index of Column
	 * @return Boolean whether it is or isn't out of bounds
	 */
	public static boolean outOfBounds(int i, int j){
		
		return !(i < 8 && j < 8);
	}
	
	/**
	 * Method for the player to shoot a rocket at given position
	 * 
	 * If the player hit a grenade on his previous turn, his turn will be skipped
	 * 
	 * @param p Position in letter + number form
	 */
	public static void shoot(String p){
		
		if(g){
			
			char a = p.charAt(0);
			char b = p.charAt(1);
			int i;
			int j;
			
			
			if(p.length() == 2 && (int) a > 64 && (int) a < 73 && 
			(int) b > 48 && (int) b < 57){
				
				i = (int) a - 65; // (int) 'A' is 65 so the index would be 0 - 8
				j = (int) b - 49; // (int) '1' is 49 so the index be 0 - 8
				
				switch (bHidden.getBoardValue(i, j)){
					case 'S':
						bShown.setBoardValue(i, j, bHidden.getBoardValue(i, j));
						cShips--;
						System.out.println(bShown);
						break;
					case 'G':
						bShown.setBoardValue(i, j, bHidden.getBoardValue(i, j));
						cGrenades--;
						turnSkip++;
						System.out.println(bShown);
						g = false;
						break;
					case '-':
						bShown.setBoardValue(i, j, '*');
						System.out.println(bShown);
						break;
					default:
						System.out.println("Errors have occured.\nWe won't tell you where or why.\nLazy programmers.");
				}
						
			}
			else{
				
				System.out.println("Three things are certain:\nDeath, taxes and wrong data\nGuess which has occured");
				
			}
			
		}
		
		else{
			System.out.println("Jokes on you, Turn Skipped");
			g = true;
		}
		
	}
	
	/**
	 * Method for the computer to shoot a rocket at a random position within the board
	 * that isn't where it placed its pieces
	 * 
	 * If the computer hit a grenade on its previous turn, its turn will be skipped 
	 * 
	 */
	public static void compShoot(){
		
		if(compG){
			
			int i = (int) (Math.random() * 8);
			int j = (int) (Math.random() * 8);
			char a = (char) (i + 65);
			String p = Character.toString(a) + j;
			
			if(Arrays.asList(compP).contains(p) || bShown.getBoardValue(i, j) != '-'){
				battle();
			}
			else{
				
				switch (bHidden.getBoardValue(i, j)){
				
				case 's':
					bShown.setBoardValue(i, j, bHidden.getBoardValue(i, j));
					hShips--;
					System.out.println(bShown);
					break;
				case 'g':
					bShown.setBoardValue(i, j, bHidden.getBoardValue(i, j));
					hGrenades--;
					turnSkip++;
					System.out.println(bShown);
					compG = false;
					break;
				case '-':
					bShown.setBoardValue(i, j, '*');
					System.out.println(bShown);
					break;
				default:
					System.out.println("Errors have occured.\nWe won't tell you where or why.\nLazy programmers.");
			}
				
			}
		}
		
		else{
			System.out.println("My turn has been skipped\nDo not worry, I'll be back\nTo Annihilate");
			compG = true;
		}
	
	}
	
	/**
	 * Method that calls for the player and the computer to each shoot their rockets 
	 * on their given turns.
	 * 
	 * Announces whether the player wins or loses
	 */
	public static void battle(){

		while(hShips > 0 && cShips > 0){
						
			System.out.print("General, Enter the coordinates to shoot your rocket: ");
			String p = input.nextLine();
			String pH = p.toUpperCase();
			if(outOfBounds(pH)){
				System.out.println("Invalid Input");
				continue;
			}
			System.out.println();
			shoot(pH);
			compShoot();
			System.out.println(hShips + " h\nc " + cShips);
			
		}
		
		System.out.println("Turns Skipped: " + turnSkip);
		
		if(cShips == 0){
			System.out.println("Congratulations!\nThe victor here is not me.\nYou have won, this time.");
		}
		else
			System.out.println("It's unfortunate.\nYou were not able to win.\nYou are a loser.");
		
	}
	
	/**
	 * Method to play Battleship
	 */
	public static void play(){
		startGame();
		battle();		
	}

	

}
