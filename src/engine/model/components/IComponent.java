package engine.model.components;

import engine.model.entities.IEntity;

/**
 * An interface to capture the commonalities of all components.
 * 
 * See https://en.wikipedia.org/wiki/Entity-component-system
 * Component: the raw data for one aspect of the object, and how it 
 * interacts with the world. "Labels the Entity as possessing this particular aspect". 
 * Implementations typically use Structs, Classes, or Associative Arrays
 * 
 * @author matthewfaw
 *
 */
public interface IComponent {
	/**
	 * Gets the Entity object which owns this component
	 * Assumes that component is owned by a entity
	 * @return
	 */
	public IEntity getEntity();

	/**
	 * Distributes the data for different components to the router
	 * for the front end to observe, view, and display.
	 */
	public void distributeInfo();

	/**
	 * Detatches the entity from any Systems it belongs to and sets any values that others might be watching to null.
	 * It would free all of it's fields, but Java doesn't let us garbage collect for ourselves.
	 * @author Weston
	 */
	public void delete();
}
