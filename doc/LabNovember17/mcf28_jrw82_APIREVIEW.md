Worked with:
Jacob Warner: jrw82
Matthew Faw: mcf28

Matthew Faw


Part 1

 1. What about your API/design is intended to be flexible? 
	 2. Our APIs aim to abstract away ownership so that adding new objects later is easy.  For example, we're using the Visitor pattern to handle placing objects on our Map, so that any object that implements that interface can visit the map. This idea runs through many areas of our design
 2. How is your API/design encapsulating your implementation decisions? How is your part linked to other parts of the project? 
	 1. We're aiming to have all of our game entities manage themselves, and provide simple means of communication.  This means being careful about our ownership model to ensure that each object knows about things only that are logically relevant to it. We're using the Strategy pattern to insert flexibility into our design
	 2. I'm working on the game map right now. This map is intended to provide a simple interface to add to the map, and allow elements to move on the map
 3. What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)? 
	 1. One exception is placing an element in an invalid location on the map. I'm making a simple interface for managing this interaction so that the map structure can be easily queried for valid info
 4. Why do you think your API/design is good (also
    define what your measure of good is)?
    1. I think it is clean, the ownership model is specific, and interactions between objects are simple.  Observer/observable will allow easy communication of events. The visitor interface will allow abstraction to allow flexibility in what objects can be placed where. Strategy pattern will allow easy swapping of functionality

Part 2

 1. What feature/design problem are you most excited to work on? 
	 1. I'm interested in looking into the entity-component-system structure.
 2. What feature/design problem are you most worried about working on? 
	 1. I'm worried that the observer/observable pattern might get messy
 3. What is do you plan to implement this weekend? 
	 1. I'm going to finish implementing basic construction of the map, and allowing interfacing with the map and allowing objects to move on the map. We're also going to work on integration with the larger team to get a simple game working on the player side
 4. Discuss the use cases/issues created for your pieces: are they descriptive,
    appropriate, and reasonably sized? 
	    1. The issues I've created for myself on gitlab are intended to be fairly self contained and implementable in a small amount of time
 5. Do you have use cases for errors that might occur?
	 1. They have some high level descriptions of problems that could occur in implementation

