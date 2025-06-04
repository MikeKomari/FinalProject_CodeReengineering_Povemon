package interfaces;

public interface Poisonable {
	boolean isPoisoned();
	int basePoisonDamage = 3;
	void poisoned(boolean isPoisoned);
}
