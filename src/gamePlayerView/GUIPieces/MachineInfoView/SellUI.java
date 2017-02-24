package gamePlayerView.GUIPieces.MachineInfoView;

import engine.controller.ApplicationController;
import gamePlayerView.interfaces.IButtonMaker;
import gamePlayerView.interfaces.IGUIPiece;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class SellUI implements IGUIPiece,IButtonMaker {
	private Button mySellButton;
	private ApplicationController myAppController;
	public SellUI(ApplicationController appController){
		mySellButton=makeButton("Sell");
		myAppController=appController;
	}

	@Override
	public Button makeButton(String text) {
		Button b=new Button(text);
		b.setPrefSize(100, 20);
		b.setStyle("-fx-background-color: linear-gradient(#f0aa35, #a9ff00), radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%)");
		b.setOnAction(e->myAppController.onSellButtonPressed());
		return b;
	}

	@Override
	public Node getNode() {
		return mySellButton;
	}

}
