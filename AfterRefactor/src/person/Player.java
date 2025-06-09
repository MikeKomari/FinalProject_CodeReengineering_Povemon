package person;
import java.util.Vector;

import interfaces.Utility;
import povemon.Povemon;

public class Player extends Person implements Utility{
	private Vector<Povemon> povemonList;
	
	public Player(String name, Vector<Povemon> team, Vector<Povemon> list) {
		super(name, team);
		this.povemonList = list;
	}

	public Vector<Povemon> getPovemonList() {
		return new Vector<>(povemonList); 
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
	    return isWinner ? "Victory is ours! Let’s keep this streak going!" : "Ouch, that was rough...";
	}
	
	private void seeTeam(Povemon currentInBattle) {
	    System.out.println();
	    Utility.printBorder(70);
	    System.out.printf(" | %-20s | %-5s | %-15s | %-17s |\n", "Name", "Type", "Status", "Effects");
	    Utility.printBorder(70);
	    for (int i = 0; i < this.getTeam().size(); i++) {
	        Povemon current = this.getTeam().get(i);
	        System.out.printf(" | %-20s | %-5s | %-15s | %-17s |\n", current.getName(), current.getType(), current.getBattleStatus(currentInBattle), current.getEffects());
	        Utility.printBorder(70);
	    }
	}

	private String formatPovemon(Povemon povemon, boolean useBrackets) {
	    return povemon.getName() + (useBrackets ? " [" : " (") + povemon.getType() + (useBrackets ? "]" : ")");
	}
	
	public void seeAllPovemon() {
		Utility.printBorder(60);
	    System.out.println("            Modify Your Team            ");
	    Utility.printBorder(60);
	    System.out.printf(" %-20s │ %-20s\n", "Current Team", "Povémon Storage");
	    Utility.printBorder(60);
	    int maxSize = Math.max(this.getTeam().size(), this.povemonList.size());
	    for (int i = 0; i < maxSize; i++) {
	    	String teamPovemon = (i < this.getTeam().size()) ? formatPovemon(this.getTeam().get(i), false) : "";
	    	String availablePovemon = (i < this.povemonList.size()) ? formatPovemon(this.povemonList.get(i), true) : "";
	        System.out.printf(" %-20s │ %-20s\n", teamPovemon, availablePovemon);}
	    Utility.printBorder(60);
	}


	public Integer swapPovemon(Povemon currentInBattle) {
	    while (true) {
	        this.seeTeam(currentInBattle);
	        System.out.println(" Which Pokémon would you like to switch to battle? [case insensitive, 0 to go back]");
	        System.out.print(" >> ");
	        String inTeamName = scan.nextLine().trim();
	        if (inTeamName.equals("0")) return -1;
	        Integer index = findTeamPovemonIndexByName(inTeamName);
	        if (index == -1) {printInvalidChoice();
	        } else if (!isPovemonAlive(index)) {printFaintedMessage(index);
	        } else if (isSameAsCurrentBattle(index, currentInBattle)) {printAlreadyInBattleMessage(currentInBattle);
	        } else {printSuccessSwap(index);return index;}
	        Utility.pressEnter();
	    }
	}

	private Integer findTeamPovemonIndexByName(String name) {
	    for (int i = 0; i < this.getTeam().size(); i++) {
	        if (this.getTeam().get(i).getName().equalsIgnoreCase(name)) {
	            return i;
	        }
	    }
	    return -1;
	}

	private boolean isPovemonAlive(int index) {
	    return this.getTeam().get(index).getIsAlive();
	}

	private boolean isSameAsCurrentBattle(int index, Povemon current) {
	    return this.getTeam().get(index).getName().equalsIgnoreCase(current.getName());
	}

	private void printInvalidChoice() {
	    System.out.println(" Invalid Choice!");
	}

	private void printFaintedMessage(int index) {
	    System.out.printf(" %s is fainted, please choose another Pokémon!\n", this.getTeam().get(index).getName());
	}

	private void printAlreadyInBattleMessage(Povemon current) {
	    System.out.printf(" %s is already in battle!\n", current.getName());
	}

	private void printSuccessSwap(int index) {
	    System.out.println(" Successfully swapped " + this.getTeam().get(index).getName() + " into battle!");
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
	    Povemon selectedPovemon = Utility.findPovemonByName(this.povemonList, name);
	    if (selectedPovemon == null) {System.out.println(" This Povémon is not in your storage!");return;}
	    if (this.getTeam().size() >= 4) {handleFullTeam();return;}
	    addPovemonToTeam(selectedPovemon);
	}

	private void handleFullTeam() {
	    System.out.println(" Your team is full. Would you like to swap a Povémon?");
	    System.out.println(" 1. Yes");
	    System.out.println(" 2. No");
	    System.out.print(" >> ");
	    String choice = scan.nextLine();
	    if (choice.equals("1")) { Utility.clearScreen(); this.swapPovemonFromStorage();
	    } else {System.out.println(" No Povémon were inserted into your team.");}
	}

	private void addPovemonToTeam(Povemon selectedPovemon) {
	    this.getTeam().add(selectedPovemon);
	    this.povemonList.remove(selectedPovemon);
	    System.out.println(" Successfully inserted " + selectedPovemon.getName() + " into your team!");
	}


	public void swapPovemonFromStorage() {
	    this.seeAllPovemon();
	    Povemon teamPovemon = promptForPovemon( "Which Povémon from your team would you like to swap with one from storage? [case insensitive, 0 to cancel]", this.getTeam());
	    if (teamPovemon == null) return;
	    Povemon storagePovemon = promptForPovemon("Which Povémon from your storage would you like to insert? [case insensitive, 0 to cancel]",this.povemonList);
	    if (storagePovemon == null) return;
	    performSwap(teamPovemon, storagePovemon);
	}

	private Povemon promptForPovemon(String prompt, Vector<Povemon> list) {
	    System.out.println(prompt);
	    System.out.print(" >> ");
	    String name = scan.nextLine().trim();
	    if (name.equals("0")) return null;
	    Povemon found = Utility.findPovemonByName(list, name);
	    if (found == null) {System.out.println(list == this.getTeam() ? " This Povémon is not in your team.": " This Povémon is not in your storage.");}
	    return found;
	}

	private void performSwap(Povemon teamPovemon, Povemon storagePovemon) {
	    this.povemonList.add(teamPovemon);
	    this.getTeam().remove(teamPovemon);
	    this.getTeam().add(storagePovemon);
	    this.povemonList.remove(storagePovemon);
	    System.out.println(" Successfully swapped " + teamPovemon.getName() + " with " + storagePovemon.getName());
	}
}
