package main;

import gamePlayerView.Main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import authoring.controller.MapDataContainer;
import authoring.controller.Router;
import authoring.model.serialization.GameStateSerializer;
import authoring.view.display.AuthorDisplay;
import authoring.view.display.GameDisplay;
import authoring.view.menus.TopMenuBar;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Christopher Lu
 * Initializes the VOOGASalad application to the Main Menu.
 */

public class MainInitializer {
	
	private Stage stage;
	private int screenWidth;
	private int screenHeight;
	private String title;
	private Scene scene;
	private BorderPane root;
	private static AuthorDisplay AuthDisp;

	public MainInitializer(Stage s) throws IOException {
		this.stage = s;
		root = new BorderPane();
		setUpScreenResolution();
		MainMenu menu = new MainMenu(this, s);
		Scene mainMenu = menu.init();
		scene = new Scene(root, screenWidth, screenHeight, Color.WHITE);
		scene.getStylesheets().add("style.css");
		stage.setScene(mainMenu);
		stage.show();
	}

	private void setUpScreenResolution() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight();
	}
	
	public void setTitle(String newTitle) {
		title = newTitle;
	}
	
	public int getScreenWidth() {
		return screenWidth;
	}
	
	public int getScreenHeight() {
		return screenHeight;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Scene getScene() {
		return scene;
	}
	

	public static GameStateSerializer setUpSerialization(){
		return new GameStateSerializer(AuthDisp.getRouter());
	}
	public void initPlayer() {
		try{
			Stage s = new Stage();
			Main playInstance = new Main();
			playInstance.start(s);
		}
		catch(Exception e){
		}
	}
	
}
