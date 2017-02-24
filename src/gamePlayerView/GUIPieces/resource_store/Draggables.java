package gamePlayerView.GUIPieces.resource_store;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import authoring.model.EntityData;
import engine.IObservable;
import engine.IObserver;
import engine.model.playerinfo.IViewablePlayer;
import gamePlayerView.interfaces.IGUIPiece;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class Draggables extends ListView<ImageView> implements IGUIPiece, IObservable<Draggables>{
	public static final double DEFAULT_UNAFFORDABLE_OPACITY = 0.3;
	public static final String SOURCE_PATH = "src/";
	private Map<ImageView,EntityData> myImageToDataMap;
	private ImageView mySelectedImage;
	private List<IObserver<Draggables>> myObservers;
	
	public Draggables(Map<ImageView,EntityData> map) {
		myObservers = new ArrayList<IObserver<Draggables>>();
		myImageToDataMap = map;
	}
	
	/**
	 * Updates the draggable list view of entities.
	 * @param aPlayer
	 */
	public void update(IViewablePlayer aPlayer) {
		setItems(getUpdatedTowerImages(aPlayer));
		setDragFunctionality();
	}

	/**
	 * Updates the towers available to the player and affordable by the player.
	 * @param aPlayer
	 */
	private ObservableList<ImageView> getUpdatedTowerImages(IViewablePlayer aPlayer) {
		List<EntityData> availableTowers = aPlayer.getAvailableTowers();
		List<EntityData> affordableTowers = aPlayer.getAffordableTowers();
		ObservableList<ImageView> towerImageViews = FXCollections.observableArrayList();
//		myImageToDataMap.clear();
		for (EntityData t: availableTowers) {
			String imagePath = t.getComponents()
					.get("PhysicalComponent")
					.getFields()
					.get("myImagePath");
			
			if (imagePath.substring(0, 4).equals(SOURCE_PATH)) {
				imagePath = imagePath.substring(4);
			}
			imagePath = imagePath.replace('\\', File.separatorChar);

			ImageView towerImageView = new ImageView();
			towerImageView.setImage(new Image(
							this
							.getClass()
							.getClassLoader()
							.getResourceAsStream(imagePath)
							));
			if (!affordableTowers.contains(t)) {
				towerImageView.setOpacity(DEFAULT_UNAFFORDABLE_OPACITY);
			}
			myImageToDataMap.put(towerImageView, t);
			towerImageView.setFitHeight(50);
			towerImageView.setFitWidth(50);
			towerImageViews.add(towerImageView);
			this.setFixedCellSize(50);
		
		}
		
		updateClickEvent();
		
		return towerImageViews;
		
	}
	
	/**
	 * Updates the selected image that was clicked on.
	 * @param towerSet
	 * @param towerDataDisplay
	 */
	private void updateClickEvent () {
		setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event) {
                setSelectedImage(getSelectionModel().getSelectedItem());
            }
        });
	}
	
	private void setSelectedImage(ImageView selectedItem) {
		mySelectedImage = selectedItem;
		notifyObservers();
	}  
	
	public ImageView getSelectedImage() {
		return mySelectedImage;
	}
	
	/**
	 * Gives drag functionality for images in the resource store.
	 */
	private void setDragFunctionality() {
		setOnDragDetected(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event) {
                Dragboard db = startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                ImageView towerToBeDragged = getSelectionModel().getSelectedItem();
                if(towerToBeDragged.getOpacity()>0.5){
                	content.putImage(towerToBeDragged.getImage());
                	content.putString(myImageToDataMap.get(towerToBeDragged).getName());
                	db.setContent(content);
                	event.consume();
                }
            }  
        });
	}

	@Override
	public Node getNode() {
		return this;
	}

	@Override
	public void attach(IObserver<Draggables> aObserver) {
		myObservers.add(aObserver);
	}

	@Override
	public void detach(IObserver<Draggables> aObserver) {
		myObservers.remove(aObserver);
	}

	@Override
	public void notifyObservers() {
		myObservers.forEach(o -> o.update(this));
	}
}
