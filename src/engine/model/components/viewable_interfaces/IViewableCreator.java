package engine.model.components.viewable_interfaces;

import engine.IObservable;

public interface IViewableCreator extends IViewable, IObservable<IViewableCreator> {
	public int getTimeBetweenSpawns();
}
