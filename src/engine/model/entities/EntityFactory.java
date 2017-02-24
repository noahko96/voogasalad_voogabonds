package engine.model.entities;

import java.util.Arrays;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import authoring.model.ComponentData;
import authoring.model.EntityData;
import engine.model.components.ComponentFactory;
import engine.model.components.IModifiableComponent;
import engine.model.data_stores.DataStore;
import engine.model.game_environment.MapMediator;
import engine.model.systems.ISystem;
import gamePlayerView.gamePlayerView.Router;
import utility.Point;
/**
 * Creates all the entities
 * @author owenchung 
 * @author alanguo
 *
 */
public class EntityFactory {
	private static final Point DEFAULT_LOCATION = new Point(0,0);
	private ComponentFactory myComponentFactory;
//	@SuppressWarnings("unused")
//	private List<ISystem<?>> mySystems;
	private DataStore<EntityData> myEntityDataStore;
	private Router myRouter;
	private MapMediator myMapMediator;
	private IModifiableEntityManager myEntityManager;

	public EntityFactory(List<ISystem<?>> systems, 
			DataStore<EntityData> entityDataStore, 
			Router router, 
			MapMediator mapMediator,
			IModifiableEntityManager entityManager) {
//		mySystems = systems;
		myEntityDataStore = entityDataStore;
		myRouter = router;
		myComponentFactory = new ComponentFactory(systems, myRouter); // depends on router initialization
		myMapMediator = mapMediator;
		myEntityManager = entityManager;
	}
	
	/**
	 * Using its name, fetch the Entity data out of a map (that the factory owns) of all the entity data.
	 * This way, you wan't need to know everything about an entity to make it, just the entity's name.
	 * @param entityName
	 * @return the constructed entity
	 * @throws ComponentCreationException  
	 */
	public ConcreteEntity constructEntity(String entityName, Point p) throws UnsupportedOperationException {
		EntityData entityData = myEntityDataStore.getData(entityName);
		return constructEntity(entityData, p);
	}
	
	public IEntity constructEntity(EntityData aEntityData) 
			throws UnsupportedOperationException {
		return constructEntity(aEntityData, DEFAULT_LOCATION);
	}
	
	public ConcreteEntity constructEntity(EntityData aEntityData, Point aLocation) 
			throws UnsupportedOperationException
	{
		//System.out.println("Contructing an entity.");
		//1. Construct the entity object
		//2. Construct each component using the component factory, and link this to the component object
		//2.5 Attach components to relevant systems?
		//3. return the fully constructed object
		ConcreteEntity entity = new ConcreteEntity();
		Collection<ComponentData> componentMap = aEntityData.getComponents().values();
		for (ComponentData compdata : componentMap) {
			IModifiableComponent component = myComponentFactory.constructComponent(entity, compdata, aLocation);
//			component.setEntity(entity);
			entity.addComponent(component);	
		}
		// Adding the entity to the Entity Manager
		myEntityManager.addEntity(entity.getId(), entity);

		return entity;
	}
	
	private boolean hasPhysicalComponent(EntityData entityData) {
		Map<String, ComponentData> componentMap = entityData.getComponents();
		return componentMap.containsKey("PhysicalComponent");
	}
	
	/**
	 * Tries to distribute an entity on the map by constructing it.
	 * @param entityDataName
	 * @param aLocation
	 * @return true if succeeded in creating entity; else false
	 */
	public boolean distributeEntity(String entityDataName, Point aLocation) {
		if (myEntityDataStore.hasKey(entityDataName)) {
			return distributeEntity(myEntityDataStore.getData(entityDataName), aLocation);
		} else {
			// Distribute error "No such entity name."
			return false;
		}
	}
	
	/**
	 * Tries to construct an entity with a physical component.
	 * @param entityData
	 * @param aLocation
	 * @return true if successfully constructed an entity; false if not
	 */
	private boolean distributeEntity(EntityData entityData, Point aLocation) {
		if (hasPhysicalComponent(entityData)) {
			String validTerrainsStr = entityData.getComponents().get("PhysicalComponent").getFields().get("myValidTerrains");
			// parse valid terrains into list of terrain data
			List<String> validTerrains = Arrays.asList(validTerrainsStr.split(", "));
			
			boolean canPlace = myMapMediator.isAValidTerrain(aLocation, validTerrains);
			if (canPlace) {
				try {
					constructEntity(entityData, aLocation);
					
					return true;
				} catch (UnsupportedOperationException e) {
					return false;
				}
//				deductCostFromPlayer(entityData.getBuyPrice());
			}
		}
		return false;
	}

}
