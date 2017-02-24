package engine.model.weapons;

import authoring.model.WeaponData;
import engine.model.game_environment.MapMediator;
import engine.model.playerinfo.IModifiablePlayer;
import engine.model.projectiles.ProjectileFactory;
import utility.Point;

/**
 * A class to create projectiles and keep track of their effects on enemies.
 * @author Weston
 *
 */
@Deprecated
public class Weapon implements IWeapon, IKillerOwner {
	private IKillerOwner myMachine;
	/*
	private MapMediator myMap;
	
	private ProjectileFactory myProjectileFactory;
	private ITargetingStrategy myTargetStrategy;
	private String myProjectile;
	private int myFireRate;
	private int myTimeToFire;
	private double myRange;
	*/
	
	private double myCareerKills;
	private double myCareerDamage;
	private double myCareerEarnings;
	
	public Weapon(WeaponData data, IKillerOwner owner, ProjectileFactory projFactory, MapMediator map) {
		/*
		myRange = data.getRange();
		myFireRate = data.getFireRate();
		myProjectile = data.getProjectileName();
		
		myMachine = owner;
		myProjectileFactory = projFactory;
		*/
		
		//TODO: Get strategy name from data
		//myTargetStrategy = StrategyFactory.targetStrategy("");
		
		myCareerKills = 0;
		myCareerDamage = 0;
		myCareerEarnings = 0;
		//myTimeToFire = 0;
	}

	@Override
	public void fire(double heading, Point position) {
		/*
		List<Machine> targets = myMap.withinRange(getPosition(), myRange);
		
		if (myTimeToFire <= 0 && targets.size() > 0){
			
			Machine target = myTargetStrategy.target(targets, heading, position);
			
			myProjectileFactory.newProjectile(myProjectile, target, this);
			
			myTimeToFire = myFireRate;
		} else {
			myTimeToFire--;
		}
		*/
		
	}

	@Override
	public DamageInfo notifyDestroy(DamageInfo dmg) {
		myCareerKills += dmg.getKills();
		myCareerDamage += dmg.getDamage();
		myCareerEarnings += dmg.getMoney();
		
		return myMachine.notifyDestroy(dmg);
	}

	@Override
	public double getHeading() {
		return myMachine.getHeading();
	}

	@Override
	public Point getPosition() {
		return myMachine.getPosition();
	}

	@Override
	public double getKills() {
		return myCareerKills;
	}

	@Override
	public double getEarnings() {
		return myCareerEarnings;
	}

	@Override
	public double getDamage() {
		return myCareerDamage;
	}
	
	@Override
	public IModifiablePlayer getOwner() {
		return myMachine.getOwner();
	}

}
