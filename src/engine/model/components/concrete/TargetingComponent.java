package engine.model.components.concrete;

import java.util.ArrayList;

import java.util.List;

import authoring.model.ComponentData;
import authoring.model.Hide;
import engine.IObserver;
import engine.model.components.AbstractComponent;
import engine.model.components.ITargeting;
import engine.model.components.viewable_interfaces.IViewableTargeting;
import engine.model.entities.IEntity;
import engine.model.strategies.IPosition;
import engine.model.strategies.ITargetingStrategy;
import engine.model.systems.PhysicalSystem;
import engine.model.systems.TargetingSystem;
import engine.model.systems.TeamSystem;
import gamePlayerView.gamePlayerView.Router;

/**
 * A component that finds targets for an entity
 * @author Weston
 *
 */
public class TargetingComponent extends AbstractComponent implements ITargeting, IViewableTargeting {

	private double mySightRange;
	private double mySightWidth;
	private boolean myTargetsEnemies;
	private transient ITargetingStrategy myTargetingStrategy;
	
	@Hide
	private transient PhysicalSystem myPhysical;
	@Hide
	private transient TargetingSystem myTargeting;
	@Hide
	private transient TeamSystem myTeams;
	
	@Hide
	private List<IObserver<IViewableTargeting>> myObservers;
	
	public TargetingComponent(IEntity aEntity, TargetingSystem target, PhysicalSystem physical, TeamSystem teams, ComponentData componentData, Router router) {
		super(aEntity, router);
		myTargeting = target;
		myObservers = new ArrayList<IObserver<IViewableTargeting>>();
		
		myPhysical = physical;
		myTeams = teams;
		
		mySightRange = Double.parseDouble(componentData.getFields().get("mySightRange"));
		mySightWidth = Double.parseDouble(componentData.getFields().get("mySightWidth"));
		myTargetsEnemies = Boolean.parseBoolean(componentData.getFields().get("myTargetsEnemies"));
		myTargetingStrategy = target.newStrategy(componentData.getFields().get("myTargetingStrategy"));
				
		target.attachComponent(this);
	}
	/******************ITargeting interface********/
	@Override
	public IPosition getTarget() {
		if (myPhysical.getComponent(this) != null)
			return myTargetingStrategy.target(myPhysical, myTeams, myPhysical.getComponent(this), this);
		return null;
	}

	@Override
	public double getTargetWedgeWidth() {
		return mySightWidth;
	}

	@Override
	public double getTargetWedgeRadius() {
		return mySightRange;
	}
	
	@Override
	public boolean targetsEnemies() {
		return myTargetsEnemies;
	}

	/******************IObservable interface********/
	@Override
	public void attach(IObserver<IViewableTargeting> aObserver) {
		myObservers.add(aObserver);
	}

	@Override
	public void detach(IObserver<IViewableTargeting> aObserver) {
		myObservers.remove(aObserver);
	}

	@Override
	public void notifyObservers() {
		myObservers.forEach(observer -> observer.update(this));
	}

	/******************IComponent interface********/
	@Override
	public void distributeInfo() {
		getRouter().distributeViewableComponent(this);
	}
	
	@Override
	public void delete() {
		myTargeting.detachComponent(this);
	}	
}
