package engine.controller;

import java.util.ArrayList;
import java.util.List;

import authoring.model.PlayerData;
import engine.model.playerinfo.Player;
import engine.model.resourcestore.ResourceStore;
import gamePlayerView.gamePlayerView.Router;

/**
 * This class is intended to provide a simple means of managing 
 * the entities that can interact with the game
 * @author matthewfaw
 *
 */
public class PlayerController {
	private List<Player> myPlayers;
	private transient Router myRouter;
	
	public PlayerController(Router aRouter)
	{
		myPlayers = new ArrayList<Player>();
		myRouter = aRouter;
	}
	
	/**
	 * A method used to add a new player to the game
	 * 
	 * @param aPlayerData info necessary to construct the player
	 */
	public void addPlayer(PlayerData aPlayerData)
	{
		//TODO: set up the infrastructure for a player
		// initially, let's assume there's only one player
		Player player = new Player(myRouter, aPlayerData);
		myPlayers.add(player);
		myRouter.distributePlayer(player);
	}
	
	/**
	 * Gets a player from the list of players at the
	 * specific player ID.
	 * @param playerID
	 * @return the player at player ID
	 */
	public Player getPlayer(int playerID) {
		return myPlayers.get(playerID);
	}
	
	@Deprecated //only temporary
	public Player getActivePlayer()
	{
		return myPlayers.get(0);
	}
	
	public void addResourceStoreForAllPlayers(ResourceStore aResourceStore)
	{
		myPlayers.forEach(player -> player.addResourceStore(aResourceStore));
	}
}
