package engine.controller;

//import java.util.ResourceBundle;

import engine.controller.timeline.TimelineController;
import engine.model.components.IComponent;
import engine.model.entities.EntityManager;
import engine.model.entities.IEntity;
import gamePlayerView.gamePlayerView.GamePlayerScene;
import gamePlayerView.gamePlayerView.Router;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utility.Point;

/**
 * A main controller whose primary purpose is to
 * create the frontend and backend objects and to manage 
 * communication from frontend to backend
 * 
 * Communication from backend to frontend will be established
 * by the Router object, and will be maintained using the 
 * Observer/Observable pattern of communication
 * 
 * @author matthewfaw
 * @author owenchung (edits)
 *
 */
public class ApplicationController {
//	private static final String GAME_OPTIONS_PATH = "resources/game_options/GamePaths";
	private static final String GAME_FOLDER = "SerializedFiles/";
	private Stage myStage;
//	private ResourceBundle myGameOptions;
	private BackendController myBackendController;
	//XXX: maybe make a frontend controller, and move this there
	private TimelineController myAnimationTimelineController;
	private GamePlayerScene myScene;
	private EntityManager myEntityManager; 
	
	//private Stage myStage; //////Guhan
	//private Pane myPane=new BorderPane();

	public ApplicationController()
	{
//		myGameOptions = ResourceBundle.getBundle(GAME_OPTIONS_PATH);
	}
	
	/**
	 * A method to be called to initialize the frontend and backend
	 * @throws Exception 
	 */
	public void init(Stage aStage,String gameTitle) throws Exception
	{
		//myStage=aStage; ///Guhan 
		//GamePlayerScene scene = constructGUI(aStage);
		myStage = aStage;
		myScene = constructGUI(myStage);
		Router router = new Router(myScene);
		myEntityManager = new EntityManager();
		constructBackend(router,gameTitle);
	}
	/**
	 * Helper method to create the view object
	 * from the GameData file
	 * @throws Exception 
	 * TODO: Move to a frontend controller?
	 */
	private GamePlayerScene constructGUI(Stage aStage) throws Exception
	{
		GamePlayerScene scene = new GamePlayerScene(aStage, this);
		myAnimationTimelineController = new TimelineController();
		myAnimationTimelineController.attach(scene.getMapDisplay());
		return scene;
	}
	/**
	 * A method to handle the construction of the backend controller
	 * This method establishes the link between the frontend and backend
	 * through the Router class
	 * @param aRouter
	 * @param gameTitle 
	 */
	private void constructBackend(Router aRouter, String gameTitle)
	{
		//TODO: Change this to make this dynamic--select different games
		myBackendController = new BackendController(GAME_FOLDER + gameTitle, aRouter, myEntityManager);
	}
	
	private void gameLost() {
		
	}
	/*
	private BorderPane constructBorderPane(){
		myPane= new BorderPane();
		LeftPane myLeftPane=new LeftPane(this);
		RightPane myRightPane=new RightPane();
	    MapDisplay myMap = new MapDisplay(this);
		BottomPane myBottomPane = new BottomPane(this);
		//myCash.add(myLeftPane.getCash());
		//myLives.add(myLeftPane.getLives());
		//myResources.add(myRightPane.getTowerColumn());
		//mySprites.add(myMap.getSprites());
		borderpane.setRight(myRightPane.getView());
		borderpane.setBottom(myBottomPane.getView());
		borderpane.setCenter(myMap.getView());
		borderpane.setLeft(myLeftPane.getView());
		myMap.setupDragging(myScene);
		return null;
	}
	*/

	public void onPlayButtonPressed() {
		myBackendController.startTimeLine();
	}

	public void onPauseButtonPressed() {
		myBackendController.pauseTimeline();
	}

	public void onFastButtonPressed() {
		//AnimationController.fastForward()
	}

	public void onSlowButtonPressed() {
		//AnimationController.slow()
	}
	
	public void onFireButtonPressed() {
		
	}
	
	public void onRightButtonPressed() {
		myBackendController.moveControllables("Right");
	}
	
	public void onLeftButtonPressed() {
		myBackendController.moveControllables("Left");
	}
	
	public void onUpButtonPressed() {
		myBackendController.moveControllables("Up");
	}
	
	public void onDownButtonPressed() {
		myBackendController.moveControllables("Down");
	}
	
	

	public void onUpgradeButtonPressed() {
		//
	}

	public void onSellButtonPressed() {
		//
	}
	
	public void onTowerDropped(String aTowerName, Point aDropLocation)
	{
		myBackendController.attemptToPlaceEntity(aTowerName, aDropLocation);
	}
/*
	public void onSellButtonPressed(TowerData tower) {
		// TODO Auto-generated method stub
//		return null;
	}
	*/
	
	public void onSavePressed() {
		myBackendController.save();
	}
	
	/**
	 * Given an entity ID, will route entity component information back to front end for observing.
	 * @param entityID
	 */
	public void onEntityClicked(String entityID) {
		myBackendController.sellEnemy(myEntityManager.getEntityMap().get(entityID));
//		IEntity clickedEntity = myEntityManager.getEntityMap().get(entityID);
//		for (IComponent component: clickedEntity.getComponents()) {
//			component.distributeInfo();
//		}
//		myScene.getMyBottomPane().clear();
//		myScene.buildEntityInfoBox();
	}
	
	public void DisplayStats() throws Exception {
		myScene.updateTowerStatisticsRow();
	}

	public void onFirstPressed() {
		// TODO Auto-generated method stub
	}

	public void onLastPressed() {
		// TODO Auto-generated method stub
	}

	public void onRefreshPressed() {
		// TODO Auto-generated method stub
	}
	
	/*
	public static void main(String[] args)
	{
		ApplicationController appcont = new ApplicationController();
		appcont.init();
		appcont.constructMap();
	}
	*/
	
}