package engine.model.components.viewable_interfaces;

import engine.IObservable;
import engine.model.entities.IEntity;

public interface IViewable {
	/**
	 * Returns the entity associated with the viewable
	 * @return the corresponding entity
	 */
//	public IEntity getEntity();
	
	/**
	 * Returns the entity ID associated with this viewable.
	 * @return
	 */
	public String getEntityID();
}
