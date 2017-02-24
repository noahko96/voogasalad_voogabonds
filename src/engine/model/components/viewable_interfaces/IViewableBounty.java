package engine.model.components.viewable_interfaces;

import engine.IObservable;

public interface IViewableBounty extends IViewable, IObservable<IViewableBounty>{

	public int getBounty();
	public int getLivesTaken();
	public int getPoints();
}
