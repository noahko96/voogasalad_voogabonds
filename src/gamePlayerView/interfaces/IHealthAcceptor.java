package gamePlayerView.interfaces;

import engine.IObservable;
import engine.model.components.viewable_interfaces.IViewableHealth;
import engine.model.playerinfo.IViewablePlayer;

public interface IHealthAcceptor {
	public void acceptHealth(IObservable<IViewableHealth> aComponent);
}
