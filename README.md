Hello! Welcome to the
Advanced
Combat
Engine
```	 _______  _______  _______ 
(  ___  )(  ____ \(  ____ \
| (   ) || (    \/| (    \/
| (___) || |      | (__    
|  ___  || |      |  __)   
| (   ) || |      | (      
| )   ( || (____/\| (____/\
|/     \|(_______/(_______/
``` 
This will be your guide through making your own custom characters and items!

So, to begin, Characters, Armours, Weapons, and Consumable Items are stored as .txt files in the Resources folder.

It's pretty easy to figure out which type goes where. First, let's go over Armours, which are the simplest.

Example:
```json
{
  "defenceValue": 10,
  "name": "Example Armour",
  "description": "An Example Armour"
}
```

It's that easy. Paste that bad boy into a text editor, and save it in the Resources\Armours\ folder and BOOM! You've got yourself your first loot drop.

Yaaaaaaaaaaaay!

Weapons are very similar:
```json
{
  "name": "Example Weapon",
  "description": "This is an Example Weapon",
  "diceMultiplier": 1,
  "damageDice": 6,
  "attackBonus": 0,
  "damageBonus": 0
}
```

The dice multiplier and damage dice need some explanation.

If the character's attack consists of rolling a 20-sided die and then rolling for damage, the formula looks like this

Attack roll = (1-20) + character's agility + attackBonus
Damage roll = ( diceMultiplier * (1-damageDice) ) + character's strength + damageBonus

It's pretty simple.

Characters are a lot more complicated.
(Hint: If you don't want to make super detailed characters, just use the built-in wizard to make one and then edit it if you want)

```json
{
  "name": "John Doe",
  "isAlive": true,
  "backstory": "An example Character",
  "maxHP": 5,
  "currentHP": 1,
  "strength": 1,
  "agility": 1,
  "stamina": 1,
  "luck": 1,
  "healing": 1,
  "AC": 0,
  "equipmentWeapon": {
    "name": "Example Weapon",
    "description": "This is an Example Weapon",
    "diceMultiplier": 1,
    "damageDice": 6,
    "attackBonus": 0,
    "damageBonus": 0
  },
  "equipmentArmour": {
    "defenceValue": 10,
    "name": "Example Armour",
    "description": "An Example Armour"
  },
  "inventory": [
    null,
    null,
    null,
    null,
    null,
    null,
    null,
    null,
    null,
    null
  ],
  "numberOfItems": 0,
  "tanking": false,
  "boosted": false,
  "turnsBoosted": 0
}
```

As you can see, Characters are a bit more complex. But, if you'll notice, we can see our friends Example Armour and Example Weapon in there!

Each Character can be boiled down into this:

A name
A backstory
Strength
Stamina
Agility
Healing
Luck
MaxHP
