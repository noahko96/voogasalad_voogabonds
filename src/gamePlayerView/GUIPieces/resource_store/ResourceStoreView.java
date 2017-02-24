package gamePlayerView.GUIPieces.resource_store;

import java.util.HashMap;
import java.util.Map;
import authoring.model.EntityData;
import engine.IObservable;
import engine.IObserver;
import engine.model.playerinfo.IViewablePlayer;
import gamePlayerView.interfaces.IGUIPiece;
import gamePlayerView.interfaces.IResourceAcceptor;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ResourceStoreView extends VBox implements IGUIPiece, IResourceAcceptor, IObserver<IViewablePlayer>{
	
	Draggables myDraggables;
	ResourceInfo myResourceInfo;
	private Map<ImageView,EntityData> myImageToDataMap;
	
	public ResourceStoreView () {
		myImageToDataMap = new HashMap<ImageView,EntityData>();
		myDraggables = new Draggables(myImageToDataMap);
		myResourceInfo = new ResourceInfo(myImageToDataMap);
		myDraggables.attach(myResourceInfo);
		constructNode();
	}

	private void constructNode() {
		TabPane resourceTab = new TabPane();
		resourceTab.getTabs().add(buildTab(myDraggables, "Towers"));
		getChildren().add(resourceTab);
		getChildren().add(myResourceInfo);
	}
	
	private Tab buildTab(Node list, String title) {
		Tab tab= new Tab();
		tab.setText(title);
		tab.setContent(list);
		return tab;
	}

	@Override
	public Node getNode() {
		return this;
	}

	/**
	 * Updates player info given the changed viewable player.
	 */
	@Override
	public void update(IViewablePlayer aChangedObject) {
		myDraggables.update(aChangedObject);
	}

	@Override
	public void remove(IViewablePlayer aRemovedObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acceptResources(IObservable<IViewablePlayer> aPlayer) {
		aPlayer.attach(this);
	}

}
