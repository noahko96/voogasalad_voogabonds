package engine.model.components.viewable_interfaces;

import engine.IObservable;

public interface IViewableCollidable extends IViewable, IObservable<IViewableCollidable> {
	/**
	 * Gets the collision radius for a collidable entity.
	 * @return
	 */
	public double getCollisionRadius();
}
