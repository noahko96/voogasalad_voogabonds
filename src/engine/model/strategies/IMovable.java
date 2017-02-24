package engine.model.strategies;

import utility.Point;

/**
 * An interface that contains what a movement strategy needs to know to be able to calculate the point it should move to.
 * @author Weston
 *
 */
public interface IMovable {
	
	abstract public Point getGoalPoint();
	abstract public IPosition getGoal();
	abstract public double getTurnSpeed();
	abstract public double getMoveSpeed();
	
}
