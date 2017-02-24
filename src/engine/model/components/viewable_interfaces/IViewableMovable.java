package engine.model.components.viewable_interfaces;

import engine.IObservable;
import engine.model.strategies.IMovementStrategy;

public interface IViewableMovable extends IViewable, IObservable<IViewableMovable>{
	/**
	 * Gets the movement strategy for a movable entity.
	 * @return
	 */
	public IMovementStrategy getMovementStrategy();
	/**
	 * Get the turn speed for a movable entity.
	 * @return
	 */
	public double getTurnSpeed();
	/**
	 * Get the move speed for a movable entity.
	 * @return
	 */
	public double getMoveSpeed();
}
