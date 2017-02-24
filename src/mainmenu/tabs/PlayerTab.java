package mainmenu.tabs;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ResourceBundle;

import authoring.controller.Router;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import main.MainInitializer;
import mainmenu.screens.LoadAuthoringScreen;
import mainmenu.screens.LoadGameScreen;
import mainmenu.screens.NewGameScreen;
import utility.ErrorBox;

/**
 * @author Christopher Lu
 * User can choose to either play a new game a load a previous game in this tab.
 */

public class PlayerTab extends Tab {
	private static final double PERCENT = .953;
	
	private TabPane root;
	private Tab playerTab;
	private int screenWidth;
	private int screenHeight;
	private ResourceBundle myResources;
	private String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private HBox playOptions;
	private MainInitializer initializer;
	
	public PlayerTab(TabPane mainMenuTab, MainInitializer init) {
		setUpScreenResolution();
		this.root = mainMenuTab;
		this.myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "View");
		this.playerTab = new Tab(myResources.getString("EnterPlayerMode"));
		this.playOptions = new HBox(3);
		playOptions.setId("background");
		this.initializer = init;
		populateTab();
		root.getTabs().add(playerTab);
	}
	
	private void populateTab() {
		populateBox();
		playerTab.setContent(playOptions);
	}
	
	private void populateBox() {
		Button newGame = new Button(myResources.getString("PlayNewGame"));
		newGame.getStylesheets().add("style.css");
		newGame.setId("button");
		newGame.setMinWidth(screenWidth/4 * PERCENT);
		newGame.setMinHeight(screenWidth/5);
		newGame.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent e) {
				try {
					handleNewGame();
				} catch (IOException e1) {
					//System.out.println("playerTab 69");
					e1.printStackTrace();
					ErrorBox.displayError(myResources.getString("NewPlayerError"));
				}
			}
		});
		Button loadGame = new Button(myResources.getString("PlayOldGame"));
		loadGame.getStylesheets().add("style.css");
		loadGame.setId("button");
		loadGame.setMinWidth(screenWidth/4 * PERCENT);
		loadGame.setMinHeight(screenWidth/5);
		loadGame.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					handleOldGame();
				} catch (IOException e){
					//System.out.println("playerTab 86");
					e.printStackTrace();
					ErrorBox.displayError(myResources.getString("NewPlayerError"));
				}
			}
		});
		playOptions.getChildren().addAll(newGame, loadGame);
	}
	
	private void handleNewGame() throws IOException {
		NewGameScreen newGameScreen = new NewGameScreen(myResources.getString("PlayNewGame"), 
				myResources.getString("ExistingAuthoringFiles"));
	}
	
	private void handleOldGame() throws IOException {
		LoadGameScreen loadGameScreen = new LoadGameScreen(myResources.getString("PlayOldGame"),
				myResources.getString("ExistingSavedGames"));
	}
	
	private void setUpScreenResolution() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight();
	}
	
}
