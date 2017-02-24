package engine.model.components.concrete;

import authoring.model.ComponentData;
import engine.model.components.AbstractComponent;
import engine.model.entities.IEntity;
import gamePlayerView.gamePlayerView.Router;

/**
 * A component that makes an entity buyable from the store
 * @author mathewfaw
 *
 */
public class PurchasableComponentData extends AbstractComponent {
	private int myPurchaseValue;
	
	public PurchasableComponentData(IEntity aEntity, ComponentData componentData, Router aRouter) {
		super(aEntity, aRouter);
		myPurchaseValue = Integer.parseInt(componentData.getFields().get("myPurchaseValue"));
	}
	
	/**
	 * Gets the buy price for this entity
	 * @return price
	 */
	public int getBuyPrice()
	{
		return myPurchaseValue;
	}
	
	/**
	 * Gets the sell price for this entity
	 * @return
	 */
	public void setBuyPrice(int aPrice)
	{
		myPurchaseValue = aPrice;
	}

	/********************IComponent interface***********/
	@Override
	public void distributeInfo() {
		// do nothing
	}

	@Override
	public void delete() {
		// do nothing
	}
}
