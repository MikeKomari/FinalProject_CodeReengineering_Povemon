package povemon;

import interfaces.Burnable;
import interfaces.Poisonable;

public class WaterType extends Povemon implements Poisonable, Burnable{
	
	public WaterType(String name, Integer hp, Integer defense, Integer speed, Integer attack, String type, Integer price) {
		super(name, hp, defense, speed, attack, type, price);
		this.setWeakness(GRASS);
	}

	@Override
	public void poisoned(boolean isPoisoned) {
		if(isPoisoned) {
			this.damaged("poisoned", basePoisonDamage);
		}
	}

	@Override
	public void burned(boolean isBurned) {
		if(isBurned) {
			this.damaged("burned", baseBurnDamage / 2);
		}
	}
}
