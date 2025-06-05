package povemon;

import java.util.HashMap;
import java.util.Map;

import interfaces.AdditionalPovemonInfo;
import interfaces.Burnable;
import interfaces.Poisonable;
import interfaces.Utility;
import person.Entity;

public abstract class Povemon extends Entity implements AdditionalPovemonInfo{
	private Integer currHp;
	private Integer hp;
	private Integer defense;
	private Integer speed;
	private Integer attack;
	private String type;
	private String weakness;
	private Boolean isAlive;
	private Integer price;
	
	private boolean isBurned;
    private boolean isPoisoned;
    
    private Integer STATUS_DAMAGE = 5;
    
    @FunctionalInterface
    private interface PovemonFactory {
        Povemon create(String name, Integer hp, Integer attack, Integer defense, Integer speed, String type, Integer price);
    }
    
    private static final Map<String, PovemonFactory> typeFactoryMap = new HashMap<>();

    static {
        typeFactoryMap.put(FIRE, FireType::new);
        typeFactoryMap.put(WATER, WaterType::new);
        typeFactoryMap.put(GRASS, GrassType::new);
    }
	
	public Povemon(String name, Integer hp, Integer attack, Integer defense, Integer speed, String type, Integer price) {
		super(name);
		this.hp = hp;
		this.defense = defense;
		this.speed = speed;
		this.attack = attack;
		this.currHp = hp;
		this.type = type;
		this.isAlive = true;
		this.price = price;
		this.isBurned = false;
		this.isPoisoned = false;
	}

	public Integer getCurrHp() {
		return currHp;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public void setCurrHp(Integer currHp) {
		this.currHp = currHp;
	}

	public Integer getHp() {
		return hp;
	}
	public void setHp(Integer hp) {
		this.hp = hp;
	}
	
	public String getType() {
		return type;
	}

	public Boolean getIsAlive() {
		return isAlive;
	}

	public void setIsAlive(Boolean isAlive) {
		this.isAlive = isAlive;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDefense(Integer defense) {
		this.defense = defense;
	}
	public Integer getDefense() {
		return defense;
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer agility) {
		this.speed = agility;
	}
	public Integer getAttack() {
		return attack;
	}
	public boolean isBurned() {
		return isBurned;
	}

	public void setBurned(boolean isBurned) {
		this.isBurned = isBurned;
	}

	public boolean isPoisoned() {
		return isPoisoned;
	}

	public void setPoisoned(boolean isPoisoned) {
		this.isPoisoned = isPoisoned;
	}

	public void setAttack(Integer attack) {
		this.attack = attack;
	}
	
	public String getWeakness() {
		return weakness;
	}

	public void setWeakness(String weakness) {
		this.weakness = weakness;
	}
	
	private void checkAlive() {
        if (this.currHp <= 0) {
            this.currHp = 0;
            this.isAlive = false;
            System.out.println(" " + this.getName() + " has fainted!");
            Utility.pressEnter();
        }
    }

	public void damaged(int attackDamage) {
	    int damageTaken = Math.max(attackDamage - this.defense, 0);
	    System.out.printf(" %s took %d damage!\n", this.getName(), damageTaken);
	    applyDamage(damageTaken);
	}

	public void damaged(String status, int statusDamage) {
	    System.out.printf(" %s is %s and took extra %d damage!\n", this.getName(), status, statusDamage);
	    applyDamage(statusDamage);
	}

	private void applyDamage(int damage) {
	    this.currHp = Math.max(this.currHp - damage, 0);
	    checkAlive();
	}
    public void takeStatusEffectDamage() {
        if (isBurned) {
            this.damaged("burned", STATUS_DAMAGE * 2);
        }
        if (isPoisoned) {
            this.damaged("poisoned", STATUS_DAMAGE);
        }
    }

    public void performTurn() {
        if (!this.isAlive) return;
        takeStatusEffectDamage();
    }

    public void attack(Povemon target) {
        if (!this.isAlive) return;
        int damage = calculateDamage(target);
        target.damaged(damage);
        if (this.type.equals(GRASS) && target instanceof Poisonable) {
            Poisonable poisonableTarget = (Poisonable) target;
            if (Math.random() < 0.3) { 
                poisonableTarget.poisoned(true);
            }
        } 
        else if (this.type.equals(FIRE) && target instanceof Burnable) {
            Burnable burnableTarget = (Burnable) target;
            if (Math.random() < 0.5) {
                burnableTarget.burned(true);
            }
        }
    }

    private int calculateDamage(Povemon target) {
	    int damage = this.attack;
	    if (target.getType().equals(this.weakness)) {
	    	damage /= 2;
	    } 
	    else if (target.getWeakness().equals(this.type)) {
	    	damage *= 2; 
	    }
	    return damage;
    }

    public static Povemon createPovemon(String name) {
        int index = findPokemonInfoByIndex(name);
        if (index == -1) return null;
        String type = AdditionalPovemonInfo.povemonTypeList[index];
        Integer hp = AdditionalPovemonInfo.povemonStatList[index][0];
        Integer attack = AdditionalPovemonInfo.povemonStatList[index][1];
        Integer defense = AdditionalPovemonInfo.povemonStatList[index][2];
        Integer speed = AdditionalPovemonInfo.povemonStatList[index][3];
        Integer price = AdditionalPovemonInfo.povemonPriceList[index];
        PovemonFactory factory = typeFactoryMap.get(type);
        return (factory != null) ? factory.create(name, hp, attack, defense, speed, type, price) : null;
    }
    
    private static int findPokemonInfoByIndex(String name) {
    	int index = -1;
        for (int i = 0; i < AdditionalPovemonInfo.povemonNameList.length; i++) {
            if (AdditionalPovemonInfo.povemonNameList[i].equalsIgnoreCase(name)) {
                index = i;
                break;
            }
        }
        
        return index;
    }
     
    public void reset() {
        this.currHp = this.hp; 
        this.isAlive = true;
    }
    
    public String getBattleStatus(Povemon currentInBattle) {
        if (!this.getIsAlive()) return "Fainted";
        if (this.equals(currentInBattle)) return "In Battle";
        return "Available";
    }

    public String getEffects() {
        String effects = "";
        if (this instanceof Poisonable && ((Poisonable) this).isPoisoned()) effects += "Poisoned ";
        if (this instanceof Burnable && ((Burnable) this).isBurned()) effects += "Burned ";
        return effects.isEmpty() ? "None" : effects.trim();
    }
}