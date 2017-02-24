package authoring.view.tabs;

import java.util.ResourceBundle;

import authoring.controller.MapDataContainer;
import authoring.view.display.GameDisplay;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

/**
 * @author Christopher Lu
 * Initializes the Map Tab. This tab will contain the Map and te toolbar where the user will be able to edit the map.
 */

public class MapTab extends Tab {

	private TabPane tbPane;
	private Tab mapTab;
	private String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "View");
	private GameDisplay mapDisplay;
	private BorderPane pane;
	private Scene scene;
	private MapDataContainer controller;
	private String mapDataFilePath;
	
	public MapTab(TabPane tPain, Scene sc, MapDataContainer con) {
		super("Map");
		this.tbPane = tPain;
		this.pane = new BorderPane();
		this.scene = sc;
		this.controller = con;
		this.mapDisplay = new GameDisplay(pane, scene, controller);
		this.setContent(mapDisplay.getTerrainBox());
	}
}
