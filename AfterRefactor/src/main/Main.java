package main;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import interfaces.AdditionalPovemonInfo;
import interfaces.Utility;
import interfaces.MenuAction;
import other.Shop;
import person.Player;
import povemon.Povemon;

import menu.BattleAction;
import menu.ExitAction;
import menu.ModifyTeamAction;
import menu.ShopAction;

public class Main implements AdditionalPovemonInfo, Utility{
	
	private static Player player;
	private static GameUI ui = new GameUI();
	private static GameHandler gameHandler = new GameHandler(); 
	private static Shop shop = new Shop("Shop");
	private Map<String, MenuAction> menuActions = new HashMap<>();
	public static Povemon playerCurrentPovemon;
	public static Povemon enemyCurrentPovemon;
	
	private void greetingsMenu_Main() {
		Vector<Povemon> playerTeam = new Vector<Povemon>();
		Vector<Povemon> playerPovemonList = new Vector<Povemon>();
		String playerName = ui.greetingsMenu_Introduction();
		ui.chooseStarter(playerTeam, playerPovemonList);
		player = Player.createPlayer(playerName, playerTeam, playerPovemonList);
	}
	
	private void setupMenuActions() {
		menuActions.put("1", new BattleAction(gameHandler, player));
		menuActions.put("2", new ShopAction(shop, player));
		menuActions.put("3", new ModifyTeamAction(gameHandler, player));
		menuActions.put("4", new ExitAction());
	}
	
	public Main() {
		greetingsMenu_Main();
		setupMenuActions();
		boolean exit = false;
		while(!exit) {
			Utility.clearScreen();
			ui.mainMenu(player.getName());
			String input = scan.nextLine();
			MenuAction action = menuActions.get(input);
			if (action != null) {action.execute();
				if (action instanceof ExitAction) {exit = true;}
			} else {System.out.println(" Invalid Input!");Utility.pressEnter();}}
	}
	
	public static void main(String[] args) {
		new Main();
	}
}