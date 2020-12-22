/* ------------------------------------------------
 * Assignment 4
 * Written by: Diana Alexandra Merlusca (40169125)
 * For COMP 248 (Section P)
 * ------------------------------------------------
 */
import java.util.*;
public class Game {

	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		
		// initializing variables
		int i;
		int j;
		final int MAXSHIP = 6;
		final int MAXGRENADE = 4;
		int index1; // index corresponding to the letter
		int index2; // index corresponding to the number
		int index1Computer;
		int index2Computer;
		int hShipCounter = 0;
		int cShipCounter = 0;
		String coord;
		String winner;

		// Initializing an array of objects
		Coordinates[][] battleGrid = new Coordinates[8][8];
		
		// Initializing an array of strings
		String[][] displayGrid = new String[8][8];

		// This is the main set up of the simulator, where each slot is empty
		// and untouched.
		for(i = 0; i < 8; i++) {
			for(j = 0; j < 8 ; j++) {
				battleGrid[i][j] = new Coordinates();
			}
		}
		
		System.out.println("Hi! Let's play battleship!");
		
		// Prompt the user to enter their ship coordinates
		for(i=1; i<=MAXSHIP; i++) {
			System.out.print("Enter the coordinates of your ship #" + i + ": ");
			coord = kb.next();
			coord = coord.toLowerCase();
			index1 = coord.charAt(0)-97;
			index2 = Integer.parseInt(coord.substring(1))-1;
			if((index1 < 0 || index1 > 7) || (index2 < 0 || index2 > 7)) {
				System.out.println("Sorry, coordinates out of bounds, try again.");
				i--;
			} else if(battleGrid[index1][index2].getType() != null) {
				System.out.println("Sorry, coordinate already used, try again.");
				i--;
			} else {
				battleGrid[index1][index2].setType("ship");
				battleGrid[index1][index2].setOwner("human");
			}
		}
		
		// Prompt the user to enter their grenade coordinates
		for(i=1; i<=MAXGRENADE; i++) {
			System.out.print("Enter the coordinates of your grenade #" + i + ": ");
			coord = kb.next();
			coord = coord.toLowerCase();
			index1 = coord.charAt(0)-97;
			index2 = Integer.parseInt(coord.substring(1))-1;
			if((index1 < 0 || index1 > 7) || (index2 < 0 || index2 > 7)) {
				System.out.println("Sorry, coordinates out of bounds, try again.");
				i--;
			} else if(battleGrid[index1][index2].getType() != null) {
				System.out.println("Sorry, coordinate already used, try again.");
				i--;
			} else {
				battleGrid[index1][index2].setType("grenade");
				battleGrid[index1][index2].setOwner("human");
			}
		}
		

		// Getting the computer's ship coordinates
		//System.out.println("Comp ships: ");
		for(i=1; i<=MAXSHIP; i++) {
			index1Computer = (int) (Math.random()*8);
			index2Computer = (int) (Math.random()*8);
			if(battleGrid[index1Computer][index2Computer].getType() == null){
				battleGrid[index1Computer][index2Computer].setType("ship");
				battleGrid[index1Computer][index2Computer].setOwner("computer");
				//System.out.println(index1Computer + "\t" + index2Computer);
			} else i--;
			
		}
		// Getting the computer's grenade coordinates
		//System.out.println("Comp grenades: ");
		for(i=1; i<=MAXGRENADE; i++) {
			index1Computer = (int) (Math.random()*8);
			index2Computer = (int) (Math.random()*8);
			if(battleGrid[index1Computer][index2Computer].getType() == null){
				battleGrid[index1Computer][index2Computer].setType("grenade");
				battleGrid[index1Computer][index2Computer].setOwner("computer");
				//System.out.println(index1Computer + "\t" + index2Computer);
			} else i--;
			
		}
		System.out.println("OK, the computer placed it's ships and grenades at random. Let's play!");

		// Displaying the Battleship Grid
		for(i = 0; i < 8; i++) {
			for(j = 0; j < 8 ; j++) {
				displayGrid[j][i] = "_";
				System.out.print(displayGrid[j][i] + "\t");
				//updating the counters
				if (battleGrid[j][i].getType() == "ship" && battleGrid[j][i].getOwner() == "human") {
					hShipCounter++;
				} else if (battleGrid[j][i].getType() == "ship" && battleGrid[j][i].getOwner() == "computer") {
					cShipCounter++;
				}

			}
			System.out.println();
		}

		// Creating Players
		Player human = new Player("human");
		Player computer = new Player("computer");
		// Starting the battle

		do {
			int[] pos;
			// --------------------HUMAN'S TURN--------------------
			
			if(!human.isSkipNextTurn()) {
				pos = human.choosePosition();
				//if human hit ship
				if(battleGrid[pos[0]][pos[1]].getCalled() == true) {
					System.out.println("This position has already been called, nothing happens.");
					for(i = 0; i < 8; i++) {
						for(j = 0; j < 8 ; j++) {
							System.out.print(displayGrid[j][i] + "\t");
						}
						System.out.println();
					}
				} else {
					if(battleGrid[pos[0]][pos[1]].getType() == "ship" && 
							battleGrid[pos[0]][pos[1]].getOwner() == "computer") { //hit computer ship
						System.out.println("Aye Aye captain! You sunk a ship!");
						cShipCounter--;
						displayGrid[pos[0]][pos[1]] = "S";
						battleGrid[pos[0]][pos[1]].setCalled(true);
						for(i = 0; i < 8; i++) {
							for(j = 0; j < 8 ; j++) {
								System.out.print(displayGrid[j][i] + "\t");
							}
							System.out.println();
						}
					} else if(battleGrid[pos[0]][pos[1]].getType() == "ship" && 
							battleGrid[pos[0]][pos[1]].getOwner() == "human") { //hit user ship
						System.out.println("Oh no! Dumbo you sunk your own ship :(");
						hShipCounter--;
						battleGrid[pos[0]][pos[1]].setCalled(true);
						displayGrid[pos[0]][pos[1]] = "s";
						for(i = 0; i < 8; i++) {
							for(j = 0; j < 8 ; j++) {
								System.out.print(displayGrid[j][i] + "\t");
							}
							System.out.println();
						}
					}
					//if human hit grenade
					if(battleGrid[pos[0]][pos[1]].getType() == "grenade" &&
							battleGrid[pos[0]][pos[1]].getOwner() == "computer") { // hit computer grenade
						System.out.println("BOOM! You hit a grenade! You lose a turn :((");
						human.setSkipNextTurn(true);
						battleGrid[pos[0]][pos[1]].setCalled(true);
						displayGrid[pos[0]][pos[1]] = "G";
						for(i = 0; i < 8; i++) {
							for(j = 0; j < 8 ; j++) {
								System.out.print(displayGrid[j][i] + "\t");
							}
							System.out.println();
						}
					} else if (battleGrid[pos[0]][pos[1]].getType() == "grenade" &&
							battleGrid[pos[0]][pos[1]].getOwner() == "human") { // hit human grenade
						System.out.println("BOOM! You hit a grenade! You lose a turn :((");
						human.setSkipNextTurn(true);
						battleGrid[pos[0]][pos[1]].setCalled(true);
						displayGrid[pos[0]][pos[1]] = "g";
						for(i = 0; i < 8; i++) {
							for(j = 0; j < 8 ; j++) {
								System.out.print(displayGrid[j][i] + "\t");
							}
							System.out.println();
						}
					}
					//if human hit nothing
					if(battleGrid[pos[0]][pos[1]].getType() == null) {
						System.out.println("Fell right into the sea! Nothing happens");
						battleGrid[pos[0]][pos[1]].setCalled(true);
						displayGrid[pos[0]][pos[1]] = "*";
						for(i = 0; i < 8; i++) {
							for(j = 0; j < 8 ; j++) {
								System.out.print(displayGrid[j][i] + "\t");
							}
							System.out.println();
						}
						
					}
				}
			} else human.setSkipNextTurn(false);
			
			// --------------------COMPUTER'S TURN--------------------
			
			if(!computer.isSkipNextTurn() && hShipCounter != 0 && cShipCounter != 0) {
				pos = computer.choosePosition();
				// if position was already called
				if(battleGrid[pos[0]][pos[1]].getCalled() == true) {
					System.out.println("This position has already been called, nothing happens.");
					for(i = 0; i < 8; i++) {
						for(j = 0; j < 8 ; j++) {
							System.out.print(displayGrid[j][i] + "\t");
						}
						System.out.println();
					}
				} else {
					// if computer hit ship
					if(battleGrid[pos[0]][pos[1]].getType() == "ship" && 
							battleGrid[pos[0]][pos[1]].getOwner() == "computer") { //hit computer ship
						System.out.println("Oh no! Dumbo you sunk your own ship :(");
						cShipCounter--;
						battleGrid[pos[0]][pos[1]].setCalled(true);
						displayGrid[pos[0]][pos[1]] = "S";
						for(i = 0; i < 8; i++) {
							for(j = 0; j < 8 ; j++) {
								System.out.print(displayGrid[j][i] + "\t");
							}
							System.out.println();
						}
					} else if(battleGrid[pos[0]][pos[1]].getType() == "ship" && 
							battleGrid[pos[0]][pos[1]].getOwner() == "human") { //hit user ship
						System.out.println("Aye Aye captain! You sunk a ship!");
						hShipCounter--;
						battleGrid[pos[0]][pos[1]].setCalled(true);
						displayGrid[pos[0]][pos[1]] = "s";
						for(i = 0; i < 8; i++) {
							for(j = 0; j < 8 ; j++) {
								System.out.print(displayGrid[j][i] + "\t");
							}
							System.out.println();
						}
					}
					//if computer hit grenade
					if(battleGrid[pos[0]][pos[1]].getType() == "grenade" &&
							battleGrid[pos[0]][pos[1]].getOwner() == "computer") { // hit computer grenade
						System.out.println("BOOM! You hit a grenade! You lose a turn :((");
						computer.setSkipNextTurn(true);
						battleGrid[pos[0]][pos[1]].setCalled(true);
						displayGrid[pos[0]][pos[1]] = "G";
						for(i = 0; i < 8; i++) {
							for(j = 0; j < 8 ; j++) {
								System.out.print(displayGrid[j][i] + "\t");
							}
							System.out.println();
						}
					} else if (battleGrid[pos[0]][pos[1]].getType() == "grenade" &&
							battleGrid[pos[0]][pos[1]].getOwner() == "human") { // hit human grenade
						System.out.println("BOOM! You hit a grenade! You lose a turn :((");
						computer.setSkipNextTurn(true);
						battleGrid[pos[0]][pos[1]].setCalled(true);
						displayGrid[pos[0]][pos[1]] = "g";
						for(i = 0; i < 8; i++) {
							for(j = 0; j < 8 ; j++) {
								System.out.print(displayGrid[j][i] + "\t");
							}
							System.out.println();
						}
					}
					//if computer hit nothing
					if(battleGrid[pos[0]][pos[1]].getType() == null) {
						System.out.println("Fell right into the sea! Nothing happens");
						battleGrid[pos[0]][pos[1]].setCalled(true);
						displayGrid[pos[0]][pos[1]] = "*";
						for(i = 0; i < 8; i++) {
							for(j = 0; j < 8 ; j++) {
								System.out.print(displayGrid[j][i] + "\t");
							}
							System.out.println();
						}
						
					}
				}
			}else computer.setSkipNextTurn(false);
		}while(hShipCounter != 0 && cShipCounter != 0);

		
		// game over!!
		if(hShipCounter == 0) winner = "The computer is the winner!";
		else winner = "You are the winner!";
		
		System.out.println("After a difficult, but fair game...");
		System.out.println(winner);
		
		// Displaying the results of the game
		for(i = 0; i < 8; i++) {
			for(j = 0; j < 8 ; j++) {
				if(battleGrid[j][i].getType() == null) {
					System.out.print("_\t");
				} else if (battleGrid[j][i].getType() == "ship") {
					if(battleGrid[j][i].getOwner() == "human") {
						System.out.print("s\t");
					} else System.out.print("S\t");
				} else {
					if(battleGrid[j][i].getOwner() == "human") {
						System.out.print("g\t");
					} else System.out.print("G\t");
				}
			}
			System.out.println();
		}
	}
}