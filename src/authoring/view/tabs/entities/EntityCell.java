package authoring.view.tabs.entities;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * @author Niklas Sjoquist
 * 
 * @resource answer by Rainer Schwarze on http://stackoverflow.com/questions/15661500/javafx-listview-item-with-an-image-button
 *
 * 
 */
public class EntityCell extends ListCell<EntityListView> {
    private static final String HAS_NO_IMAGE = "resources/noimage.png";
    
    private HBox cellBox = new HBox();
    private EntityListView entityView;
    
    public EntityCell(EntityTab parent) {
        super();
    }
    
    @Override
    public void updateItem(EntityListView entityView, boolean empty) {
        super.updateItem(entityView, empty);
        if (empty || entityView == null) {
            this.setText(null);
            this.setGraphic(null);
        } else {
            this.setText(entityView.getEntityName());
            if (entityView.getEntityImageView() == null) {
                Image noImgImg = new Image(this.getClass().getClassLoader().getResourceAsStream(HAS_NO_IMAGE));
                this.setGraphic(new ImageView(noImgImg));
            } else {
                this.setGraphic(entityView.getEntityImageView());
            }
        }
    }
    
}
