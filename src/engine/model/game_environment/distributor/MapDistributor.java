package engine.model.game_environment.distributor;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import authoring.model.EntityData;
import engine.controller.PlayerController;
import engine.model.components.concrete.PhysicalComponent;
import engine.model.entities.EntityFactory;
import engine.model.game_environment.MapMediator;
import engine.model.playerinfo.Player;
import engine.model.resourcestore.ResourceStore;
import utility.ErrorBox;
import utility.Point;
@Deprecated
public class MapDistributor {
	
	private EntityFactory myEntityFactory;
	private ResourceStore myResourceStore;
	private MapMediator myMapMediator;
	private List<PhysicalComponent> myPhysicalComponents;
	private PlayerController myPlayerController;
	private ResourceBundle myResources;
	private String DEFAULT_RESOURCE_PACKAGE = "resources/";
	
	public MapDistributor(MapMediator mapMediator, ResourceStore resourceStore, 
			EntityFactory entityFactory, PlayerController playerController)
	{
		this.myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Error");
		myEntityFactory = entityFactory;
		myMapMediator = mapMediator;
		myResourceStore = resourceStore;
		myPlayerController = playerController;
	}
	
	public void distribute(EntityData entityData, Point aLocation) {
		String validTerrainsStr = entityData.getComponents().get("PhysicalComponent").getFields().get("myValidTerrains");
		
		// parse valid terrains into list of terrain data
		List<String> validTerrains = Arrays.asList(validTerrainsStr.split(", "));
		
		boolean canPlace = myMapMediator.isAValidTerrain(aLocation, validTerrains);
		if (canPlace) {
//			createEntity(entityData);
			deductCostFromPlayer(entityData.getBuyPrice());
		}
	}
	
	public void distribute(String entityDataName, Point aLocation) {
		
	}
	/*
	private void createEntity(EntityData entityData) {
		try {
			myEntityFactory.constructEntity(entityData.getName());
		} catch (UnsupportedOperationException e) {
			ErrorBox.displayError(myResources.getString("CannotConstructEntity"));
			// Router.distributeError("Can't construct entity.");
//			 myRouter.distributeError("Can't construct entity.");
		}
	}
	*/

	/**
	 * Changes active player's money by negative buy price.
	 * @param buyPrice
	 */
	private void deductCostFromPlayer(int buyPrice) {
		Player myPlayer = myPlayerController.getActivePlayer();
		myPlayer.updateAvailableMoney(-1*buyPrice);
	}
	
//	public void distribute(String aEntityName, String aPlayerID, Point aLocation)
//	{
//		
//		// find physical component from entity name
//		PhysicalComponent aPhysicalComponent = ;
//		
//		// ask map mediator if you can place
//		myMapMediator.attemptToPlaceEntity(aLocation, aPhysicalComponent);
//		// if yes
//			// deduct cost from player
//			// create new entity
//		
//	}

}
