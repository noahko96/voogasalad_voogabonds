package mainmenu.tabs;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.ResourceBundle;

import authoring.model.serialization.JSONSerializer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.MainInitializer;

/**
 * @author Christopher Lu
 * Implements the front end aspects of the NEW game tab in the main menu screen. The user can create a new project in this tab by setting the project's name and save path, 
 * then clicking on CREATE NEW GAME.
 */

public class NewGameTab {

	private TabPane root;
	private ResourceBundle myResources;
	private String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private Tab newGame;
	private int screenWidth;
	private int screenHeight;
	private MainInitializer initializer;

	
	public NewGameTab (TabPane pane, MainInitializer init) {
		setUpScreenResolution();
		this.myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "View");
		this.root = pane;
		this.newGame = new Tab(myResources.getString("NewGameTabName"));
		this.initializer = init;
		populateTab();
		root.getTabs().add(newGame);
	}
	
	private void populateTab() {
		VBox newGameOptions = new VBox(screenHeight*0.1);
		newGameOptions.setPadding(new Insets(screenHeight*0.01, screenWidth*0.01, screenHeight*0.01, screenWidth*0.01));
		Button chooseSavePath = new Button(myResources.getString("SavePath"));
		saveHandler(chooseSavePath);
		newGameOptions.getChildren().addAll(chooseSavePath);
		newGame.setContent(newGameOptions);
	}
	
	private void saveHandler(Button button) {
		button.setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(final ActionEvent e) {
						FileChooser newGameSave = new FileChooser();
						Stage stage = new Stage();
						File file = newGameSave.showSaveDialog(stage);
						if (file != null) {
							JSONSerializer json = new JSONSerializer();
							try {
								json.serializeToFile(file, file.getName());
							} catch (Exception exception) {
								//System.out.println("Cannot create new game.");
							}
						}
					}
				});
	}
	
	private void setUpScreenResolution() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight();
	}
	
}
