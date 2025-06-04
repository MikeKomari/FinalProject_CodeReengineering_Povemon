package other;

import java.util.Vector;

import interfaces.AdditionalPovemonInfo;
import interfaces.Utility;
import main.Main;
import person.Entity;
import person.Player;
import povemon.Povemon;

public class Shop extends Entity implements AdditionalPovemonInfo, Utility {
	private Vector<Povemon> povemonList;
	
	public Shop(String name) {
		super(name);
		this.povemonList = new Vector<Povemon>();
		generateShopList();
	}
	
	public Vector<Povemon> getPovemonList() {
		return povemonList;
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
		Main.clearScreen();
		Main.printBorder(40);
        System.out.println("            Povémon Shop            ");
        Main.printBorder(40);
        System.out.printf(" %-20s ┃ %-20s\n", "Available Storage", "Price");
        Main.printBorder(40);
		for(Povemon p : this.povemonList) {
			 System.out.printf(" %-20s ┃ %-20d\n", p.getName()+ " [" +p.getType() + "]", p.getPrice());
			 Main.printBorder(40);
		}
	}
	
	public void shopMenu(Player player) {
	    boolean exit = false;

	    while (!exit) {
	        seeShop();
	        
	        System.out.printf(" Money: %d poveDollars\n", player.getMoney());
	        Main.printBorder(40);
	        System.out.println(" 1. Buy Povémon");
	        System.out.println(" 2. Exit Shop");
	        Main.printBorder(40);
	        System.out.print(" Choose an option: ");
	        
	        String choice = scan.nextLine();

	        switch (choice) {
	            case "1":
	                buyPovemon(player);
	                break;
	            case "2":
	                System.out.println(" Thank you for visiting the shop!");
	                exit = true;
	                break;
	            default:
	                System.out.println(" Invalid Choice!.");
	        }
	        
	        Main.pressEnter();
	    }
	}

	private void buyPovemon(Player player) {
	    seeShop();
	    System.out.println(" Enter the name of the Povémon you want to buy [case insensitive, 0 to go back]: ");
	    System.out.print(" >> ");
	    String selectedPovemon = scan.nextLine();

	    if (selectedPovemon.equalsIgnoreCase("0")) {
	        return;
	    }

	    Povemon povemonToBuy = null;

	    for (Povemon p : this.povemonList) {
	        if (p.getName().equalsIgnoreCase(selectedPovemon)) {
	            povemonToBuy = p;
	            break;
	        }
	    }

	    if (povemonToBuy == null) {
	        System.out.println(" Povémon not found! Please choose a valid one.");
	        return;
	    }

	    if (player.getMoney() < povemonToBuy.getPrice()) {
	        System.out.println(" You don't have enough money to buy this Povémon!");
	        return;
	    }

	    player.setMoney(player.getMoney() - povemonToBuy.getPrice());
	    player.getPovemonList().add(povemonToBuy);

	    System.out.printf(" You successfully bought %s for %d poveDollars!\n", povemonToBuy.getName(), povemonToBuy.getPrice());

	    this.povemonList.remove(povemonToBuy);
	}
}
