package engine.model.strategies.spawning;

import engine.model.components.concrete.CreatorComponent;
import engine.model.entities.ConcreteEntity;
import engine.model.entities.EntityFactory;
import engine.model.strategies.IPosition;
import engine.model.strategies.ISpawningStrategy;
import engine.model.strategies.factories.AbstractStrategyFactory;
import engine.model.systems.MovementSystem;
import engine.model.systems.PhysicalSystem;
 import utility.ResouceAccess;

public class BasicSpawnStrategy implements ISpawningStrategy {
	
	public BasicSpawnStrategy(AbstractStrategyFactory<ISpawningStrategy> factory) {
		//Do nothing
	}

	@Override
	public ConcreteEntity spawn(EntityFactory myEntityFactory, IPosition myTarget, MovementSystem myMovement,
			PhysicalSystem myPhysical, CreatorComponent creatorComponent) {
		try {
			if (myPhysical.getComponent(creatorComponent) != null) {
				ConcreteEntity spawn = myEntityFactory.constructEntity(creatorComponent.getSpawnName(), myPhysical.getComponent(creatorComponent).getPosition());
				if (myMovement.get(spawn) != null)
					myMovement.get(spawn).setGoal(myTarget);
				
				return spawn;
			} else 
				return null;
		} catch (IllegalArgumentException e) {
			throw new UnsupportedOperationException(ResouceAccess.getError("NoSpawn"), e);
		}
	}


}
