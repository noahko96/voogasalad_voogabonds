package authoring.view.menus;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.swing.*;

import authoring.controller.Router;
import authoring.model.serialization.GameStateSerializer;
import authoring.model.serialization.JSONSerializer;
import main.MainInitializer;
import main.MainMenu;
import mainmenu.screens.LoadAuthoringScreen;
import mainmenu.screens.NewAuthoringScreen;
import utility.ErrorBox;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author Christopher Lu
 * Creates the menus under the File Button. These include: New, Open, Close, Save, and Save As.
 */

public class FileMenu extends Menu {
	
	private ResourceBundle myResources;
	private String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private Menu file;
	private Router router;
	
	public FileMenu(MenuBar bar, Router r) {
		this.router = r;
		this.myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "View");
		this.file = new Menu(myResources.getString("FileMenuLabel"));
		fileOptions(file);
		bar.getMenus().add(file);
	}
	
	/**
	 * Populates the drop down file menu.
	 * @param menu
	 */
	private void fileOptions(Menu menu) {
		MenuItem newProject = new MenuItem(myResources.getString("NewFileLabel"));
		performNewProject(newProject);
		MenuItem openProject =  new MenuItem(myResources.getString("OpenFileLabel"));
		performOpenProject(openProject);
		MenuItem closeProject = new MenuItem(myResources.getString("CloseFileLabel"));
		performCloseProject(closeProject);
		MenuItem saveProject = new MenuItem(myResources.getString("SaveFileLabel"));
		performSaveProject(saveProject);
		MenuItem saveAs = new MenuItem(myResources.getString("SaveAsLabel"));
		performSaveAs(saveAs);
		file.getItems().addAll(newProject, openProject, closeProject, saveProject, saveAs);
	}
	
	/**
	 * Opens a new project when user clicks on new project.
	 * @param newProject
	 */
	private void performNewProject(MenuItem newProject) {
		newProject.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				try {
					NewAuthoringScreen newScreen = new NewAuthoringScreen();
				} catch (IOException e) {
					ErrorBox.displayError(e.getMessage());
				}
			}
		});
	}
	
	/**
	 * Opens up an existing project by displaying a TableView displaying all the existing projects.
	 * @param openProject
	 */
	private void performOpenProject(MenuItem openProject) {
		openProject.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				try {
					LoadAuthoringScreen loadScreen = new LoadAuthoringScreen(myResources.getString("AuthorOldProject"),
							myResources.getString("ExistingAuthoringFiles"));
				} catch (IOException e) {
					ErrorBox.displayError(myResources.getString("MysteryError"));
				}
			}
		});
	}
	/**
	 * Closes current project.
	 * @param closeProject
	 */
	private void performCloseProject(MenuItem closeProject) {
		closeProject.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Platform.exit();
			}
		});
	}
	
	/**
	 * Saves current project.
	 * @param saveProject
	 */
	private void performSaveProject(MenuItem saveProject) {
		saveProject.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				if (router.getGameTitle() != null){
					save(router.getGameTitle(), true);
				}
				else {
					saveAs();
				}
			}
		});
	}
	
	/**
	 * Saves current project.
	 * @param saveAs
	 */
	private void performSaveAs(MenuItem saveAs) {
		saveAs.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				saveAs();
			}
		});
	}
	
	public void saveAs(){
		FileChooser newGameSave = new FileChooser();
		Stage stage = new Stage();
		File file = newGameSave.showSaveDialog(stage);
		if (file != null) {
			router.setGameTitle(file.getName());
			save(file.getName(), false);
		}
	}

	private void save(String name, boolean overwrite) {
		GameStateSerializer GSS = new GameStateSerializer(router);
		try {
			GSS.saveGameState(name, overwrite);
		} catch (Exception exception) {
			ErrorBox.displayError(myResources.getString("MysteryError"));
		}
	}
	
}
