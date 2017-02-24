package engine.model.components;

import engine.model.entities.IEntity;
import engine.model.strategies.IPosition;
import engine.model.weapons.DamageInfo;

/**
 * An interface for components that create other entities. 
 * @author Weston
 *
 */
public interface ICreator {

	/**
	 * Sets the creator's (and by extension, their spawns) target to target
	 * @param target
	 */
	abstract public void setTarget(IPosition target);
	
	/**
	 * 
	 * @return the position of the creator's target
	 */
	abstract public IPosition getTarget();
	
	/**
	 * 
	 * @return the name of the entity this creator spawns
	 */
	abstract public String getSpawnName();
	
	/**
	 * Checks if this creator is the parent of the entity e
	 * @param e
	 * @return true iff it is the parent
	 */
	abstract boolean isParent(IEntity e);
	
	/**
	 * Adds data to the creator's damage statistics
	 * @param data
	 */
	abstract void updateStats(DamageInfo data);
	
	/**
	 * @return the creator's damage statistics
	 */
	abstract DamageInfo getStats();

}
