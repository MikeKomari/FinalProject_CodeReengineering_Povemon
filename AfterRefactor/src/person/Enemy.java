package person;

import java.util.Random;
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
	
	public static Enemy createEnemy(Player player){
		int teamSize = player.getTeam().size();
		String enemyName = enemyNameList[rand.nextInt(enemyNameList.length)];
		Vector<Povemon> enemyTeam = new Vector<Povemon>();
		for(int i = 0; i < teamSize; i++) {
			String randomName = povemonNameList[rand.nextInt(povemonNameList.length)];
			boolean isDuplicate = false;
			for(Povemon p : enemyTeam) {
	            if (p.getName().equals(randomName)) {
	                isDuplicate = true;
	                break;
	            }
	        }
			if(!isDuplicate) {
				Povemon newPovemon = Povemon.createPovemon(randomName);
				enemyTeam.add(newPovemon);
			}
			else {				
				i--;
			}
		}
		Enemy enemy = new Enemy(enemyName, enemyTeam);
		return enemy;
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
