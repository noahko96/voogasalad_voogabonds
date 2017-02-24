package engine.model.strategies;

import java.util.List;

import engine.model.components.ITargeting;
import engine.model.components.concrete.PhysicalComponent;
import engine.model.systems.PhysicalSystem;

/**
 * A class so that any concrete targeting strategies can use the getTargets method below.
 * @author Weston
 *
 */
abstract public class AbstractTargetingStrategy implements ITargetingStrategy{
	
	/**
	 * Finds all the entities within the targeter's sight range
	 * @param map
	 * @param location
	 * @param targeter
	 * @return
	 */
	protected List<PhysicalComponent> getTargets(PhysicalSystem map, IPhysical location, ITargeting targeter) {
		List<PhysicalComponent> targets = map.withinRange(
				location.getPosition(),
				targeter.getTargetWedgeRadius(),
				location.getHeading(),
				targeter.getTargetWedgeWidth());
		
		return targets;
	}
}
