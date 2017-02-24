package authoring.view.tabs.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import authoring.model.AttributeFetcher;
import authoring.model.ComponentData;
import authoring.model.EntityData;
import authoring.view.tabs.ISubmittable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Niklas Sjoquist
 * 
 * A menu box that allows the user to edit an Entity.
 *
 */
public class EditEntityBox extends VBox implements ISubmittable{
    private static final int SPACING = 2;

    private static final String NAME_CHANGE_ALERT = "Name changed";
    private static final String NAME_CHANGED = "The name of this Entity has been changed.";
    private static final String NAME_CONTENT = "Would you like to confirm changes?";

    private EntityTab myTab;
    private String entityName;
    private EntityListView entityView;

    private ObservableList<String> myComponents;
    private HashMap<String, ComponentData> myComponentData = new HashMap<String, ComponentData>();

    private TextField nameField;
    private ComboBox<String> componentsBox;
    private ListView<String> myComponentsView;

    public EditEntityBox (EntityTab parent, AttributeFetcher fetcher) {
        super(SPACING);
        this.setId("vbox");
        myTab = parent;
        nameField = parent.setUpTextInputWithLabel(myTab.getResources().getString("EnterName"), myTab.getResources().getString("Entity"), this);
        setUpComponentBox(parent, fetcher);
        setUpComponentListView(fetcher, null);
        HBox buttons = setUpButtonContainer(null);
        this.getChildren().addAll(myComponentsView, buttons);
    }

    public EditEntityBox (EntityTab parent, AttributeFetcher fetcher, EntityData entityData, EntityListView entityView) {
        super(SPACING);
        this.setId("vbox");
        myTab = parent;
        retrieveEntityData(entityData);
        this.entityView = entityView;
        nameField = parent.setUpTextInputWithLabel(myTab.getResources().getString("EnterName"), entityName, this);
        nameField.setId("textfield");
        setUpComponentBox(parent, fetcher);
        setUpComponentListView(fetcher, entityData.getComponents().keySet());
        HBox buttons = setUpButtonContainer(entityView);
        this.getChildren().addAll(myComponentsView, buttons);
    }

    public void editComponent(String name, ComponentData data) {
        myComponentData.put(name, data);
    }

    private void tryAddNewEntity(EntityData entity) {
        if (myTab.addEntity(entity)) {
            ////System.out.println("Added Entity");
            for (String component : entity.getComponents().keySet()) {
                myComponentData.put(component, entity.getComponents().get(component));
            }
            ////System.out.println("Entity has "+myComponentData.size()+" Components");
            myTab.getTilePane().getChildren().remove(EditEntityBox.this); // this = reference of parent (i.e., this EditEntityBox class)
        }
    }

    private void tryEditEntity(EntityListView oldEntity, EntityData entity) {
        if (myTab.updateEntity(oldEntity, entity)) {
            myTab.getTilePane().getChildren().remove(EditEntityBox.this); // this = reference of parent (i.e., this EditEntityBox class)
        }
    }

    private EntityData createDataFromInput() {
        // define name+components
        EntityData entity = new EntityData();
        entity.setName(nameField.getCharacters().toString());
        for (String component : myComponentsView.getItems()) {
            ////System.out.println(component+" component created: "+myComponentData.get(component));
            entity.addComponent(component, myComponentData.get(component));
        }
        return entity;
    }

    private void retrieveEntityData(EntityData entityData) {
        entityName = entityData.getName();
        for (String component : entityData.getComponents().keySet()) {
            myComponentData.put(component, entityData.getComponents().get(component));
        }
    }

    /**
     * @param oldName - is equal to null when creating a new Entity
     * @return
     */
    private EventHandler<ActionEvent> handleDone(EntityListView oldEntity) {
        EventHandler<ActionEvent> finish = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event){
                EntityData entity = createDataFromInput();
                if (oldEntity == null) {
                    tryAddNewEntity(entity);
                }
                else {
                    // editing existing entity
                    if (!oldEntity.getEntityName().equals(entity.getName())) {
                        // changed name of entity
                        Optional<ButtonType> result = showNameChangeConfirmation();
                        if (result.get() == ButtonType.OK) {
                            tryEditEntity(oldEntity, entity);
                        }
                    } else {
                        // overwrite data to existing name
                        tryEditEntity(oldEntity, entity);
                    }
                }
            }
        };
        return finish;
    }

    private void setUpComponentBox(EntityTab parent, AttributeFetcher fetcher) {
        List<String> componentList = fetcher.getComponentList();
        ObservableList<String> list = FXCollections.observableArrayList();
        componentList.forEach((component) -> list.add(cleanUpComponentName(component)));
        componentsBox = parent.setUpStringComboBoxWithLabel(myTab.getResources().getString("AddComponents"), null, list, this);
        componentsBox.setOnAction(handleAddComponent(fetcher));
    }
    
    private String cleanUpComponentName(String component) {
        // remove leading '.'
        String cleanedName = component.substring(1);
        return cleanedName;
    }

    private void setUpComponentListView(AttributeFetcher fetcher, Set<String> components) {
        if (components != null) {
            myComponents = FXCollections.observableArrayList(components);
        } else {
            myComponents = FXCollections.observableArrayList();
        }
        myComponentsView = new ListView<String>(myComponents);
        myComponentsView.setOnMouseClicked(handleEditComponent(fetcher));
        myComponentsView.setOnKeyPressed(handleKeyPress());
    }

    private HBox setUpButtonContainer(EntityListView entityView) {
        HBox buttons = new HBox(SPACING);
        buttons.setPadding(new Insets(SPACING,SPACING,SPACING,SPACING));
        // finish button
        Button done = setUpSubmitButton();
        // cancel button
        Button cancel = new Button(myTab.getResources().getString("Cancel"));
        cancel.setOnAction(handleCancel());
        // set styling
        done.setId("button");
        cancel.setId("button");
        buttons.setId("hbox");
        // add nodes
        buttons.getChildren().addAll(done, cancel);
        return buttons;
    }

    private EventHandler<ActionEvent> handleAddComponent(AttributeFetcher fetcher) {
        EventHandler<ActionEvent> addToListView = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event){
                String component = componentsBox.getValue();
                if (!myComponents.contains(component)) {
                    myComponents.add(component);
                    ComponentData data = new ComponentData();
                    for (String attribute : fetcher.getComponentAttributeList(convertBackToFieldName(component))) {
                        data.addField(attribute, "");
                    }
                    myComponentData.put(component, data);
                }
            }
        };
        return addToListView;
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

    private Optional<ButtonType> showNameChangeConfirmation() {
        Alert changeNameAlert = new Alert(AlertType.CONFIRMATION);
        changeNameAlert.setTitle(NAME_CHANGE_ALERT);
        changeNameAlert.setHeaderText(NAME_CHANGED);
        changeNameAlert.setContentText(NAME_CONTENT);
        Optional<ButtonType> result = changeNameAlert.showAndWait();
        return result;
    }

    private EventHandler<ActionEvent> handleCancel() {
        EventHandler<ActionEvent> cancel = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event){
                myTab.getTilePane().getChildren().remove(EditEntityBox.this); // this = reference of parent (i.e., this EditEntityBox class)
            }
        };
        return cancel;
    }

    private EventHandler<MouseEvent> handleEditComponent(AttributeFetcher fetcher) {
        EventHandler<MouseEvent> handleEdit = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String selectedAttribute = myComponentsView.getSelectionModel().getSelectedItem();
                if (event.getClickCount() == 2 && selectedAttribute!=null) {
                    ////System.out.println("Double Click: "+selectedAttribute);
                    EditComponentBox editComponent = null;
                    if (myComponentData.containsKey(selectedAttribute)) {
                        editComponent = new EditComponentBox(EditEntityBox.this, myTab, fetcher, selectedAttribute, myComponentData.get(selectedAttribute));
                    }
                    myTab.getTilePane().getChildren().add(editComponent);
                }
            }
        };
        return handleEdit;
    }
    
    private EventHandler<KeyEvent> handleKeyPress() {
        EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>() {
            @Override
            public void handle (KeyEvent event) {
                String selected = myComponentsView.getSelectionModel().getSelectedItem();
                if (selected != null && (event.getCode() == KeyCode.D || event.getCode() == KeyCode.BACK_SPACE)) {
                    try {
                        myComponents.remove(selected);
                    } catch (Exception e) {
                        myTab.showError(e.getMessage());
                    }
                }
            }
        };
        return handler;
    }

    @Override
    public Button setUpSubmitButton() {
        Button done = new Button(myTab.getResources().getString("Finish"));
        done.setOnAction(handleDone(entityView));
        return done;
    }

}
