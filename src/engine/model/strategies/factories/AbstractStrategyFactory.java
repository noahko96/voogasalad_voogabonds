package engine.model.strategies.factories;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import utility.ResouceAccess;

abstract public class AbstractStrategyFactory<A> {
	private static final String STRATEGY_PATH = "src/engine/model/strategies/";
	private List<String> myAtypeStrategies;
	private String myFolderPath;
	
	public AbstractStrategyFactory(String strategyType) {
		myFolderPath = String.format("%s%s/", STRATEGY_PATH, strategyType);

		File[] folder = new File(myFolderPath).listFiles();
		myAtypeStrategies = Arrays.stream(folder)
											.map(e -> e.getName().replaceAll(".java", ""))
											.collect(Collectors.toList());
	}
	

	/**
	 * Gets a new instance of the movement strategy named movementStrategy.
	 * Currently only returns GreedyMovementStrategy.
	 * @param movementStrategy
	 * @return strategy with name movementStrategy
	 * @throws ClassNotFoundException 
	 */
	public A newStrategy(String strategyName) {
		//TODO: Users see names from a resource file (resource key is class name)
		
		if (!myAtypeStrategies.contains(strategyName)) {
			throw new IllegalArgumentException(String.format("%s%s", ResouceAccess.getError("BadStrategy"), strategyName));
		}
		
		strategyName = String.format("%s%s", myFolderPath, strategyName);
		A result = constructStrategy(strategyName.replace("/", ".").substring(4));
		return result;
	}


	abstract protected A constructStrategy(String strategyName);

}