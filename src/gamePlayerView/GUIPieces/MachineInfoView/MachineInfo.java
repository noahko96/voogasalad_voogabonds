package gamePlayerView.GUIPieces.MachineInfoView;

import engine.model.components.viewable_interfaces.IViewablePhysical;
import gamePlayerView.interfaces.IGUIPiece;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * @author Guhan Muruganandam
 * 
 */

public class MachineInfo implements IGUIPiece {
	private VBox myDisplay;
	
	public MachineInfo(IViewablePhysical aComponent){
		myDisplay= new VBox();
		myDisplay=makeDisplay(aComponent);
	}

	private VBox makeDisplay(IViewablePhysical aComponent) {
		VBox vbox=new VBox();
		//Image image= new Image(this.getClass().getClassLoader().getResourceAsStream("resources/boss.png"));
		Image image= new Image(this.getClass().getClassLoader().getResourceAsStream(aComponent.getImagePath())); //Substring stuff?
		ImageView imageView=new ImageView();
		imageView.setImage(image);
		imageView.setFitWidth(100);
		imageView.setPreserveRatio(true);
		imageView.setSmooth(true);
		//imageView.setCache
		vbox.getChildren().add(imageView);
		
		return vbox;
		
	}

	@Override
	public Node getNode() {
		return myDisplay;
	}

}
