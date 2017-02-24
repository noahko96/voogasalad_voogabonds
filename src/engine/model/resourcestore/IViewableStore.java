package engine.model.resourcestore;

import java.util.List;

import authoring.model.EntityData;

/**
 * Interface for viewable store
 */
public interface IViewableStore {
	public List<EntityData> getAvailableEntities();
}
