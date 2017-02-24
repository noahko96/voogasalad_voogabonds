package gamePlayerView.interfaces;

import engine.IObservable;
import engine.model.components.viewable_interfaces.IViewableBounty;
import engine.model.components.viewable_interfaces.IViewableDamageDealer;

public interface IDamageAcceptor {
	public void acceptDamage(IObservable<IViewableDamageDealer> aComponent);
}
