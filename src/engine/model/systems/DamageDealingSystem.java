package engine.model.systems;

import java.util.ArrayList;
import java.util.List;

import engine.model.components.IComponent;
import engine.model.components.concrete.DamageDealingComponent;
import engine.model.entities.IEntity;
import engine.model.strategies.IDamageStrategy;
import engine.model.strategies.factories.DamageStrategyFactory;

public class DamageDealingSystem implements ISystem<DamageDealingComponent> {
	private List<DamageDealingComponent> myComponents;
	private DamageStrategyFactory myStrategyFactory;

	public DamageDealingSystem() {
		myComponents = new ArrayList<DamageDealingComponent>();
		myStrategyFactory = new DamageStrategyFactory();
	}

	/**
	 * Makes a deal damage to b and explode, if applicable
	 * @param a
	 * @param b
	 */
	public void dealDamageToTarget(IComponent a, IComponent b) {
		if (getComponent(a) != null && !a.equals(b))
			getComponent(a).explode(b);
	}

	/**
	 * Makes c's entity explode, possibly dealing damage to nearby entities
	 * @param c
	 */
	public void explode(IComponent c) {
		DamageDealingComponent dmg = getComponent(c);
		if(dmg != null)
			dmg.explode();
		else {
			c.getEntity().delete();
		}
	}
	
	/**
	 * Creates a new damage strategy named name
	 * Used for DamageDealingComponent construction
	 * @param name
	 * @return the new strategy
	 */
	public IDamageStrategy newStrategy(String name) {
		return myStrategyFactory.newStrategy(name);
	}
	
	/***********ISystem interface*******/
	@Override
	public List<DamageDealingComponent> getComponents() {
		return myComponents;
	}
	@Override
	public DamageDealingComponent getComponent(IComponent component) {
		return component == null ? null : getComponent(component.getEntity());
	}
	@Override
	public DamageDealingComponent getComponent(IEntity entity) {
		for (DamageDealingComponent component: myComponents) {
			if (component.getEntity().equals(entity)) {
				return component;
			}
		}
		return null;
	}
	@Override
	public void attachComponent(DamageDealingComponent aComponet) {
		myComponents.add(aComponet);
	}
	@Override
	public void detachComponent(DamageDealingComponent aComponent) {
		myComponents.remove(aComponent);
	}
}
