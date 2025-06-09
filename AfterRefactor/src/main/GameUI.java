package main;

import java.util.Map;
import java.util.Vector;

import interfaces.Utility;
import menu.BattleData;
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
        Map<String, String> starterOptions = getStarterOptions();

        while (true) {
            printStarterMenu();
            String choice = promptStarterChoice(starterOptions);
            if (choice == null) continue;

            handleStarterSelection(choice, playerTeam);
            break;
        }
    }

    private Map<String, String> getStarterOptions() {
        return Map.of(
            "1", "CharbolT",
            "2", "AqualaSh",
            "3", "TreethIng"
        );
    }

    private String promptStarterChoice(Map<String, String> options) {
        System.out.print("\n Which Povémon will you choose?[1,2,3]: ");
        String input = Utility.scan.nextLine();
        if (!options.containsKey(input)) {
            System.out.println(" Invalid Choice!");
            Utility.pressEnter();
            return null;
        }
        return options.get(input);
    }

    private void handleStarterSelection(String povemonName, Vector<Povemon> playerTeam) {
        Utility.clearScreen();
        Povemon starter = Povemon.createPovemon(povemonName);
        System.out.println("\n You chose " + starter.getName() + " as your starter!");
        playerTeam.add(starter);
        System.out.println(" Take good care of it, and embark on your adventure!");
        Utility.pressEnter();
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
	
	public static void displayBattleScreen(BattleData bd) {
		System.out.println("\n ------------------------ Battle -----------------------");
		System.out.printf(" |  Opponent: %-40s | \n", bd.getEnemy().getName());
		System.out.printf(" |  %-50s | \n", bd.getEnemyCurrentPovemon().getName() + " ["+ bd.getEnemyCurrentPovemon().getType()+ "]");
		System.out.printf(" |  HP: %3d/%3d %38s | \n", bd.getEnemyCurrentPovemon().getCurrHp(), bd.getEnemyCurrentPovemon().getHp(), "");
		bitsEmoji();
		System.out.printf(" |  Player  : %-40s | \n", bd.getPlayer().getName());
		System.out.printf(" |  %-50s | \n", bd.getPlayerCurrentPovemon().getName() + " ["+ bd.getPlayerCurrentPovemon().getType()+ "]");
		System.out.printf(" |  HP: %3d/%3d %38s | \n", bd.getPlayerCurrentPovemon().getCurrHp(), bd.getPlayerCurrentPovemon().getHp(), "");
		userChoicesScreen();
	}
	
	private static void bitsEmoji() {
		System.out.println(" |                       (•ᴗ•)                         | ");
		System.out.println(" |                                                     | ");
		System.out.println(" |                                                     | ");
		System.out.println(" |                      \\(O.O)/                        | ");
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
