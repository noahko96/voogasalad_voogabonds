package engine.model.weapons;

import utility.Point;

@Deprecated
public class NullWeapon implements IWeapon {

	@Override
	public void fire(double initialDirectionRadians, Point initialLocation) {
		//Do nothing.
	}

	@Override
	public double getKills() {
		return 0;
	}

	@Override
	public double getEarnings() {
		return 0;
	}

	@Override
	public double getDamage() {
		return 0;
	}

}
