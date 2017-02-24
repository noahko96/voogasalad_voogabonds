package main;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author Christopher Lu
 * This is the main class. This starts up the VoogaSalad Program by calling MainInitializer.
 */

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Main extends Application{
	
	private MainInitializer controller;
	
	@Override
	public void start (Stage s) throws IOException {
		controller = new MainInitializer(s);
		s.setTitle(controller.getTitle());
	}
	
	public static void main (String[] args) {
		launch(args);
	}
	
}
