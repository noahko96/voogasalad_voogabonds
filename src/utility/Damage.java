package utility;

/**
 * A class to contain Damage, in case we want to add damages types and vulnerabilities/resistances to
 * different types at a later date.
 * @author Weston
 *
 */

public class Damage {
	double myDamage;
	
	public Damage(double d) {
		myDamage = d;
	}
	
	public double getDamage() {
		return myDamage;
	}
}
