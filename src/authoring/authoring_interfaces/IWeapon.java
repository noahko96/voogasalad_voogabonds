package authoring.authoring_interfaces;

/**
 * @author Christopher Lu
 * This interface is responsible for providing the controller with access to traits of the weapon.
 */

public interface IWeapon {

	/**
	 * Gets the name of the weapon.
	 */
	public String getName();
	
	/**
	 * Type determines what units this weapon is effective/useless against. Also determines if the weapon is a support-type tower with a healing aura/some defensive stat aura with no
	 * offense, or if it is a single target damage dealer or an AOE damage dealer, etc.
	 */
	public String getType();
	
	/**
	 * Gets how much damage a tower/enemy does, or how much it heals. This method is called when user enters a value into the text field for the weapon.
	 */
	public Double getEffectAmount();
	
	/**
	 * Gets the radius of the range of the tower's/enemy's operating ability. This method is called when user enters a value into the text field.
	 */
	public Double getRange();
	
	/**
	 * Gets the firing strategy for the weapon. This includes target closest enemy, target farthest enemy, target enemy with least health, etc.
	 */
	public String getStrategy();
	
	/**
	 * Gets the firing rate for the weapon. Bullets/Pulses/Effects per second.
	 */
	public Double getFireRate();
	
	/**
	 * Accuracy might be a little too intricate. Determines hit rate of the weapon. Increases only with rank, not upgrades.
	 */
	public Double getAccuracy();
	
	/**
	 * Rank of weapon will be equivalent to the rank of the tower. Increases weapon's accuracy. Not sure if we need this method.
	 */
	public Double getRank();
	
	
}
