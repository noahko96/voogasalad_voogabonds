package gamePlayerView.ScenePanes;

import java.util.Collection;

import gamePlayerView.interfaces.IGUIPiece;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class TopPane implements IViewPane{
	private HBox myNode;
	
	public TopPane () {
		myNode = new HBox();
	}
	
	@Override
	public void setUpPane() {
		myNode.setPrefWidth(900);
		myNode.setPrefHeight(50);
		myNode.setPadding(new Insets(10, 10,10, 10));
		myNode.setSpacing(10);
		myNode.setStyle("-fx-background-color: #914484;");
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
