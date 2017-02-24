
package gamePlayerView.GUIPieces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import authoring.model.EntityData;
import engine.IObservable;
import engine.IObserver;
import engine.model.playerinfo.IViewablePlayer;
import gamePlayerView.interfaces.IGUIPiece;
import gamePlayerView.interfaces.IResourceAcceptor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;


/**
 * @author Guhan Muruganandam & Grayson Wise
 * @author owenchung(refactored)
 */
public class TowerColumn extends VBox implements IResourceAcceptor, IObserver<IViewablePlayer>, IGUIPiece {
	
	private ImageView myTowerToBeDragged;
	private Map<ImageView,EntityData> myTowerSettings;
	private TextArea myTowerDataDisplay;
	private ListView<ImageView> myTowerInfo;

	
	
	public TowerColumn() {
		myTowerSettings= new HashMap<ImageView,EntityData>();
		buildTowerColumn();
	}
	
	public EntityData getTowerData(String aTowerName)
	{
		for (EntityData data: myTowerSettings.values()) {
			if (data.getName().equals(aTowerName)) {
				return data;
			}
		}
		return null;
	}
	
	/**
	 * Builds object that will actually be returned
	 */
	private void buildTowerColumn() {
	    myTowerDataDisplay= new TextArea();
	    myTowerInfo=new ListView<ImageView>(); 
	    //populatetowerInfo(availableTowers,towerDataDisplay);
	    TabPane resourceTabs= new TabPane();
	    resourceTabs.getTabs().add(buildTab(myTowerInfo, "Towers"));
	    resourceTabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
	    
	    getChildren().addAll(resourceTabs,myTowerDataDisplay);
	}
	/*
	 * Creates ListView for the selected towerData
	 */
	private void populateTowerInfo(IViewablePlayer aPlayer) {
		List<EntityData> availableTowers = aPlayer.getAvailableTowers();
		List<EntityData> affordableTowers = aPlayer.getAffordableTowers();
		
		ObservableList<ImageView> items =FXCollections.observableArrayList();
		myTowerSettings.clear();
		for(EntityData t : availableTowers){
			ImageView towerPicture = new ImageView();
			//TODO: This is super hack-y.
			String imagePath = t.getComponents().get("PhysicalComponent").getFields().get("myImagePath");
			//TODO: make this cleaner--hard coded now
			if (imagePath.substring(0, 4).equals("src/")) {
				imagePath = imagePath.substring(4);
			}
			Image towerImage = new Image(this.getClass().getClassLoader().getResourceAsStream(imagePath)); //THIS IS IFFY. COME BACK TO THIS
			towerPicture.setImage(towerImage);
			if(!affordableTowers.contains(t)){
				towerPicture.setOpacity(0.3);
			}
			// TODO: Hardcoded, change asap
			towerPicture.setPreserveRatio(true);
			towerPicture.setFitHeight(50);
			towerPicture.setFitWidth(50);
			myTowerSettings.put(towerPicture,t);
			items.add(towerPicture);
		}
		
		myTowerInfo.setFixedCellSize(50);
        myTowerInfo.setItems(items);
        setDragFunctionality(myTowerInfo);
        setPopulateFunctionality(myTowerInfo,myTowerDataDisplay);
        getChildren().clear();
        
	    TabPane resourceTabs= new TabPane();
	    resourceTabs.getTabs().add(buildTab(myTowerInfo, "Towers"));
	    resourceTabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        
	    getChildren().addAll(resourceTabs, myTowerDataDisplay);
	}
	/*
	 * Sets Mouse Click event for List View
	 */
	private void setPopulateFunctionality(ListView<ImageView> towerSet, TextArea towerDataDisplay) {
		towerSet.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event) {
                ImageView towerChosen = towerSet.getSelectionModel().getSelectedItem();
                EntityData tower=myTowerSettings.get(towerChosen);
                PopulateTowerDataDisplay(tower,towerDataDisplay);
            }  
        });
	}
	/*
	 * Allows Text Area to display attributes based on the Tower selected in the ListView
	 */
	private void PopulateTowerDataDisplay(EntityData tower,TextArea towerDataDisplay) {
		towerDataDisplay.clear();
		towerDataDisplay.setEditable(false);
		String name= new String(tower.getName());
		String cost=new String(Integer.toString(tower.getBuyPrice()));
		
		towerDataDisplay.setText(String.format("Tower Name: %s\nCost: %s\n", name, cost));
	}
	
	private void setDragFunctionality(ListView<ImageView> towerSet) {
		towerSet.setOnDragDetected(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event) {
                Dragboard db = towerSet.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                myTowerToBeDragged = towerSet.getSelectionModel().getSelectedItem();
                if(myTowerToBeDragged.getOpacity()>0.5){
                	content.putImage(myTowerToBeDragged.getImage());
                	content.putString(myTowerSettings.get(myTowerToBeDragged).getName());
                	db.setContent(content);
                	event.consume();
                }
            }  
        });
	}

	private Tab buildTab(Node list, String title) {
		Tab tab= new Tab();
		tab.setText(title);
		tab.setContent(list);
		return tab;
	}

	public Node getView() {
		return this;
	}
	
	public void acceptResources(IObservable<IViewablePlayer> aPlayer) {
		aPlayer.attach(this);
//		update(aPlayer);
	}

	@Override
	public void update(IViewablePlayer aChangedObject) {
		populateTowerInfo(aChangedObject);
	}

	@Override
	public void remove(IViewablePlayer aRemovedObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Node getNode() {
		return this;
	}

	
	/*
	 * 
	 * @Override // for the IResourceStoreAcceptor interface 
	 * void acceptResourceStore(IViewableResourceStore aResourceStore)
	 * {
	 * 		aResourceStore.attach(this);
	 * }
	 * 
	 * @Override // for the IObserver interface
	 * void update(...)
	 * {
	 * 		refresh the list view
	 * }
	 * 
	 * 
	 * 
	 */
	
	//MIGHT NEED LATER
	/*public IResourceAcceptor getResources() {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	
	/*private Button makeTowerImage(String string, ImageView imageView) {
	Button b =new Button(string,imageView);
	b.setOnMouseEntered(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent me) {
        	Tooltip t= new Tooltip("Towers");
	        Tooltip.install(b, t);
        }    
    });
	
	b.setStyle("-fx-effect: dropshadow( one-pass-box , rgba(0,0,0,0.9) , 1, 0.0 , 0 , 1 );");
	b.setOnMouseClicked(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent me) {
        	//TODO:
        }    
    });
	
	return b;
}*/
}