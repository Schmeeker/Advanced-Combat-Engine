package code;

public class LevelItem {
	
	int changeMaxHP;
	int changeStrength;
	int changeStamina;
	int changeAgility;
	int changeLuck;
	int changeObservation;

	public LevelItem(int X) {
		this.changeMaxHP = (int) (Math.random() * (X + 1));
		this.changeStrength = (int) (Math.random() * (X + 1));
		this.changeStamina = (int) (Math.random() * (X + 1));
		this.changeAgility = (int) (Math.random() * (X + 1));
		this.changeLuck = (int) (Math.random() * (X + 1));
		this.changeObservation = (int) (Math.random() * (X + 1));
	}

}
