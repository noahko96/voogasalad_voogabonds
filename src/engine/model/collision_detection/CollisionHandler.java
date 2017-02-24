package engine.model.collision_detection;

@Deprecated
public class CollisionHandler implements ICollisionHandler{

	/**
	 * Handle collisions differently based on types that collided.
	 * Collide IFF both radii are NOT 0.
	 */
	@Override
	public void handleCollision(ICollidable moved, ICollidable unmoved) {
		/*
		if (! (moved.getCollisionRadius() == 0 || unmoved.getCollisionRadius() == 0) ) {
			unmoved.collideInto(moved);
		}
		*/
	}

}
