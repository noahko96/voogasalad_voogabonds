package engine.model.strategies;

import utility.Point;

/**
 * An interface for objects that have a position
 * @author Weston
 *
 */
public interface IPosition {
	/**
	 * @return this object's position
	 */
	public abstract Point getPosition();
}
