package other;
import interfaces.Utility;

import java.util.Map;
import java.util.Vector;

import interfaces.AdditionalPovemonInfo;
import person.Entity;
import person.Player;
import povemon.Povemon;

public class Shop extends Entity implements AdditionalPovemonInfo, Utility {
	private Vector<Povemon> povemonList;
	private Map<String, ShopOption> optionMap = new java.util.HashMap<>();
	
	public Shop(String name) {
		super(name);
		this.povemonList = new Vector<Povemon>();
		optionMap.put("1", new BuyPovemonOption());
	    optionMap.put("2", new ExitShopOption());
	    generateShopList();
	}
	
	public Vector<Povemon> getPovemonList() {
	    return new Vector<>(povemonList); 
	}

	public void setPovemonList(Vector<Povemon> povemonList) {
		this.povemonList = povemonList;
	}

	private void generateShopList() {
		for (int i = 0; i < povemonNameList.length; i++) {
            povemonList.add(Povemon.createPovemon(povemonNameList[i]));
        }
	}

	private void seeShop() {
		Utility.clearScreen();
		Utility.printBorder(40);
        System.out.println("            Povémon Shop            ");
        Utility.printBorder(40);
        System.out.printf(" %-20s ┃ %-20s\n", "Available Storage", "Price");
        Utility.printBorder(40);
		for(Povemon p : this.povemonList) {
			 System.out.printf(" %-20s ┃ %-20d\n", p.getName()+ " [" +p.getType() + "]", p.getPrice());
			 Utility.printBorder(40);
		}
	}
	
	public void shopMenu(Player player) {
	    boolean exit = false;
	    while (!exit) {
	        seeShop();
	        displayPlayerMoney(player);
	        displayShopOptions();
	        System.out.print(" Choose an option: ");
	        String choice = scan.nextLine();
	        exit = handleShopChoice(choice, player);
	        Utility.pressEnter();
	    }
	}
	
	private void displayPlayerMoney(Player player) {
	    System.out.printf(" Money: %d poveDollars\n", player.getMoney());
	    Utility.printBorder(40);
	}

	private void displayShopOptions() {
	    System.out.println(" 1. Buy Povémon");
	    System.out.println(" 2. Exit Shop");
	    Utility.printBorder(40);
	}

	private boolean handleShopChoice(String choice, Player player) {
	    ShopOption option = optionMap.getOrDefault(choice, new InvalidOption());
	    return option.execute(player, this);
	}

	public void buyPovemon(Player player) {
	    seeShop();
	    String selectedName = promptPovemonName();
	    if (selectedName.equalsIgnoreCase("0")) return;
	    Povemon selected = Utility.findPovemonByName(povemonList, selectedName);
	    if (selected == null) {
	        System.out.println("Povémon not found! Please choose a valid one.");
	        return;
	    }
	    processPurchase(player, selected);
	}

	private String promptPovemonName() {
	    System.out.println(" Enter the name of the Povémon you want to buy [case insensitive, 0 to go back]: ");
	    System.out.print(" >> ");
	    return scan.nextLine();
	}

	private void processPurchase(Player player, Povemon p) {
	    if (player.getMoney() < p.getPrice()) {
	        System.out.println(" You don't have enough money to buy this Povémon!");
	        return;
	    }
	    player.setMoney(player.getMoney() - p.getPrice());
	    player.getPovemonList().add(p);
	    povemonList.remove(p);
	    System.out.printf(" You successfully bought %s for %d poveDollars!\n", p.getName(), p.getPrice());
	}
}
