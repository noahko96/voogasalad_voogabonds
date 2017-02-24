package gamePlayerView;

import engine.controller.ApplicationController;
import gamePlayerView.gamePlayerView.GamePlayerScene;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * @author Guhan Muruganandam
 */

/**
 * Temporary main
 */

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage s) throws Exception {
		//TODO:
		ApplicationController appController = new ApplicationController();

		appController.init(s, "taptapgood");
//		appController.init(s, "AuthorGame");
		//appController.init(s, "ExampleGame");
//		GamePlayerScene gamePlayerScene = new GamePlayerScene(s); 
//		gamePlayerScene.init(s);
//		Router myRouter = new Router(gamePlayerScene);
  
	}

}