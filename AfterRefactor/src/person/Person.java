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
	    for (Povemon povemon : this.team) {
	        povemon.reset();
	    }
	}
	
	public abstract String voiceLines(Boolean isWinner);
}
