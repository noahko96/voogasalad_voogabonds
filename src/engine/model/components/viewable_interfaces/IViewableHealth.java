package engine.model.components.viewable_interfaces;

import engine.IObservable;

public interface IViewableHealth extends IViewable, IObservable<IViewableHealth> {
	public double getMaxHealth();
	public double getCurrHealth();
}
