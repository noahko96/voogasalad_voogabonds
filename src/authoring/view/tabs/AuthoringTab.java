package authoring.view.tabs;

import java.io.File;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public abstract class AuthoringTab extends Tab {
    private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
    
    private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "View");
	
	public AuthoringTab(String text) {
        super(text);
    }
	
	public ResourceBundle getResources(){
		return myResources;
	}
	
	/**
     * Helper method to create a basic text label.
     * 
     * Simply packages the string input into a Text object.
     * 
     * @param label
     * @return
     */
    public Label setUpLabel(String text) {
        Label label = new Label(text);
        label.getStylesheets().add("style.css");
    	label.setId("label");
    	return label;
    }
    
    /**
     * Helper method to create a basic input field.
     * 
     * This adds a Text label and TextField area to the Menu's view, and returns a reference to the TextField input area.
     * 
     * @param root
     * @param label - string representation of desired label/prompt to indicate to the user what to enter
     * @param value - initial value of the TextField input area
     * @return
     */
    public TextField setUpTextInput(String value) {
        TextField textField = new TextField(value);
        textField.getStylesheets().add("style.css");
        textField.setId("textfield");
        return textField;
    }
    
    /**
     * Helper method to create a ComboBox. 
     * 
     * This method adds a ComboBox with a given label to the Menu's view and returns the ComboBox, if the user wants a reference
     * 
     * @param root
     * @param label
     * @param choices - the list of choices of the ComboBox
     * @param value - the default value of the ComboBox
     * @return ComboBox
     */
    public ComboBox<String> setUpStringComboBox(ObservableList<String> choices, String value) {
        ComboBox<String> cb = new ComboBox<String>(choices);
        cb.getStylesheets().add("style.css");
        cb.setId("combobox");
        if (value != null)
        	cb.setValue(value);
        return cb;
    }

    /**
     * Helper method to create a FileChooser button.
     * 
     * This method takes in a TextField to edit, and returns a FileChooser.
     * 
     * @param field - text field to edit with filepath
     * @param extensionName - name of expected file extension (e.g., WAV, PNG, etc.)
     * @param extension - format of expected file extension (e.g., '*.wav', '*.png', etc.)
     * @return
     */
    public Button setUpBrowseButton(TextField field, String extensionName, String extension) {
        Button browseButton = new Button(myResources.getString("Browse"));
        browseButton.getStylesheets().add("style.css");
        browseButton.setId("button");
        browseButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event){
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(extensionName, extension));
                File file = fileChooser.showOpenDialog(new Stage());
                if (file != null){
                	int placeHolder = file.getPath().indexOf("resources");
                	String simplePath = file.getPath().substring(placeHolder, file.getPath().length());
                    field.setText(simplePath);
                }
            }
        });
        return browseButton;
    }
    
    /**
     * Helper method to create a CheckBox dropdown menu.
     * 
     * This returns a reference to the MenuButton.
     * 
     * @param root
     * @param value - default value of check box
     * @param options - possible values of the check box
     * @return
     */
    public MenuButton setUpMenuButton(String value, ObservableList<String> options) {
        MenuButton menuBtn = new MenuButton(value);
        for (String terrain : options){ //changed from myTab.getTerrains() -> options
                CheckBox checkBox = new CheckBox(terrain);
                //checkBox.setSelected(options.contains(terrain)); Not sure why this was included
                CustomMenuItem custom = new CustomMenuItem(checkBox);
                menuBtn.getItems().add(custom);
        }
        return menuBtn;
    }
    
    public TextField setUpTextInputWithLabel(String enter, String defaultText, VBox root){
    	Label label = setUpLabel(enter);
    	TextField field = setUpTextInput(defaultText);
    	label.setLabelFor(field);
    	root.getChildren().addAll(label, field);
    	return field;
    }
    
    public ComboBox<String> setUpStringComboBoxWithLabel(String select, String defaultText, 
    		ObservableList<String> values, VBox root){
    	Label label = setUpLabel(select);
    	ComboBox<String> box = setUpStringComboBox(values, defaultText);
    	label.setLabelFor(box);
    	root.getChildren().addAll(label, box);
    	return box;
    }
    
    public void showError (String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(myResources.getString("ErrorTitle"));
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    
    
    
	
}
