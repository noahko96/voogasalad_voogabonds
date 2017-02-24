package engine.model.components;

import engine.model.strategies.IPosition;

/**
 * An interface to give the targeting strategies everything they need to know to calculate targets
 * @author Weston
 *
 */
public interface ITargeting {
	
	/**
	 *  Gets the width of the taregeter's sight range
	 *  180 means it can see in a full circle, 15 means it can only see 15 degrees left or right of its heading
	 * @return the width (in degrees) of the targeter's sight wedge
	 */
	abstract public double getTargetWedgeWidth();
	
	/**
	 * 
	 * @return the targeter's sight radius
	 */
	abstract public double getTargetWedgeRadius();
	
	/**
	 * @return true if targeter targets enemies, false if it targets allies
	 */
	abstract public boolean targetsEnemies();
	
	/**
	 * @return the target chosen my the targeting strategy
	 */
	abstract public IPosition getTarget();
}
