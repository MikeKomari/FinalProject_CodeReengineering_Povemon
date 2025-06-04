package povemon;

import interfaces.Burnable;

public class GrassType extends Povemon implements Burnable{
		
	public GrassType(String name, Integer hp, Integer defense, Integer speed, Integer attack, String type, Integer price) {
		super(name, hp, defense, speed, attack, type, price);
		this.setWeakness(FIRE);
	}

	@Override
	public void burned(boolean isBurned) {
		if(isBurned) {
			this.damaged("burned", baseBurnDamage * 2);
		}
	}
}
