package engine.model.projectiles;
import engine.IObserver;
import engine.controller.timeline.TimelineController;
import engine.model.machine.Machine;
import engine.model.strategies.IMovable;
import utility.Point;

/**
 * This interface provides the model with access to the advance method to that the projectile can move when the game ticks.
 * @author Weston
 */
@Deprecated
public interface IProjectile extends IObserver<TimelineController>, IMovable {
	
	/**
	 * Advances the projectile, possibly exploding it if it runs into an enemy unit.
	 * @return the new location of the projectile
	 */
	public Point advance(); // returns the point that the projectile advanced to
	
	/**
	 * @return the machine this projectile is targeting
	 */
	public Machine getTargetMachine();

}
