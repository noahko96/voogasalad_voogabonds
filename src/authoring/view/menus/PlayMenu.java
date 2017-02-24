package authoring.view.menus;

import gamePlayerView.Main;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * @author Christopher Lu
 * Creates the Play submenus that include: play game from start, play game from current state, and return to authoring.
 */

public class PlayMenu extends Menu {

	private ResourceBundle myResources;
	private String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private Menu play;

	public PlayMenu(MenuBar bar) {
		this.myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "View");
		this.play = new Menu(myResources.getString("PlayMenuLabel"));
		personalizeOptions(play);
		bar.getMenus().add(play);
	}

	private void personalizeOptions(Menu play) {
		MenuItem playStart = new MenuItem(myResources.getString("PlayFromStart"));
		performPlayStart(playStart);
		MenuItem playHere = new MenuItem(myResources.getString("PlayFromHere"));
		performPlayHere(playHere);
		MenuItem returnToAuthoring = new MenuItem(myResources.getString("ReturnToAuthoring"));
		performReturnAuthoring(returnToAuthoring);
		play.getItems().addAll(playStart, playHere, returnToAuthoring);
	}

	private void performPlayStart(MenuItem playStart) {
		playStart.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				createGamePlayerInstance();
			}
		});
	}

	private void performPlayHere(MenuItem playHere) {
		playHere.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				createGamePlayerInstance();
			}
		});
	}

	private void performReturnAuthoring(MenuItem returnToAuthoring) {
		returnToAuthoring.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
			}
		});
	}
	
	private void createGamePlayerInstance(){
		try{
			Stage s = new Stage();
			Main playInstance = new Main();
			playInstance.start(s);
		}
		catch(Exception e){
		}
	}

}
