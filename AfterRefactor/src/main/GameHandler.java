package main;

import interfaces.Utility;
import person.Enemy;
import person.Person;
import person.Player;
import povemon.Povemon;

public class GameHandler {
	private static GameUI ui;
	private Povemon playerCurrentPovemon;
	private Povemon enemyCurrentPovemon;
	private static Player player;
	
	
	public boolean promptForNextBattle() {
	    while (true) {
	        System.out.print(" Do you want to start another battle? [Y/N]: ");
	        String response = Utility.scan.nextLine().toLowerCase();
	        if (response.equals("y")) return true;
	        if (response.equals("n")) return false;
	        System.out.println(" Invalid input. Please enter Y or N.");
	    }
	}
	
	public boolean handlePlayerAction(String action, Player player, Enemy enemy) {
	    switch (action) {
	        case "1":
	        	Utility.clearScreen();
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
	            Utility.pressEnter();
	            break;
	    }
	    return false;
	}
	
	public void fight() {
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
	
	public void enterBattle(Player player) {
	    boolean exitGame = false;
	    while (!exitGame) {
	    	Utility.clearScreen();
	        Enemy enemy = Enemy.createEnemy(player);
	        player.resetTeam();
	        enemyCurrentPovemon = enemy.getTeam().firstElement();
	        playerCurrentPovemon = player.getTeam().firstElement();
	        boolean exitBattle = false;
	        boolean firstTurn = true;
	        while (!exitBattle) {
	        	ui.displayBattleScreen(player, enemy, playerCurrentPovemon, enemyCurrentPovemon);
	            if (firstTurn) {handleFirstTurn(player, enemy);firstTurn = false;}
	            String winner = checkWinner();
	            if (processBattleOutcome(player, enemy, winner)) {exitBattle = true;break;}
	            String action = getPlayerAction();
	            exitBattle = handlePlayerAction(action, player, enemy);
	        }
	        exitGame = !promptForNextBattle();
	    }
	}
	
	public void handleFirstTurn(Player player, Enemy enemy) {
	    String playerLine = player.voiceLines(playerCurrentPovemon);
	    String enemyLine = enemy.voiceLines(player);

	    if (!playerLine.isEmpty()) {
	        System.out.printf(" %s: \"%s\"\n", enemy.getName(), enemyLine);
	        System.out.printf(" %s: \"%s\"\n", player.getName(), playerLine);
	        System.out.println();
	    }
	}
	
	public boolean processBattleOutcome(Player player, Enemy enemy, String winner) {
	    if (winner.equals("none")) return false;

	    if (winner.equals("player")) {
	        battleConclusion(player, enemy);
	    } 
	    else {
	        battleConclusion(enemy, player);
	    }
	    Utility.pressEnter();
	    return true;
	}
	
	public void modifyTeam() {
        while (true) {
            player.seeAllPovemon();
            ui.showTeamModificationMenu();
            String choice = Utility.scan.nextLine();
            if (choice.equals("1")) {
                player.insertPovemon();
            } else if (choice.equals("2")) {
                break;
            } else {
                Utility.showError("Invalid Choice!");
            }
            Utility.pressEnter();
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
	
	public String checkWinner() {
		if(!playerCurrentPovemon.getIsAlive() && 
				enemyCurrentPovemon.getIsAlive()) return "enemy";
		else if (!enemyCurrentPovemon.getIsAlive() &&
				playerCurrentPovemon.getIsAlive()) return "player";
		else return "none";
	}
	
	
	public String getPlayerAction() {
	    if (!playerCurrentPovemon.getIsAlive()) return "3";
	    System.out.print(" Choose an action [1-3]: ");
	    return Utility.scan.nextLine();
	}
}
