package engine.model.collision_detection;

import engine.model.components.concrete.CollidableComponent;

public interface ICollidable {

	public void checkCollision(CollidableComponent unmovedCollidable);
	public double getCollisionRadius();
	
}
