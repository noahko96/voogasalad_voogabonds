package engine.model.systems;

import java.util.List;

import engine.model.components.IComponent;
import engine.model.entities.IEntity;

/**
 * An interface to capture the commonalities between all systems 
 * 
 * https://en.wikipedia.org/wiki/Entity%E2%80%93component%E2%80%93system
 * System: "Each System runs continuously (as though each System had its own private thread) 
 * and performs global actions on every Entity that possesses a Component of the same aspect as that System."
 * @author matthewfaw
 *
 *
 * Alan - 11/30
 * Currently, able to register and unregister IRegisterable objects.
 * Classes should be ISystems if they hold references to IRegisterables.
 *
 */
//TODO: Add comments 
public interface ISystem <T> {
	public List<T> getComponents();
	public T getComponent(IComponent component);
	public T getComponent(IEntity entity);
	public void attachComponent(T aComponet);
	public void detachComponent(T aComponent);
	/**
	 * Adds aComonent to be registered with the system
	 * @param aComponent
	 */
//	public void register(IRegisterable registerable);
	
	/**
	 * Removes aComonent to be registered with the system
	 * @param aComponent
	 */
//	public void unregister(IRegisterable registerable);
}
