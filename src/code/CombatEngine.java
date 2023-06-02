package code;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CombatEngine {
	
	public static Armour rollArmour(File directory, Gson build) throws Exception {
		// this method will pick a random file from the passed directory and return an armour object
		File item = randomFile(directory);
		FileReader reader = new FileReader(item);
		
		Armour result = build.fromJson(reader, Armour.class);
		reader.close();
		
		return result;
	}
	
	public static Weapon rollWeapon(File directory, Gson build) throws Exception {
		// this method will pick a random file from the passed directory and return an weapon object
		File item = randomFile(directory);
		FileReader reader = new FileReader(item);
		
		Weapon result = build.fromJson(reader, Weapon.class);
		reader.close();
		
		return result;
	}
	
	public static ItemConsumable rollConsumable(File directory, Gson build) throws Exception {
		// this method will pick a random file from the passed directory and return an consumable object
		File item = randomFile(directory);
		FileReader reader = new FileReader(item);
		
		ItemConsumable result = build.fromJson(reader, ItemConsumable.class);
		reader.close();
		
		return result;
	}
	
	public static File randomFile(File directory) {
		// this method will pick a random file from the passed directory and return it
		File[] files = directory.listFiles();
		File choice;
		
		int index = (int) (Math.random() * (files.length - 1));
		choice = files[index];
		
		return choice;
	}
	
	public static void prompt(Scanner input) {
		// this method allows the user to stagger the speed of the game.
		System.out.println("Press Enter to Continue...");
		if (!input.hasNextLine()) {
			input.nextLine();
		}
	}
	
	public static File getFile(Scanner input, File[] options) {
		// this method displays the files in the passed file array
		// then asks the user which one they want
		
		System.out.println("Choose Carefully!");
		
		File target;
		
		int choose;
		
		do {
			// display all the files in the options list
			File current;
			
			for (int q = 0; q < options.length; q++) {
				current = options[q];
				
				if(current.exists()) {
					System.out.print(" |" + (q + 1) + ". ");
					System.out.println(current.getName());
				}
			}
			
			choose = getChoice(input, options.length);

		} while (choose < 1 || choose > options.length);

		target = options[choose - 1];

		return target;
	}

	public static ItemConsumable getItem(Scanner input, Character user) {
		// this method displays a characters inventory and asks the user to select an item, which it returns
		ItemConsumable item = null;
		int choose;

		do {
			displayInventory(user);
			System.out.println(" |0. Wait!"); // give them the option to go back.
			choose = getChoice(input, user.inventory.length);

			// if they choose to go back, return null to signify a non-input.
			// WARNING! THIS NEEDS TO BE HANDLED!!
			// it is in my base code, but just for code documentation.
			if (choose == 0)
				return null;

		} while (choose < 1 || choose > user.inventory.length);

		item = user.inventory[choose - 1];

		return item;

	}

	// Making a targeting menu method because AAAAAAAAAAAAAAAAAAAAAAAAAA

	public static Character getTarget(Scanner input, Character[] userParty, Character[] otherParty, String reason) {
		// this method displays the characters in each party, and returns the character that the user selects
		Character[] targetParty;
		Character target;
		boolean ownTeam = false;
		int choose;

		do {
			if (ownTeam)
				targetParty = userParty;
			else
				targetParty = otherParty;

			displayParty(targetParty, "Choose Your Target to " + reason + ":", -1);
			System.out.println(" |" + (targetParty.length + 1) + ". Switch Teams");
			System.out.println(" |0. Wait!"); // give them the option to go back.
			choose = getChoice(input, targetParty.length + 1);

			// if they switch teams with by inputting higher than the length, flip ownTeam
			// and then continue;
			if (choose > targetParty.length) {
				ownTeam = !ownTeam;
				continue;
			}

			// if they choose to go back, return null to signify a non-input.
			// WARNING! THIS NEEDS TO BE HANDLED!!
			if (choose == 0)
				return null;

		} while (choose < 1 || choose > targetParty.length);

		target = targetParty[choose - 1];

		return target;

	}

	public static int checkForWin(Character[] one, Character[] two) {
		// this method checks to see if either of the passed parties (character arrays) have been defeated.

		boolean oneDefeated = true;
		boolean twoDefeated = true;

		for (int i = 0; i < one.length; i++) {
			if (one[i].isAlive) {
				oneDefeated = false;
				break;
			}
		}
		for (int j = 0; j < two.length; j++) {
			if (two[j].isAlive) {
				twoDefeated = false;
				break;
			}
		}

		if (twoDefeated && !oneDefeated)
			return 1;
		else if (oneDefeated && !twoDefeated)
			return 2;
		else if (!oneDefeated && !twoDefeated)
			return 0;
		else
			return -1;

	}
	
	public static int rollX(int X) {
		// pretty self explanatory.
		return (int) (Math.random() * (X - 1 + 1) + 1);
	}

	public static int roll20() {
		// rolls a 20-sided die
		return (int) (Math.random() * (20 - 1 + 1) + 1);
	}

	public static int getChoice(Scanner input, int cap) {
		// get's the users' integer choice between 0 and the passed cap.
		int choice = -1;

		do {
			System.out.print(" > ");
			if (input.hasNextInt()) {
				choice = input.nextInt();
			} else {
				System.out.println("Please enter a number");
				input.next();
			}
		} while (choice <= -1 || choice > cap);

		input.nextLine();

		return choice;
	}

	public static void displayInventory(Character target) {
		// displays a character's inventory
		System.out.println(target.name + "'s Items:");

		if (target.numberOfItems <= 0) {
			System.out.println("  |0. No Items!");
			return;
		}

		for (int i = 0; i < target.numberOfItems; i++) {
			if (target.inventory[i] != null) {
				System.out.print(target.inventory[i].type + "|");
				System.out.print(i + 1 + ". " + target.inventory[i].name);
				System.out.println("\tUses: " + target.inventory[i].uses);
			}
		}

	}

	public static void displayParty(Character[] party, String name, int self) {
		// displays a party of characters
		System.out.println(name);

		Character current;

		for (int p = 0; p < party.length; p++) {
			current = party[p];

			if (current.isAlive && p != self)
				System.out.print(" ");
			else if(p == self)
				System.out.print(">");
			else
				System.out.print("X");
			
			System.out.print("|" + (p + 1) + ". ");

			System.out.print(current.name + ": " + current.currentHP + "/" + current.maxHP + "\t[");

			for (int h = 1; h <= current.currentHP; h++)
				System.out.print("#");
			for (int n = 0; n < (current.maxHP - current.currentHP); n++)
				System.out.print(" ");

			System.out.println("]");
		}
	}

	public static void main(String[] args) throws Exception {
		// TODO set the max partysize here
		int PARTYCAP = 4;
		
		// start the GsonBuilder
		Gson build = new GsonBuilder().setPrettyPrinting().create();
		
		// TODO Initialize the resource directories
		// edit these if you need to.
		File importedCharacters = new File("Resources\\Characters\\");
		File importedWeapons = new File("Resources\\Weapons\\");
		File importedArmours = new File("Resources\\Armours\\");
		File importedConsumables = new File("Resources\\Consumables\\");
		
		// if any of them don't exist, make a new one.
		if(!importedCharacters.exists()) {
			importedCharacters.mkdir();
		}
		if(!importedWeapons.exists()) {
			importedWeapons.mkdir();
		}
		if(!importedArmours.exists()) {
			importedArmours.mkdir();
		}
		if(!importedConsumables.exists()) {
			importedConsumables.mkdir();
		}
		
		// Initialize a Scanner for user Input:
		Scanner input = new Scanner(System.in);
		
		// stop now if there are no characters
		File[] characters = importedCharacters.listFiles();
		
		// do they make a new character?
		boolean noChar = false;
		
		if(characters.length == 0) {
			
			System.out.println("No character files found in \'" + importedCharacters.getAbsolutePath() + "\'");
			noChar = true;
			
		} else {
			System.out.println("Want to make a new Character? (1/0)");
			if(getChoice(input, 1) == 1)
				noChar = true;
		}
		
		if(noChar == true) {
			CharacterCreator newCharacter = new CharacterCreator();
			newCharacter.wizard(input, build);
		}
		
		// ask how big each team will be
		System.out.println("Welcome! How large will Party 1 be?");
		Character[] team1 = new Character[getChoice(input, PARTYCAP)];
		System.out.println("And how large will Party 2 be?");
		Character[] team2 = new Character[getChoice(input, PARTYCAP)];
		
		// move onto character selection
		File selectedFile;
		int done;
		FileReader reader;
		
		// let player 1 select their team captain and members
		System.out.println("Alrighty then! Player 1, select Party 1's Captain!");
		selectedFile = getFile(input, characters);
		reader = new FileReader(selectedFile);
		team1[0] = build.fromJson(reader, Character.class);
		
		for(done = 1; done < team1.length; done++) {
			System.out.println("Now, select " + (team1.length - done) + " more characters for Party 1:");
			selectedFile = getFile(input, characters);
			reader = new FileReader(selectedFile);
			team1[done] = build.fromJson(reader, Character.class);
		}
		
		// player 2's turn
		System.out.println("Player 2, It's your turn! Select Party 2's Captain!");
		selectedFile = getFile(input, characters);
		reader = new FileReader(selectedFile);
		team2[0] = build.fromJson(reader, Character.class);
		
		for(done = 1; done < team2.length; done++) {
			System.out.println("Now, select " + (team2.length - done) + " more characters for Party 2:");
			selectedFile = getFile(input, characters);
			reader = new FileReader(selectedFile);
			team2[done] = build.fromJson(reader, Character.class);
		}
		
		reader.close();
		
		// Begin the encounter!
		
		System.out.println("Ready?");
		Thread.sleep(1500);
		System.out.println("3");
		Thread.sleep(500);
		System.out.println("2");
		Thread.sleep(500);
		System.out.println("1");
		Thread.sleep(500);
		System.out.println(" _______  __    _______  __    __  .___________. __  \r\n"
				+ "|   ____||  |  /  _____||  |  |  | |           ||  | \r\n"
				+ "|  |__   |  | |  |  __  |  |__|  | `---|  |----`|  | \r\n"
				+ "|   __|  |  | |  | |_ | |   __   |     |  |     |  | \r\n"
				+ "|  |     |  | |  |__| | |  |  |  |     |  |     |__| \r\n"
				+ "|__|     |__|  \\______| |__|  |__|     |__|     (__) \r\n"
				+ "                                                     ");
		Thread.sleep(1000);
		
		
		// at this point, neither team has won.
		int winner = 0;

		// make some variables to keep track of the menus
		boolean confirmed;
		boolean back = false;
		boolean beginning = true;

		// there is no target at the moment
		Character target = null;
		Character current;

		// these are for attacking
		boolean critical;
		int hitRoll;
		int toHit;
		int damage;
		
		// team1 will go first

		Character[] currentParty = team1;
		Character[] opponentParty = team2;
		
		//BEGIN!

		while (winner == 0) {
			
			System.out.println(currentParty[0].name + "'s Team is Going!");

			int characterTurn = 0;

			while (characterTurn < currentParty.length) {
				confirmed = false;
				back = false;
				
				// Who's turn is it?
				current = currentParty[characterTurn];
				
				if(beginning == true) {
					
					if(!current.isAlive) {
						System.out.println(current.name + " is still unconcious!");
						prompt(input);
						characterTurn++;
						continue;
					}
					
					System.out.println(current.name + "'s Turn!");

					// if the current party member is tanking, they end their tank.
					if (current.tanking)
						System.out.println(current.name + " has stopped tanking hits.");

					current.beginTurn();
					
					if (current.boosted)
						System.out.print("The booster \'" + current.currentBoost.name + "\' will be effective for: " + current.turnsBoosted + " more turns!\n");
				}


				displayParty(currentParty, "Your Team", characterTurn);
				displayParty(opponentParty, "Their Team", -1);
				System.out.println("What will " + current.name + " do?");
				System.out.println(" |1. Bash");
				System.out.println(" |2. Goods");
				System.out.println(" |3. Action");
				System.out.println(" |4. Battle Info");
				System.out.println(" |0. Nothing");
				
				int decision = getChoice(input, 4); // what will they do?

				switch (decision) {

				case 1:
					// BASH!
					// okay, first it'll ask for what target you want.

					confirmed = false;
					
						do {
							
							target = getTarget(input, currentParty, opponentParty, "Attack");
							// if this method returns null, the user would like to go back.
							if(target == null) {
								back = true;
								break;
							}
							// find the user's target for that action.
							
							// you can't attack an unconcious person.
							if(!target.isAlive) {
								System.out.println("That person is unconcsious!");
								prompt(input);
								back = true;
								break;
							}
							
							
							// Do they really want to check that one?
							confirmed = false;
							System.out.println("Attack " + target.name + " with " + current.equipmentWeapon.name + "(+" + current.equipmentWeapon.attackBonus + ") at " + current.equipmentWeapon.diceMultiplier + "d" + current.equipmentWeapon.damageDice + " + " + current.equipmentWeapon.damageBonus + "?");
							System.out.println(" |1. Yes!");
							System.out.println(" |0. Wait!");
							decision = getChoice(input, 1);
	
							if (decision == 1)
								confirmed = true;
							else
								continue; // hop back to the beginning and ask for the target again.

						} while (confirmed == false); // keep asking until they pick something!

						if (back == true)
							continue; // head back to the main menu.

					// now that they've chosen a target, check to see if their attack roll will hit.
					critical = false;

					hitRoll = roll20();
					toHit = current.agility + hitRoll + current.equipmentWeapon.attackBonus;

					System.out.println("Rolled " + hitRoll + " to hit, +" + current.agility + " = " + toHit);

					if (hitRoll == 20) {
						System.out.println("CRITICAL HIT!");
						critical = true;
					} else if (toHit >= target.AC) {
						System.out.println("Hits!");
					} else {
						System.out.println("Misses...");
						break; // okay, if I just break, I don't even need a hit variable.
					}

					// calculate damage for the hit.
					damage = current.equipmentWeapon.rollDamage();

					if (critical)
						damage *= 3; // MASSIVE critical damage

					System.out.println("Rolled " + damage + " Damage,");
					System.out.println("Dealt " + target.damage(damage) + " Damage!");
					prompt(input);

					if (!target.isAlive) {
						System.out.println(target.name + " was defeated!");
						prompt(input);
						System.out.println(current.name + " levels up!");
						
						System.out.println(current.levelUp(target));
						prompt(input);
						
					}
					
					break;

				case 2:
					// Goods menu!
					// this should display the inventory of the current player. Maybe that should be
					// a character-specific method?

					// first check if they even have any items
					if (current.numberOfItems == 0) {
						System.out.println(current.name + " has no goods!");
						continue;
					}

					// okay, they DO have some consumable items
					ItemConsumable item = getItem(input, current);
					// go back to main menu if they pick 0.
					if(item == null)
						continue;
					
					do {
						
						target = getTarget(input, currentParty, opponentParty, "Use \'" + item.name + "\' On");
						// if this method returns null, the user would like to go back.
						if(target == null) {
							back = true;
							break;
						}
						// find the user's target for that item.
						
						// Do they really want to use it on that one?
						confirmed = false;
						System.out.println("Use " + item.name + " on " + target.name + "?");
						System.out.printf("%-15s %n", "Description:\n\t" + item.effectDescription);
						System.out.println(" |1. Yes!");
						System.out.println(" |0. Wait!");
						decision = getChoice(input, 1);

						if (decision == 1)
							confirmed = true;
						else
							continue; // hop back to the beginning and ask for the target again.

					} while (confirmed == false); // keep asking until they pick something!

					if (back == true)
						continue; // head back to the main menu.
					
					// now that they've decided, let's do it!

					System.out.println("Using " + item.name + " on " + target.name);

					if (item instanceof ItemHealing) {
						ItemHealing usedItem = (ItemHealing) item;
						System.out.println("Used " + item.name + " on " + target.name + "! Healed " + usedItem.use(current, target) + " damage! " + usedItem.uses + " Uses left!");
					} else if (item instanceof StatBooster ) {
						StatBooster usedItem = (StatBooster) item;
						System.out.println(current.name + " will be boosted for " + usedItem.boost(target) + " turns.");
						System.out.println("Used " + item.name + " on " + target.name + "! " + usedItem.uses + " Uses left!");
					}

					// end the turn.
					break;
				
				case 3:
					// ACTION!
					// do all kinds of stuff!

					System.out.println("Availible Actions:");
					System.out.println(" |1. Tank");
					System.out.println(" |2. Heal");
					System.out.println(" |0. Wait!");
					// TODO // add more actions, like assist, dodge, finisher, etc.
					// Unique moves are added via each character.
					
					int actChoice = getChoice(input, 2);

					if (actChoice == 0)
						continue; // go back to the main menu.

					switch (actChoice) {
					// what action would you like?

					case 1:
						// TANK
						// the tank option just allows the character to reduce damage taken for the rest
						// of the opponent's turn.
	
						confirmed = false;
						System.out.println(current.name + " will begin tanking incoming damage? (Lasts 1 turn)");
						System.out.println(" |1. Yes!");
						System.out.println(" |0. Wait!");
						decision = getChoice(input, 1);
	
						if (decision == 1) {
							current.tank();
							break; // leave the switch and end the turn, I think;
						} else
							continue; // hop back to the beginning and ask for the choice again.
						
					case 2:
						// healing!
						// okay, first it'll ask for what target you want.

						confirmed = false;
						
						do {
							
							target = getTarget(input, currentParty, opponentParty, "Heal");
							// if this method returns null, the user would like to go back.
							if(target == null) {
								back = true;
								break;
							}
							// find the user's target for that action.
							
						
							// Do they really want to heal that one?
							confirmed = false;
							System.out.println("Heal " + target.name + " for 1d8+" + current.healing + "?");
							System.out.println(" |1. Yes!");
							System.out.println(" |0. Wait!");
							decision = getChoice(input, 1);
	
							if (decision == 1)
								confirmed = true;
							else
								continue; // hop back to the beginning and ask for the target again.

						} while (confirmed == false); // keep asking until they pick something!

						if (back == true)
							continue; // head back to the main menu.

						// now that they've chosen a target, heal that boi up
						if(!target.isAlive) {
							target.currentHP = 1 + current.healing;
							target.isAlive = true;
							System.out.println("Revived " + target.name);
						} else {
							int heal = rollX(8) + current.healing;
							target.damage(-heal);
							System.out.println(target.name + " was healed for " + heal);
						}
						break;
						
						
					//TODO this is a debug /kill command
					case 3:
						target = getTarget(input, currentParty, opponentParty, "/kill");

						target.damage(target.currentHP);
						System.out.println(current.levelUp(target));
						
						break;
					}
					break;
					
					
				case 4:
					// Get info on the players, items, battle state, etc. etc.
					target = getTarget(input, currentParty, opponentParty, "Check Info On");
					// if this method returns null, the user would like to go back.
					if(target == null) {
						continue;
					}
					// find the user's target for that action.
					
					System.out.println(target.getBio());
					
					// let 'em read it before zoomin' on.
					prompt(input);
					continue;
				}
				// next character's turn.
				prompt(input);
				characterTurn++;
				beginning = true;
			}
			
		// whew! After the whole party has gone, we switch who's the currentParty and
		// opponentParty.

		if (currentParty == team1) {
			currentParty = team2;
			opponentParty = team1;
		} else {
			currentParty = team1;
			opponentParty = team2;
		}

		// now loop until someone is DEAD!
		winner = checkForWin(team1, team2);
		}
		
		Character[] winners;
		
		if(winner == 1)
			winners = team1;
		else
			winners = team2;
			
		
		// roll for a loot drop on I guess the loot table?
		
		boolean wepSafe = true;
		boolean armSafe = true;
		boolean conSafe = true;
		
		
		if(importedWeapons.list().length == 0) {
			System.out.println("No weapon loot files found in \'" + importedWeapons.getAbsolutePath() + "\'");
			wepSafe = false;
		}
		if(importedArmours.list().length == 0) {
			System.out.println("No armour loot files found in \'" + importedArmours.getAbsolutePath() + "\'");
			armSafe = false;
		}
		if(importedConsumables.list().length == 0) {
			System.out.println("No consumable loot files found in \'" + importedConsumables.getAbsolutePath() + "\'");
			conSafe = false;
		}
		
		if(!wepSafe && !armSafe && !conSafe)
			System.out.println("Go get some loot files! For now, you're good to go!");
		else
			System.out.println("\nCongratulations to " + winners[0].name + "\'s team! Now loot for everyone!");
		
		Character currentLooter;
		
		for(int l = 0; l < winners.length; l++) {
			currentLooter = winners[l];
			currentLooter.endBattle();
			
			System.out.println(currentLooter.name + ", You got:");
			
			int type = rollX(3);
			int pick;
			
			switch(type) {
			
			case 1:
				if(wepSafe) {
					Weapon newWep = rollWeapon(importedWeapons, build);
					System.out.println("\t" + newWep.name + "\n\t" + newWep.diceMultiplier + "d" + newWep.damageDice + "\n\t" + newWep.description);
					System.out.println("\nDo you take the " + newWep.name + "?");
					System.out.println(" |1. Yes!");
					System.out.println(" |0. No.");
					pick = getChoice(input, 1);
					
					if (pick == 0) {
						System.out.println("Didn't take it.");
					} else {
						currentLooter.equipmentWeapon = newWep;
						System.out.println("Took it!");
					}
				} else
					System.out.println("\tNothing!");
				
				break;
			case 2:
				if(armSafe) {
					Armour newArm = rollArmour(importedArmours, build);
					System.out.println("\t" + newArm.name + "\n\t" + newArm.defenceValue + " + " + currentLooter.agility + "\n\t" + newArm.description);
					System.out.println("\nDo you take the " + newArm.name + "?");
					System.out.println(" |1. Yes!");
					System.out.println(" |0. No.");
					pick = getChoice(input, 1);
					
					if (pick == 0) {
						System.out.println("Didn't take it.");
					} else {
						currentLooter.equipmentArmour = newArm;
						System.out.println("Took it!");
					}
				} else
					System.out.println("\tNothing!");
				
				break;
			case 3:
				if(conSafe) {
					ItemConsumable newCon = rollConsumable(importedConsumables, build);
					System.out.println("\t" + newCon.name + "\n\tUses: " + newCon.uses + "\n\tPotency: " + newCon.potency + "\n\t" + newCon.description);
					System.out.println("\nDo you take the " + newCon.name + "?");
					System.out.println(" |1. Yes!");
					System.out.println(" |0. No.");
					pick = getChoice(input, 1);
					
					if (pick == 0) {
						System.out.println("Didn't take it.");
					} else {
						currentLooter.giveItem(newCon);
						System.out.println("Took it!");
					}
				} else
					System.out.println("\tNothing!");
				
				break;
			}
			
			System.out.println("Select where to save:");
			selectedFile = getFile(input, characters);
			FileWriter save = new FileWriter(selectedFile);
			build.toJson(currentLooter, save);
			save.close();
		}
		input.close();
	}
}