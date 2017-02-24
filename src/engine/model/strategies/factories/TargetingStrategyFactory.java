package engine.model.strategies.factories;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import engine.model.strategies.ITargetingStrategy;
import engine.model.strategies.target.BadTargetingStrategy;

public class TargetingStrategyFactory extends AbstractStrategyFactory<ITargetingStrategy> {
	private static final String FOLDER_NAME = new String("target");

	public TargetingStrategyFactory() {
		super(FOLDER_NAME);
	}

	@Override
	protected ITargetingStrategy constructStrategy(String strategyName) {
		ITargetingStrategy result;
		try {
			Class<?> strategyType = Class.forName(strategyName);
			Constructor<?> construct = strategyType.getConstructor(this.getClass());
			result = (ITargetingStrategy) construct.newInstance(this);
		} catch (
				ClassNotFoundException |
				NoSuchMethodException |
				InstantiationException |
				SecurityException |
				IllegalArgumentException |
				InvocationTargetException |
				IllegalAccessException e) {
			return new BadTargetingStrategy(this);
		}
		return result;
	}

}
