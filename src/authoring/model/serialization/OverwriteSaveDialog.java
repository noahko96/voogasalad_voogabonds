package authoring.model.serialization;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Christopher Lu
 * This class creates the dialog that allows the user to choose to overwrite a file with the same name.
 */

public class OverwriteSaveDialog {
	
	private Scene scene;
	private Stage stage;
	private BorderPane root;
	private ResourceBundle myResources;
	private String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private int screenWidth;
	private int screenHeight;
	private File file;
	
	public OverwriteSaveDialog(File f) {
		setUpScreenResolution();
		this.file = f;
		this.myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "View");
		root = new BorderPane();
		this.scene = new Scene(root);
		this.stage = new Stage();
		stage.setScene(scene);
		stage.show();
		populatePane();
	}
	
	private void populatePane() {
		VBox dialogueContainer = new VBox(screenHeight*0.1);
		HBox buttonContainer = new HBox(screenWidth*0.1);
		displayText(dialogueContainer);
		displayButtons(dialogueContainer, buttonContainer);
		dialogueContainer.getChildren().add(buttonContainer);
		root.setCenter(dialogueContainer);
	}
	
	private void displayText(VBox dialogueContainer) {
		Text dialogueMessage = new Text(myResources.getString("OverwriteSave"));
		dialogueContainer.getChildren().add(dialogueMessage);
	}
	
	private void displayButtons(VBox dialoguecontainer, HBox buttonContainer) {
		Button yes = new Button(myResources.getString("YES"));
		Button no = new Button(myResources.getString("NO"));
		yesHandler(yes);
		noHandler(no);
		buttonContainer.getChildren().addAll(yes, no);
	}

	private void yesHandler(Button yes) {
		yes.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				file.mkdir();
			}
		});
	}
	
	private void noHandler(Button no) {
		no.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				stage.close();
			}
		});
	}
	
	private void setUpScreenResolution() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight();
	}
	
}
