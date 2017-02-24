package gamePlayerView.interfaces;

import engine.IObservable;
import engine.model.playerinfo.IViewablePlayer;


/**
 * @author Guhan Muruganandam
 */

/**
 *  interface utilised by the router.
 */

public interface IPlayerAcceptor{
	
 /**
 * Allows router to establish connection between backend and frontend object 
 */
	public void acceptPlayer(IObservable<IViewablePlayer> aPlayer);
	
}
