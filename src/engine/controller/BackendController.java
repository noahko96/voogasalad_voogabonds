package engine.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import java.util.List;
import java.util.ResourceBundle;

import authoring.controller.LevelDataContainer;
import authoring.controller.MapDataContainer;
import authoring.model.EnemyData;
import authoring.model.EntityData;
import authoring.model.PlayerData;
import authoring.model.serialization.JSONDeserializer;
import authoring.model.serialization.JSONSerializer;
import engine.controller.timeline.TimelineController;
import engine.controller.waves.LevelController;
import engine.model.components.IComponent;
import engine.model.components.concrete.BountyComponent;
import engine.model.data_stores.DataStore;
import engine.model.entities.ConcreteEntity;
import engine.model.entities.EntityFactory;
import engine.model.entities.EntityManager;
import engine.model.entities.IEntity;
import engine.model.game_environment.MapMediator;
import engine.model.game_environment.distributor.MapDistributor;
import engine.model.playerinfo.Player;
import engine.model.resourcestore.ResourceStore;
import engine.model.systems.*;
import gamePlayerView.gamePlayerView.Router;
import utility.ErrorBox;
import utility.file_io.FileRetriever;

import utility.Point;
import utility.file_io.FileRetriever;

/**
 * The primary gateway into the game engine
 * The purpose of this class is to manage constructing
 * all relevant data structures pertaining to the game engine
 * 
 * TODO: This class is getting large
 * We may consider further dividing it's responsibilities
 * to make it more maintainable
 * 
 * @author matthewfaw
 *
 */
public class BackendController {
	private static final String GAME_DATA_PATH = "resources/game_data_relative_paths/relative_paths";
	private static final int DEFAULT_STARTING_LEVEL=0;
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";

	//Utilities
	private transient ResourceBundle myGameDataRelativePaths;
	private transient FileRetriever myFileRetriever;
	private transient JSONDeserializer myJsonDeserializer;
	
	//Primary backend objects
	private transient ResourceStore myResourceStore;

	//Data relevant to constructing objects
	private transient DataStore<EntityData> myEntityDataStore;
	private transient PlayerData myPlayerData;
	private transient LevelDataContainer myLevelDataContainer;
	private transient MapDataContainer myMapData;
	//TODO: Move this to a system??
	private transient MapMediator myMapMediator;
	
	//Controllers to manage events
	private transient TimelineController myTimelineController;
	private transient PlayerController myPlayerController;
	private transient LevelController myLevelController;
	private transient Router myRouter;
	
	//Factories
	private transient EntityFactory myEntityFactory;
	
	//Systems
	private CollisionDetectionSystem myCollisionDetectionSystem;
	private DamageDealingSystem myDamageDealingSystem;
	private HealthSystem myHealthSystem;
	private MovementSystem myMovementSystem;
	private PhysicalSystem myPhysicalSystem;
	private BountySystem myBountySystem;
	private SpawningSystem mySpawningSystem;
	private TargetingSystem myTargetingSystem;
	private TeamSystem myTeamSystem;
	private ControllableSystem myControllableSystem;
	
	// EntityManager
	private EntityManager myEntityManager;
	
	private transient ResourceBundle myResources;
	
	public BackendController(String aGameDataPath, Router aRouter, EntityManager entityManager)
	{
		myRouter = aRouter;
		myGameDataRelativePaths = ResourceBundle.getBundle(GAME_DATA_PATH);
		myFileRetriever = new FileRetriever(aGameDataPath);
		myJsonDeserializer = new JSONDeserializer();

		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Error");
		myTimelineController = new TimelineController();
		myPlayerController = new PlayerController(myRouter);

		myEntityManager = entityManager;
		
		//Must construct static before dynamic.
		constructStaticBackendObjects();
		constructDynamicBackendObjects();
	}
	
	private void constructSystems() {
		myTeamSystem = new TeamSystem();
		myHealthSystem = new HealthSystem();
		myBountySystem = new BountySystem(myPlayerController);
		myDamageDealingSystem = new DamageDealingSystem();
		
		// ORDERING MATTERS for physical -> targeting -> collision -> movement
		myPhysicalSystem = new PhysicalSystem(myMapMediator);
		
		myTargetingSystem = new TargetingSystem();
		myCollisionDetectionSystem = new CollisionDetectionSystem();
		
		myMovementSystem = new MovementSystem(myMapMediator, myTimelineController);
		mySpawningSystem = new SpawningSystem(myTimelineController);
		
		myControllableSystem = new ControllableSystem();
		
	}
	
	
	public void moveControllables(String movement) {
		//System.out.println("moving at the back end");
		myControllableSystem.move(movement);
	}
	
	/**
	 * Given an entity ID, will route entity component information back to front end for observing.
	 * @param entityID
	 */
	public void onEntityClicked(Integer entityID) {
		IEntity clickedEntity = myEntityManager.getEntityMap().get(entityID);
		for (IComponent component: clickedEntity.getComponents()) {
			component.distributeInfo();
		}
	}
	
	//TODO
	/**
	 * Places the tower, if it can
	 * @param aTowerName
	 * @param aLocation
	 * @return true if it is successfully placed, false otherwise
	 */
	public void attemptToPlaceEntity(String aEntityName, Point aLocation)
	{
		boolean success = myEntityFactory.distributeEntity(aEntityName, aLocation);
		if (success) {
			EntityData entityData = myEntityDataStore.getData(aEntityName);
			if (entityData != null) {
				int cost = entityData.getBuyPrice();
				// TODO: change if implementing multiplayer
				deductCostFromPlayer(cost, 0); // hard coded as 0th player
			}
			
		}
	}
	
	/**
	 * Deducts the cost of an entity from a player.
	 * @param buyPrice
	 */
	private void deductCostFromPlayer(int buyPrice, int playerID) {
		Player myPlayer = myPlayerController.getPlayer(playerID);
		myPlayer.updateAvailableMoney(-1*buyPrice);
	}
	
	//TODO: Update when WaveData is ready from Authoring
	private void constructDynamicBackendObjects()
	{
		//List<DummyWaveOperationData> data = getData(myGameDataRelativePaths.getString("WavePath"), DummyWaveOperationData.class);
		//XXX: This depends on the map distributor already being constructed
		// we should refactor this to remove the depenency in calling
//		myWaveController = new WaveController(myLevelDataContainer, myEntityDataStore, myPlayerController.getActivePlayer());
		myLevelController = new LevelController(myLevelDataContainer, DEFAULT_STARTING_LEVEL, myEntityDataStore, myEntityFactory, myPhysicalSystem, myMovementSystem, myMapData);
		myTimelineController.attach(myLevelController);
	}
	
	/**
	 * The primary dispatcher method for constructing objects from the GameDataFiles
	 */
	private void constructStaticBackendObjects()
	{
		constructEntityDataStore();
		constructPlayerData();
		
		
		myPlayerController.addPlayer(myPlayerData);
		myPlayerController.addResourceStoreForAllPlayers(myResourceStore);
		
		constructLevelData();
		constructMap();
		constructSystems();
		
		constructEntityFactory(); //depends on constructing systems first
	}

	private void constructEntityFactory() {
		List<ISystem<?>> mySystems = new ArrayList<ISystem<?>>();
		mySystems.add(myCollisionDetectionSystem);
		mySystems.add(myDamageDealingSystem);
		mySystems.add(myHealthSystem);
		mySystems.add(myMovementSystem);
		mySystems.add(myPhysicalSystem);
		mySystems.add(myBountySystem);
		mySystems.add(mySpawningSystem);
		mySystems.add(myTargetingSystem);
		mySystems.add(myTeamSystem);
		mySystems.add(myControllableSystem);
		myEntityFactory = new EntityFactory(mySystems, myEntityDataStore, myRouter, myMapMediator, myEntityManager);
		mySpawningSystem.setEntityFactory(myEntityFactory);
	}
	
	public void sellEnemy(ConcreteEntity entity) {
		
		for (IComponent c: entity.getComponents()) {
			if (c instanceof BountyComponent) {
				myBountySystem.collectBounty(c);
				entity.delete();
				break;
			}
		}
		
	}

	/**
	 * Helper method to create the backend map object
	 * from the GameData file
	 * 
	 * Assumes that the only map data to use is the first one
	 */
	private void constructMap()
	{
		try {
			MapDataContainer mapData = getMapData();
			
			//XXX: is the map mediator needed anywhere? Could we just keep the map distributor? this would be ideal
			myMapMediator = new MapMediator(mapData, myRouter.getPixelWidth());

			//distribute to frontend
			myRouter.distributeMapData(mapData);
		} catch (FileNotFoundException e) {
			//TODO: Make error message come from resource file
			myRouter.distributeErrors("The file for MapDataContainer cannot be found!");
		}
		
	}

	private MapDataContainer getMapData() throws FileNotFoundException {
		List<MapDataContainer> data = getData(myGameDataRelativePaths.getString("MapPath"), MapDataContainer.class);
		MapDataContainer mapData = data.get(0);
		myMapData = mapData;
		return mapData;
	}
	
	/**
	 * Constructs level data object, assuming there's exactly one of them
	 */
	private void constructLevelData() {
		try {
			List<LevelDataContainer> data = getData(myGameDataRelativePaths.getString("LevelPath"), LevelDataContainer.class);
			myLevelDataContainer = data.get(0);
		} catch (FileNotFoundException e) {
			myRouter.distributeErrors("The file for LevelData cannot be found!");
		}
	}
	
	/**
	 * This method handles the construction of the object which manages all of the enemy
	 * data
	 */
	private void constructEntityDataStore()
	{
		try {
			List<EntityData> data = getData(myGameDataRelativePaths.getString("EntityPath"), EntityData.class);
			myEntityDataStore = new DataStore<EntityData>(data);
			myResourceStore = new ResourceStore(data);
		} catch (FileNotFoundException e) {
			myRouter.distributeErrors("The file for EntityData cannot be found!");
		}
		
	}
	
	/**
	 * This method constructs the player data
	 * Assumes there is exactly one player data specified
	 * 
	 * TODO: Possibly change this to make it more flexible?
	 * @throws FileNotFoundException 
	 */
	private void constructPlayerData() 
	{
		try {
			List<PlayerData> data = getData(myGameDataRelativePaths.getString("PlayerPath"), PlayerData.class);
			myPlayerData = data.get(0);
		} catch (FileNotFoundException e) {
			myRouter.distributeErrors("The file for PlayerData cannot be found!");
		}
	}
	
	/**
	 * A templated method to get the list of GameData objects from the specified file path
	 * This method assumes that all GameData in the file path is of the type aClass
	 * TODO: Add error throwing here
	 * @param aFilePath
	 * @param aClass
	 * @return
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	private <T> List<T> getData(String aFilePath, Class<T> aClass) throws FileNotFoundException
	{
		List<String> files = myFileRetriever.getFileNames(aFilePath);
		List<T> data = new ArrayList<T>();
		for (String file: files) {
			T entry;
			entry = (T) myJsonDeserializer.deserializeFromFile(file, aClass);
			data.add(entry);
		}
		return data;
	}

	public void startTimeLine() {
		myTimelineController.start();
	}
	public void pauseTimeline()
	{
		myTimelineController.pause();
	}
	
	public void save() 
	{
		JSONSerializer js = new JSONSerializer();
		try {
			js.serializeToFile(this, "plswork");
		} catch (Exception e1) {
			ErrorBox.displayError(myResources.getString("CannotSave"));
			myRouter.distributeErrors(e1.toString());
		}
		try {
			BackendController b = (BackendController)myJsonDeserializer.deserializeFromFile("SerializedFiles/plswork", BackendController.class);
		} catch (FileNotFoundException e) {
			ErrorBox.displayError(myResources.getString("CannotLoad"));
			myRouter.distributeErrors(e.toString());
		}
	}
	
	/*
	public static void main(String[] args)
	{
		BackendController controller = new BackendController("SerializedFiles/exampleGame",null);
//		controller.getClass();
		controller.save();
	}
	*/
	
}