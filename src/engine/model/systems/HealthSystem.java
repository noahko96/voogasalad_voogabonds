package engine.model.systems;

import java.util.ArrayList;
import java.util.List;

import engine.model.components.IComponent;
import engine.model.components.concrete.HealthComponent;
import engine.model.entities.IEntity;
import engine.model.weapons.DamageInfo;
import utility.Damage;

/**
 * A system to keep track of health and units taking damage
 * @author Weston
 * @author owenchung(edited)
 *
 */
public class HealthSystem implements ISystem<HealthComponent> {
	private List<HealthComponent> myComponents;

	public HealthSystem() {
		myComponents = new ArrayList<HealthComponent>();
	}
	
	/**
	 * Deals the damageToTake to the HealthComponent corresponding to target
	 * @param target
	 * @param damageToTake
	 * @return
	 */
	public DamageInfo dealDamageTo(IComponent target, Damage damageToTake) {
		HealthComponent health = getComponent(target);
		if (health != null) {
			return health.takeDamage(damageToTake);
		}
		return new DamageInfo();
	}
	
	/***********ISystem interface*******/
	@Override
	public List<HealthComponent> getComponents() {
		return myComponents;
	}
	@Override
	public HealthComponent getComponent(IComponent component) {
		return component == null ? null : getComponent(component.getEntity());
	}
	@Override
	public HealthComponent getComponent(IEntity entity) {
		for (HealthComponent component: myComponents) {
			if (component.getEntity().equals(entity)) {
				return component;
			}
		}
		return null;
	}
	@Override
	public void attachComponent(HealthComponent aComponet) {
		myComponents.add(aComponet);
	}
	@Override
	public void detachComponent(HealthComponent aComponent) {
		myComponents.remove(aComponent);
	}
}
