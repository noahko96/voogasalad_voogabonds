package engine.model.components.concrete;

import java.util.ArrayList;

import java.util.List;

import authoring.model.ComponentData;
import authoring.model.Hide;
import engine.IObserver;
import engine.model.components.AbstractComponent;
import engine.model.components.viewable_interfaces.IViewableTeam;
import engine.model.entities.IEntity;
import gamePlayerView.gamePlayerView.Router;
import engine.model.systems.TeamSystem;

/**
 * The purpose of this class is to manage which team an entity belongs to
 * This information could be used to determine which entities should be targeted
 * since you may not want to target an entity on your own team (Or maybe you do. We don't judge.)
 * @author matthewfaw
 * @author Weston
 */
public class TeamComponent extends AbstractComponent implements IViewableTeam {
	private String myTeamID;
	
	@Hide
	private transient List<IObserver<IViewableTeam>> myObservers;

	@Hide
	private transient TeamSystem mySystem;
	
	public TeamComponent(IEntity aEntity, TeamSystem teams, ComponentData componentData, Router router) {
	    super(aEntity, router);
		myTeamID = componentData.getFields().get("myTeamID");
		myObservers = new ArrayList<IObserver<IViewableTeam>>();
		mySystem = teams;
		teams.attachComponent(this);
	}
	
	/******************IViewableTeam interface********/
	@Override
	public String getTeamID() {
		return myTeamID;
	}

	
	/******************IObservable interface********/
	@Override
	public void attach(IObserver<IViewableTeam> aObserver) {
		myObservers.add(aObserver);
	}

	@Override
	public void detach(IObserver<IViewableTeam> aObserver) {
		myObservers.remove(aObserver);
	}

	@Override
	public void notifyObservers() {
		myObservers.forEach(observer -> observer.update(this));
	}
	
	/******************IComponent interface********/
	@Override
	public void delete() {
		mySystem.detachComponent(this);
	}
	
	@Override
	public void distributeInfo() {
		getRouter().distributeViewableComponent(this);
	}
}
