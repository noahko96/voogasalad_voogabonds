package authoring.view.tabs.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import authoring.model.AttributeFetcher;
import authoring.model.ComponentData;
import authoring.view.tabs.ISubmittable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utility.file_io.FileRetriever;
import utility.ListStringManipulator;

/**
 * @author Niklas Sjoquist
 *
 * A menu box that allows the user to edit a Component of an Entity.
 *
 */
public class EditComponentBox extends VBox implements ISubmittable {
    private final String PACKAGE = "engine/model/strategies/";
    private final String STRATEGY = "strategy";
    private final String DAMAGE = "damage";
    private final String MOVEMENT = "movement";
    private final String SPAWNING = "spawning";
    private final String TARGET = "target";
    private final String WIN = "win";
    private final String LOSE = "lose";
    private final String EXTENSION = ".class";
    private final String TERRAINS = "Terrains";
    private final String CALC = "calc";
    public static final String IMAGE_PATH = "myImagePath";
    public static final String DONE = "Done";
    public static final String CANCEL = "Cancel";
    private static final int SPACING = 2;

    private List<Label> myLabels;
    private List<TextField> myFields;
    private List<MenuButton> myComboCheckBoxes;
    private EditEntityBox parent;
    private EntityTab grandparent;
    private String myName;

    /**
     * Create new/Edit existing component.
     * @param parent - the EditEntityBox that spawned this EditComponentBox
     * @param grandparent - the EntityTab that contains this EditComponentBox
     * @param fetcher - used to retrieve attributes of this component
     * @param componentName - name of this component
     * @param componentData - values of attributes
     */
    public EditComponentBox(EditEntityBox parent, EntityTab grandparent, AttributeFetcher fetcher, String componentName, ComponentData componentData) {
        super(SPACING);
        String uglyComponentName = this.convertBackToFieldName(componentName);
        List<String> attributes = fetcher.getComponentAttributeList(uglyComponentName);
        List<String> attributeTypes = fetcher.getComponentAttributeTypeList(uglyComponentName);
        Map<String,String> retrievedData = componentData.getFields();
        init(parent, grandparent, componentName, retrievedData.size());

        // Set up input fields
        for (int i = 0; i < attributes.size(); i++) {
            String attributeType = attributeTypes.get(i);
            String attributeName = attributes.get(i);
            // Set up/Add Label:
            Label lbl = new Label(attributeName);
            lbl.setId("label");
            myLabels.add(lbl);
            this.getChildren().add(lbl);
            // Set up/Add user input:
            if (attributeType.toLowerCase().equals("list")) {
                // set up multi-combo box of choices
                setUpTerrainInput(grandparent, componentData, attributeName, lbl);
            } else if (attributeType.toLowerCase().equals("boolean")) {
                setUpBooleanInput(retrievedData, attributeName, lbl);
            } else if (attributeName.toLowerCase().contains(STRATEGY) || attributeName.toLowerCase().contains(CALC)) {
                setUpStrategyInput(retrievedData, attributeName, lbl);
            } else {
                setUpTextInput(grandparent, retrievedData, attributeType, attributeName, lbl);
            }
        }
        HBox btns = getBottomButtons();
        this.getChildren().add(btns);
    }

    private void setUpTextInput (EntityTab grandparent,
                                 Map<String, String> retrievedData,
                                 String attributeType,
                                 String attributeName,
                                 Label lbl) {
        TextField field = new TextField(retrievedData.get(attributeName));
        setUpLabeledField(lbl, field);
        if (attributeType.toLowerCase().equals("int")) {
            forceNumericInput(field);
        } else if (attributeType.toLowerCase().equals("double")) {
            forceDoubleInput(field);
        }
        if (lbl.getText().equals(IMAGE_PATH)) {
            Button browse = grandparent.setUpBrowseButton(field, "PNG", "*.png");
            this.getChildren().add(browse);
        }
    }

    private void setUpStrategyInput (Map<String, String> retrievedData,
                                     String attributeName,
                                     Label lbl) {
        // Make ComboBox and retrieve Strategies by reflection
        List<String> fileList = new ArrayList<String>();
        FileRetriever fr;
        if (attributeName.toLowerCase().contains(DAMAGE)) {
            fr = new FileRetriever(PACKAGE+DAMAGE);
        } else if (attributeName.toLowerCase().contains(MOVEMENT)) {
            fr = new FileRetriever(PACKAGE+MOVEMENT);
        } else if (attributeName.toLowerCase().contains(SPAWNING)) {
            fr = new FileRetriever(PACKAGE+SPAWNING);
        } else if (attributeName.toLowerCase().contains(TARGET)) {
            fr = new FileRetriever(PACKAGE+TARGET);
        } else if (attributeName.toLowerCase().contains(WIN) || attributeName.toLowerCase().contains(LOSE)) {
            fr = new FileRetriever(PACKAGE+WIN+LOSE);
        } else {
            fr = new FileRetriever(PACKAGE+MOVEMENT);
        }
        fileList = fr.getFileNames("/");
        List<String> strategies = new ArrayList<String>(fileList.size());
        fileList.forEach((path) -> strategies.add(stripStrategyClassPath(path)));
        ComboBox<String> strategyChoice = new ComboBox<String>(FXCollections.observableArrayList(strategies));
        strategyChoice.setValue(retrievedData.get(attributeName));
        lbl.setLabelFor(strategyChoice);
        strategyChoice.setId("combobox");
        this.getChildren().add(strategyChoice);
    }

    private void setUpBooleanInput (Map<String, String> retrievedData,
                                    String attributeName,
                                    Label lbl) {
        ObservableList<String> choices = FXCollections.observableArrayList("True", "False");
        ComboBox<String> boolCombo = new ComboBox<>(choices);
        lbl.setLabelFor(boolCombo);
        boolCombo.setValue(retrievedData.get(attributeName));
        boolCombo.setId("combobox");
        this.getChildren().add(boolCombo);
    }

    private void setUpTerrainInput (EntityTab grandparent,
                                    ComponentData componentData,
                                    String attributeName,
                                    Label lbl) {
        MenuButton checkbox = null;
        if (attributeName.toLowerCase().contains(TERRAINS.toLowerCase())) {
            checkbox = grandparent.setUpMenuButton(TERRAINS, FXCollections.observableArrayList(grandparent.getTerrains()));
            String terrainData = componentData.getFields().get(attributeName);
            List<String> terrains;
            try {
                terrains = ListStringManipulator.stringToList(terrainData);
            }
            catch (Exception e) {
                terrains = null;
            }
            // Set retrieved valid terrains
            setRetrievedTerrains(terrains, checkbox);
        }
        if (checkbox != null) {
            lbl.setLabelFor(checkbox);
            checkbox.setId("combobox");
            myComboCheckBoxes.add(checkbox);
            this.getChildren().add(checkbox);
        }
    }
    
    private void forceNumericInput(TextField field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed (ObservableValue<? extends String> observable,
                                 String oldValue,
                                 String newValue) {
                if (!newValue.matches("\\d*")) {
                    field.setText(newValue.replaceAll("[^\\d]",""));
                }
            }
        });
    }
    
    private void forceDoubleInput(TextField field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed (ObservableValue<? extends String> observable,
                                 String oldValue,
                                 String newValue) {
                if (!newValue.matches("\\d+\\.?\\d+")) {
                    field.setText(newValue.replaceAll("[^-?\\d+(\\.\\d+)?]", ""));
                }
            }
        });
    }
    
    private void setRetrievedTerrains (List<String> terrains, MenuButton checkbox) {
        if (terrains != null && terrains.size() > 0) {
            for (MenuItem item : checkbox.getItems()) {
                CheckBox check = (CheckBox) ((CustomMenuItem) item).getContent();
                if (terrains.contains(check.getText())) {
                    check.setSelected(true);
                }
            }
        }
    }

    private String stripStrategyClassPath(String strategyClassPath) {
        int lastSlash = strategyClassPath.lastIndexOf("/");
        return strategyClassPath.substring(lastSlash+1,strategyClassPath.length()-EXTENSION.length());
    }

    private void init(EditEntityBox parent, EntityTab grandparent, String componentName, int numAttributes) {
        this.setId("vbox");
        this.parent = parent;
        this.grandparent = grandparent;
        myName = componentName;
        myLabels = new ArrayList<Label>(numAttributes);
        myFields = new ArrayList<TextField>(numAttributes);
        myComboCheckBoxes = new ArrayList<MenuButton>();
    }
    
    /**
     * Converts name back into form stored in AttributeFetcher.
     * 
     * Converts from the form: 'Physical Component'
     * To the form: '.PhysicalComponent'
     * 
     * @param cleanedName
     * @return
     */
    private String convertBackToFieldName(String cleanedName) {
        return "."+smoosh(cleanedName);
    }
    
    private String smoosh(String spaced) {
        return spaced.replaceAll("\\s", "");
    }

    private void setUpLabeledField(Label lbl, TextField field) {
        lbl.setLabelFor(field);
        field.setId("textfield");
        myFields.add(field);
        this.getChildren().add(field);
    }

    private HBox getBottomButtons() {
        HBox btns = new HBox(SPACING);
        btns.setPadding(new Insets(SPACING,SPACING,SPACING,SPACING));
        btns.setId("hbox");
        Button done = setUpSubmitButton();
        Button cancel = new Button(CANCEL);
        cancel.setOnAction(handleCancel());
        cancel.setId("button");
        btns.getChildren().addAll(done,cancel);
        return btns;
    }

    private EventHandler<ActionEvent> handleDone() {
        EventHandler<ActionEvent> finish = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event){
                String name = getName();
                ComponentData entity = createDataFromInput();
                ////System.out.println("Put Component ("+name+") in parent map");
                parent.editComponent(name, entity);
                grandparent.getTilePane().getChildren().remove(EditComponentBox.this); // this = reference of parent (i.e., this EditEntityBox class)
            }
        };
        return finish;
    }

    private EventHandler<ActionEvent> handleCancel() {
        EventHandler<ActionEvent> cancel = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event){
                grandparent.getTilePane().getChildren().remove(EditComponentBox.this); // this = reference of parent (i.e., this EditEntityBox class)
            }
        };
        return cancel;
    }

    private ComponentData createDataFromInput () {
        ComponentData component = new ComponentData();
        component.setComponentName(myName);
        for (int i = 0; i < myLabels.size(); i++) {
            Label lbl = myLabels.get(i);
            String attributeName = lbl.getText();
            Node input = lbl.getLabelFor();
            if (input.getClass()==TextField.class) {
                String value = ((TextField) input).getText();
                component.addField(attributeName, value);
            } else if (input.getClass()==MenuButton.class) {
                MenuButton checkbox = (MenuButton) input;
                List<String> selectedItems = new ArrayList<String>();
                for (MenuItem item : checkbox.getItems()) {
                    CheckBox check = (CheckBox) ((CustomMenuItem) item).getContent();
                    if (check.isSelected()) {
                        selectedItems.add(check.getText());
                    }
                }
                String list = ListStringManipulator.listToString(selectedItems);
                component.addField(attributeName, list);
            } else if (input.getClass()==ComboBox.class) {
                ComboBox<String> combo = (ComboBox<String>) input;
                component.addField(attributeName, combo.getValue());
            }
        }
        return component;
    }

    private String getName() {
        return myName;
    }

    @Override
    public Button setUpSubmitButton() {
        Button done = new Button(DONE);
        done.setOnAction(handleDone());
        done.setId("button");
        return done;
    }

}
