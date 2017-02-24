package gamePlayerView.GUIPieces;

import java.util.ArrayList;
import java.util.List;

import engine.controller.ApplicationController;
import gamePlayerView.GUIFactory.ButtonFactory;
import gamePlayerView.interfaces.IGUIPiece;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * 
 * @author Guhan Muruganandam
 * @author owenchung (refactored)
 */

public class GamePlayOptions extends VBox implements IGUIPiece {
	private ApplicationController myAppController;
	private ButtonFactory myButtonFactory;
	
	public GamePlayOptions(ApplicationController applicationcontroller){
		myAppController=applicationcontroller;
		myButtonFactory = new ButtonFactory();
		List<Button> buttons = createButtons();
		setButtonStyle(buttons);
		setConfiguration();
		getChildren().addAll(buttons);
	}
	
	private void setConfiguration() {
		setPrefWidth(100);
		setMaxHeight(700);
	    setPadding(new Insets(10, 10, 10, 10));
	    setSpacing(10);
	    setStyle("-fx-background-color: #336699;");
	}

	private void setButtonStyle(List<Button> buttons) {
		for (Button b : buttons) {
			b.setMinSize(110, 30);
			b.setStyle("-fx-background-color: linear-gradient(#f0ff35, #a9ff00), "
					+ "radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%)");
		}
		
	}

	private List<Button> createButtons() {
		Button playButton = myButtonFactory.getButton("Play", e -> myAppController.onPlayButtonPressed());
		Button pauseButton = myButtonFactory.getButton("Pause", e -> myAppController.onPauseButtonPressed());
		Button fastforwardButton = myButtonFactory.getButton("Fast-Forward", e -> myAppController.onFastButtonPressed());
		Button saveButton = myButtonFactory.getButton("Save", e -> myAppController.onSavePressed());
		Button refreshButton = myButtonFactory.getButton("Refresh", e -> myAppController.onRefreshPressed());
		List<Button> buttons = new ArrayList<Button>(4);
		buttons.add(playButton);
		buttons.add(pauseButton);
		buttons.add(fastforwardButton);		
		buttons.add(saveButton);
		buttons.add(refreshButton);
		return buttons;
	}

	@Override
	public Node getNode() {
		return this;
	}
}
