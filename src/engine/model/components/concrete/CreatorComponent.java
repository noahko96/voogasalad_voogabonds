package engine.model.components.concrete;

import java.util.ArrayList;

import java.util.List;

import authoring.model.ComponentData;
import authoring.model.Hide;
import engine.IObserver;
import engine.model.components.AbstractComponent;
import engine.model.components.ICreator;
import engine.model.entities.ConcreteEntity;
import engine.model.components.viewable_interfaces.IViewableCreator;
import engine.model.entities.EntityFactory;
import engine.model.entities.IEntity;
import engine.model.strategies.IPosition;
import engine.model.strategies.ISpawningStrategy;
import engine.model.systems.MovementSystem;
import engine.model.systems.PhysicalSystem;
import engine.model.systems.SpawningSystem;
import engine.model.systems.TargetingSystem;
import gamePlayerView.gamePlayerView.Router;
import engine.model.weapons.DamageInfo;

/**
 * The purpose of this class is to manage the work necessary
 * to creating child entites
 * An example of where this class is used:
 * if a certain entity needs to fire a projectile, then this component would
 * be responsible for that action
 * @author matthewfaw
 * @author Weston
 *
 */
public class CreatorComponent extends AbstractComponent implements ICreator, IViewableCreator {

	private transient ISpawningStrategy mySpawningStrategy;
	private boolean homingProjectiles;
	private int myTimeBetweenSpawns;
	private String mySpawnName;

	@Hide
	private transient IPosition myTarget;
	@Hide
	private int myTimeSinceSpawning;
	
	@Hide
	private transient EntityFactory myEntityFactory;
	@Hide
	private transient PhysicalSystem myPhysical;
	@Hide
	private transient TargetingSystem myTargeting;
	@Hide
	private transient MovementSystem myMovement;
	@Hide
	private transient SpawningSystem mySpawning;
	@Hide
	private List<ConcreteEntity> myChildren;
	@Hide
	private DamageInfo myStats;
	@Hide
	private List<IObserver<IViewableCreator>> myObservers;

	
	public CreatorComponent(IEntity aEntity, SpawningSystem spawning,
			PhysicalSystem physical,
			TargetingSystem targeting,
			MovementSystem movement,
			ComponentData data,
			Router router) {
		super(aEntity, router);
		
		myObservers = new ArrayList<IObserver<IViewableCreator>>();
		
		mySpawning = spawning;
		myPhysical = physical;
		myTargeting = targeting;
		myMovement = movement;
		myEntityFactory = mySpawning.getFactory();
		
		mySpawnName = data.getFields().get("mySpawnName");
		myTimeBetweenSpawns = Integer.parseInt(data.getFields().get("myTimeBetweenSpawns"));
		mySpawningStrategy = mySpawning.newStrategy(data.getFields().get("mySpawningStrategy"));
		homingProjectiles = Boolean.parseBoolean(data.getFields().get("homingProjectiles"));
		
		myTimeSinceSpawning = 0;
		myChildren = new ArrayList<ConcreteEntity>();
		myStats = new DamageInfo();
		spawning.attachComponent(this);
	}

	/**
	 * this method creates a new entity based on the
	 * entity spawning strategy.  This entity will be launched
	 * towards the target determined by the targeting strategy
	 */
	public void spawnIfReady() {
		myTarget = myTargeting.getTarget(this);
		if (myPhysical.getComponent(this) != null)
			if (myTarget != null && myTimeSinceSpawning >= myTimeBetweenSpawns) {
				myTimeSinceSpawning = 0;
				if (homingProjectiles)
					myChildren.add(mySpawningStrategy.spawn(myEntityFactory, myTarget, myMovement, myPhysical, this));
				else
					myChildren.add(mySpawningStrategy.spawn(myEntityFactory, myTarget.getPosition(), myMovement, myPhysical, this));
					
		} else{
			myTimeSinceSpawning++;
		}
		
	}
	/******************ICreator interface********/
	@Override
	public boolean isParent(IEntity entity) {
		return myChildren.contains(entity);
	}

	@Override
	public void setTarget(IPosition target) {
		myTarget = target;
	}

	@Override
	public IPosition getTarget() {
		return myTarget;
	}

	@Override
	public String getSpawnName() {
		return mySpawnName;
	}


	@Override
	public int getTimeBetweenSpawns() {
		return myTimeBetweenSpawns;
	}
	
	@Override
	public void updateStats(DamageInfo data) {
		myStats.add(data);
	}
	
	@Override
	public DamageInfo getStats() {
		return myStats;
	}

	/******************IObservable interface********/
	@Override
	public void attach(IObserver<IViewableCreator> aObserver) {
		myObservers.add(aObserver);
	}

	@Override
	public void detach(IObserver<IViewableCreator> aObserver) {
		myObservers.remove(aObserver);
	}

	@Override
	public void notifyObservers() {
		myObservers.forEach(observer -> observer.update(this));
	}

	/******************Component interface********/
	@Override
	public void delete() {
		mySpawning.detachComponent(this);
	}

	@Override
	public void distributeInfo() {
		getRouter().distributeViewableComponent(this);
	}
}
