#################################
#                               #
#  Legends: Heroes and Monster  #
#  Game By: Vitor Vicente       #
#  Assignment #3                #
#  CGS CS611 Spring '21         #
#                               #
#################################


#########################
#                       #
#  Compilation/Running  #
#                       #
#########################

- Compile (on Windows, in the original folder): javac *.java */*.java */*/*.java */*/*/*.java */*/*/*/*.java
- Run (on Windows, in the original folder): java Main.java



#######################
#                     #
#  Class Description  #
#                     #
#######################

- Main - Access Class for the game
- README - This file which holds class descriptions & an essay length Implementation Detailing (sorry, I'm excited about what I made)
- /Game/
       - LegendsGame - Main LegendsGame Object (extends Game Abstract Class), includes basic playing
       - LegendsHero - Main Hero Object Class (extends LegendsEntity Abstract class), includes basic functionality/properties for Heroes
       - LegendsItem - Abstract Class for all types of Items in the game, includes basic functionality for every Item Type
       - LegendsMap - Main Map Object Class, implements Map functionalities like moving, etc...
       - LegensMonster - Main Monster Object Class (extends LegendsEntity Abstract Class), includes basic functionality/properties for Heroes
       - LegendsPlayer - Main Player Object Class (extends Player Class, which extends Entity Abstract Class), includes basic functionality for PLAYERS, clearly distinct from Heroes!
       - /Creation/
                  - HeroCreation - Class with the Static Method to create a randomized Hero, randomizing name, stats, class, etc...
                  - ItemCreation - Class with the Static Method to create a randomized Item, randomizing name, type, subtype, class, stats, etc...
                  - MapCreation - Class with the Static Method to create a randomized Tile, randomizing type, properties etc..
                  - MonsterCreation - Class with the Static method to create a randomized Monster, randomizing name, stats, class, etc...
       - /Util/
              - LegendsEntity - Abstract Class with basic functionality for all game Entities (Heroes/Monsters)
              - LegendsGameEntities - Object that represents all Playable Entities (Players) in the game (extends GameEntities Abstract Class), separated from just a regular ArrayList for simplicity of access
              - LegendsGamePlayerHeroes - Object that represents all Heroes played by a single Player in the game, separated from a regular ArrayList for simplicity of access
              - LegendsMapTile - Abstract Class with basic functionality for all map tiles (marketplace/common/inaccessible/etc..)
              - LegendsPlayerGameStats - Optional class to record all meta-game statics for a specific Player
              - /Creation/
                         - NameListsArmour - Class with the Static Variables with all names for specific types/levels of Armour
                         - NameListsEntities - Class with the Static Variables with all names for specific types/levels of Entities
                         - NameListsPotions - Class with the Static Variables with all names for specific types/levels of Potions
                         - NameListsWeapons - Class with the Static Variables with all names for specific types/levels of Weapons
                         - NameListsSpells - Class with the Static Variables with all names for specific types/levels of Spells
                         - Names - Class with the Static Methods to get randomized names for each specific type of Entity/Item
              - /Hero/
              		 - LegendsHeroArmour - Class object that holds/maintains the Armour a Hero is currently using
              		 - LegendsHeroGameStats - Class object that holds/maintains meta-game statistics for a Hero (Kills, Deaths, etc..)
              		 - LegendsHeroHeroStats - Class object that holds/maintain in-game statistics for a Hero (HP, Mana, etc..)
              		 - LegendsHeroInventory - Class object that holds/maintains the infinite list of non-spell items a hero can have
              - /Monster/
                        - LegendsMonsterGameStats - Class object that holds/maintains meta-game statistics for a Monster (this is currently not used, and sounds redundant, but it could be implemented for statistical exploration in the future)
                        - LegendsMonsterMonsterStats - Class object that holds/maintains in-game statistics for a Monster (this is currently not used, and sounds redundant, but it could be implemented for statistical exploration in the future)
- /Classes/
          - LegendsEntityClass - Abstract Entity Class object that holds/maintains/sets out the basis for an Entity Class
          - LegendsHeroClass - Class object for Hero classes (extends LegendsEntityClass), maintains/holds specific Hero class details
          - LegendsMonsterClass - Class object for Monster classes (extends LegendsEntityClass), maintains/holds specific Monster class details
          - /Heroes/
                   - Paladin - Class object for specific details of Paladin Class (extends LegendsHeroClass)
                   - Sorcerer - Class object for specific details of Sorcerer Class (extends LegendsHeroClass)
                   - Warrior - Class object for specific details of Warrior Class (extends LegendsHeroClass)
          - /Monsters/
                     - Dragon - Class object for specific details of Dragon Class (extends LegendsMonsterClass)
                     - Exoskeleton - Class object for specific details of Exoskeleton Class (extends LegendsMonsterClass)
                     - Spirit - Class object for specific details of Spirit Class (extends LegendsMonsterClass)
- /Items/
        - Armour - Class object for specific Armour items (extends LegendsItem), holds and maintains details (type, etc...) specific to the Armour item type
        - Potion - Class object for specific Potion items (extends LegendsItem), holds and maintains details (type, etc...) specific to the Potion item type
        - Spell - Class object for specific Spell items (extends LegendsItem), holds and maintains details (type, etc...) specific to the Spell item type
        - Weapon - Class object for specific Weapon items (extends LegendsItem), holds and maintains details (type, etc...) specific to the Weapon item type
        - /Util/
               - ArmourType - Class object for ArmourTypes, holds specific type details for Armour objects, such as maxDurability, and the defense multiplier
               - PotionType - Class object for PotionTypes, holds specific type details for Potion objects, such as targetAbility, and the multiplier
               - SpellType - Class object for SpellType, holds specific type details for Spell objects, such as targetAbility, and the multiplier
               - WeaponType - Class object for ArmourTypes, holds specific type details for Weapon objects, such as maxDurability, and the attack damage
- /Tiles/
        - Common - Class object for Common tiles (extends LegendsMapTile), holds/maintains all the needed logic to run hero/monster encounters and their repercussions 
        - Inaccessible - Class object for Inaccessible tiles (extends LegendsMapTile), literally holds no info outside of the Abstract Requirements, exists only for stopping heroes from moving onto it
        - Marketplace - Class object for Marketplace tile (extends LegendsMapTile), holds/maintains all the needed logic to run hero markets, also has a memory encoded to keep track of sold/bought items
- /Util/
       - Entity - Transfered class from previous assignments, abstract class used to set out major details for both Players and Teams
       - Game - Transfered class from previous assignments, abstract class used to set out major details for any and all games (including this one)
       - GameEntities - New Util Abstract Class, that should have been in previous assignment, holds an object with all the current entities at game, makes things easier to deal with
       - Player - Transfered class from previous assignments (extends Entity), this class holds the basics for every kind of player
       - Random - New Util class, holds two basic random generators that are used everywhere in the code
       - Team - Transfered class from previous assignments (extends Entity), this class holds the basics for every kind of entity team, not used in this assignment as of now.
       
       
       
################################
#                              #
#  Implementation Description  #
#                              #
################################


	This is going to be a little bit of a long-winded description, I might have gone overboard with stuff, but hopefully not. Basic idea of the game is the same, a _single_ Player controls a team of Heroes, going around the map, beating Monsters, buying stuff from Markets, and just forever playing!
	
	The Player has been extended from my previous implementations to be LegendsPlayer, which not includes both the regular Player details, a collection of statistics PlayerGameStats, and a collection of Player Heroes LegendsGamePlayerHeroes. The Game Statistics collect Kills, Deaths, Items Bought and Sold, Tiles Moved, Times Revived, and how many Friendlies they Revived, as well as the amount, and a list, of Heroes Used, all of this was put into a single Object to make coding/reading/using clearer. The LegendsGamePlayerHeroes is the independent Object that manages in full all of the Heroes the Player uses, again made separate for easier coding/reading/using. The amount of Maximum Heroes can also be changed pretty easily.
	
	Before we get to Heroes, let's tackle something else, I created an abstract LegendsEntity class, since both Heroes and Monsters share a lot of functionality including names, IDs, and a class. Heroes themselves extend this class to include a bunch of things! Firstly, like the Player, they have a LegendsHeroGameStats object, which keeps track of all the meta-game stats (kills, deaths, tiles moved, items bough and sold, friendlies revived, and amount of times revived). Further Hero's have a LegendsHeroHeroStats object, which collects and holds all the in-game stats, such as HP, Mana, Level, Exp, Coins, Dexterity, Strength, Agility, the class also includes methods for proper manipulation of these stats. Next in the Hero we have the LegendsHeroClass, which is an extension of the Abstract Class LegendsEntityClass, which in itself holds some of the shared info between Hero Classes and Monster Classes. The Hero Class Object itself holds the boosts to Hero Abilities, as discussed in the description of the game, each Class, which we'll get to in a bit, has one or more preferred abilities, so these boosts reflect that. There are 3 "Implementations" of the Hero Class, Paladin, Sorcerer, and Warrior, each of which boosts 2 abilities by 5%. Going back to the Hero, we have a few more things to discuss, firstly the LegendsHeroInventory, which is basically an object that includes methods to hold and maintain the possibly infinite set of items a Hero can have on him. Secondly, the LegendsHeroArmour, which is an object that holds and maintains the 4 different active Armour pieces each of which holds a specific slot (Head, Chest, Legs, Feet), this is an extension of what was required, but this way made more sense to me, they act almost as a whole when defending. Thirdly we have the Equipped Weapon, which is just a single Weapon and not an object, and represents the current active Weapon. Finally we have the Known Spells, which is an ArrayList (and not an object, although in the future this can change), of Spells the Hero knows, later we'll get to why Spells are set apart from the other Inventory Items.
	
	After Heroes we have the Monsters implementation, this class shares the extension of the LegendsEntity Abstract Class, and includes three major things outside the Abstract requirements, a LegendsMonsterClass object, a LegendsMonsterMonsterStats Object, and a LegendsMonsterGameStats object. The LegendsMonsterClass object is an extension of the LegendsEntityClass Abstract Class, that includes methods to hold/maintain the boosts to the Monster's three abilities, these work just as the ones for the Heroes did, except for Strength, Defense, and Dodge. The LegendsMonsterMonsterStats, just as its Hero counterpart, maintains and holds all the required in-game stats (HP, Defense, Strength, Dodge, Exp, and Level), the only one here that wasn't required was Exp and it currently doesn't make much of a difference but in the future it could be implemented. The Final object also shares most of its properties with its Hero counterpart, LegendsMonsterGameStats maintains all the meta-game stats for Monsters (Kills & Assists), these are purely for records sake and nothing else.
	
	With entities out of the way, let us look at our Item Implementation. All items extend the Abstract Class LegendsItem, which has the basics required, including Name, ID, Cost, and Minimum Level to use. Then each Item Type has its own Object. To start let us look at Armour, Armour items have three distinct properties outside of the ones shared by all items: Slot, ArmourType, durability. Slot represents in which of the four armour slots it goes: Head, Chest, Legs, Feet. As explained before, this is an extension to the original idea, that allows for further flexibility/realism in the game, and also allows for a sharing of the brunt of the damage, since armour takes damage, each piece takes up some of the damage, with Chest pieces taking the brunt of it, followed by Legs, Head, and finally Feet. Put together, they contribute to the defense of the player by averaging the defense multipliers of each. The ArmourType is the object that holds and maintains all the details on the specific armour piece, including its multiplier and max durability, these are generated in a specific way which I'll get to later. They also both have a theoretical limit to sort of maintain normality. The final detail, the durability, is also an addition to the base game, I thought this would bring more realism, and make players want to switch armour more, durability can be lost, and regened, although nothing in the game yet does this (perhaps a future BlackSmith tile could), if armour breaks it is deleted from the inventory/playerarmour. The second item type is weapon, a weapon is used for direct attack of monsters, each weapon holds two properties, durability, which works much like the armour, and goes down with use; and WeaponType, which is a object that holds specific details about the type of the weapon, including the damage it gives, the max durability it has, and whether or not it is dual handed (although this last property has little effect as of yet). Both the damage and max durability have hard max caps, to keep things balanced (or try to...). 
	
	The next two item types are Potions and Spells, and as described, both affect some ability, whether on Monsters or Heroes, and Spells also do damage. However, in an extension of what was required, now both Potions and Spells can be cast on Monsters, or on the Hero. When cast on the monsters, different types of potions/spells affect each of their abilities in a negative way, according to a multiplier that is kept in both PotionType and SpellType (so is the target ability). When used on a Hero, the buff the Hero's abilities, again each has a target ability and multiplier kept by each potion/spell type. Potions have the trick of being one-off items, which disappear after use, while Spells are permanent, and once learnt (or well bought...) they cannot be sold or lost, since knowledge cannot be thrown away (or at least I'd like to think so, but that's for philosophers to decide). Spells on the other hand also damage monsters when used against them (when used on the Hero they deal no damage), this damage has a minimum and maximum value which are stored in the SpellType, and the variation is determined by the level of the Hero.
	
	With all this out of the way let's get to the map, tiles and gameplay. The game has a map, which is an instance of LegendsMap, which holds and maintains all information about the different tiles that make up the Game World. These tiles are generated randomly, but I'll get to generation later. The crucial detail is that each of them is an extension of the abstract class LegendsMapTile. There are three extensions as of now, Marketplace, Common, Inaccessible. Each tile holds in it an onEntry and onExit method, which run everything that occur inside the specific tiles. The game object only manages the moving between tiles, and not what happens within (and even this is assisted by the LegendsMap object). Inaccessible tiles are the simplest, they do nothing, and Heroes can't go into them at all, they are represented on the map with an "I".
	
	Marketplace tiles represent Markets the Heroes can interact with, buying every kind of item, and selling every kind except Spells. The Marketplace tile object holds, besides the required details from the Abstract class a list of items they have for sale. This list is originally created upon the creation of the tile (again I'll get to generation later), but if it ever becomes empty, new items are generated to refill it! Furthermore, a Marketplace maintains its memory, so if I sell a sword to a specific Marketplace, it will be available for purchase later (and fully repaired which is a neat trick). Items are sold by their stated price, and bough by the market at half price (which means technically you can fix items by selling them and buying them, which is a little hidden feature).
	
	The Common tile holds the heart of the gameplay logic, the encounters (or battles). Upon creation, a tile gets a random set of Monsters (generation details later), but if it ever runs out, new ones will be given to it, thus assuring an infinite gameplay experience! The encounters work mostly as expected, upon starting, each Hero is assigned a Monster (and vice-versa), and each round the Hero gets to make a move, being able to attack using the equipped weapon, casting a spell, accessing their inventory (they can switch weapons, armour, and use potions here), or printing information (CAREFUL THOUGHT! This will waste the Heroes turn, making it a strategic move), or revive another Hero, at the start of a specific combat event both the current Hero and Monster details are printed just in case. After the Hero makes their move, the Monster will just blindly attack the Hero (because why not, but in the future it'd be fun to implement some more 'AI' functionalities). This will go on until one of the two for any specific encounter is dead, in which case the winner is put into a special list of "unmatched" entities. For simplicities sake, and to make gameplay fair, I did not allow for 2v1, 3v2, 3v1. Instead the Hero/Monster will just wait until a new opponent is freed from another fight, or all fights end and their side wins (ie: if all Heroes beat their Monsters there is never a redistribution, However if Hero 1 beats Monster 1, and Monster 2 beats Hero 2, then Hero 1 and Monster 2 will fight), both entities keep their current status between these battles. Heroes regen 10% of health and mana after each round, if a Hero is revived, it gains back all its health and mana, and gets put into the waiting list until it can find a monster. Once the encounter is completely over, one of three things could have happened, the player quit the game, in which case the game just ends; the monsters won, in which case the game ends (no saves, so gameover is gameover); or the players won, in which case they regen everything and exit the tile, being prompted to move again. All other battle functionalities are as described in the project requirements doc given to us.
	
	
	Now for the really fun part, since I wanted to make this game highly replayable, I implemented a complete random generator for EVERYTHING!! Lets go over each of the generators: Heroes, Monsters, Items, Tiles.
	
	The Hero generator is relatively simple, it is a static method (as are all the generators) that takes in an integer, with the amount of heroes of each class to create. Then it creates a new hero of that class with pseudo random stats (the reason for pseudo random here is because of the rules regarding leveling up and ability increasing which were given, ie: the abilities have a base random value, and increase by either 5% or 10% depending on class preferance, per each level the hero gets). All heroes get a random level, HP and mana based on the level, randomized number of coins that is also level dependent, an empty inventory, known spells, and armour objects, new game stats, an entityID that is kept as a static variable in the LegendsGame class, so to be incremented and not to cause any entity equality crashes. Finally, each hero gets a random name or name and title, depending on their class, these names came from either https://www.fantasynamegenerators.com/, pop culture references, or my imagination, and are kept in static variable lists in classes under Game.Util.Creation. The Monster Generator is actually incredibly similar, generating a list of Monsters containing a certain amount of monsters from each class, randomizing stats based on level, and giving them a name, or name plus title also relating to their class. Again the same thing applies for the origin of the names, and where they are kept (this took me like 3+ hours to do, but I was too excited not to do it, sorry).
	
	The Item generator is a big more complex, since the ItemTypes have subtypes in itself, and so make this a lot more messy, they also have a lot of MUCH MORE different properties. Granted with a few more days I could have probably figured out a better way, but for now I don't think it's too bad. Again the generator has a static method that returns a list with set amount of each item type. Each item type than has its own static creation method, which creates and attributes properties to the item. All properties are associated with the item multiplier. The higher the multiplier, the higher the level, the better the item. Stats, and even names, are attribute related to the multiplier. For each item type, a randomizer is also ran to decide on one subtype, and then the item is created (example Armour slots, Weapon dualHandedness, Potion and Spell target ability). ID's are kept similarly to the entities, by using a single shared static variable, to assure that there are no collisions.
	
	Finally we have the map generator, which is also a static method that returns a list of tiles, which are split into the three types of cells based on a configured percentage. The method begins by assessing how many of each type to generate, and go through a little process to assure that the exact amount of total tiles is generated, as not to cause problems. After that the generator calls each sub-generator to get a list of the determined length of that type of tile, creating the tiles using 0,0 coords, since these are set later by the map class, and giving them IDs in the same fashion as entities and items. Marketplace tiles call the item generator the first time they are created, and subsequently when they run out of items, and the same is said about Common tiles and the Monster Generator.
	
	These generators might seem like such a small thing, but they increase playability by a tone, and really make the game be fair more lively than it was before. Further I coded them in a way that it would be easy to change configurations. This was an absolute blast to code, although it was by far NOT EASY.
	
	
	
	
###################
#                 #
#  Personal Note  #
#                 #
###################

	This assignment was a blast, and honestly it made me want to code even more, I really want to create a Blacksmith tile, and extend both Monsters and Heroes classes, add more weapon functionalities, and fix up the Item Types logic, sadly I ran out of time to do these things, but since they are all extraneous to the assignment requirements, this is it for now... (insert wondering eyes) I'm sorry for the huge essay description, I hope you enjoy playing, piece of advice, get a weapon quick!!!