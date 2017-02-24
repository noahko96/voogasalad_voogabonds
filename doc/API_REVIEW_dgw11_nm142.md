### API REVIEW
#### Tripp Whaley (dgw11) reviewed with Noah Monn (nm142)

#### Part 1
* Towers, weapons, and projectiles are all separated as separate classes to allow for very specific modulation of each individual component
* My component holds data for backend and authoring parts of project, creating an interface that allows for all data of each tower, weapon, projectile, etc. to be accessed and/or modified by authoring environment
* Errors could come from incorrect passing of data or accidental changing of data structures by other parts of code in backend and authoring environment. We are using interfaces to prevent this, but we will need to make a specific error handler class that can check to see if an error is occuring with newly passed in data, then keep the old data if necessary (check new before replacing the old data)
* I think our API is good because it instantiates every part of a weapon separately with all its relevant parameters individually, so that if a tower image wants to be updated, the weapon and projectile parts of the tower do not even have to be accessed. It allows for extensive modulation of current parameters of each tower object while adhering to the open closed principle by avoiding inappropriate intimacy between classes that are not very related.

#### Part 2
* Most excited to work on interactive changing of parameters of backend data while playing the game (being able to author the game during runtime). Also excited to work on a dynamic frontend authoring features such as a popup to click and edit parameters of turrets if I can move to more front end side of the authoring environment, as well as implementing a Groovy interface to allow users to add in code themselves.
* Most worried about implementing JSON streaming so that multiple computers can connect over a network, as that seems extremely difficult and bulky in terms of data transfer
* I plan on implementing the finishing touches of the Backend side of the Game authoring environtment that my team needs to properly get a functional prototype working and also getting JSON serialization of our frontend and backend environments using GSON.
* use cases with JSON serialization:
	* user wants to save the current game state that they have authored, so they save it which serializes all current frontend and backend properties to json and saves in a config file to be loaded later. 
	* user wants to connect with another player over a network, so the current game state must be serialized and sent over a network to be downloaded and also modified by another user
	* These use cases are fairly descriptive but are slightly daunting in the task itself. This will be difficult to implement.
* We have not considered the implications of incorrect data being passed in to the authoring backend data too heavily, except for implementing methods in our controllers that check for correctly formatted data parameters. Other than that, we are in early stages of planning how to throw errors on backend authoring side to be displayed on the frontend. Ideally, we check new entered data before saving it as the current state, if the new state is inappropriately formatted, we show the frontend in a message dialogue box that allows the user to try appropriate parameters.