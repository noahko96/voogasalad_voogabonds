package engine.model.machine;

import java.util.ArrayList;

import java.util.List;

import authoring.model.MachineData;
import engine.IObserver;
import engine.controller.timeline.TimelineController;
import engine.model.collision_detection.ICollidable;
import engine.model.components.viewable_interfaces.IViewable;
import engine.model.playerinfo.IModifiablePlayer;
import engine.model.strategies.IMovable;
import engine.model.systems.IRegisterable;
import engine.model.systems.ISystem;
import engine.model.weapons.DamageInfo;
import engine.model.weapons.IKillerOwner;
import engine.model.weapons.WeaponFactory;
import javafx.util.Pair;
import utility.Damage;
import utility.Point;

@Deprecated //do not use this class--using ECS now
abstract public class Machine implements IViewableMachine, IModifiableMachine, IMovable, IObserver<TimelineController>, ICollidable, IKillerOwner, IRegisterable {
	private List<IObserver<IViewable>> myObservers;
	private IModifiablePlayer myModifiablePlayer;
	//private WeaponFactory myArmory;
	
	private String myName;
	private String myImagePath;
	//private IWeapon myWeapon;
	private Health myHealth;
	
	//private IMovementStrategy myMoveCalc;
	//private List<String> myValidTerrains;
	private double myCollisionRadius;
	private double myTurnSpeed;
	private double myMoveSpeed;
	private double myHeading;
	private Point myPosition;

	List<ISystem> mySystems;
	
	public Machine (WeaponFactory armory, IModifiablePlayer owner, MachineData data, Point initialPosition) {
		myObservers = new ArrayList<IObserver<IViewable>>();

		myModifiablePlayer = owner;
		//myArmory = armory;
		
		myName = data.getName();
		myImagePath = data.getImagePath();
		//myWeapon = myArmory.newWeapon(data.getWeaponName(), this);
		myHealth = new Health(data.getMaxHealth());
		
		//myMoveCalc = StrategyFactory.movementStrategy(data.getMoveStrategyName());
		//myValidTerrains = data.getTerrainList();
		myCollisionRadius = data.getCollisionRadius();
		myTurnSpeed = data.getTurnSpeed();
		myMoveSpeed = data.getMoveSpeed();
		myHeading = 0;
		myPosition = initialPosition;
	}
	
	public Machine(String name, int maxHealth) {
		myName = name;
		myHealth = new Health(maxHealth);
	}
	
	public String getName() {
		return myName;
	}
	
	@Deprecated
	public void setPossibleTerrains(List<String> l) {
		//myValidTerrains = l;
	}
	
	@Override
	public void update(TimelineController time) {
		move();
//		myWeapon.fire(myHeading, myPosition);
	}
	
	@Deprecated //TODO
	private void move() {
		Pair<Double, Point> nextMove = new Pair<Double, Point>(0.0, null);//myMoveCalc.nextMove(this);
		myPosition = nextMove.getValue();
		myHeading = nextMove.getKey();
//		notifyObservers();
	}
	
//	@Override
//	public String getImagePath() {
//		return myImagePath;
//	}
	@Override
	public double getHeading() {
		return myHeading;
	}
	@Override
	public double getHealth() {
		return myHealth.getHealth();
	}
	
	@Override
	public DamageInfo takeDamage(Damage dmg) {
		double dmgTaken = myHealth.takeDamage(dmg);
		
		int unitsKilled = 0;
		int moneyEarned = 0;
		
		if (myHealth.getHealth() <= 0){
			unitsKilled++;
			moneyEarned = die();
		}
		
		if (dmgTaken > 0)
			return new DamageInfo(dmgTaken, 0, unitsKilled, moneyEarned);
		else
			return new DamageInfo(0, dmgTaken, unitsKilled, moneyEarned);
	}

	
	abstract protected int die();
	
	public double getDistanceTo(Point p) {
		return getPosition().euclideanDistance(p) - myCollisionRadius;
	}
	
	@Override
	public IModifiablePlayer getOwner() {
		return myModifiablePlayer;
	}

	@Deprecated//TODO
	public boolean onMap() {
		return true;
	}
	
	@Override
	public Point getPosition() {
		return myPosition;
	}
	
	@Override
	public double getTurnSpeed() {
		return myTurnSpeed;
	}
	@Override
	public double getMoveSpeed() {
		return myMoveSpeed;
	}
	/*
	@Override
	public double getCollisionRadius() {
		return myCollisionRadius;
	}
	
	@Override
	public List<String> getValidTerrains() {
		return myValidTerrains;
	}
	@Override
	public void setPosition(Pair<Double, Point> p) {
		myPosition = p.getValue();
		myHeading = p.getKey();
	}
	@Override
	public double getSize() {
		return myCollisionRadius;
	}
	*/
	
	/************** IObservable interface ****************/
//	@Override
//	public void attach(IObserver<IViewable> aObserver)
//	{
//		myObservers.add(aObserver);
//		notifyObservers();
//	}
//	@Override
//	public void detach(IObserver<IViewable> aObserver)
//	{
//		myObservers.remove(aObserver);
//	}
//	@Override
//	public void notifyObservers()
//	{
//		myObservers.forEach(observer -> observer.update(this));
//	}

	
	/********** ICollidable Interface Methods ************/
	/*
	@Override
	public void collideInto(ICollidable movedCollidable) {
		movedCollidable.collideInto(this);
	}
	*/
	/*
	@Override
	public void collideInto(Machine unmovedCollidable) {
		//do nothing for now since machines will not deal dmg
	}
	@Override
	public void collideInto(Projectile unmovedCollidable) {
		//do nothing for now since machines will not deal dmg
		//Should the Machine running into the projectile trigger an explode
		//unmovedCollidable.collideInto(this);
	}
	*/
	
	public DamageInfo notifyDestroy(DamageInfo result) {
		return result;
	}

	protected void upgrade(MachineData upgradeData) {
		myName = upgradeData.getName();
		//myWeapon = myArmory.newWeapon(upgradeData.getWeaponName(), this);
		myHealth.setMaxHealth(upgradeData.getMaxHealth());
		
		//myMoveCalc = StrategyFactory.movementStrategy(upgradeData.getMoveStrategyName());
		//myValidTerrains = upgradeData.getTerrainList();
		myCollisionRadius = upgradeData.getCollisionRadius();
		myTurnSpeed = upgradeData.getTurnSpeed();
		myMoveSpeed = upgradeData.getMoveSpeed();
	}
	
	/********** IRegisterable Interface Methods ************/
	@Override
	public void unregisterMyself() {
		for(ISystem s: mySystems) {
//			s.unregister(this);
			mySystems.remove(s);
		}
	}
}