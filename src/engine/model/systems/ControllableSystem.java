package engine.model.systems;

import java.util.ArrayList;
import java.util.List;

import engine.model.components.IComponent;
import engine.model.components.concrete.ControllableComponent;
import engine.model.entities.IEntity;

/**
 * 
 * @author owenchung
 *
 */
public class ControllableSystem implements ISystem<ControllableComponent> {
	private List<ControllableComponent> myComponents;
	
	public ControllableSystem()
	{
		myComponents = new ArrayList<ControllableComponent>();
	}
	
	public void move(String movement) {
		for (ControllableComponent component : getComponents() ){
			component.move(movement);
		}
	}
	
	public void fire() {
		for (ControllableComponent component : getComponents() ){
			component.fire();
		}
	}

	@Override
	public List<ControllableComponent> getComponents() {
		return myComponents;
	}

	@Override
	public ControllableComponent getComponent(IComponent component) {
		return component == null ? null : getComponent(component.getEntity());
	}

	@Override
	public ControllableComponent getComponent(IEntity entity) {
		for (ControllableComponent component: myComponents) {
			if (component.getEntity().equals(entity)) {
				return component;
			}
		}
		return null;
	}

	@Override
	public void attachComponent(ControllableComponent aComponet) {
		myComponents.add(aComponet);
	}

	@Override
	public void detachComponent(ControllableComponent aComponent) {
		myComponents.remove(aComponent);
	}
	
	
}
