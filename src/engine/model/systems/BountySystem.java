package engine.model.systems;

import java.util.ArrayList;
import java.util.List;

import engine.model.components.IComponent;
import engine.model.components.concrete.BountyComponent;
import engine.model.entities.IEntity;
import engine.controller.PlayerController;
import engine.model.playerinfo.IModifiablePlayer;

/**
 * A system to keep track of the bounties of entities and award them to the player.
 * @author Weston
 *
 */
public class BountySystem implements ISystem<BountyComponent> {
	private List<BountyComponent> myComponents;
	private IModifiablePlayer myPlayer;
	
	public BountySystem(PlayerController player) 
	{
		myComponents = new ArrayList<BountyComponent>();
		myPlayer = player.getPlayer(0);
	}
	
	/**
	 * Called when a c's entity reaches its goal, (an enemy reaches a player's base)
	 * @param c
	 * @return the amount of lives taken
	 */
	public int pillagePlayerBase(IComponent c) {
		BountyComponent b = getComponent(c);
		if (b == null)
			return 0;
		myPlayer.updateLivesRemaining(b.getLivesTaken());
		return b.getLivesTaken();
	}
	
	/**
	 * Called when c's entity is destroyed
	 * @param c
	 * @return the amount of bounty money
	 */
	public int collectBounty(IComponent c) {
		BountyComponent b = getComponent(c);
		if (b == null)
			return 0;
		myPlayer.updateAvailableMoney(b.getBounty());
		myPlayer.updatePoints(b.getPoints());
		return b.getPoints();
		
	}

	/***********ISystem interface*******/
	@Override
	public List<BountyComponent> getComponents() {
		return myComponents;
	}
	@Override
	public BountyComponent getComponent(IComponent component) {
		return component == null ? null : getComponent(component.getEntity());
	}
	@Override
	public BountyComponent getComponent(IEntity entity) {
		for (BountyComponent component: myComponents) {
			if (component.getEntity().equals(entity)) {
				return component;
			}
		}
		return null;
	}
	@Override
	public void attachComponent(BountyComponent aComponet) {
		myComponents.add(aComponet);
	}
	@Override
	public void detachComponent(BountyComponent aComponent) {
		myComponents.remove(aComponent);
	}
}
