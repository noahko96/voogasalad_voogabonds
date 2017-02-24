// This entire file is part of my masterpiece.
// CHRISTOPHER LU
/**
 * Purpose: The purpose of this class is to generate an image gallery that the user can choose from.
 * The image gallery contains images that the user can select by clicking on an image and pressing the confirm button.
 * When this occurs, and the user decides to use an image instead of a color to fill a terrain,
 * whenever the user draws this terrain, the terrain's fill will be of the selected image. This image gallery presents the 
 * images in a user friendly way, in that the user does not have to navigate through a FileChooser.
 * What I changed: Back when I was first considering how to allow the user to set a cell's appearance to an image, I considered using a FileChooser and I considered
 * keeping the implementation within the GridToolBar class where all my other handlers, like colorHandler and sink/spawnHandler were. However, I refactored and changed
 * the FileChooser in GridToolBar into a separate ImageGallery class. The reason why I did this was to provide a more user friendly interface for choosing images,
 * and to eliminate the possible errors that might occur when a user selects a non image not from the specified directory.
 * Design justification: 
 * 		Here are some of the code smells and design concepts fixed/improved upon by creating a separate ImageGallery class:
 * 			Large Class: My GridToolBar class was already bursting at the seams at nearly 500 lines, and given my limited amount of time, I knew that I would have to start
 * 			refactoring soon or else my class would be even larger. After successfully refactoring the ImageGallery into its separate class, if I had more time, I would 
 * 			have done the same for the fillHandler, sinkHandler, and spawnHandlers. This would result in a favorable division of responsibility, making debugging and testing much easier.
 * 			For example, if the front end of the spawn handler was having issues and I made a separate class for just the spawn handler display, I would go to that specific class
 *			to easily fix the error. In addition, splitting the different handlers and windows up into different classes makes my toolBar class and my individual component classes more
 *			extendable. Ideally, I would create a parent object toolbar class that contains just the skeleton of a toolbar. I would also have separate classes for spawn handler,
 *			sink handler, some other feature I want to have on my toolbar in a separate class, and I would essentially have a sort of flexible system where I could make a toolbar
 *			with custom features. I could make a toolbar elsewhere (e.g. for setting background color) that would only have the colorHandler, or I could add a toolbar in the main menu
 *			using the parent toolbar class that has a Help Menu handler (implemented in a separate class) as well as a color handler. * 			
 */

package authoring.view.display;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Christopher Lu
 * ImageGallery displays images in a grid based view by iterating through files and
 * placing imageviews of the files in a horizontal tile pane.
 */

public class ImageGallery {
	
	private Stage terrainStage;
	private Scene scene;
	private String filePath;
	private String selectedImagePath;
	private GridToolBar toolBar;
	private ScrollPane scrollPane;
	private ListView<TerrainImage> imagePane;
	private int screenWidth;
	private VBox container;
	private Button confirmImage;
	private ResourceBundle myResources;
	private String DEFAULT_RESOURCE_PACKAGE = "resources/";
	
	public ImageGallery(String fPath) {
		setUpScreenResolution();
		this.myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "View");
		this.scrollPane = new ScrollPane();
		this.terrainStage = new Stage();
		this.container = new VBox();
		container.setId("vbox");
		imagePane = new ListView<TerrainImage>();
		imagePane.setOrientation(Orientation.HORIZONTAL);
		imagePane.setId("background");
		imagePane.setMaxHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/4);
		imagePane.setMinWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2);
		this.filePath = fPath;
		this.confirmImage = new Button(myResources.getString("ApplyChanges"));
		confirmImage.setId("button");
		container.getChildren().addAll(imagePane, confirmImage);
		this.scene = new Scene(container);
		scene.getStylesheets().add("style.css");
		populatePane();
		terrainStage.setScene(scene);
		terrainStage.show();
		confirmImageHandler();
	}
	
	public ImageGallery(GridToolBar tBar, String fPath) {
		this(fPath);
		this.toolBar = tBar;
	}
	
	private void setUpScreenResolution() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) screenSize.getWidth();
	}
	
	/**
	 * Iterates through the specified file directory and displays the images in a selectable ListView.
	 */
	private void populatePane() {
		String path = myResources.getString("TerrainImageFilePath").substring(1);
		File file = new File(path);
		File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			TerrainImage image  = new TerrainImage(fileList[i].toURI().toString(), this);
			image.setFitWidth(screenWidth*0.1);
			image.setFitHeight(screenWidth*0.1);
			imagePane.getItems().add(image);
		}
	}
	
	/**
	 * Handles the confirm button. when clicked, the toolBar's selectedImagePath for the terrain is set to the selected image's imagepath.
	 * Gallery closes when image is confirmed.
	 */
	private void confirmImageHandler() {
		confirmImage.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					selectedImagePath = imagePane.getSelectionModel().getSelectedItem().getImagePath();
					toolBar.setSelectedImagePath(selectedImagePath);
					terrainStage.close();
				} catch (Exception e1) {
					terrainStage.close();
				}
			}
		});
	}
	
	public void setSelectedImagePath(String newPath) {
		selectedImagePath = newPath;
	}
	
	public String getSelectedImagePath() {
		return selectedImagePath;
	}
	
}
