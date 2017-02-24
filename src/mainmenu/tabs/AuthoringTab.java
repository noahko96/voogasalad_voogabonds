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
import mainmenu.screens.NewAuthoringScreen;
import utility.ErrorBox;

/**
 * @author Christopher Lu
 * User can choose to either enter New Porject or Load Project from this tab.
 */

public class AuthoringTab extends Tab {
	private static final double PERCENT = .953;
	
	private TabPane root;
	private Tab authorTab;
	private int screenWidth;
	private int screenHeight;
	private ResourceBundle myResources;
	private String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private HBox authorOptions;
	private MainInitializer initializer;

	public AuthoringTab(TabPane mainMenuTab, MainInitializer init) {
		setUpScreenResolution();
		this.root = mainMenuTab;
		this.myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "View");
		this.authorTab = new Tab(myResources.getString("EnterAuthorMode"));
		this.authorOptions = new HBox(3);
		authorOptions.setId("background");
		this.initializer = init;
		populateTab();
		root.getTabs().add(authorTab);
	}
	
	private void populateTab() {
		populateBox();
		authorTab.setContent(authorOptions);
	}
	
	private void populateBox() {
		Button newProject = new Button(myResources.getString("AuthorNewProject"));
		newProject.getStylesheets().add("style.css");
		newProject.setId("button");
		newProject.setMinWidth(screenWidth/4 * PERCENT);
		newProject.setMinHeight(screenWidth/5);
		newProject.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent e) {
				try {
					handleNewProject();
				} catch (IOException e1) {
					ErrorBox.displayError(myResources.getString("NewAuthoringError"));
				}
			}
		});
		Button loadProject = new Button(myResources.getString("AuthorOldProject"));
		loadProject.getStylesheets().add("style.css");
		loadProject.setId("button");
		loadProject.setMinWidth(screenWidth/4 * PERCENT);
		loadProject.setMinHeight(screenWidth/5);
		loadProject.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					handleOldProject();
				} catch (IOException e1) {
					ErrorBox.displayError(myResources.getString("LoadAuthoringError"));
				}
			}
		});
		authorOptions.getChildren().addAll(newProject, loadProject);
	}
	
	private void handleNewProject() throws IOException {
		NewAuthoringScreen newScreen = new NewAuthoringScreen();
	}
	
	private void handleOldProject() throws IOException {
		LoadAuthoringScreen loadScreen = new LoadAuthoringScreen(myResources.getString("LoadExistingAuthoringTitle"), 
				myResources.getString("ExistingAuthoringFiles"));
	}
	
	private void setUpScreenResolution() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight();
	}
	
}
