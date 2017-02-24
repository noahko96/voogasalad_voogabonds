package gamePlayerView.GUIPieces.MachineInfoView;

import engine.controller.ApplicationController;
import gamePlayerView.interfaces.IGUIPiece;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * @author Guhan Muruganandam
 * 
 */

public class TargetingButtons implements IGUIPiece {
	private HBox targetOptions;
	private ApplicationController myAppController;
	public TargetingButtons(ApplicationController appController){
		targetOptions=makeTargetingButtons();
		myAppController=appController;
	}
	
	private HBox makeTargetingButtons() {
		Button b1= makeButton("First");
		b1.setOnAction(e->myAppController.onFirstPressed());
		Button b2= makeButton("Last");
		b2.setOnAction(e->myAppController.onLastPressed());
		Button b3= makeButton("Strongest");
		//b3.setOnAction(e->myAppController.onFirstPressed());
		Button b4= makeButton("Weakest");
		//b4.setOnAction(e->myAppController.onFirstPressed());
		HBox hbox=new HBox();
		hbox.setSpacing(10);
		hbox.getChildren().addAll(b1,b2,b3,b4);
		return hbox;
	}
	
	private Button makeButton(String string) {
		Button b= new Button(string);
		b.setPrefSize(100, 20);
		//b.setId(value);
		b.setStyle("-fx-background-color: linear-gradient(#f0aa35, #a9ff00), radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%)");
		return b;
	}

	@Override
	public Node getNode() {
		return targetOptions;
	}
}
