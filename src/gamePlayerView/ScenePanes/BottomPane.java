package gamePlayerView.ScenePanes;

import java.util.Collection;


import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * @author Guhan Muruganandam
 * 
 */

public class BottomPane implements IViewPane {
	private HBox myNode;
	//private Pane myGamePlayer;
	
	public BottomPane(){
		myNode = new HBox();
    	setUpPane();
	}
	
	/**
	 * Creates the object that will actually be returned
	 */
	public void setUpPane() {
		//myBottomPane.setPrefWidth(myGamePlayer.getWidth()*0.9);
		//myBottomPane.setPrefHeight(myGamePlayer.getHeight()*0.14285);
		myNode.setPrefWidth(900);
		myNode.setPrefHeight(100);
		myNode.setPadding(new Insets(10, 10,10, 10));
		myNode.setSpacing(10);
		myNode.setStyle("-fx-background-color: #993384;");
	    //TowerStatisticsandOptions myTowerOptions=new TowerStatisticsandOptions();
	    //UpgradeOrSell myUpgradeandSell=new UpgradeOrSell();
	    //MachineInfo myMachineView=new MachineInfo();
	   // myBottomPane.getChildren().addAll(/*myMachineView.getView(),*/myTowerOptions.getView(),myUpgradeandSell.getView());
	}
	
	public Node getView() {
		return myNode;
	}

	@Override
	public void add(Collection<Node> collection) {
		myNode.getChildren().addAll(collection);
		
	}
	
	@Deprecated
	@Override
	public void clear() {
		myNode.getChildren().clear();
	}

	@Override
	public Node getNode() {
		return myNode;
	}

	@Override
	public void add(Node node) {
		myNode.getChildren().add(node);
	}
}
