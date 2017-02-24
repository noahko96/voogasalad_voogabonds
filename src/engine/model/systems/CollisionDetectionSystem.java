package engine.model.systems;

import java.util.ArrayList;
import java.util.List;

import engine.model.components.IComponent;
import engine.model.components.concrete.CollidableComponent;
import engine.model.components.concrete.PhysicalComponent;
import engine.model.entities.IEntity;

/**
 * A system to manage collision detection in the game
 * Entities with the proper components can register with the
 * Collision detection system so that collisions may be reported
 * @author Weston
 * @author Alan
 *
 */
public class CollisionDetectionSystem implements ISystem<CollidableComponent> {
	private List<CollidableComponent> myComponents;

	public CollisionDetectionSystem() {
		myComponents = new ArrayList<CollidableComponent>();
	}
	/**
	 * Checks if a physical component collides with anything in
	 * it's collision radius.
	 * @param the physical component that changed position
	 */
	public void checkCollision(PhysicalComponent movedPhysical) {
		CollidableComponent movedCollidable = getComponent(movedPhysical);
		if (movedCollidable != null) {
			List<CollidableComponent> iterate = new ArrayList<CollidableComponent>(getComponents());
			for (CollidableComponent unmovedCollidable: iterate)	
				movedCollidable.checkCollision(unmovedCollidable);
		}
	}
	
	
	/**************ISystem interface*******************/
	@Override
	public List<CollidableComponent> getComponents() {
		return myComponents;
	}
	@Override
	public CollidableComponent getComponent(IComponent component) {
		return component == null ? null : getComponent(component.getEntity());
	}
	@Override
	public CollidableComponent getComponent(IEntity entity) {
		for (CollidableComponent component: myComponents) {
			if (component.getEntity().equals(entity)) {
				return component;
			}
		}
		return null;
	}
	@Override
	public void attachComponent(CollidableComponent aComponet) {
		myComponents.add(aComponet);
	}
	@Override
	public void detachComponent(CollidableComponent aComponent) {
		myComponents.remove(aComponent);
	}

}
