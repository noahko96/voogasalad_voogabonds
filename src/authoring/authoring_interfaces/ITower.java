package authoring.authoring_interfaces;

/**
 * @author Christopher LU
 * This interface is responsible for providing the controller access to the tower's traits entered in by the user.
 */

public interface ITower {
	
	/**
	 * Gets the price of the tower.
	 */
	public double getCost();
	
	/**
	 * Gets the selling price of the tower. Incorporates the upgrade level and rank of the tower.
	 */
	public double getSellPrice();
	
	/**
	 * Gets the rank of the tower. Rank goes up per wave (not always one per wave). Tower health back at 100% when rank up.
	 */
	public double getRank();
	
	/**
	 * Gets the size of the tower by increasing the number of grids the tower takes up.
	 */
	public double getSize();
	
	/**
	 * Gets the price per upgrade of the tower.
	 */
	public double getUpgradeCost();
}
