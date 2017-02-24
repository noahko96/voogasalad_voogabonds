package engine.model.strategies;

import engine.model.components.concrete.TargetingComponent;
import engine.model.systems.PhysicalSystem;
import engine.model.systems.TeamSystem;

public interface ITargetingStrategy {

	/**
	 * Picks a target entity from the collection of nearby entities.
	 * @param map
	 * @param teams
	 * @param location
	 * @param targeter
	 * @return the physical component of the targeted entity
	 */
	public IPhysical target(PhysicalSystem map, TeamSystem teams, IPhysical location, TargetingComponent targeter);

}
