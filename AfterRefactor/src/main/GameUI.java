package main;

import java.util.Map;
import java.util.Vector;

import interfaces.Utility;
import person.Enemy;
import person.Player;
import povemon.Povemon;

public class GameUI {
	private static final int BorderLength = 70;
	
	public void printLogo() {
		System.out.println("\r\n"
				+ " ██████   ██████  ██    ██ ███████ ███    ███  ██████  ███    ██ \r\n"
				+ " ██   ██ ██    ██ ██    ██ ██      ████  ████ ██    ██ ████   ██ \r\n"
				+ " ██████  ██    ██ ██    ██ █████   ██ ████ ██ ██    ██ ██ ██  ██ \r\n"
				+ " ██      ██    ██  ██  ██  ██      ██  ██  ██ ██    ██ ██  ██ ██ \r\n"
				+ " ██       ██████    ████   ███████ ██      ██  ██████  ██   ████ \r\n");
	}
	
    public void printBorder() {
        Utility.printBorder(BorderLength);
    }
   
    public void chooseStarter(Vector<Povemon> playerTeam, Vector<Povemon> playerPovemonList) {
        Map<String, String> starterOptions = Map.of(
            "1", "CharbolT",
            "2", "AqualaSh",
            "3", "TreethIng"
        );
        while (true) {
            printStarterMenu();
            System.out.print("\n Which Povémon will you choose?[1,2,3]: ");
            String choose = Utility.scan.nextLine();
            String selected = starterOptions.get(choose);
            if (selected == null) {
                System.out.println(" Invalid Choice!");
                Utility.pressEnter();
                continue;
            }
            Utility.clearScreen();
            Povemon starter = Povemon.createPovemon(selected);
            System.out.println("\n You chose " + starter.getName() + " as your starter!");
            playerTeam.add(starter);
            System.out.println(" Take good care of it, and embark on your adventure!");
            Utility.pressEnter();
            break;
        }
    }

    private void printStarterMenu() {
        printBorder();
        System.out.println("        Choose Your Starter!         ");
        printBorder();
        System.out.println(" 1. CharbolT - The Fire Povémon");
        System.out.println("    -> A brave and fiery Povémon, always ready for a challenge.");
        System.out.println(" 2. AqualaSh - The Water Povémon");
        System.out.println("    -> A cool-headed Povémon that flows like water in battle.");
        System.out.println(" 3. TreethIng - The Grass Povémon");
        System.out.println("    -> A calm and nurturing Povémon, in tune with nature.");
    }

	public String greetingsMenu_Introduction() {
		while(true) {
			System.out.println(" ------------------------------- ");
			System.out.println(" | Welcome to Povémon World!   | ");
			System.out.println(" ------------------------------- ");
	        System.out.print("\n Please enter your name: ");
	        String playerName = Utility.scan.nextLine();
	        if (confirmName(playerName)) {
	        	Utility.clearScreen();
                return playerName;
            }
		}
	}
	
	public boolean confirmName(String playerName) {
        while (true) {
        	Utility.clearScreen();
            System.out.println(" Is your name " + playerName + "?");
            System.out.print(" [Y/N]: ");
            String confirm = Utility.scan.nextLine().toLowerCase();
            if (confirm.equals("y")) return true;
            if (confirm.equals("n")) return false;
            System.out.println(" Invalid input. Please enter Y or N.");
        }
    }
	
	
	public void mainMenu(String playerName) {
		printLogo();
		Utility.printBorder(65);
		System.out.println(" Welcome, " + playerName + "! Let's start your adventure...\n");
		System.out.println(" 1. Enter Battle");
		System.out.println(" 2. Enter Shop");
		System.out.println(" 3. Modify Povémon");
		System.out.println(" 4. Exit");
		System.out.print(" >> ");
	}
	
	public void printExit() {
		printLogo();
		System.out.println(" --------------------------------------------------------------- \r\n"
                	+ "                Thank You for Playing Povémon!               \r\n"
                	+ "    Your journey may end here, but the story lives on...        \r\n"
	                + " --------------------------------------------------------------- ");
	}
	
	public void showTeamModificationMenu() {
	    System.out.println("\n Options:");
	    System.out.println(" 1. Insert Pokémon to team");
	    System.out.println(" 2. Exit");
	    System.out.print(" >> ");
	}
	
	public static void displayBattleScreen(Player player, Enemy enemy, Povemon playerCurrentPovemon, Povemon enemyCurrentPovemon) {
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
		userChoicesScreen();
	}
	
	private static void userChoicesScreen() {
		System.out.println(" -------------------------+-----------------------------");
		System.out.println(" | What will you do?      | ");
		System.out.println(" | 1. Fight               | ");
		System.out.println(" | 2. Run                 | ");
		System.out.println(" | 3. Povémon             | ");
		System.out.println(" --------------------------");
	}
	
}
