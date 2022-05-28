# Assignment Details

**Title**: Legends Of Valor

**Class**: CGS CS 611

**Semester**: Spring '21

**Authors**:
- Jack Giunta
- Victoria-Rose Burke
- Victor Vicente



## How to Run

**WARNING** This code makes use of the _latest_ version of java and the JDK, and it might not run properly on older versions!

Compilation (On Windows, in the original folder): javac *.java

Run (On Windows, in the original folder): java Main.java



## Class Description

- Main.java - Access Class for the game
- README.MD - This file, which holds compiling/running details, class descriptions, and an essay length Implementation Detailing
- /Entities/
  - LegendsEntity.java - Abstract Entity Class with basic functionality for all game Entities (Heroes/Monsters)
  - LegendsHero.java - Main Hero Object Class (extends LegendsEntity Abstract Class), includes basic functionality/properties for Heroes
  - LegendsMonster.java - Main Monster Object Class (extends LegendsEntity Abstract Class), includes basic functionality/properties for Monsters
  - LegendsPlayer.Java - Main Player Object Class (extends Player Class), includes functionality for PLAYERS, clearly distinct from Heroes
  - /Classes/
    - LegendsEntityClass.java - Abstract Entity Class Object that holds/maintains/sets out the basis for an Entity Class
	- LegendsHeroClass.java - Class Object for Hero classes (extends LegendsEntityClass Abstract Class), maintains/holds specific Hero class details
	- LegendsMonsterClass.java - Class Object for Monster classes (extends LegendsEntityClass Abstract Class), maintains/holds specific Monster class details
	- /Instances/
	  - Dragon.java - Class Object for specific details of Dragon Monster Class (extends LegendsMonsterClass Class)
	  - Exoskeleton.java - Class Object for specific details of Exoskeleton Monster Class (extends LegendsMonsterClass Class)
	  - Paladin.java - Class Object for specific details of Paladin Hero Class (extends LegendsHeroClass Class)
	  - Sorcerer.java - Class Object for specific details of Sorcerer Hero Class (extends LegendsHeroClass Class)
	  - Spirit.java - Class Object for specific details of Spirit Monster Class (extends LegendsMonsterClass Class)
	  - Warrior.java - Class Object for specific details of Warrior Hero Class (extends LegendsHeroClass Class)
  - /Util/
    - LegendsEntityStats.java - Abstract Stats Class Object for Entities, it holds/maintains/sets out the basis for all Entity Stats Objects
	- /Hero/
	  - LegendsHeroArmour.java - Class Object that holds/maintains the currently Equipped Armour for a specific Hero
	  - LegendsHeroInventory.java - Class Object that holds/maintains the current inventory for a specific Hero, keeping track of all stored items, balance, known spells, and currently equipped items
	  - LegendsHeroStats.java - Class Object for Hero Stats (extends LegendsEntityStats Abstract Class), maintains/holds the in-game statistics specific of Heroes
	- /Monster/
	  - LegendsMonsterStats.java - Class Object for Monster Stats (extends LegendsEntityStats Abstract Class), maintains/holds the in-game statistics specific of Monsters
	- /Player/
	  - LegendsPlayerHeroTeam.java - Class Object for the Hero Team of a specific Player (implements the Iterable Interface), maintains/holds/manipulates the Hero Team
- /Game/
  - LegendsOfValor.java - Main Class Object for the LegendsOfValor RPG Game (extends RPG Abstract Class), includes the needed tools to actually play the game
  - RPG.java - Abstract Class Object that holds the basic functionality for any RPG style game (extends Game Abstract Class)
- /Items/
  - LegendsArmour.java - Class Object for all Armour Items in the game (extends LegendsItem Abstract Class), includes basic functionality for the use of the Armour item
  - LegendsItem.java - Abstract Class Object for all Items used by the LegendsGame, holds/maintains the basic info/functionality shared by all items
  - LegendsPotion.java - Class Object for all Potion Items in the game (extends LegendsItem Abstract Class), includes basic functionality for the use of the Potion item
  - LegendsSpell.java - Class Object for all Spell Items in the game (extends LegendsItem Abstract Class), includes basic functionality for the use of the Spell item
  - LegendsWeapon.java - Class Object for all Weapon Items in the game (extends LegendsItem Abstract Class), includes basic functionality for the use of the Weapon item
- /Map/
  - LegendsMap.java - Class Object for the Legends Game Map (extends Map Abstract Class), includes basic functionality required of the game map
  - /Places/
    - Nature.java - Class Object for the "inaccessible" cells (extends Place Abstract Class), has little functionality outside of the inaccessible status
	- Place.java - Abstract Class Object for all Place cells (extends Cell Class), includes/sets out the basic functionality shared by all Place cells
	- /Market/
	  - Market.java - Class Object for the Market Cells (extends Place Abstract Class), holds all the functionality needed to run the Market Cells
	  - MonsterNexus.java - Class Object for Monster Nexus Cells (extends Nexus Class), specified for the winning/losing conditions
	  - Nexus.java - Class Object for the Nexus Cells (extends Market Class), specified for the specific naming implementation
	  - PlayerNexus.java - Class Object for Player Nexus Cells (extends Nexus Class), specified for the winning/losing conditions
	- /Plains/
	  - Bush.java - Class Object for specific details of the Bush Plains Cell (extends Plains Class)
	  - Cave.java - Class Object for specific details of the Cave Plains Cell (extends Plains Class)
	  - Koulou.java - Class Object for specific details of the Koulou Plains Cell (extends Plains Class)
	  - Plains.java - Class Object for the basic Plains Cells (extends Place Abstract Class), holds all the functionality needed to run the Plains Cells and their subtypes
  - /Tracks/
    - Boundary.java - Class Object for the specific details of a Boundary Track (extends Track Abstract Class), holds the required functionality to stop movement into it
	- Lane.java - Class Object for the specific details of a Lane Track (extends Track Abstract Class), holds the required functionality of the Lanes in the LegendsOfValor game
	- Path.java - Class Object for the specific details of a Path Track (extends Track Abstract Class), holds the most basic info needed of a general Track
	- Track.java - Abstract Class Object for all Map Tracks, holds/sets out the basic functionality shared by all Tracks
- /Util/
  - Printer.java - Static Class to allow for more consistent/clean printing throughout the game
  - Random.java - Static Class to hold basic modified random number generators
  - State.java - Enum Class to hold all the possible State types for any game
  - Token.java - Class Object to hold the displayable Token of an Entity/Cell
  - /Abstraction/
    - Cell.java - Class Object for all Game Map Cells, holds the most basic functionality needed of a general Cell
	- Entity.java - Abstract Class Object for all game Playable Entities, holds the basic functionality needed of any Playable Object
	- Game.java - Abstract Class Object for a generalized Game, holds the basic functionality needed of all Games
	- Inventory.java - Abstract Class Object for a generalized Inventory, holds the basic functionality needed for this Collection class
	- Map.java - Abstract Class Object for a generalized Game Map, holds the basic functionality shared by all Game Maps
	- Player.java - Abstract Class Object for a generalized Game Player, holds the basic functionality shared by all Players
  - /Creation/
    - EntityGenerator.java - Static Class to allow randomized creation of Entities for the LegendsOfValor Game
	- ItemGenerator.java - Static Class to allow randomized creation of Items for the LegendsOfValor Game
	- Names.java - Static Class to get a randomized name for an Entity or Item
	- /Config/
	  - NameListsArmour.java - Static List of all names for LegendsArmour Items
	  - NameListsEntities.java - Static List of all names for LegendsEntities Entities
	  - NameListsPotions.java - Static List of all names for LegendsPotion Items
	  - NameListsSpells.java - Static List of all names for LegendsSpell Items
	  - NameListsWeapons.java - Static List of all names for LegendsWeapon Items




## Implementation Detailing

This description is going to be slightly long, so please bear with me. The basic idea of the game is the same as in the detailing of the assignment, a Player controls a team of 3 Heroes playing in 3 lanes on a map, attempting to get to the Monster's Nexus, while the Monsters attempt to get to their Nexus.

The Player itself is an improved upon version of prior Players, it holds some basic information required of it, and also holds a LegendsHeroTeam object, which holds and maintains all the Heroes the Player has (which in this case are 3, but could be extended to be more vvery easily). Unlike the prior assignment, the Heroes are controlled individually, and each hold the required information to know where they are, and to move. Furthermore, a Hero holds its Stats, its Class, and its Inventory (it also extends LegendsEntity). The LegendsHeroInventory is an extension of the basic Inventory (which is just a collection of items), and holds a the currently equipped Weapon, and Armour (via the LegendsHeroArmour object, which holds and maintains all details required for the use of the equipped armour in each of the 4 available slots: Head, Chest, Legs, Feet). The HeroClass just holds which abilities to boost for the Hero, and the LegendsHeroStats hold all the statistics for the Hero. Both the LegendsHeroClass and LegendsHeroStats extend some LegendsEntityXXXX Class that holds the most basic details for that object type (Class or Stats). 

The Monsters follow the same base structure of the Heroes, extending LegendsEntity, and holding the Monster Stats and Class. Both the LegendsMonsterClass and LegendsMonsterStats also hold the basic stats and class boosts for Monsters, and extends LegendsEntityXXXX objects. The Monsters themselves also hold the required detailing to move around the Map. Neither the Hero or Monster implementations changed much since last time besides the moving mechanisms.

All items in the game extend LegendsItem, an Abstract Class that holds the most basic detailing for all items. From here we have the 4 respective Item Types, LegendsWeapon, LegendsArmour, LegendsPotion, LegendsSpell. Each of them has some specific required of that item. Weapons have a damage value, a current and max durability values, which hold the current and max durability of the item; and a dualHanded boolean to check if the weapon is dual handed. Armour has a Slot variable, to check which slot the armour belongs to (Head, Chest, Legs, Feet); a defense multiplier, which is how much damage the armour deflects; and a current and max durability, which work the same way that the weapon durability works. Spells hold a Mana Required value, which is the mana cost of the spell; a target ability value, which keeps track of the ability that the Spell multiplies (or debuffs); a multiplier, which is how much the spell impacts the ability; a isBuff flag, which is used to diferentiate buffing spells from debuffing spells; and a maxDamage value, which is used in conjunction to the Hero level to calculate the damage that is dealt by the spell. Potions also keep a target ability, multiplier, and isBuff values, that work as the ones for Spells do; they also hold a used flag, to keep track of whether or not the potion has been used.

The Map implementation is a bit more complex this time. The main class for the game map is LegendsMap, which extends the Abstract Map class. This class holds an array of playable Tracks (what the instructions call lanes), it also holds the required methods to print the map. Track is an abstract class, which is than extended to 3 different Track Types: Lane, Path, Boundary. The Path Track is a generalized Track, and doesn't see direct use. The Lane Track is the actually playable Lane where Heroes and Monsters can play. Finally the Boundary Track serves as the Boundary between Lanes.

Each of these tracks consists of a list of Places, which is an extension of the Abstract Class Cell, and represent single squares in the map. There are 3 major types of Places: Markets, Plains, Nature. Market Places are the Nexus Cells, there are 2 types of Nexus: PlayerNexus and MonsterNexus, both extend the general Nexus class, which in turn extends the Market class, it is in this last class that all the functionality for the Market Sequence is stored; the other subtypes of markets serve for checks of winning/losing conditions. Nature Places are the inaccessible places on the map, and have no functionality outside of keeping players out. Finally Plains Places, and the different types of Plains (Bush, Cave, Koulou), all serve as the Common tiles for the game; all subtypes extend the basic Plains Place, and each has a specific ability they boost (or in the case of the Regular Plains, None); they also hold and maintain all the logic required to fight. The generalized Place Abstract	Class holds some basic information shared by all Places, including a list of Monsters and Heroes that are in that Place.

Before we get to the actual LegendsOfValor game class, lets first take a look through the Util classes. There are a handful of general util classes: Random, Printer, State, Token. Random and Printer serve as utilities used for better usability/ease of use/consistency throughout the code base. The Token class holds the basic functionality of an Entity/Place Token, ie: what is actually displayed on printing. The State class is an Enum of valid game states: Playing, Quit, Win, Lose. We also have a handful of Util classes used for Abstraction purposes: Cell, Entity, Game, Invventory, Map, Player. We have touched on what each of these does before, but I'll give a brief overview of each. Cell is an abstraction of the common map cell. Entity is the abstract playable entity (ie: players/teams). Game is the basic abstract Game class. Inventory is an abstract collection of items. Map is the most Abstract and basic game map. Player is the Abstract generalized Player for all games.

Now we are left with the Creation Utilities classes: EntityGenerator, ItemGenerator, Names. Each serves as a static random generator for something in the game. We decided to go random to make the game far more playable, since always loading the same Heroes/Monsters/Items makes everything super repetitive and less fun. The Names class and all the List Classes under Creation.Config simply allow us to get random names for everything in the game, this gives us a little flare and impact very little in the playability, although the lists do hide a lot of easter eggs.

Finally we can get into the actual implementation of the Game. Here we have 3 classes: Game, RPG, LegendsOfValor. The Game class is the most abstract, and holds the basic functionality which goes into the game, plays, and checks for win/lose/quit; it also declares a lot of required abstract methods. Then we have the RPG class, which holds the basic functionality for an RPG game, including a Player, and a Map. It also includes an initializing method for the player, and the quit and reset methods that all RPGs share. At last we reach the LegendsOfValor class. This class holds the current Monsters and round counter, as well as some further initialization for the game objects required. Each time a player is to move, the playGame method in RPG is called, which instead calls processUserInput, which is the main playing method, this method gets the use input, and moves the respective hero. Every so often, the processUserInput will also prompt for the moving of monsters (once every 3 rounds, ie: once all heroes have moved), it'll also attempt to respawn heroes and spawn more monsters every so often. After every move by a Hero, the playGame method in RPG will also call updateStatus, which checks for deaths and wins/losts.