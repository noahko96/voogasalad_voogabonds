package gamePlayerView.ScenePanes;

import java.util.Collection;

import gamePlayerView.interfaces.IGUIPiece;
import javafx.scene.Node;

/**
 * @author Guhan Muruganandam
 */

public interface IViewPane extends IGUIPiece{
	public void setUpPane();
	public void add(Collection<Node> collection);
	public void add(Node node);
	public void clear();
	
}
