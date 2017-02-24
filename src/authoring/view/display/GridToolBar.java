package authoring.view.display;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import authoring.controller.MapDataContainer;
import authoring.model.map.TerrainData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utility.BoomBox;
import utility.ErrorBox;

/**
 * @author Christopher Lu
 * Creates the toolbar for the grid, allowing the user to select the draw tool and terrain type and fill in terrain types upon clicking individual cells.
 */

public class GridToolBar {

	private Scene scene;
	private HBox toolBar;
	private ResourceBundle myResources;
	private String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private boolean toggleStatus;
	private boolean spawnStatus;
	private boolean sinkStatus;
	private boolean imageStatus = false;
	private Color selectedColor;
	private String selectedTerrain;
	private String selectedImagePath;
	private Image mouseCursor;
	private int screenHeight;
	private int screenWidth;
	private HashMap<String, Color> colorToTerrain;
	private HashMap<String, String> imageToTerrain;
	private HashMap<String, Boolean> boolToTerrain;
	private ObservableList<String> terrainOptions = 
			FXCollections.observableArrayList (
					"Add Terrain..."
					);
	private MapDataContainer controller;
	private ToggleButton myDraw;
	private ToggleButton mySpawn;
	private ToggleButton mySink;

	public GridToolBar(VBox box, Scene sc, MapDataContainer controller) {
		setUpScreenResolution();
		this.scene = sc;
		this.myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "View");
		this.toolBar = new HBox();
		toolBar.setId("hbox");
		this.colorToTerrain = new HashMap<String, Color>();
		this.imageToTerrain = new HashMap<String, String>();
		this.boolToTerrain = new HashMap<String, Boolean>();
		selectedColor = Color.WHITE;
		this.selectedTerrain = myResources.getString("DNE");
		this.controller = controller;
		createToolBar();
		importTerrains();
		box.getChildren().add(toolBar);
		toolBar.setAlignment(Pos.BOTTOM_CENTER);
	}

	private void setUpScreenResolution() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight();
	}

	/**
	 * Populates the toolbar with the togggle buttons allowing the user to set sink, spawn or terrain.
	 * Populates toolbar with combo box that contains available terrains.
	 * Populates toolbar with music button.
	 */
	private void createToolBar() {
		ToggleGroup toggles = new ToggleGroup();
		myDraw = new ToggleButton(myResources.getString("DrawMode"));
		myDraw.setToggleGroup(toggles);
		myDraw.setId("button");
		toggleHandler(toggles);
		mySpawn = new ToggleButton(myResources.getString("SpawnPoint"));
		mySpawn.setToggleGroup(toggles);
		mySpawn.setId("button");
		spawnHandler(toggles);
		mySink = new ToggleButton(myResources.getString("SinkPoint"));
		mySink.setToggleGroup(toggles);
		mySink.setId("button");
		sinkHandler(toggles);
		ComboBox<String> terrainChooser = new ComboBox<String>(terrainOptions);
		terrainChooser.setId("menu-combobox");
		terrainChooser.setPromptText(myResources.getString("Terrains"));
		terrainChooser.setMinHeight(screenHeight*0.04);
		terrainHandler(terrainChooser);
		Button music = new Button(myResources.getString("SelectMusic"));
		music.setId("button");
		musicHandler(music);
		toolBar.getChildren().addAll(mySink, mySpawn, myDraw, terrainChooser, music);
	}

	/**
	 * Plays music when pressed. Currently, the only available song is X Gon Give it to ya.
	 * @param music
	 */
	private void musicHandler(Button music) {
		music.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event){
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3", "*.mp3"), new FileChooser.ExtensionFilter("WAV", "*.wav"));
				File file = fileChooser.showOpenDialog(new Stage());
				if (file != null){
					String fileName = file.getName();
					try {
						controller.setTune(fileName);
					} catch (Exception e) {
						ErrorBox.displayError(e.getMessage());
					}
				}
			}
		});
	}

	/**
	 * During the deserialization of an authoring environment, the previous authoring environment may already have existing terrains.
	 * These existing terrains need to show up not only on the map, but in the terrains combo box. This method does that.
	 */
	private void importTerrains() {
		HashMap<String, String> terrainList = controller.getValidTerrainMap();
		for (String terrainName : terrainList.keySet()) {
			try {
				boolToTerrain.put(terrainName, false);
				colorToTerrain.put(terrainName, Color.valueOf(terrainList.get(terrainName)));
			} catch (Exception e) {
				boolToTerrain.put(terrainName, true);
				imageToTerrain.put(terrainName, terrainList.get(terrainName));
			}
			terrainOptions.add(terrainName);
		}
	}

	/**
	 * Sets toggleStatus to true if the draw mode toggle button is selected, or false if not.
	 * When selected, user will draw terrain when clicking or dragging on the map.
	 * @param drawMode
	 */
	private void toggleHandler(ToggleGroup drawGroup)  {
		drawGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov,
					Toggle toggle, Toggle new_toggle) {		 
				if (new_toggle == null) {
					toggleStatus = false;
					myDraw.setId("button");
				}
				else {
					if (drawGroup.getSelectedToggle().equals(myDraw)) {
						myDraw.setId("button-selected");
						mySpawn.setId("button");
						mySink.setId("button");
						toggleStatus = true;
						spawnStatus = false;
						sinkStatus = false;
						mouseCursor = new Image(getClass().getClassLoader().getResourceAsStream("resources/rsz_mousepenicon.png"));  
						scene.setCursor(new ImageCursor(mouseCursor)); 
					}
				}
				if (!toggleStatus) {
					scene.setCursor(Cursor.DEFAULT);
				}
			}
		});
	}

	/**
	 * When this toggle is selected, the user will place spawn points on the map when clicking cells.
	 * @param spawnGroup
	 */
	private void spawnHandler(ToggleGroup spawnGroup) {
		spawnGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov,
					Toggle toggle, Toggle new_toggle) {		 
				if (new_toggle == null) {
					spawnStatus = false;
					scene.setCursor(Cursor.DEFAULT);
					mySpawn.setId("button");
				}
				else {
					if (spawnGroup.getSelectedToggle().equals(mySpawn)) {
						myDraw.setId("button");
						mySpawn.setId("button-selected");
						mySink.setId("button");
						toggleStatus = false;
						spawnStatus = true;
						sinkStatus = false;
						scene.setCursor(Cursor.CROSSHAIR); 
					}
				}
			}
		});
	}

	/**
	 * When this toggle is seleced, the user will place sink points on the map when clicking cells.
	 * @param sinksGroup
	 */
	private void sinkHandler(ToggleGroup sinksGroup) {
		sinksGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov,
					Toggle toggle, Toggle new_toggle) {		 
				if (new_toggle == null) {
					sinkStatus = false;
					scene.setCursor(Cursor.DEFAULT);
					mySink.setId("button");
				}
				else {
					if (sinksGroup.getSelectedToggle().equals(mySink)) {
						myDraw.setId("button");
						mySpawn.setId("button");
						mySink.setId("button-selected");
						toggleStatus = false;
						spawnStatus = false;
						sinkStatus = true;
						scene.setCursor(Cursor.CROSSHAIR); 
					}
				}
			}
		});
	}

	/**
	 * Sets selectedTerrain to the terrain chosen by the user when using the terrain combo box from the toolbar.
	 * @param terrains
	 */
	private void terrainHandler(ComboBox<String> terrains) {
		terrains.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				String selectedItem = terrains.getValue();
				if (selectedItem == null){
					return;
				}
				else if (selectedItem.equals(myResources.getString("DefaultTerrainOption"))) {
					Stage createTerrain = new Stage();
					createTerrain.initModality(Modality.APPLICATION_MODAL);
					createTerrain.setOnCloseRequest(new EventHandler<WindowEvent>() {
						public void handle(WindowEvent event){
							terrains.setValue(null);
							createTerrain.close();
						}
					});
					VBox choiceContainer = new VBox(screenHeight*0.02);
					choiceContainer.setId("menu-vbox");
					HBox choiceArea = new HBox(screenWidth*0.01);
					choiceArea.setId("hbox");
					HBox toggleArea = new HBox(screenWidth*0.05);
					toggleArea.setId("hbox");
					ColorPicker colorChooser = new ColorPicker();
					colorChooser.setId("menu-combobox");
					TextField terrainName = new TextField();
					terrainName.setText(myResources.getString("TerrainName"));
					terrainName.setId("menu-textfield");
					Button chooseImage = new Button(myResources.getString("ChooseTerrainImage"));
					chooseImage.setId("button");
					confirmImageHandler(chooseImage);
					ToggleGroup toggles = new ToggleGroup();
					ToggleButton imageMode = new ToggleButton(myResources.getString("ImageMode"));
					imageMode.setToggleGroup(toggles);
					imageMode.setId("button");
					imageStatus = false;
					fillImageHandler(toggles, imageMode, terrainName);
					Button confirmTerrain = new Button(myResources.getString("ApplyChanges"));
					confirmTerrain.setId("button");
					choiceArea.getChildren().addAll(colorChooser, chooseImage, terrainName, confirmTerrain);
					toggleArea.getChildren().addAll(imageMode);					
					choiceContainer.getChildren().addAll(choiceArea, toggleArea);
					confirmTerrainHandler(createTerrain, terrainName, confirmTerrain, colorChooser, terrains);
					Scene terrainChoiceScene = new Scene(choiceContainer);
					terrainChoiceScene.getStylesheets().add("style.css");
					createTerrain.setScene(terrainChoiceScene);
					createTerrain.show();
				}
				else {
					selectedTerrain = terrains.getValue();
					imageStatus = boolToTerrain.get(terrains.getSelectionModel().getSelectedItem());
					if (imageStatus) {
						selectedImagePath = imageToTerrain.get(terrains.getSelectionModel().getSelectedItem());
					}
					else {
						selectedColor = colorToTerrain.get(terrains.getSelectionModel().getSelectedItem());
					}
				}
			}
		});
	}

	/**
	 * If the choose image toggle button is selected, imageStatus is set to true.
	 * @param group
	 * @param button
	 * @param field
	 */
	private void fillImageHandler(ToggleGroup group, ToggleButton button, TextField field) {
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov,
					Toggle toggle, Toggle new_toggle) {		 
				if (new_toggle == null) {
					button.setId("button");
					imageStatus = false;
				}
				else {
					button.setId("button-selected");
					imageStatus = true;
				}
			}
		});
	}

	/**
	 * If the confirm image button in the ImageGallery is selected, the image that was most recently selected will be the image
	 * that the terrain will display.
	 * @param chooseImage
	 */
	private void confirmImageHandler(Button chooseImage) {
		chooseImage.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				ImageGallery terrainImages = new ImageGallery(GridToolBar.this, myResources.getString("TerrainImageFilePath"));
			}
		});
	}

	/**
	 * This is the handler for the final button the user clicks on to confrim terrain.
	 * When clicked, the imageStatus of the terrain is added to boolToTerrain, the terrain name is added to the combo box,
	 * the fill is either added to the colorToterrain or imageToTerrain maps, and most importantly, the data is added to the MapDataContainer.
	 * @param createTerrain
	 * @param field
	 * @param b
	 * @param colors
	 * @param terrains
	 */
	private void confirmTerrainHandler(Stage createTerrain, TextField field, Button b, ColorPicker colors, ComboBox<String> terrains) {
		b.setOnAction(new EventHandler<ActionEvent>() {
			public void handle (ActionEvent event) {
				terrainOptions.add(field.getText());
				boolToTerrain.put(field.getText(), imageStatus);
				if (imageStatus) {
					imageToTerrain.put(field.getText(), selectedImagePath);
					try {
						controller.addValidTerrain(field.getText(), selectedImagePath);
					} catch (Exception e) {
						ErrorBox.displayError(myResources.getString("TerrainError"));
					}
				}
				else {
					colorToTerrain.put(field.getText(), colors.getValue());
					try {
						controller.addValidTerrain(field.getText(), colors.getValue().toString());
					} catch (Exception e) {
						ErrorBox.displayError(myResources.getString("TerrainError"));
					}
				}
				terrains.setValue(null);
				createTerrain.close();
			}
		});
	}

	public boolean getSpawnStatus() {
		return spawnStatus;
	}

	public boolean getToggleStatus() {
		return toggleStatus;
	}
	
	public void setToggleStatus(boolean newToggleStatus) {
		toggleStatus = newToggleStatus;
	}

	public  boolean getSinkStatus() {
		return sinkStatus;
	}

	public Color getSelectedColor() {
		return selectedColor;
	}

	public String getSelectedTerrain() {
		return selectedTerrain;
	}

	public String getSelectedImagePath() {
		return selectedImagePath;
	}

	public void setSelectedImagePath(String newPath) {
		selectedImagePath = newPath;
	}

	public boolean getImageStatus() {
		return imageStatus;
	}

}
