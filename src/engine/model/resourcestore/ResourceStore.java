package engine.model.resourcestore;

import java.util.List;

import authoring.model.EntityData;

/**
 * This class is the store that keeps all available resources
 * @author owenchung
 * @author matthewfaw
 */
public class ResourceStore implements /*IModifiableStore, */ IViewableStore {

	private EntityUpgradeStore myUpgradeStore;
	
	public ResourceStore(List<EntityData> aEntityInfoList) {
		myUpgradeStore = new EntityUpgradeStore(aEntityInfoList);
	}
	
	@Override
	public List<EntityData> getAvailableEntities() {
		return myUpgradeStore.getBaseEntitesData();
	}
	
	public int getTowerPrice(String aEntityName)
	{
		return myUpgradeStore.getPrice(aEntityName);
	}
}
