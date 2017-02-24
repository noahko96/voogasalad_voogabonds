package gamePlayerView.GUIPieces.MachineInfoView;

import java.util.HashMap;
import java.util.List;

import authoring.model.TowerData;
import engine.controller.ApplicationController;
import engine.model.components.viewable_interfaces.IViewableUpgrade;
import engine.model.playerinfo.IViewablePlayer;
import gamePlayerView.interfaces.IGUIPiece;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * @author Guhan Muruganandam
 * 
 */

public class UpgradeUI implements IGUIPiece {
	private HBox myDisplay;
	private ApplicationController myAppController;
	private HashMap towerMapping;
	private HBox myUpgradeSet;
	private ListView<ImageView> upgradeInfo;
	
	public UpgradeUI(IViewableUpgrade aComponent/*TowerData tower,ApplicationController applicationController*/){
		//myDisplay=makeDisplay(tower);
		//myAppController=applicationController;
		towerMapping=new HashMap<ImageView,TowerData>();
		myUpgradeSet=buildHBox();
	}

	private HBox buildHBox() {
		
		VBox upgrade=new VBox();
		Button UpgradeButton=new Button("Upgrade");
		UpgradeButton.setStyle("-fx-background-color: linear-gradient(#f0aa35, #a9ff00), radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%)");
		UpgradeButton.setPrefSize(100, 20);
		UpgradeButton.setOnAction(e->myAppController.onUpgradeButtonPressed());
		upgrade.getChildren().add(UpgradeButton);
		
		HBox hbox=new HBox();
		hbox.setSpacing(10);
	    upgradeInfo=new ListView<ImageView>(); 
	    Label heading = new Label("Upgrades");
	    heading.setFont(new Font("Cambria",18));
	    TabPane resourceTabs= new TabPane();
	    resourceTabs.getTabs().add(buildTab(upgradeInfo,"Path 1"));
	    resourceTabs.getTabs().add(buildTab(upgradeInfo,"Path 2"));
	    //resourceTabs.getTabs().add(buildTab(upgradeInfo,"Path 3"));
	    resourceTabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
	    hbox.getChildren().addAll(resourceTabs,upgrade);
	    
	    return hbox;
	}

	private Tab buildTab(Node list, String title) {
		Tab tab= new Tab();
		tab.setText(title);
		tab.setContent(list);
		return tab;
	}
	//TODO:
	//private void populateUpgradeInfo(IViewableTower aTower) {
		//	
	//}

	@Override
	public Node getNode() {
		return myUpgradeSet;
	}
	
}
