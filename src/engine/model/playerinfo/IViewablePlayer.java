package engine.model.playerinfo;

import java.util.List;

import authoring.model.EntityData;
import authoring.model.TowerData;
import engine.IObservable;

public interface IViewablePlayer extends IObservable<IViewablePlayer> {
	/**
	 * Get the number of remaining lives of an object
	 * @return the number of lives a player has remaining
	 */
	public int getLivesRemaining();
	/**
	 * Get the cash the player has
	 * @return returns the value of the money
	 */
	public int getAvailableMoney();
	/**
	 * Get the number of accumulated points
	 * @return the number of points the player has
	 */
	int getPoints();
	/**
	 * XXX: What is this?
	 * @return
	 */
	public int getID();
	
	/**
	 * Get all the towers that are available to the player
	 * @return the available towers
	 */
	public List<EntityData> getAvailableTowers();
	/**
	 * Get all towers that the player can afford
	 * @return all the affordable towers
	 */
	public List<EntityData> getAffordableTowers();

}
