package engine.model.components.concrete;

import java.util.ArrayList;

import java.util.List;

import authoring.model.ComponentData;
import authoring.model.Hide;
import engine.IObserver;
import engine.model.components.AbstractComponent;
import engine.model.components.IComponent;
import engine.model.components.viewable_interfaces.IViewableDamageDealer;
import engine.model.entities.IEntity;
import engine.model.strategies.IDamageStrategy;
import engine.model.strategies.IPhysical;
import engine.model.systems.DamageDealingSystem;
import engine.model.systems.HealthSystem;
import engine.model.systems.PhysicalSystem;
import engine.model.systems.SpawningSystem;
import engine.model.systems.TeamSystem;
import engine.model.weapons.DamageInfo;
import gamePlayerView.gamePlayerView.Router;
import utility.Damage;

/**
 * The purpose of this class is to manage the information relevant to 
 * dealing damage to another entity
 * 
 * @author matthewfaw
 *
 */
public class DamageDealingComponent extends AbstractComponent implements IViewableDamageDealer {
	private int myDamage;
	private double myDamageArc;
	private double myDamageRadius;
	private transient IDamageStrategy myDamageStrategy;
	
	@Hide
	private List<IObserver<IViewableDamageDealer>> myObservers;
	
	private boolean explodesOnEnemies;
	private boolean explodesOnAllies;
	private boolean diesOnExplosion;

	@Hide
	private transient HealthSystem myHealthSystem;
	
	@Hide
	private transient PhysicalSystem myPhysicalSystem;
	@Hide
	private transient DamageDealingSystem myDamageSystem;
	@Hide
	private transient SpawningSystem myCreators;
	@Hide
	private transient TeamSystem myTeams;
	
	public DamageDealingComponent(IEntity aEntity, 
			DamageDealingSystem damageDealingSystem,
			HealthSystem healthSysytem,
			TeamSystem teams,
			PhysicalSystem physicalSystem,
			SpawningSystem creators,
			ComponentData data,
			Router router
			) {
		super(aEntity, router);
		
		myObservers = new ArrayList<IObserver<IViewableDamageDealer>>();
		myDamageSystem = damageDealingSystem;
		myHealthSystem = healthSysytem;
		myPhysicalSystem = physicalSystem;
		myCreators = creators;
		myTeams = teams;
		
		myDamage = Integer.parseInt(data.getFields().get("myDamage"));
		myDamageArc = Double.parseDouble(data.getFields().get("myDamageArc"));
		myDamageRadius = Double.parseDouble(data.getFields().get("myDamageRadius"));
		myDamageStrategy = damageDealingSystem.newStrategy(data.getFields().get("myDamageStrategy"));
		
		explodesOnEnemies = Boolean.parseBoolean(data.getFields().get("explodesOnEnemies"));
		explodesOnAllies = Boolean.parseBoolean(data.getFields().get("explodesOnAllies"));
		diesOnExplosion = Boolean.parseBoolean(data.getFields().get("diesOnExplosion"));
		
		damageDealingSystem.attachComponent(this);
	}
	
	/**
	 * gets the amount of damage this entity inflicts on its target
	 * @return the damage value
	 */
	public Damage getTargetDamage()
	{
		return myDamageStrategy.getTargetDamage(myDamage);
	}
	
	public Damage getDamageTo(IPhysical dealer, IPhysical taker)
	{
		return myDamageStrategy.getAoEDamage(dealer, taker, myDamage);
	}

	public DamageInfo explode(IComponent target) {
		DamageInfo result = new DamageInfo();
		
		if ((explodesOnEnemies && myTeams.areEnemies(this, target)) || (explodesOnAllies && myTeams.areAllies(this, target))) {
			result.add(myHealthSystem.dealDamageTo(target, getTargetDamage()));
			result.add(explodeNoTarget());
		}
		
		myCreators.updateStats(this, result);
		if (diesOnExplosion) {
			getEntity().delete();
		}
		return result;
	}

	public DamageInfo explode() {
		DamageInfo result = explodeNoTarget();
		
		myCreators.updateStats(this, result);
		if (diesOnExplosion) {
			getEntity().delete();
		}
		return result;
	}
	
	private DamageInfo explodeNoTarget() {
		DamageInfo result = new DamageInfo();

		//Deal damage to anyone in blast radius
		PhysicalComponent myPhysical = myPhysicalSystem.getComponent(this);
		if (myPhysicalSystem.getComponent(this) != null) {
			List<PhysicalComponent> inBlast = myPhysicalSystem.withinRange(myPhysical.getPosition(), myDamageRadius, myPhysical.getHeading(), myDamageArc);
			for (PhysicalComponent p: inBlast){
				result.add(myHealthSystem.dealDamageTo(p, getDamageTo(myPhysical, p)));
			}
		}
		return result;
	}

	/******************IViewableDamageDealer interface********/
	@Override
	public double getDamageRadius() {
		return myDamageRadius;
	}
	
	@Override
	public int getDamage() {
		return myDamage;
	}

	@Override
	public double getDamageArc() {
		return myDamageArc;
	}

	/******************IObservable interface********/
	@Override
	public void attach(IObserver<IViewableDamageDealer> aObserver) {
		myObservers.add(aObserver);
	}

	@Override
	public void detach(IObserver<IViewableDamageDealer> aObserver) {
		myObservers.remove(aObserver);
	}

	@Override
	public void notifyObservers() {
		myObservers.forEach(observer -> observer.update(this));
	}

	/******************IComponent interface********/
	public void delete() {
		myDamageSystem.detachComponent(this);
	}
	
	@Override
	public void distributeInfo() {
		getRouter().distributeViewableComponent(this);
	}
}
