package gamePlayerView.GUIPieces.MapView;

import java.io.File;

import engine.IObservable;

import engine.IObserver;
import engine.controller.ApplicationController;
import engine.model.components.viewable_interfaces.IViewablePhysical;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MoveableComponentView extends ImageView implements IObserver<IViewablePhysical> {

	private Pane myPane;
	private ApplicationController myAppController;
	private IObservable<IViewablePhysical> myObservable;
	private String entityID;
	private boolean hasEntityID;
	private int tileSize;
	public static final String SOURCE_PATH = "src/";
	
    public MoveableComponentView(
    		IObservable<IViewablePhysical> aObservable, ApplicationController aAppController, Pane pane, int myCellSize){
    	myAppController = aAppController;
    	myObservable = aObservable;
    	myPane = pane;
    	tileSize = myCellSize;
    	hasEntityID = false;
    }	
    
    public String getEntityID() {
    	return entityID;
    }
    
    public void setEntityID(String id) {
    	entityID = id;
    }

	@Override
	public void update(IViewablePhysical aChangedObject) {
		String imagePath = aChangedObject.getImagePath();
		if (imagePath != null) {
			if (imagePath.substring(0, 4).equals(SOURCE_PATH)) {
				imagePath = imagePath.substring(4);
			}
			imagePath = imagePath.replace('\\', File.separatorChar);
			Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(imagePath));
			this.setImage(image);
			this.setX(aChangedObject.getPosition().getX() - tileSize / 2);
			this.setY(aChangedObject.getPosition().getY() - tileSize / 2);
			this.setFitWidth(aChangedObject.getSize()*tileSize);
			this.setFitHeight(aChangedObject.getSize()*tileSize);
			//this.setOnMouseClicked(e -> myAppController.onEntitySelected(aChangedObject.getEntity()));
			this.setRotate(aChangedObject.getHeading());
			if (aChangedObject.getEntityID() != null && !hasEntityID){
				setEntityID(aChangedObject.getEntityID());
				//System.out.println("Setting entity ID");
				this.setOnMouseClicked(e -> myAppController.onEntityClicked(aChangedObject.getEntityID()));
				hasEntityID = true;
			}
		}
	}

	@Override
	public void remove(IViewablePhysical aRemovedObject) {
		myPane.getChildren().remove(this);
	}
}