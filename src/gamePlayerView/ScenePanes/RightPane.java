package gamePlayerView.ScenePanes;

import java.util.Collection;

import gamePlayerView.GUIPieces.TowerColumn;
import gamePlayerView.GUIPieces.InfoBoxes.CashBox;
import gamePlayerView.GUIPieces.InfoBoxes.LivesBox;
import gamePlayerView.interfaces.IGUIPiece;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * @author Guhan Muruganandam
 */

public class RightPane implements IViewPane {
	private VBox myNode;
	
	public RightPane(){
		myNode = new VBox();
		setUpPane();
	}

	@Override
	public void setUpPane() {
		myNode.setPrefWidth(200);
		myNode.setPrefHeight(600);
		myNode.setPadding(new Insets(10));
		myNode.setSpacing(8);
	    //TODO:Export CSS to other part
		myNode.setStyle("-fx-background-color: #778899;");
	}

	@Override
	public void add(Collection<Node> collection) {
		myNode.getChildren().addAll(collection);
	}

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
