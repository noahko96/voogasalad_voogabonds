package authoring.view.menus;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ResourceBundle;

import authoring.controller.Router;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import main.MainInitializer;
;
/**
 * @author Christopher Lu
 * Sets up the top menu bar that will allow the user to perform multiple actions, including accessing a help menu, oepning and closing workspaces, playing the game,
 * deleting towers, adding enemies, and personalize the workspace among multiple other things.
 */

public class TopMenuBar {
	
	private MenuBar topMenu;
	private ResourceBundle myResources;
	private String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private Router router;
	
	public TopMenuBar(BorderPane root, Router r) {
		topMenu = new MenuBar();
		topMenu.setId("background");
		this.router = r;
		Menu fileMenu = new FileMenu(topMenu, router);
		Menu playMenu = new PlayMenu(topMenu);
		for (Menu menu: topMenu.getMenus()){
			menu.setId("menu");
		}
		root.setTop(topMenu);
		this.myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "View");
	}
}
