package engine.model.strategies;

import engine.model.playerinfo.IModifiablePlayer;

/**
 * An interface for players to subscribe and unsubscribe to winning and losing strategies.
 * @author Weston
 *
 */
public interface IWinLoseStrategy {
	
	/**
	 * Adds a player p to the list of subscribed players.
	 * Subscribed player's win or lose method will be called when the Strategy's condition is met. 
	 * @param p
	 */
	abstract public void subscribe(IModifiablePlayer p);
	
	/**
	 * Removes a player p from the list of subscribed players.
	 * Subscribed player's win or lose method will be called when the Strategy's condition is met. 
	 * @param p
	 */
	abstract public void unsubscribe(IModifiablePlayer p);
}
