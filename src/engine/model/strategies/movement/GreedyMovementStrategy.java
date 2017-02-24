package engine.model.strategies.movement;

import engine.model.strategies.AbstractMovementStrategy;
import engine.model.strategies.IMovable;
import engine.model.strategies.IMovementStrategy;
import engine.model.strategies.IPhysical;
import engine.model.strategies.factories.AbstractStrategyFactory;
import javafx.util.Pair;
import utility.Point;

public class GreedyMovementStrategy extends AbstractMovementStrategy {

	public GreedyMovementStrategy(AbstractStrategyFactory<IMovementStrategy> creator) {
		super(creator);
	}

	/**
	 * Move as close to straight towards your goal as possible.
	 */
	protected  Pair<Double, Point> nextMoveWithGoal(IMovable m, IPhysical p) {
		if (p.getPosition().equals(m.getGoal()))
			return new Pair<Double, Point>(p.getHeading(), p.getPosition());

		double newHeading = newHeadingTowards(m.getGoalPoint(), m, p);

		double distance = Math.min(m.getMoveSpeed(), p.getPosition().euclideanDistance(m.getGoalPoint()));
		
		return new Pair<Double, Point>(newHeading, p.getPosition().moveAlongHeading(distance, newHeading));
	}
	
	/**
	 * Just move straight ahead.
	 */
	protected Pair<Double, Point> nextMoveNoGoal(IMovable m, IPhysical p) {
		double heading = p.getHeading();
		double speed = m.getMoveSpeed();
		
		Point newLoc = p.getPosition().moveAlongHeading(speed, heading);
		
		return new Pair<Double, Point>(heading, newLoc);
	}

}
