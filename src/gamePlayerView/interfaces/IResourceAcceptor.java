package gamePlayerView.interfaces;

import engine.IObservable;
import engine.model.playerinfo.IViewablePlayer;


public interface IResourceAcceptor {

	void acceptResources(IObservable<IViewablePlayer> aPlayer);

}
