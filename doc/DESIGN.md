Design
======




## Introduction
The goal of this project is to develop a flexible, extendable, and user friendly tower defense game making application. The user will be able to define tower attributes such as weapon type, health, image, and sound. The user will also be able to define enemy health, weapon type, weapon range, pathing, and speed. Other attributes such as wave characteristics and background images will also be editable to the user inside a game authoring environment that allows the user to graphically set these properties and easily test these properties in a demoing environment. The entire game development state will also be saveable and loadable to allow users to invest large amounts of time into sophisticated game development projects.




## Overview


### Project Structure


#### GameData package




#### Utility package
	Point.class
	
#### Authoring package
	Model package
		IAuthor.interface
	View package
	Controller package
		Router.class
#### Game Engine package
	Model package
		Machine package
			IViewableMachine.interface
			Machine.class
			Tower package
				Tower.class
			Enemy package
				Enemy.class
			Weapon package
				IWeapon.interface
				Weapon.class
			Projectile package
				IProjectile.interface
				Projectile.class
		PlayerInfo package
			IModifiablePlayerInfo.interface
			PlayerInfo.class
		GameStrategy package
			IEndCondition.interface
			WinningStrategy.class
			LosingStrategy.class
		ResourceStore package
			IViewableStore.interface
			IModifiableStore.interface
	Controller package
		Controller.class




#### GamePlayer package
	View package
		MapView Package
			DisplayMap.class
		Adjustments Package
			Towers.class
			Upgrades.class
			AnimationSpeed.class
		GameStats Package
			Lives.class
			Money.class
			WaveCount.class
	Controller package
		Menus Package
			StartMenu.class
			SelectControls.class
			PauseMenu.class
		LoadGame Package
			SelectFile.class
	Utility Package
		DefineControls.class
		Menu.class
		


### UML Diagram
![UML Diagram](https://git.cs.duke.edu/CompSci308_2016Fall/voogasalad_voogabonds/raw/22dcba49be98b1fdc692f07b7805391592f2a0d1/resources/backend_images/gameengine_uml_1024.jpg).












#### User Interface
![The startup menu will look like this](https://git.cs.duke.edu/CompSci308_2016Fall/voogasalad_voogabonds/raw/86e1104bc093d949f2fb02b847a3b2590702a873/resources/user_interface_images/VOOGASALADMainMenu.JPG).
In the main menu, the user can decide to either click on a NEW PROJECT tab or BROWSE PROJECTS tab. If the user wants to make a new project, the user would select NEW PROJECT tab, type the name of the project in the Name space, and enter where to save the file in the Save Path space. The user would then select the big  CREATE PROJECT button to create the new project with the desired specifications. If the user wishes to open a preexisting object, the user would select BROWSE PROJECT and select from a list of old projects.




![The game authoring environment will look like this](https://git.cs.duke.edu/CompSci308_2016Fall/voogasalad_voogabonds/raw/86e1104bc093d949f2fb02b847a3b2590702a873/resources/user_interface_images/VOOGASALADGameAuthoringPic.JPG).
The FILE button will allow the user to open, close, save, and load workspaces. The PERSONALIZE button will allow the user to change background color and allow other visual options. In the toolbar under that, the user and select show grid to show a grid in the game display window to provide greater accuracy when placing terrain objects. The Set Background button will allow the user to set the background image. The Define Spawn Point/Path will allow the user to select where the enemies will spawn from and where their destination is. The user will also be able to define the lose condition, whether it is a nexus with health that takes damage from enemies, or simply a gate that enemies pass through. The Create Wave button will allow users to select what type of enemies the wave will have, how many enemies in the wave, the wave pattern (e.g. interweaving enemy 1 and enemy 2, or just have all enemies of type 1 in the first half of the wave and enemies of type 2 in the second half), as well as the wave progression (design wave 1, wave 2, wave 3, etc.). The play button allows the user to demo their changes. In the left hand window will be buttons to Create Tower and Create Enemy. When either button is selected, a pop-up window will appear, allowing the user to define the enemy/tower name, health, weapon, range, image, sound, and description among other properties. After the tower or enemy has been created, it will show up as a button in the corresponding Enemy/Tower List panes. These towers/enemies can be edited upon clicking the corresponding button.




For the game player, the map of the game will take most of the space. There will be a column on the right side with several options. There will be a display for all possible towers which can be dragged and placed onto the map. There will be options to speed up the animations and start waves as well as a display to show the time and lives as well as other possible features. When towers are placed, the range will also be displayed as well their ability to be placed in specific sports of the terrain. Furthermore, there will be functionality either on the tower or a row on the bottom of the screen to upgrade the towers. This row will also show the player’s current money.




### Design Details




### Example Games
##### Canyon Defense
* Map separated into area where enemies go and where you can place towers
* Grid defined spots where we can place towers
* Unlockable achievements/tower with specific achievements needed
* Anti-ground and anti-air distinction for weapons
* One single source point for enemies, one single end point




##### Kingdom Rush
* Enemies does not have a grid for movements
* Multiple sources of enemies
* Tower could generate soldiers to fight the enemies
* Asynchronous weapons that can be used in desperate conditions 
* User could drag around the background to observe other components on the map 
* Multiple waves within a level (extension)
* Has a GUI that allows user to select different maps of the games
* Z axis display (3D display)




##### Bloons TD
* Monkey towers that have a buy price, sell price, targeting variations, upgrades
* Free to place towers anywhere as long as they collide with neither the path nor other towers
* Different terrain that can hold different tower types (boats go on water, etc.)
* Some enemies are immune to the attacks of different towers
* Towers can be upgraded into more powerful versions
* Buying money generating towers is the key to doing well
* Enemies spawn other enemies too 








### Design Considerations
* Implementing winning and losing conditions:
	* Having each object implement hasWon/hasLost call.
		* pros: 
			* could possibly be easier to implement, but we could not come up with a way to implement it
		* cons:
			* to add a new winning/losing conditions requires multiple modifications to the code
	* Having each player implement winning and losing conditions that observe what they need in order to determine a win or a loss.
		* pros: 
			* pretty straightforward, but could not determine which condition to check when a new game is made. 
		* cons:
			* there are other conditions under which the player would lose and might result in a complicated class for each player
	* Having EndCondition class with a list of possible losing conditions
		* pros:
			* if-then-else structure with boolean will suffice
		* cons:
			* messy nested if statements might take long time to understand
			* adding a new ending condition requires multiple modifications on different parts of the code
	* Having every possible object in an end condition extend observable. Upon creation of game from authoring, reflect to determine which winning/losing conditions and which observables to observe.
		* pros: 
			* easy to extend and add more ending conditions (simple point of modification)
			* good structure, do not need multiple/nested if-then-else structures
		* cons:
			* use of reflection might lead to exposure of classes
			* hard to implement
* How will the front-end know what type of object is there upon click if you only give interface information? 
	* Option 1: IViewable interface has method: isChangeable() (Tower would implement isChangeable() = true)
		* pros: 
			* does not expose too much information to front end except for it the item is changeable
		* cons:
			* might need to expose other methods that might be different between different objects
	* Option 2: IViewableTower or IViewableOther -> front-end checks for type and creates a different event listener based on what it is (overloaded method).
		* pros: 
			* IViewableTower interface could expose other useful methods . For example, getUpgradeOptions(), upgrade(), etc.
		* cons:
			* When creating the GUI, front end would have to keep track of different objects on the user interface
*  Other considerations
	* Upgrade structure (tree, graphs (directed)) for towers
	* Weapons/Projectiles - what class should contain range, direction, movement, damage
	* Level of indirection between authoring front-end and authoring back-end
	* For middle-man controller: More indirection if front-end or back-end change, check for validity
	* Against middle-man controller: Is it necessary? It will just call getters/setters
	* Observers for game-engine, call notifyObservers() upon change of state for a model
	* Solidify communication between game engine and game player -> what goes through the controller. Back-end tells front-end where to go, front-end doesn’t need to know about the valid path for a machine.
	* Viewable interface with defined methods for all machines - getLocation(), getHeading()
	* Machines own a PlayerInterface, Machines observe weapons, weapons observe projectiles








