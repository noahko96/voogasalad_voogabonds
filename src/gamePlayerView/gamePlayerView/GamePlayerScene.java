package gamePlayerView.gamePlayerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import authoring.controller.MapDataContainer;
import engine.controller.ApplicationController;
import gamePlayerView.Resources;
import gamePlayerView.GUIPieces.GamePlayOptions;
import gamePlayerView.GUIPieces.TowerColumn;
import gamePlayerView.GUIPieces.InfoBoxes.Controls;
import gamePlayerView.GUIPieces.InfoBoxes.DisplayBoxFactory;
import gamePlayerView.GUIPieces.MachineInfoView.TargetingButtons;
import gamePlayerView.GUIPieces.MachineInfoView.TowerStatistics;
import gamePlayerView.GUIPieces.InfoBoxes.PauseMenu;
import gamePlayerView.GUIPieces.MapView.MapDisplay;
import gamePlayerView.GUIPieces.player_info.PlayerInfoBox;
import gamePlayerView.GUIPieces.resource_store.ResourceStoreView;
import gamePlayerView.ScenePanes.BottomPane;
import gamePlayerView.ScenePanes.IViewPane;
import gamePlayerView.ScenePanes.LeftPane;
import gamePlayerView.ScenePanes.RightPane;
import gamePlayerView.ScenePanes.TopPane;
import gamePlayerView.builders.EntityInfoBox;
import gamePlayerView.builders.EntityInfoBoxBuilder;
//import gamePlayerView.interfaces.ICashAcceptor;
import gamePlayerView.interfaces.IEnemiesKilledAcceptor;
import gamePlayerView.interfaces.IGUIPiece;
import gamePlayerView.interfaces.IPlayerAcceptor;
import gamePlayerView.interfaces.IResourceAcceptor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * @author Guhan Muruganandam
 * 
 */

public class GamePlayerScene {
	private static final String GAME_PLAYER_PATH = "resources/textfiles";
	private Stage myStage;
	private IViewPane myBottomPane;


	private IViewPane myLeftPane;
	private IViewPane myRightPane;
	private IViewPane myTopPane;
	private MapDisplay myMap;
	private Scene myScene;
	private KeyInputHandler myKeyInputHandler;
	private Pane myGamePlayer;
	private List<IPlayerAcceptor> myCash;
	private List<IPlayerAcceptor> myLives; 
	private List<IPlayerAcceptor> myWaves;
	private List<IResourceAcceptor> myResources;
	private List<IEnemiesKilledAcceptor> myEnemiesKilled;
	private BorderPane myBorderPane;
	private DisplayBoxFactory myDisplayBoxFactory;
	private ResourceBundle myResourceBundle;
	private EntityInfoBoxBuilder myBuilder;
	private ApplicationController myAppController;
	private Controls myControls;
	private PauseMenu myPauseMenu;

	public GamePlayerScene(Stage aStage, ApplicationController aAppController) throws Exception{
		myAppController = aAppController;
		
		myStage = aStage;
		myStage.setResizable(false);
		
		myControls = new Controls();
		myCash = new ArrayList<IPlayerAcceptor>();
		myLives = new ArrayList<IPlayerAcceptor>();
		myWaves = new ArrayList<IPlayerAcceptor>();
		myResources = new ArrayList<IResourceAcceptor>();
		myDisplayBoxFactory=new DisplayBoxFactory();
		myBorderPane=new BorderPane();
		myResourceBundle=ResourceBundle.getBundle(GAME_PLAYER_PATH);
		myBuilder=new EntityInfoBoxBuilder(this, myAppController);
		init(aStage);
	}

	public void init(Stage s) throws Exception {
		Scene scene = build(s);
		scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));               
		s.setScene(scene);
		s.show();
//		setScene(s, scene);
	}
	
	public Scene build(Stage stage) throws Exception {
		myBorderPane.setPrefWidth(Resources.SCREEN_WIDTH);
		myBorderPane.setPrefHeight(Resources.SCREEN_HEIGHT);
		myScene = new Scene(myBorderPane);
		
		initMapDisplay();
		initPauseMenu();
		setPanes();
		
		return myScene;
	}
	
	private void initPauseMenu() {
		myPauseMenu = new PauseMenu();
		makePauseMenu();
	}

	public void setPanes() {
		// Create and set panes
		myTopPane = new TopPane();
		createTopPane();
		myBottomPane = new BottomPane();
		createBottomPane();
		myRightPane = new RightPane();
		createRightPane();
		myLeftPane = new LeftPane();
		createLeftPane();
	        myBorderPane.setCenter(myMap.getNode());

		myBorderPane.setTop(myTopPane.getNode());
		myBorderPane.setBottom(myBottomPane.getNode());
		myBorderPane.setRight(myRightPane.getNode());
		myBorderPane.setLeft(myLeftPane.getNode());
	}
	
	private void initMapDisplay() throws Exception {
		myMap = new MapDisplay(myAppController);
		myMap.getControls(myControls);
		myMap.setupDragging(myScene);
	}
	
	public void createTopPane() {
		
	}
	
	public void createBottomPane() {
			
	}
	
	public void createRightPane() {
		
		// Resource Store
//		TowerColumn towercolumn = new TowerColumn();
//		myRightPane.add(towercolumn);
//		myResources.add(towercolumn);
		
		ResourceStoreView resourceStoreView = new ResourceStoreView();
		myResources.add(resourceStoreView);
		myRightPane.add(resourceStoreView);
	}

	public void createLeftPane() {
		
		// Game play options
		IGUIPiece gamePlayOptions = new GamePlayOptions(myAppController);
		
		// Player info
		PlayerInfoBox playerInfoBox = new PlayerInfoBox(myResourceBundle, myDisplayBoxFactory);
		myCash.add((IPlayerAcceptor) playerInfoBox.getMyWallet()); ///FIX LATER
		myLives.add((IPlayerAcceptor) playerInfoBox.getMyLives());//// FIX LATER
		
		myLeftPane.add(gamePlayOptions.getNode());
		myLeftPane.add(playerInfoBox.getNode());
	}
	
//	public void setScreen() throws Exception{
//		// Create panes for pane views
//		createPanes();
//		
//		// Init map display
//		myMap = new MapDisplay(myAppController);
//		myMap.getControls(myControls);
//		
//		// Init pause menu
//		myPauseMenu = new PauseMenu();
//		makePauseMenu();
//		
//		// Set the panes that were created
//		setPanes();
//		
//		// Set up dragging for things on map?
//		myMap.setupDragging(myScene);
//	}
	

//	private void setScene(Stage s, Scene scene) { ///public or private
//		s.setScene(scene);
//		s.show();
//	}
	/*
	public List<ICashAcceptor> getCashAcceptors()
	{
		List<ICashAcceptor> acceptors = new ArrayList<ICashAcceptor>();
		acceptors.add(gui.getCash());
		return acceptors;
	}
	public List<IResourceStoreAcceptor> getResourceStoreAcceptors()
	{
		//TODO:
		// get all frontend components that need info from the resource store (available towers, ect)
	}
	 */

	
	//This might be called by controller
	/*public void rebuild(Stage aStage,BorderPane aPane) {
		myScene = new Scene(myGamePlayer, 1000, 700);
		myGamePlayer.getChildren().clear();
		myGamePlayer.getChildren().add(updateScreen(aPane));
		setScene(aStage,myScene);
	}*/

	public void updateTowerStatisticsRow(/*TowerData tower*/) throws Exception {
		myBottomPane.clear();
		Collection<Node> myCollection=new ArrayList<Node>();
		TowerStatistics myTowerStatistics=new TowerStatistics();
		TargetingButtons myTargetingMechanism=new TargetingButtons(myAppController);
		VBox myTowerOptions=new VBox();
		myTowerOptions.setSpacing(10);
		myTowerOptions.getChildren().addAll(myTowerStatistics.getNode(),myTargetingMechanism.getNode());
		//UpgradeUI myUpgradeandSell=new UpgradeUI();
		//MachineInfo myInfo=new MachineInfo();
		// myCollection.add(myInfo.getView());
		myCollection.add(myTowerOptions);
		//myCollection.add(myUpgradeandSell.getView());
		myBottomPane.add(myCollection);
		myBorderPane.setBottom(myBottomPane.getNode());
	}
	
	public IViewPane getMyBottomPane() {
		return myBottomPane;
	}


//	private void createPanes() {
//		myLeftPane=createLeftPane();
//		myRightPane=createRightPane();
//		myBottomPane=createBottomPane();
//		myTopPane = createTopPane();
//	}
//
//	private void setPanes() {
//		
//		myBorderPane.setCenter(myMap.getView());
//		myBorderPane.setRight(myRightPane.getView());
//		myBorderPane.setBottom(myBottomPane.getView());
//		myBorderPane.setTop(myTopPane.getView());
//		myBorderPane.setLeft(myLeftPane.getView());
//	}
//	private BottomPane createBottomPane() {
//		BottomPane pane=new BottomPane();
//		Label l =new Label("Bottom Pane label");
//		Collection<Node> myCollection=new ArrayList<Node>();
//		//MachineInfo myInfo=new MachineInfo();
//		//myCollection.add(myInfo.getView());
//		myCollection.add(l);
//		pane.add(myCollection);
//		return pane;
//	}
//	
//	private TopPane createTopPane() {
//		TopPane topPane= new TopPane();
//		Label l = new Label("Top Pane label");
//		Collection<Node> myCollection=new ArrayList<Node>();
//		myCollection.add(l);
//		topPane.add(myCollection);
//		return topPane;
//	}
//

//
//	private LeftPane createLeftPane() {
//		LeftPane pane=new LeftPane();
//		GamePlayOptions myGamePlayOptions = new GamePlayOptions(myAppController);
//		InfoBox myWallet=myDisplayBoxFactory.createBox(myResourceBundle.getString("Cash"));
//		InfoBox myLife=myDisplayBoxFactory.createBox(myResourceBundle.getString("Lives"));
//		myCash.add((IPlayerAcceptor) myWallet); ///FIX LATER
//		myLives.add((IPlayerAcceptor) myLife);//// FIX LATER
//		Collection<Node> myCollection=new ArrayList<Node>();
//		myCollection.add(myGamePlayOptions.getView());
//		myCollection.add(myWallet.getView());
//		myCollection.add(myLife.getView());
//		pane.add(myCollection);
//		return pane;
//	}


	public void makePauseMenu() { 
		myPauseMenu.getControls(myControls);
		myPauseMenu.getStage(myStage);
		//myScene.setOnKeyPressed(e -> pause.handleKeyInput(e.getCode()));               
	}

	public List<IPlayerAcceptor> getCash() {
		return myCash;
	}

	public List<IPlayerAcceptor> getLives() {
		return myLives;
	}

	public List<IPlayerAcceptor> getWaves() {
		return myWaves;
	}

	public List<IEnemiesKilledAcceptor> getEnemiesKilled() {
		return myEnemiesKilled;
	}

	public void giveMapData(MapDataContainer aMapData){
		myMap.setMap(aMapData);
		//myScene.setOnKeyPressed(e -> myMap.handleKeyInput(e.getCode()));    

	}

	public MapDisplay getMapDisplay() {
		return myMap;
	}

	public List<IResourceAcceptor> getResources() {
		//TODO;Refactor later to seperate the Resource object from tower column. Not doing now so I don't screw with Grayson's stuff
		return myResources;
	}
	//TODO:Uncomment
	//public List<ISprite> getSprites(){
	//return mySprites;
	//}

	public EntityInfoBoxBuilder getBuilder(){
		return myBuilder;
	}
	public void buildEntityInfoBox(){
		EntityInfoBox myStatisticsBox= myBuilder.build();
		updateDisplay(myStatisticsBox);
	}

	public void updateDisplay(EntityInfoBox myStatisticsBox) {
		myBottomPane.clear();
		Collection<Node> myCollection = new ArrayList<Node>();
		myCollection.add(myStatisticsBox.getNode());
		myBottomPane.add(myCollection);
	}

	public void handleKeyInput(KeyCode code) {
		myKeyInputHandler.handleKeyInput(code);
	}

	public void gameLost() {
		VBox vbox = new VBox();
		Scene scene= new Scene(vbox);
		vbox.getChildren().add(new Text("You Lose"));
		myStage.setScene(scene);
		myStage.show();
		
		
	}

}
