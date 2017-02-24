package gamePlayerView.gamePlayerView;

import java.util.List;


import authoring.controller.MapDataContainer;
import authoring.model.TowerData;
import authoring.model.map.MapData;
import engine.IObservable;
import engine.model.components.viewable_interfaces.IViewable;
import engine.model.components.viewable_interfaces.IViewableBounty;
import engine.model.components.viewable_interfaces.IViewableCollidable;
import engine.model.components.viewable_interfaces.IViewableCreator;
import engine.model.components.viewable_interfaces.IViewableDamageDealer;
import engine.model.components.viewable_interfaces.IViewableHealth;
import engine.model.components.viewable_interfaces.IViewableMovable;
import engine.model.components.viewable_interfaces.IViewablePhysical;
import engine.model.components.viewable_interfaces.IViewableTargeting;
import engine.model.components.viewable_interfaces.IViewableTeam;
import engine.model.components.viewable_interfaces.IViewablePhysical;
import engine.model.playerinfo.IViewablePlayer;
import engine.model.resourcestore.IViewableStore;
import gamePlayerView.GUIPieces.InfoBoxes.ErrorPopup;
import gamePlayerView.GUIPieces.InfoBoxes.LostPopUp;
import gamePlayerView.interfaces.IPlayerAcceptor;
import gamePlayerView.interfaces.IResourceAcceptor;
import javafx.scene.text.Text;


/**
 * @author Guhan Muruganandam
 */
public class Router {
	private transient GamePlayerScene  myGamePlayerScene;
	//TODO: Change these box objects to instead be acceptor interfaces
	private transient List<IPlayerAcceptor> myCash;
	private transient List<IPlayerAcceptor> myLives; 
	private transient List<IPlayerAcceptor> myWaves;
	private transient List<IResourceAcceptor> myResources;
	//List<ISprites> mySprites;


	public Router(GamePlayerScene aGamePlayerScene)
	{
		myGamePlayerScene  = aGamePlayerScene;
		myCash = myGamePlayerScene.getCash();
		myLives = myGamePlayerScene.getLives();
		myWaves = myGamePlayerScene.getWaves();
		myResources = myGamePlayerScene.getResources();
		//mySprites = myGamePlayerScene.getSprites();
	}

	/**
	 * Create a new moveable component data using an iviewablephysical.
	 * Should happen whenever a new PhysicalComponent is constructed.
	 */
	public void createNewViewableComponent(IViewablePhysical physicalComponent) 
	{
		myGamePlayerScene.getMapDisplay().giveViewableComponent(physicalComponent);
	}
	public void distributeGameLost() {
		LostPopUp lostPopup= new LostPopUp();
	}

	public void distributeErrors(String aErrorMessage)
	{
		ErrorPopup error = new ErrorPopup(aErrorMessage);
	}

	public void distributePlayer(IObservable<IViewablePlayer> aPlayer)
	{
		//This is where you'll get player specific info such as money and lives and Tower Data
		distributeCash(aPlayer);
		distributeLives(aPlayer);
		distributeResourceStore(aPlayer);

	}

	//TODO:
	//	public void distributeGameState() //Will have wave stuff

	private void distributeResourceStore(IObservable<IViewablePlayer> aPlayer) {
		for(IResourceAcceptor r : myResources){
			r.acceptResources(aPlayer);
		}
	}

	private void distributeLives(IObservable<IViewablePlayer> aPlayer) {
		for(IPlayerAcceptor l : myLives){
			l.acceptPlayer(aPlayer);
		}
	}

	private void distributeCash(IObservable<IViewablePlayer> aPlayer) {
		for(IPlayerAcceptor c : myCash){
			c.acceptPlayer(aPlayer);
		}
	}
	
	public int getPixelWidth(){
	    return (int) myGamePlayerScene.getMapDisplay().getPane().getHeight();
	}
	
	//TODO: What is the input argument for this?
	//public void distributeSprite(//Something){
	//	for(ISprite s : mySprites){
	//s.acceptSprite(//Something);
	//}		
	//}

	public void distributeMapData(MapDataContainer aMapData)
	{
		//TODO: distribute to all interested frontend objects
		myGamePlayerScene.giveMapData(aMapData);
	}

	/**
	 * A method to distribute viewable game elements
	 * @param aComponent
	 */

	//public void distributeViewableComponent(IObservable<IViewable> aComponent)
	//{
	//TODO: give all viewable components the new component
	//myGamePlayerScene.getMapDisplay().giveViewableComponent(aComponent);
	//}

	public void distributeViewableComponent(IViewableTargeting aComponent)
	{
		//TODO: give all viewable components the new component
		myGamePlayerScene.getBuilder().withTargetingMechanism(aComponent);
		//myGamePlayerScene.getMapDisplay().giveViewableComponent(aComponent);
	}

	public void distributeViewableComponent(IViewableDamageDealer aComponent)
	{
		myGamePlayerScene.getBuilder().withDamageBox(aComponent);
	}

	public void distributeViewableComponent(IViewableCollidable aComponent)
	{
		//TODO: give all viewable components the new component
	}

	public void distributeViewableComponent(IViewableCreator aComponent)
	{
		//TODO: give all viewable components the new component
	}
	public void distributeViewableComponent(IViewablePhysical aComponent)
	{

		myGamePlayerScene.getBuilder().withMachineInfo(aComponent);
	}
	public void distributeViewableComponent(IViewableMovable aComponent)
	{
		//TODO: give all viewable components the new component
	}
	public void distributeViewableComponent(IViewableTeam aComponent)
	{
		//TODO: give all viewable components the new component
	}
	public void distributeViewableComponent(IViewableBounty aComponent)
	{
		Text text = new Text();
		text.setText("money given on death" + aComponent.getBounty() );
		myGamePlayerScene.getMyBottomPane().add(text);
		myGamePlayerScene.getBuilder().withBountyBox(aComponent);
	}
	public void distributeViewableComponent(IViewableHealth aComponent)
	{
		Text text = new Text();
		text.setText("Current Health" + aComponent.getCurrHealth());
		myGamePlayerScene.getMyBottomPane().add(text);
		myGamePlayerScene.getBuilder().withHealthBox(aComponent);
	}
	/*
	public void distributeViewableComponent(IViewableUpgrade aComponent)
	{
		myGamePlayerScene.getBuilder().withUpgrade(aComponent);
	}
	public void distributeViewableComponent(IViewableSell aComponent)
	{
		myGamePlayerScene.getBuilder().withSell(aComponent);
	}
	 */
	/**
	 * An example to follow for setting up the router
	 * @param aResourceStore
	 */
	/*
	public void distributeResourceStore(IViewableResourceStore aResourceStore)
	{
		myGamePlayerScene.getResourceStoreAcceptors().forEach(acceptor -> acceptor.acceptResourceStore(aResourceStore));
	}
	 */

	/*
	 * public void distributeTowerData(IViewableTowerData)// Stuff like EnemiesKilledInfo. Might not be necessary. As it is called by lcik events
	 * {
	 * 
	 * }
	 *  
	 *  
	 */


	/*
	 * void distributeEnemy(IViewableEnemy aEnemy)
	 * {
	 * 		for (IEnemySource enemySource: myFrontendEnemySources) {
	 * 			enemySource.giveEnemy(aEnemy)
	 * 		]
	 * }
	 * 
	 * 
	 * 
	 * over  in some IEnemySource object:
	 * 
	 * @Override
	 * void giveEnemy(IViewableEnemy aEnemy)
	 * {
	 * 		aEnemy.attach(this);
	 * }
	 * 
	 * @Override
	 * void update()
	 * {
	 * 		//redraw the enemy
	 * }
	 */

}
