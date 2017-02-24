package gamePlayerView.interfaces;

import engine.IObservable;
import engine.model.components.viewable_interfaces.IViewableBounty;

public interface IBountyAcceptor {
	public void acceptBounty(IObservable<IViewableBounty> aComponent);
}
