package gamePlayerView.interfaces;

import engine.IObservable;
import engine.model.playerinfo.IViewablePlayer;

/**
 * @author Guhan Muruganandam
 */

public interface IEnemiesKilledAcceptor {
	public void acceptEnemiesKilled(IObservable<IViewablePlayer> aPlayer);
}
