package engine.model.strategies.movement;

import java.util.Random;

import engine.model.game_environment.MapMediator;
import engine.model.game_environment.paths.PathManager;
import engine.model.strategies.AbstractMovementStrategy;
import engine.model.strategies.IMovable;
import engine.model.strategies.IPhysical;
import engine.model.strategies.factories.MovementStrategyFactory;
import javafx.util.Pair;
import utility.Point;

public class PathMovementStrategy extends AbstractMovementStrategy{

	private static final Random RANDOM = new Random();
	private Point myPreviousGoalLocation;
	private MapMediator myMap;
	private PathManager myPath;
	
	public PathMovementStrategy(MovementStrategyFactory creator) {
		super(creator);
		myMap = creator.getMap();
	}
	
	@Override
	public  Pair<Double, Point> nextMove(IMovable m, IPhysical p) {
		if (m.getGoal() != null && (myPath == null || myPreviousGoalLocation == null || !myPreviousGoalLocation.equals(m.getGoal()))) {
			myPreviousGoalLocation = m.getGoalPoint();
			myPath = myMap.constructPaths(p, m);
		}
		return super.nextMove(m, p);
	}
	
	@Override
	protected Pair<Double, Point> nextMoveNoGoal(IMovable m, IPhysical p) {
		//Spins around aimlessly
		return new Pair<Double, Point>(p.getHeading() + RANDOM.nextDouble() * m.getTurnSpeed(), p.getPosition());
	}

	@Override
	protected Pair<Double, Point> nextMoveWithGoal(IMovable m, IPhysical p) {
		Point subGoal = myPath.getNextVertex(p.getPosition());
		
		//Check if we aren't on the path anymore
		if (subGoal == null)
			return nextMoveNoGoal(m, p);
		
		double newHeading = newHeadingTowards(subGoal, m, p);
		
		Point newPosition;
		if (p.getPosition().towards(subGoal) == newHeading)
			newPosition = moveTowards(subGoal, newHeading, m, p);
		else
			newPosition = p.getPosition();
		
		return new Pair<Double, Point>(newHeading, newPosition);
	}
	
	

	private Point moveTowards(Point subGoal, double heading, IMovable m, IPhysical p) {
		double distance = Math.min(m.getMoveSpeed(), p.getPosition().euclideanDistance(subGoal));
		
		return p.getPosition().moveAlongHeading(distance, heading);
	}
}
