package code;

public class Character {
	
	String name;
	boolean isAlive;
	
	String backstory;
	
	int maxHP;
	int currentHP;
	
	int strength;
	int agility;
	int stamina;
	int luck;
	int healing;
	
	int AC;
	
	Weapon equipmentWeapon;
	Armour equipmentArmour;
	//ItemEquippable equipmentItem1;
	//ItemEquippable equipmentItem2;
	
	ItemConsumable[] inventory = new ItemConsumable[10];
	int numberOfItems = 0;

	boolean tanking;
	boolean boosted;
	int turnsBoosted = 0;
	StatBooster currentBoost;

	public Character(int health) {
		// by default, they will be alive at full hp
		this.isAlive = true;
		this.maxHP = health;
		this.currentHP = maxHP;
		
	}
	
	public int damage(int amount) {
		// if the character is currently tanking, subtract 1d4 + stamina modifier
		if(this.tanking)
			amount -= this.stamina + (int) (Math.random() * (4) + 1);

		// subtract the damage from the current HP.
		this.currentHP -= amount;
		// check if this kills them.
		if(this.currentHP <= 0) {
			this.currentHP = 0;
			this.isAlive = false;
		} else if (this.currentHP > 0)
			this.isAlive = true;
		
		return amount;
	}
	
	public boolean giveItem(ItemConsumable item) {
		if (this.numberOfItems >= this.inventory.length) {
			return false;
		} else {
			this.inventory[numberOfItems] = item;
			this.numberOfItems++;
			return true;
		}
	}

	public String getBio() {

		String bio = "";

		bio += "\tName:\t\t\t" + this.name;
		bio += "\n\tIs Currently Alive:\t" + this.isAlive;
		bio += "\n\tEquipped Weapon:\t" + this.equipmentWeapon.name;
		bio += "\n\tEquipped Armour:\t" + this.equipmentArmour.name;
		bio += "\n\tStrength:\t\t" + this.strength;
		bio += "\n\tStamina:\t\t" + this.stamina;
		bio += "\n\tAgility:\t\t" + this.agility;
		bio += "\n\tHealing:\t\t" + this.healing;
		bio += "\n\tAC:\t\t\t" + this.AC;
		bio += "\n\tLuck:\t\t\t" + this.luck;
		bio += "\n\tBackstory:\n\t\t" + this.backstory; 
		
		return bio;
	}

	public void tank() {
		this.tanking = true;
	}

	public void beginTurn() {
		
		if(this.currentHP > 0)
			this.isAlive = true;
		else if(this.currentHP <= 0) {
			this.currentHP = 0;
			this.isAlive = false;
		}
		
		this.tanking = false;
		
		if(this.equipmentArmour != null)
			this.AC = this.equipmentArmour.defenceValue + this.agility;
		else
			this.AC = this.agility;
		
		if(this.boosted && this.turnsBoosted >= 0)
			turnsBoosted--;
		else if (this.boosted && this.turnsBoosted <= 0) {
			this.boosted = false;
			turnsBoosted = 0;
			
			this.maxHP -= this.currentBoost.changeMaxHP;
			this.currentHP -= this.currentBoost.changeMaxHP;
			
			this.strength -= this.currentBoost.changeStrength;
			this.agility -= this.currentBoost.changeAgility;
			this.stamina -= this.currentBoost.changeStamina;
			this.luck -= this.currentBoost.changeLuck;
			this.healing -= this.currentBoost.changeHealing;
			//this.AC -= this.currentBoost.changeAC;
		}
		
	}
	
	public String levelUp(Character defeated) {
		
		String text = "";
		
		text +=this.name;
		text +="\n\tHP:\t\t" + this.maxHP + "\t+ " + (int) (defeated.maxHP / 3);
		text +="\n\tStrength:\t" + this.strength + "\t+ " + (int) (defeated.strength / 1.5);
		text +="\n\tStamina:\t" + this.stamina + "\t+ " + (int) (defeated.stamina / 1.5);
		text +="\n\tAgility:\t" + this.agility + "\t+ " + (int) (defeated.agility / 1.5);
		text +="\n\tHealing:\t" + this.healing + "\t+ " + (int) (defeated.healing / 2);
		text +="\n\tLuck:\t\t" + this.luck + "\t+ " + (int) (defeated.luck / 4);
		
		this.maxHP += (int) defeated.maxHP/3;
		this.currentHP += (int) defeated.maxHP/3;
		
		this.strength += (int) defeated.strength/2;
		this.agility += (int) defeated.agility/4;
		this.stamina += (int) defeated.stamina/1.5;
		this.luck += (int) defeated.luck/16;
		this.healing += (int) defeated.healing/2;
		
		return text;
	}
	
	public void endBattle() {
		this.numberOfItems = this.inventory.length;
		for(int i = this.inventory.length - 1; i > 0 ; i--) {
			
			if(this.inventory[i] == null) {
				this.numberOfItems--;
				continue;
				
			} else {
			
				if(this.inventory[i].uses <= 0) {
					
					this.inventory[i] = null;
					this.numberOfItems--;
					continue;
					
				} else if(this.inventory[i-1] == null && this.inventory[i] != null) {
					
					this.inventory[i-1] = this.inventory[i];
					this.inventory[i] = null;
					continue;
					
				}
			}
		}
	}
	

}
