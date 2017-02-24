package mainmenu.screens;

import java.awt.Toolkit;
import java.io.IOException;

import authoring.controller.Router;
import authoring.model.serialization.GameStateLoader;
import authoring.view.display.AuthorDisplay;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;


/**
 * @author Christopher Lu
 * This class creates the load authoring environment screen when the user wants to open a preexisting authoring environment to work on.
 */

public class LoadAuthoringScreen extends AbstractLoadScreen {

	private Router router;
	
	public LoadAuthoringScreen(String title, String files) throws IOException {
		super(title, files);
		this.router = new Router();
	}
	
	/**
	 * Loads the correct game data containers.
	 */
	protected void start(String selectedGame){
		GameStateLoader loader = new GameStateLoader();
		router.clearContainers();
		router.setGameTitle(selectedGame);
		loader.loadMapData(router, selectedGame);
		loader.loadEntityData(router, selectedGame);
		loader.loadLevelData(router, selectedGame);
		loader.loadWaveData(router, selectedGame);
		loader.loadPlayerData(router, selectedGame);
		initAuthoring();
	}
	
	public void initAuthoring() {
		BorderPane bPane = new BorderPane();
		Scene authorScene = new Scene(bPane, Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
				Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		authorScene.getStylesheets().add("style.css");
	    AuthorDisplay authoring = new AuthorDisplay(bPane, authorScene, router);
		getStage().setScene(authorScene);
	}
		
	
}
