package engine.model.strategies.factories;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import engine.model.game_environment.MapMediator;
import engine.model.strategies.IMovementStrategy;
import engine.model.strategies.movement.GreedyMovementStrategy;

/**
 * A class to pick the right strategy from a given Strategy name
 * @author Weston
 *
 */
public class MovementStrategyFactory extends AbstractStrategyFactory<IMovementStrategy> {
	private static final String FOLDER_NAME = "movement";
	private MapMediator myMap;
	
	public MovementStrategyFactory(MapMediator map) {
		super(FOLDER_NAME);
		myMap = map;
	}

	public MapMediator getMap() {
		return myMap;
	}

	@Override
	protected IMovementStrategy constructStrategy(String strategyName) {
		IMovementStrategy result;
		try {
			Class<?> strategyType = Class.forName(strategyName);
			Constructor<?> construct = strategyType.getConstructor(this.getClass());
			result = (IMovementStrategy) construct.newInstance(this);
		} catch (
				ClassNotFoundException |
				NoSuchMethodException |
				InstantiationException |
				SecurityException |
				IllegalArgumentException |
				InvocationTargetException |
				IllegalAccessException e) {
			result = new GreedyMovementStrategy(this);
		}
		return result;
	}

}
