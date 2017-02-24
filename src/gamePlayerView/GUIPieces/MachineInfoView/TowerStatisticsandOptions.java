package gamePlayerView.GUIPieces.MachineInfoView;

import authoring.model.TowerData;
import gamePlayerView.interfaces.IGUIPiece;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Guhan Muruganandam
 * 
 */
@Deprecated

public class TowerStatisticsandOptions implements IGUIPiece {
	private VBox myDisplay;
	
	public TowerStatisticsandOptions(){
		myDisplay= new VBox();
		myDisplay=makeDisplay();
	}

	private VBox makeDisplay() {
		VBox vbox=new VBox();
		TowerStatistics towerStats=new TowerStatistics();
		//TargetingButtons targetingButtons=new TargetingButtons();
		vbox.setSpacing(20);
		vbox.getChildren().add(towerStats.getNode());
		//vbox.getChildren().add(targetingButtons.getView());
		return vbox;
		
	}
	
	@Override
	public Node getNode() {
		return myDisplay;
	}
}
