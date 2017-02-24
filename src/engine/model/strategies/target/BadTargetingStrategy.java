package engine.model.strategies.target;

import engine.model.components.concrete.PhysicalComponent;
import engine.model.components.concrete.TargetingComponent;
import engine.model.strategies.AbstractTargetingStrategy;
import engine.model.strategies.IPhysical;
import engine.model.strategies.ITargetingStrategy;
import engine.model.strategies.factories.AbstractStrategyFactory;
import engine.model.systems.PhysicalSystem;
import engine.model.systems.TeamSystem;


public class BadTargetingStrategy extends AbstractTargetingStrategy {
	
	public BadTargetingStrategy(AbstractStrategyFactory<ITargetingStrategy> factory) {
		//Do nothing
	}

	@Override
	public IPhysical target(PhysicalSystem map, TeamSystem teams, IPhysical location, TargetingComponent targeter) {
		for (PhysicalComponent target: getTargets(map, location, targeter)) {
			if (
					targeter.getEntity() != target.getEntity() && 
					((teams.areEnemies(targeter, target) && targeter.targetsEnemies()) ||
					(teams.areAllies(targeter, target) && !targeter.targetsEnemies()))) {
				return target;
			}
			
		}
		return null;
	}
}
