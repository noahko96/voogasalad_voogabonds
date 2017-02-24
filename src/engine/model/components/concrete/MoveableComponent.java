package engine.model.components.concrete;

import java.util.ArrayList;
import java.util.List;

import authoring.model.ComponentData;
import authoring.model.Hide;
import engine.IObserver;
import engine.model.components.AbstractComponent;
import engine.model.components.viewable_interfaces.IViewableMovable;
import engine.model.entities.IEntity;
import engine.model.strategies.IMovable;
import engine.model.strategies.IMovementStrategy;
import engine.model.strategies.IPhysical;
import engine.model.strategies.IPosition;
import engine.model.systems.BountySystem;
import engine.model.systems.CollisionDetectionSystem;
import engine.model.systems.DamageDealingSystem;
import engine.model.systems.MovementSystem;
import engine.model.systems.PhysicalSystem;
import engine.model.systems.TargetingSystem;
import gamePlayerView.gamePlayerView.Router;
import javafx.util.Pair;
import utility.Point;

/**
 * A component that defines an entity's ability to move
 * @author matthewfaw, Weston
 *
 */
public class MoveableComponent extends AbstractComponent implements IMovable, IViewableMovable {
	@Hide
	private transient MovementSystem myMovement;
	@Hide
	private transient PhysicalSystem myPhysical;
	@Hide
	private transient TargetingSystem myTargeting;
	@Hide
	private transient CollisionDetectionSystem myCollision;
	@Hide
	private transient DamageDealingSystem myDamage;
	@Hide
	private transient BountySystem myBounty;
	
	private transient IMovementStrategy myMovementCalc;
	private double myTurnSpeed;
	private double myMoveSpeed;
	
	@Hide
	private transient IPosition myGoal;
	
	//NOTE: So that entities can die after traveling a certain distance.
	private boolean explodesAtMaxDistance;
	private double myMaxDistance;
	@Hide
	private double myMovedDistance;
	private boolean removeOnGoal;
	private boolean explodesOnGoal;

	
	@Hide
	private List<IObserver<IViewableMovable>> myObservers;

	public MoveableComponent(IEntity aEntity, 
			MovementSystem movement,
			PhysicalSystem physical,
			TargetingSystem targeting,
			CollisionDetectionSystem collision,
			BountySystem bounty,
			Router router,
			DamageDealingSystem damage,
			ComponentData data
			) throws ClassNotFoundException {
		super(aEntity, router);
		
		myMovement = movement;
		myPhysical = physical;
		myTargeting = targeting;
		myCollision = collision;
		myBounty = bounty;
		myDamage = damage;
		
		myMovedDistance = 0;
		myMaxDistance = Double.parseDouble(data.getFields().get("myMaxDistance"));
		explodesAtMaxDistance = Boolean.parseBoolean(data.getFields().get("explodesAtMaxDistance"));
		
		explodesOnGoal = Boolean.parseBoolean(data.getFields().get("explodesOnGoal"));
		removeOnGoal = Boolean.parseBoolean(data.getFields().get("removeOnGoal"));
		
		myTurnSpeed = Double.parseDouble(data.getFields().get("myTurnSpeed"));
		myMoveSpeed = Double.parseDouble(data.getFields().get("myMoveSpeed"));
		myMovementCalc = movement.newStrategy(data.getFields().get("myMovementCalc"));
		
		myObservers = new ArrayList<IObserver<IViewableMovable>>();
		
		movement.attachComponent(this);
	}
	

	
	public void setGoal(IPosition p) {
		myGoal = p;
	}

	public void move() {
		setGoal(myTargeting.getTarget(this));
		
		//This means my target was deleted. (And I don't have any way to find a new one)
		if ((myGoal != null && myGoal.getPosition() == null))
			getEntity().delete();
		
		PhysicalComponent p = myPhysical.getComponent(this);
		if (p != null)
			moveTowardsGoal(p);
		
		myCollision.checkCollision(p);
		
		if (myMovedDistance + Math.exp(0) >= myMaxDistance) {
				if (explodesAtMaxDistance)
					myDamage.explode(this);
				else
					getEntity().delete();
		}
				
		if (explodesOnGoal && atGoal())
			myDamage.explode(this);
		
		if (removeOnGoal && atGoal()) {
			myBounty.pillagePlayerBase(this);
			getEntity().delete();
		}	
	}
	
	/********************IMovable interface***********/
	@Override
	public Point getGoalPoint() {
		return (myGoal == null) ? null : myGoal.getPosition();
	}
	
	@Override
	public IPosition getGoal() {
		return myGoal;
	}

	@Override
	public double getTurnSpeed() {
		return myTurnSpeed;
	}

	@Override
	public double getMoveSpeed() {
		return myMoveSpeed;
	}
	
	/******************IObservable interface********/
	@Override
	public void attach(IObserver<IViewableMovable> aObserver) {
		myObservers.add(aObserver);
	}

	@Override
	public void detach(IObserver<IViewableMovable> aObserver) {
		myObservers.remove(aObserver);
	}

	@Override
	public void notifyObservers() {
		myObservers.forEach(observer -> observer.update(this));
	}

	/******************IViewableMovable interface********/
	@Override
	public IMovementStrategy getMovementStrategy() {
		return myMovementCalc;
	}
	
	/******************IComponent interface********/
	@Override
	public void delete() {
		myMovement.detachComponent(this);
	}
	
	@Override
	public void distributeInfo() {
		getRouter().distributeViewableComponent(this);
	}

	/**
	 * Checks if this entity has reached it's goal or not
	 * @return true iff yes
	 */
	private boolean atGoal() {
		IPhysical p = myPhysical.getComponent(this);
		if (p != null)
			return myPhysical.getComponent(this).getPosition().equals(getGoalPoint());
		else
			return false;
	}
	
	/**
	 * @param p the physical part of this entity
	 * @return the next point the entity should  move to and the corresponding heading
	 */
	private Pair<Double, Point> moveTowardsGoal(IPhysical p) {
		if ((myMaxDistance - myMovedDistance) < myMoveSpeed)
			myMoveSpeed = Math.max(0.0, myMovedDistance - myMaxDistance + Math.exp(-6));
		
		Pair<Double, Point> nextMove = myMovementCalc.nextMove(this, p);
		myMovedDistance += nextMove.getValue().euclideanDistance(p.getPosition());

		p.setPosition(nextMove);
		return nextMove;
	}
}
