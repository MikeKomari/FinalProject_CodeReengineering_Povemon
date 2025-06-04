package main;

import java.util.Vector;

import interfaces.AdditionalPovemonInfo;
import interfaces.Utility;
import other.Shop;
import person.Enemy;
import person.Person;
import person.Player;
import povemon.Povemon;

public class Main implements AdditionalPovemonInfo, Utility{
	
	private static Player player;
	private static Shop shop = new Shop("Shop");
	
	public static Povemon playerCurrentPovemon;
	public static Povemon enemyCurrentPovemon;
	
	private String greetingsMenu_Introduction() {
		String playerName = "";
		while(true) {
			System.out.println();
			System.out.println(" ------------------------------- ");
			System.out.println(" | Welcome to Povémon World!   | ");
			System.out.println(" ------------------------------- ");
	        
	        System.out.print("\n Please enter your name: ");
	        
	        playerName = scan.nextLine();
	        
	        String confirm = "";
	        
	        do {
	        	clearScreen();
		        System.out.println(" Is your name " + playerName + "?");
		        System.out.print(" [Y/N]: ");
		        confirm = scan.nextLine();
			} while (!confirm.equalsIgnoreCase("y") &&
					 !confirm.equalsIgnoreCase("n"));
	        
	        if(confirm.equalsIgnoreCase("y")) {
	        	clearScreen();
	        	break;
	        }
	        
	        clearScreen();
		}
		
		return playerName;
	}
	
	private void chooseStarter(Vector<Povemon> playerTeam, Vector<Povemon> playerPovemonList) {
		Povemon starter = null;
		String name = null;
		String type = null;
		Integer hp = null, defense = null, attack = null, speed = null;
		
		while(true) {
			printBorder(70);
	        System.out.println("        Choose Your Starter!         ");
	        printBorder(70);
	        
	        System.out.println(" 1. CharbolT - The Fire Povémon");
	        System.out.println("    -> A brave and fiery Povémon, always ready for a challenge.");
	        
	        System.out.println(" 2. AqualaSh - The Water Povémon");
	        System.out.println("    -> A cool-headed Povémon that flows like water in battle.");
	        
	        System.out.println(" 3. TreethIng - The Grass Povémon");
	        System.out.println("    -> A calm and nurturing Povémon, in tune with nature.");
	        
	        System.out.print("\n Which Povémon will you choose?[1,2,3]: ");
	        
	        String choose = scan.nextLine();
	        
	        boolean valid = true;
	        
	        String selected = "";
	        
	        if(choose.equals("1")) {
	        	selected = "CharbolT";
	        }
	        else if(choose.equals("2")) {
	        	selected = "AqualaSh";
	        }
	        else if(choose.equals("3")) {
	        	selected = "TreethIng";
	        }
	        else {
	        	System.out.println(" Invalid Choice!");
	        	valid = false;
	        } 
	        
	        if(valid) {
	        	clearScreen();
	        	starter = Povemon.createPovemon(selected);
	        	System.out.println("\n You chose " + starter.getName() + " as your starter!");
	        	playerTeam.add(starter);
	        	System.out.println(" Take good care of it, and embark on your adventure!");
	        }
	        
	        pressEnter();
	        
	        if(valid) break;
		}
	}
	
	private void greetingsMenu_Main() {
		Vector<Povemon> playerTeam = new Vector<Povemon>();
		Vector<Povemon> playerPovemonList = new Vector<Povemon>();
		String playerName = greetingsMenu_Introduction();
		chooseStarter(playerTeam, playerPovemonList);
		
		player = Player.createPlayer(playerName, playerTeam, playerPovemonList);
		
		// cheat code for money (testing)
		if(player.getName().equals("OV") || player.getName().equals("Octa")) {
			player.setMoney(9999);
		}
	}
	
	private void printLogo() {
		System.out.println("\r\n"
				+ " ██████   ██████  ██    ██ ███████ ███    ███  ██████  ███    ██ \r\n"
				+ " ██   ██ ██    ██ ██    ██ ██      ████  ████ ██    ██ ████   ██ \r\n"
				+ " ██████  ██    ██ ██    ██ █████   ██ ████ ██ ██    ██ ██ ██  ██ \r\n"
				+ " ██      ██    ██  ██  ██  ██      ██  ██  ██ ██    ██ ██  ██ ██ \r\n"
				+ " ██       ██████    ████   ███████ ██      ██  ██████  ██   ████ \r\n");
	}
	
	private void printExit() {
		printLogo();
		System.out.println(" --------------------------------------------------------------- \r\n"
                + "                Thank You for Playing Povémon!               \r\n"
                + "    Your journey may end here, but the story lives on...        \r\n"
                + " --------------------------------------------------------------- ");
	}
	private void mainMenu() {
		printLogo();
		printBorder(65);
		System.out.println(" Welcome, " + player.getName() + "! Let's start your adventure...\n");
		System.out.println(" 1. Enter Battle");
		System.out.println(" 2. Enter Shop");
		System.out.println(" 3. Modify Povémon");
		System.out.println(" 4. Exit");
		System.out.print(" >> ");
	}
	
	public static void pressEnter() {
		System.out.println("\n Press ENTER to continue...");
		scan.nextLine();
		clearScreen();
	}
	
	public static void clearScreen() {
		for (int i = 0; i < 50; i++) {
			System.out.println();
		}
	}
	
	public static void printBorder(int length) {
		System.out.print(" ");
		for (int i = 0; i < length; i++) {
			System.out.print("-");
		}
		System.out.println();
	}
	
	private void fight() {
		if(playerCurrentPovemon.getSpeed() >= enemyCurrentPovemon.getSpeed()) {
			playerCurrentPovemon.attack(enemyCurrentPovemon);
			
			if(enemyCurrentPovemon.getIsAlive()) {
				enemyCurrentPovemon.attack(playerCurrentPovemon);							
			}
		}
		else {
			enemyCurrentPovemon.attack(playerCurrentPovemon);	
			
			if(playerCurrentPovemon.getIsAlive()) {
				playerCurrentPovemon.attack(enemyCurrentPovemon);
			}
		}
	}
	
	private String checkWinner() {
		if(!playerCurrentPovemon.getIsAlive() && 
				enemyCurrentPovemon.getIsAlive()) return "enemy";
		else if (!enemyCurrentPovemon.getIsAlive() &&
				playerCurrentPovemon.getIsAlive()) return "player";
		else return "none";
	}

	private void enterBattle() {
	    boolean exitGame = false;

	    while (!exitGame) {
	    	clearScreen();
	        Enemy enemy = Enemy.createEnemy(player);
	        player.resetTeam();
	        
	        enemyCurrentPovemon = enemy.getTeam().firstElement();
	        playerCurrentPovemon = player.getTeam().firstElement();

	        boolean exitBattle = false;
	        boolean firstTurn = true;

	        while (!exitBattle) {
	            displayBattleScreen(player, enemy);

	            if (firstTurn) {
	                handleFirstTurn(player, enemy);
	                firstTurn = false;
	            }

	            String winner = checkWinner();
	            if (processBattleOutcome(player, enemy, winner)) {
	                exitBattle = true;
	                break;
	            }

	            String action = getPlayerAction();
	            exitBattle = handlePlayerAction(action, player, enemy);
	        }
	        exitGame = !promptForNextBattle();
	    }
	}

	private void displayBattleScreen(Player player, Enemy enemy) {
		System.out.println("\n ------------------------ Battle -----------------------");
		System.out.printf(" |  Opponent: %-40s | \n", enemy.getName());
		System.out.printf(" |  %-50s | \n", enemyCurrentPovemon.getName() + " ["+ enemyCurrentPovemon.getType()+ "]");
		System.out.printf(" |  HP: %3d/%3d %38s | \n", enemyCurrentPovemon.getCurrHp(), enemyCurrentPovemon.getHp(), "");
		System.out.println(" |                       (•ᴗ•)                         | ");
		System.out.println(" |                                                     | ");
		System.out.println(" |                                                     | ");
		System.out.println(" |                      \\(O.O)/                        | ");
		System.out.printf(" |  Player  : %-40s | \n", player.getName());
		System.out.printf(" |  %-50s | \n", playerCurrentPovemon.getName() + " ["+ playerCurrentPovemon.getType()+ "]");
		System.out.printf(" |  HP: %3d/%3d %38s | \n", playerCurrentPovemon.getCurrHp(), playerCurrentPovemon.getHp(), "");
		System.out.println(" -------------------------+-----------------------------");
		System.out.println(" | What will you do?      | ");
		System.out.println(" | 1. Fight               | ");
		System.out.println(" | 2. Run                 | ");
		System.out.println(" | 3. Povémon             | ");
		System.out.println(" --------------------------");

	}

	private void handleFirstTurn(Player player, Enemy enemy) {
	    String playerLine = player.voiceLines(playerCurrentPovemon);
	    String enemyLine = enemy.voiceLines(player);

	    if (!playerLine.isEmpty()) {
	        System.out.printf(" %s: \"%s\"\n", enemy.getName(), enemyLine);
	        System.out.printf(" %s: \"%s\"\n", player.getName(), playerLine);
	        System.out.println();
	    }
	}

	private boolean processBattleOutcome(Player player, Enemy enemy, String winner) {
	    if (winner.equals("none")) return false;

	    if (winner.equals("player")) {
	        battleConclusion(player, enemy);
	    } 
	    else {
	        battleConclusion(enemy, player);
	    }

	    pressEnter();
	    return true;
	}


	private String getPlayerAction() {
	    if (!playerCurrentPovemon.getIsAlive()) return "3";

	    System.out.print(" Choose an action [1-3]: ");
	    return scan.nextLine();
	}

	private boolean handlePlayerAction(String action, Player player, Enemy enemy) {
	    switch (action) {
	        case "1":
	        	clearScreen();
	            fight();
	            
	            if(!playerCurrentPovemon.getIsAlive()) {
	            	if(player.leftAlive()) handlePlayerAction("3", player, enemy);
	            }
	            
	            if(!enemyCurrentPovemon.getIsAlive()) {
	            	Povemon nextPovemon = enemy.switchNextPovemon();
	            	if(nextPovemon != null) enemyCurrentPovemon = enemy.switchNextPovemon();
	            }

	            break;

	        case "2":
	            System.out.println(" You ran away...");
	            return true;

	        case "3":
	        	int index = player.swapPovemon(playerCurrentPovemon);
	        	if(index != -1) {
	        		playerCurrentPovemon = player.getTeam().get(index);
	        	}
	            break;

	        default:
	            System.out.println(" Invalid Choice!");
	            pressEnter();
	            break;
	    }
	    return false;
	}

	private boolean promptForNextBattle() {
	    while (true) {
	        System.out.print(" Do you want to start another battle? [Y/N]: ");
	        String response = scan.nextLine().toLowerCase();
	        if (response.equals("y")) return true;
	        if (response.equals("n")) return false;
	        System.out.println(" Invalid input. Please enter Y or N.");
	    }
	}

	
	private void modifyTeam() {
		while(true) {
	        player.seeAllPovemon();
	        
	        System.out.println("\n Options:");
	        System.out.println(" 1. Insert Povémon to team");
	        System.out.println(" 2. Exit");
	        System.out.print(" >> ");
	        
	        String choice = scan.nextLine();
	        
	        if(choice.equals("1")) {
	        	clearScreen();
	        	player.insertPovemon();
	        }
	        else if(choice.equals("2")) {
	        	clearScreen();
	        	break;
	        }
	        else {
	        	System.out.println(" Invalid Choice!");
	        }
	        pressEnter();
		}
	}
	
	public static void battleConclusion(Person winner, Person loser) {
	    System.out.printf(" %s won!\n", winner.getName());
	    System.out.printf(" %s: \"%s\"\n", loser.getName(), loser.voiceLines(false));
	    System.out.printf(" %s: \"%s\"\n", winner.getName(), winner.voiceLines(true));

	    int prizeMoney = (int) (loser.getMoney() * 0.25);
	    System.out.printf(" %s got %d poveDollars!\n", winner.getName(), prizeMoney);

	    winner.setMoney(winner.getMoney() + prizeMoney); 
	    loser.setMoney((int) (loser.getMoney() * 0.75));
	}
	
	public Main() {
		greetingsMenu_Main();
		
		boolean exit = false;
		while(!exit) {
			clearScreen();
			mainMenu();
			
			String input = scan.nextLine();
			
			switch (input) {
			case "1":
				clearScreen();
				enterBattle();
				break;
			case "2":
				clearScreen();
				shop.shopMenu(player);
				break;
			case "3":
				clearScreen();
				modifyTeam();
				break;
			case "4":
				exit = true;
				clearScreen();
				printExit();
				break;
			default:
				System.out.println(" Invalid Input!");
				pressEnter();
				break;
			}
			
		}
	}
	
	public static void main(String[] args) {
		new Main();
	}
}