package person;

import java.util.Vector;

import interfaces.Burnable;
import interfaces.Poisonable;
import interfaces.Utility;
import main.Main;
import povemon.Povemon;

public class Player extends Person implements Utility{
	private Vector<Povemon> povemonList;
	
	public Player(String name, Vector<Povemon> team, Vector<Povemon> list) {
		super(name, team);
		this.povemonList = list;
	}

	public Vector<Povemon> getPovemonList() {
		return povemonList;
	}

	public void setPovemonList(Vector<Povemon> povemonList) {
		this.povemonList = povemonList;
	}
	
	public static Player createPlayer(String playerName, Vector<Povemon> playerTeam, Vector<Povemon> playerPovemonList) {
		Player player = new Player(playerName, playerTeam, playerPovemonList);
		
		return player;
	}

	@Override
	public String voiceLines(Povemon currentPovemon) {
		String line = "Time to shine! Let’s go, " + currentPovemon.getName() + "!";
		return line;
	}

	@Override
	public String voiceLines(Boolean isWinner) {
		String line = "";

		if(isWinner) line = "Victory is ours! Let’s keep this streak going!";
		else line = "Ouch, that was rough...";
		
		return line;
	}
	
	private void seeTeam(Povemon currentInBattle) {
	    System.out.println();
	    Main.printBorder(70);
	    System.out.printf(" | %-20s | %-5s | %-15s | %-17s |\n", "Name", "Type", "Status", "Effects");
	    Main.printBorder(70);
	    for (int i = 0; i < this.getTeam().size(); i++) {
	        Povemon current = this.getTeam().get(i);
	        String status = "";
	        String effects = "";

	        if (current.getIsAlive()) {
	            if (current.equals(currentInBattle)) {
	                status = "In Battle";
	            } else {
	                status = "Available";
	            }
	        } else {
	            status = "Fainted";
	        }

	        if (current instanceof Poisonable && ((Poisonable) current).isPoisoned()) {
	            effects += "Poisoned ";
	        }
	        if (current instanceof Burnable && ((Burnable) current).isBurned()) {
	            effects += "Burned ";
	        }

	        effects = effects.trim();

	        System.out.printf(" | %-20s | %-5s | %-15s | %-17s |\n", 
	            current.getName(), 
	            current.getType(), 
	            status, 
	            effects.isEmpty() ? "None" : effects);
	        Main.printBorder(70);
	    }
	}

	
	public void seeAllPovemon() {
	    Main.printBorder(60);
	    System.out.println("            Modify Your Team            ");
	    Main.printBorder(60);
	    System.out.printf(" %-20s │ %-20s\n", "Current Team", "Povémon Storage");
	    Main.printBorder(60);

	    int maxSize = Math.max(this.getTeam().size(), this.getPovemonList().size());

	    for (int i = 0; i < maxSize; i++) {
	        String teamPovemon = (i < this.getTeam().size()) 
	            ? this.getTeam().get(i).getName() + " (" + this.getTeam().get(i).getType() + ")" 
	            : "";
	        
	        String availablePovemon = (i < this.getPovemonList().size()) 
	            ? this.getPovemonList().get(i).getName() + " [" + this.getPovemonList().get(i).getType() + "]" 
	            : "";

	        System.out.printf(" %-20s │ %-20s\n", teamPovemon, availablePovemon);
	    }
	    Main.printBorder(60);
	}


	public Integer swapPovemon(Povemon currentInBattle) {
	    boolean exit = false;
	    Integer index = -1;
	    
	    while (!exit) {
	        this.seeTeam(currentInBattle);
	        System.out.println(" Which Pokémon would you like to switch to battle? [case insensitive, 0 to go back]");
	        System.out.print(" >> ");
	        
	        String inTeamName = scan.nextLine();
	        
	        if (inTeamName.equals("0")) {
	            exit = true;
	            continue; 
	        }
	        
	        for (int i = 0; i < this.getTeam().size(); i++) {
	        	if(this.getTeam().get(i).getName().equalsIgnoreCase(inTeamName)) {
	        		index = i;
	        		break;
	        	}
	        }
	        
	        if (index == -1) {
	            System.out.println(" Invalid Choice!");
	        } 
	        else if (!this.getTeam().get(index).getIsAlive()) {
	            System.out.printf(" %s is fainted, please choose another Pokémon!\n", this.getTeam().get(index).getName());
	        } 
	        else if (this.getTeam().get(index).getName().equalsIgnoreCase(currentInBattle.getName())) {
	            System.out.printf(" %s is already in battle!\n", currentInBattle.getName());
	        } 
	        else {
	            System.out.println(" Successfully swapped " + this.getTeam().get(index).getName() + " into battle!");
	            exit = true;
	            return index;
	        }
	        
	        Main.pressEnter();
	    }

	    return -1;
	}
	
	public boolean leftAlive() {
		for (Povemon p : this.getTeam()) {
	        if (p.getIsAlive()) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public void insertPovemon() {
	    this.seeAllPovemon();
	    System.out.println(" Which Povémon would you like to insert? [case insensitive, 0 to cancel]");
	    System.out.print(" >> ");
	    
	    String name = scan.nextLine();
	    
	    if (name.equals("0")) return;

	    Povemon selectedPovemon = null;
	    
	    for (Povemon p : this.getPovemonList()) {
	        if (p.getName().equalsIgnoreCase(name)) {
	            selectedPovemon = p;
	            break;
	        }
	    }
	    
	    if (selectedPovemon == null) {
	        System.out.println(" This Povémon is not in your storage!");
	        return;
	    }

	    if (this.getTeam().size() >= 4) {
	        System.out.println(" Your team is full. Would you like to swap a Povémon?");
	        System.out.println(" 1. Yes");
	        System.out.println(" 2. No");
	        System.out.print(" >> ");
	        String choice = scan.nextLine();
	        
	        if (choice.equals("1")) {
	        	Main.clearScreen();
	            this.swapPovemonFromStorage();
	            return;
	        } else {
	            System.out.println(" No Povémon were inserted into your team.");
	            return;
	        }
	    }
	    
	    this.getTeam().add(selectedPovemon);
	    this.getPovemonList().remove(selectedPovemon);
	    System.out.println(" Successfully inserted " + selectedPovemon.getName() + " into your team!");
	}


	public void swapPovemonFromStorage() {
	    this.seeAllPovemon();

	    System.out.println(" Which Povémon from your team would you like to swap with one from storage? [case insensitive, 0 to cancel]");
	    System.out.print(" >> ");
	    String teamPovemonName = scan.nextLine().trim();

	    if (teamPovemonName.equals("0")) return;

	    Povemon teamPovemon = null;
	    for (Povemon p : this.getTeam()) {
	        if (p.getName().equalsIgnoreCase(teamPovemonName)) {
	            teamPovemon = p;
	            break;
	        }
	    }

	    if (teamPovemon == null) {
	        System.out.println(" This Povémon is not in your team.");
	        return;
	    }

	    System.out.println(" Which Povémon from your storage would you like to insert? [case insensitive, 0 to cancel]");
	    System.out.print(" >> ");
	    String storagePovemonName = scan.nextLine().trim();

	    if (storagePovemonName.equals("0")) return;

	    Povemon storagePovemon = null;
	    for (Povemon p : this.getPovemonList()) {
	        if (p.getName().equalsIgnoreCase(storagePovemonName)) {
	            storagePovemon = p;
	            break;
	        }
	    }

	    if (storagePovemon == null) {
	        System.out.println(" This Povémon is not in your storage.");
	        return;
	    }

	    this.getPovemonList().add(teamPovemon);
	    this.getTeam().remove(teamPovemon);
	    this.getTeam().add(storagePovemon);
	    this.getPovemonList().remove(storagePovemon);

	    System.out.println(" Successfully swapped " + teamPovemon.getName() + " with " + storagePovemon.getName());
	}
}
