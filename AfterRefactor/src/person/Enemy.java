package person;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import interfaces.AdditionalPovemonInfo;
import povemon.Povemon;

public class Enemy extends Person implements AdditionalPovemonInfo{
	private static final String[] enemyNameList = {"Fortino", "Aldric", "Tommy", "Marco", "Willsen"};
	private final Integer BASE_MONEY = 200;
	
	private static Random rand = new Random();
	
	public Enemy(String name, Vector<Povemon> team) {
		super(name, team);
		this.setMoney(BASE_MONEY + 1 + rand.nextInt(100));
	}
	
	public static Enemy createEnemy(Player player) {
	    String enemyName = getRandomEnemyName();
	    Vector<Povemon> enemyTeam = generateEnemyTeam(player.getTeam().size());
	    return new Enemy(enemyName, enemyTeam);
	}

	private static String getRandomEnemyName() {
	    return enemyNameList[rand.nextInt(enemyNameList.length)];
	}

	private static Vector<Povemon> generateEnemyTeam(int teamSize) {
	    Vector<Povemon> team = new Vector<>();
	    Set<String> selectedNames = new HashSet<>();
	    while (team.size() < teamSize) {
	        String name = getRandomPovemonName();
	        if (selectedNames.add(name)) {
	            team.add(Povemon.createPovemon(name));
	        }
	    }
	    return team;
	}

	private static String getRandomPovemonName() {
	    // Assume povemonNameList is accessible here or passed in
	    return povemonNameList[rand.nextInt(povemonNameList.length)];
	}

	public String voiceLines(Player player) {
		String line = "Oh? You're approaching me, " + player.getName() + "?";
		return line;
	}
	
	public Povemon switchNextPovemon() {
	    if (!this.getTeam().isEmpty()) {
	        this.getTeam().remove(0);
	        return this.getTeam().isEmpty() ? null : this.getTeam().firstElement();
	    }
	    return null;
	}

	@Override
	public String voiceLines(Boolean isWinner) {
		String line = "";
		if(isWinner) line = "Hmph. Skillissue";
		else line = "Argh, fine. You win this time, but I’ll be back!";
		return line;
	}
}
