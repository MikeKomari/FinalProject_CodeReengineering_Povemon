package person;

import java.util.Vector;

import povemon.Povemon;

public abstract class Person extends Entity {
	private Integer money;
	private Vector<Povemon> team;
	
	public Person(String name, Vector<Povemon> team) {
		super(name);
		this.team = team;
		this.money = 0;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Vector<Povemon> getTeam() {
		return team;
	}

	public void setTeam(Vector<Povemon> team) {
		this.team = team;
	}	
	
	public String voiceLines(Povemon currentPovemon) {
		String line = "\"I choose you, " + currentPovemon.getName() + "!";
		
		return line;
	}
	
	public void resetTeam() {
		int len = this.getTeam().size();
		
		for(int i = 0; i < len; i++) {
			Povemon currentPovemon = this.getTeam().get(i);
			currentPovemon.setCurrHp(currentPovemon.getHp());
			currentPovemon.setIsAlive(true);
		}
	}
	
	public abstract String voiceLines(Boolean isWinner);
}
