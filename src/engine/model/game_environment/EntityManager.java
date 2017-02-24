package engine.model.game_environment;

import java.util.ArrayList;
import java.util.List;

import engine.model.entities.IEntity;

class EntityManager {
	private ArrayList<IEntity> myEntities;

	EntityManager()
	{
		myEntities = new ArrayList<IEntity>();
	}
	
	/**
	 * Adds the entity to the list of entities to track
	 * @param aEntityToTrack
	 */
	void trackEntity(IEntity aEntityToTrack)
	{
		myEntities.add(aEntityToTrack);
	}
	
	public List<IEntity> getEntities() {
		return myEntities;
	}
}
