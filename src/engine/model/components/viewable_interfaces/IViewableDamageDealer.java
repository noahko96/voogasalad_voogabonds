package engine.model.components.viewable_interfaces;

import engine.IObservable;

public interface IViewableDamageDealer extends IViewable, IObservable<IViewableDamageDealer> {
	/**
	 * @return damage dealt by this damage dealer.
	 */
	public int getDamage();
	/**
	 * 
	 * @return damage arc of this damage dealer
	 */
	public double getDamageArc();
	/**
	 * 
	 * @return damage radius of this damage dealer.
	 */
	public double getDamageRadius();
}
