package main;

import java.util.Map;

import actions.FightAction;
import actions.RunAction;
import actions.SwapAction;
import interfaces.BattleAction;
import interfaces.Utility;
import person.Enemy;
import person.Person;
import person.Player;
import povemon.Povemon;

public class GameHandler {
	private static GameUI ui = new GameUI();
	private Povemon playerCurrentPovemon;
	private Povemon enemyCurrentPovemon;
	private static Player player;
	
	private final Map<String, BattleAction> actionMap = Map.of(
		    "1", new FightAction(),
		    "2", new RunAction(),
		    "3", new SwapAction()
	);
	
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
		BattleAction selectedAction = actionMap.get(action);
	    
	    if (selectedAction != null) {
	        return selectedAction.execute(player, enemy, this);
	    } else {
	        System.out.println(" Invalid Choice!");
	        Utility.pressEnter();
	        return false;
	    }
	}
	
	public void fight() {
		if(getPlayerCurrentPovemon().getSpeed() >= getEnemyCurrentPovemon().getSpeed()) {
			getPlayerCurrentPovemon().attack(getEnemyCurrentPovemon());
			
			if(getEnemyCurrentPovemon().getIsAlive()) {
				getEnemyCurrentPovemon().attack(getPlayerCurrentPovemon());							
			}
		}
		else {
			getEnemyCurrentPovemon().attack(getPlayerCurrentPovemon());	
			
			if(getPlayerCurrentPovemon().getIsAlive()) {
				getPlayerCurrentPovemon().attack(getEnemyCurrentPovemon());
			}
		}
	}
	
	public void enterBattle(Player player) {
	    boolean exitGame = false;
	    while (!exitGame) {
	    	Utility.clearScreen();
	        Enemy enemy = Enemy.createEnemy(player);
	        player.resetTeam();
	        setEnemyCurrentPovemon(enemy.getTeam().firstElement());
	        setPlayerCurrentPovemon(player.getTeam().firstElement());
	        boolean exitBattle = false;
	        boolean firstTurn = true;
	        while (!exitBattle) {
	        	ui.displayBattleScreen(player, enemy, getPlayerCurrentPovemon(), getEnemyCurrentPovemon());
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
	    String playerLine = player.voiceLines(getPlayerCurrentPovemon());
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
	
	public void modifyTeam(Player player) {
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
		if(!getPlayerCurrentPovemon().getIsAlive() && 
				getEnemyCurrentPovemon().getIsAlive()) return "enemy";
		else if (!getEnemyCurrentPovemon().getIsAlive() &&
				getPlayerCurrentPovemon().getIsAlive()) return "player";
		else return "none";
	}
	
	
	public String getPlayerAction() {
	    if (!getPlayerCurrentPovemon().getIsAlive()) return "3";
	    System.out.print(" Choose an action [1-3]: ");
	    return Utility.scan.nextLine();
	}

	public Povemon getEnemyCurrentPovemon() {
		return enemyCurrentPovemon;
	}

	public void setEnemyCurrentPovemon(Povemon enemyCurrentPovemon) {
		this.enemyCurrentPovemon = enemyCurrentPovemon;
	}

	public Povemon getPlayerCurrentPovemon() {
		return playerCurrentPovemon;
	}

	public void setPlayerCurrentPovemon(Povemon playerCurrentPovemon) {
		this.playerCurrentPovemon = playerCurrentPovemon;
	}
}
