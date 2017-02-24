package engine.model.components.concrete;

import java.util.ArrayList;
import java.util.List;

import authoring.model.ComponentData;
import authoring.model.Hide;
import engine.IObserver;
import engine.model.collision_detection.ICollidable;
import engine.model.components.AbstractComponent;
import engine.model.components.viewable_interfaces.IViewableCollidable;
import engine.model.entities.IEntity;
import engine.model.systems.BountySystem;
import engine.model.systems.CollisionDetectionSystem;
import engine.model.systems.DamageDealingSystem;
import engine.model.systems.HealthSystem;
import engine.model.systems.PhysicalSystem;
import gamePlayerView.gamePlayerView.Router;
import utility.Point;

/**
 * The purpose of this class is to encapsulate the information relevant
 * to entities that can collide
 * These entities will register with the CollisionDetectionSystem, and this
 * class will define what happens when a collision occurs
 * @author matthewfaw
 * @author Weston
 *
 */
public class CollidableComponent extends AbstractComponent implements ICollidable, IViewableCollidable {
	private double myCollisionRadius;
	
	@Hide
	private transient PhysicalSystem myPhysicalSystem;
	@Hide
	private transient DamageDealingSystem myDamageDealingSystem;
	@Hide
	private transient CollisionDetectionSystem myCollidable;
	
	@Hide
	private List<IObserver<IViewableCollidable>> myObservers;
	
	public CollidableComponent(IEntity aEntity,
			CollisionDetectionSystem collisionDetectionSystem, 
			PhysicalSystem physicalSystem,
			HealthSystem healthSystem,
			DamageDealingSystem damageDealingSystem, 
			BountySystem rewardSystem,
			ComponentData data,
			Router router) {
		super(aEntity, router);
		myObservers = new ArrayList<IObserver<IViewableCollidable>>();
		myCollidable = collisionDetectionSystem;
		myPhysicalSystem = physicalSystem;
		myDamageDealingSystem = damageDealingSystem;
		
		myCollisionRadius = Double.parseDouble(data.getFields().get("myCollisionRadius"));
		
		collisionDetectionSystem.attachComponent(this);
	}

	//*******************ICollidable interface***********//
	@Override
	public double getCollisionRadius()
	{
		return myCollisionRadius;
	}
	
	@Override
	public void checkCollision(CollidableComponent unmovedCollidable) {
		if (!equals(unmovedCollidable) && intersects(unmovedCollidable))
			collideInto(unmovedCollidable);
	}
	
	/**
	 * A method to determine if collidables a and b intersect
	 * @param this: first entity
	 * @param c: second entity
	 * @return true if a and b are in either of each other's 
	 * collision radii; false if not
	 */
	private boolean intersects(CollidableComponent c) {
		if (myPhysicalSystem.getComponent(this) != null && myPhysicalSystem.getComponent(c) != null) {
			Point a = myPhysicalSystem.getComponent(this).getPosition();
			Point b = myPhysicalSystem.getComponent(c).getPosition();
			
			double a_r = getCollisionRadius();
			double b_r = c.getCollisionRadius();
	
			if (myCollisionRadius > 0)
				return (a_r + b_r) >= a.euclideanDistance(b);
			else
				return a.equals(b);
		} else
			return false;
	}
	
	/**
	 * When one this collides into an unmovedCollidable, they deal damage to each other, if they can.
	 * @param unmovedCollidable
	 */
	private void collideInto(CollidableComponent unmovedCollidable) {
		dealDamage(this, unmovedCollidable);
		dealDamage(unmovedCollidable, this);
	}

	/**
	 * Dealing damage uses the DamageDealingSystem to check if a can damage b
	 * @param a
	 * @param b
	 */
	private void dealDamage(CollidableComponent a, CollidableComponent b) {
		myDamageDealingSystem.dealDamageToTarget(a, b);
	}

	/******************IObservable interface********/
	@Override
	public void attach(IObserver<IViewableCollidable> aObserver) {
		myObservers.add(aObserver);
	}

	@Override
	public void detach(IObserver<IViewableCollidable> aObserver) {
		myObservers.remove(aObserver);
	}

	@Override
	public void notifyObservers() {
		myObservers.forEach(observer -> observer.update(this));
	}

	/******************IComponent interface********/
	@Override
	public void delete() {
		myCollidable.detachComponent(this);
	}
	
	@Override
	public void distributeInfo() {
		getRouter().distributeViewableComponent(this);
	}
}
