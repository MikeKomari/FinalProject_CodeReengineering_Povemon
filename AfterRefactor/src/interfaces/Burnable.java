package interfaces;

public interface Burnable {
	boolean isBurned();
	int baseBurnDamage = 5;
	void burned(boolean isBurned);
}
