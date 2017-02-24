package engine.model.components.concrete;

import authoring.model.ComponentData;
import engine.model.components.AbstractComponent;
import engine.model.entities.IEntity;
import gamePlayerView.gamePlayerView.Router;

/**
 * The purpose of this class is to manage the information relevant
 * to selling an object, such as the value a player would obtain
 * when selling an entity
 * @author matthewfaw
 *
 */
public class SellableComponentData extends AbstractComponent {
	private int mySellValue;
	
	public SellableComponentData(IEntity aEntity, ComponentData componentData, Router aRouter) {
		super(aEntity, aRouter);
		mySellValue = Integer.parseInt(componentData.getFields().get("mySellValue"));
	}
	public int getSellValue()
	{
		return mySellValue;
	}

	public void setSellPrice(int aPrice)
	{
		mySellValue = aPrice;
	}
	@Override
	public void distributeInfo() {
		// Do nothing
	}
	@Override
	public void delete() {
		// Do nothing
	}
}
