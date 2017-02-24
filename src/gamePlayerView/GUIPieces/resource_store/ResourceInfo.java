package gamePlayerView.GUIPieces.resource_store;

import java.util.Map;

import authoring.model.EntityData;
import engine.IObserver;
import gamePlayerView.interfaces.IGUIPiece;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ResourceInfo extends Text implements IGUIPiece, IObserver<Draggables>{
	
	private Map<ImageView,EntityData> myImageToDataMap;
	
	public ResourceInfo (Map<ImageView,EntityData> map) {
		myImageToDataMap = map;
	}
	
	@Override
	public Node getNode() {
		return this;
	}

	/**
	 * When the available towers are selected, this gets updated to show
	 * the information about that tower.
	 */
	@Override
	public void update(Draggables aChangedObject) {
		ImageView selected = aChangedObject.getSelectedImage();
		EntityData data = myImageToDataMap.get(selected);
		updateMyText(data);
	}

	private void updateMyText(EntityData data) {
		String name = data.getName();
		String cost = Integer.toString(data.getBuyPrice());
		String sellPrice = Integer.toString(data.getSellPrice());
		setText(String.format(
				"Tower Name: %s\nCost: %s\nSell Price: %s\n"
				, name
				, cost
				, sellPrice));
	}

	@Override
	public void remove(Draggables aRemovedObject) {
		// TODO Auto-generated method stub
	}


}
