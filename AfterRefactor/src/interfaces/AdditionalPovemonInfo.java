package interfaces;

public interface AdditionalPovemonInfo {
	String FIRE = "Fire";
	String GRASS = "Grass";
	String WATER = "Water";
	
	String[] povemonNameList = {"CharbolT", "AqualaSh", "TreethIng", "RUmbleu", "ACranix", "VerMarok", "MirMage", "FOscorra"};
	Integer[] povemonPriceList = {200, 200, 200, 750, 500, 1000, 350, 400};
	String[] povemonTypeList = {FIRE, WATER, GRASS, FIRE, WATER, GRASS, FIRE, GRASS};
	
	Integer povemonCount = povemonNameList.length;
	
	Integer[][] povemonStatList = {
		    {50, 30, 20, 60}, 
		    {44, 25, 25, 43}, 
		    {45, 25, 20, 45}, 
		    {50, 35, 15, 55}, 
		    {40, 20, 20, 45}, 
		    {60, 30, 25, 40}, 
		    {35, 40, 15, 75}, 
		    {45, 30, 20, 50}  
		};
}
