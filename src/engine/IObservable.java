package engine;

/**
 * This is the interface used by any object that wants to publish changes to other objects
 * 
 * @author matthewfaw
 *
 *@param A: The class that determines what is being observed
 */
public interface IObservable<A> {
	/**
	 * Adds the observer to the list of objects to be notified by the publisher
	 * @param aObserver
	 */
	public void attach(IObserver<A> aObserver);

	/**
	 * Removes the observer from the list of objects to be notified by the publisher
	 * @param aObserver
	 */
	public void detach(IObserver<A> aObserver);

	/**
	 * A method to announce a change to all subscribers
	 */
	public void notifyObservers();
}
