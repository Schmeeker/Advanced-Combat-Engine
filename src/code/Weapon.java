package code;

public class Weapon {
	
	String name;
	String description;
	
	int diceMultiplier = 1;
	int damageDice;
	
	int attackBonus = 0;
	int damageBonus = 0;
	
	public Weapon() {
	}
	
	public int rollDamage() {
		int roll = damageBonus;
		
		for(int i = 1; i <= diceMultiplier; i++)
			roll += (int) (Math.random() * (damageDice) + 1);
		
		return roll;
	}

}
