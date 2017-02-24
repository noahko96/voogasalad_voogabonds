package engine.model.strategies;

import utility.Damage;

/**
 * An interface for the strategy that calculates how much damage an object deals to objects around it when it explodes.
 * @author Weston
 *
 */
public interface IDamageStrategy {
	
	/**
	 * Calculates the amount of damage dealt to an enemy in the dealer's blast radius
	 * @param dealer the damage dealer's location
	 * @param taker the damage taker's location
	 * @param damage the base damage the dealer does
	 * @return a Damage object containing the amount of damage
	 */
	abstract public Damage getAoEDamage(IPhysical dealer, IPhysical taker, double damage);
	
	/**
	 * Calculates the amount of damage dealt to an ally in the dealer's blast radius
	 * @param dealer the damage dealer's location
	 * @param taker the damage taker's location
	 * @param damage the base damage the dealer does
	 * @return a Damage object containing the amount of damage
	 */
	abstract public Damage getAoEAllyDamage(IPhysical dealer, IPhysical taker, double damage);
	
	/**
	 * Calculates the amount of damage the dealer deals to the object it collides with (it's target)
	 * @param damage the base damage the dealer does
	 * @return a Damage object containing the amount of damage
	 */
	abstract public Damage getTargetDamage(double damage);

}
