package gamePlayerView.GUIFactory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
/**
 * 
 * @author owenchung
 *
 */
public class ButtonFactory{


	public Button getButton(String label, EventHandler<ActionEvent> handler) {
		Button button = new Button(label);
		button.setOnAction(handler);
		return button;
	}
}
