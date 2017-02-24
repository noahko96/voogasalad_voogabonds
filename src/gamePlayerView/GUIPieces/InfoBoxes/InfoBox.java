package gamePlayerView.GUIPieces.InfoBoxes;

import com.sun.prism.paint.Color;

import engine.IObservable;
import engine.model.playerinfo.IViewablePlayer;
import gamePlayerView.interfaces.IGUIPiece;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;


/**
 * 
 * @author Guhan Muruganandam
 *
 */

/**
 * UI feature for Statistics text box and Label
 */

public abstract class InfoBox implements IGUIPiece {
	public HBox myDisplay = new HBox();
	Label myLabel = new Label();
	Label myOutput = new Label();
	
	public InfoBox(){
		//
	}

	public HBox makeDisplay(String text){
		HBox h=new HBox();
		myLabel= makeLabel(text);
		myOutput = makeLabel("");//makeTextArea();
		h.getChildren().addAll(myLabel,myOutput);
		return h;
	}
	
	public TextArea makeTextArea() {
		TextArea t = new TextArea();
		t.setEditable(false);
		t.setMaxSize(5, 5);
		return t;
	}

	public Label makeLabel(String text){
		Label l= new Label(text);
		l.setFont(new Font("Cambria",14));
		//l.setTextFill(Color.web("#0076a3"));
		return l;
	}
	
	@Override
	public Node getNode(){
		return myDisplay;
	}
}
