package engine.model.components.viewable_interfaces;

import engine.IObservable;

public interface IViewableTeam extends IViewable, IObservable<IViewableTeam> {
	public String getTeamID();
}
