package engine.model.strategies;

import engine.model.components.concrete.CreatorComponent;
import engine.model.entities.ConcreteEntity;
import engine.model.entities.EntityFactory;
import engine.model.systems.MovementSystem;
import engine.model.systems.PhysicalSystem;

public interface ISpawningStrategy {

	/**
	 * Used by CreatorComponent to create a new entity
	 * @param myEntityFactory
	 * @param myTarget
	 * @param myMovement
	 * @param myPhysical
	 * @param creatorComponent
	 * @return the newly created entity
	 */
	abstract public ConcreteEntity spawn(EntityFactory myEntityFactory, IPosition myTarget, MovementSystem myMovement, PhysicalSystem myPhysical,
			CreatorComponent creatorComponent);
	
}
