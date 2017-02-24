package engine.model.weapons;

/**
 * A class to more easily pass around the information that results from a projectile exploding.
 * The class could be replaced by a struct with little change in functionality.
 * @author Weston
 *
 */

public class DamageInfo {
	private double myDamage;
	private double myHealing;
	private int myMoney;
	private int myKills;
	
	public DamageInfo(double damageDealt, double healing, int moneyGained, int unitsKilled){
		myDamage = damageDealt;
		myHealing = healing;
		myMoney = moneyGained;
		myKills = unitsKilled;
	}
	
	public DamageInfo() {
		this(0, 0, 0, 0);
	}

	public DamageInfo(double healthDelta, int moneyGained, int unitsKilled) {
		if (healthDelta > 0) {
			myDamage = healthDelta;
			myHealing = 0;
		} else {
			myDamage = 0;
			myHealing = -healthDelta;
		}
		
		myMoney = moneyGained;
		myKills = unitsKilled;
	}

	public int getDamage(){
		return (int) myDamage;
	}
	public int getMoney(){
		return myMoney;
	}
	public int getKills(){
		return myKills;
	}
	
	public DamageInfo add(DamageInfo d) {
		return new DamageInfo(myDamage + d.myDamage, myHealing + d.myHealing, myMoney + d.myMoney, myKills + d.myKills);
	}
}
