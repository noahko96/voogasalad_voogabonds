package engine.model.entities;

import java.util.List;

import engine.IObservable;
import engine.model.components.IComponent;
import engine.model.components.IModifiableComponent;


/**
 * an interface to capture the commonalities among each object that can be added
 * to the game map
 * 
 * https://en.wikipedia.org/wiki/Entity%E2%80%93component%E2%80%93system
 * Entity: The entity is a general purpose object. Usually, it only consists of a unique id. They "tag every 
 * coarse gameobject as a separate item". Implementations typically use a plain integer for this.
 * 
 * @author matthewfaw
 *
 */
public interface IEntity extends IObservable<IEntity> {

	/**
	 * a method to get the unique identifier corresponding to the entity
	 * @return Unique object ID
	 */
	public String getId();

	/**
	 * Add a component to an entity
	 * This is how entities are given new functionality
	 * @param aComponent
	 */
	public void addComponent(IModifiableComponent aComponent);
	
	/**
	 * Use this method to set a unique ID for each entity on creation.
	 * @param uniqueID
	 */
	public void setId(String uniqueID);
	
	public void delete();

	public List<IComponent> getComponents();
	
}