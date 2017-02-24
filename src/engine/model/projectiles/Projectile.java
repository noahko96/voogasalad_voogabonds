package engine.model.projectiles;

/**
 * This class contains the information a projectile needs to move, deal damage to enemies, and be represented in the View.
 * @author Weston
 */
@Deprecated
public class Projectile /* implements IViewable, IMovable, IObserver<TimelineController>, ICollidable, ISystem, IRegisterable*/ {
	/*
	private static final double COLLISION_ERROR_TOLERANCE = Math.exp(-6);
	
	//private List<IObserver<IViewable>> myObservers;
	
	private String myImagePath;
	private IKillerOwner myOwner;
	private IModifiablePlayer myPlayer;
	private Machine myTarget;
	//private MapMediator myMap;
	
	//private IMovementStrategy myMovementCalc;
	private double mySpeed;
	private double myTurnSpeed;
	private double myTraveled;
	
	private double myHeading;
	private Point myLocation;
	private double myCollisionRadius;
	
	private IDamageStrategy myDamageCalc;
	private double myDamage;
	private int myMaxRange;
	//private int myAoERadius;
	
	List<String> myValidTerrain;
	
	List<ISystem> mySystems;
	
	List<IRegisterable> myRegisterables;
	
	public Projectile(ProjectileData data, Machine target, IKillerOwner owner, TimelineController time, MapMediator map) {
		
		myObservers = new ArrayList<IObserver<IViewable>>();
		
		myImagePath = data.getImagePath();
		myTarget = target;
		myOwner = owner;
		//myPlayer = myOwner.getOwner();
		//myMap = map;
		
		//myMovementCalc = StrategyFactory.movementStrategy(data.getMovementStrategy());
		myLocation = myOwner.getPosition();
		myHeading = myOwner.getHeading();
		myTraveled = 0;
		mySpeed = data.getSpeed();
		myTurnSpeed = data.getTurnSpeed();
		myCollisionRadius = data.getCollisionRadius();
		
		myValidTerrain = data.getValidTerrains();
		myDamageCalc = StrategyFactory.damageStrategy(data.getDamageStrategy());
		myMaxRange = data.getMaxRange();
		//myAoERadius = data.getAreaOfEffectRadius();
		myDamage = data.getDamage();	
		
		time.attach(this);
	}
//	@Override
//	public double getHeading() {
//		return myHeading;
//	}
//	@Override
//	public Point getPosition() {
//		return myLocation;
//	}
//	@Override
//	public String getImagePath() {
//		return myImagePath;
//	}
	@Override
	public Point getGoal() {
		return myTarget == null ? null : myTarget.getPosition();
	}
	@Override
	public double getTurnSpeed() {
		return myTurnSpeed;
	}
	@Override
	public double getMoveSpeed() {
		return mySpeed;
	}
	
	@Override
	public double getCollisionRadius() {
		return myCollisionRadius;
	}
	@Override
	public void update(TimelineController aChangedObject) {
		advance();
		
		//TODO: Remove if goes too far off map
	}
	
	private Point advance() {
		Pair<Double, Point> nextMove = new Pair<Double, Point>(0.0, null);//myMovementCalc.nextMove(this, this);
		
		myTraveled += myLocation.euclideanDistance(nextMove.getValue());
		myHeading = nextMove.getKey();
		myLocation = nextMove.getValue();
		
		if (myTarget != null && myTarget.getDistanceTo(myLocation) <= COLLISION_ERROR_TOLERANCE) {
			hitUnit(myTarget);
		} else if (myTraveled >= myMaxRange) {
			explode();
		}
		
		return myLocation;
	}
	
	private void hitUnit(Machine hit) {
		DamageInfo result = new DamageInfo();
		
		result.add(hit.takeDamage(myDamageCalc.getTargetDamage(myDamage)));
		result.add(explode());
		
		myOwner.notifyDestroy(result);
//		getOwner().updateAvailableMoney(result.getMoney());
	}
	
	private DamageInfo explode() {
		List<Machine> targets = myMap.withinRange(getPosition(), myAoERadius);
		
		DamageInfo result = new DamageInfo();
		
		for (Machine m: targets) {

			Damage toDeal;
//			if (getOwner().isAlly(m.getOwner()))
//				toDeal = myDamageCalc.getAoEAllyDamage(this, myTarget.getPosition(), myDamage);
//			else
//				toDeal = myDamageCalc.getAoEDamage(this, myTarget.getPosition(), myDamage);
			
//			result = result.add(m.takeDamage(toDeal));
		}
		// destroySelf();
		// remove references
		unregisterMyself();
		return result;
		
		return null;
		
		
	}

	@Override
	public List<String> getValidTerrains() {
		return myValidTerrain;
	}
	@Override
	public void setPosition(Pair<Double, Point> aLocation) {
		myLocation = aLocation.getValue();
		myHeading = aLocation.getKey();
	}
	
	@Override
	public double getSize() {
		return myCollisionRadius;
	}
	@Override
	public IModifiablePlayer getOwner() {
		return myPlayer;
	}

	@Override
	public void collideInto(Machine unmoved) {
		//This method is a bit of a mess; refactor?
		if (getOwner().isAlly(myTarget.getOwner()))
			if (getOwner().isAlly(unmoved.getOwner()))
				//Projectile targets allies, unmoved is an ally
				hitUnit((Machine) unmoved);
		else
			if (!getOwner().isAlly(unmoved.getOwner()))
				//Projectile targets enemies, unmoved is an enemy
				hitUnit((Machine) unmoved);
	}
	
//	@Override
//	public void collideInto(CollidableComponent unmovedCollidable) {
//		unmovedCollidable.collideInto(unmovedCollidable);
//	}

	@Override
	public void attach(IObserver<IViewable> aObserver) {
		myObservers.add(aObserver);
	}
	@Override
	public void detach(IObserver<IViewable> aObserver) {
		myObservers.remove(aObserver);
	}
	@Override
	public void notifyObservers() {
		myObservers.forEach(observer -> observer.update(this));
	}
	
//	@Override
//	public void register(IRegisterable registerable) {
//		myRegisterables.add(registerable);
//	}
//	@Override
//	public void unregister(IRegisterable registerable) {
//		myRegisterables.remove(registerable);
//	}
	
	@Override
	public void unregisterMyself() {
		for(ISystem s: mySystems) {
//			s.unregister(this);
			mySystems.remove(s);
		}
	}
//	@Override
//	public IEntity getEntity() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	*/
}
