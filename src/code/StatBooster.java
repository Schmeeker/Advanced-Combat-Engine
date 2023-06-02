package code;

public class StatBooster extends ItemConsumable {
	
	int turns;
	
	int changeMaxHP;
	int changeStrength;
	int changeStamina;
	int changeAgility;
	int changeLuck;
	int changeHealing;
	//int changeAC;
	

	public StatBooster() {
		type = 'X';
		this.uses = 1;
	}
	
	public int boost(Character target) {
		if(!target.boosted) {
			target.currentBoost = this;
			
			target.boosted = true;
			target.turnsBoosted = this.turns;
			
			
			target.maxHP += this.changeMaxHP * this.potency;
			target.currentHP += this.changeMaxHP * this.potency;
			
			target.strength += this.changeStrength * this.potency;
			target.agility += this.changeAgility * this.potency;
			target.stamina += this.changeStamina * this.potency;
			target.luck += this.changeLuck * this.potency;
			target.healing += this.changeHealing * this.potency;
			//target.AC += this.changeAC * this.potency;
			
			this.uses--;
			
			return target.turnsBoosted;
		} else
			return -1; // the player is already boosted and cannot be boosted more.
	}

}
