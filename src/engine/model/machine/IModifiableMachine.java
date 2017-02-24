package engine.model.machine;

import engine.model.weapons.DamageInfo;
import utility.Damage;

@Deprecated
public interface IModifiableMachine {
	abstract public double getHealth();
	abstract public DamageInfo takeDamage(Damage dmg);
}
