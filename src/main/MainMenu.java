package main;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import utility.BoomBox;
import authoring.controller.Router;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import mainmenu.tabs.AuthoringTab;
import mainmenu.tabs.LoadGameTab;
import mainmenu.tabs.NewGameTab;
import mainmenu.tabs.PlayerTab;
/**
 * @author ChristopherLu
 * This class generates the main menu that allows the user to choose between entering the player or the authoring environment.
 */
public class MainMenu {
	private Scene scene;
	private Stage stage;
	private MainInitializer initializer;
	private BorderPane pane;
	private ResourceBundle myResources;
	private String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private TabPane tabContainer;
	private PlayerTab playTab;
	private AuthoringTab authorTab;
	private int screenWidth;
	private int screenHeight;
	private static MediaPlayer mp;
	
	public MainMenu(MainInitializer init, Stage s) {
		setUpScreenResolution();
		this.stage = s;
		this.initializer = init;
		this.pane = new BorderPane();
		pane.setId("menu-background");
		this.scene = new Scene(pane, init.getScreenWidth()/2, init.getScreenHeight()/2);
		scene.getStylesheets().add("style.css");
		this.myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "View");
		this.tabContainer = new TabPane();
		tabContainer.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		tabContainer.setId("background");
		this.playTab = new PlayerTab(tabContainer, initializer);
		this.authorTab = new AuthoringTab(tabContainer, initializer);
		for (Tab tab: tabContainer.getTabs()){
			tab.setId("tab");
		}
		this.initializer.setTitle(myResources.getString("MainMenuTitle"));
	}
	
	public Scene init() {
		pane.setPadding(new Insets(screenHeight*0.01, screenWidth*0.01, screenHeight*0.01, screenWidth*0.01));
		pane.setTop(createText(myResources.getString("ApplicationTitle")));
		pane.setCenter(tabContainer);
		
		return scene;
	}
	
	private Text createText(String desiredText) {
		Text t = new Text(desiredText);
		t.setFont(Font.font("Verdana", 70));
		return t;
	}
	
	private void setUpScreenResolution() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight();
	}
	
}