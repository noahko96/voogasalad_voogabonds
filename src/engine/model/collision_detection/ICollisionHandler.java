package engine.model.collision_detection;

@Deprecated
public interface ICollisionHandler {
	// moved collide with unmoved
	public void handleCollision(ICollidable moved, ICollidable unmoved);
}
