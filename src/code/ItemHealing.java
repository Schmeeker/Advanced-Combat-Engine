package code;

public class ItemHealing extends ItemConsumable {
	
	public ItemHealing() {
		type = 'H';
	}
	
	public int use(Character user, Character target) {
		int value = 0;
		if(this.uses > 0) {
			value += 3 * ((int) (Math.random() * (this.potency + 1)) + target.luck + user.healing);
			target.damage(-value);
			this.uses--;
		}
		return value;
	}

}
