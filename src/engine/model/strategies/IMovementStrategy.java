package engine.model.strategies;

import javafx.util.Pair;
import utility.Point;

/**
 * An interface for the strategies that MovableComponents use to select their next point to move to.
 * @author Weston
 *
 */
public interface IMovementStrategy {

	/**
	 * Finds a next heading and location for the movable, based on the movable's goal and the strategy's algorithm.
	 * @param m Movable to be moved
	 * @return a javafx pair containg the new heading (double) and position (Point)
	 */
	public Pair<Double, Point> nextMove(IMovable m, IPhysical p);
}
