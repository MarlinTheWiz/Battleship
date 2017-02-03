
import java.util.Scanner;
import java.util.Arrays;

public class Tools {
	
	static Scanner input = new Scanner(System.in);

	private static int hShips = 0;
	private static int hGrenades = 0;
	private static int cShips = 0;
	private static int cGrenades = 0;
	private static boolean g = true;
	private static int turnSkip = 0;
	private static Board bShown = new Board();
	private static Board bHidden = new Board();
	private static String[] compP = new String[10];
	
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

			if(outOfBounds(i, j) && !(bHidden.getBoardValue(i, j) == '-')){
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

			if(outOfBounds(i, j) && !(bHidden.getBoardValue(i, j) == '-')){
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
	
	public static boolean outOfBounds(String p){
		
		int i = (int) p.charAt(0) - 65;
		int j = (int) p.charAt(1) - 49;
		
		return !(i < 8 && j < 8);
	}
	
	public static boolean outOfBounds(int i, int j){
		
		return !(i < 8 && j < 8);
	}
	
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
				
					case 's':
						bShown.setBoardValue(i, j, bHidden.getBoardValue(i, j));
						hShips--;
						System.out.println(bShown);
						break;
					case 'g':
						bShown.setBoardValue(i, j, bHidden.getBoardValue(i, j));
						hGrenades--;
						turnSkip++;
						g = false;
						System.out.println(bShown);
						break;
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
			System.out.println("Turn Skipped");
			g = true;
		}
		
	}
	
	public static String compShoot(){
		
		int i = (int) (Math.random() * 8);
		int j = (int) (Math.random() * 8);
		char a = (char) (i + 65);
		String p = Character.toString(a) + j;
		
		if(Arrays.asList(compP).contains(p) || bShown.getBoardValue(i, j) != '-'){
			compShoot();
		}
		
		return p;
	
	}
	
	
	public static void play(){
		
		startGame();
		
		while(hShips > 0 || cShips > 0){
						
			System.out.print("General, Enter the coordinates to shoot your rocket: ");
			String p = input.nextLine();
			String pH = p.toUpperCase();
			System.out.println();
			shoot(pH);
			
			String pC = compShoot();
			shoot(pC);
			
		}
		
		System.out.println("Turns Skipped: " + turnSkip);
		
		if(cShips == 0){
			System.out.println("Congratulations!\nThe victor here is not me.\nYou have won, this time.");
		}
		else
			System.out.println("It's unfortunate.\nYou were not able to win.\nYou are a loser.");
		
	}

	

}
