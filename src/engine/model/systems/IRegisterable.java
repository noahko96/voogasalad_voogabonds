package engine.model.systems;

/**
 * Interface for objects that need to be registered so that
 * they and their references can be removed from the game later.
 * 
 * @author alanguo
 *
 */
public interface IRegisterable {
	public void unregisterMyself();
}
