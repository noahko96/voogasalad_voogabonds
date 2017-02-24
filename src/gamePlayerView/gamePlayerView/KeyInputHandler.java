package gamePlayerView.gamePlayerView;

import engine.controller.ApplicationController;
import gamePlayerView.GUIPieces.InfoBoxes.Controls;
import gamePlayerView.GUIPieces.InfoBoxes.PauseMenu;
import javafx.scene.input.KeyCode;

public class KeyInputHandler {
	private ApplicationController myAppController;
	private PauseMenu myPauseMenu;
	private Controls myControls;
	
	private KeyInputHandler data;
	
	public void handleKeyInput(KeyCode code) {
		if(code.getName().equals(data.myControls.getControlFor("Up"))){
			//System.out.println("Going up");
			data.myAppController.onUpButtonPressed();
		}
		else if(code.getName().equals(data.myControls.getControlFor("Pause"))){
			data.myPauseMenu.init();
		}
		else if(code.getName().equals(data.myControls.getControlFor("Down"))){
			//System.out.println("Going down");
			data.myAppController.onDownButtonPressed();
		}
		else if(code.getName().equals(data.myControls.getControlFor("Left"))){
			//System.out.println("Going left");
			data.myAppController.onLeftButtonPressed();
		}
		else if(code.getName().equals(data.myControls.getControlFor("Right"))){
			//System.out.println("Going right");
			data.myAppController.onRightButtonPressed();
		}
		else if(code.getName().equals("Space")){
			//System.out.println("Firing");
		}
	}
}