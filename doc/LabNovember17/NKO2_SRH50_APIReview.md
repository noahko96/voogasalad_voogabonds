Lab 11/17, API Review
=========

Noah Over, nko2
Sean Hudson, srh50

### Sean's Design
* All of the objects and everything is created in the backend. A button will create the object in the backend and the fields will be filled in each time you edit something. The backend and frontend will communicate through getters and setters. 
* Accommodates a bunch of different types of objects. It can handle all types of objects (e.g., Tower, Enemy)
* Communicating with team over what to include in XML file that will contain all of the information on the game.
* Validating input on the backend will throw exceptions if input is incorrect.
* It's good that they are communicating through a controller. It's bad that they pass up a full interface from the back end to the front end. 
* Might try to work on a utility or something as the excited thing.
* Worried about passing data from back end to front end.