package authoring.view.tabs.entities;

import authoring.controller.Container;
import authoring.controller.EntityDataContainer;
import authoring.controller.MapDataContainer;
import authoring.model.AttributeFetcher;
import authoring.model.EntityData;
import authoring.view.tabs.ListTab;
import engine.IObserver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityTab extends ListTab<EntityListView> implements IObserver<Container>{
    public static final String TITLE = "Entities";
    public static final String ADD_ENTITY = "Add Entity";
    private static final int COLS = 3;

    private EntityDataContainer myEntities;
    private ArrayList<String> validTerrains = new ArrayList<String>();

    private AttributeFetcher ecFetcher = new AttributeFetcher();

    public EntityTab(EntityDataContainer entities) throws ClassNotFoundException {
        super(TITLE, COLS);
        myEntities = entities;
        ecFetcher.fetch();
        this.setCellFactory(getCustomCellFactory());
        this.setKeyboardAction(handleKeyPress());
        //setDragFunctionality(this.getListView());
    }

    public boolean addEntity(EntityData entity){
        try{
            myEntities.createEntityData(entity);
            return true;
        }catch(Exception e){
            this.showError(e.getMessage());
            return false;
        }
    }

    public boolean updateEntity(EntityListView oldEntity, EntityData entity){
        try{
            myEntities.updateEntityData(oldEntity.getEntityName(), entity);
            return true;
        }catch(Exception e){
            this.showError(e.getMessage());
            return false;
        }
    }
    
    public boolean deleteEntity(EntityListView entity) {
        try {
            myEntities.removeEntityData(entity.getEntityName());
            return true;
        }
        catch (Exception e) {
            this.showError(e.getMessage());
            return false;
        }
    }
    
    public List<String> getTerrains() {
        return validTerrains;
    }

    public EditEntityBox getNewEntityMenu() {
        return new EditEntityBox(this, ecFetcher);
    }

    public EditEntityBox getEditEntityMenu(EntityData entityData, EntityListView entityView) {
        return new EditEntityBox(this, ecFetcher, entityData, entityView);
    }
    
    /**
     * Customizes the ListView of Entities to include an image+name
     * 
     * @return
     */
    private Callback<ListView<EntityListView>, ListCell<EntityListView>> getCustomCellFactory() {
        return new Callback<ListView<EntityListView>, ListCell<EntityListView>>() {
            @Override
            public ListCell<EntityListView> call (ListView<EntityListView> param) {
                return new EntityCell(EntityTab.this);
            }
        };
    }
    
    private EventHandler<KeyEvent> handleKeyPress() {
        EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>() {
            @Override
            public void handle (KeyEvent event) {
                EntityListView selected = EntityTab.this.getListView().getSelectionModel().getSelectedItem();
                if (selected != null && (event.getCode() == KeyCode.D || event.getCode() == KeyCode.BACK_SPACE)) {
                    try {
                        myEntities.removeEntityData(selected.getEntityName());
                        EntityTab.this.getList().remove(selected);
                    } catch (Exception e) {
                        EntityTab.this.showError(e.getMessage());
                    }
                }
            }
        };
        return handler;
    }
    
//    private void setDragFunctionality(ListView<EntityListView> entities) {
//        entities.setOnDragDetected(new EventHandler<MouseEvent>(){
//            @Override
//            public void handle (MouseEvent event) {
//                Dragboard db = entities.startDragAndDrop(TransferMode.COPY);
//                Clipboard
//            }
//        });
//    }

    @Override
    protected EventHandler<ActionEvent> handleAddNewObject () {
        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event){
                EditEntityBox newEntityMenu = getNewEntityMenu();
                getTilePane().getChildren().add(newEntityMenu);
            }
        };
        return handler;
    }

    @Override
    protected void edit(EntityListView name) {
        EntityListView clickedEntity = EntityTab.this.getListView().getSelectionModel().getSelectedItem();
        EntityData clickedEntityData = myEntities.getEntityData(clickedEntity.getEntityName());
        EditEntityBox editEntityMenu = EntityTab.this.getEditEntityMenu(clickedEntityData, clickedEntity);
        getTilePane().getChildren().add(editEntityMenu);
    }

    /**
     * IObserver functions
     */
    public void update(Container c){
        if (c instanceof MapDataContainer){
            validTerrains.clear();
            for (String terrain: ((MapDataContainer) c).getValidTerrainMap().keySet()){
                validTerrains.add(terrain);
            }
        }else if(c instanceof EntityDataContainer){
        	getList().clear();
        	List<EntityData> entityList = new ArrayList<EntityData>(((EntityDataContainer) c).getEntityDataMap().values());
        	entityList = entityList.stream().sorted((e1,e2) -> e1.getName().compareTo(e2.getName())).collect(Collectors.toList());
        	for (EntityData entity: entityList){
        		getList().add(new EntityListView(entity));
        	}
        }
    }

	@Override
	public void remove(Container aRemovedObject) {
		//Do nothing.
	}

}
