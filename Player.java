import java.util.*;

public class Player {
Scanner kb = new Scanner(System.in);

	// Couldn't figure out how to use enum so yee lmao
	private String playerType;
	private boolean skipNextTurn;

// Constructor
public Player(String playerType){
	this.playerType = playerType;
	this.skipNextTurn = false;
}


public String getPlayerType() {
	return playerType;
}



public void setPlayerType(String playerType) {
	this.playerType = playerType;
}


public boolean isSkipNextTurn() {
	return skipNextTurn;
}


public void setSkipNextTurn(boolean skipNextTurn) {
	this.skipNextTurn = skipNextTurn;
}


public int[] choosePosition(){
	int[] position = new int[2];
	int index1;
	int index2;
	char c;
	String coord;
		if(playerType == "human") { // prompts human to pick a position

			System.out.print("Position your rocket: ");
			coord = kb.next();
			coord = coord.toLowerCase();
			index1 = coord.charAt(0)-97;
			index2 = Integer.parseInt(coord.substring(1))-1;
			
			// checking if coordinate out of bounds
			while((index1 < 0 || index1 > 7) || (index2 < 0 || index2 > 7)){
				System.out.print("Sorry, your rocket is out of bounds, try again: ");
				coord = kb.next();
				coord = coord.toLowerCase();
				index1 = coord.charAt(0)-97;
				index2 = Integer.parseInt(coord.substring(1))-1;
			}
			position[0] = index1;
			position[1] = index2;

		
		} else { // Auto-generates a computer position

			index1 = (int) (Math.random()*8);
			index2 = (int) (Math.random()*8);
			c = (char)(index1+65);
			coord = c + String.valueOf(index2 + 1);
			System.out.println("Position of my rocket: " + coord);
			position[0] = index1;
			position[1] = index2;

		}
	return position;
 }
}
