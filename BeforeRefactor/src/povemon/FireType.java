package povemon;

import interfaces.Poisonable;

public class FireType extends Povemon implements Poisonable{
		
	public FireType(String name, Integer hp, Integer defense, Integer speed, Integer attack, String type, Integer price) {
		super(name, hp, defense, speed, attack, type, price);
		this.setWeakness(WATER);
	}

	@Override
	public void poisoned(boolean isPoisoned) {
		if(isPoisoned) {
			this.damaged("poisoned", basePoisonDamage);
		}
		
	}
}
