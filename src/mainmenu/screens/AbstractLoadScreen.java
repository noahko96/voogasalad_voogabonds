package mainmenu.screens;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.ResourceBundle;

import authoring.model.serialization.GameStateDeserializer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utility.ErrorBox;

/**
 * @author Christopher Lu
 * This class is mainly responsible for creating the tableView in the LoadAutoringScreen tat displays a sorted list of existing authoring projects. These
 * projects can be sorted by name and by date.
 */

public abstract class AbstractLoadScreen {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	
	private ResourceBundle myResources;
	private Stage stage;
	private int screenWidth;
	private int screenHeight;
	
	public AbstractLoadScreen(String title, String files) throws IOException {
		setUpScreenResolution();
		this.myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "View");
		this.stage = new Stage();
		stage.setTitle(title);
		BorderPane pane = new BorderPane();
		Scene scene = new Scene(pane);
		scene.getStylesheets().add("style.css");
		pane.setId("background");
		populatePane(pane, files);
		stage.setScene(scene);
		stage.show();
	}
	
	protected void populatePane(BorderPane pane, String files) {
		HBox optionArea = new HBox();
		TableView<MenuTableItem> chooseProjectTable = new TableView<MenuTableItem>();
		chooseProjectTable.setPrefWidth(screenWidth*0.3);
		TableColumn<MenuTableItem, String> firstCol = new TableColumn<MenuTableItem, String>(myResources.getString("ProjectTitle"));
		TableColumn<MenuTableItem, Date> secondCol = new TableColumn<MenuTableItem, Date>(myResources.getString("LastModified"));
		String path = files.substring(1);
		File file = new File(path);
		File[] fileList = file.listFiles();
		ObservableList<MenuTableItem> data = FXCollections.observableArrayList();
		for (File f : fileList) {
			Date date = new Date(f.lastModified());
			MenuTableItem item = new MenuTableItem(f.getName(), date);
			data.add(item);
		}
		firstCol.setCellValueFactory(
				new PropertyValueFactory<MenuTableItem, String>("projectName"));
		secondCol.setCellValueFactory(
				new PropertyValueFactory<MenuTableItem, Date>("modifiedDate"));
		chooseProjectTable.setItems(data);
		chooseProjectTable.getColumns().addAll(firstCol, secondCol);
		VBox boxRight = new VBox(screenHeight*0.1);
		boxRight.setId("vbox");
		TextField showSelectedTitle = new TextField();
		showSelectedTitle.setPromptText(myResources.getString("ShowSelectedProject"));
		showSelectedTitle.setEditable(false);
		showSelectedTitle.setId("textfield");
		Button startAuthoring = new Button(myResources.getString("ConfirmAuthoringSetUp"));
		startAuthoring.setId("button");
		boxRight.getChildren().addAll(showSelectedTitle, startAuthoring);
		optionArea.getChildren().addAll(chooseProjectTable, boxRight);
		pane.setCenter(optionArea);
		chooseProjectTable.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() ){
		        	GameStateDeserializer GSD = new GameStateDeserializer();
		        	showSelectedTitle.setText(chooseProjectTable.getSelectionModel().getSelectedItem().getProjectName());
		        	String selectedGame = chooseProjectTable.getSelectionModel().getSelectedItem().getProjectName();
		    		//System.out.println("Selected Game is: " + selectedGame);
		    		startHandler(startAuthoring, selectedGame);
		        	try {
						GSD.loadGameState("src/SerializedFiles/"+chooseProjectTable.getSelectionModel().getSelectedItem().getProjectName().toString());
		        	} catch (Exception e) {
						ErrorBox.displayError(e.getMessage());
					}
		        }
		        if (event.getClickCount() == 2){
		        	start(chooseProjectTable.getSelectionModel().getSelectedItem().getProjectName());
		        }
		    }
		});
	}
	
	protected void setUpScreenResolution() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight();
	}
	
	protected void startHandler(Button start, String selectedGame){
		start.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				start(selectedGame);
			}
		});
	}
	
	protected Stage getStage(){
		return stage;
	}
	
	protected ResourceBundle getResources(){
		return myResources;
	}
	
	protected abstract void start(String selectedGame);

}
