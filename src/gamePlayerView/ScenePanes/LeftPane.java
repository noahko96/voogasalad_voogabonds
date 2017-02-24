package gamePlayerView.ScenePanes;

import java.util.Collection;


//import gamePlayerView.interfaces.ICashAcceptor;
import gamePlayerView.interfaces.IGUIPiece;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * @author Guhan Muruganandam
 */

public class LeftPane implements IViewPane {
	private VBox myNode;
	
	public LeftPane(){
		myNode = new VBox();
	}
	public void setUpPane() {
		myNode.setPrefWidth(100);
		myNode.setMaxHeight(700);
		myNode.setPadding(new Insets(10, 10,10, 10));
		myNode.setSpacing(10);
		myNode.setStyle("-fx-background-color: #336699;");
	}
	
	public void add(Collection<Node> collection){
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
