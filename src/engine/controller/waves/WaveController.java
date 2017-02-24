package engine.controller.waves;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import authoring.controller.MapDataContainer;
import authoring.model.EntityData;
import authoring.model.LevelData;
import engine.model.components.concrete.MoveableComponent;
import engine.model.data_stores.DataStore;
import engine.model.entities.EntityFactory;
import engine.model.entities.IEntity;
import engine.model.systems.MovementSystem;
import engine.model.systems.PhysicalSystem;
import utility.Point;
import utility.ResouceAccess;

/**
 * A class to handle which wave the game is currently in,
 * and the game time of the simulation
 * 
 * This class depends on the ActiveWaveManager to determine which enemies should be constructed
 * at a given clock tick
 * It also depends on the MapDistributor 
 * 
 * @author matthewfaw and owenchung
 *
 */
public class WaveController {
	private ActiveWaveManager myActiveWaveManager;
	private EntityFactory myEntityFactory;
	private MapDataContainer myMapDataContainer;
	private PhysicalSystem myPhysicalSystem;
	private MovementSystem myMovementSystem;
	
	
	public WaveController (
			DataStore<EntityData> enemyDataStore,
			LevelData levelData,
			double startTime,
			EntityFactory entityFactory,
			PhysicalSystem physicalSystem,
			MovementSystem movementSystem,
			MapDataContainer mapDataContainer
			) {
		
		myMapDataContainer = mapDataContainer;
		myPhysicalSystem = physicalSystem;
		myMovementSystem = movementSystem;
		myActiveWaveManager = new ActiveWaveManager(enemyDataStore, levelData, startTime);
		myEntityFactory = entityFactory;
		
	}


	public void distributeEntities(double aElapsedTime)
	{
		List<PathFollowerData> entitiesToConstruct = myActiveWaveManager.getEntitiesToConstruct(aElapsedTime);
		
		for (Iterator<PathFollowerData> iterator = entitiesToConstruct.iterator(); iterator.hasNext();) {
			PathFollowerData entityData = iterator.next();
			try {	
				
				List<Point> spawns = myMapDataContainer.getSpawnPoints(entityData.getSpawnPoint());
				List<Point> sinks = myMapDataContainer.getSinkPoints(entityData.getSinkPoint());
				
				Collections.shuffle(spawns);
				Collections.shuffle(sinks);
//				//System.out.println("trying to spawn");
				IEntity newEntity = myEntityFactory.constructEntity(entityData.getMyEntityData(), spawns.get(0));
				////System.out.println("after");
				MoveableComponent m = myMovementSystem.get(newEntity);
				if (m != null) {
					m.setGoal(sinks.get(0));
				}
			} catch (UnsupportedOperationException e) {
				throw new UnsupportedOperationException(ResouceAccess.getError("NoEntity") + e.getMessage(), e);
			}
			
		}
	}

	public boolean isLevelFinished() {
		return !myActiveWaveManager.hasEnemiesToRelease();
	}


	public void newWave(DataStore<EntityData> store, LevelData levelData, double totalTimeElapsed) {
		myActiveWaveManager = new ActiveWaveManager(store, levelData, totalTimeElapsed);
		
	}

}