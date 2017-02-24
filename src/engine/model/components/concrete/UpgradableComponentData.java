package engine.model.components.concrete;

import java.util.HashMap;
import java.util.Map;

import authoring.model.ComponentData;
import engine.model.components.AbstractComponent;
import engine.model.entities.IEntity;
import gamePlayerView.gamePlayerView.Router;

public class UpgradableComponentData extends AbstractComponent {
	private Map<String, Integer> myUpgradesMap;
	
	public UpgradableComponentData(IEntity aEntity, ComponentData componentData, Router aRouter)
	{
		super(aEntity, aRouter);
		myUpgradesMap = new HashMap<String, Integer>();
	}
	
	public Map<String, Integer> getUpgrades()
	{
		return myUpgradesMap;
	}

	@Override
	public void distributeInfo() {
		// do nothing
	}

	@Override
	public void delete() {
		// do nothing
	}
}
