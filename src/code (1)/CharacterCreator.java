package code;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import com.google.gson.Gson;

public class CharacterCreator {
	
	Character creation;

	public CharacterCreator() {
		this.creation = new Character(1);
	}
	
	public Character wizard(Scanner input, Gson build) throws Exception {
		
		System.out.println("Welcome! Let's make your new Character!");
		
		System.out.println("First, they need a name:");
		System.out.print("Name: \t>");
		
		this.creation.name = input.nextLine();
		
		System.out.println("Now write a quick sentence describing " + this.creation.name + ":");
		System.out.print("Backstory: \t>");
		this.creation.backstory = input.nextLine();
		
		// TODO MAGE COMING SOON!
		
		System.out.println("Now select an archetype for your character:");
		System.out.println(" |1. Fighter");
		System.out.println(" |2. Thief");
		System.out.println(" |3. Healer");
		System.out.println(" |0. Custom");
		
		int choice = CombatEngine.getChoice(input, 3);
		Weapon weapon = new Weapon();
		Armour armour = new Armour();
		
		if(choice > 0) {
			switch(choice) {
			
			case 1:
				this.creation.strength = 4;
				this.creation.stamina = 2;
				this.creation.agility = 3;
				this.creation.luck = 1;
				this.creation.healing = 2;
				
				weapon.name = "Greatsword";
				weapon.attackBonus = 0;
				weapon.damageBonus = 2;
				weapon.diceMultiplier = 2;
				weapon.damageDice = 6;
				weapon.description = "Now THAT is a knife.";
				
				armour.name = "Chain Shirt";
				armour.defenceValue = 13;
				armour.description = "Just a shirt made of interlocking rings.";
				
				break;
				
			case 2:
				this.creation.strength = 1;
				this.creation.stamina = 2;
				this.creation.agility = 4;
				this.creation.luck = 3;
				this.creation.healing = 1;
				
				weapon.name = "Rapier";
				weapon.attackBonus = 2;
				weapon.damageBonus = 0;
				weapon.diceMultiplier = 1;
				weapon.damageDice = 8;
				weapon.description = "Have you seen a therapist lately?";
				
				armour.name = "Leather Armour";
				armour.defenceValue = 11;
				armour.description = "A simple set of armour made from Leather, with boiled strips of leather sewn or adhered to portions to create flexible yet protective armour. Many adventurers begin with this armour as it is cheap and provides more defence than being naked.";
				
				break;
				
			case 3:
				this.creation.strength = 3;
				this.creation.stamina = 2;
				this.creation.agility = 1;
				this.creation.luck = 2;
				this.creation.healing = 5;
				
				weapon.name = "Warhammer";
				weapon.attackBonus = 1;
				weapon.damageBonus = 1;
				weapon.diceMultiplier = 1;
				weapon.damageDice = 10;
				weapon.description = "SMAAAAAAAAAAAAAAAASH!";
				
				armour.name = "Chain Mail";
				armour.defenceValue = 16;
				armour.description = "A whole set of garments made of pure metal. It's flexible yet strong, but it does NOT breathe.";
				
				break;
			}		
		} else {
			System.out.println("Customize Stats:");
			
			System.out.println("Str:");
			this.creation.strength = CombatEngine.getChoice(input, 4);
			
			System.out.println("Sta:");
			this.creation.stamina = CombatEngine.getChoice(input, 3);
			
			System.out.println("Agi:");
			this.creation.agility = CombatEngine.getChoice(input, 3);
			
			System.out.println("Luck:");
			this.creation.luck = CombatEngine.getChoice(input, 2);
			
			System.out.println("Heal:");
			this.creation.healing = CombatEngine.getChoice(input, 3);
			
			// now for the weapons
			System.out.println("What do they have equipped?");
			
			
			System.out.print("Weapon:\nName:");
			weapon.name = input.nextLine();
			
			System.out.println("Description:");
			weapon.description = input.nextLine();
			
			System.out.println("What dice does it roll?");
			weapon.damageDice = CombatEngine.getChoice(input, 12);
			
			System.out.println("Please enter a filename to save this weapon to:\n\t(Warning! This will overwrite any file with the same name!)");
			System.out.print("Resources\\Weapons\\");
			String wepPath = "Resources\\Weapons\\" + input.nextLine();
			
			System.out.println("Now Saving...");
			
			File wepSave = new File(wepPath);
			FileWriter wepWriter = new FileWriter(wepSave);
			
			build.toJson(weapon, wepWriter);
			
			wepWriter.close();
			
			
			System.out.print("Armour:\nName:");
			armour.name = input.nextLine();
			
			System.out.println("Description:");
			armour.description = input.nextLine();
			
			System.out.println("What is the base armour class it provides?");
			armour.defenceValue = CombatEngine.getChoice(input, 15);
			
			System.out.println("Please enter a filename to save this armour to:\n\t(Warning! This will overwrite any file with the same name!)");
			System.out.print("Resources\\Armours\\");
			String armPath = "Resources\\Armours\\" + input.nextLine();
			
			System.out.println("Now Saving...");
			
			File armSave = new File(armPath);
			FileWriter armWriter = new FileWriter(armSave);
			
			build.toJson(armour, armWriter);
			
			armWriter.close();
			
		}
		
		this.creation.maxHP = CombatEngine.rollX(10) + this.creation.stamina;
		
		this.creation.equipmentWeapon = weapon;
		this.creation.equipmentArmour = armour;
		
		System.out.println(this.creation.getBio());
		System.out.println("This is your character!");
		
		System.out.println("Please enter a filename to save this character as:\n\t(Warning! This will overwrite any file with the same name!)");
		System.out.print("Resources\\Characters\\");
		String path = "Resources\\Characters\\" + input.nextLine();
		
		System.out.println("Now Saving...");
		
		File save = new File(path);
		FileWriter writer = new FileWriter(save);
		
		build.toJson(this.creation, writer);
		
		writer.close();
		
		return this.creation;
	}

}
