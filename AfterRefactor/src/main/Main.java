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
	private static GameUI ui = new GameUI();
	private static GameHandler gameHandler = new GameHandler(); 
	private static Shop shop = new Shop("Shop");
	
	public static Povemon playerCurrentPovemon;
	public static Povemon enemyCurrentPovemon;
	
	private void greetingsMenu_Main() {
		Vector<Povemon> playerTeam = new Vector<Povemon>();
		Vector<Povemon> playerPovemonList = new Vector<Povemon>();
		String playerName = ui.greetingsMenu_Introduction();
		ui.chooseStarter(playerTeam, playerPovemonList);
		
		player = Player.createPlayer(playerName, playerTeam, playerPovemonList);
		
		if(player.getName().equals("OV") || player.getName().equals("Octa")) {
			player.setMoney(9999);
		}
	}
	
	public Main() {
		greetingsMenu_Main();
		
		boolean exit = false;
		while(!exit) {
			Utility.clearScreen();
			ui.mainMenu(player.getName());
			
			String input = scan.nextLine();
			
			switch (input) {
			case "1":
				Utility.clearScreen();
				gameHandler.enterBattle(player);
				break;
			case "2":
				Utility.clearScreen();
				shop.shopMenu(player);
				break;
			case "3":
				Utility.clearScreen();
				gameHandler.modifyTeam();
				break;
			case "4":
				exit = true;
				Utility.clearScreen();
				ui.printExit();
				break;
			default:
				System.out.println(" Invalid Input!");
				Utility.pressEnter();
				break;
			}
			
		}
	}
	
	public static void main(String[] args) {
		new Main();
	}
}