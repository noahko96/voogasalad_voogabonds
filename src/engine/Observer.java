//package engine;
///*
////XXX matthewfaw: what do add and remove do? I don't recognize these as observer functionality
////I'm not sure that I want to go down the rabbit hole of making this interface templated
////We should talk about this
////The issue is that this forces the observable to know exactly what kind of observer it's observing
////which seems to be violating the data hiding that the observer/observable pattern provides
//
///*
// * The way I've been using this is that projectiles are constructed with an instance of Observer<IMovable>.
// * (which would be a reference to some class in the View/Controller, probably the one that updates what objects are in the scene)
// * After construction, the projectile calls myObserver.add(this) so that the scene knows to create an image to
// * represent the new projectile. Every time the projectile moves, it calls myObserver.update(this) and it calls 
// * myObserver.remove(this) when it explodes. It doesn't force the observer to know exactly what kind of thing it's
// * observing, only that it implements whatever interface A is (IMovable in this case).
// * 
// * You can use Observer<IEntity> to do exactly the same thing you are doing with IObserver, except you get
// * more control with the distinction between add, update, and remove.
// */
//
///**
// * This Interface allows an object to be notified of changes to objects of a specific type, given the implementing object
// * registers as an observer of the object of type A. 
// * @author Weston
// *
// * @param <A> The type of object the observer is observing.
// */
//@Deprecated
//public interface Observer<A> {
//	
//	/**
//	 * aObjectToAdd calls add when it is created so the observer knows of it's existence.
//	 * @param aObjectToAdd
//	 */
//	abstract public void add(A aObjectToAdd);
//	
//	/**
//	 * aObjectToAdd calls update when its internal state has changed in a way that the observer needs to know about.
//	 * (Change in heading, position, or imagePath, etc.)
//	 * @param aObjectToUpdate
//	 */
//	abstract public void update(A aObjectToUpdate);
//	
//	/**
//	 * aObjectToAdd calls add when it is destroyed so the observer knows to stop representing/displaying it.
//	 * @param aObjectToRemove
//	 */
//	abstract public void remove(A aObjectToRemove);
//}